package io.github.chouyoux.realmsofchaos.challenge;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import io.github.chouyoux.realmsofchaos.custom_mobs.MobType;
import io.github.chouyoux.realmsofchaos.memory.Regions;
import io.github.chouyoux.realmsofchaos.objects.Region;

public class ChallengesList {
	
	private static ArrayList<ChallengeEntrance> challengeEntrances;
	
	public static ArrayList<ChallengeEntrance> getList(){
		return challengeEntrances;
	}
	
	public static Challenge getPlayerChallenge(Player player) {
		for (ChallengeEntrance challengeEntrance : challengeEntrances) {
			for (Challenge challenge : challengeEntrance.runningChallenges) {
				if (challenge.getPlayers().contains(player))
					return challenge;
			}
		}
		return null;
	}
	
	public static ChallengeEntrance getPlayerChallengeEntrance(Player player) {
		for (ChallengeEntrance challengeEntrance : challengeEntrances) {
			for (Challenge challenge : challengeEntrance.runningChallenges) {
				if (challenge.getPlayers().contains(player))
					return challengeEntrance;
			}
		}
		return null;
	}
	
	public static ChallengeEntrance getChallengeEntrance(Challenge challenge) {
		for (ChallengeEntrance challengeEntrance : challengeEntrances) {
			if (challenge.getWorld().getName().contains(challengeEntrance.name))
				return challengeEntrance;
		}
		return null;
	}
	
	public static Region getChallengeEntranceRegion(ChallengeEntrance entrance) {
		for (String r_name : Regions.regions.keySet())
			if (entrance.name.toLowerCase().contains(r_name)) return Regions.regions.get(r_name);
		return null;
	}
	
	public static Challenge getMapChallenge(World w) {
		for (ChallengeEntrance challengeEntrance : challengeEntrances) {
			for (Challenge challenge : challengeEntrance.runningChallenges) {
				if (challenge.getWorld().getName().equals(w.getName()))
					return challenge;
			}
		}
		return null;
	}
	
	public static ChallengeEntrance getMapChallengeEntrance(World w) {
		for (ChallengeEntrance challengeEntrance : challengeEntrances) {
			if (w.getName().contains(challengeEntrance.name))
				return challengeEntrance;
		}
		return null;
	}
	
