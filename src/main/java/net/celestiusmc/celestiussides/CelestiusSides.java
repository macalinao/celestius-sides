/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.celestiussides;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Celestius sides main plugin class.
 */
public class CelestiusSides extends JavaPlugin {

    private static CelestiusSides _instance;

    private CSListener listener;

    private RaceManager raceManager;

    @Override
    public void onDisable() {
        _instance = null;
        getLogger().log(Level.INFO, "Plugin disabled.");
    }

    @Override
    public void onEnable() {
        _instance = this;
        listener = new CSListener(this);
        raceManager = new RaceManager(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getLogger().log(Level.INFO, "Plugin enabled.");
    }

    public CSListener getListener() {
        return listener;
    }

    public RaceManager getRaceManager() {
        return raceManager;
    }

    public static CelestiusSides getInstance() {
        return _instance;
    }

}
