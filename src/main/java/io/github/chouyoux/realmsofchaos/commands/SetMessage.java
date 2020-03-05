package io.github.chouyoux.realmsofchaos.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.chouyoux.realmsofchaos.data_handlers.YMLFiles;

public class SetMessage implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (args.length < 1) return false;
			String message = args[0];
			if (args.length > 1) for (int i = 1; i < args.length; i++) message += " "+args[i];
			YMLFiles.get("world_state").set("Message", message);
			YMLFiles.save("world_state");
		}
		
		return true;
	}
	
}
