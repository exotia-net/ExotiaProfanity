package net.exotia.exotiaprofanity.configuration;

import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import org.bukkit.plugin.Plugin;

import java.net.URI;
import java.net.URISyntaxException;

public class Configuration {
    @Inject private Plugin plugin;

    private String apiUrl;
    private String delimiter;

    @PostConstruct()
    public void onConstruct() {
        this.apiUrl = this.plugin.getConfig().getString("apiUrl");
        this.delimiter = this.plugin.getConfig().getString("delimiter");
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
}
