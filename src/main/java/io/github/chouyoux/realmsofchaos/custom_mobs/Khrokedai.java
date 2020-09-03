package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.KhrokedaiTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;

public class Khrokedai extends Mob {

	public Khrokedai(Location spawnPoint) {
		super(0.1, 2, 12, 20*2, 10, 1.5, spawnPoint, EntityType.CAVE_SPIDER, "Khrokedai", Material.AIR, MobType.KHROKEDAI);
		this.getNpc().setUseMinecraftAI(true);
		if (!getNpc().hasTrait(KhrokedaiTrait.class)) {
			getNpc().addTrait(KhrokedaiTrait.class);
        }
		getNpc().getTrait(KhrokedaiTrait.class);
		

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}