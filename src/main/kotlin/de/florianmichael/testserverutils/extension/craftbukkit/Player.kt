package de.florianmichael.testserverutils.extension.craftbukkit

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CommandSender.checkPlayer(): Boolean {
    return (this is Player).also {
        if (!it) sendMessage("You need to be a player")
    }
}
