package com.sunnyspaceweather;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StackableFlowers implements ModInitializer {
	public static final String modid = "stackableflowers";
    public static final Logger LOGGER = LoggerFactory.getLogger("stackableflowers");

	@Override
	public void onInitialize() {

		SFBlocks.registerModBlocks();
	}
}