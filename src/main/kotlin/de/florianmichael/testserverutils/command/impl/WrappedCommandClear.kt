package de.florianmichael.testserverutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.testserverutils.command.WrappedCommand
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.config.ConfigurationWrapper
import de.florianmichael.testserverutils.util.extension.checkPermission
import de.florianmichael.testserverutils.util.extension.checkPlayer
import de.florianmichael.testserverutils.util.extension.prefixedMessage
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