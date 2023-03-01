package de.florianmichael.spigotcommandutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.spigotcommandutils.command.WrappedCommand
import de.florianmichael.spigotcommandutils.command.brigadier.SpigotCommandSource
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import de.florianmichael.spigotcommandutils.util.extension.checkPermission
import de.florianmichael.spigotcommandutils.util.extension.checkPlayer
import de.florianmichael.spigotcommandutils.util.extension.prefixedMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class WrappedCommandGiveAll : WrappedCommand("giveall") {
    private val permission = ConfigurationWrapper.unwrapString("commands.giveall.permission")
    private val message = ConfigurationWrapper.unwrapString("commands.giveall.message")

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> {
        return builder.executes {
            it.source.sender.apply {
                if (checkPlayer() && checkPermission(permission)) {
                    val sender = this as Player
                    val item = sender.inventory.itemInMainHand
                    for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                        onlinePlayer.inventory.addItem(item)
                    }
                    if (item.itemMeta != null) {
                        sender.prefixedMessage(message.format(item.itemMeta?.displayName))
                    } else {
                        sender.prefixedMessage(message.format(item.type.name))
                    }
                }
            }
            return@executes SUCCESS
        }
    }
}
