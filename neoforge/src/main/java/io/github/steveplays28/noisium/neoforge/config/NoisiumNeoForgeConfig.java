package io.github.steveplays28.noisium.neoforge.config;

import io.github.steveplays28.noisium.config.NoisiumConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * NeoForge config using ForgeConfigSpec.
 * Syncs to shared NoisiumConfig instance.
 */
public class NoisiumNeoForgeConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue NOISE_CHUNK_GENERATOR;
    public static final ModConfigSpec.BooleanValue GENERATION_SHAPE_CONFIG;
    public static final ModConfigSpec.BooleanValue CHUNK_SECTION;
    public static final ModConfigSpec.BooleanValue CHAINED_BLOCK_SOURCE;
    public static final ModConfigSpec.BooleanValue CHUNK_NOISE_SAMPLER_INTERPOLATION;
    public static final ModConfigSpec.BooleanValue USE_GUI_GRAPHICS;

    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("mixins");
        BUILDER.comment("Optimization toggles - requires game restart to take effect");

        NOISE_CHUNK_GENERATOR = BUILDER
                .comment("Optimizes NoiseChunkGenerator#populateNoise for 20-30% speedup in world generation")
                .define("noiseChunkGenerator", true);

        GENERATION_SHAPE_CONFIG = BUILDER
                .comment("Caches GenerationShapeConfig computations")
                .define("generationShapeConfig", true);

        CHUNK_SECTION = BUILDER
                .comment("Optimizes ChunkSection#populateBiomes axis order")
                .define("chunkSection", true);

        CHAINED_BLOCK_SOURCE = BUILDER
                .comment("Micro-optimization for ChainedBlockSource lookups")
                .define("chainedBlockSource", true);

        CHUNK_NOISE_SAMPLER_INTERPOLATION = BUILDER
                .comment("Optimizes ChunkNoiseSampler#interpolateZ by skipping quarter-step cases (deltaZ=0.0) for 5-15% speedup")
                .define("chunkNoiseSamplerInterpolation", true);

        BUILDER.pop();

        BUILDER.push("general");

        USE_GUI_GRAPHICS = BUILDER
                .comment("Enables GuiGraphics-accelerated rendering paths when available")
                .define("useGuiGraphics", true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static void syncToSharedConfig() {
        NoisiumConfig.get().update(
                NOISE_CHUNK_GENERATOR.get(),
                GENERATION_SHAPE_CONFIG.get(),
                CHUNK_SECTION.get(),
                CHAINED_BLOCK_SOURCE.get(),
                CHUNK_NOISE_SAMPLER_INTERPOLATION.get(),
                USE_GUI_GRAPHICS.get()
        );
    }
}
