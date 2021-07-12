package io.github.vampirestudios.raa_core.api.data;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa_core.RAACore;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

public interface DataProvider {

    SimpleRegistry<DataProvider> DATA_PROVIDER_REGISTRY = FabricRegistryBuilder.createSimple(DataProvider.class, new Identifier(RAACore.MOD_ID, "data_provider")).buildAndRegister();

    Identifier getId();

    String getAddonId();

    <T> void generateJSONs(T object, ArtificeResourcePack.ServerResourcePackBuilder serverResourcePackBuilder);

    default Identifier makeId(String path) {
        return new Identifier(this.getAddonId(), path);
    }
}
