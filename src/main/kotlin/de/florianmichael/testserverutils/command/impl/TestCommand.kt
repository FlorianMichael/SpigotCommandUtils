package de.florianmichael.testserverutils.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.florianmichael.testserverutils.command.brigadier.SpigotCommandSource
import de.florianmichael.testserverutils.command.WrappedCommand

class TestCommand : WrappedCommand("test") {

    override fun builder(builder: LiteralArgumentBuilder<SpigotCommandSource>): LiteralArgumentBuilder<SpigotCommandSource> {
        return builder.then(literal("uwu").executes {
            it.source.sender.sendMessage("ABC")
            return@executes SUCCESS
        })
    }
}
