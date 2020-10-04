package net.premiermc.premiercore.colosseum.game

import org.bukkit.entity.Player
import org.dragonetmc.hydra.game.Game
import org.dragonetmc.hydra.obj.Objective
import org.dragonetmc.hydra.team.Team

class ColosseumGame : Game {

    val teams = arrayListOf<Team>()
    val joinable = true

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun finish() {
        TODO("Not yet implemented")
    }

    override fun join(p: Player?) {
        TODO("Not yet implemented")
    }

    override fun leave(p: Player?) {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun getId(): String {
        TODO("Not yet implemented")
    }

    override fun getTeams(): MutableSet<Team> {
        TODO("Not yet implemented")
    }

    override fun getObjectives(): MutableSet<Objective> {
        TODO("Not yet implemented")
    }
}