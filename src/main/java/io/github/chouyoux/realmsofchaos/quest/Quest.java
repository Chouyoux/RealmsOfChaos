package io.github.chouyoux.realmsofchaos.quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Quest {
	
	public ArrayList<ItemStack> rewards;
	public String objectiveName;
	public int objectiveCount;
	public String objectiveKey;

	public String faction;
	
	protected HashMap<UUID, Integer> playersProgress;

	public Quest(String faction, ArrayList<ItemStack> rewards, String objectiveName, int objectiveCount) {
		super();
		this.rewards = rewards;
		this.objectiveName = objectiveName;
		this.objectiveCount = objectiveCount;
		this.faction = faction;
		playersProgress = new HashMap<UUID, Integer>();
	}
	
	public void addPlayer(Player player) {
		playersProgress.put(player.getUniqueId(), 0);
		QuestRegistry.instance.createObjective(player, objectiveName, ChatColor.GOLD+objectiveName+" : "+ChatColor.YELLOW+"0 / "+objectiveCount, objectiveKey);
	}
	
	public boolean hasPlayer(Player player) {
		return playersProgress.containsKey(player.getUniqueId());
	}
	
	public boolean isDone(Player player) {
		return hasPlayer(player) && playersProgress.get(player.getUniqueId()) >= objectiveCount;
	}
	
	public boolean hasBeenRewarded(Player player) {
		return hasPlayer(player) && playersProgress.get(player.getUniqueId()) == -1;
	}
	
	public void removePlayer(Player player) {
		playersProgress.remove(player.getUniqueId());
		QuestRegistry.instance.deleteObjective(player, objectiveName);
	}
	
	public void removeAllPlayer() {
		for (UUID uuid : playersProgress.keySet()) {
			Player player = Bukkit.getServer().getPlayer(uuid);
			removePlayer(player);
		}
	}
	
	public void grantReward(Player player) {
		playersProgress.put(player.getUniqueId(), -1);
	}
	
	public void addProgress(Player player, int add) {
		if (!hasPlayer(player)) return;
		playersProgress.put(player.getUniqueId(), playersProgress.get(player.getUniqueId())+add);
		QuestRegistry.instance.updateObjective(player, objectiveName, ChatColor.GOLD+objectiveName+" : "+ChatColor.YELLOW+Math.min(objectiveCount, playersProgress.get(player.getUniqueId()))+" / "+objectiveCount);
	}
	
	public void setProgress(Player player, int set) {
		if (!hasPlayer(player)) return;
		playersProgress.put(player.getUniqueId(), set);
		QuestRegistry.instance.updateObjective(player, objectiveName, ChatColor.GOLD+objectiveName+" : "+ChatColor.YELLOW+Math.min(objectiveCount, playersProgress.get(player.getUniqueId()))+" / "+objectiveCount);
	}
	
	
	
	
	
	
	

}
