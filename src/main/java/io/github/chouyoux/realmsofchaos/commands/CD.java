package io.github.chouyoux.realmsofchaos.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.chouyoux.realmsofchaos.challenge.ChallengeEntrance;
import io.github.chouyoux.realmsofchaos.challenge.ChallengesList;

public class CD implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (args.length > 1) return false;
			Player target = (Player) sender;
			if (args.length == 1)
				target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) return false;
			
			for (ItemStack item : target.getInventory().getContents())
				if (item != null && item.getType() != Material.AIR)
					target.setCooldown(item.getType(), 0);
			
			for (ChallengeEntrance challengeEntrance : ChallengesList.getList())
				challengeEntrance.cooldowns.remove(target.getUniqueId());
			
			((Player) sender).sendMessage(target.getName() + " cooldowns have been reset.");
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
	        
	        return arguments;
		}
		
		return null;
	}
}