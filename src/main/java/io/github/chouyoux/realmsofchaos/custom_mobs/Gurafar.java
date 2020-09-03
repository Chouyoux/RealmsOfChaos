package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.GurafarTrait;
import io.github.chouyoux.realmsofchaos.custom_IAs.MustTargetTrait;
import net.citizensnpcs.trait.SlimeSize;

public class Gurafar extends Mob {

	public Gurafar(Location spawnPoint) {
		super(0.5, 5, 12, 0, 20*4, 0, spawnPoint, EntityType.MAGMA_CUBE, "Gurafar", Material.BLAZE_ROD, MobType.GURAFAR);
		this.getNpc().setUseMinecraftAI(true);
		if (!getNpc().hasTrait(GurafarTrait.class)) {
			getNpc().addTrait(GurafarTrait.class);
        }
		getNpc().getTrait(GurafarTrait.class);
		
		if (!getNpc().hasTrait(SlimeSize.class)) {
			getNpc().addTrait(SlimeSize.class);
        }
		SlimeSize size = getNpc().getTrait(SlimeSize.class);
		size.setSize(5);

		if (!getNpc().hasTrait(MustTargetTrait.class)) {
			getNpc().addTrait(MustTargetTrait.class);
        }
		getNpc().getTrait(MustTargetTrait.class);
	}

}