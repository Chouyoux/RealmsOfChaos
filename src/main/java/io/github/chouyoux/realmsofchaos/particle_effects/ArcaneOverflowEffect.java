package io.github.chouyoux.realmsofchaos.particle_effects;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.HelixEffect;
import io.github.chouyoux.realmsofchaos.gameplay.skills.ArcaneOverflow;

public class ArcaneOverflowEffect extends AnimatedBallEffect {

	public ArcaneOverflowEffect(EffectManager effectManager, Entity entity) {
		super(effectManager);
		setEntity(entity);
		asynchronous = true;
		particle = Particle.SPELL_WITCH;
		particles = 150;
		particlesPerIteration = 10;
		size = 1;
		xFactor = 1;
		yFactor = -1.25F;
		zFactor = 1;
		xOffset = 0;
		yOffset = -0.2F;
		zOffset = 0;
		xRotation = 0;
		yRotation = 0;
		zRotation = 0;
		iterations = 30;
		period = 1;
		type = EffectType.REPEATING;
		delay = 0;
		start();
		
		HelixEffect helix = new HelixEffect(effectManager);
		helix.setEntity(entity);
		helix.asynchronous = true;
		helix.particle = Particle.SPELL_WITCH;
		helix.particles = 300;
		helix.strands = 8;
		helix.radius = (float) ArcaneOverflow.skillRadius;
		helix.curve = (float) ArcaneOverflow.skillRadius;
		helix.rotation = 4;
		helix.iterations = 8;
		helix.period = 1;
		helix.type = EffectType.DELAYED;
		helix.delay = 30;
		helix.start();
		
	}
	

}
