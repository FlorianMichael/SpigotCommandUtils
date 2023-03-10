package de.florianmichael.spigotcommandutils

import de.florianmichael.spigotcommandutils.command.ManagerCommand
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

open class SpigotCommandUtils : JavaPlugin() {

    companion object {
        fun event(listener: Listener) {
            get().apply { server.pluginManager.registerEvents(listener, this) }
        }

        fun get() = getPlugin(SpigotCommandUtils::class.java)
    }

    override fun onEnable() {
        ConfigurationWrapper

        ManagerCommand
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        ManagerCommand.unwrapCommandExecution(sender, command, args, label)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return ManagerCommand.unwrapCommandCompletion(sender, command, args, alias)
    }
}
