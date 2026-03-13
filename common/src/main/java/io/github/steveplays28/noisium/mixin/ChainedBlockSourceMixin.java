package io.github.steveplays28.noisium.mixin;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.material.MaterialRuleList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MaterialRuleList.class)
public abstract class ChainedBlockSourceMixin {
	@Shadow
	@Final
	private NoiseChunk.BlockStateFiller[] materialRuleList;

	/**
	 * @author Steveplays28
	 * @reason Micro-optimisation
	 */
	@Overwrite
	@Nullable
	@SuppressWarnings("ForLoopReplaceableByForEach")
	public BlockState calculate(DensityFunction.FunctionContext pos) {
		for (int i = 0; i < this.materialRuleList.length; i++) {
			BlockState blockState = this.materialRuleList[i].calculate(pos);
			if (blockState == null) {
				continue;
			}

			return blockState;
		}

		return null;
	}
}