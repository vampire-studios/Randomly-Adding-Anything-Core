package io.github.vampirestudios.raa_core.api.data;

import io.github.vampirestudios.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa_core.RAACore;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public interface DataProvider {

	public static final RegistryKey<Registry<DataProvider>> REGISTRY_KEY =
		RegistryKey.ofRegistry(new Identifier(RAACore.MOD_ID, "data_provider"));
	
	public static final SimpleRegistry<DataProvider> REGISTRY =
		FabricRegistryBuilder.createSimple(REGISTRY_KEY).buildAndRegister();

	Identifier getId();

	String getAddonId();

	<T> void generateJSONs(T object, ArtificeResourcePack.ServerResourcePackBuilder serverResourcePackBuilder);

	default Identifier makeId(String path) {
		return new Identifier(this.getAddonId(), path);
	}
}
