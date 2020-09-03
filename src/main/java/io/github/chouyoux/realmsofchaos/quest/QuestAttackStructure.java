package io.github.chouyoux.realmsofchaos.quest;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class QuestAttackStructure extends Quest {
	
	public String structureType;

	public QuestAttackStructure(String faction, ArrayList<ItemStack> rewards, String structureType) {
		super(faction, rewards, "Attack "+structureType, 1);
		if (structureType.equals("Watchtower"))
			this.objectiveName = "Attack Tower";
		this.structureType = structureType;
		objectiveKey = ChatColor.YELLOW + " " + ChatColor.WHITE;
	}

}
