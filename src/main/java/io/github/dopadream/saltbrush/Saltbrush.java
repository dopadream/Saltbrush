package io.github.dopadream.saltbrush;


import io.github.dopadream.saltbrush.util.CutoutTerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.AlphaCutoffParameter;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Saltbrush implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Saltbrush");
    public static final Material CUTOUT_NON_MIPPED = new Material(CutoutTerrainRenderPass.CUTOUT_NON_MIPPED, AlphaCutoffParameter.ONE_TENTH, false);


    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Hello Quilt world from {}! Stay fresh!", mod.metadata().name());
    }
}