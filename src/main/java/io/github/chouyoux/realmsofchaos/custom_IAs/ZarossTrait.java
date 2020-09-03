package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("zarosstrait")
public class ZarossTrait extends Trait {

	RealmsOfChaos plugin = null;

    public ZarossTrait() {
        super("zarosstrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
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
		
		
    }
}