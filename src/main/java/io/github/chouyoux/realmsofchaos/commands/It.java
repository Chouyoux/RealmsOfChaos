package io.github.chouyoux.realmsofchaos.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.chouyoux.realmsofchaos.ruleset.EnchantRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.MaterialNamesRuleset;

public class It implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length > 3 ||args.length < 1 ) return false;
		
		if (args.length == 1) {
			if (Integer.valueOf(args[0]) < 1 || Integer.valueOf(args[0]) > 6) return false;
    		ItemStack item = new ItemStack(EnchantRuleset.materials.get(Integer.valueOf(args[0])-1), 1);
			ItemMeta m = item.getItemMeta();
			m.setDisplayName(MaterialNamesRuleset.names.get(item.getType()));
			item.setItemMeta(m);
			((Player) sender).getInventory().addItem(item);
		}
		
		if (args.length == 2) {
			if (!Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(args[0]))) return false;
			if (Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > 6) return false;
    		ItemStack item = new ItemStack(EnchantRuleset.materials.get(Integer.valueOf(args[1])-1), 1);
			ItemMeta m = item.getItemMeta();
			m.setDisplayName(MaterialNamesRuleset.names.get(item.getType()));
			item.setItemMeta(m);
			Bukkit.getServer().getPlayer(args[0]).getInventory().addItem(item);
		}
		
		if (args.length == 3) {
			if (!Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(args[0]))) return false;
			if (Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > 6) return false;
			if (Integer.valueOf(args[2]) < 0 || Integer.valueOf(args[1]) > 9999) return false;
    		ItemStack item = new ItemStack(EnchantRuleset.materials.get(Integer.valueOf(args[1])-1), Integer.valueOf(args[2]));
			ItemMeta m = item.getItemMeta();
			m.setDisplayName(MaterialNamesRuleset.names.get(item.getType()));
			item.setItemMeta(m);
			Bukkit.getServer().getPlayer(args[0]).getInventory().addItem(item);
		}

		return true;
	}
}