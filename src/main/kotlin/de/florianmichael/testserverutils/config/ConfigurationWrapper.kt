package de.florianmichael.testserverutils.config

import de.florianmichael.testserverutils.TestServerUtils
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration

object ConfigurationWrapper {

    private val configuration: FileConfiguration

    // General settings
    var prefix: String
    var notAPlayer: String
    var noPermission: String
    var playerNotOnline: String

    init {
        TestServerUtils.get().apply {
            configuration = config
            saveDefaultConfig()

            prefix = unwrapString("messages.prefix")
            notAPlayer = unwrapString("messages.not-a-player")
            noPermission = unwrapString("messages.no-permission")
            playerNotOnline = unwrapString("messages.player-not-online")
        }
    }

    fun unwrapString(key: String) = ChatColor.translateAlternateColorCodes('&', configuration.getString(key)!!)
}
