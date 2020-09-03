package io.github.chouyoux.realmsofchaos.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bson.BasicBSONObject;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Type;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import de.tr7zw.nbtapi.NBTEntity;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import io.github.chouyoux.realmsofchaos.util.LocSerialization;

public class Faction {
	
	private String name;
	private Location spawn;
	private String spawnRegion;
	private ArrayList<String> playersUuids;
	private HashMap<String, Location> npcLocations;
	private ArrayList<LivingEntity> npcs;

	public Faction(String name, Location spawn, String spawnRegion, ArrayList<String> playersUuids,
			HashMap<String, Location> npcLocations, ArrayList<LivingEntity> npcs) {
		this.name = name;
		this.spawn = spawn;
		this.spawnRegion = spawnRegion;
		this.playersUuids = playersUuids;
		this.npcLocations = npcLocations;
		this.npcs = npcs;
	}
	
	public Faction(DBObject o) {
		this.name = (String) o.get("name");
		this.spawn = LocSerialization.getLocationFromString((String) o.get("spawn"));
		this.spawnRegion = (String) o.get("spawnRegion");
		this.playersUuids = new ArrayList<String>();
		for (Object str : ((BasicDBList) o.get("playersUuids")) )
			playersUuids.add((String)str);
		this.npcLocations = new HashMap<String, Location>();
		for (Entry<String, Object> e : ((BasicBSONObject) o.get("npcLocations")).entrySet() ) {
			Location npc_loc = LocSerialization.getLocationFromString((String) e.getValue());
			npcLocations.put(e.getKey(), npc_loc);
		}
		this.npcs = new ArrayList<LivingEntity>();
	}
	
	public BasicDBObject toDB() {
		BasicDBObject o = new BasicDBObject();
		o.put("name", this.name);
		o.put("spawn", LocSerialization.getStringFromLocation(this.spawn));
		o.put("spawnRegion", this.spawnRegion);
		o.put("playersUuids", this.playersUuids.toArray());
		BasicBSONObject npclocs = new BasicBSONObject();
		for (Entry<String, Location> e : this.npcLocations.entrySet())
			npclocs.put(e.getKey(),LocSerialization.getStringFromLocation(e.getValue()));
		o.put("npcLocations", npclocs);
		return o;
	}
	
	public void spawnNPCs() {
		for (Entry<String, Location> e : getNpcLocations().entrySet()) {
			String npc_name = e.getKey();
			Location npc_loc = e.getValue();
			Villager npc = (Villager) npc_loc.getWorld().spawnEntity(npc_loc, EntityType.VILLAGER);
			if (getName().equals("Greeks"))
				npc.setVillagerType(Type.PLAINS);
			else if (getName().equals("Persians"))
				npc.setVillagerType(Type.SAVANNA);
			else if (getName().equals("Egyptians"))
				npc.setVillagerType(Type.DESERT);
			RoCNPC.setFaction(npc, getName());
	        npc.setCustomName(FactionRuleset.factionChatMsgColors.get(name)+npc_name);
	        npc.setCustomNameVisible(true);
	        npc.setRemoveWhenFarAway(false);
	        npc.setInvulnerable(true);
	        NBTEntity nbtent = new NBTEntity(npc);
	        nbtent.setBoolean("NoAI", true);
	        npcs.add(npc);
	        npc.getWorld().setChunkForceLoaded(npc.getLocation().getChunk().getX(), npc.getLocation().getChunk().getZ(), true);
		}
	}
	
	public void killNPCs() {
		for (LivingEntity e : npcs) {
			e.setHealth(0);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public String getSpawnRegion() {
		return spawnRegion;
	}
	
	public String getDisplaySpawnRegion() {
		String str = new String(this.spawnRegion);
		String first_letter = str.substring(0, 1).toUpperCase();
		String rest = str.substring(1);
		return first_letter + rest;
	}

	public void setSpawnRegion(String spawnRegion) {
		this.spawnRegion = spawnRegion;
	}

	public ArrayList<String> getPlayersUuids() {
		return playersUuids;
	}

	public void setPlayersUuids(ArrayList<String> playersUuids) {
		this.playersUuids = playersUuids;
	}

	public HashMap<String, Location> getNpcLocations() {
		return npcLocations;
	}

	public void setNpcLocations(HashMap<String, Location> npcLocations) {
		this.npcLocations = npcLocations;
	}

	@Override
	public String toString() {
		return "Faction [name=" + name + ", spawn=" + spawn + ", spawnRegion=" + spawnRegion + ", playersUuids="
				+ playersUuids + ", npcLocations=" + npcLocations + "]";
	}

}
