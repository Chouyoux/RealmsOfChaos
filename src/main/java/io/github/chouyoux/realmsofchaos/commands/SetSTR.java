package io.github.chouyoux.realmsofchaos.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;

public class SetSTR implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			if (args.length < 1) return false;
			Player player = (Player) sender;
			int value = 0;
			try {
				value = Integer.parseInt(args[0]);
			}
			catch(Exception e) {
				return false;
			}
			RoCPlayers.setSTR(player, value);
			
			RoCPlayers.updateAllAttributes(player);
		}

		return true;
	}
}
