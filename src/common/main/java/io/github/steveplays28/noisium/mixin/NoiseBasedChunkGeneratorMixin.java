package io.github.steveplays28.noisium.mixin;

import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin extends ChunkGenerator {
	public NoiseBasedChunkGeneratorMixin(BiomeSource biomeSource) {
		super(biomeSource);
	}

	@Redirect(
			method = "doFill(Lnet/minecraft/world/level/levelgen/blending/Blender;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/chunk/ChunkAccess;II)Lnet/minecraft/world/level/chunk/ChunkAccess;",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/level/chunk/LevelChunkSection;setBlockState(IIILnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/level/block/state/BlockState;"))
	private BlockState noisium$optimiseSetBlockState(@NotNull LevelChunkSection chunkSection, int chunkSectionBlockPosX, int chunkSectionBlockPosY, int chunkSectionBlockPosZ,
			@NotNull BlockState blockState, boolean lock) {
		// Update the non empty block count to avoid issues with MC's lighting engine and other systems not recognising the direct palette storage set
		// See LevelChunkSection#setBlockState
		chunkSection.nonEmptyBlockCount += 1;

		if (!blockState.getFluidState().isEmpty()) {
			chunkSection.tickingFluidCount += 1;
		}

		if (blockState.isRandomlyTicking()) {
			chunkSection.tickingBlockCount += 1;
		}

		// Set the blockstate in the palette storage directly to improve performance
		var blockStateId = chunkSection.states.data.palette.idFor(blockState);
		chunkSection.states.data.storage().set(chunkSection.states.strategy.getIndex(chunkSectionBlockPosX, chunkSectionBlockPosY, chunkSectionBlockPosZ), blockStateId);

		return blockState;
	}
}
