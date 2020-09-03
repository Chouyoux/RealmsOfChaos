package io.github.chouyoux.realmsofchaos.particle_effects;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.TraceEffect;

public class TeleportTraceEffect extends TraceEffect {

	public TeleportTraceEffect(EffectManager effectManager, Entity entity) {
		super(effectManager);
		asynchronous = true;
		visibleRange = 128;
		setEntity(entity);
		particle = Particle.SPELL_WITCH;
		disappearWithTargetEntity = true;
		disappearWithOriginEntity = true;
		autoOrient = true;
		start();
	}

}
