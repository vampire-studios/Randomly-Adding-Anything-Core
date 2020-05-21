package io.github.vampirestudios.raa_core.api.name_generation;

import com.ibm.icu.text.MessageFormat;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public interface GeneratedItemName {
    
    default Text generateName(String translationKey, Object[] args) {
        Text translatableText = new TranslatableText(translationKey);
        MessageFormat messageFormat = new MessageFormat(translatableText.getString());
        return new LiteralText(messageFormat.format(args));
    }

}
