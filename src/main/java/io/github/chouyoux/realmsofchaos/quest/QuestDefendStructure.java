package io.github.chouyoux.realmsofchaos.quest;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class QuestDefendStructure extends Quest {

	public QuestDefendStructure(String faction, ArrayList<ItemStack> rewards) {
		super(faction, rewards, "Defend Structure", 1);
		objectiveKey = ChatColor.GREEN + " " + ChatColor.WHITE;
	}

}
