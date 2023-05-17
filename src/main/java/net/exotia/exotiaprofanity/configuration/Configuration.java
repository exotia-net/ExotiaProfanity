package net.exotia.exotiaprofanity.configuration;

import org.bukkit.plugin.Plugin;
import java.net.URI;
import java.net.URISyntaxException;

public class Configuration {
    private final String apiUrl;
    private final String delimiter;
    private final boolean autoReconnect;

    public Configuration(Plugin plugin) {
        this.apiUrl = plugin.getConfig().getString("apiUrl");
        this.delimiter = plugin.getConfig().getString("delimiter");
        this.autoReconnect = plugin.getConfig().getBoolean("autoReconnect");
    }
    public URI getApiUrl() {
        try {
            return new URI(this.apiUrl);
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
    public String getDelimiter() {
        return this.delimiter;
    }
    public boolean isAutoReconnect() {
        return this.autoReconnect;
    }
}
