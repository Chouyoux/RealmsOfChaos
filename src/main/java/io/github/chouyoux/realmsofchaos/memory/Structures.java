package io.github.chouyoux.realmsofchaos.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import io.github.chouyoux.realmsofchaos.data_handlers.MongoDatabase;
import io.github.chouyoux.realmsofchaos.objects.Structure;

public class Structures {
	
	public static HashMap<String, Structure> structures;
	
	
	public Structures() {
		structures = new HashMap<String, Structure>();
        List<DBObject> db_structures = MongoDatabase.getStructures();
        for (DBObject o : db_structures) {
    		Structure s = new Structure(o);
    		structures.put(s.getName(), s);
        }
	}
	
	public static void spawnNPCs() {
		for (Structure s : structures.values())
			s.spawnNPC();
	}
	
	public static void killNPCs() {
		for (Structure s : structures.values()) {
			s.killNPC();
			s.killGuards();
		}
	}
	
	public static void updateBanners() {
		for (Structure s : structures.values())
			s.updateBanners();
	}
	
	public static boolean isAHeartLoc(Location loc) {
		for (Structure s : structures.values())
			if (s.isAHeartLoc(loc))
				return true;
		return false;
	}
	
	public static void UpdateStructures() {
		ArrayList<BasicDBObject> struct_update = new ArrayList<BasicDBObject>();
		for (Structure s : structures.values())
			struct_update.add(s.toDB());
		MongoDatabase.updateStructures(struct_update);
	}
}
