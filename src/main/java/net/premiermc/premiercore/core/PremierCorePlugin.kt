package net.premiermc.premiercore.core

import de.leonhard.storage.Yaml
import net.premiermc.premiercore.core.configuration.ConfigManager
import org.bukkit.plugin.java.JavaPlugin

class PremierCorePlugin : JavaPlugin() {

    val configManager = ConfigManager(Yaml("config", dataFolder.absolutePath))

    companion object {
        lateinit var instance : PremierCorePlugin

        @JvmName("getInstance0")
        fun getInstance() : PremierCorePlugin {
            return instance;
        }
    }

    override fun onDisable() {

    }

    override fun onEnable() {
        instance = this;
    }
}