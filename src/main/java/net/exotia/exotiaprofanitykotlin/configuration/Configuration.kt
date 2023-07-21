package net.exotia.exotiaprofanitykotlin.configuration

import org.bukkit.plugin.Plugin

class Configuration(plugin: Plugin) {
  val apiUrl: String
  val delimiter: String
  val autoReconnect: Boolean

  init {
    this.apiUrl = plugin.config.getString("apiUrl") ?: ""
    this.delimiter = plugin.config.getString("delimiter") ?: ""
    this.autoReconnect = plugin.config.getBoolean("autoReconnect")
  }
}