	public ChallengesList() {
		challengeEntrances = new ArrayList<ChallengeEntrance>();
		
		Location lakeCreateLoc = new Location(Bukkit.getWorld("Realms"), 352, 26, 1197);
		Location lakeJoinLoc = new Location(Bukkit.getWorld("Realms"), 352, 26, 1195);
		ArrayList<Location> lakeSpawnLocs = new ArrayList<Location>();
		lakeSpawnLocs.add(new Location(null, -2, 92, 61));
		lakeSpawnLocs.add(new Location(null, 50, 83, 31));
		lakeSpawnLocs.add(new Location(null, 58, 85, 20));
		lakeSpawnLocs.add(new Location(null, 62, 85, -8));
		lakeSpawnLocs.add(new Location(null, -2, 80, -43));
		lakeSpawnLocs.add(new Location(null, -22, 79, -48));
		lakeSpawnLocs.add(new Location(null, -43, 79, -45));
		lakeSpawnLocs.add(new Location(null, -50, 79, -31));
		lakeSpawnLocs.add(new Location(null, -22, 58, 3));
		lakeSpawnLocs.add(new Location(null, -19, 52, 19));
		lakeSpawnLocs.add(new Location(null, 38, 84, 35));
		Location lakeStartLoc = new Location(null, 0, 85, 0);
		HashMap<MobType, Double> lakeMonstersRate = new HashMap<MobType, Double>();
		lakeMonstersRate.put(MobType.SKYLEK, 5.0);
		lakeMonstersRate.put(MobType.ANTALOCK, 3.0);
		lakeMonstersRate.put(MobType.BULDAVAX, 10.0);
		HashMap<PotionEffectType, Double> lakeBuffsRate = new HashMap<PotionEffectType, Double>();
		lakeBuffsRate.put(PotionEffectType.INCREASE_DAMAGE, 1.8);
		lakeBuffsRate.put(PotionEffectType.DOLPHINS_GRACE, 1.4);
		lakeBuffsRate.put(PotionEffectType.DAMAGE_RESISTANCE, 1.6);
		lakeBuffsRate.put(PotionEffectType.INVISIBILITY, 2.0);
		challengeEntrances.add(new ChallengeEntrance("Euphrate_Lake", lakeCreateLoc, lakeJoinLoc, lakeSpawnLocs, lakeStartLoc, lakeMonstersRate, lakeBuffsRate));
		

		
		Location desertCreateLoc = new Location(Bukkit.getWorld("Realms"), 1097, 59, 1349);
		Location desertJoinLoc = new Location(Bukkit.getWorld("Realms"), 1095, 59, 1349);
		ArrayList<Location> desertSpawnLocs = new ArrayList<Location>();
		desertSpawnLocs.add(new Location(null, 16, 81, -44));
		desertSpawnLocs.add(new Location(null, 26, 81, -33));
		desertSpawnLocs.add(new Location(null, 31, 81, -19));
		desertSpawnLocs.add(new Location(null, 19, 81, 12));
		desertSpawnLocs.add(new Location(null, 22, 82, 18));
		desertSpawnLocs.add(new Location(null, 14, 82, 23));
		desertSpawnLocs.add(new Location(null, 14, 81, 16));
		desertSpawnLocs.add(new Location(null, 7, 82, 27));
		desertSpawnLocs.add(new Location(null, -9, 81, 24));
		desertSpawnLocs.add(new Location(null, -17, 80, 22));
		desertSpawnLocs.add(new Location(null, -19, 79, 16));
		desertSpawnLocs.add(new Location(null, -23, 82, 4));
		desertSpawnLocs.add(new Location(null, 1, 82, -30));
		desertSpawnLocs.add(new Location(null, 9, 81, -39));
		desertSpawnLocs.add(new Location(null, 16, 81, -46));
		desertSpawnLocs.add(new Location(null, 13, 86, -55));
		Location desertStartLoc = new Location(null, 0, 81, 0);
		HashMap<MobType, Double> desertMonstersRate = new HashMap<MobType, Double>();
		desertMonstersRate.put(MobType.GURAFAR, 18.0);
		desertMonstersRate.put(MobType.GARAMIN, 15.0);
		desertMonstersRate.put(MobType.KREDEKAI, 10.0);
		desertMonstersRate.put(MobType.KHROKEDAI, 14.0);
		desertMonstersRate.put(MobType.ZAROSS, 20.0);
		desertMonstersRate.put(MobType.SKYLEK, 15.0);
		desertMonstersRate.put(MobType.ELABUS, 32.0);
		desertMonstersRate.put(MobType.AZUTHOT, 20.0);
		desertMonstersRate.put(MobType.BEROCIUS, 20.0);
		HashMap<PotionEffectType, Double> desertBuffsRate = new HashMap<PotionEffectType, Double>();
		desertBuffsRate.put(PotionEffectType.INCREASE_DAMAGE, 1.8);
		desertBuffsRate.put(PotionEffectType.FIRE_RESISTANCE, 1.4);
		desertBuffsRate.put(PotionEffectType.DAMAGE_RESISTANCE, 1.6);
		desertBuffsRate.put(PotionEffectType.INVISIBILITY, 2.0);
		challengeEntrances.add(new ChallengeEntrance("Arabic-Desert", desertCreateLoc, desertJoinLoc, desertSpawnLocs, desertStartLoc, desertMonstersRate, desertBuffsRate));
		

		
		Location hillCreateLoc = new Location(Bukkit.getWorld("Realms"), 770, 53, 423);
		Location hillJoinLoc = new Location(Bukkit.getWorld("Realms"), 768, 53, 423);
		ArrayList<Location> hillSpawnLocs = new ArrayList<Location>();
		hillSpawnLocs.add(new Location(null, 34, 112, 0));
		hillSpawnLocs.add(new Location(null, 23, 103, 38));
		hillSpawnLocs.add(new Location(null, -8, 106, 22));
		hillSpawnLocs.add(new Location(null, -42, 115, -6));
		hillSpawnLocs.add(new Location(null, -49, 117, -13));
		hillSpawnLocs.add(new Location(null, -13, 113, -47));
		hillSpawnLocs.add(new Location(null, 15, 120, -34));
		hillSpawnLocs.add(new Location(null, 44, 138, -51));
		hillSpawnLocs.add(new Location(null, 53, 125, -28));
		hillSpawnLocs.add(new Location(null, 59, 112, -1));
		hillSpawnLocs.add(new Location(null, 26, 110, 12));
		hillSpawnLocs.add(new Location(null, 12, 106, 48));
		hillSpawnLocs.add(new Location(null, -44, 113, 8));
		Location hillStartLoc = new Location(null, 0, 105, 0);
		HashMap<MobType, Double> hillMonstersRate = new HashMap<MobType, Double>();
		hillMonstersRate.put(MobType.GURAFAR, 18.0);
		hillMonstersRate.put(MobType.GARAMIN, 15.0);
		hillMonstersRate.put(MobType.KREDEKAI, 10.0);
		hillMonstersRate.put(MobType.KHROKEDAI, 14.0);
		hillMonstersRate.put(MobType.ZAROSS, 20.0);
		hillMonstersRate.put(MobType.SKYLEK, 15.0);
		hillMonstersRate.put(MobType.ELABUS, 32.0);
		hillMonstersRate.put(MobType.AZUTHOT, 20.0);
		hillMonstersRate.put(MobType.BEROCIUS, 20.0);
		hillMonstersRate.put(MobType.GURON, 35.0);
		HashMap<PotionEffectType, Double> hillBuffsRate = new HashMap<PotionEffectType, Double>();
		hillBuffsRate.put(PotionEffectType.INCREASE_DAMAGE, 1.8);
		hillBuffsRate.put(PotionEffectType.FIRE_RESISTANCE, 1.4);
		hillBuffsRate.put(PotionEffectType.DAMAGE_RESISTANCE, 1.6);
		hillBuffsRate.put(PotionEffectType.INVISIBILITY, 2.0);
		challengeEntrances.add(new ChallengeEntrance("Pateras_Hill", hillCreateLoc, hillJoinLoc, hillSpawnLocs, hillStartLoc, hillMonstersRate, hillBuffsRate));
		
		Location bridgesCreateLoc = new Location(Bukkit.getWorld("Realms"), 465, 51, 1346);
		Location bridgesJoinLoc = new Location(Bukkit.getWorld("Realms"), 463, 51, 1346);
		ArrayList<Location> bridgesSpawnLocs = new ArrayList<Location>();
		bridgesSpawnLocs.add(new Location(null, -1, 118, 63));
		bridgesSpawnLocs.add(new Location(null, 1, 118, 63));
		bridgesSpawnLocs.add(new Location(null, 61, 114, 22));
		bridgesSpawnLocs.add(new Location(null, 58, 113, 19));
		bridgesSpawnLocs.add(new Location(null, 38, 114, -18));
		bridgesSpawnLocs.add(new Location(null, 32, 115, -22));
		bridgesSpawnLocs.add(new Location(null, -11, 116, -29));
		bridgesSpawnLocs.add(new Location(null, -15, 117, -28));
		bridgesSpawnLocs.add(new Location(null, -49, 113, -27));
		bridgesSpawnLocs.add(new Location(null, -50, 113, -26));
		bridgesSpawnLocs.add(new Location(null, -37, 114, -6));
		bridgesSpawnLocs.add(new Location(null, -35, 115, -4));
		bridgesSpawnLocs.add(new Location(null, -18, 116, 33));
		bridgesSpawnLocs.add(new Location(null, -16, 116, 35));
		bridgesSpawnLocs.add(new Location(null, 14, 116, 34));
		bridgesSpawnLocs.add(new Location(null, 16, 116, 34));
		Location bridgesStartLoc = new Location(null, 0, 122, 0);
		HashMap<MobType, Double> bridgesMonstersRate = new HashMap<MobType, Double>();
		bridgesMonstersRate.put(MobType.GURAFAR, 18.0);
		bridgesMonstersRate.put(MobType.GARAMIN, 15.0);
		bridgesMonstersRate.put(MobType.KREDEKAI, 10.0);
		bridgesMonstersRate.put(MobType.KHROKEDAI, 14.0);
		bridgesMonstersRate.put(MobType.ZAROSS, 20.0);
		bridgesMonstersRate.put(MobType.SKYLEK, 15.0);
		bridgesMonstersRate.put(MobType.ELABUS, 32.0);
		bridgesMonstersRate.put(MobType.AZUTHOT, 20.0);
		bridgesMonstersRate.put(MobType.BEROCIUS, 20.0);
		bridgesMonstersRate.put(MobType.GURON, 35.0);
		HashMap<PotionEffectType, Double> bridgesBuffsRate = new HashMap<PotionEffectType, Double>();
		bridgesBuffsRate.put(PotionEffectType.INCREASE_DAMAGE, 1.8);
		bridgesBuffsRate.put(PotionEffectType.FIRE_RESISTANCE, 1.4);
		bridgesBuffsRate.put(PotionEffectType.DAMAGE_RESISTANCE, 1.6);
		bridgesBuffsRate.put(PotionEffectType.INVISIBILITY, 2.0);
		challengeEntrances.add(new ChallengeEntrance("Parthyene_Bridges", bridgesCreateLoc, bridgesJoinLoc, bridgesSpawnLocs, bridgesStartLoc, bridgesMonstersRate, bridgesBuffsRate));
		
		Location canyonCreateLoc = new Location(Bukkit.getWorld("Realms"), 1030, 33, 1240);
		Location canyonJoinLoc = new Location(Bukkit.getWorld("Realms"), 1030, 33, 1238);
		ArrayList<Location> canyonSpawnLocs = new ArrayList<Location>();
		canyonSpawnLocs.add(new Location(null, 29, 54, 33));
		canyonSpawnLocs.add(new Location(null, -26, 54, 31));
		canyonSpawnLocs.add(new Location(null, -23, 54, 32));
		canyonSpawnLocs.add(new Location(null, -25, 54, 35));
		canyonSpawnLocs.add(new Location(null, -54, 76, 31));
		canyonSpawnLocs.add(new Location(null, -44, 78, 25));
		canyonSpawnLocs.add(new Location(null, -33, 78, 14));
		canyonSpawnLocs.add(new Location(null, -23, 76, -5));
		canyonSpawnLocs.add(new Location(null, 6, 75, 10));
		canyonSpawnLocs.add(new Location(null, -1, 75, 18));
		canyonSpawnLocs.add(new Location(null, -10, 77, 29));
		canyonSpawnLocs.add(new Location(null, -17, 75, 39));
		canyonSpawnLocs.add(new Location(null, -26, 75, 50));
		canyonSpawnLocs.add(new Location(null, -35, 74, 56));
		canyonSpawnLocs.add(new Location(null, -47, 72, 55));
		Location canyonStartLoc = new Location(null, 11, 76, -13);
		HashMap<MobType, Double> canyonMonstersRate = new HashMap<MobType, Double>();
		canyonMonstersRate.put(MobType.GURAFAR, 18.0);
		canyonMonstersRate.put(MobType.GARAMIN, 15.0);
		canyonMonstersRate.put(MobType.KREDEKAI, 10.0);
		canyonMonstersRate.put(MobType.KHROKEDAI, 14.0);
		canyonMonstersRate.put(MobType.ZAROSS, 20.0);
		canyonMonstersRate.put(MobType.SKYLEK, 15.0);
		canyonMonstersRate.put(MobType.ELABUS, 32.0);
		canyonMonstersRate.put(MobType.AZUTHOT, 20.0);
		canyonMonstersRate.put(MobType.BEROCIUS, 20.0);
		canyonMonstersRate.put(MobType.GURON, 35.0);
		HashMap<PotionEffectType, Double> canyonBuffsRate = new HashMap<PotionEffectType, Double>();
		canyonBuffsRate.put(PotionEffectType.INCREASE_DAMAGE, 1.8);
		canyonBuffsRate.put(PotionEffectType.FIRE_RESISTANCE, 1.4);
		canyonBuffsRate.put(PotionEffectType.DAMAGE_RESISTANCE, 1.6);
		canyonBuffsRate.put(PotionEffectType.INVISIBILITY, 2.0);
		challengeEntrances.add(new ChallengeEntrance("Sinai_Canyon", canyonCreateLoc, canyonJoinLoc, canyonSpawnLocs, canyonStartLoc, canyonMonstersRate, canyonBuffsRate));
		
		Location templeCreateLoc = new Location(Bukkit.getWorld("Realms"), 561, 41, 431);
		Location templeJoinLoc = new Location(Bukkit.getWorld("Realms"), 560, 41, 430);
		ArrayList<Location> templeSpawnLocs = new ArrayList<Location>();
		templeSpawnLocs.add(new Location(null, 3, 63, -44));
		templeSpawnLocs.add(new Location(null, 7, 63, -38));
		templeSpawnLocs.add(new Location(null, 7, 63, -29));
		templeSpawnLocs.add(new Location(null, 7, 63, -51));
		templeSpawnLocs.add(new Location(null, 7, 63, -56));
		templeSpawnLocs.add(new Location(null, 22, 63, -54));
		templeSpawnLocs.add(new Location(null, 21, 63, -41));
		templeSpawnLocs.add(new Location(null, 34, 63, -58));
		templeSpawnLocs.add(new Location(null, 28, 63, -52));
		templeSpawnLocs.add(new Location(null, 39, 63, -53));
		templeSpawnLocs.add(new Location(null, 48, 53, -56));
		templeSpawnLocs.add(new Location(null, 45, 63, -32));
		templeSpawnLocs.add(new Location(null, 39, 63, -36));
		templeSpawnLocs.add(new Location(null, 35, 63, -45));
		templeSpawnLocs.add(new Location(null, 29, 63, -41));
		templeSpawnLocs.add(new Location(null, 33, 63, -34));
		templeSpawnLocs.add(new Location(null, 27, 63, -31));
		Location templeStartLoc = new Location(null, -8, 63, -44);
		HashMap<MobType, Double> templeMonstersRate = new HashMap<MobType, Double>();
		templeMonstersRate.put(MobType.GURAFAR, 18.0);
		templeMonstersRate.put(MobType.GARAMIN, 15.0);
		templeMonstersRate.put(MobType.KREDEKAI, 10.0);
		templeMonstersRate.put(MobType.KHROKEDAI, 14.0);
		templeMonstersRate.put(MobType.ZAROSS, 20.0);
		templeMonstersRate.put(MobType.SKYLEK, 15.0);
		templeMonstersRate.put(MobType.ELABUS, 32.0);
		templeMonstersRate.put(MobType.AZUTHOT, 20.0);
		templeMonstersRate.put(MobType.BEROCIUS, 20.0);
		templeMonstersRate.put(MobType.GURON, 35.0);
		HashMap<PotionEffectType, Double> templeBuffsRate = new HashMap<PotionEffectType, Double>();
		templeBuffsRate.put(PotionEffectType.INCREASE_DAMAGE, 1.8);
		templeBuffsRate.put(PotionEffectType.FIRE_RESISTANCE, 1.4);
		templeBuffsRate.put(PotionEffectType.DAMAGE_RESISTANCE, 1.6);
		templeBuffsRate.put(PotionEffectType.INVISIBILITY, 2.0);
		challengeEntrances.add(new ChallengeEntrance("Marathon_Temple", templeCreateLoc, templeJoinLoc, templeSpawnLocs, templeStartLoc, templeMonstersRate, templeBuffsRate));
		
	}

}
