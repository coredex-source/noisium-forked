package io.github.steveplays28.noisium.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LevelChunkSection.class)
public class ChunkSectionMixin {
	@Unique
	private static final int noisium$sliceSize = 4;

	@Shadow
	private PalettedContainerRO<Holder<Biome>> biomes;

	/**
	 * @author Steveplays28
	 * @reason Axis order micro-optimisation
	 */
	@Overwrite
	public void fillBiomesFromNoise(BiomeResolver biomeSupplier, Climate.Sampler sampler, int x, int y, int z) {
		PalettedContainer<Holder<Biome>> palettedContainer = this.biomes.recreate();

		for (int posY = 0; posY < noisium$sliceSize; ++posY) {
			for (int posZ = 0; posZ < noisium$sliceSize; ++posZ) {
				for (int posX = 0; posX < noisium$sliceSize; ++posX) {
					palettedContainer.getAndSetUnchecked(posX, posY, posZ, biomeSupplier.getNoiseBiome(x + posX, y + posY, z + posZ, sampler));
				}
			}
		}

		this.biomes = palettedContainer;
	}
}
