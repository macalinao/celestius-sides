/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.celestiussides.cmd;

import net.celestiusmc.celestiussides.CelestiusSides;
import net.celestiusmc.celestiussides.Race;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The race base command.
 */
public class RaceCommand implements CommandExecutor {
    private CelestiusSides plugin;
    
    public RaceCommand(CelestiusSides plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        this.parseCommand(sender, cmd, label, args);
        return true;
    }
    
    public void parseCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You're not allowed to do this from console.");
            return;
        }
        
        if (args.length < 1) {
            sender.sendMessage("Usage: /race spawn or /race setspawn <race>.");
            return;
        }
        
        Player player = (Player) sender;
        
        String action = args[0];
        if (action.equalsIgnoreCase("spawn")) {
            
            Location spawn = plugin.getRaceManager().getRace(player.getName()).getSpawnLocation();
            player.sendMessage(ChatColor.YELLOW + "Teleporting to spawn...");
            player.teleport(spawn);
            
        } else if (action.equalsIgnoreCase("setspawn")) {
            
            if (!player.hasPermission("sides.setspawn")) {
                player.kickPlayer("Nope");
                return;
            }
            
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "You didn't specify a race.");
                return;
            }
            
            Race race = Race.fromString(args[1]);
            if (race == null) {
                player.sendMessage(ChatColor.RED + "Unknown race '" + args[1] + "'.");
                return;
            }
            
            Location location = player.getLocation();
            plugin.getRaceManager().setSpawnLocation(race, location);
            player.sendMessage(ChatColor.YELLOW + "Set the spawn location of " + race.getNiceName() + " to your location.");
            
        } else {
            
            player.sendMessage(ChatColor.RED + "Unknown subcommand.");
            
        }
    }
    
}
