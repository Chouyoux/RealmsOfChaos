package io.github.chouyoux.realmsofchaos.particle_effects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.DnaEffect;

public class FirePropagationHelixEffect extends DnaEffect {

	public FirePropagationHelixEffect(EffectManager effectManager, Location location) {
		super(effectManager);
		this.updateLocations = true;
		location.setDirection(new Vector(0, 1, 0));
		setLocation(location.clone().add(0, 0.2, 0));
		setTargetLocation(location.clone().add(0, 4, 0));
		particleHelix = Particle.FLAME;
		particleBase1 = Particle.FLAME;
		particleBase2 = Particle.FLAME;
		period = 1;
		radius = 0.8F;
		grow = 0.1F;
		particlesHelix = 30;
		particlesBase = 0;
		length = 4;
		visibleRange = 128;
		radials = 8;
		type = EffectType.REPEATING;
		delay = 1;
		iterations = 40;
		start();
	}
	
	

}
