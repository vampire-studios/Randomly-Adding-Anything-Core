package io.github.vampirestudios.raa_core;

import io.github.vampirestudios.raa_core.api.name_generation.Language;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.resources.ResourceLocation;
import java.util.Objects;

@Config(name = "raa_config")
@Config.Gui.Background("minecraft:textures/block/bedrock.png")
public class RAACoreConfig implements ConfigData {

	public final String language = Objects.requireNonNull(Language.LANGUAGE_REGISTRY.getKey(Language.ENGLISH)).toString();

    public Language getLanguage() {
        return Language.LANGUAGE_REGISTRY.get(ResourceLocation.tryParse(language));
    }

}
