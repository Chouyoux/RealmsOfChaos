package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.AzuthotTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;

public class Azuthot extends Mob {

	public Azuthot(Location spawnPoint) {
		super(0.4, 7, 16, 0, 20*4, 0.8, spawnPoint, EntityType.BLAZE, "Azuthot", Material.BLAZE_ROD, MobType.AZUTHOT);
		this.getNpc().setUseMinecraftAI(true);
		if (!getNpc().hasTrait(AzuthotTrait.class)) {
			getNpc().addTrait(AzuthotTrait.class);
        }
		getNpc().getTrait(AzuthotTrait.class);
		

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}
