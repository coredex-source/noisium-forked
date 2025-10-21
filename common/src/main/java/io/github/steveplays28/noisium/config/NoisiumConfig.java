package io.github.steveplays28.noisium.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Simple JSON-backed config used by both Fabric and NeoForge entrypoints.
 * This keeps configuration independent from platform config systems and provides
 * per-mixin enable flags and a few additional options requested by the user.
 */
public class NoisiumConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Mixins / optimisations
    public boolean noiseChunkGenerator = true;
    public boolean generationShapeConfig = true;
    public boolean chunkSection = true;
    public boolean chainedBlockSource = true;
    public boolean chunkNoiseSamplerInterpolation = true;

    // Additional toggles
    /** Allows using GuiGraphics-accelerated paths when available */
    public boolean useGuiGraphics = true;

    // Backing instance loaded at startup
    private static NoisiumConfig INSTANCE = new NoisiumConfig();

    static {
        // Load eagerly so mixin plugin can use values even if called before mod init
        loadFromResourceInternal();
    }

    public static NoisiumConfig get() {
        return INSTANCE;
    }

    /** Loads default config from resources if present, otherwise keeps defaults. */
    public static void loadFromResource(Logger logger) {
        try (InputStream in = NoisiumConfig.class.getResourceAsStream("/noisium-config.json")) {
            if (in == null) {
                logger.debug("Noisium config resource not found, using defaults.");
                return;
            }
            try (InputStreamReader r = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                INSTANCE = GSON.fromJson(r, NoisiumConfig.class);
                logger.info("Loaded Noisium config from resource");
            }
        } catch (Exception e) {
            logger.warn("Failed to load Noisium config, using defaults", e);
        }
    }

    private static void loadFromResourceInternal() {
        try (InputStream in = NoisiumConfig.class.getResourceAsStream("/noisium-config.json")) {
            if (in != null) {
                try (InputStreamReader r = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                    INSTANCE = GSON.fromJson(r, NoisiumConfig.class);
                }
            }
        } catch (Exception e) {
            // Silently use defaults if loading fails during static init
        }
    }

    /** Updates this config instance from platform config values */
    public void update(boolean noiseChunkGenerator, boolean generationShapeConfig, 
                      boolean chunkSection, boolean chainedBlockSource, 
                      boolean chunkNoiseSamplerInterpolation, boolean useGuiGraphics) {
        this.noiseChunkGenerator = noiseChunkGenerator;
        this.generationShapeConfig = generationShapeConfig;
        this.chunkSection = chunkSection;
        this.chainedBlockSource = chainedBlockSource;
        this.chunkNoiseSamplerInterpolation = chunkNoiseSamplerInterpolation;
        this.useGuiGraphics = useGuiGraphics;
    }
}
