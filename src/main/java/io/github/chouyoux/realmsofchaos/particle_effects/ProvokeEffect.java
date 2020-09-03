package io.github.chouyoux.realmsofchaos.particle_effects;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ShieldEffect;

public class ProvokeEffect extends ShieldEffect {
	
	public ProvokeEffect(EffectManager effectManager, Entity entity) {
		super(effectManager);
		
		asynchronous = true;
		visibleRange = 128;
		particle = Particle.VILLAGER_ANGRY;
		particleCount = 10;
		iterations = 1;
		radius = (float) entity.getHeight();
		setEntity(entity);
		entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_CHAIN_PLACE, 0.4F, 0.5F);
		start();
	}
	 
} 