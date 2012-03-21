/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.celestiussides;

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
    CHINKY;

    public String getNiceName() {
        return name().toLowerCase();
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
