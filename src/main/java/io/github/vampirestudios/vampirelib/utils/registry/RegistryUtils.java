package io.github.vampirestudios.vampirelib.utils.registry;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegistryUtils {

    public static Block register(Block block, Identifier name) {
        register(block, name, ItemGroup.DECORATIONS);
        return block;
    }

    public static Block register(Block block, Identifier name, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, name, block);
        BlockItem item = new BlockItem(block, new Item.Settings().group(itemGroup));
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        Registry.register(Registry.ITEM, name, item);
        return block;
    }

    public static Block registerBlockWithoutItem(Block block, Identifier identifier) {
        Registry.register(Registry.BLOCK, identifier, block);
        return block;
    }

    public static Item registerItem(Item item, Identifier name) {
        return Registry.register(Registry.ITEM, name, item);
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(BlockEntityType.Builder<T> builder, Identifier name) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        Registry.register(Registry.BLOCK_ENTITY, name, blockEntityType);
        return blockEntityType;
    }

}
