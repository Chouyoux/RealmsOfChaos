package io.github.chouyoux.realmsofchaos.challenge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import gyurix.api.TitleAPI;
import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.GUIs.RewardGUI;
import io.github.chouyoux.realmsofchaos.custom_mobs.Mob;
import io.github.chouyoux.realmsofchaos.custom_mobs.MobType;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.memory.Factions;
import io.github.chouyoux.realmsofchaos.quest.Quest;
import io.github.chouyoux.realmsofchaos.quest.QuestChallengeWave;
import io.github.chouyoux.realmsofchaos.quest.QuestRegistry;
import io.github.chouyoux.realmsofchaos.ruleset.EnchantRuleset;
import io.github.chouyoux.realmsofchaos.worlds_handler.WorldsHandler;
import net.md_5.bungee.api.ChatColor;

public class Challenge {
	
	// Parameters
	private static int waveTime = 60;
	private int lives = 5;
	
	// Locations
	private Location startLoc;
	private ArrayList<Location> spawnLocs;
	private HashMap<MobType, Double> monsters_rate;
	private HashMap<PotionEffectType, Double> buffs_rate;
	
	// Behavior
	private World world;
	private ArrayList<Player> players;
	private int currentWave = 0;
	private ArrayList<ItemStack> rewards;
	private boolean started = false;
	private ArrayList<Wave> waves;
	private int lastWaveTime = Integer.MAX_VALUE;
	
	
	// Statistics
	private int timeIn = 0;
	private int deathCount = 0;
	private int kills = 0;
	private double damageDealt = 0;
	private double damageTaken = 0;
	private double healDealt = 0;
	BukkitRunnable newWaveTask;
	Scoreboard scoreboard;
	
	public Challenge(World world, Player player, Location startLoc, ArrayList<Location> spawnLocs, HashMap<MobType, Double> monsters_rate, HashMap<PotionEffectType, Double> buffs_rate) {
		super();
		this.waves = new ArrayList<Wave>();
		this.waves.add(new Wave());
		this.world = world;
		this.startLoc = startLoc;
		this.spawnLocs = spawnLocs;
		this.monsters_rate = monsters_rate;
		this.buffs_rate = buffs_rate;
		this.rewards = new ArrayList<ItemStack>();
		this.players = new ArrayList<Player>();
		createScoreboard();
		addPlayer(player);
		update();
	}
	
	public Challenge(World world, Player player, Location startLoc) {
		super();
		this.waves = new ArrayList<Wave>();
		this.waves.add(new Wave());
		this.world = world;
		this.startLoc = startLoc;
		this.spawnLocs = new ArrayList<Location>();
		this.monsters_rate = new HashMap<MobType, Double>();
		this.buffs_rate = new HashMap<PotionEffectType, Double>();
		this.rewards = new ArrayList<ItemStack>();
		this.players = new ArrayList<Player>();
		createScoreboard();
		addPlayer(player);
		update();
	}
	
