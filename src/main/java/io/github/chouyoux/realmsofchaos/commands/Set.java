package io.github.chouyoux.realmsofchaos.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.ruleset.Ruleset;

public class Set implements TabExecutor {
	
	private HashMap<String, PersistentDataType> availableKeys;
	
	public Set() {
		availableKeys = new HashMap<String, PersistentDataType>();
		availableKeys.put("Coins", PersistentDataType.INTEGER);
		availableKeys.put("StatPoints", PersistentDataType.INTEGER);
		availableKeys.put("STR", PersistentDataType.INTEGER);
		availableKeys.put("DEX", PersistentDataType.INTEGER);
		availableKeys.put("INT", PersistentDataType.INTEGER);
		availableKeys.put("VIT", PersistentDataType.INTEGER);
		availableKeys.put("FAI", PersistentDataType.INTEGER);
		availableKeys.put("Class", PersistentDataType.STRING);
		availableKeys.put("Faction", PersistentDataType.STRING);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (args.length != 3) return false;
			Player player = (Player) sender;
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) return false;
			
			if (availableKeys.get(args[1]) == PersistentDataType.INTEGER)
				try {
					Integer.valueOf(args[2]);
				}
				catch (NumberFormatException e) {
					return false;
				}
			
			if (args[1].equals("Class") && Ruleset.availableClasses.contains(args[2]))
				RoCPlayers.setClass(target, args[2]);
			
			else if (args[1].equals("Faction") && Ruleset.availableFactions.contains(args[2]))
				RoCPlayers.setFaction(target, args[2]);
			
			else if (args[1].equals("StatPoints") && Integer.valueOf(args[2]) < 120 && Integer.valueOf(args[2]) >= 0)
				RoCPlayers.setStatPoints(target, Integer.valueOf(args[2]));
			
			else if (args[1].equals("STR") && Integer.valueOf(args[2]) < 120 && Integer.valueOf(args[2]) >= 0)
				RoCPlayers.setSTR(target, Integer.valueOf(args[2]));
			
			else if (args[1].equals("DEX") && Integer.valueOf(args[2]) < 120 && Integer.valueOf(args[2]) >= 0)
				RoCPlayers.setDEX(target, Integer.valueOf(args[2]));
			
			else if (args[1].equals("INT") && Integer.valueOf(args[2]) < 120 && Integer.valueOf(args[2]) >= 0)
				RoCPlayers.setINT(target, Integer.valueOf(args[2]));
			
			else if (args[1].equals("VIT") && Integer.valueOf(args[2]) < 120 && Integer.valueOf(args[2]) >= 0)
				RoCPlayers.setVIT(target, Integer.valueOf(args[2]));
			
			else if (args[1].equals("FAI") && Integer.valueOf(args[2]) < 120 && Integer.valueOf(args[2]) >= 0)
				RoCPlayers.setFAI(target, Integer.valueOf(args[2]));
			
			else if (args[1].equals("Coins") && Integer.valueOf(args[2]) >= 0)
				RoCPlayers.setCoins(target, Integer.valueOf(args[2]));

			else
				return false;
			
			if (!player.getName().equals(target.getName()))
				player.sendMessage(args[0] + "'s " + args[1] + " has been set to " + args[2] + ".");
			target.sendMessage("Your " + args[1] + " has been set to " + args[2] + ".");
		}
	
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String alias, @NotNull String[] args) {
		
		List<String> arguments = new ArrayList<String>();
		
		if (args.length == 1) {
			Collection<? extends Player> online_players = Bukkit.getServer().getOnlinePlayers();
			
			@SuppressWarnings("unchecked")
			Iterator<Player> iterator = (Iterator<Player>) online_players.iterator();
			 
	        // while loop
	        while (iterator.hasNext()) {
	        	arguments.add(iterator.next().getName());
	        }
		}
		
		else if (args.length == 2)
			for (String s : availableKeys.keySet())
				arguments.add(s);
		
		else if (args.length == 3)
			if (availableKeys.get(args[1]) != null)
				if (availableKeys.get(args[1]).equals(PersistentDataType.STRING))
					if (args[1].equals("Class"))
						for (String s : Ruleset.availableClasses)
								arguments.add(s);
					else if (args[1].equals("Faction"))
						for (String s : Ruleset.availableFactions)
								arguments.add(s);
					else
						arguments.add("STRING");
				else if (availableKeys.get(args[1]).equals(PersistentDataType.INTEGER))
					arguments.add("INTEGER");
		
		return arguments;
	}
}