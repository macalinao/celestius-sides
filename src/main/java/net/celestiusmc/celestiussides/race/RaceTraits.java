/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.celestiussides.race;

import org.bukkit.Location;

/**
 * Race traits that are specific to a race.
 */
public interface RaceTraits {
    /**
     * Gets the spawn location of the race.
     * 
     * @return The location of the race's spawn.
     */
    public Location getSpawnLocation();
}
