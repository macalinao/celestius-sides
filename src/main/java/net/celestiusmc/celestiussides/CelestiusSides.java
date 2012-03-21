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
    
    @Override
    public void onDisable() {
        _instance = null;
        getLogger().log(Level.INFO, "Plugin disabled.");
    }

    @Override
    public void onEnable() {
        _instance = this;
        getServer().getPluginManager().registerEvents(new CSListener(), this);
        getLogger().log(Level.INFO, "Plugin enabled.");
    }

    public static CelestiusSides getInstance() {
        return _instance;
    }
}
