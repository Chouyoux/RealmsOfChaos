package io.github.chouyoux.realmsofchaos.objects;

import org.bukkit.Location;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import io.github.chouyoux.realmsofchaos.util.LocSerialization;

public class Region {
	
	private String name;
	private String faction;
	private Location spawn;

	public Region(String name, String faction, Location spawn) {
		this.name = new String(name);
		this.faction = new String(faction);
		this.spawn = spawn;
	}
	
	public Region(DBObject o) {
		this.name = (String) o.get("name");
		this.faction = (String) o.get("faction");
		this.spawn = LocSerialization.getLiteLocationFromString((String) o.get("spawn"));
	}
	
	public BasicDBObject toDB() {
		BasicDBObject o = new BasicDBObject();
		o.put("name", this.name);
		o.put("faction", this.faction);
		o.put("spawn", LocSerialization.getLiteStringFromLocation(this.spawn));
		return o;
	}
	
	public String getDisplayName() {
		String result = "";
		String[] split = name.split("_");
		for (String str : split)
			result += str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	@Override
	public String toString() {
		return "Region [name=" + name + ", faction=" + faction + ", spawn=" + spawn + "]";
	}

}
