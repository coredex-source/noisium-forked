package io.github.steveplays28.noisium.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LevelChunkSection.class)
public class LevelChunkSectionMixin {
	@Shadow private PalettedContainerRO<Holder<Biome>> biomes;

	@Unique private static final int noisium$SLICE_SIZE = 4;

	/**
	 * @author Steveplays28
	 * @reason Micro-optimise the order of the axes that are iterated.
	 */
	@Overwrite
	public void fillBiomesFromNoise(BiomeResolver biomeResolver, Sampler sampler, int x, int y, int z) {
		PalettedContainer<Holder<Biome>> palettedContainer = this.biomes.recreate();

		for (int posY = 0; posY < noisium$SLICE_SIZE; ++posY) {
			for (int posZ = 0; posZ < noisium$SLICE_SIZE; ++posZ) {
				for (int posX = 0; posX < noisium$SLICE_SIZE; ++posX) {
					palettedContainer.getAndSetUnchecked(posX, posY, posZ, biomeResolver.getNoiseBiome(x + posX, y + posY, z + posZ, sampler));
				}
			}
		}

		this.biomes = palettedContainer;
	}
}
