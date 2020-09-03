package io.github.chouyoux.realmsofchaos.ruleset;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RegionIconsRuleset {
	
	public static HashMap<String, Integer> regionCustomIDs;
	
	public RegionIconsRuleset() {
		regionCustomIDs = new HashMap<String, Integer>();
		regionCustomIDs.put("pateras", 1100);
		regionCustomIDs.put("marathon", 1200);
		regionCustomIDs.put("kyllene", 1300);
		regionCustomIDs.put("euphrate", 2100);
		regionCustomIDs.put("yehoud", 2200);
		regionCustomIDs.put("parthyene", 2300);
		regionCustomIDs.put("arabic-desert", 3100);
		regionCustomIDs.put("sinai", 3200);
		regionCustomIDs.put("fayoum", 3300);
	}
	
	public static ItemStack updateIcon(ItemStack item, String region) {
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setCustomModelData(regionCustomIDs.get(region));
		item.setItemMeta(itemmeta);
		return item;
	}

}
