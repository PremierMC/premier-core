package net.premiermc.premiercore.colosseum.command

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import net.premiermc.premiercore.colosseum.Colosseum
import net.premiermc.premiercore.colosseum.bet.ColosseumBet
import org.bukkit.Bukkit
import java.lang.Exception

class ColosseumCommand : CommandExecutor {

    private val colosseum = Colosseum.getInstance()

    override fun onCommand(sender: CommandSender?, cmd: Command?, label: String?, args: Array<out String>?): Boolean {
        if (cmd?.name.equals("col", true) || cmd?.name.equals("colosseum", true)) {
            if (sender is Player) {
                if (args?.size == 0)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAvailable subcommands: &2join&a, &2leave&a, &2bet"))
                else {
                    when (args?.get(0)) {
                        "join" -> colosseum.colosseumQueue.join(sender)
                        "leave" -> colosseum.colosseumQueue.leave(sender)
                        "bet" -> {
                            try {
                                val colosseumBet = ColosseumBet(sender, Bukkit.getServer().getPlayer(args[1]), args[2].toDouble())
                                colosseum.betManager.bets.put(sender, colosseumBet)
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have bet &2${args[2]} &acoins on &2${sender.name}&a!"))
                            } catch (e: Exception) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCommand is /bet <fighter> <amount>"))
                                return true
                            }
                        }
                    }
                }
            } else {
                sender?.sendMessage("You must be a player to use that command!")
            }
        }
        return true
    }
}