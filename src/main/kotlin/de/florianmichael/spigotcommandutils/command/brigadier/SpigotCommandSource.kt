package de.florianmichael.spigotcommandutils.command.brigadier

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class SpigotCommandSource(val sender: CommandSender, val command: Command, val label: String)
