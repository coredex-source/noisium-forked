package io.github.steveplays28.noisium.mixin;

import net.minecraft.core.QuartPos;
import net.minecraft.world.level.levelgen.NoiseSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Caches cellWidth and cellHeight.
 */
@Mixin(NoiseSettings.class)
public abstract class NoiseSettingsMixin {
	@Unique private int noisium$cellWidth;
	@Unique private int noisium$cellHeight;

	@Inject(method = "<init>", at = @At(value = "TAIL"))
	private void noisium$cacheCellWidthAndHeight(int minimumY, int height, int horizontalSize, int verticalSize, CallbackInfo ci) {
		noisium$cellWidth = QuartPos.toBlock(horizontalSize);
		noisium$cellHeight = QuartPos.toBlock(verticalSize);
	}

	@Inject(method = "getCellWidth", at = @At(value = "HEAD"), cancellable = true)
	private void noisium$getCellWidthFromCache(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(noisium$cellWidth);
	}

	@Inject(method = "getCellHeight", at = @At(value = "HEAD"), cancellable = true)
	private void noisium$getCellHeightFromCache(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(noisium$cellHeight);
	}
}
