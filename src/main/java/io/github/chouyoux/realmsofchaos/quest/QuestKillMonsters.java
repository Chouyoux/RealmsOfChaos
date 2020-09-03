package io.github.chouyoux.realmsofchaos.quest;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class QuestKillMonsters extends Quest {
	
	public EntityType monster;

	public QuestKillMonsters(String faction, ArrayList<ItemStack> rewards, int objectiveCount, EntityType monster) {
		super(faction, rewards, "Kill "+monster.toString()+"s", objectiveCount);
		objectiveKey = ChatColor.BLACK + " " + ChatColor.WHITE;
		this.monster = monster;
	}

}
