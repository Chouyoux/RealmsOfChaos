package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ravager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("berociustrait")
public class BerociusTrait extends Trait {

	RealmsOfChaos plugin = null;

    public BerociusTrait() {
        super("berociustrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
    }
	
	@EventHandler
	public void onExit(EntityDismountEvent event) {
		if (!this.getNPC().isSpawned()) return;
    	if (!this.getNPC().getEntity().getUniqueId().equals(event.getDismounted().getUniqueId())) return;
		
    	event.setCancelled(true);
    	this.getNPC().getEntity().addPassenger(event.getEntity());
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (!this.getNPC().isSpawned()) return;
    	if (!this.getNPC().getEntity().getUniqueId().equals(event.getDamager().getUniqueId())) return;
    	
    	Ravager ravager = (Ravager) event.getDamager();
    	
		if (ravager.getPassengers().size() > 0) {
			return;
		}
    	
    	event.setDamage(0);
    	
    	Entity victim = event.getEntity();
    	
    	if (ravager.getUniqueId().equals(victim.getUniqueId()))
    		return;

    	ravager.addPassenger(victim);
    	ravager.getLocation().getWorld().playSound(ravager.getLocation(), Sound.ENTITY_RAVAGER_CELEBRATE, 1, 0.5F);
    	
    	BukkitRunnable effectEnd = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		ravager.removePassenger(victim);
	        	ravager.getLocation().getWorld().playSound(ravager.getLocation(), Sound.ENTITY_RAVAGER_STUNNED, 1, 0.5F);
	    	}
	    };
	    effectEnd.runTaskLater(RealmsOfChaos.instance, 20*5);
    	
    }
}