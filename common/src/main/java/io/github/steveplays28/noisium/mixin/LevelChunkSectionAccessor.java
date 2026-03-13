package io.github.steveplays28.noisium.mixin;

import net.minecraft.world.level.chunk.LevelChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelChunkSection.class)
public interface LevelChunkSectionAccessor {
	@Accessor("nonEmptyBlockCount")
	short noisium$getNonEmptyBlockCount();

	@Accessor("nonEmptyBlockCount")
	void noisium$setNonEmptyBlockCount(short nonEmptyBlockCount);

	@Accessor("tickingFluidCount")
	short noisium$getTickingFluidCount();

	@Accessor("tickingFluidCount")
	void noisium$setTickingFluidCount(short tickingFluidCount);

	@Accessor("tickingBlockCount")
	short noisium$getTickingBlockCount();

	@Accessor("tickingBlockCount")
	void noisium$setTickingBlockCount(short tickingBlockCount);
}