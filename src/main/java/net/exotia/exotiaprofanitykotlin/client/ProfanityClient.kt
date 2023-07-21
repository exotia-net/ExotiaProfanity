package net.exotia.exotiaprofanitykotlin.client

import net.exotia.exotiaprofanitykotlin.configuration.Configuration
import net.exotia.exotiaprofanitykotlin.ExotiaProfanityKotlin
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.OkHttpClient
import okhttp3.Request
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import java.util.logging.Logger
import java.util.UUID
import kotlin.reflect.KFunction


class ProfanityClient(
  private val plugin: Plugin,
  private val logger: Logger,
  private val configuration: Configuration,
  private var tryToReconnect: () -> Unit
) : WebSocketListener() {

  override fun onOpen(webSocket: WebSocket, response: Response) {
    this.logger.info("New connection has been opened!")
  }

  override fun onMessage(webSocket: WebSocket, text: String) {
    val parts: List<String> = text.split(this.configuration.delimiter)
    if (parts.size < 2) return

    this.plugin.server.scheduler.runTask(this.plugin, Runnable {
      val player = Bukkit.getPlayer(UUID.fromString(parts[0]))
      player?.chat(parts[1])
    })
  }

  override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
    this.logger.severe("Closed with exit code $code additional info: $reason");
    webSocket.close(code, reason)
    this.tryToReconnect()
  }

  override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    this.logger.severe("WebSocket failure!")
    if (t.message != null) this.logger.info(t.message)
    this.tryToReconnect()
  }
}
