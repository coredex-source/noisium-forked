package io.github.steveplays28.noisium.mixin;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.NoiseChunk.BlockStateFiller;
import net.minecraft.world.level.levelgen.material.MaterialRuleList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(MaterialRuleList.class)
public abstract class MaterialRuleListMixin {
	@Shadow @Final private List<BlockStateFiller> materialRuleList;

	/**
	 * @author Steveplays28
	 * @reason Micro-optimisation
	 */
	@Overwrite
	@Nullable
	@SuppressWarnings("ForLoopReplaceableByForEach")
	public BlockState calculate(FunctionContext functionContext) {
		for (int i = 0; i < this.materialRuleList.size(); i++) {
			BlockState blockState = this.materialRuleList.get(i).calculate(functionContext);
			if (blockState == null) {
				continue;
			}

			return blockState;
		}

		return null;
	}
}
