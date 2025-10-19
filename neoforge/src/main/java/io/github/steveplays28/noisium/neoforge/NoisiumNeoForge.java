package io.github.steveplays28.noisium.neoforge;

import io.github.steveplays28.noisium.Noisium;
import io.github.steveplays28.noisium.neoforge.config.NoisiumNeoForgeConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

@Mod(Noisium.MOD_ID)
public class NoisiumNeoForge {
    public NoisiumNeoForge(IEventBus modBus, ModContainer container) {
        Noisium.initialize();
        
        // Register config as CLIENT type - NeoForge will automatically provide a config GUI
        container.registerConfig(ModConfig.Type.CLIENT, NoisiumNeoForgeConfig.SPEC, "noisium-client.toml");
        
        // Register config screen factory using reflection to avoid compile-time dependency on client classes
        try {
            // Load the IConfigScreenFactory class
            Class<?> screenFactoryClass = Class.forName("net.neoforged.neoforge.client.gui.IConfigScreenFactory");
            
            // Create a proxy that implements IConfigScreenFactory
            Object screenFactory = java.lang.reflect.Proxy.newProxyInstance(
                screenFactoryClass.getClassLoader(),
                new Class<?>[] { screenFactoryClass },
                (proxy, method, args) -> {
                    // args[0] = minecraft, args[1] = parent screen
                    try {
                        Class<?> configScreenClass = Class.forName("net.neoforged.neoforge.client.gui.ConfigurationScreen");
                        
                        // Get the Screen class from the parent argument instead of using Class.forName
                        // This avoids issues with remapped client classes
                        Class<?> screenClass = args[1].getClass().getSuperclass();
                        while (screenClass != null && !screenClass.getSimpleName().equals("Screen")) {
                            screenClass = screenClass.getSuperclass();
                        }
                        
                        if (screenClass == null) {
                            Noisium.LOGGER.error("Could not find Screen class!");
                            return args[1];
                        }
                        
                        // Create ConfigurationScreen(ModContainer container, Screen parent)
                        // ConfigurationScreen will automatically find all configs registered to this container
                        java.lang.reflect.Constructor<?> constructor = configScreenClass.getConstructor(
                            Class.forName("net.neoforged.fml.ModContainer"),
                            screenClass
                        );
                        return constructor.newInstance(container, args[1]);
                    } catch (Exception e) {
                        Noisium.LOGGER.error("Failed to create config screen: " + e.getMessage());
                        return args[1]; // Return parent screen as fallback
                    }
                }
            );
            
            // Create a Supplier that returns the factory
            java.util.function.Supplier<?> factorySupplier = () -> screenFactory;
            
            // Find and invoke registerExtensionPoint method
            for (java.lang.reflect.Method method : container.getClass().getMethods()) {
                if (method.getName().equals("registerExtensionPoint") && method.getParameterCount() == 2) {
                    method.invoke(container, screenFactoryClass, factorySupplier);
                    Noisium.LOGGER.info("Successfully registered NeoForge config screen");
                    break;
                }
            }
        } catch (Exception e) {
            Noisium.LOGGER.error("Failed to register config screen factory: " + e.getMessage());
        }
        
        // Register config event listeners
        modBus.addListener(this::onConfigLoad);
        modBus.addListener(this::onConfigReload);
    }

    private void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == NoisiumNeoForgeConfig.SPEC) {
            NoisiumNeoForgeConfig.syncToSharedConfig();
            Noisium.LOGGER.info("Loaded NeoForge config");
        }
    }

    private void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == NoisiumNeoForgeConfig.SPEC) {
            NoisiumNeoForgeConfig.syncToSharedConfig();
            Noisium.LOGGER.info("Reloaded NeoForge config");
        }
    }
}