	public void start() {
		for (Player player : players) {
			this.started = true;
			player.teleport(startLoc);
			TitleAPI.set("", ChatColor.RED+"Challenge begins !", (int)(0.6*20), (int)(1.2*20), (int)(0.6*20), player);
			player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 1F, 0.5F);
			player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 1F, 0.5F);
			player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 1F, 0.5F);
		}
	}
	
	private void startNextWave(boolean immidiate) {
		this.currentWave ++;	    		
		generateWave();
		Challenge challenge = this;
		newWaveTask = new BukkitRunnable() {

	    	@Override
	        public void run() {

	    		Wave wave = waves.get(waves.size()-1);
	    		lastWaveTime = timeIn;
	    		wave.spawn();
	    		for (Player player : players) {
					TitleAPI.set("", ChatColor.DARK_RED+"Wave "+String.valueOf(currentWave), (int)(0.6*20), (int)(1.2*20), (int)(0.6*20), player);
					player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 1F, 0.5F);
	    		}
	    		
	    		for (Player p : players) {
	    	    	for (Quest quest : QuestRegistry.quests) {
	    				if (quest instanceof QuestChallengeWave) {
	    					QuestChallengeWave killcQuest = (QuestChallengeWave) quest;
	    					if (killcQuest.hasPlayer(p) && killcQuest.challengeEntranceName.equals(ChallengesList.getChallengeEntrance(challenge).name))
	    						killcQuest.setProgress(p, currentWave);
	    				}
	    			}
	            }
	    		
	    	}

		};
		if (currentWave > 30 || immidiate)
			newWaveTask.runTaskLater(RealmsOfChaos.getInstance(), 0);
		else if (currentWave < 10)
			newWaveTask.runTaskLater(RealmsOfChaos.getInstance(), 20*20);
		else if (currentWave < 20)
			newWaveTask.runTaskLater(RealmsOfChaos.getInstance(), 10*20);
		else if (currentWave < 30)
			newWaveTask.runTaskLater(RealmsOfChaos.getInstance(), 5*20);
	}
	
	private ArrayList<ItemStack> generateReward() {
		ArrayList<ItemStack> rewards = new ArrayList<ItemStack>();
		for (int i  = 0; i < 4; i++) {
    		int amount = currentWave - (i*10);
    		ItemStack item = new ItemStack(EnchantRuleset.materials.get(i), amount);
    		rewards.add(item);
		}
		return rewards;
	}
	
	private void generateWave() {
		double score = 0.0;
		int max_score = (this.currentWave)*10;
		Wave wave = new Wave();
		this.waves.add(wave);
		for (Location spawn : this.spawnLocs) {
			if (score >= max_score) break;

			int size = this.monsters_rate.keySet().size();
			int item = new Random().nextInt(size);
			int i = 0;
			
			MobType randomEntityType = null;
			for(MobType obj : this.monsters_rate.keySet())
			{
			    if (i == item)
			    	randomEntityType = obj;
			    i++;
			}
			if (randomEntityType != null) {
				wave.addMonster(spawn, randomEntityType);
				score += this.monsters_rate.get(randomEntityType);
			}
		}
		for (Mob monster : wave.getSpawned_monsters()) {
			if (score >= max_score) break;
			LivingEntity _monster = (LivingEntity) monster.getEntity();
			
			int size = this.buffs_rate.keySet().size();
			int item = new Random().nextInt(size);
			int i = 0;
			
			PotionEffectType randomPotionType = null;
			for(PotionEffectType obj : this.buffs_rate.keySet())
			{
			    if (i == item)
			    	randomPotionType = obj;
			    i++;
			}
			if (randomPotionType != null) {
				int randomInt = new Random().nextInt(2)+1;
				_monster.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, randomInt, false));
				score += this.monsters_rate.get(monster.getMobType())*((this.buffs_rate.get(randomPotionType)*randomInt)-1);
			}
			
		}
	}
	
	private void update() {
		BukkitRunnable task = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		int player_count = 0;
    			ArrayList<Player> toRemove = new ArrayList<Player>();
        		for (Player player : players) {
        			if (!world.getPlayers().contains(player)) {
        				removePlayer(player);
        				toRemove.add(player);
        			}
        			else if (player.getGameMode().equals(GameMode.ADVENTURE))
        				player_count++;
        		}
        		for (Player player : toRemove) {
        			players.remove(player);
        		}
        		
        		if (player_count == 0) {
        			end();
        			this.cancel();
        			if (newWaveTask != null)
        				newWaveTask.cancel();
        			return;
        		}
        		
        		Wave wave = waves.get(waves.size()-1);
        		if (started && (wave.isSpawned() || currentWave == 0) && wave.isCleaned()) {
        			if (currentWave != 0) {
        				for (Player player : players) {
        					TitleAPI.set("", ChatColor.GREEN+"Wave cleared !", (int)(0.6*20), (int)(1.2*20), (int)(0.6*20), player);
        					player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
        	    		}
        			}
        			startNextWave(false);
        		}
        		if (timeIn - lastWaveTime > waveTime)
        			startNextWave(true);
        		if (started)
        			timeIn++;
        		updatePlayersScoreboard();
	    	}
	    };
    	task.runTaskTimer(RealmsOfChaos.getInstance(), 0, 20);
	}

	private void end() {
		if (getPlayers().size() > 0)
			for (Player player : getPlayers())
				removePlayer(player);
		players.clear();
		ChallengesList.getChallengeEntrance(this).runningChallenges.remove(this);
		WorldsHandler.deleteWorld(world);
	}
	
	private void createScoreboard() {
		ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective("challenge", "dummy", ChatColor.RED + getDisplayName());
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Team waveCounter = scoreboard.registerNewTeam("waveCounter");
		Team timeCounter = scoreboard.registerNewTeam("timeCounter");
		Team killCounter = scoreboard.registerNewTeam("killCounter");
		Team damageCounter = scoreboard.registerNewTeam("damageCounter");
		Team hurtCounter = scoreboard.registerNewTeam("hurtCounter");
		Team healCounter = scoreboard.registerNewTeam("healCounter");
		Team deathCounter = scoreboard.registerNewTeam("deathCounter");
		Team livesCounter = scoreboard.registerNewTeam("livesCounter");
		String waveKey = ChatColor.BLACK + "" + ChatColor.WHITE;
		String timeKey = ChatColor.RED + "" + ChatColor.WHITE;
		String killKey = ChatColor.GREEN + "" + ChatColor.WHITE;
		String damageKey = ChatColor.WHITE + "" + ChatColor.WHITE;
		String hurtKey = ChatColor.GOLD + "" + ChatColor.WHITE;
		String healKey = ChatColor.DARK_RED + "" + ChatColor.WHITE;
		String deathKey = ChatColor.BLUE + "" + ChatColor.WHITE;
		String livesKey = ChatColor.DARK_GREEN + "" + ChatColor.WHITE;
		waveCounter.addEntry(waveKey);
		timeCounter.addEntry(timeKey);
		killCounter.addEntry(killKey);
		damageCounter.addEntry(damageKey);
		hurtCounter.addEntry(hurtKey);
		healCounter.addEntry(healKey);
		deathCounter.addEntry(deathKey);
		livesCounter.addEntry(livesKey);
		waveCounter.setPrefix(ChatColor.GOLD + "Curent Wave : " + ChatColor.YELLOW + getCurrentWave());
		timeCounter.setPrefix(ChatColor.GOLD + "Time In : " + ChatColor.YELLOW + getTimeIn());
		killCounter.setPrefix(ChatColor.GOLD + "Kills : " + ChatColor.YELLOW + getKills());
		damageCounter.setPrefix(ChatColor.GOLD + "Damage Dealt : " + ChatColor.YELLOW + new DecimalFormat("#.##").format((getDamageDealt())));
		hurtCounter.setPrefix(ChatColor.GOLD + "Damage Taken : " + ChatColor.YELLOW + new DecimalFormat("#.##").format(getDamageTaken()));
		healCounter.setPrefix(ChatColor.GOLD + "Heal : " + ChatColor.YELLOW + new DecimalFormat("#.##").format(getHealDealt()));
		deathCounter.setPrefix(ChatColor.GOLD + "Deaths : " + ChatColor.YELLOW + getDeathCount());
		livesCounter.setPrefix(ChatColor.GOLD + "Lives : " + ChatColor.YELLOW + getLives());
		objective.getScore(waveKey).setScore(7);
		objective.getScore(timeKey).setScore(6);
		objective.getScore(killKey).setScore(5);
		objective.getScore(damageKey).setScore(4);
		objective.getScore(hurtKey).setScore(3);
		objective.getScore(healKey).setScore(2);
		objective.getScore(deathKey).setScore(1);
		objective.getScore(livesKey).setScore(0);
		this.scoreboard = scoreboard;
	}
	
	private void updatePlayersScoreboard() {
		scoreboard.getTeam("waveCounter").setPrefix(ChatColor.GOLD + "Curent Wave : " + ChatColor.YELLOW + getCurrentWave());
		scoreboard.getTeam("timeCounter").setPrefix(ChatColor.GOLD + "Time In : " + ChatColor.YELLOW + getTimeIn());
		scoreboard.getTeam("killCounter").setPrefix(ChatColor.GOLD + "Kills : " + ChatColor.YELLOW + getKills());
		scoreboard.getTeam("damageCounter").setPrefix(ChatColor.GOLD + "Damage Dealt : " + ChatColor.YELLOW + new DecimalFormat("#.##").format((getDamageDealt())));
		scoreboard.getTeam("hurtCounter").setPrefix(ChatColor.GOLD + "Damage Taken : " + ChatColor.YELLOW + new DecimalFormat("#.##").format(getDamageTaken()));
		scoreboard.getTeam("healCounter").setPrefix(ChatColor.GOLD + "Heal : " + ChatColor.YELLOW + new DecimalFormat("#.##").format(getHealDealt()));
		scoreboard.getTeam("deathCounter").setPrefix(ChatColor.GOLD + "Deaths : " + ChatColor.YELLOW + getDeathCount());
		scoreboard.getTeam("livesCounter").setPrefix(ChatColor.GOLD + "Lives : " + ChatColor.YELLOW + getLives());
	}
	
	public String getDisplayName() {
		String result = "";
		String[] split = world.getName().split("_");
		for (String str : split)
			result += str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
		return result;
	}
	
	public void addSpawnPoint(Location spawnPoint) {
		this.spawnLocs.add(spawnPoint);
	}
	
	public void addMonsterRate(Map.Entry<MobType, Double> monster_rate) {
		this.monsters_rate.put(monster_rate.getKey(), monster_rate.getValue());
	}
	
	public void addBuffRate(Map.Entry<PotionEffectType, Double> buff_rate) {
		this.buffs_rate.put(buff_rate.getKey(), buff_rate.getValue());
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
		player.teleport(startLoc);
		player.setScoreboard(scoreboard);
		player.setGameMode(GameMode.ADVENTURE);
		ChallengesList.getChallengeEntrance(this).cooldowns.put(player.getUniqueId(), 60*60*10);
	}
	
	public void removePlayer(Player player) {
		ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
		player.setGameMode(GameMode.SURVIVAL);
		player.setScoreboard(scoreboardManager.getNewScoreboard());
		player.teleport(Factions.factions.get(RoCPlayers.getFaction(player)).getSpawn());
		BukkitRunnable task = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		new RewardGUI(player, generateReward()).openInventory();
	    	}
		};
		task.runTaskLater(RealmsOfChaos.instance, 20*4);
	}


	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}


	public int getCurrentWave() {
		return currentWave;
	}


	public void setCurrentWave(int currentWave) {
		this.currentWave = currentWave;
	}


	public ArrayList<ItemStack> getRewards() {
		return rewards;
	}


	public void setRewards(ArrayList<ItemStack> rewards) {
		this.rewards = rewards;
	}


	public int getTimeIn() {
		return timeIn;
	}


	public void setTimeIn(int timeIn) {
		this.timeIn = timeIn;
	}


	public int getDeathCount() {
		return deathCount;
	}


	public void setDeathCount(int deathCount) {
		this.deathCount = deathCount;
	}


	public int getKills() {
		return kills;
	}


	public void setKills(int kills) {
		this.kills = kills;
	}


	public double getDamageDealt() {
		return damageDealt;
	}


	public void setDamageDealt(double damageDealt) {
		this.damageDealt = damageDealt;
	}


	public double getDamageTaken() {
		return damageTaken;
	}


	public void setDamageTaken(double d) {
		this.damageTaken = d;
	}


	public double getHealDealt() {
		return healDealt;
	}


	public void setHealDealt(double healDealt) {
		this.healDealt = healDealt;
	}


	public World getWorld() {
		return world;
	}


	public void setWorld(World world) {
		this.world = world;
	}


	public boolean isStarted() {
		return started;
	}


	public void setStarted(boolean started) {
		this.started = started;
	}

	public Location getStartLoc() {
		return startLoc;
	}

	public void setStartLoc(Location startLoc) {
		this.startLoc = startLoc;
	}

	public ArrayList<Location> getSpawnLocs() {
		return spawnLocs;
	}

	public void setSpawnLocs(ArrayList<Location> spawnLocs) {
		this.spawnLocs = spawnLocs;
	}

	public HashMap<MobType, Double> getMonsters_rate() {
		return monsters_rate;
	}

	public void setMonsters_rate(HashMap<MobType, Double> monsters_rate) {
		this.monsters_rate = monsters_rate;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

}
