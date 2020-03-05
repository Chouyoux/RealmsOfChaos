package io.github.chouyoux.realmsofchaos.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Vindicator;
import org.bukkit.scheduler.BukkitRunnable;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import de.tr7zw.nbtapi.NBTEntity;
import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.ruleset.Ruleset;
import io.github.chouyoux.realmsofchaos.util.LocSerialization;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class Structure {
	
	// Stored and retrieved on DB
	private String name;
	private String faction;
	private String type;
	private Location center;

	private int rotate;
	private int tier;
	
	// Filled by Ruleset
	private int protect_radius;
	private ArrayList<Location> heart;
	private HashMap<Location, LivingEntity> ranged_guards;
	private HashMap<Location, LivingEntity> melee_guards;
	private Villager npc;
	private Location npc_loc; 
	
	// Filled by food-related ruleset (Defensive structures)
	private double production_rate;
	private int production_max;
	
	
	// Behavior usage
	private boolean raid = false;

	public Structure(String name, String faction, String type, Location center, int rotate, int tier) {
		this.name = new String(name);
		this.faction = new String(faction);
		this.type = new String(type);
		this.center = center.clone();
		this.tier = tier;
		this.heart = new ArrayList<Location>();
		this.ranged_guards = new HashMap<Location, LivingEntity>();
		this.melee_guards = new HashMap<Location, LivingEntity>();
		
		fillWithRuleset();
		startStructureBehavior(20*30);
		startGuardSpawning(20*10);
		startGuardBehavior(20*1);
	}
	
	public Structure(DBObject o) {
		this.name = (String) o.get("name");
		this.faction = (String) o.get("faction");
		this.type = (String) o.get("type");
		this.center = LocSerialization.getLocationFromString((String) o.get("center"));
		if (o.get("tier") instanceof Double)
			this.tier = ((Double) o.get("tier")).intValue();
		else
			this.tier = ((Integer) o.get("tier")).intValue();
		if (o.get("rotate") instanceof Double)
			this.rotate = ((Double) o.get("rotate")).intValue();
		else
			this.rotate = ((Integer) o.get("rotate")).intValue();
		this.heart = new ArrayList<Location>();
		this.ranged_guards = new HashMap<Location, LivingEntity>();
		this.melee_guards = new HashMap<Location, LivingEntity>();
		
		fillWithRuleset();
		spawnNPC();
		startStructureBehavior(20*30);
		startGuardSpawning(20*10);
		startGuardBehavior(20*1);
	}
	
	public BasicDBObject toDB() {
		BasicDBObject o = new BasicDBObject();
		o.put("name", name);
		o.put("faction", faction);
		o.put("type", type);
		o.put("center", LocSerialization.getStringFromLocation(center));
		o.put("rotate", rotate);
		o.put("tier", tier);
		return o;
	}
	
	@SuppressWarnings("unchecked")
	private void fillWithRuleset() {
		double angle = Math.toRadians(rotate);
		for (Location l : (ArrayList<Location>) Ruleset.Structures_ruleset.get(type).get(tier-1).get("heart")) {
			l.setWorld(center.getWorld());
			Location end_l = UtilFuncs.rotateLocationAroundY(l, angle);
			end_l.add(center);
			heart.add(end_l);
		}
		for (Location l : (ArrayList<Location>) Ruleset.Structures_ruleset.get(type).get(tier-1).get("melee_guards")) {
			l.setWorld(center.getWorld());
			Location end_l = UtilFuncs.rotateLocationAroundY(l, angle);
			end_l.add(center);
			melee_guards.put(end_l, null);
		}
		for (Location l : (ArrayList<Location>) Ruleset.Structures_ruleset.get(type).get(tier-1).get("ranged_guards")) {
			l.setWorld(center.getWorld());
			Location end_l = UtilFuncs.rotateLocationAroundY(l, angle);
			end_l.add(center);
			ranged_guards.put(end_l, null);
		}
		npc_loc = ((Location) Ruleset.Structures_ruleset.get(type).get(tier-1).get("npc")).clone();
		npc_loc = UtilFuncs.rotateLocationAroundY(npc_loc, angle);
		npc_loc.add(center);
		protect_radius = (int) Ruleset.Structures_ruleset.get(type).get(tier-1).get("protect_radius");
		if (type.compareTo("Fort") == 0 || type.compareTo("Watchtower") == 0 || type.compareTo("Stable") == 0) {
			production_rate = (double) Ruleset.Structures_ruleset.get(type).get(tier-1).get("production_rate");
			production_max = (int) Ruleset.Structures_ruleset.get(type).get(tier-1).get("production_max");
		}
	}
	
	public void spawnNPC() {
		if (type.compareTo("Fort") == 0 || type.compareTo("Watchtower") == 0) {
			String npc_name = "Farmer";
			npc = (Villager) npc_loc.getWorld().spawnEntity(npc_loc, EntityType.VILLAGER);
			if (faction.compareTo("Greeks") == 0)
				npc.setProfession(Villager.Profession.LIBRARIAN);
			else if (faction.compareTo("Persians") == 0)
				npc.setProfession(Villager.Profession.CARTOGRAPHER);
			else if (faction.compareTo("Egyptians") == 0)
				npc.setProfession(Villager.Profession.CLERIC);
			RoCNPC.setFaction(npc, faction);
			RoCNPC.setStructure(npc, name);
	        npc.setCustomName(npc_name);
	        npc.setCustomNameVisible(true);
			npc.setInvulnerable(true);
	        npc.setRemoveWhenFarAway(false);
	        NBTEntity nbtent = new NBTEntity(npc);
	        nbtent.setBoolean("NoAI", true);
		}
		if (type.compareTo("Stable") == 0) {
			String npc_name = "Stable Holder";
			npc = (Villager) npc_loc.getWorld().spawnEntity(npc_loc, EntityType.VILLAGER);
			if (faction.compareTo("Greeks") == 0)
				npc.setProfession(Villager.Profession.LIBRARIAN);
			else if (faction.compareTo("Persians") == 0)
				npc.setProfession(Villager.Profession.CARTOGRAPHER);
			else if (faction.compareTo("Egyptians") == 0)
				npc.setProfession(Villager.Profession.CLERIC);
			RoCNPC.setFaction(npc, faction);
			RoCNPC.setStructure(npc, name);
	        npc.setCustomName(npc_name);
	        npc.setCustomNameVisible(true);
			npc.setInvulnerable(true);
	        npc.setRemoveWhenFarAway(false);
	        NBTEntity nbtent = new NBTEntity(npc);
	        nbtent.setBoolean("NoAI", true);
		}
	}
	
	public void killNPC() {
		npc.setHealth(0);
		npc = null;
	}
	
	private void startStructureBehavior(int ticks_frequence) {
		BukkitRunnable structureBehave = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		if (type.compareTo("Fort") == 0 || type.compareTo("Watchtower") == 0) {
		    	    for(Player p : Bukkit.getOnlinePlayers()){
		    	        if (RoCPlayers.getFaction(p).compareTo(faction) == 0) {
		    	        	double current_food = RoCPlayers.getStructureFood(name, p);
		    	        	RoCPlayers.setStructureFood(name, p, Math.min(production_max, current_food+production_rate));
		    	        }
		    	    }
	    		}
	    		if (type.compareTo("Stable") == 0) {
		    	    for(Player p : Bukkit.getOnlinePlayers()){
		    	        if (RoCPlayers.getFaction(p).compareTo(faction) == 0) {
		    	        	double current_horse = RoCPlayers.getStructureHorse(name, p);
		    	        	RoCPlayers.setStructureHorse(name, p, Math.min(production_max, current_horse+production_rate));
		    	        }
		    	    }
	    		}
	    	}
	    };
	    structureBehave.runTaskTimer(RealmsOfChaos.getInstance(), ticks_frequence, ticks_frequence);
	}
	
	private void startGuardBehavior(int ticks_frequence) {
		BukkitRunnable guardsBehave = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		for (Entry<Location, LivingEntity> e : melee_guards.entrySet())
	    		{
					Location npc_loc = e.getKey();
	    			if (!(e.getValue() == null || e.getValue().isDead())) {
	    				LivingEntity npc = e.getValue();
	    				int aggro = 10;
	    				int de_aggro = 12;
	    				List<Entity> nearby = UtilFuncs.getNearbyEntities(npc_loc, aggro);
	    				for (Entity e2 : nearby) {
	    					if (e2 instanceof Player && RoCPlayers.getFaction((Player) e2).compareTo(faction) != 0) {
			    	            npc.setAI(true);
			    				((Mob) npc).setTarget((LivingEntity) e2);
	    					}
	    				}
	    				if (((Mob) npc).getTarget() == null || RoCNPC.sameFaction(((Mob) npc).getTarget(), npc)) {
	    					((Mob) npc).setTarget(null);
	    					npc.teleport(npc_loc);
		    	            npc.setAI(false);
		    	            continue;
	    				}
    					Location target_loc_0 = ((Mob) npc).getTarget().getLocation().clone();
	    				Location center_0 = heart.get(0).clone();
	    				Location npc_loc_0 = npc.getLocation();
    					target_loc_0.setY(0);
	    				center_0.setY(0);
	    				npc_loc_0.setY(0);
	    				if ((((Mob) npc).getTarget().isDead() || npc_loc_0.distance(center_0) > protect_radius || npc_loc_0.distance(target_loc_0) > de_aggro) && npc.getLocation().distance(npc_loc) > 0.5) {
	    					((Mob) npc).setTarget(null);
	    					npc.teleport(npc_loc);
	    					npc.setAI(false);
	    				}
	    			}
	    		}
	    		for (Entry<Location, LivingEntity> e : ranged_guards.entrySet()) {
					Location npc_loc = e.getKey();
	    			if (!(e.getValue() == null || e.getValue().isDead())) {
	    				LivingEntity npc = e.getValue();
	    				int aggro = 12;
	    				int de_aggro = 15;
	    				List<Entity> nearby = UtilFuncs.getNearbyEntities(npc_loc, aggro);
	    				for (Entity e2 : nearby) {
	    					if (e2 instanceof Player && RoCPlayers.getFaction((Player) e2).compareTo(faction) != 0) {
			    	            npc.setAI(true);
			    				((Mob) npc).setTarget((LivingEntity) e2);
	    					}
	    				}
	    				if (((Mob) npc).getTarget() == null || RoCNPC.sameFaction(((Mob) npc).getTarget(), npc)) {
	    					((Mob) npc).setTarget(null);
	    					npc.teleport(npc_loc);
		    	            npc.setAI(false);
		    	            continue;
	    				}
    					Location target_loc_0 = ((Mob) npc).getTarget().getLocation().clone();
	    				Location center_0 = heart.get(0).clone();
	    				Location npc_loc_0 = npc.getLocation();
    					target_loc_0.setY(0);
	    				center_0.setY(0);
	    				npc_loc_0.setY(0);
	    				if ((((Mob) npc).getTarget().isDead() || npc_loc_0.distance(center_0) > protect_radius || npc_loc_0.distance(target_loc_0) > de_aggro) && npc.getLocation().distance(npc_loc) > 0.5) {
	    					((Mob) npc).setTarget(null);
	    					npc.teleport(npc_loc);
		    	            npc.setAI(false);
	    				}
	    			}
	    		}
	    	}
	    };
	    guardsBehave.runTaskTimer(RealmsOfChaos.getInstance(), ticks_frequence, ticks_frequence);
	}
	
	private void startGuardSpawning(int ticks_frequence) {
		BukkitRunnable spawnGuards = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		for (Entry<Location, LivingEntity> e : melee_guards.entrySet())
	    		{
    				String npc_name = "Melee Guard";
					Location npc_loc = e.getKey();
	    			if (e.getValue() == null || e.getValue().isDead()) {
						Vindicator npc = (Vindicator) npc_loc.getWorld().spawnEntity(npc_loc, EntityType.VINDICATOR);
						/*if (faction.compareTo("Greeks") == 0)
							//iron
						else if (faction.compareTo("Persians") == 0)
							//stone
						else if (faction.compareTo("Egyptians") == 0)
							//wooden*/
						RoCNPC.setFaction(npc, faction);
			            npc.setCustomName(npc_name);
			            npc.setRemoveWhenFarAway(false);
			            npc.setAI(false);
			            e.setValue(npc);
			            return;
	    			}
	    		}
	    		for (Entry<Location, LivingEntity> e : ranged_guards.entrySet()) {
    				String npc_name = "Ranged Guard";
					Location npc_loc = e.getKey();
	    			if (e.getValue() == null || e.getValue().isDead()) {
						Pillager npc = (Pillager) npc_loc.getWorld().spawnEntity(npc_loc, EntityType.PILLAGER);
						
						/*if (faction.compareTo("Greeks") == 0)
						else if (faction.compareTo("Persians") == 0)
						else if (faction.compareTo("Egyptians") == 0)
							*/
						RoCNPC.setFaction(npc, faction);
			            npc.setCustomName(npc_name);
			            npc.setRemoveWhenFarAway(false);
			            npc.setAI(false);
			            e.setValue(npc);
			            return;
	    			}
	    		}
	    	}
	    };
	    spawnGuards.runTaskTimer(RealmsOfChaos.getInstance(), ticks_frequence, ticks_frequence);
	}
	
	public void killGuards() {
		for (LivingEntity v : ranged_guards.values())
			if (v != null)
				v.setHealth(0);
		for (LivingEntity v : melee_guards.values())
			if (v != null)
				v.setHealth(0);
	}
	
	public boolean isAHeartLoc(Location loc) {
		for (Location l : heart)
			if (l.distance(loc) < 1)
				return true;
		return false;
	}
	
	public String getDisplayName() {
		String result = "";
		String[] split = name.split("_");
		for (String str : split)
			result += str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
		return result;
	}
	
	public String getType() {
		if (this.getDisplayName().contains("Fort")) return "Def";
		else return "Util";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getCenter() {
		return center;
	}

	public void setCenter(Location center) {
		this.center = center;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public ArrayList<Location> getHeart() {
		return heart;
	}

	public void setHeart(ArrayList<Location> heart) {
		this.heart = heart;
	}

	public boolean isRaid() {
		return raid;
	}

	public void setRaid(boolean is_raid) {
		this.raid = is_raid;
	}

	public int getProtect_radius() {
		return protect_radius;
	}

	public void setProtect_radius(int protect_radius) {
		this.protect_radius = protect_radius;
	}

	public HashMap<Location, LivingEntity> getRanged_guards() {
		return ranged_guards;
	}

	public void setRanged_guards(HashMap<Location, LivingEntity> ranged_guards) {
		this.ranged_guards = ranged_guards;
	}

	public HashMap<Location, LivingEntity> getMelee_guards() {
		return melee_guards;
	}

	public void setMelee_guards(HashMap<Location, LivingEntity> melee_guards) {
		this.melee_guards = melee_guards;
	}

	@Override
	public String toString() {
		return "Structure [name=" + name + ", faction=" + faction + ", tier=" + tier + ", protect_radius="
				+ protect_radius + ", heart=" + heart + ", ranged_guards=" + ranged_guards + ", melee_guards="
				+ melee_guards + ", raid=" + raid + "]";
	}

}
