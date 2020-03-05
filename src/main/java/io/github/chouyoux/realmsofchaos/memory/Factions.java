package io.github.chouyoux.realmsofchaos.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import io.github.chouyoux.realmsofchaos.data_handlers.MongoDatabase;
import io.github.chouyoux.realmsofchaos.objects.Faction;

public class Factions {
	public static HashMap<String, Faction> factions;
	
	
	public Factions() {
		factions = new HashMap<String, Faction>();
        List<DBObject> db_factions = MongoDatabase.getFactions();
        for (DBObject o : db_factions) {
        	Faction f = new Faction(o);
        	factions.put(f.getName(), f);
        }
	}
	
	public static void UpdateFactions() {
		ArrayList<BasicDBObject> fac_update = new ArrayList<BasicDBObject>();
		for (Faction f : factions.values())
			fac_update.add(f.toDB());
		MongoDatabase.updateFactions(fac_update);
	}
	
	public static void spawnNPCs() {
		for (Faction f : factions.values())
			f.spawnNPCs();
	}
	
	public static void killNPCs() {
		for (Faction f : factions.values())
			f.killNPCs();
	}

}
