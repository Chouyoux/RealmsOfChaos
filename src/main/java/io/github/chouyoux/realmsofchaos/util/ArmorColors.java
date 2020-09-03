package io.github.chouyoux.realmsofchaos.util;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;

public class ArmorColors {

	// only call that if the player wears leather armor
	public static void updateArmorColor(Player p, String faction) {
		PlayerInventory inv = p.getInventory();
		ArrayList<ItemStack> leather_pieces = new ArrayList<ItemStack>();
		leather_pieces.add(inv.getHelmet());
		leather_pieces.add(inv.getChestplate());
		leather_pieces.add(inv.getLeggings());
		leather_pieces.add(inv.getBoots());
		for (ItemStack leather_piece : leather_pieces) {
			LeatherArmorMeta meta = (LeatherArmorMeta) leather_piece.getItemMeta();
			meta.setColor(FactionRuleset.factionArmorColors.get(RoCPlayers.getFaction(p)));
			leather_piece.setItemMeta(meta);
		}
	}
	
	public static void updateArmorColor(Horse h, String faction) {
		HorseInventory inv = h.getInventory();
		ItemStack leather_piece = (inv.getArmor());
		LeatherArmorMeta meta = (LeatherArmorMeta) leather_piece.getItemMeta();
		if (faction.equals("Greeks"))
			meta.setColor(Color.RED);
		if (faction.equals("Persians"))
			meta.setColor(Color.AQUA);
		if (faction.equals("Egyptians"))
			meta.setColor(Color.ORANGE);
		leather_piece.setItemMeta(meta);
	}
	
	public static void updateInventoryFaction(Player p) {
		for (ItemStack item : p.getInventory())
			updateItemFaction(item, RoCPlayers.getFaction(p));
	}
	
	public static void updateItemFaction(ItemStack item, String faction) {
		if (item == null) return;
		if (!(item.getType() == Material.STONE_SWORD || item.getType() == Material.STONE_AXE || item.getType() == Material.DIAMOND_PICKAXE || item.getType() == Material.STICK || item.getType() == Material.BAMBOO)) return;
		ItemMeta itemmeta = item.getItemMeta();
		if (faction.equals("Greeks"))
			itemmeta.setCustomModelData(1000);
		if (faction.equals("Persians"))
			itemmeta.setCustomModelData(2000);
		if (faction.equals("Egyptians"))
			itemmeta.setCustomModelData(3000);
		item.setItemMeta(itemmeta);
	}
	
	public static void updateArmorFaction(ItemStack item, String faction) {
		if (item == null) return;
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		if (faction.equals("Greeks"))
			meta.setColor(Color.RED);
		if (faction.equals("Persians"))
			meta.setColor(Color.AQUA);
		if (faction.equals("Egyptians"))
			meta.setColor(Color.ORANGE);
		item.setItemMeta(meta);
	}
	
}
