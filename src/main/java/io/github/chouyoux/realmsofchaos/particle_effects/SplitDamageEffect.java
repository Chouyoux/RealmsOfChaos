package io.github.chouyoux.realmsofchaos.particle_effects;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.LineEffect;

public class SplitDamageEffect extends LineEffect {

	public SplitDamageEffect(EffectManager effectManager, Entity source, Entity target) {
		super(effectManager);
		setEntity(source);
		setTargetEntity(target);
		asynchronous = true;
		visibleRange = 128;
		particle = Particle.REDSTONE;
		particleCount = (int) source.getLocation().distance(target.getLocation())/2;
		iterations = 1;
		start();
	}

}
