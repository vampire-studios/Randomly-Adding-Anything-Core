package io.github.vampirestudios.raa_core;

import io.github.vampirestudios.raa_core.api.name_generation.Language;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import net.minecraft.util.Identifier;

@Config(name = "raa_config")
public class RAACoreConfig implements ConfigData {

	public final String language = Language.LANGUAGE_REGISTRY.getId(Language.ENGLISH).toString();

    public Language getLanguage() {
        return Language.LANGUAGE_REGISTRY.get(Identifier.tryParse(language));
    }

}
