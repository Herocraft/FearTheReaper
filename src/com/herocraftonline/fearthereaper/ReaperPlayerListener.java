package com.herocraftonline.fearthereaper;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.herocraftonline.fearthereaper.spawnpoint.Spawn;
import com.herocraftonline.fearthereaper.spawnpoint.SpawnPoint;
import com.herocraftonline.fearthereaper.utils.GraveyardUtils;

public class ReaperPlayerListener extends PlayerListener {
    public static FearTheReaper plugin;

    public ReaperPlayerListener(FearTheReaper instance)
    {
        plugin = instance;
    }

    public void onPlayerRespawn(PlayerRespawnEvent event) {

        if (!GraveyardUtils.useBed(event.getPlayer())) {
            if (event.getPlayer().hasPermission("graveyard.respawn.closest")) {
                if (SpawnPoint.getAllowedList(event.getPlayer()).size() != 0) {
                    Spawn closest = SpawnPoint.getClosestAllowed(event.getPlayer());
                    if (!closest.getSpawnMessage().equalsIgnoreCase("none"))
                        event.getPlayer().sendMessage(closest.getSpawnMessage());
                    event.setRespawnLocation(closest.getLocation());
                }
            }
            if (event.getPlayer().hasPermission("graveyard.respawn.random")) {
                if (SpawnPoint.getAllowedList(event.getPlayer()).size() != 0) {
                    Spawn random = SpawnPoint.getRandomAllowed(event.getPlayer());
                    if (!random.getSpawnMessage().equalsIgnoreCase("none"))
                        event.getPlayer().sendMessage(random.getSpawnMessage());
                    event.setRespawnLocation(random.getLocation());
                }
            }
        }
    }
}