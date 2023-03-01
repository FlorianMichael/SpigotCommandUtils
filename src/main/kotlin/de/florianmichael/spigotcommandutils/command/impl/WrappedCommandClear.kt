package de.florianmichael.spigotcommandutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.spigotcommandutils.command.WrappedCommand
import de.florianmichael.spigotcommandutils.command.brigadier.SpigotCommandSource
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import de.florianmichael.spigotcommandutils.util.extension.checkPermission
import de.florianmichael.spigotcommandutils.util.extension.checkPlayer
import de.florianmichael.spigotcommandutils.util.extension.prefixedMessage
import org.bukkit.entity.Player

class WrappedCommandClear : WrappedCommand("clear") {
    
    private val permission = ConfigurationWrapper.unwrapString("commands.clear.permission")
    private val message = ConfigurationWrapper.unwrapString("commands.clear.message")

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> = builder.executes {
        it.source.sender.apply {
            if (checkPlayer() && checkPermission(permission)) {
                (this as Player).inventory.clear()
                prefixedMessage(message)
            }
        }
        return@executes SUCCESS
    }
}