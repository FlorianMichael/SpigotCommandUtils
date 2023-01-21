package de.florianmichael.testserverutils.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource

abstract class WrappedCommand(private vararg val aliases: String)  {

    companion object {

        const val SUCCESS = 1
        const val ERROR = 0
    }

    open fun literal(name: String): LiteralArgumentBuilder<SpigotCommandSource> = LiteralArgumentBuilder.literal(name)
    open fun argument(name: String?, type: ArgumentType<*>?): RequiredArgumentBuilder<SpigotCommandSource?, *>? = RequiredArgumentBuilder.argument(name, type)

    abstract fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource>

    fun setup(dispatcher: CommandDispatcher<SpigotCommandSource>) {
        aliases.forEach { alias ->
            literal(alias).also {
                dispatcher.register(literal(alias).redirect(dispatcher.register(builder(it))))
            }
        }
    }
}
