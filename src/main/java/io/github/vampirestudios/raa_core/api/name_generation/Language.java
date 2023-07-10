package io.github.vampirestudios.raa_core.api.name_generation;

import io.github.vampirestudios.raa_core.RAACore;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import java.util.HashMap;
import java.util.Map;

public class Language {
    public static final Registry<Language> LANGUAGE_REGISTRY = FabricRegistryBuilder.createSimple(Language.class, new ResourceLocation(RAACore.MOD_ID, "language")).buildAndRegister();

    public static final Language ENGLISH;
    public static final Language ENGLISH_PIRATE_SPEAK;
    public static final Language ENGLISH_UPSIDE_DOWN;
    public static final Language ENGLISH_SHAKESPEAREAN;
    public static final Language FRENCH;
    public static final Language CHINESE_SIMPLIFIED;
    public static final Language CHINESE_TRADITIONAL;
    public static final Language SPANISH;
    public static final Language RUSSIAN;
    public static final Language NORWEGIAN_BOKMAL;
    public static final Language NORWEGIAN_NYNORSK;
    public static final Language VLAAMS;
    public static final Language DUTCH;
    public static final Language LOLCAT;

    private final Map<String, NameGenerator> nameGeneratorMap;
    private final String id;

    public Language(String id) {
        this.id = id;
        this.nameGeneratorMap = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public Language addNameGenerator(String type, NameGenerator nameGenerator) {
        this.nameGeneratorMap.put(type, nameGenerator);
        return this;
    }

    public boolean hasNameGeneratorType(String type) {
        return this.nameGeneratorMap.containsKey(type);
    }

    public NameGenerator getNameGenerator(String type) {
        if (this.hasNameGeneratorType(type)) return this.nameGeneratorMap.get(type);
        else return ENGLISH.getNameGenerator(type);
    }

    static {
        ENGLISH = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("en_us"), new Language("en_us"));
        ENGLISH_PIRATE_SPEAK = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("en_7s"), new Language("en_7s"));
        ENGLISH_UPSIDE_DOWN = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("en_ud"), new Language("en_ud"));
        ENGLISH_SHAKESPEAREAN = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("en_ws"), new Language("en_ws"));
        FRENCH = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("fr_fr"), new Language("fr_fr"));
        CHINESE_SIMPLIFIED = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("zh_cn"), new Language("zh_cn"));
        CHINESE_TRADITIONAL = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("zh_tw"), new Language("zh_tw"));
        SPANISH = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("es_es"), new Language("es_es"));
        RUSSIAN = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("ru_ru"), new Language("ru_ru"));
        NORWEGIAN_BOKMAL = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("no_no"), new Language("no_no"));
        NORWEGIAN_NYNORSK = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("nn_no"), new Language("nn_no"));
        VLAAMS = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("nl_be"), new Language("nl_be"));
        DUTCH = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("nl_nl"), new Language("nl_nl"));
        LOLCAT = Registry.register(LANGUAGE_REGISTRY, new ResourceLocation("lol_aa"), new Language("lol_aa"));
    }
}
