package io.github.vampirestudios.raa_core;

import io.github.vampirestudios.raa_core.api.name_generation.Language;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import net.minecraft.util.Identifier;

@Config(name=RAACore.MOD_ID)
public class RAACoreConfig implements ConfigData {

    @Comment("The language names are generated in")
    public String namingLanguage = Language.LANGUAGE_REGISTRY.getId(Language.ENGLISH).toString();

    public Language getLanguage() {
        return Language.LANGUAGE_REGISTRY.get(new Identifier(this.namingLanguage));
    }

}
