package de.florianmichael.testserverutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.testserverutils.TestServerUtils
import de.florianmichael.testserverutils.command.WrappedCommand
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.config.ConfigurationWrapper
import de.florianmichael.testserverutils.extension.craftbukkit.checkPermission
import de.florianmichael.testserverutils.extension.craftbukkit.checkPlayer
import de.florianmichael.testserverutils.extension.craftbukkit.prefixedMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import java.util.UUID

class WrappedCommandSocialSpy : WrappedCommand("socialspy") {
    private val permission = ConfigurationWrapper.unwrapString("commands.socialspy.permission")

    private val addedMessage = ConfigurationWrapper.unwrapString("commands.socialspy.added-message")
    private val removedMessage = ConfigurationWrapper.unwrapString("commands.socialspy.removed-message")

    private val format = ConfigurationWrapper.unwrapString("commands.socialspy.format")

    private val listeners = ArrayList<UUID>()

    init {
        TestServerUtils.event(object : Listener {
            @EventHandler
            fun onPerformCommand(event: PlayerCommandPreprocessEvent) {
                for (listener in listeners)
                    Bukkit.getPlayer(listener)?.prefixedMessage(String.format(format, event.player.name, event.message))
            }
        })
    }

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> =
        builder.executes {
            it.source.sender.apply {
                if (checkPlayer() && checkPermission(permission)) {
                    val id = (it.source.sender as Player).uniqueId
                    if (listeners.contains(id)) {
                        listeners.remove(id)
                        prefixedMessage(removedMessage)
                    } else {
                        listeners.add(id)
                        prefixedMessage(addedMessage)
                    }
                }
            }
            return@executes SUCCESS
        }
}
