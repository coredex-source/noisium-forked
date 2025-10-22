package io.github.steveplays28.noisium.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.steveplays28.noisium.Noisium;
import io.github.steveplays28.noisium.config.NoisiumConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Fabric-specific config handler that persists to JSON file in config folder
 * and syncs with the shared NoisiumConfig instance.
 */
public class NoisiumFabricConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("noisium.json");

    public static class ConfigData {
        public boolean noiseChunkGenerator = true;
        public boolean generationShapeConfig = true;
        public boolean chunkSection = true;
        public boolean chainedBlockSource = true;
        public boolean useGuiGraphics = true;
    }

    private static ConfigData config = new ConfigData();

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH, StandardCharsets.UTF_8);
                config = GSON.fromJson(json, ConfigData.class);
                Noisium.LOGGER.info("Loaded Fabric config from {}", CONFIG_PATH);
            } catch (Exception e) {
                Noisium.LOGGER.error("Failed to load Fabric config, using defaults", e);
            }
        } else {
            save();
        }
        syncToSharedConfig();
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String json = GSON.toJson(config);
            Files.writeString(CONFIG_PATH, json, StandardCharsets.UTF_8);
            Noisium.LOGGER.debug("Saved Fabric config to {}", CONFIG_PATH);
        } catch (IOException e) {
            Noisium.LOGGER.error("Failed to save Fabric config", e);
        }
        syncToSharedConfig();
    }

    private static void syncToSharedConfig() {
        NoisiumConfig.get().update(
                config.noiseChunkGenerator,
                config.generationShapeConfig,
                config.chunkSection,
                config.chainedBlockSource,
                config.useGuiGraphics
        );
    }

    public static ConfigData getConfig() {
        return config;
    }

    public static void setConfig(ConfigData newConfig) {
        config = newConfig;
        save();
    }
}
