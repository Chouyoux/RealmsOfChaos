package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.CustomAI;
import io.github.chouyoux.realmsofchaos.custom_mobs.Guron;
import io.github.chouyoux.realmsofchaos.particle_effects.GuronShieldEffect;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("gurontrait")
public class GuronTrait extends Trait {

	RealmsOfChaos plugin = null;

	private int stage = 0;
	
    public GuronTrait() {
        super("gurontrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
    }
    
    @EventHandler
    public void onDmg(EntityDamageEvent event) {
		if (!this.getNPC().isSpawned()) return;
		Entity victim = event.getEntity();
		if (victim == null || this.getNPC().getEntity() == null) return;
		if (!this.getNPC().getEntity().getUniqueId().equals(victim.getUniqueId())) return;
		
		Guron guron = (Guron) CustomAI.NPCToMob.get(getNPC());
		IronGolem golem = (IronGolem) victim;
		double max_health = guron.getHealth();
		double health = golem.getHealth() - event.getFinalDamage();
		double rate = health / max_health;
		
		double absorb_amount = 0;
		
		if (stage == 0 && rate < 0.74) {
			absorb_amount = max_health*0.25;
		}

		if (stage == 1 && rate < 0.50) {
			absorb_amount = max_health*0.50;
		}
		

		if (stage == 2 && rate < 0.25) {
			absorb_amount = max_health*0.75;
		}
		
		if (absorb_amount > 0) {
			stage++;
			GuronShieldEffect effect = new GuronShieldEffect(RealmsOfChaos.effectManager, golem);
			golem.setAbsorptionAmount(absorb_amount);
			golem.setAware(false);
			double speed = guron.getMoveSpeed();
			guron.setMoveSpeed(0);
			BukkitRunnable cancelEffect = new BukkitRunnable() {
				
		    	@Override
		        public void run() {
	        		effect.cancel();
	        		if (golem.getAbsorptionAmount() > 0) {
		    			golem.setAware(true);
		    			guron.setMoveSpeed(speed);
	        			golem.setHealth(golem.getHealth()+golem.getAbsorptionAmount());
	        		}
	        		else {
	        			BukkitRunnable setAwarenessLater = new BukkitRunnable() {
	        		    	@Override
	        		        public void run() {
	        	    			golem.setAware(true);
	    		    			guron.setMoveSpeed(speed);
	        		    	}
	        		    };
	        		    setAwarenessLater.runTaskLater(RealmsOfChaos.instance, 20*3);
	        		}
	        		golem.setAbsorptionAmount(0);
		    	}
		    };
		    cancelEffect.runTaskLater(RealmsOfChaos.instance, 20*5);
		}
		
    }
    
    
}