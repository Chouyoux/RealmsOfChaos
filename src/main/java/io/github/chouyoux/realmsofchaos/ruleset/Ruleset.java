package io.github.chouyoux.realmsofchaos.ruleset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Ruleset {
	
	public static HashMap<String, HashMap<String, ArrayList<HashMap<String, Object>>>> Structures_ruleset;
	public static List<String> availableClasses;
	public static List<String> availableFactions;
	
	public Ruleset() {
		Structures_ruleset = new HashMap<String, HashMap<String, ArrayList<HashMap<String, Object>>>>();
		
		// ! Persian
		HashMap<String, ArrayList<HashMap<String, Object>>> Persians = new HashMap<String, ArrayList<HashMap<String, Object>>>();
		
		// ! Persian Castle
		ArrayList<HashMap<String, Object>> PersiansCastles = new ArrayList<HashMap<String, Object>>();
		
		// ! Persian Castle tier 1
		HashMap<String, Object> PersiansCastle_0 = new HashMap<String, Object>();

		ArrayList<Location> PersiansCastle_0_heart = new ArrayList<Location>();
		PersiansCastle_0_heart.add(new Location(null, 0, 21, -10, 0, 0));
		PersiansCastle_0_heart.add(new Location(null, 0, 22, -10, 0, 0));
		PersiansCastle_0_heart.add(new Location(null, 0, 23, -10, 0, 0));
		PersiansCastle_0_heart.add(new Location(null, 0, 21, 10, 0, 0));
		PersiansCastle_0_heart.add(new Location(null, 0, 22, 10, 0, 0));
		PersiansCastle_0_heart.add(new Location(null, 0, 23, 10, 0, 0));
		PersiansCastle_0.put("heart", PersiansCastle_0_heart);

		ArrayList<Location> PersiansCastle_0_banners = new ArrayList<Location>();
		PersiansCastle_0_banners.add(new Location(null, -5, 11, 22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 5, 11, 22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -15, 11, 22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 15, 11, 22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -5, 19, 22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 5, 19, 22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 22, 9, 10, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 22, 9, 7, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 22, 9, -7, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 22, 9, -10, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 22, 20, 0, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -5, 11, -22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 5, 11, -22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -15, 11, -22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 15, 11, -22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -5, 19, -22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 5, 19, -22, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -22, 9, 10, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -22, 9, 7, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -22, 9, -7, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -22, 9, -10, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -22, 20, 0, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -1, 12, -4, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 1, 12, -4, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 4, 12, -1, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 4, 12, 1, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, 1, 12, 4, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -1, 12, 4, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -4, 12, 1, 0, 0));
		PersiansCastle_0_banners.add(new Location(null, -4, 12, -1, 0, 0));
		PersiansCastle_0.put("banners", PersiansCastle_0_banners);
	
		ArrayList<Location> PersiansCastle_0_melee_guards = new ArrayList<Location>();
		PersiansCastle_0_melee_guards.add(new Location(null, 3, 4, -8, 0, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -3, 4, -8, 0, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 3, 4, 8, 180, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -3, 4, 8, 180, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 12, 3.5, -16, 45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 16, 3.5, 12, 45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 16, 3.5, 13, 135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 13, 3.5, 16, 135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -11, 3.5, 16, -135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -16, 3.5, 12, -135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -16, 3.5, -13, -45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -13, 3.5, -16, -45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 13, 13, 10, 135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 13, 13, 7, 135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 11, 13, 8, 135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -12, 13, -3, -45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -10, 13, -7, -45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -13, 13, -7, -45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 16, 15.5, -14, 45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 13, 15.5, -10, 180, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -17, 15.5, 14, -135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -12, 15.5, 10, 0, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 3, 21, -16, 0, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -3, 21, -16, 0, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -3, 21, 16, 180, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 3, 21, 16, 180, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 17, 21, 0, 90, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -17, 21, 0, -90, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -14, 21, 12, -135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -6, 21, 6, -135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 16, 21, 10, 135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 6, 21, 6, 135, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -16, 21, -10, -45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, -6, 21, -6, -45, 0));
		PersiansCastle_0_melee_guards.add(new Location(null, 6, 21, -6, 45, 0));
		PersiansCastle_0.put("melee_guards", PersiansCastle_0_melee_guards);

		ArrayList<Location> PersiansCastle_0_ranged_guards = new ArrayList<Location>();
		PersiansCastle_0_ranged_guards.add(new Location(null, 0, 4, -6, 0, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 0, 4, 6, 180, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 6, 3.5, 0, 90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -6, 3.5, 0, -90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -25, 15.5, -19, -90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -19, 15.5, -25, 0, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 25, 15.5, -19, 90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 19, 15.5, -25, 0, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 20, 15.5, 0, 90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -20, 15.5, 0, -90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 0, 13, -7, 0, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 7, 13, 0, 90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 0, 13, 7, 180, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -7, 13, 0, -90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 0, 21, 18, 180, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 0, 21, 8, 180, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 0, 21, -8, 0, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 0, 21, -18, 0, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 8, 21, 0, 90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -8, 21, 0, -90, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 21, 26, 19, 135, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 19, 26, 21, 135, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 14, 27, 19, 135, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -19, 26, 21, -135, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -21, 26, 19, -135, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -19, 27, 14, -135, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -21, 26, -19, -45, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -19, 26, -21, -45, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, -14, 27, -19, -45, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 19, 26, -21, 45, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 21, 26, -19, 45, 0));
		PersiansCastle_0_ranged_guards.add(new Location(null, 19, 27, -14, 45, 0));
		PersiansCastle_0.put("ranged_guards", PersiansCastle_0_ranged_guards);
		
		PersiansCastle_0.put("protect_radius", 40);
		PersiansCastle_0.put("production_rate", 3);
		PersiansCastle_0.put("production_needed", 1);
		PersiansCastle_0.put("production_max", 20);

		PersiansCastle_0.put("reliqua", new Location(null, 0, 22, -18, 0, 0));
		PersiansCastle_0.put("npc", new Location(Bukkit.getWorld("Realms"), -18, 3.5, 0, -90, 0));
		
		PersiansCastles.add(PersiansCastle_0);
		// Persian Castle tier 1 !
		
		Persians.put("Castle", PersiansCastles);
		// Persian Castle !
		
		// ! Persian Fort
		ArrayList<HashMap<String, Object>> PersiansForts = new ArrayList<HashMap<String, Object>>();
		
		// ! Persian Fort tier 1
		HashMap<String, Object> PersiansFort_0 = new HashMap<String, Object>();

		ArrayList<Location> PersiansFort_0_heart = new ArrayList<Location>();
		PersiansFort_0_heart.add(new Location(null, 0, 16, -6, 0, 0));
		PersiansFort_0_heart.add(new Location(null, 0, 17, -6, 0, 0));
		PersiansFort_0_heart.add(new Location(null, -1, 18, -6, 0, 0));
		PersiansFort_0_heart.add(new Location(null, 0, 19, -6, 0, 0));
		PersiansFort_0_heart.add(new Location(null, 1, 18, -6, 0, 0));
		PersiansFort_0.put("heart", PersiansFort_0_heart);
		
		ArrayList<Location> PersiansFort_0_banners = new ArrayList<Location>();
		PersiansFort_0_banners.add(new Location(null, -11, 4, 20, 0, 0));
		PersiansFort_0_banners.add(new Location(null, 0, 4, 18, 0, 0));
		PersiansFort_0_banners.add(new Location(null, 11, 4, 20, 0, 0));
		PersiansFort_0_banners.add(new Location(null, 25, 3, 5, 0, 0));
		PersiansFort_0_banners.add(new Location(null, 4, 3, -17, 0, 0));
		PersiansFort_0_banners.add(new Location(null, 0, 3, -17, 0, 0));
		PersiansFort_0_banners.add(new Location(null, -4, 3, -17, 0, 0));
		PersiansFort_0_banners.add(new Location(null, -25, 3, 5, 0, 0));

		PersiansFort_0.put("banners", PersiansFort_0_banners);
		ArrayList<Location> PersiansFort_0_melee_guards = new ArrayList<Location>();
		PersiansFort_0_melee_guards.add(new Location(null, -2, 1, 5, 0, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 2, 1, 5, 0, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 1, 1, -10, 0, 0));
		PersiansFort_0_melee_guards.add(new Location(null, -2, -3, -1, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 2, -3, -1, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 9, -3, 13, 90, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 7, -3, 12, 90 , 0));
		PersiansFort_0_melee_guards.add(new Location(null, -9, -4, 13, -90, 0));
		PersiansFort_0_melee_guards.add(new Location(null, -16, -4, 2, -90, 0));
		PersiansFort_0_melee_guards.add(new Location(null, -9, -3, -7, 0, 0));
		PersiansFort_0_melee_guards.add(new Location(null, -14, 3, -12, 0, 0));
		PersiansFort_0_melee_guards.add(new Location(null, -13, 20, -14, 90, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 15, 3, 6, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 17, 3, 10, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 18, 4, 7, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 11, 4, 15, -135, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 14, 3, -12, 0, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 13, 20, -14, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 4, 7, -4, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, -4, 7, -4, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 3, 7, -9, 90, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 3, 16, -6, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 0, 16, -9, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, -3, 16, -6, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, -14, 29, -15, -90, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 14, 29, -15, 90, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 15, -4, 10, 180, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 16, -4, -3, 90, 0));
		PersiansFort_0_melee_guards.add(new Location(null, 14, -4, -17, 0, 0));
		PersiansFort_0.put("melee_guards", PersiansFort_0_melee_guards);
		
		ArrayList<Location> PersiansFort_0_ranged_guards = new ArrayList<Location>();
		PersiansFort_0_ranged_guards.add(new Location(null, 0, 10, 1, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 7, 9, 5, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, -7, 9, 5, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 22, 4, 5, 90, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 22, 4, 0, 90, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, -21, 3, 5, -90, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, -21, 3, 1, -90, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, -15, 7, -6, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 16, 7, -6, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 14, 20, -10, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 14, 20, -20, 180, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 19, 20, -15, -90, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 9, 20, -15, 90, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, -19, 20, -15, 90, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, -9, 20, -15, 90, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, -14, 20, -10, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, -14, 20, -20, 180, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 14, 30, -11, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, -14, 30, -11, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 13, -4, -8, 0, 0));
		PersiansFort_0_ranged_guards.add(new Location(null, 13, -3, -18, 0, 0));
		PersiansFort_0.put("ranged_guards", PersiansFort_0_ranged_guards);
		
		PersiansFort_0.put("protect_radius", 30);
		PersiansFort_0.put("production_rate", 2);
		PersiansFort_0.put("production_needed", 1);
		PersiansFort_0.put("production_max", 15);
		PersiansFort_0.put("npc", new Location(Bukkit.getWorld("Realms"), 6, 1, -6, 90, 0));
		
		PersiansForts.add(PersiansFort_0);
		// Persian Fort tier 1 !
		
		Persians.put("Fort", PersiansForts);
		// Persian Fort !
		
		// ! Persian Watchtower
		ArrayList<HashMap<String, Object>> PersiansTowers = new ArrayList<HashMap<String, Object>>();
		
		// ! Persian Watchtower tier 1
		HashMap<String, Object> PersiansTower_0 = new HashMap<String, Object>();
		
		ArrayList<Location> PersiansTower_0_heart = new ArrayList<Location>();
		PersiansTower_0_heart.add(new Location(null, 0, 22, 0, 0, 0));
		PersiansTower_0_heart.add(new Location(null, 0, 23, 0, 0, 0));
		PersiansTower_0.put("heart", PersiansTower_0_heart);
		
		ArrayList<Location> PersiansTower_0_banners = new ArrayList<Location>();
		PersiansTower_0_banners.add(new Location(null, 0, 21, 10, 0, 0));
		PersiansTower_0_banners.add(new Location(null, 0, 25, 6, 0, 0));
		PersiansTower_0_banners.add(new Location(null, 10, 21, 0, 0, 0));
		PersiansTower_0_banners.add(new Location(null, 6, 25, 0, 0, 0));
		PersiansTower_0_banners.add(new Location(null, 0, 21, -10, 0, 0));
		PersiansTower_0_banners.add(new Location(null, 0, 25, -6, 0, 0));
		PersiansTower_0_banners.add(new Location(null, -10, 21, 0, 0, 0));
		PersiansTower_0_banners.add(new Location(null, -6, 25, 0, 0, 0));
		PersiansTower_0.put("banners", PersiansTower_0_banners);
		
		ArrayList<Location> PersiansTower_0_melee_guards = new ArrayList<Location>();
		PersiansTower_0_melee_guards.add(new Location(null, -8, 3, 0, 0, 0));
		PersiansTower_0_melee_guards.add(new Location(null, -1, 6, -8, 90, 0));
		PersiansTower_0_melee_guards.add(new Location(null, 8, 10, 0, 180, 0));
		PersiansTower_0_melee_guards.add(new Location(null, -4, 13, 0, -32, 0));
		PersiansTower_0_melee_guards.add(new Location(null, 4, 13, 0, 32, 0));
		PersiansTower_0_melee_guards.add(new Location(null, 0, 13, -4, 0, 0));
		PersiansTower_0_melee_guards.add(new Location(null, 3, 22, 0, 90, 0));
		PersiansTower_0_melee_guards.add(new Location(null, -3, 22, 0, -90, 0));
		PersiansTower_0_melee_guards.add(new Location(null, 0, 22, -3, 0, 0));
		PersiansTower_0_melee_guards.add(new Location(null, 5, 22, -5, -135, 0));
		PersiansTower_0_melee_guards.add(new Location(null, -5, 22, 5, 135, 0));
		PersiansTower_0_melee_guards.add(new Location(null, 5, 22, 5, -45, 0));
		PersiansTower_0_melee_guards.add(new Location(null, -5, 22, -5, 45, 0));
		PersiansTower_0_melee_guards.add(new Location(null, 2, 0, -2, -135, 0));
		PersiansTower_0_melee_guards.add(new Location(null, -2, 0, -2, 135, 0));
		PersiansTower_0_melee_guards.add(new Location(null, 0, 0, 4, 180, 0));
		PersiansTower_0.put("melee_guards", PersiansTower_0_melee_guards);
		
		ArrayList<Location> PersiansTower_0_ranged_guards = new ArrayList<Location>();
		PersiansTower_0_ranged_guards.add(new Location(null, 0, 13, 7, -90, 0));
		PersiansTower_0_ranged_guards.add(new Location(null, 0, 22, 7, 180, 0));
		PersiansTower_0_ranged_guards.add(new Location(null, 0, 22, -7, 0, 0));
		PersiansTower_0_ranged_guards.add(new Location(null, 7, 22, 0, 90, 0));
		PersiansTower_0_ranged_guards.add(new Location(null, -7, 22, 0, -90, 0));
		PersiansTower_0.put("ranged_guards", PersiansTower_0_ranged_guards);
		
		PersiansTower_0.put("protect_radius", 15);
		PersiansTower_0.put("production_rate", 1);
		PersiansTower_0.put("production_needed", 1);
		PersiansTower_0.put("production_max", 5);
		PersiansTower_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, 0, -4, 0, 0));
		
		PersiansTowers.add(PersiansTower_0);
		// Persian Watchtower tier 1 !
		
		Persians.put("Watchtower", PersiansTowers);
		// Persian Watchtower !
		
		// ! Persian Stable
		ArrayList<HashMap<String, Object>> PersiansStables = new ArrayList<HashMap<String, Object>>();
		
		// ! Persian Stable tier 1
		HashMap<String, Object> PersiansStable_0 = new HashMap<String, Object>();
		
		ArrayList<Location> PersiansStable_0_heart = new ArrayList<Location>();
		PersiansStable_0_heart.add(new Location(null, -11, 0, 0, 0, 0));
		PersiansStable_0_heart.add(new Location(null, -11, 1, 0, 0, 0));
		PersiansStable_0_heart.add(new Location(null, -11, 2, 0, 0, 0));
		PersiansStable_0.put("heart", PersiansStable_0_heart);
		
		ArrayList<Location> PersiansStable_0_banners = new ArrayList<Location>();
		PersiansStable_0_banners.add(new Location(null, -4, 5, 8, 0, 0));
		PersiansStable_0_banners.add(new Location(null, 7, 6, 1, 0, 0));
		PersiansStable_0_banners.add(new Location(null, -12, 4, -4, 0, 0));
		PersiansStable_0_banners.add(new Location(null, -15, 4, -1, 0, 0));
		PersiansStable_0.put("banners", PersiansStable_0_banners);
		
		ArrayList<Location> PersiansStable_0_melee_guards = new ArrayList<Location>();
		PersiansStable_0_melee_guards.add(new Location(null, 4, 0, 1, 0, 0));
		PersiansStable_0_melee_guards.add(new Location(null, 10, 0, 1, 0, 0));
		PersiansStable_0_melee_guards.add(new Location(null, -9, 0, 8, -90, 0));
		PersiansStable_0_melee_guards.add(new Location(null, -9, 0, 1, -45, 0));
		PersiansStable_0_melee_guards.add(new Location(null, -7, 0, -5, 180, 0));
		PersiansStable_0_melee_guards.add(new Location(null, -9, 0, -1, 0, 0));
		PersiansStable_0_melee_guards.add(new Location(null, -11, 0, 4, 180, 0));
		PersiansStable_0.put("melee_guards", PersiansStable_0_melee_guards);
		
		ArrayList<Location> PersiansStable_0_ranged_guards = new ArrayList<Location>();
		PersiansStable_0_ranged_guards.add(new Location(null, 2, 0, -6, 0, 0));
		PersiansStable_0_ranged_guards.add(new Location(null, -3, 0, 5, -90, 0));
		PersiansStable_0_ranged_guards.add(new Location(null, -3, 0, 2, -90, 0));
		PersiansStable_0_ranged_guards.add(new Location(null, -13, 5, -2, 135, 0));
		PersiansStable_0.put("ranged_guards", PersiansStable_0_ranged_guards);
		
		PersiansStable_0.put("protect_radius", 20);
		PersiansStable_0.put("production_rate", 1);
		PersiansStable_0.put("production_needed", 2);
		PersiansStable_0.put("production_max", 1);
		PersiansStable_0.put("npc", new Location(Bukkit.getWorld("Realms"), 9, 0, -1, 45, 0));
		
		PersiansStables.add(PersiansStable_0);
		// Persian Stable tier 1 !
		
		Persians.put("Stable", PersiansStables);
		// Persian Stable !
		
		// ! Persian Temple
		ArrayList<HashMap<String, Object>> PersiansTemples = new ArrayList<HashMap<String, Object>>();
		
		// ! Persian Temple tier 1
		HashMap<String, Object> PersiansTemple_0 = new HashMap<String, Object>();
		
		ArrayList<Location> PersiansTemple_0_heart = new ArrayList<Location>();
		PersiansTemple_0_heart.add(new Location(null, -1, 26, -9, 0, 0));
		PersiansTemple_0_heart.add(new Location(null, -1, 28, -9, 0, 0));
		PersiansTemple_0_heart.add(new Location(null, 1, 26, -9, 0, 0));
		PersiansTemple_0_heart.add(new Location(null, 1, 28, -9, 0, 0));
		PersiansTemple_0_heart.add(new Location(null, 0, 27, -9, 0, 0));
		PersiansTemple_0.put("heart", PersiansTemple_0_heart);
		
		ArrayList<Location> PersiansTemple_0_banners = new ArrayList<Location>();
		PersiansTemple_0_banners.add(new Location(null, -4, 31, -5, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, -3, 31, -4, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, 3, 31, -4, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, 4, 31, -5, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, 4, 31, -11, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, 3, 31, -12, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, -3, 31, -12, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, -4, 31, -11, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, 0, 24, 13, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, -4, 24, 17, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, 0, 24, 21, 0, 0));
		PersiansTemple_0_banners.add(new Location(null, 4, 24, 17, 0, 0));
		PersiansTemple_0.put("banners", PersiansTemple_0_banners);
		
		ArrayList<Location> PersiansTemple_0_melee_guards = new ArrayList<Location>();
		PersiansTemple_0_melee_guards.add(new Location(null, -4, 1, 38, 0, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 4, 1, 38, 0, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -1, 16, 23, 0, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 1, 16, 23, 0, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -4, 16, 8, 0, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 4, 16, 8, 0, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 1, 26, -4, 0, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -1, 26, -4, 0, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -6, 16, 18, 90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -6, 16, 16, 90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -21, 1, 21, 90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -12, 13, 10, 90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -16, 13, -5, 90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -16, 13, -16, 90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -15, 13, -21, 90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 6, 16, 18, -90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 6, 16, 16, -90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 12, 13, 10, -90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 21, 1, 21, -90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 16, 13, -5, -90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 17, 13, -16, -90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 15, 13, -21, -90, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, 4, 13, -24, 180, 0));
		PersiansTemple_0_melee_guards.add(new Location(null, -4, 13, -24, 180, 0));
		PersiansTemple_0.put("melee_guards", PersiansTemple_0_melee_guards);
		
		ArrayList<Location> PersiansTemple_0_ranged_guards = new ArrayList<Location>();
		PersiansTemple_0_ranged_guards.add(new Location(null, -11, 8, 25, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, -7, 8, 25, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 8, 8, 25, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 11, 8, 25, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, -1, 26, 20, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 16, 13, 8, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, -16, 13, 8, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, -9, 21, 1, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 9, 21, 1, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 4, 26, -4, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, -4, 26, -4, 0, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, -16, 13, -24, 180, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, -9, 21, -17, 180, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, -4, 26, -12, 180, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 4, 26, -12, 180, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 9, 21, -17, 180, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 16, 13, -24, 180, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 1, 26, 14, 180, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, -3, 26, 16, 90, 0));
		PersiansTemple_0_ranged_guards.add(new Location(null, 3, 26, 18, -90, 0));
		PersiansTemple_0.put("ranged_guards", PersiansTemple_0_ranged_guards);
		
		PersiansTemple_0.put("protect_radius", 45);
		PersiansTemple_0.put("production_rate", 1);
		PersiansTemple_0.put("production_needed", 120);
		PersiansTemple_0.put("production_max", 1);
		PersiansTemple_0.put("npc", new Location(Bukkit.getWorld("Realms"), -2, 16, 15, -45, 0));
		
		PersiansTemples.add(PersiansTemple_0);
		// Persian Temple tier 1 !
		
		Persians.put("Temple", PersiansTemples);
		// Persian Temple !
		
		Structures_ruleset.put("Persians", Persians);
		// Persian !
		
		
		
		// ! Greeks
		HashMap<String, ArrayList<HashMap<String, Object>>> Greeks = new HashMap<String, ArrayList<HashMap<String, Object>>>();
		
		// ! Greeks Castle
		ArrayList<HashMap<String, Object>> GreeksCastles = new ArrayList<HashMap<String, Object>>();
		
		// ! Greeks Castle tier 1
		HashMap<String, Object> GreeksCastle_0 = new HashMap<String, Object>();

		ArrayList<Location> GreeksCastle_0_heart = new ArrayList<Location>();
		GreeksCastle_0_heart.add(new Location(null, -28, 19, -9, 0, 0));
		GreeksCastle_0_heart.add(new Location(null, -28, 20, -9, 0, 0));
		GreeksCastle_0_heart.add(new Location(null, -28, 21, -9, 0, 0));
		GreeksCastle_0_heart.add(new Location(null, -28, 19, -13, 0, 0));
		GreeksCastle_0_heart.add(new Location(null, -28, 20, -13, 0, 0));
		GreeksCastle_0_heart.add(new Location(null, -28, 21, -13, 0, 0));
		GreeksCastle_0.put("heart", GreeksCastle_0_heart);

		ArrayList<Location> GreeksCastle_0_banners = new ArrayList<Location>();
		GreeksCastle_0_banners.add(new Location(null, 1, 10, 17, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, -19, 13, 17, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, -28, 13, 7, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, -28, 24, 7, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, -25, 24, 10, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, -22, 24, 7, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, -25, 24, 4, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, -30, 13, -11, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, -19, 13, -17, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, 26, 14, -8, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, 22, 18, 15, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, 19, 18, 18, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, 16, 18, 15, 0, 0));
		GreeksCastle_0_banners.add(new Location(null, 19, 18, 12, 0, 0));
		GreeksCastle_0.put("banners", GreeksCastle_0_banners);
			
		ArrayList<Location> GreeksCastle_0_melee_guards = new ArrayList<Location>();
		GreeksCastle_0_melee_guards.add(new Location(null, 3, 1, 17, 0, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -1, 1, 17, 0, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -1, 1, 6, -135, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 2, 1, 5, 90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -1, 1, 3, -45, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -7, 1, 10, 180, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -9, 1, 0, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -9, 1, -4, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 0, 1, -5, 0, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -2, 1, -9, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -12, 1, -16, 45, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 8, 1, -2, -180, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 17, 1, -2, 90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 11, 1, 10, 180, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 16, 1, 13, 180, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 4, 7, -10, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 16, 7, -13, 90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -2, 7, -16, 180, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 11, 7, -1, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 17, 7, -1, 90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 14, 7, 14, 180, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 11, 7, 8, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -4, 12, -7, 45, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 19, 10, -8, 135, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, 11, 12, 12, -45, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -11, 7, 9, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -11, 7, 2, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -17, 7, 12, 90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -21, 7, 12, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -24, 7, -4, 90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -19, 7, -15, 180, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -16, 15, -15, 0, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -18, 18, -10, -90, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -19, 18, 15, 0, 0));
		GreeksCastle_0_melee_guards.add(new Location(null, -7, 18, 18, 135, 0));
		GreeksCastle_0.put("melee_guards", GreeksCastle_0_melee_guards);

		ArrayList<Location> GreeksCastle_0_ranged_guards = new ArrayList<Location>();
		GreeksCastle_0_ranged_guards.add(new Location(null, 0, 12, -17, 180, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 13, 12, -17, 180, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 5, 12, -4, 90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 6, 12, 2, 0, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 18, 12, 1, -90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 18, 12, 7, -90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 15, 12, 14, 0, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 10, 18, 11, 180, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 12, 18, 18, -90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 10, 18, 19, 0, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 6, 18, 18, 0, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 5, 18, 14, 135, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -4, 18, 12, 180, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -8, 18, 11, 180, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -10, 18, 18, 0, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -4, 18, 18, 0, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -16, 18, 15, 0, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -22, 18, 15, 0, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 15, 18, 8, -90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -23, 18, 1, 90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -23, 18, 3, 90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -16, 18, -15, 180, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 15, 18, -2, -90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -26, 26.5, 7, 90 , 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 11, 12, 5, 90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 24, 22, -9, -90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 21, 22, -10, 180, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 22, 16, -10, 180, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 24, 16, -8, -90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, 20, 20.5, 15, -90, 0));
		GreeksCastle_0_ranged_guards.add(new Location(null, -11, 7, 9, -90, 0));
		GreeksCastle_0.put("ranged_guards", GreeksCastle_0_ranged_guards);
		
		GreeksCastle_0.put("protect_radius", 40);
		GreeksCastle_0.put("production_rate", 3);
		GreeksCastle_0.put("production_needed", 1);
		GreeksCastle_0.put("production_max", 20);

		GreeksCastle_0.put("reliqua", new Location(null, -26, 21, -11, 0, 0));
		GreeksCastle_0.put("npc", new Location(Bukkit.getWorld("Realms"), 4, 1, 11, 90, 0));
		
		GreeksCastles.add(GreeksCastle_0);
		// Greeks Castle tier 1 !
		
		Greeks.put("Castle", GreeksCastles);
		// Greeks Castle !
		
		// ! Greeks Fort
		ArrayList<HashMap<String, Object>> GreeksForts = new ArrayList<HashMap<String, Object>>();
		
		// ! Greeks Fort tier 1
		HashMap<String, Object> GreeksFort_0 = new HashMap<String, Object>();

		ArrayList<Location> GreeksFort_0_heart = new ArrayList<Location>();
		GreeksFort_0_heart.add(new Location(null, -7, 12, -5, 0, 0));
		GreeksFort_0_heart.add(new Location(null, -7, 13, -5, 0, 0));
		GreeksFort_0_heart.add(new Location(null, -8, 14, -5, 0, 0));
		GreeksFort_0_heart.add(new Location(null, -7, 15, -5, 0, 0));
		GreeksFort_0_heart.add(new Location(null, -6, 14, -5, 0, 0));
		GreeksFort_0.put("heart", GreeksFort_0_heart);
		
		ArrayList<Location> GreeksFort_0_banners = new ArrayList<Location>();
		GreeksFort_0_banners.add(new Location(null, 24, 12, 16, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 10, 15, 18, 0, 0));
		GreeksFort_0_banners.add(new Location(null, -1, 11, 16, 0, 0));
		GreeksFort_0_banners.add(new Location(null, -13, 11, 16, 0, 0));
		GreeksFort_0_banners.add(new Location(null, -24, 15, 18, 0, 0));
		GreeksFort_0_banners.add(new Location(null, -19, 15, 13, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 5, 15, 13, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 10, 15, 8, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 10, 15, -8, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 15, 15, 13, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 27, 12, 13, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 27, 12, -1, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 10, 15, -8, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 15, 15, -13, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 10, 15, -18, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 5, 15, -13, 0, 0));
		GreeksFort_0_banners.add(new Location(null, -19, 15, -13, 0, 0));
		GreeksFort_0_banners.add(new Location(null, -24, 15, -18, 0, 0));
		GreeksFort_0_banners.add(new Location(null, -29, 15, -13, 0, 0));
		GreeksFort_0_banners.add(new Location(null, -24, 15, -8, 0, 0));
		GreeksFort_0_banners.add(new Location(null, -24, 15, 8, 0, 0));
		GreeksFort_0_banners.add(new Location(null, 24, 12, -4, 0, 0));
		GreeksFort_0.put("banners", GreeksFort_0_banners);

		ArrayList<Location> GreeksFort_0_melee_guards = new ArrayList<Location>();
		GreeksFort_0_melee_guards.add(new Location(null, -4, 1, 0, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -10, 1, 0, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -16, 1, 1, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -20, 1, 1, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 3, 2, -11, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -9, 1, -9, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -7, 1, -9, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -5, 1, -9, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -7, 1, -11, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 7, 1, 3, 90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 7, 1, 9, 90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 23, 1, 4, 90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 23, 1, 8, 90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 8, 7, -7, 90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 17, 1, 0, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -1, 7, 13, 180, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -13, 7, 13, 180, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 17, 1, 12, 180, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -24, 7, 13, -90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -24, 7, -13, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -6, 7, -11, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -8, 7, -11, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 11, 7, -12, 90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 10, 8, 0, 180, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 25, 8, -2, 45, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 25, 8, 14, -45, 0));
		GreeksFort_0_melee_guards.add(new Location(null, 11, 7, 12, 0, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -5, 11.5, -2, 180, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -9, 11.5, -2, 180, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -10, 11.5, -11, -90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -12, 11.5, -9, -90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -12, 11.5, -5, -90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -4, 11.5, -11, 90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -2, 11.5, -9, 90, 0));
		GreeksFort_0_melee_guards.add(new Location(null, -2, 11.5, -5, 90, 0));
		GreeksFort_0.put("melee_guards", GreeksFort_0_melee_guards);
		
		ArrayList<Location> GreeksFort_0_ranged_guards = new ArrayList<Location>();
		GreeksFort_0_ranged_guards.add(new Location(null, -18, 1, 11, 180, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, 4, 1, 11, 180, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -22, 1, 7, -90, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -24, 7, 7, -90, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -24, 7, -7, -90, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, 10, 8, 5, 90, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, 10, 8, 6, 90, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, 10, 8, 7, 90, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, 10, 7, -7, 90, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -10, 1, -6, 0, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -10, 1, -12, 0, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -4, 1, -12, 0, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -4, 1, -6, 0, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -7, 16.5, -11, 0, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -3, 16.5, -7, 90, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -11, 16.5, -7, -90, 0));
		GreeksFort_0_ranged_guards.add(new Location(null, -7, 16.5, -3, 180, 0));
		GreeksFort_0.put("ranged_guards", GreeksFort_0_ranged_guards);
		
		GreeksFort_0.put("protect_radius", 35);
		GreeksFort_0.put("production_rate", 2);
		GreeksFort_0.put("production_needed", 1);
		GreeksFort_0.put("production_max", 15);
		GreeksFort_0.put("npc", new Location(Bukkit.getWorld("Realms"), -2, 1, 5, 90, 0));
		
		GreeksForts.add(GreeksFort_0);
		// Greeks Fort tier 1 !
		
		Greeks.put("Fort", GreeksForts);
		// Greeks Fort !
		
		// ! Greeks Watchtower
		ArrayList<HashMap<String, Object>> GreeksTowers = new ArrayList<HashMap<String, Object>>();
		
		// ! Greeks Watchtower tier 1
		HashMap<String, Object> GreeksTower_0 = new HashMap<String, Object>();
		
		ArrayList<Location> GreeksTower_0_heart = new ArrayList<Location>();
		GreeksTower_0_heart.add(new Location(null, 0, 22, 0, 0, 0));
		GreeksTower_0_heart.add(new Location(null, 0, 21, 0, 0, 0));
		GreeksTower_0.put("heart", GreeksTower_0_heart);
		
		ArrayList<Location> GreeksTower_0_banners = new ArrayList<Location>();
		GreeksTower_0_banners.add(new Location(null, 2, 4, 10, 0, 0));
		GreeksTower_0_banners.add(new Location(null, -2, 4, 10, 0, 0));
		GreeksTower_0_banners.add(new Location(null, -10, 4, 2, 0, 0));
		GreeksTower_0_banners.add(new Location(null, -10, 4, -2, 0, 0));
		GreeksTower_0_banners.add(new Location(null, 10, 4, 2, 0, 0));
		GreeksTower_0_banners.add(new Location(null, 10, 4, -2, 0, 0));
		GreeksTower_0_banners.add(new Location(null, 2, 17, 7, 0, 0));
		GreeksTower_0_banners.add(new Location(null, -2, 17, 7, 0, 0));
		GreeksTower_0_banners.add(new Location(null, -7, 17, 2, 0, 0));
		GreeksTower_0_banners.add(new Location(null, -7, 17, -2, 0, 0));
		GreeksTower_0_banners.add(new Location(null, 2, 17, -7, 0, 0));
		GreeksTower_0_banners.add(new Location(null, 7, 17, -2, 0, 0));
		GreeksTower_0_banners.add(new Location(null, 7, 17, 2, 0, 0));
		GreeksTower_0.put("banners", GreeksTower_0_banners);
		
		ArrayList<Location> GreeksTower_0_melee_guards = new ArrayList<Location>();
		GreeksTower_0_melee_guards.add(new Location(null, 0, 1, 8, 0, 0));
		GreeksTower_0_melee_guards.add(new Location(null, -7, 1, 0, 90, 0));
		GreeksTower_0_melee_guards.add(new Location(null, 7, 1, 0, -90, 0));
		GreeksTower_0_melee_guards.add(new Location(null, -2, 1, -5, 0, 0));
		GreeksTower_0_melee_guards.add(new Location(null, 2, 1, -5, 0, 0));
		GreeksTower_0_melee_guards.add(new Location(null, 3, 9, 0 , 135, 0));
		GreeksTower_0_melee_guards.add(new Location(null, 2, 13, 3, 180, 0));
		GreeksTower_0_melee_guards.add(new Location(null, 0, 9, 2, -135, 0));
		GreeksTower_0_melee_guards.add(new Location(null, -3, 13, 4, 180, 0));
		GreeksTower_0_melee_guards.add(new Location(null, 0, 1, 0, 0, 0));
		GreeksTower_0_melee_guards.add(new Location(null, -1, 1.5, -8, 0, 0));
		GreeksTower_0_melee_guards.add(new Location(null, 1, 1.5, -8, 0, 0));
		GreeksTower_0_melee_guards.add(new Location(null, 5, 9, 2, 135, 0));
		GreeksTower_0_melee_guards.add(new Location(null, -2, 21, 3, -90, 0));
		GreeksTower_0_melee_guards.add(new Location(null, 3, 21, -2, 45, 0));
		GreeksTower_0_melee_guards.add(new Location(null, -2, 21, -3, 0, 0));
		GreeksTower_0.put("melee_guards", GreeksTower_0_melee_guards);
		
		ArrayList<Location> GreeksTower_0_ranged_guards = new ArrayList<Location>();
		GreeksTower_0_ranged_guards.add(new Location(null, 0, 14, 3, 180, 0));
		GreeksTower_0_ranged_guards.add(new Location(null, 4, 22.5, 3, -45, 0));
		GreeksTower_0_ranged_guards.add(new Location(null, -4, 22.5, -3, 135, 0));
		GreeksTower_0_ranged_guards.add(new Location(null, -4, 22.5, 3, 45, 0));
		GreeksTower_0_ranged_guards.add(new Location(null, 4, 22.5, -3, -135, 0));
		GreeksTower_0.put("ranged_guards", GreeksTower_0_ranged_guards);
		
		GreeksTower_0.put("protect_radius", 15);
		GreeksTower_0.put("production_rate", 1);
		GreeksTower_0.put("production_needed", 1);
		GreeksTower_0.put("production_max", 5);
		GreeksTower_0.put("npc", new Location(Bukkit.getWorld("Realms"), -4, 1, -3, -45, 0));
		
		GreeksTowers.add(GreeksTower_0);
		// Greeks Watchtower tier 1 !
		
		Greeks.put("Watchtower", GreeksTowers);
		// Greeks Watchtower !
		
		// ! Greeks Stable
		ArrayList<HashMap<String, Object>> GreeksStables = new ArrayList<HashMap<String, Object>>();
		
		// ! Greeks Stable tier 1
		HashMap<String, Object> GreeksStable_0 = new HashMap<String, Object>();
		
		ArrayList<Location> GreeksStable_0_heart = new ArrayList<Location>();
		GreeksStable_0_heart.add(new Location(null, 6, 9, 3, 0, 0));
		GreeksStable_0_heart.add(new Location(null, 6, 8, 3, 0, 0));
		GreeksStable_0_heart.add(new Location(null, 6, 7, 3, 0, 0));
		GreeksStable_0.put("heart", GreeksStable_0_heart);
		
		ArrayList<Location> GreeksStable_0_banners = new ArrayList<Location>();
		GreeksStable_0_banners.add(new Location(null, -11, 9, -1, 0, 0));
		GreeksStable_0_banners.add(new Location(null, 6, 10, 9, 0, 0));
		GreeksStable_0_banners.add(new Location(null, 13, 6, -1, 0, 0));
		GreeksStable_0.put("banners", GreeksStable_0_banners);
		
		ArrayList<Location> GreeksStable_0_melee_guards = new ArrayList<Location>();
		GreeksStable_0_melee_guards.add(new Location(null, -2, 1, 8, 0, 0));
		GreeksStable_0_melee_guards.add(new Location(null, 4, 1, 9, 0, 0));
		GreeksStable_0_melee_guards.add(new Location(null, 8, 1, 9, 0, 0));
		GreeksStable_0_melee_guards.add(new Location(null, 13, 1, -5, -90, 0));
		GreeksStable_0_melee_guards.add(new Location(null, -11, 1, -5, 135, 0));
		GreeksStable_0_melee_guards.add(new Location(null, -11, 1, 8, 45, 0));
		GreeksStable_0_melee_guards.add(new Location(null, 7, 1, 1, 135, 0));
		GreeksStable_0.put("melee_guards", GreeksStable_0_melee_guards);
		
		ArrayList<Location> GreeksStable_0_ranged_guards = new ArrayList<Location>();
		GreeksStable_0_ranged_guards.add(new Location(null, 10, 6, 0, -90, 0));
		GreeksStable_0_ranged_guards.add(new Location(null, 7, 7, 7, 0, 0));
		GreeksStable_0_ranged_guards.add(new Location(null, 0, 6.5, -3, 180, 0));
		GreeksStable_0_ranged_guards.add(new Location(null, -9, 6.5, -1, 90, 0));
		GreeksStable_0.put("ranged_guards", GreeksStable_0_ranged_guards);
		
		GreeksStable_0.put("protect_radius", 20);
		GreeksStable_0.put("production_rate", 1);
		GreeksStable_0.put("production_needed", 2);
		GreeksStable_0.put("production_max", 1);
		GreeksStable_0.put("npc", new Location(Bukkit.getWorld("Realms"), 1, 1, 5, 0, 0));
		
		GreeksStables.add(GreeksStable_0);
		// Greeks Stable tier 1 !
		
		Greeks.put("Stable", GreeksStables);
		// Greeks Stable !
		
		// ! Greeks Temple
		ArrayList<HashMap<String, Object>> GreeksTemples = new ArrayList<HashMap<String, Object>>();
		
		// ! Greeks Temple tier 1
		HashMap<String, Object> GreeksTemple_0 = new HashMap<String, Object>();
		
		ArrayList<Location> GreeksTemple_0_heart = new ArrayList<Location>();
		GreeksTemple_0_heart.add(new Location(null, -1, 4, -12, 0, 0));
		GreeksTemple_0_heart.add(new Location(null, 0, 5, -12, 0, 0));
		GreeksTemple_0_heart.add(new Location(null, -1, 6, -12, 0, 0));
		GreeksTemple_0_heart.add(new Location(null, 1, 6, -12, 0, 0));
		GreeksTemple_0_heart.add(new Location(null, 1, 4, -12, 0, 0));
		GreeksTemple_0.put("heart", GreeksTemple_0_heart);
		
		ArrayList<Location> GreeksTemple_0_banners = new ArrayList<Location>();
		GreeksTemple_0_banners.add(new Location(null, 12, 15, 34, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, -12, 15, 34, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, -8, 8, 13, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, 8, 8, 13, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, 22, 15, 24, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, 22, 15, 8, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, 22, 15, -8, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, 22, 15, -24, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, 12, 15, -34, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, -12, 15, -34, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, -22, 15, -24, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, -22, 15, -8, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, -22, 15, 8, 0, 0));
		GreeksTemple_0_banners.add(new Location(null, -22, 15, 24, 0, 0));
		GreeksTemple_0.put("banners", GreeksTemple_0_banners);
		
		ArrayList<Location> GreeksTemple_0_melee_guards = new ArrayList<Location>();
		GreeksTemple_0_melee_guards.add(new Location(null, -5, 1, 14, 0, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 5, 1, 14, 0, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -6, 1, 8, -90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -6, 1, 2, -90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 6, 1, 2, 90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 6, 1, 8, 90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -9, 1, -11, -90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -9, 1, -13, -90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -1, 1, -21, 0, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 1, 1, -21, 0, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 9, 1, -13, 90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 9, 1, -11, 90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -1, 4, -11, 0, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 1, 4, -11, 0, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -10, 1, -2, -135, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 10, 1, -2, 135, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 10, 1, -22, 45, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -10, 1, -22, -45, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -15, 1, 11, 90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -15, 1, 0, 90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -15, 1, -24, 90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, -12, 1, -27, 180, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 12, 1, -27, 180, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 15, 1, -24, -90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 15, 1, 0, -90, 0));
		GreeksTemple_0_melee_guards.add(new Location(null, 15, 1, 11, -90, 0));
		GreeksTemple_0.put("melee_guards", GreeksTemple_0_melee_guards);
		
		ArrayList<Location> GreeksTemple_0_ranged_guards = new ArrayList<Location>();
		GreeksTemple_0_ranged_guards.add(new Location(null, -2, 1, 1, 0, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 2, 1, 1, 0, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 6, 1, 14, 0, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -6, 1, 14, 0, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -3, 1, -9, 45, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 3, 1, -9, -45, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 3, 1, -15, -135, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -3, 1, -15, 135, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 16, 1, 32, 0, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 8, 1, 32, 0, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -8, 1, 32, 0, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -16, 1, 32, 0, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 20, 1, 28, -90, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 20, 1, 12, -90, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 20, 1, -4, -90, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 20, 1, -20, -90, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -20, 1, 28, 90, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -20, 1, 12, 90, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -20, 1, -4, 90, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -20, 1, -20, 90, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -8, 1, -32, 180, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 8, 1, -32, 180, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, -18, 1, 30, -135, 0));
		GreeksTemple_0_ranged_guards.add(new Location(null, 18, 1, 30, 135, 0));
		GreeksTemple_0.put("ranged_guards", GreeksTemple_0_ranged_guards);
		
		GreeksTemple_0.put("protect_radius", 45);
		GreeksTemple_0.put("production_rate", 1);
		GreeksTemple_0.put("production_needed", 120);
		GreeksTemple_0.put("production_max", 1);
		GreeksTemple_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, 1, 16, 0, 0));
		
		GreeksTemples.add(GreeksTemple_0);
		// Greeks Temple tier 1 !
		
		Greeks.put("Temple", GreeksTemples);
		// Greeks Temple !
		
		Structures_ruleset.put("Greeks", Greeks);
		// Greeks !
		
		
		
		
		
		
		
		
		
		
		
		// ! Egyptians
		HashMap<String, ArrayList<HashMap<String, Object>>> Egyptians = new HashMap<String, ArrayList<HashMap<String, Object>>>();
		
		// ! Egyptians Castle
		ArrayList<HashMap<String, Object>> EgyptiansCastles = new ArrayList<HashMap<String, Object>>();
		
		// ! Egyptians Castle tier 1
		HashMap<String, Object> EgyptiansCastle_0 = new HashMap<String, Object>();

		ArrayList<Location> EgyptiansCastle_0_heart = new ArrayList<Location>();
		EgyptiansCastle_0_heart.add(new Location(null, -1, -13, -23, 0, 0));
		EgyptiansCastle_0_heart.add(new Location(null, -1, -12, -23, 0, 0));
		EgyptiansCastle_0_heart.add(new Location(null, -1, -11, -23, 0, 0));
		EgyptiansCastle_0_heart.add(new Location(null, 1, -13, -23, 0, 0));
		EgyptiansCastle_0_heart.add(new Location(null, 1, -12, -23, 0, 0));
		EgyptiansCastle_0_heart.add(new Location(null, 1, -11, -23, 0, 0));
		EgyptiansCastle_0.put("heart", EgyptiansCastle_0_heart);

		ArrayList<Location> EgyptiansCastle_0_banners = new ArrayList<Location>();
		EgyptiansCastle_0_banners.add(new Location(null, 11, -15, 33, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, 18, -15, 28, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, 32, -15, 22, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, 32, -15, -1, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, 32, -15, -24, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, 21, -15, -29, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -21, -15, -29, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -32, -15, -24, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -32, -15, -1, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -32, -15, 22, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -18, -15, 28, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -11, -15, 33, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -22, 8, 24, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -25, 8, 21, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -25, 8, -23, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, -22, 8, -26, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, 22, 8, -26, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, 25, 8, -23, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, 25, 8, 21, 0, 0));
		EgyptiansCastle_0_banners.add(new Location(null, 22, 8, 24, 0, 0));
		EgyptiansCastle_0.put("banners", EgyptiansCastle_0_banners);
			
		ArrayList<Location> EgyptiansCastle_0_melee_guards = new ArrayList<Location>();
		EgyptiansCastle_0_melee_guards.add(new Location(null, 11, -24, 33, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 0, -24, 33, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -11, -24, 33, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -19, -24, 31, 90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -19, -24, 25, 90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -28, -24, 22, 90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -28, -24, -1, 90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -28, -24, -24, 90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 19, -24, 31, -90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 19, -24, 25, -90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 28, -24, 22, -90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 28, -24, -1, -90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 28, -24, -24, -90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -4, -22, 18, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 0, -22, 18, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 4, -22, 18, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 3, -18, 12, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 3, -18, 6, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -3, -18, 6, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -3, -18, 12, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -9, -14, -23, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -5, -14, -23, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -2, -14, -20, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 2, -14, -20, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 5, -14, -23, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 9, -14, -23, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -23, -24, 17, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -22, -24, 20, 180, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 23, -24, 17, 0, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 22, -24, 20, 180, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 21, -14, 6, 90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, 21, -14, 11, 90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -21, -14, 6, -90, 0));
		EgyptiansCastle_0_melee_guards.add(new Location(null, -21, -14, 11, -90, 0));
		EgyptiansCastle_0.put("melee_guards", EgyptiansCastle_0_melee_guards);

		ArrayList<Location> EgyptiansCastle_0_ranged_guards = new ArrayList<Location>();
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 7, -22, 15, 0, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -7, -22, 15, 0, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 3, -22, 26, 180, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -3, -22, 26, 180, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 6, -18, 3, 0, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 5, -18, 3, 0, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -5, -18, 3, 0, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -6, -18, 3, 0, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -3, -17, -4, -90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -3, -17, -8, -90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -3, -17, -10, -90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 3, -17, -4, 90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 3, -17, -8, 90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 3, -17, -10, 90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -21, -14, -18, -90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -21, -14, -22, -90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 21, -14, -18, 90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 21, -14, -22, 90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 7, -14, -23, 0, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -7, -14, -23, 0, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 9, -12.5, 19, 90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -9, -12.5, 19, -90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -6, -12.5, 24, 180, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 6, -12.5, 24, 180, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -24, -14, 11, -90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, -24, -14, 6, -90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 24, -14, 6, 90, 0));
		EgyptiansCastle_0_ranged_guards.add(new Location(null, 24, -14, 11, 90, 0));
		EgyptiansCastle_0.put("ranged_guards", EgyptiansCastle_0_ranged_guards);
		
		EgyptiansCastle_0.put("protect_radius", 43);
		EgyptiansCastle_0.put("production_rate", 3);
		EgyptiansCastle_0.put("production_needed", 1);
		EgyptiansCastle_0.put("production_max", 20);
		EgyptiansCastle_0.put("reliqua", new Location(null, 0, -10, 28, 0, 0));
		EgyptiansCastle_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, -22, 25, 180, 0));
		
		EgyptiansCastles.add(EgyptiansCastle_0);
		// Egyptians Castle tier 1 !
		
		Egyptians.put("Castle", EgyptiansCastles);
		// Egyptians Castle !
		
		// ! Egyptians Fort
		ArrayList<HashMap<String, Object>> EgyptiansForts = new ArrayList<HashMap<String, Object>>();
		
		// ! Egyptians Fort tier 1
		HashMap<String, Object> EgyptiansFort_0 = new HashMap<String, Object>();

		ArrayList<Location> EgyptiansFort_0_heart = new ArrayList<Location>();
		EgyptiansFort_0_heart.add(new Location(null, 0, 12, 4, 0, 0));
		EgyptiansFort_0_heart.add(new Location(null, 0, 13, 4, 0, 0));
		EgyptiansFort_0_heart.add(new Location(null, -1, 14, 4, 0, 0));
		EgyptiansFort_0_heart.add(new Location(null, 1, 14, 4, 0, 0));
		EgyptiansFort_0_heart.add(new Location(null, 0, 15, 4 , 0, 0));
		EgyptiansFort_0.put("heart", EgyptiansFort_0_heart);
		
		ArrayList<Location> EgyptiansFort_0_banners = new ArrayList<Location>();
		EgyptiansFort_0_banners.add(new Location(null, 27, 16, 22, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, 22, 16, 27, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, 17, 16, 22, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, 22, 16, 17, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, 22, 16, -17, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, 27, 16, -22, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, 22, 16, -27, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, 17, 16, -22, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, -17, 16, -22, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, -22, 16, -27, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, -27, 16, -22, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, -22, 16, -17, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, -22, 16, 17, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, -27, 16, 22, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, -22, 16, 27, 0, 0));
		EgyptiansFort_0_banners.add(new Location(null, -17, 16, 22, 0, 0));
		EgyptiansFort_0.put("banners", EgyptiansFort_0_banners);

		ArrayList<Location> EgyptiansFort_0_melee_guards = new ArrayList<Location>();
		EgyptiansFort_0_melee_guards.add(new Location(null, -11, -3, 27, 0, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 10, -3, 27, 0, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -5, -3, 11, 0, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 5, -3, 11, 0, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 0, 4, -7, 0, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 0, 8, -6, 0, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -3, -3, 22, -90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 3, -3, 22, 90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -12, -3, 4, 90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -12, -3, -4, 90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 12, -3, 4, -90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 12, -3, -4, -90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 7, 0, 7, 135, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 7, 0, -7, 45, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -7, 0, -7, -45, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -7, 0, 7, -135, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 7, 4, 2, 90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 7, 4, -2, 90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -7, 4, -2, -90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -7, 4, 2, -90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 5, 8, 0, 90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 0, 8, 6, 180, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 4, 12, -4, 90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 4, 12, 2, 90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 4, 12, 4, 90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -4, 12, -4, -90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -4, 12, 2, -90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, -4, 12, 4, -90, 0));
		EgyptiansFort_0_melee_guards.add(new Location(null, 0, -3, -12, 180, 0));
		EgyptiansFort_0.put("melee_guards", EgyptiansFort_0_melee_guards);
		
		ArrayList<Location> EgyptiansFort_0_ranged_guards = new ArrayList<Location>();
		EgyptiansFort_0_ranged_guards.add(new Location(null, -2, 12, -5, 0, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 0, 12, -5, 0, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 2, 12, -5, 0, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, -21, -3, 4, -90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, -21, -3, -4, -90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 21, -3, 4, 90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 21, -3, -4, 90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, -12, -3, 25, 0, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, -8, -3, 25, 0, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 8, -3, 25, 0, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 12, -3, 25, 0, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, -16, 8.5, -21, 0, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 16, 8.5, -21, 0, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 21, 8.5, -16, 90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 21, 8.5, 16, 90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, -21, 8.5, 16, -90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, -21, 8.5, -16, -90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 0, 8.5, -17, 90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 0, 8.5, -14, -90, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, 16, 8.5, 21, 180, 0));
		EgyptiansFort_0_ranged_guards.add(new Location(null, -16, 8.5, 21, 180, 0));
		EgyptiansFort_0.put("ranged_guards", EgyptiansFort_0_ranged_guards);
		
		EgyptiansFort_0.put("protect_radius", 35);
		EgyptiansFort_0.put("production_rate", 2);
		EgyptiansFort_0.put("production_needed", 1);
		EgyptiansFort_0.put("production_max", 15);
		EgyptiansFort_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, 0, 4, 0, 0));
		
		EgyptiansForts.add(EgyptiansFort_0);
		// Egyptians Fort tier 1 !
		
		Egyptians.put("Fort", EgyptiansForts);
		// Egyptians Fort !
		
		// ! Egyptians Watchtower
		ArrayList<HashMap<String, Object>> EgyptiansTowers = new ArrayList<HashMap<String, Object>>();
		
		// ! Egyptians Watchtower tier 1
		HashMap<String, Object> EgyptiansTower_0 = new HashMap<String, Object>();
		
		ArrayList<Location> EgyptiansTower_0_heart = new ArrayList<Location>();
		EgyptiansTower_0_heart.add(new Location(null, 0, 23, 0, 0, 0));
		EgyptiansTower_0_heart.add(new Location(null, 0, 22, 0, 0, 0));
		EgyptiansTower_0.put("heart", EgyptiansTower_0_heart);
		
		ArrayList<Location> EgyptiansTower_0_banners = new ArrayList<Location>();
		EgyptiansTower_0_banners.add(new Location(null, -1, 8, 8, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, 1, 8, 8, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, 8, 8, 1, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, 8, 8, -1, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, 1, 8, -8, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, -1, 8, -8, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, -8, 8, -1, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, -8, 8, 1, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, 3, 33, 0, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, 0, 33, -3, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, -3, 33, 0, 0, 0));
		EgyptiansTower_0_banners.add(new Location(null, 0, 33, 3, 0, 0));
		EgyptiansTower_0.put("banners", EgyptiansTower_0_banners);
		
		ArrayList<Location> EgyptiansTower_0_melee_guards = new ArrayList<Location>();
		EgyptiansTower_0_melee_guards.add(new Location(null, -7, -2, 7, 0, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, -3, -2, 7, 0, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, 3, -2, 7, 0, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, 7, -2, 7, 0, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, 7, -2, -7, 180, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, -7, -2, -7, 180, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, -3, -1, 4, -90, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, -3, -1, 2, -90, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, 2, -1, -4, 0, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, -2, -1, -4, 0, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, 1, 9, 1, -45, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, -1, 9, 1, 45, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, 1, 9, -1, -135, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, -1, 9, -1, 135, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, -1, 20.5, -1, -45, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, 1, 20.5, -1, 45, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, 1, 20.0, 1, 135, 0));
		EgyptiansTower_0_melee_guards.add(new Location(null, -1, 20.5, 1, -135, 0));
		EgyptiansTower_0.put("melee_guards", EgyptiansTower_0_melee_guards);
		
		ArrayList<Location> EgyptiansTower_0_ranged_guards = new ArrayList<Location>();
		EgyptiansTower_0_ranged_guards.add(new Location(null, 0, 8.5, 6, 0, 0));
		EgyptiansTower_0_ranged_guards.add(new Location(null, 0, 21, 5, 0, 0));
		EgyptiansTower_0_ranged_guards.add(new Location(null, 0, 21, -5, 180, 0));
		EgyptiansTower_0_ranged_guards.add(new Location(null, -5, 21, 0, 90, 0));
		EgyptiansTower_0_ranged_guards.add(new Location(null, 5, 21, 0, -90, 0));
		EgyptiansTower_0.put("ranged_guards", EgyptiansTower_0_ranged_guards);
		
		EgyptiansTower_0.put("protect_radius", 15);
		EgyptiansTower_0.put("production_rate", 1);
		EgyptiansTower_0.put("production_needed", 1);
		EgyptiansTower_0.put("production_max", 5);
		EgyptiansTower_0.put("npc", new Location(Bukkit.getWorld("Realms"), 4, -1, 2, 90, 0));
		
		EgyptiansTowers.add(EgyptiansTower_0);
		// Egyptians Watchtower tier 1 !
		
		Egyptians.put("Watchtower", EgyptiansTowers);
		// Egyptians Watchtower !
		
		// ! Egyptians Stable
		ArrayList<HashMap<String, Object>> EgyptiansStables = new ArrayList<HashMap<String, Object>>();
		
		// ! Egyptians Stable tier 1
		HashMap<String, Object> EgyptiansStable_0 = new HashMap<String, Object>();
		
		ArrayList<Location> EgyptiansStable_0_heart = new ArrayList<Location>();
		EgyptiansStable_0_heart.add(new Location(null, 11, 13, -4, 0, 0));
		EgyptiansStable_0_heart.add(new Location(null, 11, 14, -4, 0, 0));
		EgyptiansStable_0_heart.add(new Location(null, 11, 15, -4, 0, 0));
		EgyptiansStable_0.put("heart", EgyptiansStable_0_heart);
		
		ArrayList<Location> EgyptiansStable_0_banners = new ArrayList<Location>();
		EgyptiansStable_0_banners.add(new Location(null, 4, 5, -7, 0, 0));
		EgyptiansStable_0_banners.add(new Location(null, -2, 5, -7, 0, 0));
		EgyptiansStable_0_banners.add(new Location(null, -10, 8, 2, 0, 0));
		EgyptiansStable_0_banners.add(new Location(null, -6, 8, 6, 0, 0));
		EgyptiansStable_0_banners.add(new Location(null, 12, 13, 2, 0, 0));
		EgyptiansStable_0_banners.add(new Location(null, 7, 13, -3, 0, 0));
		EgyptiansStable_0_banners.add(new Location(null, 12, 13, -8, 0, 0));
		EgyptiansStable_0_banners.add(new Location(null, 17, 13, -3, 0, 0));
		EgyptiansStable_0.put("banners", EgyptiansStable_0_banners);
		
		ArrayList<Location> EgyptiansStable_0_melee_guards = new ArrayList<Location>();
		EgyptiansStable_0_melee_guards.add(new Location(null, -1, 1, 6, 0, 0));
		EgyptiansStable_0_melee_guards.add(new Location(null, 3, 1, 6, 0, 0));
		EgyptiansStable_0_melee_guards.add(new Location(null, 13, 1, 4, 0, 0));
		EgyptiansStable_0_melee_guards.add(new Location(null, 16, 1, -8, -135, 0));
		EgyptiansStable_0_melee_guards.add(new Location(null, 4, 1, -8, 180, 0));
		EgyptiansStable_0_melee_guards.add(new Location(null, -2, 1, -8, 180, 0));
		EgyptiansStable_0_melee_guards.add(new Location(null, -10, 1, -8, 135, 0));
		EgyptiansStable_0.put("melee_guards", EgyptiansStable_0_melee_guards);
		
		ArrayList<Location> EgyptiansStable_0_ranged_guards = new ArrayList<Location>();
		EgyptiansStable_0_ranged_guards.add(new Location(null, -6, 8, -8, 180, 0));
		EgyptiansStable_0_ranged_guards.add(new Location(null, 12, 14, -7, 180, 0));
		EgyptiansStable_0_ranged_guards.add(new Location(null, 12, 14, 1, 0, 0));
		EgyptiansStable_0_ranged_guards.add(new Location(null, -9, 9.5, 5, 0, 0));
		EgyptiansStable_0.put("ranged_guards", EgyptiansStable_0_ranged_guards);
		
		EgyptiansStable_0.put("protect_radius", 20);
		EgyptiansStable_0.put("production_rate", 1);
		EgyptiansStable_0.put("production_needed", 2);
		EgyptiansStable_0.put("production_max", 1);
		EgyptiansStable_0.put("npc", new Location(Bukkit.getWorld("Realms"), -1, 1, -2, -90, 0));
		
		EgyptiansStables.add(EgyptiansStable_0);
		// Egyptians Stable tier 1 !
		
		Egyptians.put("Stable", EgyptiansStables);
		// Egyptians Stable !
		
		// ! Egyptians Temple
		ArrayList<HashMap<String, Object>> EgyptiansTemples = new ArrayList<HashMap<String, Object>>();
		
		// ! Egyptians Temple tier 1
		HashMap<String, Object> EgyptiansTemple_0 = new HashMap<String, Object>();
		
		ArrayList<Location> EgyptiansTemple_0_heart = new ArrayList<Location>();
		EgyptiansTemple_0_heart.add(new Location(null, -1, -22, -29, 0, 0));
		EgyptiansTemple_0_heart.add(new Location(null, -1, -20, -29, 0, 0));
		EgyptiansTemple_0_heart.add(new Location(null, 0, -21, -29, 0, 0));
		EgyptiansTemple_0_heart.add(new Location(null, 1, -20, -29, 0, 0));
		EgyptiansTemple_0_heart.add(new Location(null, 1, -22, -29, 0, 0));
		EgyptiansTemple_0.put("heart", EgyptiansTemple_0_heart);
		
		ArrayList<Location> EgyptiansTemple_0_banners = new ArrayList<Location>();
		EgyptiansTemple_0_banners.add(new Location(null, -7, -12, 18, 0, 0));
		EgyptiansTemple_0_banners.add(new Location(null, 0, 12, 18, 0, 0));
		EgyptiansTemple_0_banners.add(new Location(null, 7, -12, 18, 0, 0));
		EgyptiansTemple_0_banners.add(new Location(null, 0, -5, -20, 0, 0));
		EgyptiansTemple_0.put("banners", EgyptiansTemple_0_banners);
		
		ArrayList<Location> EgyptiansTemple_0_melee_guards = new ArrayList<Location>();
		EgyptiansTemple_0_melee_guards.add(new Location(null, -14, -18, 18, 0, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -4, -18, 16, 0, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 4, -18, 16, 0, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 14, -18, 18, 0, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 4, -18, -1, 0, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -4, -18, -1, 0, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -10, -18, 10, -90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 10, -18, 10, 90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -2, -23, -17, -90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 2, -23, -17, 90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -2, -22.5, -23, -90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -2, -22.5, -24, -90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -2,  -22.5, -25, -90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 2, -22.5, -23, 90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 2, -22.5, -24, 90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 2,  -22.5, -25, 90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 25, -18, 8, -90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 25, -18, -7, -90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 25, -18, -22, -90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -25, -18, 8, 90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -25, -18, -7, 90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -25, -18, -22, 90, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, -15, -18, -32, 180, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 0, -18, -32, 180, 0));
		EgyptiansTemple_0_melee_guards.add(new Location(null, 15, -18, -32, 180, 0));
		EgyptiansTemple_0.put("melee_guards", EgyptiansTemple_0_melee_guards);
		
		ArrayList<Location> EgyptiansTemple_0_ranged_guards = new ArrayList<Location>();
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 0, -10, -24, 180, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 5, -6, -21, 180, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 1, -7, -20, 180, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, -1, -7, -20, 180, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, -5, -6, -21, 180, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, -7, -11, 16, 0, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, -1, -11, 16, 0, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 1, -11, 16, 0, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 7, -11, 16, 0, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, -13, -18, 21, -90, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 13, -18, 21, 90, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, -9, -18, 5, -90, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 9, -18, 5, 90, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, -1, -22.5, -10, -90, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 1, -22.5, -10, 90, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, -2, -22.5, -30, 0, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, -1, -22.5, -27, 0, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 1, -22.5, -27, 0, 0));
		EgyptiansTemple_0_ranged_guards.add(new Location(null, 2, -22.5, -30, 0, 0));
		EgyptiansTemple_0.put("ranged_guards", EgyptiansTemple_0_ranged_guards);
		
		EgyptiansTemple_0.put("protect_radius", 45);
		EgyptiansTemple_0.put("production_rate", 1);
		EgyptiansTemple_0.put("production_needed", 120);
		EgyptiansTemple_0.put("production_max", 1);
		EgyptiansTemple_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, -18, 3, 0, 0));
		
		EgyptiansTemples.add(EgyptiansTemple_0);
		// Egyptians Temple tier 1 !
		
		Egyptians.put("Temple", EgyptiansTemples);
		// Egyptians Temple !
		
		Structures_ruleset.put("Egyptians", Egyptians);
		// Egyptians !
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// ! General
		HashMap<String, ArrayList<HashMap<String, Object>>> General = new HashMap<String, ArrayList<HashMap<String, Object>>>();
		
		// ! General Castle
		ArrayList<HashMap<String, Object>> Castle = new ArrayList<HashMap<String, Object>>();
		
		// ! General Castle tier 1
		HashMap<String, Object> Castle_0 = new HashMap<String, Object>();
		ArrayList<Location> Castle_0_heart = new ArrayList<Location>();
		Castle_0_heart.add(new Location(null, 2, 18, -3, 0, 0));
		Castle_0_heart.add(new Location(null, 2, 19, -3, 0, 0));
		Castle_0_heart.add(new Location(null, 2, 20, -3, 0, 0));
		Castle_0_heart.add(new Location(null, -2, 18, 3, 0, 0));
		Castle_0_heart.add(new Location(null, -2, 19, 3, 0, 0));
		Castle_0_heart.add(new Location(null, -2, 20, 3, 0, 0));
		Castle_0.put("heart", Castle_0_heart);
		ArrayList<Location> Castle_0_banners = new ArrayList<Location>();
		Castle_0_banners.add(new Location(null, 0, 15, 19, 0, 0));
		Castle_0_banners.add(new Location(null, -18, 15, 0, 0, 0));
		Castle_0_banners.add(new Location(null, 0, 15, -17, 0, 0));
		Castle_0_banners.add(new Location(null, 18, 15, 0, 0, 0));
		
		Castle_0_banners.add(new Location(null, -3, 4, 22, 0, 0));
		Castle_0_banners.add(new Location(null, 3, 4, 22, 0, 0));
		Castle_0_banners.add(new Location(null, 2, 4, 14, 0, 0));
		Castle_0_banners.add(new Location(null, -2, 4, 14, 0, 0));
		
		Castle_0_banners.add(new Location(null, 20, 32, 21, 0, 0));
		Castle_0_banners.add(new Location(null, -20, 32, 21, 0, 0));
		Castle_0_banners.add(new Location(null, -20, 32, -19, 0, 0));
		Castle_0_banners.add(new Location(null, 20, 32, -19, 0, 0));
		Castle_0.put("banners", Castle_0_banners);
		ArrayList<Location> Castle_0_melee_guards = new ArrayList<Location>();
		Castle_0_melee_guards.add(new Location(null, -11, 1, 2, -90, 0));
		Castle_0_melee_guards.add(new Location(null, -11, 1, -2, -90, 0));
		Castle_0_melee_guards.add(new Location(null, -1, 1, -12, 0, 0));
		Castle_0_melee_guards.add(new Location(null, 1, 1, -12, 0, 0));
		Castle_0_melee_guards.add(new Location(null, 11, 1, -2, 0, 0));
		Castle_0_melee_guards.add(new Location(null, 11, 1, 2, 0, 0));
		Castle_0_melee_guards.add(new Location(null, 4, 6, 3, 135, 0));
		Castle_0_melee_guards.add(new Location(null, 3, 6, 4, 135, 0));
		Castle_0_melee_guards.add(new Location(null, 3, 12, 5, 135, 0));
		Castle_0_melee_guards.add(new Location(null, 7, 19, 0, 90, 0));
		Castle_0_melee_guards.add(new Location(null, 0, 19, 7, 180, 0));
		Castle_0_melee_guards.add(new Location(null, -7, 19, 0, -90, 0));
		Castle_0_melee_guards.add(new Location(null, 0, 19, -7, 0, 0));
		Castle_0.put("melee_guards", Castle_0_melee_guards);
		ArrayList<Location> Castle_0_ranged_guards = new ArrayList<Location>();
		Castle_0_ranged_guards.add(new Location(null, 4, 1, 19, 180, 0));
		Castle_0_ranged_guards.add(new Location(null, -4, 1, 19, 180, 0));
		Castle_0_ranged_guards.add(new Location(null, 2, 11, 11, 180, 0));
		Castle_0_ranged_guards.add(new Location(null, -2, 11, 11, 180, 0));
		Castle_0_ranged_guards.add(new Location(null, 2, 11, -11, 0, 0));
		Castle_0_ranged_guards.add(new Location(null, -2, 11, -11, 0, 0));
		Castle_0_ranged_guards.add(new Location(null, 0, 17, -19, 0, 0));
		Castle_0_ranged_guards.add(new Location(null, -20, 17, 0, -90, 0));
		Castle_0_ranged_guards.add(new Location(null, 0, 17, 21, 180, 0));
		Castle_0_ranged_guards.add(new Location(null, 20, 17, 0, 90, 0));
		Castle_0.put("ranged_guards", Castle_0_ranged_guards);
		Castle_0.put("protect_radius", 40);
		Castle_0.put("production_rate", 3);
		Castle_0.put("production_needed", 1);
		Castle_0.put("production_max", 20);
		Castle_0.put("npc", new Location(Bukkit.getWorld("Realms"), 3, 1, 4, 135, 0));
		Castle.add(Castle_0);
		// General Castle tier 1 !
		
		General.put("Castle", Castle);
		// General Castle !

		// ! General Fort
		ArrayList<HashMap<String, Object>> Fort = new ArrayList<HashMap<String, Object>>();
				
		// ! General Fort tier 1
		HashMap<String, Object> Fort_0 = new HashMap<String, Object>();
		ArrayList<Location> Fort_0_heart = new ArrayList<Location>();
		Fort_0_heart.add(new Location(null, 0, 61, 2, 0, 0));
		Fort_0_heart.add(new Location(null, 0, 62, 2, 0, 0));
		Fort_0_heart.add(new Location(null, -1, 63, 2, 0, 0));
		Fort_0_heart.add(new Location(null, 0, 64, 2, 0, 0));
		Fort_0_heart.add(new Location(null, 1, 63, 2, 0, 0));
		Fort_0.put("heart", Fort_0_heart);
		ArrayList<Location> Fort_0_banners = new ArrayList<Location>();
		Fort_0_banners.add(new Location(null, 15, 19, 23, 0, 0));
		Fort_0_banners.add(new Location(null, 15, 39, 23, 0, 0));
		Fort_0_banners.add(new Location(null, -15, 19, 23, 0, 0));
		Fort_0_banners.add(new Location(null, -15, 39, 23, 0, 0));
		Fort_0_banners.add(new Location(null, -21, 19, 17, 0, 0));
		Fort_0_banners.add(new Location(null, -21, 39, 17, 0, 0));
		Fort_0_banners.add(new Location(null, -21, 19, -13, 0, 0));
		Fort_0_banners.add(new Location(null, -21, 39, -13, 0, 0));
		Fort_0_banners.add(new Location(null, -15, 19, -19, 0, 0));
		Fort_0_banners.add(new Location(null, -15, 39, -19, 0, 0));
		Fort_0_banners.add(new Location(null, 15, 19, -19, 0, 0));
		Fort_0_banners.add(new Location(null, 15, 39, -19, 0, 0));
		Fort_0_banners.add(new Location(null, 21, 19, -13, 0, 0));
		Fort_0_banners.add(new Location(null, 21, 39, -13, 0, 0));
		Fort_0_banners.add(new Location(null, 21, 19, 17, 0, 0));
		Fort_0_banners.add(new Location(null, 21, 39, 17, 0, 0));
		Fort_0_banners.add(new Location(null, 4, 5, 19, 0, 0));
		Fort_0_banners.add(new Location(null, -4, 5, 19, 0, 0));
		Fort_0.put("banners", Fort_0_banners);
		ArrayList<Location> Fort_0_melee_guards = new ArrayList<Location>();
		Fort_0_melee_guards.add(new Location(null, 5, 0, 3, 90, 0));
		Fort_0_melee_guards.add(new Location(null, -5, 0, 3, -90, 0));
		Fort_0_melee_guards.add(new Location(null, 8, 0, 14, 90, 0));
		Fort_0_melee_guards.add(new Location(null, -8, 0, 14, -90, 0));
		Fort_0_melee_guards.add(new Location(null, -2, 20, 2, 90, 0));
		Fort_0_melee_guards.add(new Location(null, -2, 20, 3, 90, 0));
		Fort_0_melee_guards.add(new Location(null, -1, 31, -3, 0, 0));
		Fort_0_melee_guards.add(new Location(null, 1, 31, -3, 0, 0));
		Fort_0_melee_guards.add(new Location(null, -1, 31, 7, 180, 0));
		Fort_0_melee_guards.add(new Location(null, 1, 31, 7, 180, 0));
		Fort_0_melee_guards.add(new Location(null, 0, 61, 8, 180, 0));
		Fort_0_melee_guards.add(new Location(null, 0, 61, -4, 0, 0));
		Fort_0_melee_guards.add(new Location(null, -6, 61, 2, -90, 0));
		Fort_0_melee_guards.add(new Location(null, 6, 61, 2, 90, 0));
		Fort_0.put("melee_guards", Fort_0_melee_guards);
		ArrayList<Location> Fort_0_ranged_guards = new ArrayList<Location>();
		Fort_0_ranged_guards.add(new Location(null, -15, 20, 2, 90, 0));
		Fort_0_ranged_guards.add(new Location(null, 0, 20, 17, 0, 0));
		Fort_0_ranged_guards.add(new Location(null, 15, 20, 2, -90, 0));
		Fort_0_ranged_guards.add(new Location(null, 0, 20, -13, 180, 0));

		Fort_0_ranged_guards.add(new Location(null, 8, 31, 10, -45, 0));
		Fort_0_ranged_guards.add(new Location(null, -8, 31, 10, 45, 0));
		Fort_0_ranged_guards.add(new Location(null, -8, 31, -6, 135, 0));
		Fort_0_ranged_guards.add(new Location(null, 8, 31, -6, -135, 0));
		Fort_0_ranged_guards.add(new Location(null, 0, 51, 15, 0, 0));
		
		Fort_0_ranged_guards.add(new Location(null, 6, 61, -4, 45, 0));
		Fort_0_ranged_guards.add(new Location(null, -6, 61, -4, -45, 0));
		Fort_0.put("ranged_guards", Fort_0_ranged_guards);
		Fort_0.put("protect_radius", 40);
		Fort_0.put("production_rate", 2);
		Fort_0.put("production_needed", 1);
		Fort_0.put("production_max", 15);
		Fort_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, 1, -2, 0, 0));
		Fort.add(Fort_0);
		// General Fort tier 1 !
				
		General.put("Fort", Fort);
		// General Fort !
		
		// ! General Watchtower
		ArrayList<HashMap<String, Object>> Watchtower = new ArrayList<HashMap<String, Object>>();

		// ! General Watchtower tier 1
		HashMap<String, Object> Watchtower_0 = new HashMap<String, Object>();
		ArrayList<Location> Watchtower_0_heart = new ArrayList<Location>();
		Watchtower_0_heart.add(new Location(null, 0, 0, 0, 0, 0));
		Watchtower_0_heart.add(new Location(null, 0, 1, 0, 0, 0));
		Watchtower_0.put("heart", Watchtower_0_heart);
		ArrayList<Location> Watchtower_0_banners = new ArrayList<Location>();
		Watchtower_0_banners.add(new Location(null, 7, 5, 5, 0, 0));
		Watchtower_0_banners.add(new Location(null, 5, 5, 7, 0, 0));
		Watchtower_0_banners.add(new Location(null, -5, 5, 7, 0, 0));
		Watchtower_0_banners.add(new Location(null, -7, 5, 5, 0, 0));
		Watchtower_0_banners.add(new Location(null, -7, 5, -5, 0, 0));
		Watchtower_0_banners.add(new Location(null, -5, 5, -7, 0, 0));
		Watchtower_0_banners.add(new Location(null, 5, 5, -7, 0, 0));
		Watchtower_0_banners.add(new Location(null, 7, 5, -5, 0, 0));
		Watchtower_0_banners.add(new Location(null, 6, -8, 4, 0, 0));
		Watchtower_0_banners.add(new Location(null, 4, -8, 6, 0, 0));
		Watchtower_0_banners.add(new Location(null, -4, -8, 6, 0, 0));
		Watchtower_0_banners.add(new Location(null, -6, -8, 4, 0, 0));
		Watchtower_0_banners.add(new Location(null, -6, -8, -4, 0, 0));
		Watchtower_0_banners.add(new Location(null, -4, -8, -6, 0, 0));
		Watchtower_0_banners.add(new Location(null, 4, -8, -6, 0, 0));
		Watchtower_0_banners.add(new Location(null, 6, -8, -4, 0, 0));
		Watchtower_0_banners.add(new Location(null, 0, 17, -0, 0, 0));
		Watchtower_0.put("banners", Watchtower_0_banners);
		ArrayList<Location> Watchtower_0_melee_guards = new ArrayList<Location>();
		Watchtower_0_melee_guards.add(new Location(null, -6, -17, 6, 0, 0));
		Watchtower_0_melee_guards.add(new Location(null, 6, -17, 6, 0, 0));
		Watchtower_0.put("melee_guards", Watchtower_0_melee_guards);
		ArrayList<Location> Watchtower_0_ranged_guards = new ArrayList<Location>();
		Watchtower_0_ranged_guards.add(new Location(null, 0, 1, -4, 0, 0));
		Watchtower_0_ranged_guards.add(new Location(null, -4, 1, 0, -90, 0));
		Watchtower_0_ranged_guards.add(new Location(null, 0, 1, 4, 180, 0));
		Watchtower_0_ranged_guards.add(new Location(null, 4, 1, 0, 90, 0));
		Watchtower_0.put("ranged_guards", Watchtower_0_ranged_guards);
		Watchtower_0.put("protect_radius", 20);
		Watchtower_0.put("production_rate", 1);
		Watchtower_0.put("production_needed", 1);
		Watchtower_0.put("production_max", 5);
		Watchtower_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, -17, -3, 0, 0));
		Watchtower.add(Watchtower_0);
		// General Watchtower tier 1 !
		
		General.put("Watchtower", Watchtower);
		// General Watchtower !
		
		// ! General Stable
		ArrayList<HashMap<String, Object>> Stable = new ArrayList<HashMap<String, Object>>();

		// ! General Stable tier 1
		HashMap<String, Object> Stable_0 = new HashMap<String, Object>();
		ArrayList<Location> Stable_0_heart = new ArrayList<Location>();
		Stable_0_heart.add(new Location(null, 0, 1, -9, 0, 0));
		Stable_0_heart.add(new Location(null, 0, 2, -9, 0, 0));
		Stable_0_heart.add(new Location(null, 0, 3, -9, 0, 0));
		Stable_0.put("heart", Stable_0_heart);
		ArrayList<Location> Stable_0_banners = new ArrayList<Location>();
		Stable_0_banners.add(new Location(null, 3, 6, 10, 0, 0));
		Stable_0_banners.add(new Location(null, -3, 6, 10, 0, 0));
		Stable_0_banners.add(new Location(null, -12, 6, 10, 0, 0));
		Stable_0_banners.add(new Location(null, -14, 6, 8, 0, 0));
		Stable_0_banners.add(new Location(null, -14, 6, -8, 0, 0));
		Stable_0_banners.add(new Location(null, -12, 6, -10, 0, 0));
		Stable_0_banners.add(new Location(null, 12, 6, -10, 0, 0));
		Stable_0_banners.add(new Location(null, 14, 6, -8, 0, 0));
		Stable_0_banners.add(new Location(null, 14, 6, 8, 0, 0));
		Stable_0_banners.add(new Location(null, 12, 6, 10, 0, 0));
		Stable_0.put("banners", Stable_0_banners);
		ArrayList<Location> Stable_0_melee_guards = new ArrayList<Location>();
		Stable_0_melee_guards.add(new Location(null, -14, 1, 10, 0, 0));
		Stable_0_melee_guards.add(new Location(null, 14, 1, 10, 0, 0));
		Stable_0_melee_guards.add(new Location(null, 14, 1, -10, 0, 0));
		Stable_0_melee_guards.add(new Location(null, -14, 1, -10, 0, 0));
		Stable_0_melee_guards.add(new Location(null, -5, 1, -16, 0, 0));
		Stable_0_melee_guards.add(new Location(null, 5, 1, -16, 0, 0));
		Stable_0.put("melee_guards", Stable_0_melee_guards);
		ArrayList<Location> Stable_0_ranged_guards = new ArrayList<Location>();
		Stable_0_ranged_guards.add(new Location(null, 2, 1, -9, 90, 0));
		Stable_0_ranged_guards.add(new Location(null, -2, 1, -9, -90, 0));
		Stable_0_ranged_guards.add(new Location(null, 2, 1, 6, 90, 0));
		Stable_0_ranged_guards.add(new Location(null, -2, 1, 6, -90, 0));
		Stable_0.put("ranged_guards", Stable_0_ranged_guards);
		Stable_0.put("protect_radius", 30);
		Stable_0.put("production_rate", 1);
		Stable_0.put("production_needed", 2);
		Stable_0.put("production_max", 1);
		Stable_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, 1, -6, 0, 0));
		Stable.add(Stable_0);
		// General Stable tier 1 !
		
		General.put("Stable", Stable);
		// General Stable !
		
		Structures_ruleset.put("General", General);
		// General !
		
		
		availableClasses = new ArrayList<String>();
		availableClasses.add("Archer");
		availableClasses.add("Duelist");
		availableClasses.add("Healer");
		availableClasses.add("Magician");
		availableClasses.add("Warrior");
		availableClasses.add("Builder");

		availableFactions = new ArrayList<String>();
		availableFactions.add("Greeks");
		availableFactions.add("Persians");
		availableFactions.add("Egyptians");
	}

}
