package de.florianmichael.spigotcommandutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.spigotcommandutils.SpigotCommandUtils
import de.florianmichael.spigotcommandutils.command.WrappedCommand
import de.florianmichael.spigotcommandutils.command.brigadier.SpigotCommandSource
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import de.florianmichael.spigotcommandutils.util.extension.checkPermission
import de.florianmichael.spigotcommandutils.util.extension.checkPlayer
import de.florianmichael.spigotcommandutils.util.extension.prefixedMessage
import org.bukkit.Location
import org.bukkit.entity.Player

class WrappedCommandSetSpawn : WrappedCommand("setspawn") {

    private val permission = ConfigurationWrapper.unwrapString("commands.setspawn.permission")
    private val message = ConfigurationWrapper.unwrapString("commands.setspawn.message")

    companion object {
        lateinit var spawnPoint: Location
    }

    init {
        spawnPoint = SpigotCommandUtils.get().config.get("commands.setspawn.spawn-position") as Location
    }

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> {
        return builder.executes {
            it.source.sender.apply {
                if (checkPlayer() && checkPermission(permission)) {
                    SpigotCommandUtils.get().config.set("commands.setspawn.spawn-position", (this as Player).location)
                    spawnPoint = this.location
                    prefixedMessage(message)
                }
            }
            return@executes SUCCESS
        }
    }
}
