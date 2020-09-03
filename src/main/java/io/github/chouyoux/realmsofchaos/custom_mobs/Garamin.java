package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.GaraminTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;
import net.citizensnpcs.trait.SlimeSize;

public class Garamin extends Mob {
	
	public static int damage = 4;

	public Garamin(Location spawnPoint) {
		super(0.5, damage, 12, 0, 20*2, 0, spawnPoint, EntityType.SLIME, "Garamin", Material.STONE_SWORD, MobType.GARAMIN);
		this.getNpc().setUseMinecraftAI(true);
		if (!getNpc().hasTrait(GaraminTrait.class)) {
			getNpc().addTrait(GaraminTrait.class);
        }
		getNpc().getTrait(GaraminTrait.class);
		
		if (!getNpc().hasTrait(SlimeSize.class)) {
			getNpc().addTrait(SlimeSize.class);
        }
		SlimeSize size = getNpc().getTrait(SlimeSize.class);
		size.setSize(1);

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}