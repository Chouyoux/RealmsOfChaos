package io.github.chouyoux.realmsofchaos.ticks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.listeners.Player_Connect;

public class PlayersTick extends BukkitRunnable {

    public PlayersTick() {
    	this.runTaskTimer(RealmsOfChaos.instance, 0, 20);
    }

    @Override
    public void run() {
    	
    	for (Player p : Bukkit.getServer().getOnlinePlayers()) {
    		RoCPlayers.setCombatTime(p, Math.max(RoCPlayers.getCombatTime(p)-1, 0));
    		
    		if (Player_Connect.targets.containsKey(p) && Player_Connect.targets.get(p).isValid()) {
    			
    		}
    	}
    	
    }

}
