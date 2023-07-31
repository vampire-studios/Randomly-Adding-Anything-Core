package io.github.vampirestudios.raa_core.api.client.textures;

import io.github.vampirestudios.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa_core.RAACore;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public interface TextureProvider {

	public static final RegistryKey<Registry<TextureProvider>> REGISTRY_KEY =
		RegistryKey.ofRegistry(new Identifier(RAACore.MOD_ID, "texture_provider"));
	
	public static final SimpleRegistry<TextureProvider> REGISTRY =
		FabricRegistryBuilder.createSimple(REGISTRY_KEY).buildAndRegister();

	Identifier getId();

	String getAddonId();

	<T> void generateJSONs(T object, ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder);

	default Identifier makeId(String path) {
		return new Identifier(this.getAddonId(), path);
	}

}
