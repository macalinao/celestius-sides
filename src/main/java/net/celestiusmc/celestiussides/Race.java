/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.celestiussides;

import org.bukkit.Location;

/**
 * Represents a race.
 */
public enum Race {

    HUMAN,
    XIYAD,
    NONE,
    //Racist part
    BLACK,
    AFRICAN_PYGMY,
    REDNECK,
    CHINKY,
    NERD;

    public String getNiceName() {
        return name().toLowerCase();
    }

    /**
     * Gets the spawn location of this race.
     * 
     * @return This race's spawn location.
     */
    public Location getSpawnLocation() {
        return CelestiusSides.getInstance().getRaceManager().getSpawnLocation(
                this);
    }
    
    public String getPermission() {
        return "race." + getNiceName();
    }

    public String getGroupName() {
        return "race_" + getNiceName();
    }
    
    public static Race fromString(String name) {
        name = name.toUpperCase().replace(' ', '_').replace('-', '_');
        try {
            return Race.valueOf(name);
        } catch (Exception ex) {
            return null;
        }
    }

}
