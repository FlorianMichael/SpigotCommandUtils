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

    init {
        TestServerUtils.get().apply {
            configuration = config
            saveDefaultConfig()

            prefix = unwrapString("prefix")
            notAPlayer = unwrapString("not-a-player")
            noPermission = unwrapString("no-permission")
        }
    }

    fun unwrapString(key: String) = ChatColor.translateAlternateColorCodes('&', configuration.getString(key)!!)
}
