package de.florianmichael.testserverutils.command.impl

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.testserverutils.command.WrappedCommand
import de.florianmichael.testserverutils.command.brigadier.BukkitPlayerArgumentType
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.config.ConfigurationWrapper
import de.florianmichael.testserverutils.extension.craftbukkit.checkPermission
import de.florianmichael.testserverutils.extension.craftbukkit.checkPlayer
import org.bukkit.entity.Player

class WrappedCommandInvsee : WrappedCommand("invsee") {

    private val permission = ConfigurationWrapper.unwrapString("commands.invsee.permission")

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> {
        return builder.then(argument("player", BukkitPlayerArgumentType.bukkitPlayer())!!.executes {
            it.source!!.sender.apply {
                if (checkPlayer() && checkPermission(permission)) {
                    (this as Player).openInventory(BukkitPlayerArgumentType.getBukkitPlayer(it, "player").inventory)
                }
            }
            return@executes SUCCESS
        })
    }
}
