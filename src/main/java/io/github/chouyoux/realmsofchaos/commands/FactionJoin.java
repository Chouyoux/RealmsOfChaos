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

public class FactionJoin implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (args.length < 1) return false;
			if (args[0].compareTo("Egyptians") != 0 && args[0].compareTo("Greeks") != 0 && args[0].compareTo("Persians") != 0) return false;
			Player player = (Player) sender;

			RoCPlayers.setFaction(player, args[0]);

			Bukkit.broadcastMessage(player.getDisplayName()+" has joined the " + args[0] + " faction !");
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String alias, @NotNull String[] args) {
		
		List<String> arguments = new ArrayList<String>();

		if (args.length == 1) {
			arguments.add("Greeks");
			arguments.add("Persians");
			arguments.add("Egyptians");
			return arguments;
		}
		
		return arguments;
	}
	
}
