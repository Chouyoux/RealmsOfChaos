package io.github.chouyoux.realmsofchaos.ruleset;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

public class GradesRuleset {

	public static ArrayList<String> grades;
	public static HashMap<Integer, Material> defensive;
	public static HashMap<Integer, Double> stableSpeed;
	public static HashMap<Integer, Double> stableJump;
	public static HashMap<Integer, Integer> stableHealth;
	public static HashMap<Integer, Integer> templeAmplifier;
	public static HashMap<Integer, Integer> templeDuration;
	public static HashMap<Integer, Integer> neededExp;
	
	public GradesRuleset() {
		
		grades = new ArrayList<String>();
		grades.add("New Soul");
		grades.add("Soldier");
		grades.add("Sergeant");
		grades.add("Lieutenant");
		grades.add("Corporal");
		grades.add("Commander");
		grades.add("General");
		
		defensive = new HashMap<Integer, Material>();
		defensive.put(0, Material.BEETROOT);
		defensive.put(1, Material.COOKIE);
		defensive.put(2, Material.CARROT);
		defensive.put(3, Material.APPLE);
		defensive.put(4, Material.BREAD);
		defensive.put(5, Material.COOKED_MUTTON);
		defensive.put(6, Material.COOKED_PORKCHOP);
		
		stableSpeed = new HashMap<Integer, Double>();
		stableSpeed.put(0, 0.0000);
		stableSpeed.put(1, 0.2250);
		stableSpeed.put(2, 0.2500);
		stableSpeed.put(3, 0.2750);
		stableSpeed.put(4, 0.3000);
		stableSpeed.put(5, 0.3250);
		stableSpeed.put(6, 0.3600);
		
		stableJump = new HashMap<Integer, Double>();
		stableJump.put(0, 0.0000);
		stableJump.put(1, 0.6000);
		stableJump.put(2, 0.6500);
		stableJump.put(3, 0.7000);
		stableJump.put(4, 0.7500);
		stableJump.put(5, 0.8000);
		stableJump.put(6, 0.9000);
		
		stableHealth = new HashMap<Integer, Integer>();
		stableHealth.put(0, 0);
		stableHealth.put(1, 20);
		stableHealth.put(2, 24);
		stableHealth.put(3, 28);
		stableHealth.put(4, 32);
		stableHealth.put(5, 36);
		stableHealth.put(6, 40);

		templeAmplifier = new HashMap<Integer, Integer>();
		templeAmplifier.put(0, 0);
		templeAmplifier.put(1, 0);
		templeAmplifier.put(2, 0);
		templeAmplifier.put(3, 0);
		templeAmplifier.put(4, 1);
		templeAmplifier.put(5, 1);
		templeAmplifier.put(6, 1);

		templeDuration = new HashMap<Integer, Integer>();
		templeDuration.put(0, 0);
		templeDuration.put(1, 60*10*20);
		templeDuration.put(2, 60*15*20);
		templeDuration.put(3, 60*20*20);
		templeDuration.put(4, 60*10*20);
		templeDuration.put(5, 60*15*20);
		templeDuration.put(6, 60*20*20);

		neededExp = new HashMap<Integer, Integer>();
		neededExp.put(0, 0);
		neededExp.put(1, 1000);
		neededExp.put(2, 5000);
		neededExp.put(3, 20000);
		neededExp.put(4, 40000);
		neededExp.put(5, 80000);
		neededExp.put(6, 200000);
		
	}
	
}
