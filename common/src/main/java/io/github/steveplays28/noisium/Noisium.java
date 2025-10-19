package io.github.steveplays28.noisium;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.steveplays28.noisium.config.NoisiumConfig;

public class Noisium {
	public static final String MOD_ID = "noisium";
	public static final String MOD_NAME = "Noisium";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void initialize() {
		LOGGER.info("Loading {}.", MOD_NAME);
		// Load bundled config (shared between Fabric and NeoForge)
		NoisiumConfig.loadFromResource(LOGGER);
		LOGGER.info("Noisium config: noiseChunkGenerator={}, generationShapeConfig={}, chunkSection={}, chainedBlockSource={}, useGuiGraphics={}",
				NoisiumConfig.get().noiseChunkGenerator,
				NoisiumConfig.get().generationShapeConfig,
				NoisiumConfig.get().chunkSection,
				NoisiumConfig.get().chainedBlockSource,
				NoisiumConfig.get().useGuiGraphics
		);
	}
}
