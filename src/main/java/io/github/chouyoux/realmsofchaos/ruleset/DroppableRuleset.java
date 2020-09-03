package io.github.chouyoux.realmsofchaos.ruleset;

import java.util.HashMap;

import org.bukkit.Material;

public class DroppableRuleset {
	
	public static HashMap<Material, Double> droppables;
	
	public DroppableRuleset() {
		droppables = new HashMap<Material, Double>();
		droppables.put(Material.GLOWSTONE_DUST, 0.5);
		droppables.put(Material.PRISMARINE_SHARD, 0.5);
		droppables.put(Material.PRISMARINE_CRYSTALS, 0.5);
		droppables.put(Material.QUARTZ, 0.5);
		droppables.put(Material.DIAMOND, 0.5);
	}

}
