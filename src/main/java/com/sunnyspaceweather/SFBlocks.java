package com.sunnyspaceweather;

import com.sunnyspaceweather.block.StackableFlowerBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class SFBlocks {

    public static final Block stackableflowerblock = registerBlock("stackableflower",
            new StackableFlowerBlock(FabricBlockSettings.copyOf(Blocks.POPPY).sounds(BlockSoundGroup.GRASS).nonOpaque().breakInstantly()));
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(StackableFlowers.modid, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(StackableFlowers.modid, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        StackableFlowers.LOGGER.info("Registering ModBlocks for " + StackableFlowers.modid);
    }
}
