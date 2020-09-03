package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.ruleset.PatrollersRuleset;

public class MeleePatroller extends Patroller {

	public MeleePatroller(Location spawnPoint, String faction, String structure) {
		super(0.5, 4, 30, 30, 20*60, spawnPoint, EntityType.VINDICATOR, "Melee Patroller", faction, Material.STONE_AXE, structure);
		if (PatrollersRuleset.structuresPatrolls.containsKey(structure))
			for (Location loc : PatrollersRuleset.structuresPatrolls.get(structure))
				addWaypoint(loc);
		setSpawnPoint(null);
	}

}