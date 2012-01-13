package com.herocraftonline.fearthereaper.spawnpoint;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Spawn {
    private String name;
    private Location location;
    private String spawngroup;
    private String spawnmessage;

    public Spawn(String string, World world, double x, double y, double z, String group, String message) {
        this.name = string;
        this.location = new Location(world, x, y, z);
        this.spawngroup = group;
        this.spawnmessage = message;
    }

    public Spawn(String string, Player player) {
        this.name = string;
        this.location = player.getLocation();
        this.spawngroup = "all";
        this.spawnmessage = "none";
    }

    public String getSpawnMessage() {
        return this.spawnmessage;
    }

    public String getGroup() {
        return this.spawngroup;
    }

    public String getWorldName() {
        return this.location.getWorld().getName();
    }

    public void setSpawnMessage(String message) {
        this.spawnmessage = message;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public void setGroup(String string) {
        this.spawngroup = string;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public double getX() {
        return this.location.getX();
    }

    public double getY() {
        return this.location.getY();
    }

    public double getZ() {
        return this.location.getZ();
    }

    public World getWorld() {
        return this.location.getWorld();
    }
}