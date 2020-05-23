package io.github.vampirestudios.raa_core.api.name_generation;

import com.mojang.serialization.Lifecycle;
import io.github.vampirestudios.raa_core.RAACore;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.HashMap;
import java.util.Map;

public class Language {
    public static final SimpleRegistry<Language> LANGUAGE_REGISTRY = new SimpleRegistry<>(RegistryKey.ofRegistry(new Identifier(RAACore.MOD_ID, "language")), Lifecycle.stable());
    public static final SimpleRegistry<String> NAME_GENERATOR_TYPE_REGISTRY = new SimpleRegistry<>(RegistryKey.ofRegistry(new Identifier(RAACore.MOD_ID, "name_generator_type")), Lifecycle.stable());

    public static final Language ENGLISH_US;

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
        else return ENGLISH_US.getNameGenerator(type);
    }

    static {
        ENGLISH_US = Registry.register(LANGUAGE_REGISTRY, new Identifier("en_us"), new Language("en_us"));
    }
}
