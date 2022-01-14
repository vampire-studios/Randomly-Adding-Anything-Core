package io.github.vampirestudios.raa_core.api.name_generation;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;

public interface NameGenerator {
    String generate();

    default Tuple<String, ResourceLocation> generateUnique(Collection<ResourceLocation> presentIds, final String modId) {
        int loops = 0;
        ResourceLocation identifier;
        String name;
        do {
            name = generate();
            identifier = new ResourceLocation(modId, asId(name));
            if (++loops > 50) {
                throw new RuntimeException("Couldn't find a new name anymore.");
            }
        } while (presentIds.contains(identifier));
        return new Tuple<>(name, identifier);
    }

    Map<String, String> getSpecialCharactersMap();

    default String asId(String name) {
        String id = name;
        Map<String, String> specialCharacters = getSpecialCharactersMap();
        if (specialCharacters != null) {
            for (Map.Entry<String, String> specialCharacter : specialCharacters.entrySet()) {
                id = id.replace(specialCharacter.getKey(), specialCharacter.getValue());
                id = id.replace(specialCharacter.getKey().toUpperCase(), specialCharacter.getValue());
            }
        }
        id = id.toLowerCase(Locale.ENGLISH);
        return id;
    }
}
