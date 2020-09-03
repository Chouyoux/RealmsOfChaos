package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.BuldavaxTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;

public class Buldavax extends Mob {

	public Buldavax(Location spawnPoint) {
		super(0.4, 7, 16, 0, 20*2, 1.25, spawnPoint, EntityType.DROWNED, "Buldavax", Material.TRIDENT, MobType.BULDAVAX);
		this.getNpc().setUseMinecraftAI(true);
		if (!getNpc().hasTrait(BuldavaxTrait.class)) {
			getNpc().addTrait(BuldavaxTrait.class);
        }
		getNpc().getTrait(BuldavaxTrait.class);
		

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}