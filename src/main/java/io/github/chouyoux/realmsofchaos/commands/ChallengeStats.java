package io.github.chouyoux.realmsofchaos.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.chouyoux.realmsofchaos.challenge.Challenge;
import io.github.chouyoux.realmsofchaos.challenge.ChallengesList;

public class ChallengeStats implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length != 1) return false;
		Player player = Bukkit.getServer().getPlayer(args[0]);
		if (player == null) return true;
		
		Challenge challenge = ChallengesList.getPlayerChallenge(player);
		if (challenge == null)
			sender.sendMessage(player.getName()+" isn't in a Challenge right now.");
		else {
			sender.sendMessage(player.getName()+"'s Challenge instantiated in the map "+challenge.getWorld().getName()+" :");
			sender.sendMessage("• Waves : " + challenge.getCurrentWave());
			sender.sendMessage("• Time in : " + challenge.getTimeIn());
			sender.sendMessage("• Death count : " + challenge.getDeathCount());
			sender.sendMessage("• Kill count : " + challenge.getKills());
			sender.sendMessage("• Damage Taken : " + challenge.getDamageTaken());
			sender.sendMessage("• Damage Dealt : " + challenge.getDamageDealt());
			sender.sendMessage("• Heal Dealt : " + challenge.getHealDealt());
		}

		return true;
	}
}