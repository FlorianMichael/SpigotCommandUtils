package de.florianmichael.testserverutils.command.impl

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.testserverutils.command.WrappedCommand
import de.florianmichael.testserverutils.command.brigadier.BukkitPlayerListSuggestion
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.config.ConfigurationWrapper
import de.florianmichael.testserverutils.extension.craftbukkit.checkPermission
import de.florianmichael.testserverutils.extension.craftbukkit.checkPlayer
import de.florianmichael.testserverutils.extension.craftbukkit.prefixedMessage
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WrappedCommandFly : WrappedCommand("fly") {

    private val permission = ConfigurationWrapper.unwrapString("commands.fly.permission")
    private val addedMessage = ConfigurationWrapper.unwrapString("commands.fly.added-message")
    private val removedMessage = ConfigurationWrapper.unwrapString("commands.fly.removed-message")
    private val addedOtherMessage = ConfigurationWrapper.unwrapString("commands.fly.added-other-message")
    private val removedOtherMessage = ConfigurationWrapper.unwrapString("commands.fly.removed-other-message")

    private fun internalExecutes(sender: CommandSender, target: String?) {
        val self = target == null

        if (sender.checkPlayer() && sender.checkPermission(permission)) {
            val sender = (sender as Player)
            if (!self && Bukkit.getPlayer(target!!) == null) {
                sender.prefixedMessage(ConfigurationWrapper.playerNotOnline)
                return
            }
            (if (self) sender else Bukkit.getPlayer(target!!))?.apply {
                allowFlight = !allowFlight

                if (allowFlight) {
                    if (!self) sender.prefixedMessage(addedOtherMessage.format(name))
                    prefixedMessage(addedMessage)
                } else {
                    if (!self) sender.prefixedMessage(removedOtherMessage.format(name))
                    prefixedMessage(removedMessage)
                }
            }
        }
    }

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> {
        builder.then(argument("player", StringArgumentType.string())!!.suggests(BukkitPlayerListSuggestion()).executes {
            internalExecutes(it.source!!.sender, StringArgumentType.getString(it, "player"))
            return@executes SUCCESS
        })
        return builder.executes {
            internalExecutes(it.source.sender, null)
            return@executes SUCCESS
        }
    }
}
