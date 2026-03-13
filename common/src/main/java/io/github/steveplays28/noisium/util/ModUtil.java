package io.github.steveplays28.noisium.util;

import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("unused")
public abstract class ModUtil {
	/**
	 * Checks if a mod is present during loading.
	 */
	public static boolean isModPresent(String id) {
		return FabricLoader.getInstance().isModLoaded(id);
	}
}
