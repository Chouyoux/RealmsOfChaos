package io.github.chouyoux.realmsofchaos.particle_effects;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.TraceEffect;

public class HookTraceEffect extends TraceEffect {

	public HookTraceEffect(EffectManager effectManager, Entity entity) {
		super(effectManager);
		asynchronous = true;
		visibleRange = 128;
		setEntity(entity);
		particle = Particle.SLIME;
		disappearWithTargetEntity = true;
		disappearWithOriginEntity = true;
		autoOrient = true;
		particleData = 0;
		particleOffsetX = 0;
		particleOffsetY = 0;
		particleOffsetZ = 0;
		start();
	}

}
