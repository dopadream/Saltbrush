package dev.dopadream.saltbrush.util;

import net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.minecraft.client.renderer.RenderType;

public class CutoutTerrainRenderPass extends DefaultTerrainRenderPasses {
    public static final TerrainRenderPass CUTOUT_NON_MIPPED = new TerrainRenderPass(RenderType.cutout(), false, true);
    public static final TerrainRenderPass[] ALL_NEW = new TerrainRenderPass[]{SOLID, CUTOUT, CUTOUT_NON_MIPPED, TRANSLUCENT};
    public CutoutTerrainRenderPass() {
    }
}
