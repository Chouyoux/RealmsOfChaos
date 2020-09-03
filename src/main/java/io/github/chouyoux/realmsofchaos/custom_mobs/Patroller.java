package io.github.chouyoux.realmsofchaos.custom_mobs;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.memory.Structures;
import io.github.chouyoux.realmsofchaos.objects.Structure;
import io.github.chouyoux.realmsofchaos.ruleset.PatrollersRuleset;
import net.citizensnpcs.api.npc.NPC;

public abstract class Patroller extends CustomAI {
	
	public static HashMap<String, ArrayList<Patroller>> spawnedPatrollers = new HashMap<String, ArrayList<Patroller>>();
	
	public static ArrayList<Patroller> spawnPatroll(String name){
		ArrayList<Patroller> patrollers = new ArrayList<Patroller>();
		
		if (PatrollersRuleset.structuresPatrolls.containsKey(name)) {
			MeleePatroller melee = new MeleePatroller(PatrollersRuleset.structuresPatrolls.get(name).get(0), Structures.structures.get(name).getFaction(), name);
			patrollers.add(melee);
			patrollers.add(new RangedPatroller(PatrollersRuleset.structuresPatrolls.get(name).get(0), Structures.structures.get(name).getFaction(), name, melee));
			patrollers.add(new RangedPatroller(PatrollersRuleset.structuresPatrolls.get(name).get(0), Structures.structures.get(name).getFaction(), name, melee));
			patrollers.add(new RangedPatroller(PatrollersRuleset.structuresPatrolls.get(name).get(0), Structures.structures.get(name).getFaction(), name, melee));
			patrollers.add(new RangedPatroller(PatrollersRuleset.structuresPatrolls.get(name).get(0), Structures.structures.get(name).getFaction(), name, melee));
			patrollers.add(new RangedPatroller(PatrollersRuleset.structuresPatrolls.get(name).get(0), Structures.structures.get(name).getFaction(), name, melee));
		}
		
		spawnedPatrollers.put(name, patrollers);
		
		return patrollers;
	}
	
	public static Patroller getPatroller(NPC npc) {
		for (ArrayList<Patroller> p_list : spawnedPatrollers.values()) {
			for (Patroller p : p_list)
				if (p.getNpc().equals(npc)) return p;
		}
		return null;
	}
	
	private String structure;

	public Patroller(double armor, int damage, double chaseRange, double aggroRange, int respawnTime,
			Location spawnPoint, EntityType type, String name, String faction, Material weaponType, String structure) {
		super(armor, damage, chaseRange, aggroRange, respawnTime, spawnPoint, type, name, faction, weaponType);
		this.structure = structure;
	}
	
	public String getStrStructure() {
		return structure;
	}
	
	public Structure getStructure() {
		return Structures.structures.get(structure);
	}

}