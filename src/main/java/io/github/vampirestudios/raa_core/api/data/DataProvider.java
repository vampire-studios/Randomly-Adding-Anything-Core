package io.github.vampirestudios.raa_core.api.data;

import io.github.vampirestudios.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa_core.RAACore;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceLocation;

public interface DataProvider {

    MappedRegistry<DataProvider> DATA_PROVIDER_REGISTRY = FabricRegistryBuilder.createSimple(DataProvider.class, new ResourceLocation(RAACore.MOD_ID, "data_provider")).buildAndRegister();

    ResourceLocation getId();

    String getAddonId();

    <T> void generateJSONs(T object, ArtificeResourcePack.ServerResourcePackBuilder serverResourcePackBuilder);

    default ResourceLocation makeId(String path) {
        return new ResourceLocation(this.getAddonId(), path);
    }
}
