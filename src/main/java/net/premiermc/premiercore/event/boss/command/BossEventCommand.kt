package net.premiermc.premiercore.event.boss.command

import net.premiermc.premiercore.event.boss.BossEvent
import net.premiermc.premiercore.event.boss.BossEventInstance
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BossEventCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (command?.name.equals("bossevent", true)) {
            if (sender is Player) {
                if (args != null) {
                    if (args.isEmpty())
                        return true
                    else {
                        val subcommand = args[0]
                        val instances = BossEvent.bossEventInstances
                        val green = ChatColor.GREEN
                        val red = ChatColor.RED

                        when (subcommand) {
                            "create" -> {
                                val number = instances.size + 1
                                BossEventInstance("instance-$number")
                            }

                            "join" -> {
                                val instance = args[1]

                                if (instance.isEmpty()) {
                                    sender.sendMessage("$green You must specify an instance id")
                                    return true
                                }

                                for (bossEventInstance in instances) {
                                    if (bossEventInstance.id.equals(instance, true)) {
                                        bossEventInstance.join(sender)
                                        return true
                                    }
                                }

                                sender.sendMessage("$red The instance you specified doesn't exist")
                                return true
                            }

                            "leave" -> {
                                for (bossEventInstance in instances) {
                                    if (bossEventInstance.teams.elementAt(0).players.contains(sender)) {
                                        bossEventInstance.leave(sender)
                                        return true
                                    }
                                }

                                sender.sendMessage("$red You are not in a boss event")
                                return true
                            }

                            else -> return true
                        }
                    }
                }
            }
        }
        return true
    }
}