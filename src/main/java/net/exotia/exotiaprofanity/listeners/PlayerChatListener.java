package net.exotia.exotiaprofanity.listeners;

import net.exotia.exotiaprofanity.Permissions;
import net.exotia.exotiaprofanity.client.ProfanityClient;
import net.exotia.exotiaprofanity.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final ProfanityClient profanityClient;
    private final Configuration configuration;

    public PlayerChatListener(ProfanityClient profanityClient) {
        this.profanityClient = profanityClient;
        this.configuration = profanityClient.getConfiguration();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(Permissions.PROFANITY_BYPASS)) return;
        if (!event.isAsynchronous()) return;
        if (!this.profanityClient.isOpen()) this.profanityClient.connect();
        this.profanityClient.send("/censor " + this.format(event.getMessage(), player));
        event.setCancelled(true);
    }

    private String format(String playerMessage, Player player) {
        String delimiter = this.configuration.getDelimiter();
        return String.join(delimiter, player.getUniqueId().toString(), playerMessage.replace(delimiter, " "));
    }
}
