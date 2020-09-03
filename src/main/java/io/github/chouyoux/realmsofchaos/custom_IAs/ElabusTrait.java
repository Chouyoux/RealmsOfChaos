package io.github.chouyoux.realmsofchaos.custom_IAs;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("elabustrait")
public class ElabusTrait extends Trait {

	RealmsOfChaos plugin = null;
	private int hitCount = 0;

    public ElabusTrait() {
        super("elabustrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
		
		BukkitRunnable shootHits = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		if (!npc.isSpawned()) {
	    			this.cancel();
	    			return;
	    		}

    			Shulker shulker = (Shulker) npc.getEntity();
    			int fire = shulker.getFireTicks();
    			if (npc.getNavigator().getTargetAsLocation() != null)
    				shulker.teleport(npc.getNavigator().getTargetAsLocation());
    			
    			List<LivingEntity> nearby = UtilFuncs.getNearbyEntities3D(shulker.getLocation(), 50);
	    		
    			while (hitCount > 0) {
	    			for (LivingEntity target : nearby) {
	    				if (RoCNPC.getFaction(target).equals(RoCNPC.getFaction(shulker))) continue;
	    				if (hitCount <= 0) break;
		    			ShulkerBullet bullet = shulker.launchProjectile(ShulkerBullet.class);
		    			bullet.setTarget(target);
		    			hitCount--;
	    			}
    			}
	    	}
	    };
	    shootHits.runTaskTimer(RealmsOfChaos.instance, 0, 15*20);
    }
    
    @EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		if (!(victim instanceof LivingEntity)) return;
		if (!npc.isSpawned()) return;
    	if (!this.getNPC().getEntity().getUniqueId().equals(victim.getUniqueId())) return;
		
    	hitCount++;
    }
}