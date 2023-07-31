package io.github.vampirestudios.raa_core.api.name_generation;

import io.github.vampirestudios.raa_core.RAACore;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.Map;

public class RAALanguage {
	
	public static final RegistryKey<Registry<RAALanguage>> REGISTRY_KEY =
		RegistryKey.ofRegistry(new Identifier(RAACore.MOD_ID, "language"));
	
	public static final Registry<RAALanguage> REGISTRY =
		FabricRegistryBuilder.createDefaulted(REGISTRY_KEY, new Identifier("en_us")).buildAndRegister();
	
	public static final RAALanguage ENGLISH = register("en_us");
	public static final RAALanguage ENGLISH_PIRATE_SPEAK = register("en_7s");
	public static final RAALanguage ENGLISH_UPSIDE_DOWN = register("en_ud");
	public static final RAALanguage ENGLISH_SHAKESPEAREAN = register("en_ws");
	public static final RAALanguage FRENCH = register("fr_fr");
	public static final RAALanguage CHINESE_SIMPLIFIED = register("zh_cn");
	public static final RAALanguage CHINESE_TRADITIONAL = register("zh_tw");
	public static final RAALanguage SPANISH = register("es_es");
	public static final RAALanguage RUSSIAN = register("ru_ru");
	public static final RAALanguage NORWEGIAN_BOKMAL = register("no_no");
	public static final RAALanguage NORWEGIAN_NYNORSK = register("nn_no");
	public static final RAALanguage VLAAMS = register("nl_be");
	public static final RAALanguage DUTCH = register("nl_nl");
	public static final RAALanguage LOLCAT = register("lol_aa");

	private final Map<String, NameGenerator> nameGeneratorMap;
	private final String id;

	public RAALanguage(String id) {
		this.id = id;
		this.nameGeneratorMap = new HashMap<>();
	}

	public String getId() {
		return id;
	}

	public RAALanguage addNameGenerator(String type, NameGenerator nameGenerator) {
		this.nameGeneratorMap.put(type, nameGenerator);
		return this;
	}

	public boolean hasNameGeneratorType(String type) {
		return this.nameGeneratorMap.containsKey(type);
	}

	public NameGenerator getNameGenerator(String type) {
		if (this.hasNameGeneratorType(type)) return this.nameGeneratorMap.get(type);
		else return ENGLISH.getNameGenerator(type);
	}

	public static RAALanguage register(String name) {
		return Registry.register(REGISTRY, new Identifier(name), new RAALanguage(name));
	}
	
}
