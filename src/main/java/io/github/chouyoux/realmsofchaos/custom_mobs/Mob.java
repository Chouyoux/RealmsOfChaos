package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public abstract class Mob extends CustomAI {

	private MobType mobType;
	
	public Mob(double armor, int damage, double health, int healRate, int attackRate, double moveSpeed, Location spawnPoint,
			EntityType type, String name, Material weaponType, MobType mobType) {
		super(armor, damage, health, healRate, attackRate, moveSpeed, 250, 250, -1, spawnPoint, type, name, "Chaos", weaponType, true,
				true, false, true, 250);
		this.mobType = mobType;
	}

	static public Mob spawnCustomAI(MobType mobType, Location location) {
		Mob customAI = null;
		switch (mobType) {
		case ANTALOCK:
			customAI = new Antalock(location);
			break;
		case BULDAVAX:
			customAI = new Buldavax(location);
			break;
		case KHROKEDAI:
			customAI = new Khrokedai(location);
			break;
		case KREDEKAI:
			customAI = new Kredekai(location);
			break;
		case GURAFAR:
			customAI = new Gurafar(location);
			break;
		case SKYLEK:
			customAI = new Skylek(location);
			break;
		case ZAROSS:
			customAI = new Zaross(location);
			break;
		case GARAMIN:
			customAI = new Garamin(location);
			break;
		case ELABUS:
			customAI = new Elabus(location);
			break;
		case AZUTHOT:
			customAI = new Azuthot(location);
			break;
		case BEROCIUS:
			customAI = new Berocius(location);
			break;
		case GURON:
			customAI = new Guron(location);
			break;
		default:
			break;
		}
		return customAI;
	}
	
	public MobType getMobType() {
		return mobType;
	}

}
