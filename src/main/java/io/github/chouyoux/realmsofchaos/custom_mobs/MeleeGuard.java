package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class MeleeGuard extends Guard {

	public MeleeGuard(Location spawnPoint, String faction) {
		super(0.5, 4, 40, 20, 20*60, spawnPoint, EntityType.VINDICATOR, "Melee Guard", faction, Material.STONE_AXE);
	}

}
