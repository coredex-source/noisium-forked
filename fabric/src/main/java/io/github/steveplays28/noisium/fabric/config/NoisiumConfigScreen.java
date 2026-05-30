package io.github.steveplays28.noisium.fabric.config;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Native Minecraft config screen implementation with no external UI dependencies.
 */
public class NoisiumConfigScreen extends Screen {
    private final Screen parent;
    private NoisiumFabricConfig.ConfigData workingConfig;

    public NoisiumConfigScreen(Screen parent) {
        super(Component.translatable("config.noisium.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.workingConfig = copy(NoisiumFabricConfig.getConfig());

        int centerX = this.width / 2;
        int y = this.height / 4;
        int rowHeight = 24;

        StringWidget titleWidget = this.addRenderableWidget(new StringWidget(this.title, this.font));
        titleWidget.setX(centerX - this.font.width(this.title) / 2);
        titleWidget.setY(20);

        this.addRenderableWidget(toggleButton(
                centerX - 155,
                y,
                Component.translatable("config.noisium.noiseChunkGenerator"),
                () -> this.workingConfig.noiseChunkGenerator,
                value -> this.workingConfig.noiseChunkGenerator = value
        ));

        this.addRenderableWidget(toggleButton(
                centerX - 155,
                y + rowHeight,
                Component.translatable("config.noisium.generationShapeConfig"),
                () -> this.workingConfig.generationShapeConfig,
                value -> this.workingConfig.generationShapeConfig = value
        ));

        this.addRenderableWidget(toggleButton(
                centerX - 155,
                y + rowHeight * 2,
                Component.translatable("config.noisium.chunkSection"),
                () -> this.workingConfig.chunkSection,
                value -> this.workingConfig.chunkSection = value
        ));

        this.addRenderableWidget(toggleButton(
                centerX - 155,
                y + rowHeight * 3,
                Component.translatable("config.noisium.chainedBlockSource"),
                () -> this.workingConfig.chainedBlockSource,
                value -> this.workingConfig.chainedBlockSource = value
        ));

        this.addRenderableWidget(toggleButton(
                centerX - 155,
                y + rowHeight * 4,
                Component.translatable("config.noisium.useGuiGraphics"),
                () -> this.workingConfig.useGuiGraphics,
                value -> this.workingConfig.useGuiGraphics = value
        ));

        this.addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> {
                    NoisiumFabricConfig.setConfig(this.workingConfig);
                    this.onClose();
                })
                .bounds(centerX - 155, y + rowHeight * 6, 150, 20)
                .build());

        this.addRenderableWidget(Button.builder(Component.translatable("gui.cancel"), button -> this.onClose())
                .bounds(centerX + 5, y + rowHeight * 6, 150, 20)
                .build());
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.gui.setScreen(this.parent);
        }
    }

    private Button toggleButton(int x, int y, Component label, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Button.builder(toggleText(label, getter.get()), button -> {
                    boolean next = !getter.get();
                    setter.accept(next);
                    button.setMessage(toggleText(label, next));
                })
                .bounds(x, y, 310, 20)
                .build();
    }

    private static MutableComponent toggleText(Component label, boolean enabled) {
        return Component.empty()
                .append(label)
                .append(": ")
                .append(enabled ? Component.translatable("options.on") : Component.translatable("options.off"));
    }

    private static NoisiumFabricConfig.ConfigData copy(NoisiumFabricConfig.ConfigData source) {
        NoisiumFabricConfig.ConfigData copy = new NoisiumFabricConfig.ConfigData();
        copy.noiseChunkGenerator = source.noiseChunkGenerator;
        copy.generationShapeConfig = source.generationShapeConfig;
        copy.chunkSection = source.chunkSection;
        copy.chainedBlockSource = source.chainedBlockSource;
        copy.useGuiGraphics = source.useGuiGraphics;
        return copy;
    }
}