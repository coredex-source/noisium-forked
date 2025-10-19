package io.github.steveplays28.noisium.fabric;

import io.github.steveplays28.noisium.Noisium;
import io.github.steveplays28.noisium.fabric.config.NoisiumFabricConfig;
import net.fabricmc.api.ModInitializer;

public class NoisiumFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Noisium.initialize();
        NoisiumFabricConfig.load();
    }
}
