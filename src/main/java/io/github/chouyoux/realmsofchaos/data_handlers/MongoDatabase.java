package io.github.chouyoux.realmsofchaos.data_handlers;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDatabase {

	private static List<DBObject> structures;
	private static List<DBObject> regions;
	private static List<DBObject> factions;
    private static MongoClient mc;
    private static DB mcserverdb;

    @SuppressWarnings("deprecation")
	public MongoDatabase(){
        mc = new MongoClient();
        mcserverdb = mc.getDB("RealmsOfChaos");
    }
    
	public static List<DBObject> getStructures(){
    	DBCollection col_structures = mcserverdb.getCollection("structures");
        try (DBCursor cursor = col_structures.find()){
        	structures = cursor.toArray();
        }
    	return structures;
    }
    
	public static List<DBObject> getRegions(){
    	DBCollection col_regions = mcserverdb.getCollection("regions");
        try (DBCursor cursor = col_regions.find()){
        	regions = cursor.toArray();
        }
    	return regions;
	}
	
	public static List<DBObject> getFactions(){
    	DBCollection col_factions = mcserverdb.getCollection("factions");
        try (DBCursor cursor = col_factions.find()){
        	factions = cursor.toArray();
        }
    	return factions;
	}
	
	public static void updateStructures(List<BasicDBObject> objs){
    	DBCollection col_structures = mcserverdb.getCollection("structures");
    	for (BasicDBObject obj : objs) {
    		BasicDBObject searchQuery = new BasicDBObject().append("name", (String) obj.get("name"));
    		col_structures.update(searchQuery, obj);
    	}
    }
    
	public static void updateRegions(List<BasicDBObject> objs){
    	DBCollection col_regions = mcserverdb.getCollection("regions");
    	for (BasicDBObject obj : objs) {
    		BasicDBObject searchQuery = new BasicDBObject().append("name", (String) obj.get("name"));
    		col_regions.update(searchQuery, obj);
    	}
	}
	
	public static void updateFactions(List<BasicDBObject> objs){
    	DBCollection col_factions = mcserverdb.getCollection("factions");
    	for (BasicDBObject obj : objs) {
    		BasicDBObject searchQuery = new BasicDBObject().append("name", (String) obj.get("name"));
    		col_factions.update(searchQuery, obj);
    	}
	}
    

}
