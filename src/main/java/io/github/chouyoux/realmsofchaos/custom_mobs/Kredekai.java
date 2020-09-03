package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.KredekaiTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;

public class Kredekai extends Mob {

	public Kredekai(Location spawnPoint) {
		super(0.2, 3, 22, 20*2, 15, 1.35, spawnPoint, EntityType.SPIDER, "Kredekai", Material.AIR, MobType.KREDEKAI);
		this.getNpc().setUseMinecraftAI(true);
		if (!getNpc().hasTrait(KredekaiTrait.class)) {
			getNpc().addTrait(KredekaiTrait.class);
        }
		getNpc().getTrait(KredekaiTrait.class);
		

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}