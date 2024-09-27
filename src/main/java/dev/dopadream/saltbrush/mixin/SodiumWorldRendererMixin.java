package dev.dopadream.saltbrush.mixin;


import dev.dopadream.saltbrush.util.CutoutTerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.ChunkRenderMatrices;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(SodiumWorldRenderer.class)
public class SodiumWorldRendererMixin {



    @Shadow private RenderSectionManager renderSectionManager;

    /**
     * @author dopadream
     * @reason separate render passes into 4 (5)?
     */
    @Overwrite(remap = false)
    public void drawChunkLayer(RenderType renderLayer, ChunkRenderMatrices matrices, double x, double y, double z) {
        Map<RenderType, TerrainRenderPass> renderPassMap = new HashMap<>();
        renderPassMap.put(RenderType.solid(), DefaultTerrainRenderPasses.SOLID);
        renderPassMap.put(RenderType.translucent(), DefaultTerrainRenderPasses.TRANSLUCENT);
        renderPassMap.put(RenderType.cutout(), CutoutTerrainRenderPass.CUTOUT_NON_MIPPED);
        renderPassMap.put(RenderType.cutoutMipped(), DefaultTerrainRenderPasses.CUTOUT);
        renderPassMap.put(RenderType.tripwire(), CutoutTerrainRenderPass.CUTOUT_NON_MIPPED);


        TerrainRenderPass pass = renderPassMap.get(renderLayer);
        if (pass != null) {
            renderSectionManager.renderLayer(matrices, pass, x, y, z);
        } else {
            throw new IllegalArgumentException("Unsupported Render Type: " + pass);
        }
    }
}
