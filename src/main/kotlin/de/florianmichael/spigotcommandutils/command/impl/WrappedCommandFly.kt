package de.florianmichael.spigotcommandutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.spigotcommandutils.command.WrappedCommand
import de.florianmichael.spigotcommandutils.command.brigadier.BukkitPlayerArgumentType
import de.florianmichael.spigotcommandutils.command.brigadier.SpigotCommandSource
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import de.florianmichael.spigotcommandutils.util.extension.checkPermission
import de.florianmichael.spigotcommandutils.util.extension.checkPlayer
import de.florianmichael.spigotcommandutils.util.extension.prefixedMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WrappedCommandFly : WrappedCommand("fly") {

    private val permission = ConfigurationWrapper.unwrapString("commands.fly.permission")
    private val addedMessage = ConfigurationWrapper.unwrapString("commands.fly.added-message")
    private val removedMessage = ConfigurationWrapper.unwrapString("commands.fly.removed-message")
    private val addedOtherMessage = ConfigurationWrapper.unwrapString("commands.fly.added-other-message")
    private val removedOtherMessage = ConfigurationWrapper.unwrapString("commands.fly.removed-other-message")

    private fun internalExecutes(sender: CommandSender, self: Boolean, target: Player?) {
        if (sender.checkPlayer() && sender.checkPermission(permission)) {
            val sender = (sender as Player)
            if (!self && target == null) {
                sender.prefixedMessage(ConfigurationWrapper.playerNotOnline)
                return
            }
            (if (self) sender else target)?.apply {
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
        builder.then(argument("player", BukkitPlayerArgumentType.bukkitPlayer())!!.executes {
            internalExecutes(it.source!!.sender, false, BukkitPlayerArgumentType.getBukkitPlayer(it, "player"))
            return@executes SUCCESS
        })
        return builder.executes {
            internalExecutes(it.source.sender, true, null)
            return@executes SUCCESS
        }
    }
}
