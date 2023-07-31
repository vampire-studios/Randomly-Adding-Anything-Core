package io.github.vampirestudios.raa_core.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;

import io.github.vampirestudios.raa_core.RAACore;
import io.github.vampirestudios.raa_core.helpers.GsonHelper;
import net.fabricmc.fabric.api.util.TriState;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public abstract class RAAConfig {
	
	private final String configName;
	private final File saveFile; 
	private final HashMap<String, Field<?, ?>> fields = new HashMap<String, Field<?, ?>>();

	public RAAConfig(String configName) {
		this.configName = configName;
		this.saveFile = FabricLoader.getInstance().getConfigDir().resolve("raa/" + configName + ".json").toFile();

		TriState loadSuccess = this.load();
		if(loadSuccess == TriState.DEFAULT) {
			for(Field<?, ?> field : this.fields.values()) {
				field.reset();
			}
		}
		if(loadSuccess != TriState.TRUE) {
			this.save();
		}
	}

	private final TriState load() {
		if(this.saveFile.exists()) {
			try(FileReader reader = new FileReader(this.saveFile)) {
				boolean forceSave = false;
				JsonObject data = GsonHelper.GSON.fromJson(reader, JsonObject.class);
				for(Entry<String, Field<?, ?>> entry : this.fields.entrySet()) {
					if(data.has(entry.getKey())) {
						entry.getValue().fromJson(data.get(entry.getKey()));
					} else {
						forceSave = true;
					}
				}
				return TriState.of(!forceSave);
			} catch (IOException | ClassCastException e) {
				RAACore.LOGGER.error("The following exception was raised while loading the config for %s:", this.configName);
				e.printStackTrace();
			}
		}
		return TriState.DEFAULT;
	}

	private final void save() {
		try(JsonWriter writer = new JsonWriter(new FileWriter(this.saveFile))) {
			GsonHelper.GSON.toJson(null, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected <T, J extends JsonElement> FieldBuilder<T, J> field(String name) {
		return new FieldBuilder<T, J>(this, name);
	}
	
	protected static class Field<T, J extends JsonElement> {
		
		private final Supplier<T> getter;
		private final Consumer<T> setter;
		private final Function<T, J> toJson;
		private final Function<J, T> fromJson;
		
		private final T defaultValue;
		
		Field(Supplier<T> getter, Consumer<T> setter, Function<T, J> toJson, Function<J, T> fromJson) {
			this.getter = getter;
			this.setter = setter;
			this.toJson = toJson;
			this.fromJson = fromJson;
			
			this.defaultValue = this.get();
		}
		
		public T get() {
			return this.getter.get();
		}
		
		public void set(T t) {
			this.setter.accept(t);
		}
		
		public void reset() {
			this.set(this.defaultValue);
		}
		
		public J toJson() {
			return this.toJson.apply(this.get());
		}
		
		@SuppressWarnings("unchecked")
		public void fromJson(JsonElement j) {
			this.set(this.fromJson.apply((J) j));
		}
		
	}
	
	public static class FieldBuilder<T, J extends JsonElement> {
		
		private boolean built = false;
		
		private final RAAConfig config;
		private final String name;
		
		// Config screen getter/setter
		private Supplier<T> getter;
		private Consumer<T> setter;
		
		// Json getter/setter
		private Function<T, J> toJson;
		private Function<J, T> fromJson;
		
		public FieldBuilder(RAAConfig config, String name) {
			if(config.fields.containsKey(name)) {
				throw new IllegalArgumentException("Field named \"" + name + "\" already exists");
			}
			this.config = config;
			this.name = name;
		}
		
		public FieldBuilder<T, J> getter(Supplier<T> getter) {
			this.getter = getter;
			return this;
		}
		
		public FieldBuilder<T, J> setter(Consumer<T> setter) {
			this.setter = setter;
			return this;
		}
		
		public FieldBuilder<T, J> toJson(Function<T, J> toJson) {
			this.toJson = toJson;
			return this;
		}
		
		public FieldBuilder<T, J> fromJson(Function<J, T> fromJson) {
			this.fromJson = fromJson;
			return this;
		}
		
		public void build() {
			if(this.built) {
				throw new IllegalStateException("This Field has already been built");
			}
			this.built = true;
			this.config.fields.put(this.name, new Field<T, J>(this.getter, this.setter, this.toJson, this.fromJson));
		}
		
	}
	
	public static class Functions {
		
		private static final HashMap<Registry<?>, Function<?, JsonPrimitive>> REGISTRY_OBJ_TO_JSON =
				new HashMap<Registry<?>, Function<?, JsonPrimitive>>();
			
		private static final HashMap<Registry<?>, Function<JsonPrimitive, ?>> JSON_TO_REGISTRY_OBJ =
			new HashMap<Registry<?>, Function<JsonPrimitive, ?>>();
		
		@SuppressWarnings("unchecked")
		public static <T> Function<T, JsonPrimitive> registryObjToJson(final Registry<T> registry) {
			if(!REGISTRY_OBJ_TO_JSON.containsKey(registry)) {
				REGISTRY_OBJ_TO_JSON.put(registry, new Function<T, JsonPrimitive>() {
					@Override
					public JsonPrimitive apply(T t) {
						return new JsonPrimitive(registry.getId(t).toString());
					}
				});
			}
			return (Function<T, JsonPrimitive>) REGISTRY_OBJ_TO_JSON.get(registry);
		}
		
		@SuppressWarnings("unchecked")
		public static <T> Function<JsonPrimitive, T> jsonToRegistryObj(final Registry<T> registry) {
			if(!JSON_TO_REGISTRY_OBJ.containsKey(registry)) {
				JSON_TO_REGISTRY_OBJ.put(registry, new Function<JsonPrimitive, T>() {
					@Override
					public T apply(JsonPrimitive primitive) {
						try {
							return registry.get(new Identifier(primitive.getAsString()));
						} catch(Exception e) {
							if(registry instanceof DefaultedRegistry<T> defRegistry) {
								return defRegistry.get(defRegistry.getDefaultId());
							}
							return null;
						}
					}
				});
			}
			return (Function<JsonPrimitive, T>) JSON_TO_REGISTRY_OBJ.get(registry);
		}
		
	}
	
}
