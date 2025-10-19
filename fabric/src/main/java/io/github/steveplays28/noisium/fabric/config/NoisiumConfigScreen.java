package io.github.steveplays28.noisium.fabric.config;

import io.github.steveplays28.noisium.Noisium;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * Cloth Config-based config screen for Fabric.
 */
public class NoisiumConfigScreen {
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.noisium.title"));

        builder.setSavingRunnable(() -> {
            NoisiumFabricConfig.save();
            Noisium.LOGGER.info("Saved Noisium config");
        });

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory mixins = builder.getOrCreateCategory(Text.translatable("config.noisium.category.mixins"));

        NoisiumFabricConfig.ConfigData config = NoisiumFabricConfig.getConfig();

        mixins.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("config.noisium.noiseChunkGenerator"),
                        config.noiseChunkGenerator)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("config.noisium.noiseChunkGenerator.tooltip"))
                .setSaveConsumer(value -> config.noiseChunkGenerator = value)
                .build());

        mixins.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("config.noisium.generationShapeConfig"),
                        config.generationShapeConfig)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("config.noisium.generationShapeConfig.tooltip"))
                .setSaveConsumer(value -> config.generationShapeConfig = value)
                .build());

        mixins.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("config.noisium.chunkSection"),
                        config.chunkSection)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("config.noisium.chunkSection.tooltip"))
                .setSaveConsumer(value -> config.chunkSection = value)
                .build());

        mixins.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("config.noisium.chainedBlockSource"),
                        config.chainedBlockSource)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("config.noisium.chainedBlockSource.tooltip"))
                .setSaveConsumer(value -> config.chainedBlockSource = value)
                .build());

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.noisium.category.general"));

        general.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("config.noisium.useGuiGraphics"),
                        config.useGuiGraphics)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("config.noisium.useGuiGraphics.tooltip"))
                .setSaveConsumer(value -> config.useGuiGraphics = value)
                .build());

        return builder.build();
    }
}
