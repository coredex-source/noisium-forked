package io.github.steveplays28.noisium.util;

import net.fabricmc.loader.api.FabricLoader;
import net.msrandom.multiplatform.annotations.Actual;

@Actual
public class ModUtilActual {
	/**
	 * Checks if a mod is present during loading.
	 */
	@Actual
	public static boolean isModPresent(String id) {
		return FabricLoader.getInstance().isModLoaded(id);
	}
}
