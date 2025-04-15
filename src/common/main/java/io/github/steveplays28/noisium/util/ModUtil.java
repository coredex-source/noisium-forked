package io.github.steveplays28.noisium.util;

import dev.architectury.injectables.annotations.ExpectPlatform;

@SuppressWarnings("unused")
public abstract class ModUtil {
	/**
	 * Checks if a mod is present during loading.
	 */
	@ExpectPlatform
	public static boolean isModPresent(String id) {
		throw new AssertionError("Platform implementation expected.");
	}
}
