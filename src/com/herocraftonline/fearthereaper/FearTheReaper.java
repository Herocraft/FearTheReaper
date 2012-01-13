package com.herocraftonline.fearthereaper;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.fearthereaper.commands.GraveyardCommads;
import com.herocraftonline.fearthereaper.spawnpoint.Spawn;
import com.herocraftonline.fearthereaper.spawnpoint.SpawnPoint;

public class FearTheReaper extends JavaPlugin {
    public static Logger log = Logger.getLogger("Minecraft");
    public static File pointsDirectory;
    public static FileConfiguration config;
    public static HashMap<String, Spawn> SpawnPointList = new HashMap<String, Spawn>();

    public static HashMap<String, Spawn> getSpawnList() {
        return SpawnPointList;
    }

    public void onDisable() {
        saveConfig();
        log.info("[" + getDescription().getName() + "] v" + getDescription().getVersion() + " unloaded successfully!");
    }

    public void onEnable() {
        pointsDirectory = new File(getDataFolder(), "points");
        config = getConfig();
        SpawnPoint.loadAllPoints();


        getServer().getPluginManager().registerEvent(Type.PLAYER_RESPAWN, new GraveyardPlayerListener(this), Priority.High, this);

        log.info("[" + getDescription().getName() + "] v" + getDescription().getVersion() + " loaded successfully!");

        getCommand("graveyard").setExecutor(new GraveyardCommads(this));

    }

    public static void reloadConfig(FearTheReaper plugin) {
        System.out.println("[Graveyard] Reloading config.");
        plugin.reloadConfig();
        FearTheReaper.config = plugin.getConfig();
    }

    public static boolean isBedsEnabled() {
        return FearTheReaper.config.getBoolean("enable-beds", false);
    }
}