package io.github.vampirestudios.raa_core.api.material;

import net.minecraft.item.ItemStack;

public interface RandomMaterialItem<T extends RAAMaterial> {
	
	public T getMaterial(ItemStack stack);
	
}