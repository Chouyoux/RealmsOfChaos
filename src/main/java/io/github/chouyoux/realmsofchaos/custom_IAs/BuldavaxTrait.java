package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("buldavaxtrait")
public class BuldavaxTrait extends Trait {

	RealmsOfChaos plugin = null;

    public BuldavaxTrait() {
        super("buldavaxtrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
    }
    
    @EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		if (!(victim instanceof LivingEntity)) return;
		if (!npc.isSpawned()) return;
		if (!(event.getDamager() instanceof Projectile)) return;
		
		ProjectileSource src = ((Projectile) event.getDamager()).getShooter();
		if (!(src instanceof LivingEntity)) return;
		
		LivingEntity damager = (LivingEntity) src;
		
		if (!npc.getEntity().getUniqueId().equals(damager.getUniqueId())) return;
		
		event.getEntity().setVelocity(new Vector(0, -1, 0).multiply(0.5));
    }
}