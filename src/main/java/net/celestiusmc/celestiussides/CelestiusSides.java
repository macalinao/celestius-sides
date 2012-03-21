/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.celestiussides;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author simplyianm
 */
public class CelestiusSides extends JavaPlugin {

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin disabled.");
    }

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Plugin enabled.");
    }
    
}
