package dev.dopadream.saltbrush;

import dev.dopadream.saltbrush.util.CutoutTerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.AlphaCutoffParameter;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Saltbrush implements ModInitializer {
	public static final String MOD_ID = "saltbrush";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Material CUTOUT_NON_MIPPED = new Material(CutoutTerrainRenderPass.CUTOUT_NON_MIPPED, AlphaCutoffParameter.ONE_TENTH, false);

	@Override
	public void onInitialize() {

		LOGGER.info("Saltbrush initialized!");
	}
}