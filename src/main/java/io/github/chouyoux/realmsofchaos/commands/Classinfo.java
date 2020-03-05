package io.github.chouyoux.realmsofchaos.commands;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Classinfo implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (args.length > 0) return false;
			Player player = (Player) sender;

			String foundvalue = RoCPlayers.getClass(player);
	        
			if (foundvalue != "") Bukkit.broadcastMessage(player.getDisplayName()+" is a " + foundvalue + " !");
			else Bukkit.broadcastMessage(player.getDisplayName()+" doesn't have a class. Crap that's lame xD.");
		}
	
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String alias, @NotNull String[] args) {
		
		List<String> arguments = new ArrayList<String>();
		return arguments;
	}
}
