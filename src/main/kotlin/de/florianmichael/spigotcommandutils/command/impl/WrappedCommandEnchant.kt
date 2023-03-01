package de.florianmichael.spigotcommandutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.spigotcommandutils.command.WrappedCommand
import de.florianmichael.spigotcommandutils.command.brigadier.SpigotCommandSource
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import de.florianmichael.spigotcommandutils.util.extension.checkPermission
import de.florianmichael.spigotcommandutils.util.extension.checkPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

class WrappedCommandEnchant : WrappedCommand("enchant") {
    private val permission = ConfigurationWrapper.unwrapString("commands.enchant.permission")

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> = builder.executes {
        it.source.sender.apply {
            if (checkPlayer() && checkPermission(permission)) {
                (this as Player).openInventory(Bukkit.createInventory(this, InventoryType.ENCHANTING))
            }
        }
        return@executes SUCCESS
    }
}
