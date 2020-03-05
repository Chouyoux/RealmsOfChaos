package io.github.chouyoux.realmsofchaos.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import io.github.chouyoux.realmsofchaos.data_handlers.MongoDatabase;
import io.github.chouyoux.realmsofchaos.objects.Region;

public class Regions {
	
	public static HashMap<String, Region> regions;
	
	
	public Regions() {
		regions = new HashMap<String, Region>();
        List<DBObject> db_regions = MongoDatabase.getRegions();
        for (DBObject o : db_regions) {
        	Region r = new Region(o);
    		regions.put(r.getName(), r);
        }
	}
	
	public static void UpdateRegions() {
		ArrayList<BasicDBObject> reg_update = new ArrayList<BasicDBObject>();
		for (Region r : regions.values())
			reg_update.add(r.toDB());
		MongoDatabase.updateRegions(reg_update);
	}

}
