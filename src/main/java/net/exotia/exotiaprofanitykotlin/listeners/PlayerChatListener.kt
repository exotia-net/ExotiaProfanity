package net.exotia.exotiaprofanitykotlin.listeners

import net.exotia.exotiaprofanitykotlin.configuration.Configuration
import okhttp3.WebSocket
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.logging.Logger

class PlayerChatListener(
  private var ws: WebSocket,
  private val logger: Logger,
  private val configuration: Configuration,
) : Listener {
  private val profanityBypass = "exotia.profanity.bypass"

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  fun onChat(event: AsyncPlayerChatEvent) {
    val player: Player = event.player
    if (player.hasPermission(profanityBypass)) return
    if (!event.isAsynchronous) return
    val wsResponse = this.ws.send("/censor ${this.format(event.message, player)}")
    if (wsResponse == true) {
      event.isCancelled = true
    }
  }

  private fun format(playerMessage: String, player: Player): String {
    val delimiter: String = this.configuration.delimiter
    return player.uniqueId.toString() + delimiter + playerMessage.replace(delimiter, " ")
  }
}
