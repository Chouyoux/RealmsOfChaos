package io.github.chouyoux.realmsofchaos.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;

public class AbyssEvents implements Listener{
	
    private RealmsOfChaos main;

	public AbyssEvents() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
        

    	BukkitRunnable removeLavaFire = new BukkitRunnable() {
	    	@Override
	        public void run() {
        		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
        			if (player.getLocation().getBlock().getType().equals(Material.LAVA)) {
        				player.setFireTicks(0);
        			}
        		}
	    	}
	    };
	    removeLavaFire.runTaskTimer(RealmsOfChaos.instance, 1, 1);
	}
	
	@EventHandler
	public void noLavaDamage(EntityDamageEvent event) {
	       if (event.getCause() == DamageCause.LAVA)
	           event.setCancelled(true);
	}

}