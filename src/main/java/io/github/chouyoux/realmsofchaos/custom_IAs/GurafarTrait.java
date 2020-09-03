package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.entity.EntityJumpEvent;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.trait.SlimeSize;

@TraitName("gurafartrait")
public class GurafarTrait extends Trait {

	RealmsOfChaos plugin = null;

    public GurafarTrait() {
        super("gurafartrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
    }
    
    @EventHandler
    public void onDmg(EntityDamageByEntityEvent event) {
		if (!this.getNPC().isSpawned()) return;
		Entity victim = event.getEntity();
		if (victim == null || this.getNPC().getEntity() == null) return;
		if (!this.getNPC().getEntity().getUniqueId().equals(victim.getUniqueId())) return;
		
		event.setDamage(0);
		
    	Slime slime = (Slime) this.getNPC().getEntity();
    	
    	int size = slime.getSize();
    	if (size > 1) {
			SlimeSize sizeTrait = this.getNPC().getTrait(SlimeSize.class);
	    	sizeTrait.setSize(size-1);
	    	slime.setSize(size-1);
    	}
    	else {
    		slime.remove();
        	Location deathLocation = slime.getLocation();
        	deathLocation.getWorld().playSound(deathLocation, Sound.ENTITY_GENERIC_EXPLODE, 1, 0.5F);
            for (Player player : deathLocation.getWorld().getPlayers()) {
            	if (player.getLocation().distance(deathLocation) < 6.0) {
            		if (player.getFireTicks() > 0) {
            			player.damage(8.0);
            		}
            		else {
            			player.setFireTicks(20*4);
            		}
            	}
            }
    	}
		
    }
    
    @EventHandler
    public void onDmg(EntityDamageEvent event) {
		if (!this.getNPC().isSpawned()) return;
		Entity victim = event.getEntity();
		if (victim == null || this.getNPC().getEntity() == null) return;
		if (!this.getNPC().getEntity().getUniqueId().equals(victim.getUniqueId())) return;
		
		event.setCancelled(true);
    }
    
    @EventHandler
    public void onJump(EntityJumpEvent event) {
    	if (!this.getNPC().isSpawned()) return;
    	if (!this.getNPC().getEntity().getUniqueId().equals(event.getEntity().getUniqueId())) return;
    	if (this.getNPC().getNavigator().getEntityTarget().getTarget() == null) return;
    	
    	Vector toTarget = UtilFuncs.vectorBeetween(this.getNPC().getEntity().getLocation(), this.getNPC().getNavigator().getEntityTarget().getTarget().getLocation());
    	
    	this.getNPC().getEntity().setVelocity(this.getNPC().getEntity().getVelocity().add(toTarget.rotateAroundY(Math.toRadians(90)).multiply(0.1)));
    }
}