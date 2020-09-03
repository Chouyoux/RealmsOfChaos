package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.ElabusTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;

public class Elabus extends Mob {

	public Elabus(Location spawnPoint) {
		super(0.9, 4, 20, 0, 0, 0, spawnPoint, EntityType.SHULKER, "Elabus", Material.AIR, MobType.ELABUS);
		this.getNpc().setUseMinecraftAI(false);
		if (!getNpc().hasTrait(ElabusTrait.class)) {
			getNpc().addTrait(ElabusTrait.class);
        }
		getNpc().getTrait(ElabusTrait.class);

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}