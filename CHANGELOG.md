# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## `v2.3.0` - 21/08/2024

### Added

- Minecraft 1.21.1 compatibility

### Changed

- Marked [Biospherical Expansion](https://www.curseforge.com/minecraft/mc-mods/biospherical-expansion) as incompatible
  - Fabric
  - NeoForge (Minecraft 1.21-1.21.1)

## `v2.2.2` - 10/08/2024

### Fixed

- Fixed an issue where C2ME would create more threads than it should

## `v2.2.1` - 04/07/2024

### Changed

- Moved an `@Inject` on `NoiseChunkGenerator#method_38332` to an `@Overwrite`
    - This allows other mods, such as [ishland](https://github.com/ishland)'s mods, to change the `Executor` of
      `NoiseChunkGenerator#populateNoise`'s `CompletableFuture`.

## `v2.2.0` - 21/06/2024

### Added

- Minecraft 1.21 compatibility

## `v2.1.0` - 26/05/2024

### Added

- Minecraft 1.20.5-1.20.6 compatibility

### Changed

- Removed Forge support
    - On 1.20.5 and up only

## `v2.0.3` - 29/04/2024

### Fixed

- Ad Astra compatibility
    - Ad Astra and other dimension mods no longer crash when trying to generate chunks in their dimensions
    - The code is also slightly faster and more concise, because the chunk sections array is now fetched directly from the chunk that's
      being generated, instead of recreating it before noise population, which may result in a very slight performance increase

## `v2.0.2` - 14/04/2024

### Added

- Lithium compatibility
    - You no longer have to sacrifice fish for performance

### Fixed

- NeoForge 1.20.2+ support
    - Removed the JiJ Mixin Extras, because NeoForge already includes it
- `ChunkSection#populateBiomes` axis order optimisation
    - The optimisation's axis order was incorrect (it was reversed)
    - Thank you to embeddedt for spotting this issue

## `v2.0.1` - 05/04/2024

### Fixed

- NeoForge support
    - Removed the dependency on (Neo)Forge, so the merged JAR can work for both NeoForge and Forge

## `v2.0.0` - 05/04/2024

### Added

- Forge support
- NeoForge support
- `GenerationShapeConfig` caching optimisation
    - `horizontalCellBlockCount` and `verticalCellBlockCount` are now cached, which skips a `BiomeCoords#toBlock` call every time these
      methods are invoked
- ReTerraForged, Nether Depths, and Lost Cities compatibility
    - The main optimisation (`NoiseChunkGenerator#populateNoise`) has been rewritten to improve compatibility, by using an `@Inject` and
      a `@Redirect` instead of an `@Overwrite`

## `v1.0.2` - 12/11/2023

### Added

- C2ME recommendation
    - Running C2ME alongside Noisium is now recommended to replace the biome population multithreading, since C2ME does it in a much
      better/more performant way

### Changed

- Removed the biome population multithreading
    - See the C2ME recommendation above

### Fixed

- Potential race condition due to a non-thread safe `BlockPos.Mutable` instance

## `v1.0.1` - 05/11/2023

### Fixed

- Occasional missing chunk sections

## `v1.0.0` - 29/10/2023

### Added

- 4 worldgen performance optimisations
    - `ChainedBlockSource#sample` (replace an enhanced for loop with a `fori` loop for faster blockstate sampling)
    - `Chunk#populateBiomes` (multithread biome population)
    - `NoiseChunkGenerator#populateNoise` (set blockstates directly in the palette storage)
    - `NoiseChunkGenerator#populateNoise` (replace an enhanced for loop with a `fori` loop for faster chunk unlocking)
