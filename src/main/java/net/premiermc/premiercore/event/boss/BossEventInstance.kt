package net.premiermc.premiercore.event.boss

import com.onarandombox.MultiverseCore.MultiverseCore
import net.premiermc.premiercore.core.PremierCorePlugin
import net.premiermc.premiercore.event.boss.teleport.LobbyTeleport
import net.premiermc.premiercore.event.boss.teleport.SpawnTeleport
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import org.dragonetmc.hydra.game.Game
import org.dragonetmc.hydra.obj.Objective
import org.dragonetmc.hydra.team.Team

class BossEventInstance(private val id: String) : Game {

    private val plugin = PremierCorePlugin.getInstance()
    private val teams = mutableSetOf<Team>()
    private val objectives = mutableSetOf<Objective>()
    private val multiverse = plugin.server.pluginManager.getPlugin("Multiverse-Core")
    private val world = "boss-event-$id"

    init {
        BossEvent.bossEventInstances.add(this)
    }

    override fun init() {
        if (multiverse is MultiverseCore) {
            // create lobby teleport instance
            val lobby = LobbyTeleport()
            lobby.id = "boss-event-$id-lobby"

            // create world instance
            val created = multiverse.mvWorldManager.cloneWorld("boss-event-template", world)

            // set lobby location
            lobby.location = Location(multiverse.mvWorldManager.getMVWorld(world).cbWorld, 0.0, 0.0, 0.0)

            // teleport to lobby location
            if (created)
                for (team in teams)
                    for (player in team.players)
                        player.teleport(lobby.location)
        } else
            plugin.server.logger.severe("Can't retrieve Multiverse-Core API")
    }

    override fun start() {
        if (multiverse is MultiverseCore) {
            // create spawn teleport instance
            val spawn = SpawnTeleport()

            // set spawn location
            spawn.location = Location(multiverse.mvWorldManager.getMVWorld(world).cbWorld, 0.0, 0.0, 0.0)

            // teleport to spawn location
            for (team in teams)
                for (player in team.players)
                    player.teleport(spawn.location)
        } else
            plugin.server.logger.severe("Can't retrieve Multiverse-Core API")
    }

    override fun finish() {
        // save stuff
        // TODO("Save items/stats/etc")

        // teleport players to server spawn
        for (team in teams)
            for (player in team.players)
                player.teleport(plugin.server.getWorld("Server").spawnLocation)

        // delete world instance
        if (multiverse is MultiverseCore)
            multiverse.mvWorldManager.deleteWorld("boss-event-$id")
        else
            plugin.server.logger.severe("Can't retrieve Multiverse-Core API")
    }

    override fun join(p: Player?) {
        // add player to the player list
        teams.elementAt(0).players.add(p)

        // send player a message that they have joined the party
        val color = ChatColor.GREEN
        val size = teams.elementAt(0).size
        p?.sendMessage("$color You have joined a boss event party ($size/∞")

        // notify other players in the party that a new player has joined
        for (player in teams.elementAt(0).players) {
            // if player is the player that joined, don't send the message
            if (player == p)
                continue

            player.sendMessage("$color The player $p has joined your boss event party ($size/∞")
        }
    }

    override fun leave(p: Player?) {
        // remove player from the player list
        teams.elementAt(0).players.remove(p)

        // send player a message that they have left the party
        val color = ChatColor.RED
        val size = teams.elementAt(0).size
        p?.sendMessage("$color You have left a boss event party")

        // notify other players in the party that a player has left
        for (player in teams.elementAt(0).players)
            player.sendMessage("$color The player $p has left your boss event party ($size/∞")

        // if all players leave, cancel the instance
        if (teams.elementAt(0).size == 0)
            cancel()
    }

    override fun cancel() {
        // delete world instance
        if (multiverse is MultiverseCore)
            multiverse.mvWorldManager.deleteWorld("boss-event-$id")
        else
            plugin.server.logger.severe("Can't retrieve Multiverse-Core API")
    }

    override fun getId(): String {
        return id
    }

    override fun getTeams(): MutableSet<Team> {
        return teams
    }

    override fun getObjectives(): MutableSet<Objective> {
        return objectives
    }
}