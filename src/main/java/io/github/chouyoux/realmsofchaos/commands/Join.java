package io.github.chouyoux.realmsofchaos.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.challenge.Challenge;
import io.github.chouyoux.realmsofchaos.challenge.ChallengeEntrance;
import io.github.chouyoux.realmsofchaos.challenge.ChallengesList;
import io.github.chouyoux.realmsofchaos.util.ChatFormat;
import net.md_5.bungee.api.ChatColor;

public class Join implements CommandExecutor {
	
	public static int delay = 30;
	public static HashMap<Player, Player> invites = new HashMap<Player, Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length != 1) return false;
		
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) return true;

		if (sender instanceof Player) {
			ChallengeEntrance entrance = ChallengesList.getPlayerChallengeEntrance(((Player) target));
			Challenge challenge = ChallengesList.getPlayerChallenge(((Player) target));
			if (entrance == null || challenge == null) return true;
			if (entrance.joinLoc.distance(((Player) sender).getLocation()) > 8) return true;
			if (invites.containsKey((Player) sender)) {
				((Player) sender).sendMessage("You may only request invitation every "+String.valueOf(delay)+" seconds.");
				return true;
			}
			
			invites.put((Player) sender, target);
			target.sendMessage(ChatFormat.HoverMessage("["+((Player) sender).getName()+" wants to join your Challenge]", ChatColor.WHITE, "/accept "+((Player) sender).getName(), "Accepts the request"));
			((Player) sender).sendMessage("Your invitation request to "+target.getName()+" has been sent.");
			
			BukkitRunnable task = new BukkitRunnable() {
		    	@Override
		        public void run() {
		    		invites.remove((Player) sender);
		    	}
		    };
	    	task.runTaskLater(RealmsOfChaos.getInstance(), delay*20);
		}

		return true;
	}

}
