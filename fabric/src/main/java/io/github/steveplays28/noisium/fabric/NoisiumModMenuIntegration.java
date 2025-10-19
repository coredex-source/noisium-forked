package io.github.steveplays28.noisium.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.steveplays28.noisium.fabric.config.NoisiumConfigScreen;

public class NoisiumModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return NoisiumConfigScreen::create;
    }
}
