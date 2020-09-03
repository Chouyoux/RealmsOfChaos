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

public class Get implements TabExecutor {
	
	private HashMap<String, PersistentDataType> availableKeys;
	
	public Get() {
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
		availableKeys.put("YVelocity", PersistentDataType.STRING);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (args.length != 2) return false;
			Player player = (Player) sender;
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) return false;
			
			String value = "";
			
			if (args[1].equals("Class"))
				value = RoCPlayers.getClass(target);
			
			else if (args[1].equals("Faction"))
				value = RoCPlayers.getFaction(target);
			
			else if (args[1].equals("StatPoints"))
				value = String.valueOf(RoCPlayers.getStatPoints(target));
			
			else if (args[1].equals("STR"))
				value = String.valueOf(RoCPlayers.getSTR(target));
			
			else if (args[1].equals("DEX"))
				value = String.valueOf(RoCPlayers.getDEX(target));
			
			else if (args[1].equals("INT"))
				value = String.valueOf(RoCPlayers.getINT(target));
			
			else if (args[1].equals("VIT"))
				value = String.valueOf(RoCPlayers.getVIT(target));
			
			else if (args[1].equals("FAI"))
				value = String.valueOf(RoCPlayers.getFAI(target));
			
			else if (args[1].equals("Coins"))
				value = String.valueOf(RoCPlayers.getCoins(target));
			
			else if (args[1].equals("YVelocity"))
				value = String.valueOf(target.getVelocity().getY());
			
			else
				return false;
			
			player.sendMessage(args[0] + "'s " + args[1] + " is " + value + ".");
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
		
		return arguments;
	}
}