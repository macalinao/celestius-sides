/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.celestiussides;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import net.celestiusmc.celestiussides.util.LocationSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * Manages the player races.
 */
public class RaceManager {

    private Map<Race, Set<String>> races =
            new EnumMap<Race, Set<String>>(Race.class);

    private Cache<String, Race> raceCache;

    private Map<Race, Location> raceSpawns = new EnumMap<Race, Location>(
            Race.class);

    private CelestiusSides plugin;

    public RaceManager(CelestiusSides plugin) {
        this.plugin = plugin;
        load();
        buildCache();
    }

    private void load() {
        ConfigurationSection spawns = plugin.getConfig().getConfigurationSection(
                "spawn");
        if (spawns == null) {
            spawns = plugin.getConfig().createSection("spawn");
        }

        for (String key : spawns.getKeys(false)) {
            Map<String, Object> spawnMap = (Map<String, Object>) spawns.get(key);
            Race race = Race.fromString(key);
            if (race != null) {
                Location loc = LocationSerializer.deserializeFull(spawnMap);
                raceSpawns.put(race, loc);
            }
        }
    }

    private void buildCache() {
        raceCache = CacheBuilder.newBuilder().expireAfterAccess(10L,
                TimeUnit.MINUTES).build(new CacheLoader<String, Race>() {

            @Override
            public Race load(String player) throws Exception {
                Player p = Bukkit.getPlayer(player);
                for (Race race : Race.values()) {
                    if (p.hasPermission(race.getPermission())) {
                        return race;
                    }
                }
                return Race.NONE;
            }

        });
    }

    /**
     * Gets the spawn location of a race.
     *
     * @param race The race to get the spawn of.
     * @return The race.
     */
    public Location getSpawnLocation(Race race) {
        Location location = raceSpawns.get(race);
        if (location == null) {
            setSpawnLocation(race, Bukkit.getWorlds().get(0).getSpawnLocation());
        }
        return location;
    }

    /**
     * Sets a race's spawn location.
     *
     * @param race The race.
     * @param location The location to set.
     */
    public void setSpawnLocation(Race race, Location location) {
        raceSpawns.put(race, location);
        Map<String, Object> serialized =
                LocationSerializer.serializeFull(location);
        plugin.getConfig().set("spawn." + race.getNiceName(), serialized);
    }

    /**
     * Gets the race of a given player.
     *
     * @param player The player's name.
     * @return The race of the player.
     */
    public Race getRace(String player) {
        try {
            return raceCache.get(player);
        } catch (ExecutionException ex) {
            CelestiusSides.getInstance().getLogger().log(Level.SEVERE,
                    "Exception when getting a race.", ex);
        }
        return null;
    }

    /**
     * Sets a player's race.
     * 
     * @param player The player name.
     * @param race The race.
     */
    public void setRace(String player, Race race) {
        //Works with bPermissions. Do we want Vault?
        CommandSender sender = Bukkit.getConsoleSender();
        Bukkit.dispatchCommand(sender, "world world");
        Bukkit.dispatchCommand(sender, "user " + player);
        Bukkit.dispatchCommand(sender, "user setgroup " + race.getGroupName());
    }
}
