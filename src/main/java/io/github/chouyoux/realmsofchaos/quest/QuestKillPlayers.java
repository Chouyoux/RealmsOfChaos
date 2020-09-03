package io.github.chouyoux.realmsofchaos.quest;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import io.github.chouyoux.realmsofchaos.ruleset.GradesRuleset;

public class QuestKillPlayers extends Quest {
	
	public int grade;

	public QuestKillPlayers(String faction, ArrayList<ItemStack> rewards, int objectiveCount, int grade) {
		super(faction, rewards, "Kill "+GradesRuleset.grades.get(grade)+"s", objectiveCount);
		objectiveKey = ChatColor.RED + " " + ChatColor.WHITE;
		this.grade = grade;
	}

}
