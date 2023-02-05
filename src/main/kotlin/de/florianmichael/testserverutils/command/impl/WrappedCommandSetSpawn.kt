package de.florianmichael.testserverutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.testserverutils.TestServerUtils
import de.florianmichael.testserverutils.command.WrappedCommand
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.config.ConfigurationWrapper
import de.florianmichael.testserverutils.util.extension.checkPermission
import de.florianmichael.testserverutils.util.extension.checkPlayer
import de.florianmichael.testserverutils.util.extension.prefixedMessage
import org.bukkit.Location
import org.bukkit.entity.Player

class WrappedCommandSetSpawn : WrappedCommand("setspawn") {

    private val permission = ConfigurationWrapper.unwrapString("commands.setspawn.permission")
    private val message = ConfigurationWrapper.unwrapString("commands.setspawn.message")

    companion object {
        lateinit var spawnPoint: Location
    }

    init {
        spawnPoint = TestServerUtils.get().config.get("commands.setspawn.spawn-position") as Location
    }

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> {
        return builder.executes {
            it.source.sender.apply {
                if (checkPlayer() && checkPermission(permission)) {
                    TestServerUtils.get().config.set("commands.setspawn.spawn-position", (this as Player).location)
                    spawnPoint = this.location
                    prefixedMessage(message)
                }
            }
            return@executes SUCCESS
        }
    }
}
