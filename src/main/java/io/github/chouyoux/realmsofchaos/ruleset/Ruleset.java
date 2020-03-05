package io.github.chouyoux.realmsofchaos.ruleset;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Ruleset {
	
	public static HashMap<String, ArrayList<HashMap<String, Object>>> Structures_ruleset;
	
	public Ruleset() {
		Structures_ruleset = new HashMap<String, ArrayList<HashMap<String, Object>>>();

		// ! Fort
		ArrayList<HashMap<String, Object>> Fort = new ArrayList<HashMap<String, Object>>();
		
		// ! Fort tier 1
		HashMap<String, Object> Fort_0 = new HashMap<String, Object>();
		ArrayList<Location> Fort_0_heart = new ArrayList<Location>();
		Fort_0_heart.add(new Location(null, 0, 0, 0, 0, 0));
		Fort_0_heart.add(new Location(null, 0, 1, 0, 0, 0));
		Fort_0_heart.add(new Location(null, 0, 2, 0, 0, 0));
		Fort_0.put("heart", Fort_0_heart);
		ArrayList<Location> Fort_0_melee_guards = new ArrayList<Location>();
		Fort_0_melee_guards.add(new Location(null, -7, 0, -4, -45, 0));
		Fort_0_melee_guards.add(new Location(null, -4, 0, -7, -45, 0));
		Fort_0_melee_guards.add(new Location(null, 7, 0, -4, 45, 0));
		Fort_0_melee_guards.add(new Location(null, 4, 0, -7, 45, 0));
		Fort_0_melee_guards.add(new Location(null, -7, 0, 4, -135, 0));
		Fort_0_melee_guards.add(new Location(null, -4, 0, 7, -135, 0));
		Fort_0_melee_guards.add(new Location(null, 7, 0, 4, 135, 0));
		Fort_0_melee_guards.add(new Location(null, 4, 0, 7, 135, 0));
		Fort_0.put("melee_guards", Fort_0_melee_guards);
		ArrayList<Location> Fort_0_ranged_guards = new ArrayList<Location>();
		Fort_0_ranged_guards.add(new Location(null, 0, 5, 0, 0, 0));
		Fort_0_ranged_guards.add(new Location(null, 0, 7, -9, 180, 0));
		Fort_0_ranged_guards.add(new Location(null, -9, 7, 0, 90, 0));
		Fort_0_ranged_guards.add(new Location(null, 0, 7, 9, 0, 0));
		Fort_0_ranged_guards.add(new Location(null, 9, 7, 0, -90, 0));
		Fort_0.put("ranged_guards", Fort_0_ranged_guards);
		Fort_0.put("protect_radius", 40);
		Fort_0.put("production_rate", 2.0);
		Fort_0.put("production_max", 15);
		Fort_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, 4, -2, 180, 0));
		Fort.add(Fort_0);
		// Fort tier 1 !
		
		Structures_ruleset.put("Fort", Fort);
		// Fort !
		
		// ! Watchtower
		ArrayList<HashMap<String, Object>> Watchtower = new ArrayList<HashMap<String, Object>>();

		// ! Watchtower tier 1
		HashMap<String, Object> Watchtower_0 = new HashMap<String, Object>();
		ArrayList<Location> Watchtower_0_heart = new ArrayList<Location>();
		Watchtower_0_heart.add(new Location(null, 0, 0, 0, 0, 0));
		Watchtower_0_heart.add(new Location(null, 0, 1, 0, 0, 0));
		Watchtower_0.put("heart", Watchtower_0_heart);
		ArrayList<Location> Watchtower_0_melee_guards = new ArrayList<Location>();
		Watchtower_0_melee_guards.add(new Location(null, -5, -17, -5, 180, 0));
		Watchtower_0_melee_guards.add(new Location(null, 5, -17, -5, 180, 0));
		Watchtower_0.put("melee_guards", Watchtower_0_melee_guards);
		ArrayList<Location> Watchtower_0_ranged_guards = new ArrayList<Location>();
		Watchtower_0_ranged_guards.add(new Location(null, 0, 1, -3, 180, 0));
		Watchtower_0_ranged_guards.add(new Location(null, -3, 1, 0, 90, 0));
		Watchtower_0_ranged_guards.add(new Location(null, 0, 1, 3, 0, 0));
		Watchtower_0_ranged_guards.add(new Location(null, 3, 1, 0, -90, 0));
		Watchtower_0.put("ranged_guards", Watchtower_0_ranged_guards);
		Watchtower_0.put("protect_radius", 20);
		Watchtower_0.put("production_rate", 1.0);
		Watchtower_0.put("production_max", 5);
		Watchtower_0.put("npc", new Location(Bukkit.getWorld("Realms"), 0, -17, 2, 180, 0));
		Watchtower.add(Watchtower_0);
		// Watchtower tier 1 !
		
		Structures_ruleset.put("Watchtower", Watchtower);
		// Watchtower !
		
		// ! Stable
		ArrayList<HashMap<String, Object>> Stable = new ArrayList<HashMap<String, Object>>();

		// ! Stable tier 1
		HashMap<String, Object> Stable_0 = new HashMap<String, Object>();
		ArrayList<Location> Stable_0_heart = new ArrayList<Location>();
		Stable_0_heart.add(new Location(null, -5, -7, -7, 0, 0));
		Stable_0_heart.add(new Location(null, -5, -8, -7, 0, 0));
		Stable_0.put("heart", Stable_0_heart);
		ArrayList<Location> Stable_0_melee_guards = new ArrayList<Location>();
		Stable_0_melee_guards.add(new Location(null, 5.5, 0, 5.5, 90, 0));
		Stable_0_melee_guards.add(new Location(null, -5.5, 0, -5.5, 90, 0));
		Stable_0_melee_guards.add(new Location(null, -5.5, 0, 5.5, 90, 0));
		Stable_0_melee_guards.add(new Location(null, 5.5, 0, -5.5, 90, 0));
		Stable_0.put("melee_guards", Stable_0_melee_guards);
		ArrayList<Location> Stable_0_ranged_guards = new ArrayList<Location>();
		Stable_0_ranged_guards.add(new Location(null, -5.5, -8, 4.5, -90, 0));
		Stable_0_ranged_guards.add(new Location(null, -5.5, -8, -5.5, -90, 0));
		Stable_0.put("ranged_guards", Stable_0_ranged_guards);
		Stable_0.put("protect_radius", 25);
		Stable_0.put("production_rate", 1.0);
		Stable_0.put("production_max", 1);
		Stable_0.put("npc", new Location(Bukkit.getWorld("Realms"), 3.5, 0, 0, 90, 0));
		Stable.add(Stable_0);
		// Stable tier 1 !
		
		Structures_ruleset.put("Stable", Stable);
		// Stable !
		
		
	}

}
