package de.florianmichael.testserverutils.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.command.impl.*
import de.florianmichael.testserverutils.util.extension.prefixedMessage
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
        ackCommand(WrappedCommandSocialSpy())
        ackCommand(WrappedCommandSetSpawn())
        ackCommand(WrappedCommandSpawn())
        ackCommand(WrappedCommandFly())
        ackCommand(WrappedCommandInvsee())
        ackCommand(WrappedCommandViewArmor())
        ackCommand(WrappedCommandGiveAll())
    }

    fun unwrapCommandExecution(sender: CommandSender, command: Command, args: Array<out String>, label: String) {

        args.joinToString(" ").apply {
            val input = label + (if (this.isNotEmpty()) " $this" else "")
            val nextSource = SpigotCommandSource(sender, command, label)

            try {
                dispatcher.execute(input, nextSource)
            } catch (e: CommandSyntaxException) {
                val parseResults = dispatcher.parse(label, nextSource)
                val lastNode = parseResults.context.nodes.last().node

                sender.prefixedMessage("Use: /" + label + " " + lastNode.children.joinToString(" ") { it.usageText })
            }
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
