package io.github.chouyoux.realmsofchaos.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class UtilFuncs {
	
	public static List<Entity> getNearbyEntities(Location loc, double range) {
		Location loc_clone = loc.clone();
		loc_clone.setY(0); // we want 2d distance (for cylinder not sphere)
        List<Entity> entities = loc.getWorld().getEntities();
        ArrayList<Entity> rtrn = new ArrayList<Entity>();
        for (Entity en : entities) {
        	Location en_loc = en.getLocation();
        	en_loc.setY(0); // cylinder
            if (en_loc.distance(loc_clone) > range) {
                continue;
            }
            for (Entity en2 : en.getPassengers()) {
            	rtrn.add(en2);
            }
            rtrn.add(en);
        }
        return rtrn;
    }
	
	public static Location rotateLocationAroundY(Location loc, double rad) {
		
		double degrees = Math.toDegrees(rad);
	   
		World world = loc.getWorld();
		
	    double currentX = loc.getX();
	    double currentZ = loc.getZ();
	    double currentYaw = loc.getYaw();
	    
	   
	    double cosine = Math.cos(rad);
	    double sine = Math.sin(rad);
	   
	    return new Location(world, (cosine * currentX - sine * currentZ), loc.getY(), (sine * currentX + cosine * currentZ), (int)(currentYaw+degrees), 0);
	}

}
