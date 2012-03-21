/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.celestiussides;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Celestius Sides plugin
 */
public class CSListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        
        Action action = event.getAction();
        if (!action.equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        
        BlockState state = block.getState();
        if (!(state instanceof Sign)) {
            return;
        }
        
        Sign sign = (Sign) state;
        String raceLine = sign.getLine(2);
        Race race = Race.fromString(raceLine);
        if (race == null) {
            return;
        }
        
        //TODO set race
    }

}
