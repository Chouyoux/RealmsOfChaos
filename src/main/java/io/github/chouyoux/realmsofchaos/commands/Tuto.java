package io.github.chouyoux.realmsofchaos.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tuto implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) 
			((Player) sender).teleport(new Location(Bukkit.getWorld("Realms"), 238.5, 157, 661.5, -90, 0));

		return true;
	}
}