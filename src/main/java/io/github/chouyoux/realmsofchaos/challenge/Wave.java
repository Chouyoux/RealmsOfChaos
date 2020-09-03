package io.github.chouyoux.realmsofchaos.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import de.slikey.effectlib.effect.VortexEffect;
import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.CustomAI;
import io.github.chouyoux.realmsofchaos.custom_mobs.Mob;
import io.github.chouyoux.realmsofchaos.custom_mobs.MobType;
import io.github.chouyoux.realmsofchaos.particle_effects.ChaosSpawnVortexEffect;

public class Wave {
	
	private HashMap<Location, MobType> monsters;
	private ArrayList<Mob> spawned_monsters;
	
	private boolean spawned = false;
	

	public Wave(HashMap<Location, MobType> monsters) {
		super();
		this.monsters = monsters;
		spawned_monsters = new ArrayList<Mob>();
	}
	
	public Wave() {
		super();
		this.monsters = new HashMap<Location, MobType>();
		spawned_monsters = new ArrayList<Mob>();
	}
	
	public void addMonster(Location loc, MobType ent) {
		this.monsters.put(loc, ent);
	}
	
	public void spawn() {
		spawned = true;
		for (Map.Entry monster : monsters.entrySet()) {
			Mob ai = Mob.spawnCustomAI((MobType) (monster.getValue()), ((Location) monster.getKey()));
			spawned_monsters.add(ai);
			new ChaosSpawnVortexEffect(RealmsOfChaos.effectManager, ai.getNpc().getEntity());
			ai.getNpc().getNavigator().setTarget(ai.getEntity(), true);
		}
	}
	
	public boolean isCleaned() {
		return leftAlive() == 0;
	}
	
	public int leftAlive() {
		int ret = 0;
		for (Mob e : spawned_monsters)
			if (e.getNpc().isSpawned())
				ret++;
		return ret;
	}

	public boolean isSpawned() {
		return spawned;
	}

	public void setSpawned(boolean spawned) {
		this.spawned = spawned;
	}

	public ArrayList<Mob> getSpawned_monsters() {
		return spawned_monsters;
	}

	public void setSpawned_monsters(ArrayList<Mob> spawned_monsters) {
		this.spawned_monsters = spawned_monsters;
	}
	
	

}
