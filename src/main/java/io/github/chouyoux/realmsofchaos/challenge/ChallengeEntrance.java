package io.github.chouyoux.realmsofchaos.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.MobType;
import io.github.chouyoux.realmsofchaos.util.ChatFormat;
import io.github.chouyoux.realmsofchaos.worlds_handler.WorldsHandler;
import net.md_5.bungee.api.ChatColor;

public class ChallengeEntrance {

	ArrayList<Challenge> runningChallenges;
	
	// Identifying
	public String name;
	public Location createLoc;
	public Location joinLoc;
	
	// Inside Locations, Parameters
	private ArrayList<Location> spawnLocs;
	private Location startLoc;
	private HashMap<MobType, Double> monsters_rate;
	private HashMap<PotionEffectType, Double> buffs_rate;
	
	// Cooldowns
	public HashMap<UUID, Integer> cooldowns;
	
	public ChallengeEntrance(String name, Location createLoc, Location joinLoc, ArrayList<Location> spawnLocs, Location startLoc, HashMap<MobType, Double> monsters_rate, HashMap<PotionEffectType, Double> buffs_rate) {
		super();
		this.runningChallenges = new ArrayList<Challenge>();
		this.name = name;
		this.createLoc = createLoc;
		this.joinLoc = joinLoc;
		this.spawnLocs = spawnLocs;
		this.startLoc = startLoc;
		this.monsters_rate = monsters_rate;
		this.buffs_rate = buffs_rate;
		this.cooldowns = new HashMap<UUID, Integer>();
		BukkitRunnable cdTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Entry<UUID, Integer> entry : cooldowns.entrySet()) {
					entry.setValue(entry.getValue()-1);
				}
			}
			
		};
		cdTask.runTaskTimer(RealmsOfChaos.instance, 0, 20);
	}
	
	public void createChallenge(Player player) {
		World originalWorld = Bukkit.getServer().createWorld(new WorldCreator(name));
		WorldsHandler.copyWorld(originalWorld, name+"_"+player.getName());
		World challengeWorld = Bukkit.getWorld(name+"_"+player.getName());
		
		ArrayList<Location> challengeSpawnLocs = new ArrayList<Location>();
		for (Location loc : spawnLocs) {
			Location challengeLoc = loc.clone();
			challengeLoc.setWorld(challengeWorld);
			challengeSpawnLocs.add(challengeLoc);
		}
		Location challengeStartLoc = startLoc.clone();
		challengeStartLoc.setWorld(challengeWorld);
		
		runningChallenges.add(new Challenge(challengeWorld, player, challengeStartLoc, challengeSpawnLocs, monsters_rate, buffs_rate));
	}
	
	public void sendGroupListToPlayer(Player player) {
		
		int count = 0;

		for (Challenge chall : runningChallenges) {
			if (chall.isStarted()) continue;

			String leader_name = chall.getPlayers().get(0).getName();
			String player_names = "• " + leader_name;
			for (Player p : chall.getPlayers()) {
				if (p.getName().equals(leader_name)) continue;
				player_names += ", " + p.getName();
			}
			
			count++;
			player.sendMessage(ChatFormat.HoverMessage(player_names, ChatColor.WHITE, "/join "+leader_name, "Sends a request to join this group"));
		}
		if (count == 0)
			player.sendMessage("No challenges available. You'll have to create one.");

	}

}
