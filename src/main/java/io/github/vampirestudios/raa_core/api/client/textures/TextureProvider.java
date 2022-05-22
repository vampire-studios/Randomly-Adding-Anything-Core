package io.github.vampirestudios.raa_core.api.client.textures;

import io.github.vampirestudios.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa_core.RAACore;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceLocation;

public interface TextureProvider {

    MappedRegistry<TextureProvider> TEXTURE_PROVIDER_REGISTRY = FabricRegistryBuilder.createSimple(TextureProvider.class, new ResourceLocation(RAACore.MOD_ID, "texture_provider")).buildAndRegister();

    ResourceLocation getId();

    String getAddonId();

    <T> void generateJSONs(T object, ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder);

    default ResourceLocation makeId(String path) {
        return new ResourceLocation(this.getAddonId(), path);
    }

}
