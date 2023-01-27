package de.florianmichael.testserverutils

import de.florianmichael.testserverutils.command.ManagerCommand
import de.florianmichael.testserverutils.config.ConfigurationWrapper
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

open class TestServerUtils : JavaPlugin() {

    companion object {

        fun get() = getPlugin(TestServerUtils::class.java)
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
