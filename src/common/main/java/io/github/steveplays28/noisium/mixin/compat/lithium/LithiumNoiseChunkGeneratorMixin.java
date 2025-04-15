package io.github.steveplays28.noisium.mixin.compat.lithium;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(NoiseChunkGenerator.class)
public abstract class LithiumNoiseChunkGeneratorMixin extends ChunkGenerator {
	public LithiumNoiseChunkGeneratorMixin(BiomeSource biomeSource) {
		super(biomeSource);
	}

	@Redirect(method = "populateNoise(Lnet/minecraft/world/gen/chunk/Blender;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/noise/NoiseConfig;Lnet/minecraft/world/chunk/Chunk;II)Lnet/minecraft/world/chunk/Chunk;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/ChunkSection;setBlockState(IIILnet/minecraft/block/BlockState;Z)Lnet/minecraft/block/BlockState;"))
	private BlockState noisium$populateNoiseWrapSetBlockStateOperation(@NotNull ChunkSection chunkSection, int chunkSectionBlockPosX, int chunkSectionBlockPosY, int chunkSectionBlockPosZ, @NotNull BlockState blockState, boolean lock) {
		// Set the blockstate in the palette storage directly to improve performance
		var blockStateId = chunkSection.blockStateContainer.data.palette.index(blockState);
		chunkSection.blockStateContainer.data.storage().set(
				chunkSection.blockStateContainer.paletteProvider.computeIndex(chunkSectionBlockPosX, chunkSectionBlockPosY,
						chunkSectionBlockPosZ
				), blockStateId);

		return blockState;
	}

	@Inject(method = "method_38328", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/ChunkSection;unlock()V"))
	private static void noisium$calculateCounts(Set<ChunkSection> chunkSections, @NotNull Chunk chunk, @Nullable Throwable throwable, @NotNull CallbackInfo ci, @Local @NotNull ChunkSection chunkSection) {
		// Calculate the block state counts on every chunk section to add Lithium compatibility
		chunkSection.calculateCounts();
	}
}
