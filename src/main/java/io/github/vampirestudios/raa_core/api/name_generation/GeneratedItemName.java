package io.github.vampirestudios.raa_core.api.name_generation;

import com.ibm.icu.text.MessageFormat;
import net.minecraft.text.Text;

public interface GeneratedItemName {
	
	default Text generateName(String translationKey, Object[] args) {
		Text translatableText = Text.translatable(translationKey);
		MessageFormat messageFormat = new MessageFormat(translatableText.getString());
		return Text.literal(messageFormat.format(args));
	}

}
