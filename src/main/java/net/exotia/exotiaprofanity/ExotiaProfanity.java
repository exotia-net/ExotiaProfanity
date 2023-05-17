package net.exotia.exotiaprofanity;

import net.exotia.exotiaprofanity.client.ProfanityClient;
import net.exotia.exotiaprofanity.configuration.Configuration;
import net.exotia.exotiaprofanity.listeners.PlayerChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExotiaProfanity extends JavaPlugin {
    private ProfanityClient profanityClient;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Configuration configuration = new Configuration(this);

        this.profanityClient = new ProfanityClient(configuration, this);
        this.profanityClient.connect();

        this.getServer().getPluginManager().registerEvents(new PlayerChatListener(this.profanityClient), this);
    }

    @Override
    public void onDisable() {
        this.profanityClient.close();
    }
}
