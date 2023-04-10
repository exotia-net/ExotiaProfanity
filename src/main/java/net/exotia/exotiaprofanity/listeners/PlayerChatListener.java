package net.exotia.exotiaprofanity.listeners;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.exotiaprofanity.Permissions;
import net.exotia.exotiaprofanity.client.ProfanityClient;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class PlayerChatListener implements Listener {
    @Inject private ProfanityClient profanityClient;
    @Inject private Plugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        //if (player.hasPermission(Permissions.PROFANITY_BYPASS)) return;
        if (!event.isAsynchronous()) return;

        String message = event.getMessage().replace("%%", " ");
        message = String.join("%%", player.getUniqueId().toString(), message);
        this.profanityClient.send("/censor " + message);
        event.setCancelled(true);
    }
}
