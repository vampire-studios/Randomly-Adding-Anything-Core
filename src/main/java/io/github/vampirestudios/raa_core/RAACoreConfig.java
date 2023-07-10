package io.github.vampirestudios.raa_core;

import io.github.vampirestudios.raa_core.api.name_generation.Language;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

@Config(name = "raa_config")
@Config.Gui.Background("minecraft:textures/block/bedrock.png")
public class RAACoreConfig implements ConfigData {

    @ConfigEntry.Gui.EnumHandler
	public final LanguageType language = LanguageType.ENGLISH;

    @ConfigEntry.Gui.Excluded
    public boolean shownNoAddonsScreen = false;

    public enum LanguageType {
        ENGLISH_LEGACY,
        ENGLISH,
        ENGLISH_PIRATE_SPEAK,
        ENGLISH_UPSIDE_DOWN,
        ENGLISH_SHAKESPEAREAN,
        FRENCH_LEGACY,
        FRENCH,
        CHINESE_LEGACY,
        CHINESE_SIMPLIFIED,
        CHINESE_TRADITIONAL,
        SPANISH_LEGACY,
        SPANISH,
        RUSSIAN_LEGACY,
        RUSSIAN,
        NORWEGIAN_LEGACY,
        NORWEGIAN_BOKMAL,
        NORWEGIAN_NYNORSK,
        DUTCH_LEGACY,
        VLAAMS,
        DUTCH,
        LOLCAT
    }

    public Language getLanguage() {
        return Language.LANGUAGE_REGISTRY.get(ResourceLocation.tryParse(language.name().toLowerCase(Locale.ROOT)));
    }

}
