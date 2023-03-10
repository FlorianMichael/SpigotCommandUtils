package de.florianmichael.spigotcommandutils.command.impl

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.spigotcommandutils.command.WrappedCommand
import de.florianmichael.spigotcommandutils.command.brigadier.SpigotCommandSource
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import de.florianmichael.spigotcommandutils.util.extension.checkPermission
import de.florianmichael.spigotcommandutils.util.extension.checkPlayer
import de.florianmichael.spigotcommandutils.util.extension.prefixedMessage
import org.bukkit.entity.Player

@Suppress("NAME_SHADOWING")
class WrappedCommandPTime : WrappedCommand("ptime") {
    private val permission = ConfigurationWrapper.unwrapString("commands.ptime.permission")
    private val message = ConfigurationWrapper.unwrapString("commands.ptime.message")

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> = builder.then(
        argument("time", StringArgumentType.string())!!.suggests { _, builder ->
            DayCircle.values().forEach { builder.suggest(it.name.lowercase()) }
            return@suggests builder.buildFuture()
        }.executes {
            val time = DayCircle.byName(StringArgumentType.getString(it, "time"))
            if (time == null) DayCircle.DAY

            it.source!!.sender.apply {
                if (checkPlayer() && checkPermission(permission)) {
                    (this as Player).setPlayerTime(time!!.time, true)
                    prefixedMessage(message)
                }
            }
            return@executes SUCCESS
        }
    )

    enum class DayCircle(val time: Long) {
        DAY(6000),
        SUNSET(12000),
        NIGHT(15000),
        SUNRISE(23000);

        companion object {
            fun byName(name: String) = values().firstOrNull { it.name.lowercase() == name }
        }
    }
}
