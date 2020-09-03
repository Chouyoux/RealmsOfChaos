package io.github.chouyoux.realmsofchaos.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Banner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Type;
import org.bukkit.scheduler.BukkitRunnable;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import de.tr7zw.nbtapi.NBTEntity;
import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.Guard;
import io.github.chouyoux.realmsofchaos.custom_mobs.MeleeGuard;
import io.github.chouyoux.realmsofchaos.custom_mobs.Patroller;
import io.github.chouyoux.realmsofchaos.custom_mobs.RangedGuard;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.PatrollersRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.Ruleset;
import io.github.chouyoux.realmsofchaos.util.Banners;
import io.github.chouyoux.realmsofchaos.util.LocSerialization;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class Structure {
	
	// Stored and retrieved on DB
	private String name;
	private String ofaction;
	private String faction;
	private String type;
	private Location center;

	private int rotate;
	private int tier;
	
	// Filled by Ruleset
	private int protect_radius;
	private ArrayList<Location> heart;
	private ArrayList<Location> banners;
	private HashMap<Location, Guard> ranged_guards;
	private HashMap<Location, Guard> melee_guards;
	private ArrayList<Patroller> patrollers;
	private Villager npc;
	private Location npc_loc; 
	
	// Filled by food-related ruleset (Defensive structures)
	public int production_rate;
	public int production_max;
	public int production_needed;
	
	
	// Behavior usage
	private boolean raid = false;

	public Structure(String name, String ofaction, String faction, String type, Location center, int rotate, int tier) {
		this.name = new String(name);
		this.ofaction = new String(ofaction);
		this.faction = new String(faction);
		this.type = new String(type);
		this.center = center.clone();
		this.tier = tier;
		this.heart = new ArrayList<Location>();
		this.banners = new ArrayList<Location>();
		this.ranged_guards = new HashMap<Location, Guard>();
		this.melee_guards = new HashMap<Location, Guard>();
		
		fillWithRuleset();
		spawnNPC();
		spawnAllGuards();
		startStructureBehavior(20*30);
	}
	
	public Structure(DBObject o) {
		this.name = (String) o.get("name");
		this.ofaction = (String) o.get("ofaction");
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
		this.banners = new ArrayList<Location>();
		this.ranged_guards = new HashMap<Location, Guard>();
		this.melee_guards = new HashMap<Location, Guard>();
		
		fillWithRuleset();
		spawnNPC();
		spawnAllGuards();
		startStructureBehavior(20*30);
	}
	
	public BasicDBObject toDB() {
		BasicDBObject o = new BasicDBObject();
		o.put("name", name);
		o.put("ofaction", ofaction);
		o.put("faction", faction);
		o.put("type", type);
		o.put("center", LocSerialization.getStringFromLocation(center));
		o.put("rotate", rotate);
		o.put("tier", tier);
		return o;
	}
	
	@SuppressWarnings("unchecked")
	private void fillWithRuleset() {
		if (this.type.equals("Castle")) rotate += 0;
		double angle = Math.toRadians(rotate);
		for (Location l : (ArrayList<Location>) Ruleset.Structures_ruleset.get(ofaction).get(type).get(tier-1).get("heart")) {
			l.setWorld(center.getWorld());
			Location end_l = UtilFuncs.rotateLocationAroundY(l, angle);
			end_l.add(center);
			heart.add(end_l);
		}
		for (Location l : (ArrayList<Location>) Ruleset.Structures_ruleset.get(ofaction).get(type).get(tier-1).get("banners")) {
			l.setWorld(center.getWorld());
			Location end_l = UtilFuncs.rotateLocationAroundY(l, angle);
			end_l.add(center);
			banners.add(end_l);
		}
		for (Location l : (ArrayList<Location>) Ruleset.Structures_ruleset.get(ofaction).get(type).get(tier-1).get("melee_guards")) {
			l.setWorld(center.getWorld());
			Location end_l = UtilFuncs.rotateLocationAroundY(l, angle);
			end_l.add(center);
			melee_guards.put(end_l, null);
		}
		for (Location l : (ArrayList<Location>) Ruleset.Structures_ruleset.get(ofaction).get(type).get(tier-1).get("ranged_guards")) {
			l.setWorld(center.getWorld());
			Location end_l = UtilFuncs.rotateLocationAroundY(l, angle);
			end_l.add(center);
			ranged_guards.put(end_l, null);
		}
		npc_loc = ((Location) Ruleset.Structures_ruleset.get(ofaction).get(type).get(tier-1).get("npc")).clone();
		npc_loc = UtilFuncs.rotateLocationAroundY(npc_loc, angle);
		npc_loc.add(center);
		protect_radius = (int) Ruleset.Structures_ruleset.get(ofaction).get(type).get(tier-1).get("protect_radius");
		if (type.equals("Castle") || type.equals("Fort") || type.equals("Watchtower") || type.equals("Stable") || type.equals("Temple")) {
			production_needed = Integer.valueOf(Ruleset.Structures_ruleset.get(ofaction).get(type).get(tier-1).get("production_needed").toString());
			production_rate = Integer.valueOf(Ruleset.Structures_ruleset.get(ofaction).get(type).get(tier-1).get("production_rate").toString());
			production_max = Integer.valueOf(Ruleset.Structures_ruleset.get(ofaction).get(type).get(tier-1).get("production_max").toString());
		}
	}
	
	public void spawnNPC() {
		String npc_name = "";
		if (type.equals("Castle") || type.equals("Fort") || type.equals("Watchtower"))
			npc_name = FactionRuleset.factionChatMsgColors.get(faction)+"Farmer";
		if (type.compareTo("Stable") == 0)
			npc_name = FactionRuleset.factionChatMsgColors.get(faction)+"Stable Holder";
		if (type.equals("Temple"))
			npc_name = FactionRuleset.factionChatMsgColors.get(faction)+"Priest";

		
		npc = (Villager) npc_loc.getWorld().spawnEntity(npc_loc, EntityType.VILLAGER);
		if (faction.equals("Greeks"))
			npc.setVillagerType(Type.PLAINS);
		else if (faction.equals("Persians"))
			npc.setVillagerType(Type.SAVANNA);
		else if (faction.equals("Egyptians"))
			npc.setVillagerType(Type.DESERT);
		
		
		RoCNPC.setFaction(npc, faction);
		RoCNPC.setStructure(npc, name);
        npc.setCustomName(npc_name);
        npc.setCustomNameVisible(true);
		npc.setInvulnerable(true);
        npc.setRemoveWhenFarAway(false);
        NBTEntity nbtent = new NBTEntity(npc);
        nbtent.setBoolean("NoAI", true);
        npc.getWorld().setChunkForceLoaded(npc.getLocation().getChunk().getX(), npc.getLocation().getChunk().getZ(), true);
	}
	
	public void killNPC() {
		npc.setHealth(0);
		npc = null;
	}
	
	private void startStructureBehavior(int ticks_frequence) {
		BukkitRunnable structureBehave = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		if (type.equals("Castle") || type.equals("Fort") || type.equals("Watchtower")) {
		    	    for(Player p : Bukkit.getOnlinePlayers()){
		    	        if (RoCPlayers.getFaction(p).compareTo(faction) == 0) {
		    	        	double current_food = RoCPlayers.getStructureFood(name, p);
		    	        	RoCPlayers.setStructureFood(name, p, Math.min(production_max*production_needed, current_food+production_rate));
		    	        }
		    	    }
	    		}
	    		if (type.equals("Stable")) {
		    	    for(Player p : Bukkit.getOnlinePlayers()){
		    	        if (RoCPlayers.getFaction(p).compareTo(faction) == 0) {
		    	        	double current_horse = RoCPlayers.getStructureHorse(name, p);
		    	        	RoCPlayers.setStructureHorse(name, p, Math.min(production_max*production_needed, current_horse+production_rate));
		    	        }
		    	    }
	    		}
	    		if (type.equals("Temple")) {
		    	    for(Player p : Bukkit.getOnlinePlayers()){
		    	        if (RoCPlayers.getFaction(p).compareTo(faction) == 0) {
		    	        	double current_buff = RoCPlayers.getStructureTempleBuff(name, p);
		    	        	RoCPlayers.setStructureTempleBuff(name, p, Math.min(production_max*production_needed, current_buff+production_rate));
		    	        }
		    	    }
	    		}
	    	}
	    };
	    structureBehave.runTaskTimer(RealmsOfChaos.getInstance(), ticks_frequence, ticks_frequence);
	}
	
	public void updateNPCs() {
		for (Guard guard : melee_guards.values())
			guard.setFaction(faction);
		for (Guard guard : ranged_guards.values())
			guard.setFaction(faction);
		if (patrollers != null)
			for (Patroller p : patrollers)
				p.setFaction(faction);
	}
	
	public void spawnAllGuards() {
		BukkitRunnable spawnGuards = new BukkitRunnable() {
			@Override
	        public void run() {
	    		for (Entry<Location, Guard> e : melee_guards.entrySet()) {
					Location npc_loc = e.getKey();
	    			if (e.getValue() == null) {
	    				Guard guard = new MeleeGuard(npc_loc, faction);
			            e.setValue(guard);
	    			}
	    		}
	    		for (Entry<Location, Guard> e : ranged_guards.entrySet()) {
					Location npc_loc = e.getKey();
	    			if (e.getValue() == null) {
	    				Guard guard = new RangedGuard(npc_loc, faction);
			            e.setValue(guard);
	    			}
	    		}
	    		if (PatrollersRuleset.structuresPatrolls.containsKey(name)) {
	    			patrollers = Patroller.spawnPatroll(name);
	    		}
	    	}
	    };
	    spawnGuards.runTaskLater(RealmsOfChaos.getInstance(), 20*2);
	}
	
	public void killGuards() {
		for (Guard rg : ranged_guards.values())
			if (rg != null)
				rg.kill();
		for (Guard mg : melee_guards.values())
			if (mg != null)
				mg.kill();
		if (patrollers != null)
			for (Patroller p : patrollers)
				if (p != null)
					p.kill();
	}
	
	public void updateBanners() {
		for (Location l : banners) {
			Banner banner = null;
			if (l.getBlock().getState() instanceof Banner) {
				banner = (Banner) l.getBlock().getState();
				Banners.updateBanner(banner, faction);
			}
			else
				Bukkit.getLogger().info(l.getBlock().getType().name() + " at " + l.toString());
		}
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
		if (this.getDisplayName().contains("Fort") || this.getDisplayName().contains("Castle")) return "Def";
		else return "Util";
	}
	
	public String getActType() {
		return this.type;
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

	public HashMap<Location, Guard> getRanged_guards() {
		return ranged_guards;
	}

	public void setRanged_guards(HashMap<Location, Guard> ranged_guards) {
		this.ranged_guards = ranged_guards;
	}

	public HashMap<Location, Guard> getMelee_guards() {
		return melee_guards;
	}

	public void setMelee_guards(HashMap<Location, Guard> melee_guards) {
		this.melee_guards = melee_guards;
	}
	
	public HashMap<Location, Guard> getGuard(){
		HashMap<Location, Guard> ret = new HashMap<Location, Guard>();
		for (Entry<Location, Guard> guard : getMelee_guards().entrySet())
			ret.put(guard.getKey(), guard.getValue());
		for (Entry<Location, Guard> guard : getRanged_guards().entrySet())
			ret.put(guard.getKey(), guard.getValue());
		return ret;
	}

	@Override
	public String toString() {
		return "Structure [name=" + name + ", faction=" + faction + ", tier=" + tier + ", protect_radius="
				+ protect_radius + ", heart=" + heart + ", ranged_guards=" + ranged_guards + ", melee_guards="
				+ melee_guards + ", raid=" + raid + "]";
	}

}
