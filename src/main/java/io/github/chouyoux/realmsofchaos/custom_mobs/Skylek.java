package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.SkylekTrait;

public class Skylek extends Mob {
	
	public Skylek(Location spawnPoint) {
		super(0, 12, 24, 0, 20*3, 1.2, spawnPoint, EntityType.VEX, "Skylek", Material.DIAMOND_AXE, MobType.SKYLEK);
		this.getNpc().setUseMinecraftAI(true);

		if (!getNpc().hasTrait(SkylekTrait.class)) {
			getNpc().addTrait(SkylekTrait.class);
        }
		getNpc().getTrait(SkylekTrait.class);
		
		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}
