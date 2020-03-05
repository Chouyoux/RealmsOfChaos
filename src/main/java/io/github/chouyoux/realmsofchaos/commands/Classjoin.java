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

public class Classjoin implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (args.length < 1) return false;
			if (args[0].compareTo("Warrior") != 0 && args[0].compareTo("Archer") != 0 && args[0].compareTo("Magician") != 0 && args[0].compareTo("Healer") != 0&& args[0].compareTo("Duelist") != 0) return false;
			Player player = (Player) sender;

			RoCPlayers.setClass(player, args[0]);

			Bukkit.broadcastMessage(player.getDisplayName()+" is now a " + args[0] + " !");
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String alias, @NotNull String[] args) {
		
		List<String> arguments = new ArrayList<String>();

		if (args.length == 1) {
			arguments.add("Warrior");
			arguments.add("Archer");
			arguments.add("Magician");
			arguments.add("Healer");
			arguments.add("Duelist");
			return arguments;
		}
		
		return arguments;
	}
	
}
