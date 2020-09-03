package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.AntalockTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;

public class Antalock extends Mob {

	public Antalock(Location spawnPoint) {
		super(0.2, 5, 10, 20, 20, 1.35, spawnPoint, EntityType.GUARDIAN, "Antalock", Material.AIR, MobType.ANTALOCK);
		this.getNpc().setUseMinecraftAI(true);
		
		
		if (!getNpc().hasTrait(AntalockTrait.class)) {
			getNpc().addTrait(AntalockTrait.class);
        }
		getNpc().getTrait(AntalockTrait.class);
		

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
		
	}

}