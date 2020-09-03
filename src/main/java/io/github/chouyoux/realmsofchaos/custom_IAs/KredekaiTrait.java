package io.github.chouyoux.realmsofchaos.custom_IAs;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("kredekaitrait")
public class KredekaiTrait extends Trait {

	
	public static HashMap<UUID, LocalTime> lastInteraction;
	RealmsOfChaos plugin = null;

    public KredekaiTrait() {
        super("kredekaitrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
		lastInteraction = new HashMap<UUID, LocalTime>();
    }

    @EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		if (!(victim instanceof LivingEntity)) return;
		LivingEntity lvictim = (LivingEntity) victim;
		if (!npc.isSpawned()) return;

		Entity damager = event.getDamager();
		if (!(damager instanceof LivingEntity)) return;
		LivingEntity ldamager = (LivingEntity) damager;
		
		
		if (!npc.getEntity().getUniqueId().equals(damager.getUniqueId())) return;
		
		for (PotionEffect potion : lvictim.getActivePotionEffects()) {
			if (UtilFuncs.isPositiveEffect(potion.getType())) {
				lvictim.removePotionEffect(potion.getType());
				ldamager.addPotionEffect(potion);
			}
		}
		((Damageable) ldamager).setHealth(Math.min(((Attributable) ldamager).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue(), ((Damageable) ldamager).getHealth()+(event.getDamage())));
		
		if (!(lvictim instanceof Player)) return;
		Player pvictim = (Player) lvictim;

		
		LocalTime now = LocalTime.now();
		if (lastInteraction.containsKey(pvictim.getUniqueId())) {
			long seconds = lastInteraction.get(pvictim.getUniqueId()).until(now, ChronoUnit.SECONDS);
		    if (seconds < 8)
		    	return;
			
		}
		lastInteraction.put(pvictim.getUniqueId(), now);

		String statKey = RoCPlayers.getMaxStat(pvictim);
		int maxStat = RoCPlayers.getStat(pvictim, statKey);
		RoCPlayers.setStat(pvictim, 0, statKey);
		pvictim.getLocation().getWorld().playSound(pvictim.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 0.5F);
		

    	BukkitRunnable resetStat = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		RoCPlayers.setStat(pvictim, maxStat, statKey);
	    		pvictim.getLocation().getWorld().playSound(pvictim.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 0.5F);
	    	}
	    };
	    resetStat.runTaskLater(RealmsOfChaos.instance, 20*5);
		
		
    }
}
