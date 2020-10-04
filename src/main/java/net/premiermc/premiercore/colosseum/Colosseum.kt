package net.premiermc.premiercore.colosseum

import net.premiermc.premiercore.colosseum.bet.ColosseumBetManager
import net.premiermc.premiercore.colosseum.game.ColosseumGame
import net.premiermc.premiercore.colosseum.game.ColosseumQueue

class Colosseum() {

    val colosseumQueue = ColosseumQueue()
    val betManager = ColosseumBetManager()
    val colosseumGame = ColosseumGame()

    companion object {
        lateinit var instance : Colosseum

        @JvmName("getInstance1")
        fun getInstance() : Colosseum {
            return instance;
        }
    }

    init {
        instance = this
    }
}