package de.florianmichael.testserverutils.util.extension

import de.florianmichael.testserverutils.config.ConfigurationWrapper
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CommandSender.checkPlayer(): Boolean {
    return (this is Player).also {
        if (!it) prefixedMessage(ConfigurationWrapper.notAPlayer)
    }
}

fun CommandSender.checkPermission(permission: String): Boolean {
    return (this.hasPermission(permission)).also {
        if (!it) prefixedMessage(ConfigurationWrapper.noPermission)
    }
}

fun CommandSender.prefixedMessage(message: String) {
    sendMessage(ConfigurationWrapper.prefix + " " + message)
}
