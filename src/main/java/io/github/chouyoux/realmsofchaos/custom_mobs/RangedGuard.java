package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class RangedGuard extends Guard {

	public RangedGuard(Location spawnPoint, String faction) {
		super(0.40, 6, 35, 40, 20*60, spawnPoint, EntityType.PILLAGER, "Ranged Guard", faction, Material.CROSSBOW);
	}

}