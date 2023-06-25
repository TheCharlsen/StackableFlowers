package com.sunnyspaceweather;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StackableFlowers implements ModInitializer {
	public static final String modid = "stackableflowers";
    public static final Logger LOGGER = LoggerFactory.getLogger("stackableflowers");

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
	}
}