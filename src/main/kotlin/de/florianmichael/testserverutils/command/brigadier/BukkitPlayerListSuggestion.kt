package de.florianmichael.testserverutils.command.brigadier

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import org.bukkit.Bukkit
import java.util.concurrent.CompletableFuture

class BukkitPlayerListSuggestion : SuggestionProvider<SpigotCommandSource?> {

    override fun getSuggestions(context: CommandContext<SpigotCommandSource?>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        for (player in Bukkit.getOnlinePlayers())
            if (builder.input.isEmpty() || builder.input.startsWith(player.name)) builder.suggest(player.name)
        return builder.buildFuture()
    }
}
