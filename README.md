![Noisium icon](docs/assets/icon/icon_128x128.png)

# Noisium

Optimises worldgen performance for a better gameplay experience.

Noisium changes some world generation functions that other mods don't touch, to fill in the gaps left by other performance optimisation
mods.
Most notably, `NoiseChunkGenerator#populateNoise` is optimised to speed up block state placement when generating new chunks.  
Setting the block state via abstractions/built-in functions is bypassed. Instead, the block states are set directly in the palette storage,
thus bypassing calculations Minecraft does that are normally useful when block states are set, but when generating the world only slow it
down.  
There are also 3 other optimisations, that increase biome population speed, block state sampling speed and chunk unlocking speed (Minecraft
1.21 and up) during world generation.

Noisium has full 1:1 parity with vanilla Minecraft world generation (world generation without Noisium).

## Benchmarks

### Minecraft 1.20.4 and lower

System         | Vanilla 1.20.1  | Noisium `v2.3.0` | Difference
---------------|-----------------|------------------|-----------------------------
Intel i7-9750H | -               | -                |-30% (measured via profiler)

### Minecraft 1.20.5 and up

In Minecraft 1.20.5 and up, Noisium has less effectiveness than in previous versions of Minecraft.  
In the best case measured so far, the improvements can be up to a 5% speedup when generating new chunks in vanilla Minecraft.  

System                 | Vanilla 1.20.1 | Noisium `v2.3.0` | Difference
-----------------------|----------------|------------------|----------------
Intel Xeon Silver 4510 | 07:48          | 07:24            | -5.1%
Intel Xeon Gold 5218R  | 05:31          | 05:38            | None measured*
Intel i5-7500          | 05:25          | 05:28            | None measured*
AMD Ryzen 5800X3D      | 03:00          | 03:00            | None measured*
AMD Ryzen 5600X        | 03:23          | 03:23            | None measured*
AMD Ryzen 5500U        | 04:12          | 04:05            | None measured*

*: The measured difference was less than 5%.  
Results may vary based on hardware (e.x. faster hardware may benefit less).

## Dependencies

### Required

None.

## Compatibility info

### Compatible mods

Noisium should be compatible with most, if not all, of the popular optimisation mods currently on Modrinth/CurseForge for
Noisium's supported Minecraft versions, since Noisium aims to fill in the gaps in performance optimisation left by other mods.
This includes (but is not limited to) C2ME, Lithium, Nvidium, and Sodium.

- C2ME: every world generation thread runs faster. The biome population multithreading is also done in a much better/more performant way in
  C2ME, so it's been removed from Noisium since `v1.0.2`. It's suggested to run C2ME alongside Noisium for even better world generation
  performance.
- Distant Horizons: Noisium speeds up LOD world generation threads, since LOD generation depends on Minecraft's world generation speed.
- ReTerraForged: RTF has built-in compatibility with Noisium, to fully utilize the optimisations during RTF world generation.

### Incompatibilities

See the [issue tracker](https://github.com/Steveplays28/noisium/issues?q=is%3Aissue+is%3Aopen+sort%3Aupdated-desc+label%3Acompatibility) for
a list of incompatibilities.

## Download

[![GitHub](https://github.com/intergrav/devins-badges/raw/2dc967fc44dc73850eee42c133a55c8ffc5e30cb/assets/cozy/available/github_vector.svg)](https://github.com/Steveplays28/noisium)
[![Modrinth](https://github.com/intergrav/devins-badges/raw/2dc967fc44dc73850eee42c133a55c8ffc5e30cb/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/noisium)
[![CurseForge](https://github.com/intergrav/devins-badges/raw/2dc967fc44dc73850eee42c133a55c8ffc5e30cb/assets/cozy/available/curseforge_vector.svg)](https://www.curseforge.com/minecraft/mc-mods/noisium)

![Fabric](https://github.com/intergrav/devins-badges/raw/2dc967fc44dc73850eee42c133a55c8ffc5e30cb/assets/compact/supported/fabric_vector.svg)
![Quilt](https://github.com/intergrav/devins-badges/raw/2dc967fc44dc73850eee42c133a55c8ffc5e30cb/assets/compact/supported/quilt_vector.svg)
![Forge](https://github.com/intergrav/devins-badges/raw/2dc967fc44dc73850eee42c133a55c8ffc5e30cb/assets/compact/supported/forge_vector.svg)
![NeoForge](docs/assets/badges/compact/supported/neoforge_vector.svg)

See the version info in the filename for the supported Minecraft versions.  
Made for the Fabric, Quilt, Forge, and NeoForge modloaders.  
Server-side.

## FAQ

- Q: Will you be backporting this mod to lower Minecraft versions?  
  A: No.

- Q: Does this mod work in multiplayer?  
  A: Yes, but it'll only improve performance on the server.

- Q: Does only the server need this mod or does the client need it too?  
  A: Only the server needs this mod (but it works on the client too if you're going to host LAN or play singleplayer).

## Attribution

- Thank you to [Builderb0y](https://modrinth.com/user/Builderb0y) for giving great starting points and helping with issues
- Thank you to [ishland](https://github.com/ishland) for helping with C2ME compatibility and benchmarking performance
- Thank you to [Uniter](https://github.com/Uniter343) and [raccoonman2](https://github.com/racoonman2) for benchmarking performance

## License

This project is licensed under LGPLv3, see [LICENSE](https://github.com/Steveplays28/noisium/blob/main/LICENSE).
