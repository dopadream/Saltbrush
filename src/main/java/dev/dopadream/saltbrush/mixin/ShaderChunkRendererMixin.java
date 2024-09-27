package dev.dopadream.saltbrush.mixin;

import dev.dopadream.saltbrush.util.CutoutTerrainRenderPass;
import net.caffeinemc.mods.sodium.client.gl.shader.GlProgram;
import net.caffeinemc.mods.sodium.client.render.chunk.ShaderChunkRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkFogMode;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderInterface;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderOptions;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Mixin(ShaderChunkRenderer.class)
public abstract class ShaderChunkRendererMixin {

    @Shadow protected GlProgram<ChunkShaderInterface> activeProgram;

    @Shadow protected abstract GlProgram<ChunkShaderInterface> compileProgram(ChunkShaderOptions options);

    @Shadow @Final protected ChunkVertexType vertexType;

    @Shadow @Final private Map<ChunkShaderOptions, GlProgram<ChunkShaderInterface>> programs;

    @Shadow protected abstract GlProgram<ChunkShaderInterface> createShader(String path, ChunkShaderOptions options);

    protected GlProgram<ChunkShaderInterface> compileTransProgram(ChunkShaderOptions options) {
        GlProgram<ChunkShaderInterface> program = programs.get(options);

        if (program == null) {
            programs.put(options, program = createShader("blocks/block_layer_translucent", options));
        }

        return program;
    }

    protected GlProgram<ChunkShaderInterface> compileCutProgram(ChunkShaderOptions options) {
        GlProgram<ChunkShaderInterface> program = programs.get(options);

        if (program == null) {
            programs.put(options, program = createShader("blocks/block_layer_cutout", options));
        }

        return program;
    }

    protected GlProgram<ChunkShaderInterface> compileCutMipProgram(ChunkShaderOptions options) {
        GlProgram<ChunkShaderInterface> program = programs.get(options);

        if (program == null) {
            programs.put(options, program = createShader("blocks/block_layer_cutout_mipped", options));
        }

        return program;
    }

    /**
     * @author dopadream
     * @reason separate render passes into 4
     */
    @Overwrite(remap = false)
    protected void begin(TerrainRenderPass pass) {
        pass.startDrawing();
        ChunkShaderOptions options = new ChunkShaderOptions(ChunkFogMode.SMOOTH, pass, vertexType);
        // create map to associate TerrainRenderPasses with their corresponding compile methods
        Map<TerrainRenderPass, Function<ChunkShaderOptions, GlProgram<ChunkShaderInterface>>> programMap = new HashMap<>();
        programMap.put(DefaultTerrainRenderPasses.SOLID, this::compileProgram);
        programMap.put(DefaultTerrainRenderPasses.TRANSLUCENT, this::compileTransProgram);
        programMap.put(DefaultTerrainRenderPasses.CUTOUT, this::compileCutMipProgram);
        programMap.put(CutoutTerrainRenderPass.CUTOUT_NON_MIPPED, this::compileCutProgram);

        // get appropriate function from the map
        Function<ChunkShaderOptions, GlProgram<ChunkShaderInterface> > programFunction = programMap.get(pass);

        if (programFunction != null) {
            this.activeProgram = programFunction.apply(options);
        } else {
            throw new IllegalArgumentException("Unsupported TerrainRenderPass: " + pass);
        }

        activeProgram.bind();
        activeProgram.getInterface().setupState();
    }
}
