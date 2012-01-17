package com.herocraftonline.fearthereaper.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.command.ColouredConsoleSender;
import org.bukkit.entity.Player;

import com.herocraftonline.fearthereaper.FearTheReaper;
import com.herocraftonline.fearthereaper.spawnpoint.Spawn;
import com.herocraftonline.fearthereaper.spawnpoint.SpawnPoint;
import com.herocraftonline.fearthereaper.utils.GraveyardUtils;

public class ReaperCommands implements CommandExecutor {
    public final FearTheReaper plugin;
    private static String[] arg;

    public ReaperCommands(FearTheReaper instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        arg = args;
        Player player = null;
        if ((sender instanceof Player)) {
            player = (Player)sender;
        }
        if (args.length == 0) {
            return false;
        }

        if (graveyardCommand("closest", sender)) {
            Spawn point = SpawnPoint.getClosestAllowed(player);
            commandLine(sender);

            if (point != null) {
                sender.sendMessage(ChatColor.WHITE + "Closest spawn point is: " + ChatColor.RED + SpawnPoint.getClosestAllowed(player).getName());
            } else {
                sender.sendMessage(ChatColor.WHITE + "Could not find the closest spawn point to your location.");
            }

            commandLine(sender);
            return true;
        }

        if (graveyardCommand("info", sender)) {
            Spawn point = SpawnPoint.getClosestAllowed(player);
            commandLine(sender);
            sender.sendMessage(ChatColor.GRAY + point.getName() + ": " + ChatColor.WHITE + Math.round(point.getX()) + ", " + Math.round(point.getY()) + ", " + Math.round(point.getZ()));
            sender.sendMessage(ChatColor.WHITE + "Respawn Message: " + ChatColor.GREEN + point.getSpawnMessage());
            sender.sendMessage(ChatColor.WHITE + "Group: " + ChatColor.GREEN + point.getGroup());
            sender.sendMessage(ChatColor.WHITE + "Permission: " + ChatColor.GREEN + "'graveyard.spawn." + point.getGroup() + "'");
            commandLine(sender);
            return true;
        }

        if ((graveyardCommand("list", sender)) && (args.length == 1)) {
            commandLine(sender);
            for (Spawn point : FearTheReaper.SpawnPointList.values()) {
                sender.sendMessage(ChatColor.GRAY + point.getName() + ": " + ChatColor.WHITE + Math.round(point.getX()) + ", " + Math.round(point.getY()) + ", " + Math.round(point.getZ()) + " : " + point.getWorldName());
            }
            commandLine(sender);
            return true;
        }

        if ((graveyardCommand("tp", sender)) && (args.length == 1)) {
            commandLine(sender);
            sender.sendMessage(ChatColor.GRAY + "/" + command.getName() + ChatColor.WHITE + " tp " + ChatColor.RED + "Name");
            sender.sendMessage(ChatColor.WHITE + "Teleports player to the specified spawn point.");
            commandLine(sender);
            return true;
        }

        if ((graveyardCommand("tp", sender)) && (args.length > 1)) {
            String pointname = GraveyardUtils.makeString(args);
            if (SpawnPoint.exists(pointname))
            {
                player.teleport(SpawnPoint.get(pointname).getLocation());
                player.sendMessage("Teleporting to: " + pointname);
            }
            else {
                player.sendMessage("Spawnpoint '" + pointname + "' does not exist.");
            }
            return true;
        }

        if ((graveyardCommand("message", sender)) && (args.length > 1)) {
            String message = GraveyardUtils.makeString(args);
            Spawn closest = SpawnPoint.getClosestAllowed(player);
            closest.setSpawnMessage(message);
            player.sendMessage(ChatColor.GRAY + closest.getName() + ChatColor.WHITE + " respawn message set to " + ChatColor.GREEN + message);
            SpawnPoint.save(closest);
            return true;
        }

        if ((graveyardCommand("reload", sender)) && (args.length == 1)) {
            commandLine(sender);

            FearTheReaper.SpawnPointList.clear();
            SpawnPoint.loadAllPoints();
            sender.sendMessage(ChatColor.WHITE + "All spawn points have been reloaded.");
            commandLine(sender);
            return true;
        }

        if ((graveyardCommand("message", sender)) && (args.length == 1)) {
            commandLine(sender);
            sender.sendMessage(ChatColor.GRAY + "/" + command.getName() + ChatColor.WHITE + " message " + ChatColor.RED + "Spawn Message");
            sender.sendMessage(ChatColor.WHITE + "Changes the respawn message of the closest spawn point.");
            commandLine(sender);
            return true;
        }

        if ((graveyardCommand("group", sender)) && (args.length > 1)) {
            Spawn closest = SpawnPoint.getClosestAllowed(player);
            closest.setGroup(args[1]);
            player.sendMessage(ChatColor.GRAY + closest.getName() + ChatColor.WHITE + " group set to " + ChatColor.GREEN + args[1]);
            SpawnPoint.save(closest);
            return true;
        }

        if ((graveyardCommand("group", sender)) && (args.length == 1)) {
            commandLine(sender);
            sender.sendMessage(ChatColor.GRAY + "/" + command.getName() + ChatColor.WHITE + " message " + ChatColor.RED + "Spawn Message");
            sender.sendMessage(ChatColor.WHITE + "Changes the group of the closest spawn point.");
            commandLine(sender);
            return true;
        }

        if ((graveyardCommand("group", sender)) && (args.length > 1)) {
            Spawn closest = SpawnPoint.getClosestAllowed(player);
            closest.setGroup(args[1]);
            player.sendMessage(ChatColor.GRAY + closest.getName() + ChatColor.WHITE + " group set to " + ChatColor.GREEN + args[1]);
            player.sendMessage(ChatColor.WHITE + "Permission is now " + ChatColor.GREEN + "'graveyard.spawn." + args[1] + "'");
            SpawnPoint.save(closest);
            return true;
        }

        if ((graveyardCommand("add", sender)) && (args.length == 1)) {
            commandLine(sender);
            sender.sendMessage(ChatColor.GRAY + "/" + command.getName() + ChatColor.WHITE + " add " + ChatColor.RED + "Name");
            sender.sendMessage(ChatColor.WHITE + "Adds a new spawn point at current location.");
            commandLine(sender);
            return true;
        }

        if ((graveyardCommand("add", sender)) && (args.length > 1)) {
            String pointname = GraveyardUtils.makeString(args);
            Spawn newpoint = new Spawn(pointname, player);
            SpawnPoint.save(newpoint);
            SpawnPoint.addSpawnPoint(newpoint);
            commandLine(sender);
            sender.sendMessage(ChatColor.DARK_GREEN + "Adding: " + ChatColor.GRAY + pointname);
            commandLine(sender);
            SpawnPoint.save(newpoint);
            return true;
        }

        if ((graveyardCommand("delete", sender)) && (args.length == 1)) {
            commandLine(sender);
            sender.sendMessage(ChatColor.GRAY + "/" + command.getName() + ChatColor.WHITE + " remove " + ChatColor.RED + "Name");
            sender.sendMessage(ChatColor.WHITE + "Permenently deletes specified spawn point.");
            commandLine(sender);
            return true;
        }

        if ((graveyardCommand("delete", sender)) && (args.length > 1)) {
            String pointname = GraveyardUtils.makeString(args);
            if (SpawnPoint.deleteSpawnPoint(pointname))
            {
                commandLine(sender);
                sender.sendMessage(ChatColor.DARK_GREEN + "Deleting: " + ChatColor.GRAY + pointname);
                commandLine(sender);
            } else {
                commandLine(sender);
                sender.sendMessage(ChatColor.DARK_GREEN + "Deleting: " + ChatColor.GRAY + pointname);
                sender.sendMessage(ChatColor.RED + "Error. Point does not exist.");
                commandLine(sender);
            }
            return true;
        }

        return true;
    }

    private boolean graveyardCommand(String string, CommandSender sender) {
        return (arg[0].equalsIgnoreCase(string)) && (notConsole(sender)) && ((sender.hasPermission("graveyard.command." + string) | sender.isOp()));
    }

    private void commandLine(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_GREEN + "*--------------------------------------*");
    }

    private boolean notConsole(CommandSender sender) {
        if (!(sender instanceof ColouredConsoleSender))
        {
            return true;
        }

        commandLine(sender);
        sender.sendMessage(ChatColor.WHITE + "Sorry you cannot use that command from the console.");
        commandLine(sender);
        return false;
    }
}