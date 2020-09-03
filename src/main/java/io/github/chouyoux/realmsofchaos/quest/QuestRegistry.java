package io.github.chouyoux.realmsofchaos.quest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.challenge.ChallengesList;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.memory.Factions;
import io.github.chouyoux.realmsofchaos.ruleset.EnchantRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import io.github.chouyoux.realmsofchaos.util.Materials;

public class QuestRegistry {
    public static QuestRegistry instance;
	
	public static ArrayList<Quest> quests = new ArrayList<Quest>();
	public static HashMap<UUID, Scoreboard> playersScoreboards =  new HashMap<UUID, Scoreboard>();
	public static HashMap<UUID, Integer> playersQuestsGiven =  new HashMap<UUID, Integer>();
	
	private ArrayList<EntityType> questsMonsters;
	private ArrayList<String> questsStructures;
	private ArrayList<String> questsChallenges;
	
	private int lastHourUpdate;
	
	public QuestRegistry() {
		instance = this;
		for (Player player : Bukkit.getServer().getOnlinePlayers())
			onJoin(player);
		
		questsMonsters = new ArrayList<EntityType>();
		questsMonsters.add(EntityType.GHAST);
		questsMonsters.add(EntityType.DROWNED);
		questsMonsters.add(EntityType.GUARDIAN);
		questsMonsters.add(EntityType.IRON_GOLEM);
		questsStructures = new ArrayList<String>();
		questsStructures.add("Stable");
		questsStructures.add("Watchtower");
		questsStructures.add("Fort");
		questsStructures.add("Castle");
		questsChallenges = new ArrayList<String>();
		questsChallenges.add("Euphrate_Lake");
		
		updateQuests();
		lastHourUpdate = Calendar.getInstance().get(Calendar.HOUR);
		BukkitRunnable updateOneHour = new BukkitRunnable() {
	    	@Override
	        public void run() {
        		if (lastHourUpdate != Calendar.getInstance().get(Calendar.HOUR)) {
        			updateQuests();
        			lastHourUpdate = Calendar.getInstance().get(Calendar.HOUR);
        		}
        		if (Calendar.getInstance().get(Calendar.HOUR) == 0) {
        			playersQuestsGiven.clear();
        		}
	    	}
	    };
	    updateOneHour.runTaskTimer(RealmsOfChaos.instance, 0, 20*60);
	}
	
	public void addQuestGiven(Player player) {
		if (playersQuestsGiven.containsKey(player.getUniqueId()))
			playersQuestsGiven.put(player.getUniqueId(), playersQuestsGiven.get(player.getUniqueId())+1);
		else
			playersQuestsGiven.put(player.getUniqueId(), 1);
	}
	
	public boolean hasReachedMaxQuests(Player player) {
		if (playersQuestsGiven.containsKey(player.getUniqueId()))
			return playersQuestsGiven.get(player.getUniqueId()) >= 5;
		else
			return false;
	}
	
	private void createPlayerScoreboard(Player player) {
		ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective("quests", "dummy", FactionRuleset.factionChatNameColors.get(RoCPlayers.getFaction(player)) + "=== QUESTS ===");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		playersScoreboards.put(player.getUniqueId(), scoreboard);
	}
	
	public void createObjective(Player player, String objName, String objDisplay, String objKey) {
		Scoreboard scoreboard = playersScoreboards.get(player.getUniqueId());
		Objective objective = scoreboard.getObjective("quests");
		Team team = scoreboard.registerNewTeam(objName);
		team.addEntry(objKey);
		team.setPrefix(objDisplay);
		objective.getScore(objKey).setScore(getNbObjectives(player));
	}
	
	public void updateObjective(Player player, String objName, String objDisplay) {
		Scoreboard scoreboard = playersScoreboards.get(player.getUniqueId());
		Team team = scoreboard.getTeam(objName);
		if (team == null) return;
		team.setPrefix(objDisplay);
	}

	public void deleteObjective(Player player, String objName) {
		Scoreboard scoreboard = playersScoreboards.get(player.getUniqueId());
		Team team = scoreboard.getTeam(objName);
		team.unregister();
	}
	
	public int getNbObjectives(Player player) {
		return playersScoreboards.get(player.getUniqueId()).getTeams().size();
	}
	
	public ArrayList<Quest> getFactionQuests(String faction){
		ArrayList<Quest> ret = new ArrayList<Quest>();
		for (Quest quest : quests)
			if (quest.faction.equals(faction))
				ret.add(quest);
		return ret;
	}
	
	public void onJoin(Player player) {
		if (!playersScoreboards.containsKey(player.getUniqueId()))
			createPlayerScoreboard(player);
		
		BukkitRunnable checkScoreboardStatusTask = new BukkitRunnable() {
	    	@Override
	        public void run() {
        		if (getNbObjectives(player) > 0 && ChallengesList.getPlayerChallenge(player) == null && !player.getScoreboard().equals(playersScoreboards.get(player.getUniqueId())))
        			player.setScoreboard(playersScoreboards.get(player.getUniqueId()));
        		if (getNbObjectives(player) <= 0 && ChallengesList.getPlayerChallenge(player) == null && !player.getScoreboard().equals(null))
        			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	    	}
	    };
	    checkScoreboardStatusTask.runTaskTimer(RealmsOfChaos.instance, 0, 20);
		
	}
	
	private void updateQuests() {
		if (quests.size() > 0)
			for (Quest quest : quests)
				quest.removeAllPlayer();
		quests.clear();
		for (String faction : Factions.factions.keySet()) {
			ArrayList<ItemStack> rewards = new ArrayList<ItemStack>();
			rewards.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(0), 12)));
			rewards.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(1), 8)));
			rewards.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(2), 4)));
			quests.add(new QuestKillPlayers(faction, rewards, 5, 0));
			ArrayList<ItemStack> rewards2 = new ArrayList<ItemStack>();
			rewards2.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(0), 10)));
			rewards2.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(1), 6)));
			rewards2.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(2), 2)));
			int randomIndex = (int) (Math.random() * questsMonsters.size());
			quests.add(new QuestKillMonsters(faction, rewards2, 5, questsMonsters.get(randomIndex)));
			ArrayList<ItemStack> rewards3 = new ArrayList<ItemStack>();
			rewards3.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(0), 10)));
			rewards3.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(1), 6)));
			rewards3.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(2), 2)));
			quests.add(new QuestDefendStructure(faction, rewards3));
			ArrayList<ItemStack> rewards4 = new ArrayList<ItemStack>();
			rewards4.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(0), 15)));
			rewards4.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(1), 10)));
			rewards4.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(2), 6)));
			int randomIndex2 = (int) (Math.random() * questsMonsters.size());
			quests.add(new QuestAttackStructure(faction, rewards4, questsStructures.get(randomIndex2)));
			ArrayList<ItemStack> rewards5 = new ArrayList<ItemStack>();
			rewards5.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(0), 10)));
			rewards5.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(1), 8)));
			rewards5.add(Materials.RoCItem(new ItemStack(EnchantRuleset.materials.get(2), 4)));
			int randomIndex3 = (int) (Math.random() * questsChallenges.size());
			quests.add(new QuestChallengeWave(faction, rewards5, questsChallenges.get(randomIndex3)));
		}
	}

}
