package de.florianmichael.spigotcommandutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.spigotcommandutils.command.WrappedCommand
import de.florianmichael.spigotcommandutils.command.brigadier.BukkitPlayerArgumentType
import de.florianmichael.spigotcommandutils.command.brigadier.SpigotCommandSource
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import de.florianmichael.spigotcommandutils.util.extension.checkPermission
import de.florianmichael.spigotcommandutils.util.extension.checkPlayer
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
