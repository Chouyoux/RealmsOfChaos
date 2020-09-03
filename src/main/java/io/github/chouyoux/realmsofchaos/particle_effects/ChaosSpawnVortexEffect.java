package io.github.chouyoux.realmsofchaos.particle_effects;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.WarpEffect;

public class ChaosSpawnVortexEffect extends WarpEffect {
	
	public ChaosSpawnVortexEffect(EffectManager effectManager, Entity entity) {
		super(effectManager);
		
		asynchronous = true;
		visibleRange = 128;
		particle = Particle.LANDING_OBSIDIAN_TEAR;
		iterations = 5;
		grow = (float) entity.getHeight() / iterations;
		radius = (float) entity.getWidth();
		setEntity(entity);
		entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1F, 0.5F);
		start();
	}
	 
} 