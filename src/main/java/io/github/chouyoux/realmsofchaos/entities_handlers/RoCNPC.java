package io.github.chouyoux.realmsofchaos.entities_handlers;

import static io.github.chouyoux.realmsofchaos.RealmsOfChaos.getInstance;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.memory.Factions;
import io.github.chouyoux.realmsofchaos.memory.Structures;

public class RoCNPC {
	
	public static void spawnNPCs() {
	    for (Entity e : Bukkit.getWorld("Realms").getEntities())
	    	if (!(e instanceof Player))
	    		if (e instanceof Damageable)
	    			((Damageable) e).setHealth(0);
	    		else
	    			e.remove();
	    Factions.spawnNPCs();
	    Structures.spawnNPCs();
	    Bukkit.getLogger().info("Entities cleaned / respawned !");
	}
	
	public static String getFaction(LivingEntity e) {
		NamespacedKey key = new NamespacedKey(getInstance(), "Faction");
	    PersistentDataContainer e_container = e.getPersistentDataContainer();
	    String e_foundvalue = "";
	    if(e_container.has(key , PersistentDataType.STRING)) {
	    	e_foundvalue = e_container.get(key, PersistentDataType.STRING);
	    }
		return e_foundvalue;
	}
	
	public static void setFaction(LivingEntity e, String f) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Faction");
		PersistentDataContainer container = e.getPersistentDataContainer();
		container.set(key, PersistentDataType.STRING, f);
	}
	
	public static String getStructure(LivingEntity e) {
		NamespacedKey key = new NamespacedKey(getInstance(), "Structure");
	    PersistentDataContainer e_container = e.getPersistentDataContainer();
	    String e_foundvalue = "";
	    if(e_container.has(key , PersistentDataType.STRING)) {
	    	e_foundvalue = e_container.get(key, PersistentDataType.STRING);
	    }
		return e_foundvalue;
	}
	
	public static void setStructure(LivingEntity e, String s) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Structure");
		PersistentDataContainer container = e.getPersistentDataContainer();
		container.set(key, PersistentDataType.STRING, s);
	}
	
	public static boolean sameFaction(LivingEntity e, Player p) {
		return (RoCNPC.getFaction(e).compareTo(RoCPlayers.getFaction(p)) == 0);
	}
	
	public static boolean sameFaction(LivingEntity e1, LivingEntity e2) {
		return (RoCNPC.getFaction(e1).compareTo(RoCNPC.getFaction(e2)) == 0);
	}

}
