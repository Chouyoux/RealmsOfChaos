package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.GuronTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;

public class Guron extends Mob {

	public Guron(Location spawnPoint) {
		super(0.75, 8, 40, 0, 20*2, 1.2, spawnPoint, EntityType.IRON_GOLEM, "Guron", Material.AIR, MobType.GURON);
		this.getNpc().setUseMinecraftAI(false);
		if (!getNpc().hasTrait(GuronTrait.class)) {
			getNpc().addTrait(GuronTrait.class);
        }
		getNpc().getTrait(GuronTrait.class);
		

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}