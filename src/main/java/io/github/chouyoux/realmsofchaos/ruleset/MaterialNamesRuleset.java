package io.github.chouyoux.realmsofchaos.ruleset;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class MaterialNamesRuleset {
	
	public static HashMap<Material, String> names;
	
	public MaterialNamesRuleset() {
		names = new HashMap<Material, String>();
		
		names.put(Material.GLOWSTONE_DUST, ChatColor.RESET+"Chaos Dust");
		names.put(Material.PRISMARINE_SHARD, ChatColor.LIGHT_PURPLE+"Chaos Shard");
		names.put(Material.PRISMARINE_CRYSTALS, ChatColor.LIGHT_PURPLE+"Chaos Crystals");
		names.put(Material.QUARTZ, ChatColor.DARK_PURPLE+"Chaos Gem");
		names.put(Material.DIAMOND, ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Chaos Source");
		names.put(Material.EMERALD, ChatColor.DARK_RED+""+ChatColor.BOLD+"Soul");
	}

}
