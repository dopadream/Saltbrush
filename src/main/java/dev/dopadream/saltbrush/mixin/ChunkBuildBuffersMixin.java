package dev.dopadream.saltbrush.mixin;


import dev.dopadream.saltbrush.util.CutoutTerrainRenderPass;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.buffers.BakedChunkModelBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.data.BuiltSectionInfo;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.builder.ChunkMeshBufferBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkBuildBuffers.class)
public abstract class ChunkBuildBuffersMixin {


    @Shadow @Final private Reference2ReferenceOpenHashMap<TerrainRenderPass, BakedChunkModelBuilder> builders;

    @Shadow @Final private ChunkVertexType vertexType;


    /**
     * @author dopadream
     * @reason clears builders and replaces with new list including separated cutout types
     */
    @Overwrite(remap = false)
    public void init(BuiltSectionInfo.Builder renderData, int sectionIndex) {
        builders.clear();
        for (TerrainRenderPass pass : CutoutTerrainRenderPass.ALL_NEW) {
            ChunkMeshBufferBuilder[] vertexBuffers = new ChunkMeshBufferBuilder[ModelQuadFacing.COUNT];

            for (int facing = 0; facing < ModelQuadFacing.COUNT; facing++) {
                vertexBuffers[facing] = new ChunkMeshBufferBuilder(vertexType, 131072);
            }

            this.builders.put(pass, new BakedChunkModelBuilder(vertexBuffers));
        }
        for (BakedChunkModelBuilder builder : builders.values()) {
            builder.begin(renderData, sectionIndex);
        }
    }
}
