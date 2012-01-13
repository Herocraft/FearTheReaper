package com.herocraftonline.fearthereaper.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.herocraftonline.fearthereaper.FearTheReaper;

public class GraveyardUtils {
    
    public static String makeString(String[] input) {
        String pointname = "";
        for (int i = 1; i < input.length; i++) pointname = pointname + input[i] + " ";
        pointname = pointname.substring(0, pointname.length() - 1);

        return pointname;
    }

    public static boolean hasBed(Player player) {
        Location bedlocation = player.getBedSpawnLocation();
        if ((bedlocation != null) && (bedlocation.getBlock().getType() == Material.BED_BLOCK)) {
            return true;
        }
        return false;
    }

    public static boolean useBed(Player player) {
        if (FearTheReaper.isBedsEnabled()) {
            if ((hasBed(player)) && (player.hasPermission("graveyard.respawn.bed"))) {
                return true;
            }
        }
        return false;
    }
}