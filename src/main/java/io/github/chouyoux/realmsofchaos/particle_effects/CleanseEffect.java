package io.github.chouyoux.realmsofchaos.particle_effects;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ShieldEffect;

public class CleanseEffect extends ShieldEffect {
	
	public CleanseEffect(EffectManager effectManager, Entity entity) {
		super(effectManager);
		
		asynchronous = true;
		visibleRange = 128;
		particle = Particle.END_ROD;
		particleCount = 30;
		iterations = 1;
		radius = (float) entity.getHeight();
		setEntity(entity);
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F, 0.5F);
		start();
	}
	 
} 