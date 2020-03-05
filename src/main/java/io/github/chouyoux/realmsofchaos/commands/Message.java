package io.github.chouyoux.realmsofchaos.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.chouyoux.realmsofchaos.data_handlers.YMLFiles;

public class Message implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (args.length > 0) return false;
			Player player = (Player) sender;
			player.sendMessage(YMLFiles.get("world_state").getString("Message"));
		}
		
		return true;
	}
	
}
