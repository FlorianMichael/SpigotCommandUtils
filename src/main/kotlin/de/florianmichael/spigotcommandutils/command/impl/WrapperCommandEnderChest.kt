package de.florianmichael.spigotcommandutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.spigotcommandutils.command.WrappedCommand
import de.florianmichael.spigotcommandutils.command.brigadier.SpigotCommandSource
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import de.florianmichael.spigotcommandutils.util.extension.checkPermission
import de.florianmichael.spigotcommandutils.util.extension.checkPlayer
import org.bukkit.entity.Player

class WrapperCommandEnderChest : WrappedCommand("enderchest", "echest", "ec") {
    private val permission = ConfigurationWrapper.unwrapString("commands.enderchest.permission")

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> = builder.executes {
        it.source.sender.apply {
            if (checkPlayer() && checkPermission(permission)) {
                (this as Player).openInventory(this.enderChest)
            }
        }
        return@executes SUCCESS
    }
}
