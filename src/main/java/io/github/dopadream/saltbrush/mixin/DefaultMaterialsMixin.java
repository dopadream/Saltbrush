package io.github.dopadream.saltbrush.mixin;


import io.github.dopadream.saltbrush.util.CutoutTerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.DefaultMaterials;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.AlphaCutoffParameter;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

import static io.github.dopadream.saltbrush.Saltbrush.CUTOUT_NON_MIPPED;
import static net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.DefaultMaterials.*;

@Mixin(DefaultMaterials.class)
public class DefaultMaterialsMixin {

    @Unique
    private static final Map<RenderType, Material> renderLayerMap = Map.of(
            RenderType.solid(), SOLID,
            RenderType.cutout(), CUTOUT_NON_MIPPED,
            RenderType.cutoutMipped(), CUTOUT_MIPPED,
            RenderType.tripwire(), TRIPWIRE,
            RenderType.translucent(), TRANSLUCENT
    );

    /**
     * @author dopadream
     * @reason adds new cutout type
     */
    @Overwrite(remap = false)
    public static Material forRenderLayer(RenderType layer) {
        Material material = renderLayerMap.get(layer);
        if (material != null) {
            return material;
        }
        throw new IllegalArgumentException("No material mapping exists for " + layer);
    }
}
