package net.exotia.exotiaprofanity;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import net.exotia.exotiaprofanity.configuration.Configuration;
import net.exotia.exotiaprofanity.listeners.PlayerChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExotiaProfanity extends JavaPlugin {
    private final Injector injector = OkaeriInjector.create();
    private Configuration configuration;

    @Override
    public void onEnable() {
        this.injector.registerInjectable(this.injector);
        this.injector.registerInjectable(this);
        this.setupConfiguration();

        this.getServer().getPluginManager().registerEvents(this.injector.createInstance(PlayerChatListener.class), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setupConfiguration() {
        this.saveDefaultConfig();
        this.configuration = this.injector.createInstance(Configuration.class);
        this.injector.registerInjectable(this.configuration);
    }
}
