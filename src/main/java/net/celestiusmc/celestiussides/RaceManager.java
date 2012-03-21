/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.celestiussides;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import net.celestiusmc.celestiussides.util.LocationSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

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

    private File playersFileFile;

    private YamlConfiguration playersFile;

    public RaceManager(CelestiusSides plugin) {
        this.plugin = plugin;
        load();
        buildCache();
        save();
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
        playersFileFile = new File(plugin.getDataFolder().getPath()
                + File.separator + "players.yml");
        playersFile = YamlConfiguration.loadConfiguration(playersFileFile);
    }

    private void save() {
        //Write to config
        for (Entry<Race, Set<String>> raceEntry : races.entrySet()) {
            playersFile.set(raceEntry.getKey().getNiceName(),
                    raceEntry.getValue());
        }

        //Save file
        try {
            playersFile.save(playersFileFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE,
                    "Could not save!?!@#!O$U(!@TUFISFEFAFA#R", ex);
        }
    }

    private void buildCache() {
        raceCache = CacheBuilder.newBuilder().expireAfterAccess(10L,
                TimeUnit.MINUTES).build(new CacheLoader<String, Race>() {

            @Override
            public Race load(String k) throws Exception {
                for (Entry<Race, Set<String>> entry : races.entrySet()) {
                    Set<String> raceMembers = entry.getValue();
                    if (raceMembers.contains(k)) {
                        return entry.getKey();
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
     * @param player The player to set the race of.
     * @param race The race to set.
     */
    public void setRace(String player, Race race) {
        getRacePlayers(race).add(player);
        raceCache.invalidate(player);
        save();
    }

    /**
     * Gets the players of a given race.
     *
     * @param race The race.
     * @return The list of players in that race.
     */
    private Set<String> getRacePlayers(Race race) {
        Set<String> players = races.get(race);
        if (players == null) {
            players = new HashSet<String>();
            races.put(race, players);
        }
        return players;
    }

}
