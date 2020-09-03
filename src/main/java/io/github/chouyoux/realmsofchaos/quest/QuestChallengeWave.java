package io.github.chouyoux.realmsofchaos.quest;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class QuestChallengeWave extends Quest {
	
	public String challengeEntranceName;

	public QuestChallengeWave(String faction, ArrayList<ItemStack> rewards, String challengeEntranceName) {
		super(faction, rewards, challengeEntranceName, 25);
		objectiveKey = ChatColor.WHITE + " " + ChatColor.WHITE;
		this.challengeEntranceName = challengeEntranceName;
	}


}
