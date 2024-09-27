package dev.dopadream.saltbrush.mixin;

import dev.dopadream.saltbrush.util.CutoutTerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.region.RenderRegionManager;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderRegionManager.class)
public class RenderRegionManagerMixin {

    @Redirect(method = "uploadResults(Lnet/caffeinemc/mods/sodium/client/gl/device/CommandList;Lnet/caffeinemc/mods/sodium/client/render/chunk/region/RenderRegion;Ljava/util/Collection;)V", at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/DefaultTerrainRenderPasses;ALL:[Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/TerrainRenderPass;", opcode = Opcodes.GETSTATIC), remap = false)
    private TerrainRenderPass[] inject() {
        return CutoutTerrainRenderPass.ALL_NEW;
    }

    @Redirect(method = "uploadResults(Lnet/caffeinemc/mods/sodium/client/gl/device/CommandList;Lnet/caffeinemc/mods/sodium/client/render/chunk/region/RenderRegion;Ljava/util/Collection;)V", at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/DefaultTerrainRenderPasses;TRANSLUCENT:Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/TerrainRenderPass;", opcode = Opcodes.GETSTATIC, ordinal = 0), remap = false)
    private TerrainRenderPass injectTrans() {
        return CutoutTerrainRenderPass.TRANSLUCENT;
    }

    @Redirect(method = "uploadResults(Lnet/caffeinemc/mods/sodium/client/gl/device/CommandList;Lnet/caffeinemc/mods/sodium/client/render/chunk/region/RenderRegion;Ljava/util/Collection;)V", at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/DefaultTerrainRenderPasses;TRANSLUCENT:Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/TerrainRenderPass;", opcode = Opcodes.GETSTATIC, ordinal = 1), remap = false)
    private TerrainRenderPass injectTrans2() {
        return CutoutTerrainRenderPass.TRANSLUCENT;
    }
}
