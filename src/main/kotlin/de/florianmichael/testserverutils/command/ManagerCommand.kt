package de.florianmichael.testserverutils.command

import com.mojang.brigadier.CommandDispatcher
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.command.impl.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import java.lang.Exception

object ManagerCommand {
    private val dispatcher = CommandDispatcher<SpigotCommandSource>()

    init {
        ackCommand(WrappedCommandClear())
        ackCommand(WrappedCommandTrash())
        ackCommand(WrappedCommandEnchant())
        ackCommand(WrappedCommandRepair())
        ackCommand(WrapperCommandWorkbench())
        ackCommand(WrapperCommandEnderChest())
        ackCommand(WrappedCommandPTime())
    }

    fun unwrapCommandExecution(sender: CommandSender, command: Command, args: Array<out String>, label: String) {
        try {
            args.joinToString(" ").apply {
                dispatcher.execute(label + (if (this.isNotEmpty()) " $this" else ""), SpigotCommandSource(sender, command, label))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun unwrapCommandCompletion(sender: CommandSender, command: Command, args: Array<out String>, label: String): MutableList<String> {
        return try {
            val argsAsString = args.joinToString(" ")
            val parseResults = dispatcher.parse(label + (if (argsAsString.isNotEmpty()) " $argsAsString" else ""), SpigotCommandSource(sender, command, label))
            val completionSuggestions = dispatcher.getCompletionSuggestions(parseResults).get()

            completionSuggestions.list.map { it.text }.toMutableList()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    private fun ackCommand(command: WrappedCommand) = command.setup(dispatcher)
}
