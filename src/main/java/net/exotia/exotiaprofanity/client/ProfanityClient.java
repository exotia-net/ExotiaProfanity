package net.exotia.exotiaprofanity.client;

import net.exotia.exotiaprofanity.configuration.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.util.UUID;
import java.util.logging.Logger;

public class ProfanityClient extends WebSocketClient {
    private final Plugin plugin;
    private final Logger logger;
    private final Configuration configuration;

    public ProfanityClient(Configuration configuration, Plugin plugin) {
        super(configuration.getApiUrl());
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        this.logger.info("New connection has been opened!");
    }
    @Override
    public void onMessage(String message) {
        String[] parts = message.split(this.configuration.getDelimiter());
        if (parts.length < 2) return;

        this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
            Player player = Bukkit.getPlayer(UUID.fromString(parts[0]));
            if (player == null) return;
            player.chat(parts[1]);
        });
    }
    @Override
    public void onClose(int code, String reason, boolean remote) {
        this.logger.severe("Closed with exit code " + code + " additional info: " + reason);
        if (this.configuration.isAutoReconnect()) {
            this.logger.info("Attempting to reconnect...");
            this.plugin.getServer().getScheduler().runTask(this.plugin, this::connect);
        }
    }
    @Override
    public void onError(Exception exception) {
        this.logger.severe("An error occurred:" + exception);
    }
}
