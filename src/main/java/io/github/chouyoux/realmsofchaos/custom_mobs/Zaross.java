package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.ZarossTrait;

public class Zaross extends Mob {

	public Zaross(Location spawnPoint) {
		super(0.5, 16, 40, 20*2, 20*5, 1, spawnPoint, EntityType.STRAY, "Zaross", Material.AIR, MobType.ZAROSS);
		this.getNpc().setUseMinecraftAI(true);
		if (!getNpc().hasTrait(ZarossTrait.class)) {
			getNpc().addTrait(ZarossTrait.class);
        }
		getNpc().getTrait(ZarossTrait.class);
		

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}
