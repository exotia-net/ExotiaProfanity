package net.exotia.exotiaprofanitykotlin

import net.exotia.exotiaprofanitykotlin.client.ProfanityClient
import net.exotia.exotiaprofanitykotlin.configuration.Configuration
import net.exotia.exotiaprofanitykotlin.listeners.PlayerChatListener
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class ExotiaProfanityKotlin : JavaPlugin() {
  lateinit var configuration: Configuration
  lateinit var ws: WebSocket

  override fun onEnable() {
    this.saveDefaultConfig()
    this.configuration = Configuration(this)

    this.connect()

    server.pluginManager.registerEvents(PlayerChatListener(this.ws, logger, configuration), this)

    logger.info("Exotia Profanity loaded.")
  }

  override fun onDisable() {
    this.ws.cancel()
  }

  fun tryToReconnect() {
    if (!configuration.autoReconnect) return
    logger.info("Trying to reconnect...")
    this.server.scheduler.runTaskLater(this, Runnable {
      this.connect()
      server.pluginManager.registerEvents(PlayerChatListener(this.ws, logger, configuration), this)
      logger.info("Reconnecting completed!")
    }, 100L)
  }

  fun connect() {
    val wsClient: OkHttpClient = OkHttpClient.Builder()
      .retryOnConnectionFailure(true)
      .build()
    val request: Request = Request.Builder()
      .url(this.configuration.apiUrl)
      .build()
    val profanityClient = ProfanityClient(this, this.logger, this.configuration, ::tryToReconnect)

    this.ws = wsClient.newWebSocket(request, profanityClient)
  }
}
