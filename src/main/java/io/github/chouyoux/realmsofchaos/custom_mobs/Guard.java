package io.github.chouyoux.realmsofchaos.custom_mobs;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import net.citizensnpcs.api.npc.NPC;

public abstract class Guard extends CustomAI {
	
	public static ArrayList<Guard> spawnedGuards = new ArrayList<Guard>();
	
	public static Guard getGuard(NPC npc) {
		for (Guard g : spawnedGuards) {
			if (g.getNpc().equals(npc)) return g;
		}
		return null;
	}

	public Guard(double armor, int damage, double chaseRange, double aggroRange, int respawnTime, Location spawnPoint,
			EntityType type, String name, String faction, Material weaponType) {
		super(armor, damage, chaseRange, aggroRange, respawnTime, spawnPoint, type, name, faction, weaponType);
		this.setPoseAtSpawnPoint();
		this.addWaypoint(spawnPoint);
		spawnedGuards.add(this);
	}

}
