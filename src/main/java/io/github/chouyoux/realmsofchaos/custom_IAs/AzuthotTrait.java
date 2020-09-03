package io.github.chouyoux.realmsofchaos.custom_IAs;

import java.util.List;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("azuthottrait")
public class AzuthotTrait extends Trait {

	RealmsOfChaos plugin = null;
	private int hitCount = 0;
	
    public AzuthotTrait() {
        super("azuthottrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
    }
    
    private void blow() {
    	Blaze blaze = (Blaze) getNPC().getEntity();
		List<LivingEntity> nearby = UtilFuncs.getNearbyEntities3D(blaze.getLocation(), 25);
		blaze.getLocation().getWorld().spawnParticle(Particle.FLASH, blaze.getLocation().add(0, 0.5, 0), 1);
		blaze.getLocation().getWorld().playSound(blaze.getLocation(), Sound.ENTITY_BLAZE_HURT, 1, 0.5F);
		for (LivingEntity l : nearby) {
			if (RoCNPC.getFaction(l).equals(RoCNPC.getFaction(blaze))) continue;
			l.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 1));
		}
    }
    
    @EventHandler
	public void onDamage(EntityDeathEvent event) {
		Entity victim = event.getEntity();
		if (!(victim instanceof LivingEntity)) return;
		if (!npc.isSpawned()) return;
    	if (!this.getNPC().getEntity().getUniqueId().equals(victim.getUniqueId())) return;

		blow();
    }
    
    @EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		if (!(victim instanceof LivingEntity)) return;
		if (!npc.isSpawned()) return;
    	if (!this.getNPC().getEntity().getUniqueId().equals(victim.getUniqueId())) return;
		
		hitCount++;
		if (hitCount > 5) {
			hitCount = 0;
			blow();
		}
    }
}
