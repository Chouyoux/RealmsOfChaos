package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.BerociusTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;

public class Berocius extends Mob {

	public Berocius(Location spawnPoint) {
		super(0.6, 4, 30, 0, 20*10, 1.2, spawnPoint, EntityType.RAVAGER, "Berocius", Material.AIR, MobType.BEROCIUS);
		this.getNpc().setUseMinecraftAI(false);
		if (!getNpc().hasTrait(BerociusTrait.class)) {
			getNpc().addTrait(BerociusTrait.class);
        }
		getNpc().getTrait(BerociusTrait.class);
		

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}