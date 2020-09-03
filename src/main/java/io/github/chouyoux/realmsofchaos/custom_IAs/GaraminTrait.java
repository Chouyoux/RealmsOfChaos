package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;
import org.mcmonkey.sentinel.SentinelTrait;

import com.destroystokyo.paper.event.entity.EntityJumpEvent;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.Garamin;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.trait.SlimeSize;

@TraitName("garamintrait")
public class GaraminTrait extends Trait {

	RealmsOfChaos plugin = null;

    public GaraminTrait() {
        super("garamintrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
    }
    
    @EventHandler
    public void onEntDmg(EntityDamageByEntityEvent event) {
		if (!this.getNPC().isSpawned()) return;
		Entity victim = event.getEntity();
		if (victim == null || this.getNPC().getEntity() == null) return;
		if (!this.getNPC().getEntity().getUniqueId().equals(victim.getUniqueId())) return;
		
		event.setDamage(0);
		
    	Slime slime = (Slime) this.getNPC().getEntity();
    	
    	int size = slime.getSize();
    	if (size < 6) {
			SlimeSize sizeTrait = this.getNPC().getTrait(SlimeSize.class);
			SentinelTrait sentinelTrait = this.getNPC().getTrait(SentinelTrait.class);
	    	sizeTrait.setSize(size+1);
	    	slime.setSize(size+1);
	    	sentinelTrait.damage = (int) ((Garamin.damage * 5) / size);
    	}
    	else {
    		slime.remove();
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
    	
    	this.getNPC().getEntity().setVelocity(this.getNPC().getEntity().getVelocity().add(toTarget.multiply(0.15)));
    }
}