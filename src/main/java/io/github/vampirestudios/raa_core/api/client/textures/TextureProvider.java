package io.github.vampirestudios.raa_core.api.client.textures;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.raa_core.RAACore;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

public interface TextureProvider {

    SimpleRegistry<TextureProvider> TEXTURE_PROVIDER_REGISTRY = FabricRegistryBuilder.createSimple(TextureProvider.class, new Identifier(RAACore.MOD_ID, "texture_provider")).buildAndRegister();

    Identifier getId();

    String getAddonId();

    <T> void generateJSONs(T object, ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder);

    default Identifier makeId(String path) {
        return new Identifier(this.getAddonId(), path);
    }

}
