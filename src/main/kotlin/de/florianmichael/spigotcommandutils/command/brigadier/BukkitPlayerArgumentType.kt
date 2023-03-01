package de.florianmichael.spigotcommandutils.command.brigadier

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import de.florianmichael.spigotcommandutils.config.ConfigurationWrapper
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

class BukkitPlayerArgumentType : ArgumentType<Player> {

    private val invalidPlayerException = DynamicCommandExceptionType { LiteralMessage(ConfigurationWrapper.playerNotOnline) }

    companion object {

        fun bukkitPlayer() = BukkitPlayerArgumentType()

        fun getBukkitPlayer(context: CommandContext<SpigotCommandSource?>, name: String): Player {
            return context.getArgument(name, Player::class.java)
        }
    }

    override fun parse(reader: StringReader): Player {
        Bukkit.getPlayer(reader.readString())?.also { return it }
        throw invalidPlayerException.createWithContext(reader, "")
    }

    override fun <S : Any?> listSuggestions(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        for (player in Bukkit.getOnlinePlayers()) builder.suggest(player.name)
        return builder.buildFuture()
    }
}
