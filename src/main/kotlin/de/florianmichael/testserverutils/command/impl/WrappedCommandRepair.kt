package de.florianmichael.testserverutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.testserverutils.command.WrappedCommand
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.config.ConfigurationWrapper
import de.florianmichael.testserverutils.util.extension.checkPermission
import de.florianmichael.testserverutils.util.extension.checkPlayer
import de.florianmichael.testserverutils.util.extension.prefixedMessage
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.Damageable

class WrappedCommandRepair : WrappedCommand("repair", "fix") {
    private val permission = ConfigurationWrapper.unwrapString("commands.repair.permission")
    private val message = ConfigurationWrapper.unwrapString("commands.repair.message")

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> = builder.executes {
        it.source.sender.apply {
            if (checkPlayer() && checkPermission(permission)) {
                val inventory = (it.source.sender as Player).inventory
                val item = if (inventory.itemInMainHand != null) inventory.itemInMainHand else if (inventory.itemInOffHand != null) inventory.itemInOffHand else null

                item?.itemMeta?.also { itemMeta ->
                    (itemMeta as Damageable).damage = 0
                }
                prefixedMessage(message)
            }
        }
        return@executes SUCCESS
    }
}
