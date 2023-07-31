package io.github.vampirestudios.raa_core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Joiner;

import io.github.vampirestudios.raa_core.api.RAAAddon;
import io.github.vampirestudios.raa_core.config.RAAConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModDependency;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.impl.metadata.LoaderModMetadata;
import net.minecraft.SharedConstants;

public class RAACore implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger();

	public static final String MOD_ID = "raa_core";
	public static final String MOD_VERSION = "3.0.0+build.1";

	public static Map<String, RAAAddon> ADDON_MAP;
	
	public static Joiner MOD_JOINER = Joiner.on(", ");
	
	public static RAAConfig CONFIG;

	@Override
	public void onInitialize() {		
		LOGGER.info(String.format("You're now running RAA: Core v%s for %s", MOD_VERSION, SharedConstants.getGameVersion().getName()));

		CONFIG = new RAACoreConfig();

		ADDON_MAP = discoverAddons(RAAAddon.class, "raa:addon");
		LOGGER.info("Discovered " + ADDON_MAP.size() + " RAA addons: " + MOD_JOINER.join(ADDON_MAP.keySet()));

		ADDON_MAP.values().forEach(RAAAddon::onInitialize);
	}
	
	private static boolean isRaaAddon(ModContainer mod, String entrypointName) {
		boolean hasDep = false;
		boolean hasEntrypoint = false;
		
		ModMetadata meta = mod.getMetadata();
		for(ModDependency dep : meta.getDependencies()) {
			if(dep.getKind() == ModDependency.Kind.DEPENDS && dep.getModId().equals(MOD_ID)) {
				hasDep = true;
			}
		}
		if(meta instanceof LoaderModMetadata lmeta) {
			if(lmeta.getEntrypointKeys().contains(entrypointName)) {
				hasEntrypoint = true;
			}
		}
		return hasDep && hasEntrypoint;
	}
	
	private static String getModid(ModContainer mod) {
		return mod.getMetadata().getId();
	}
	
	public static <T> HashMap<String, T> discoverAddons(Class<T> entrypointClass, String entrypointName) {
		HashMap<String, T> addonMap = new HashMap<String, T>();
		
		Set<String> raaAddons = FabricLoader.getInstance().getAllMods().stream()
			.filter(new Predicate<ModContainer>() {
				@Override
				public boolean test(ModContainer mod) {
					return isRaaAddon(mod, entrypointName);
				}
			}).map(RAACore::getModid).collect(Collectors.toSet());
		List<EntrypointContainer<T>> raaAddonEntrypoints =
			FabricLoader.getInstance().getEntrypointContainers(entrypointName, entrypointClass);

		Set<ModContainer> mods = new HashSet<ModContainer>();
		Set<ModContainer> duplicates = raaAddonEntrypoints.stream().map(EntrypointContainer::getProvider)
			.filter(mods::add).collect(Collectors.toSet());
		
		if(!duplicates.isEmpty()) {
			String s = "[" + MOD_JOINER.join(duplicates) + "]";
			throw new RAADiscoveryException("The following mod(s) tried to register multiple addons: " + s);
		}

		int lastSatisfied = -1;

		while(!raaAddonEntrypoints.isEmpty()) {
			if(lastSatisfied == 0) {
				// No addon could be satisfied during last loop, aborting
				String[] failedModids = new String[raaAddonEntrypoints.size()];
				for(int i = 0; i < raaAddonEntrypoints.size(); i++) {
					failedModids[i] = getModid(raaAddonEntrypoints.get(i).getProvider());
				}
				String s = "[" + MOD_JOINER.join(failedModids) + "]";
				throw new RAADiscoveryException("Failed to load the following RAA addons: " + s);
			}
			lastSatisfied = 0;
			Iterator<EntrypointContainer<T>> iterator = raaAddonEntrypoints.iterator();
			while(iterator.hasNext()) {
				EntrypointContainer<T> addon = iterator.next();
				boolean satisfied = true;
				for(ModDependency dep : addon.getProvider().getMetadata().getDependencies()) {
					if(dep.getKind() != ModDependency.Kind.DEPENDS) {
						continue;
					}
					if(raaAddons.contains(dep.getModId())) {
						continue;
					}
					if(!ADDON_MAP.containsKey(dep.getModId())) {
						satisfied = false;
						break;
					}
				}
				
				if(satisfied) {
					lastSatisfied ++;
					addonMap.put(getModid(addon.getProvider()), addon.getEntrypoint());
					iterator.remove();
				}
			}
		}
		
		return addonMap;
	}

	public static class RAADiscoveryException extends RuntimeException {

		private static final long serialVersionUID = 1809031118151212L;
		
		public RAADiscoveryException(String msg) {
			super(msg);
		}
		
	}

}