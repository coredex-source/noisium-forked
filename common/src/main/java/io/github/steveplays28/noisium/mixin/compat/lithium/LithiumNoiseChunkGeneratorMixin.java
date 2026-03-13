package io.github.steveplays28.noisium.mixin.compat.lithium;

import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class LithiumNoiseChunkGeneratorMixin extends ChunkGenerator {
	@Shadow
	protected abstract ChunkAccess doFill(Blender blender, StructureManager structureAccessor, RandomState noiseConfig, ChunkAccess chunk, int minimumCellY, int cellHeight);

	public LithiumNoiseChunkGeneratorMixin(BiomeSource biomeSource) {
		super(biomeSource);
	}

	@Redirect(method = "doFill(Lnet/minecraft/world/level/levelgen/blending/Blender;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/chunk/ChunkAccess;II)Lnet/minecraft/world/level/chunk/ChunkAccess;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunkSection;setBlockState(IIILnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/level/block/state/BlockState;"))
	private BlockState noisium$populateNoiseWrapSetBlockStateOperation(@NotNull LevelChunkSection chunkSection, int chunkSectionBlockPosX, int chunkSectionBlockPosY, int chunkSectionBlockPosZ, @NotNull BlockState blockState, boolean lock) {
		// Set the blockstate directly using swapUnsafe for MC 1.21.9+ (similar to biome optimization)
		chunkSection.getStates().getAndSetUnchecked(chunkSectionBlockPosX, chunkSectionBlockPosY, chunkSectionBlockPosZ, blockState);

		return blockState;
	}

	/**
	 * @author Steveplays28
	 * @reason Improved chunk locking and unlocking speed by getting the chunk section array directly from the chunk
	 * and by replacing {@code foreach} with {@code fori}.
	 */
	@Overwrite
	private @Nullable ChunkAccess method_38332(@NotNull ChunkAccess chunk, int generationShapeHeightFloorDiv, @NotNull NoiseSettings generationShapeConfig, int minimumY, @NotNull Blender blender, @NotNull StructureManager structureAccessor, @NotNull RandomState noiseConfig, int minimumYFloorDiv) {
		final int startingChunkSectionIndex = chunk.getSectionIndex(
				generationShapeHeightFloorDiv * generationShapeConfig.getCellHeight() - 1 + minimumY);
		final int minimumYChunkSectionIndex = chunk.getSectionIndex(minimumY);
		// Get the chunk section array from the chunk directly instead of constructing it manually
		@NotNull final var chunkSections = chunk.getSections();
		for (int chunkSectionIndex = startingChunkSectionIndex; chunkSectionIndex >= minimumYChunkSectionIndex; --chunkSectionIndex) {
			chunkSections[chunkSectionIndex].acquire();
		}

		@Nullable ChunkAccess chunkWithNoise;
		try {
			chunkWithNoise = this.doFill(
					blender, structureAccessor, noiseConfig, chunk, minimumYFloorDiv, generationShapeHeightFloorDiv);
		} finally {
			// Replace an enhanced for loop with a fori loop and reuse the chunk sections array used when locking the chunk sections
			// Also run calculateCounts() on every chunk section to add Lithium compatibility
			for (int chunkSectionIndex = startingChunkSectionIndex; chunkSectionIndex >= minimumYChunkSectionIndex; --chunkSectionIndex) {
				@NotNull final var chunkSection = chunkSections[chunkSectionIndex];
				chunkSection.recalcBlockCounts();
				chunkSection.release();
			}
		}

		return chunkWithNoise;
	}
}
