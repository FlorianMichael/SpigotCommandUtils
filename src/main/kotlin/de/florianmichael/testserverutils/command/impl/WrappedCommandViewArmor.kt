package de.florianmichael.testserverutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.testserverutils.command.WrappedCommand
import de.florianmichael.testserverutils.command.brigadier.BukkitPlayerArgumentType
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.config.ConfigurationWrapper
import de.florianmichael.testserverutils.util.extension.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player

const val inventorySize = 4 * 9 // 4 rows and 9 columns
class WrappedCommandViewArmor : WrappedCommand("viewarmor") {
    private val permission = ConfigurationWrapper.unwrapString("commands.viewarmor.permission")

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> {
        return builder.then(argument("player", BukkitPlayerArgumentType.bukkitPlayer())!!.executes {
            it.source!!.sender.apply {
                if (checkPlayer() && checkPermission(permission)) {
                    val target = BukkitPlayerArgumentType.getBukkitPlayer(it, "player")

                    val inventory = Bukkit.createInventory(null, inventorySize)

                    inventory.setItem(0, target.inventory.itemInOffHand)
                    target.inventory.armorContents.apply {
                        reverse()
                        forEachIndexed { index, itemStack -> inventory.setItem(4 + (index * 9), itemStack) }
                    }

                    (this as Player).openInventory(inventory)
                }
            }
            return@executes SUCCESS
        })
    }
}
