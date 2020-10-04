//package net.premiermc.premiercore.colosseum.game
//
//import net.premiermc.premiercore.colosseum.Colosseum
//import org.bukkit.entity.Player
//import org.dragonetmc.hydra.queue.Queue
//import org.dragonetmc.hydra.team.Team
//
//class ColosseumQueue : Queue {
//
//    private val colosseum = Colosseum.getInstance()
//    private val game = colosseum.colosseumGame;
//
//    override fun join(p: Player?, vararg args: Any?) {
//        if (game.joinable) {
//            if (getTeam(p) != null) {
//                if (game.teams.get(0).players.size > game.teams.get(1).players.size)
//                    game.teams.get(1).join(p)
//                else
//                    game.teams.get(0).join(p)
//
//                p?.sendMessage("You have joined a team")
//            } else {
//                p?.sendMessage("You are already part of a team")
//            }
//        } else {
//            p?.sendMessage("Game is not joinable")
//        }
//    }
//
//    override fun leave(p: Player?, vararg args: Any?) {
//        val team = getTeam(p)
//
//        if (team != null)
//            team.leave(p)
//        else
//            p?.sendMessage("You are not part of a team!")
//    }
//
//    override fun construct(vararg args: Any?) {
//        game.start()
//    }
//
//    private fun getTeam(p : Player?) : Team? {
//        for (team in game.teams) {
//            for (player in team.players) {
//                if (player == p) {
//                    return team
//                }
//            }
//        }
//        return null
//    }
//}