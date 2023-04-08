package net.exotia.exotiaprofanity.configuration;

import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

@Getter
public class Configuration {
    @Inject private Plugin plugin;

    private String apiUrl;

    @PostConstruct()
    public void onConstruct() {
        this.apiUrl = this.plugin.getConfig().getString("apiUrl");
    }
}
