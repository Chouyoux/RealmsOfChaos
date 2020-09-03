package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("khrokedaitrait")
public class KhrokedaiTrait extends Trait {

	RealmsOfChaos plugin = null;

    public KhrokedaiTrait() {
        super("khrokedaitrait");
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
		
		
		if (!npc.getEntity().getUniqueId().equals(ldamager.getUniqueId())) return;
		
		lvictim.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20*5, 0));
		lvictim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*5, 0));
		
		
    }
}