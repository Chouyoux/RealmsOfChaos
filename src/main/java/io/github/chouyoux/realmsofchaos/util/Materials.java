package io.github.chouyoux.realmsofchaos.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.chouyoux.realmsofchaos.ruleset.EnchantRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.MaterialNamesRuleset;

public class Materials {
	
	public static ItemStack RoCItem(ItemStack item) {
		boolean isARoCItem = false;
		for (Material material : EnchantRuleset.materials)
			if (material.equals(item.getType()))
				isARoCItem = true;
		if (!isARoCItem) return item;

		ItemMeta m = item.getItemMeta();
		m.setDisplayName(MaterialNamesRuleset.names.get(item.getType()));
		item.setItemMeta(m);
		return item;
	}

}
