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

public class Classleave implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			if (args.length > 0) return false;
			Player player = (Player) sender;
			
			String old_class = RoCPlayers.getClass(player);
	        RoCPlayers.setClass(player, "");
	        
			if (old_class != "") Bukkit.broadcastMessage(player.getDisplayName()+" is not a "+old_class+" anymore !");
			else Bukkit.broadcastMessage(player.getDisplayName()+" tried to quit his class but he doesn't have one ! (hella stupid)");
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
