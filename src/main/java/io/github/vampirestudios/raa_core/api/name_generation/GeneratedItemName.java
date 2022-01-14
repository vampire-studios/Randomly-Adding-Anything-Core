package io.github.vampirestudios.raa_core.api.name_generation;

import com.ibm.icu.text.MessageFormat;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public interface GeneratedItemName {
    
    default Component generateName(String translationKey, Object[] args) {
        Component translatableText = new TranslatableComponent(translationKey);
        MessageFormat messageFormat = new MessageFormat(translatableText.getString());
        return new TextComponent(messageFormat.format(args));
    }

}
