package io.github.vampirestudios.raa_core;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa_core.api.name_generation.Language;
import net.fabricmc.fabric.api.config.v1.GsonSerializer;
import net.fabricmc.loader.api.config.data.SaveType;
import net.fabricmc.loader.api.config.entrypoint.Config;
import net.fabricmc.loader.api.config.serialization.ConfigSerializer;
import net.fabricmc.loader.api.config.value.ValueKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class RAACoreConfig extends Config<JsonObject> {

    /*This is a test*/
    public static final ValueKey<String> namingLanguage = value(Language.LANGUAGE_REGISTRY.getId(Language.ENGLISH).toString());

    public Language getLanguage() {
        return Language.LANGUAGE_REGISTRY.get(new Identifier(this.namingLanguage.getValue()));
    }

    @Override
    public @NotNull ConfigSerializer<JsonObject> getSerializer() {
        return GsonSerializer.DEFAULT;
    }

    @Override
    public @NotNull SaveType getSaveType() {
        return SaveType.ROOT;
    }

    @Override
    public @NotNull String getName() {
        return "raa-config";
    }
}
