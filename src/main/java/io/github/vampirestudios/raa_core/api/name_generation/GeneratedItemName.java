package io.github.vampirestudios.raa_core.api.name_generation;

import com.ibm.icu.text.MessageFormat;
import net.minecraft.network.chat.Component;

public interface GeneratedItemName {
    
    default Component generateName(String translationKey, Object[] args) {
        Component translatableText = Component.translatable(translationKey);
        MessageFormat messageFormat = new MessageFormat(translatableText.getString());
        return Component.literal(messageFormat.format(args));
    }

}
