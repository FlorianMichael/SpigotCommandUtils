package de.florianmichael.testserverutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.testserverutils.TestServerUtils
import de.florianmichael.testserverutils.command.WrappedCommand
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class WrappedCommandSetSpawn : WrappedCommand("setspawn") {

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> {
        return builder.executes {
            return@executes SUCCESS
        }
    }
}
