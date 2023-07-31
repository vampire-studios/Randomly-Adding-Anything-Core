package io.github.vampirestudios.raa_core.api.material;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface RandomMaterialBlock<T extends RAAMaterial> {
	
	public T getMaterial(World world, BlockState state, BlockPos pos);
	
}