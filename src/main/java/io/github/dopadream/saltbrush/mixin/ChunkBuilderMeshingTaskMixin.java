package io.github.dopadream.saltbrush.mixin;


import io.github.dopadream.saltbrush.util.CutoutTerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkBuilderMeshingTask.class)
public class ChunkBuilderMeshingTaskMixin {
    @Redirect(method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;", at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/DefaultTerrainRenderPasses;ALL:[Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/TerrainRenderPass;", opcode = Opcodes.GETSTATIC), remap = false)
    private TerrainRenderPass[] inject() {
        return CutoutTerrainRenderPass.ALL_NEW;
    }

    @Redirect(method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;", at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/DefaultTerrainRenderPasses;TRANSLUCENT:Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/TerrainRenderPass;", opcode = Opcodes.GETSTATIC), remap = false)
    private TerrainRenderPass injectTrans() {
        return CutoutTerrainRenderPass.TRANSLUCENT;
    }
}
