package io.github.vampirestudios.raa_core.api.name_generation;

import io.github.vampirestudios.raa_core.RAACore;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.HashMap;
import java.util.Map;

public class Language {
    public static final SimpleRegistry<Language> LANGUAGE_REGISTRY = Registry.register(Registry.REGISTRIES, new Identifier(RAACore.MOD_ID, "language"),new SimpleRegistry<Language>());

    public static final Language ENGLISH;
    public static final Language FRENCH;
    public static final Language CHINESE;
    public static final Language SPANISH;
    public static final Language NORWEGIAN_BO;

    private Map<String, NameGenerator> nameGeneratorMap;
    private String id;

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
        ENGLISH = Registry.register(LANGUAGE_REGISTRY, new Identifier("en"), new Language("en"));
        FRENCH = Registry.register(LANGUAGE_REGISTRY, new Identifier("fr"), new Language("fr"));
        CHINESE = Registry.register(LANGUAGE_REGISTRY, new Identifier("zh"), new Language("zh"));
        SPANISH = Registry.register(LANGUAGE_REGISTRY, new Identifier("es"), new Language("es"));
        NORWEGIAN_BO = Registry.register(LANGUAGE_REGISTRY, new Identifier("no_bo"), new Language("no_bo"));
    }
}
