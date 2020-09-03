package io.github.chouyoux.realmsofchaos.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.chouyoux.realmsofchaos.challenge.Challenge;
import io.github.chouyoux.realmsofchaos.challenge.ChallengeEntrance;
import io.github.chouyoux.realmsofchaos.challenge.ChallengesList;

public class Accept implements CommandExecutor {
	

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length != 1) return false;
		
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) return true;

		if (sender instanceof Player) {
			ChallengeEntrance entrance = ChallengesList.getPlayerChallengeEntrance(((Player) target));
			Challenge challenge = ChallengesList.getPlayerChallenge(((Player) target));
			if (entrance != null || challenge != null) {
				((Player) sender).sendMessage(target.getName()+" is already in a challenge.");
				return true;
			}

			ChallengeEntrance sender_entrance = ChallengesList.getPlayerChallengeEntrance(((Player) sender));
			Challenge sender_challenge = ChallengesList.getPlayerChallenge(((Player) sender));
			if (sender_entrance == null || sender_challenge == null) {
				((Player) sender).sendMessage("You are not in a challenge.");
				return true;
			}
			
			if (sender_entrance.joinLoc.distance(((Player) target).getLocation()) > 8) {
				((Player) sender).sendMessage("Your mate isn't close enough to the Join point.");
				target.sendMessage(((Player) sender).getName()+" has accepted your request, but you are too far from the Join point.");
				return true;
			}
			
			boolean success = false;
			for (Player key : Join.invites.keySet()) {
				if (Join.invites.get(key).getName().equals(((Player) sender).getName())) {
					sender_challenge.addPlayer(target);
					success = true;
				}
			}
			if (!success)
				((Player) sender).sendMessage("The request wasn't valid anymore (>"+String.valueOf(Join.delay)+" seconds).");
			

		}

		return true;
	}

}
