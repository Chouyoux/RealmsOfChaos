package io.github.chouyoux.realmsofchaos.particle_effects;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.ShieldEffect;

public class GuronShieldEffect extends ShieldEffect {
	
	public GuronShieldEffect(EffectManager effectManager, Entity entity) {
		super(effectManager);
		
		asynchronous = true;
		visibleRange = 128;
		particle = Particle.DRIPPING_OBSIDIAN_TEAR;
		particleCount = 20;
		radius = (float) entity.getHeight();
		setEntity(entity);
		type = EffectType.REPEATING;
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_IRON_GOLEM_REPAIR, 1F, 0.5F);
		start();
	}
	 
} 