package io.github.chouyoux.realmsofchaos.custom_mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcmonkey.sentinel.SentinelTrait;
import org.mcmonkey.sentinel.targeting.SentinelTargetLabel;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.memory.Factions;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Poses;
import net.citizensnpcs.trait.waypoint.WanderWaypointProvider;
import net.citizensnpcs.trait.waypoint.Waypoint;
import net.citizensnpcs.trait.waypoint.WaypointProvider;
import net.citizensnpcs.trait.waypoint.Waypoints;

public abstract class CustomAI {
	
	private double baseArmor;
	private double armor;
	private double health = 20;
	private int damage;
	private int healRate = 20*5;
	private int attackRate = 20;
	private double moveSpeed = 1.25;
	private double chaseRange;
	private double aggroRange;
	private int respawnTime;
	private Location spawnPoint;
	private ArrayList<Location> wayPoints;
	private boolean closeChase = true;
	private boolean fightback = false;
	private boolean realistic = true;
	private boolean ignoreLOS = false;
	private int pathFindingRange = 60;
	private EntityType type;
	private Material weaponType;
	
	private String name;
	private String faction;
	
	private ItemStack weapon;
	private NPC npc;
	
	public static HashMap<NPC, CustomAI> NPCToMob = new HashMap<NPC, CustomAI>();

	public CustomAI(double armor, int damage, double chaseRange, double aggroRange, int respawnTime, Location spawnPoint,
			EntityType type, String name, String faction, Material weaponType) {
		super();
		this.baseArmor = armor;
		this.armor = armor;
		this.damage = damage;
		this.chaseRange = chaseRange;
		this.aggroRange = aggroRange;
		this.respawnTime = respawnTime;
		this.spawnPoint = spawnPoint;
		this.type = type;
		this.name = name;
		this.faction = faction;
		this.weaponType = weaponType;
		this.wayPoints = new ArrayList<Location>();
		this.weapon = new ItemStack(weaponType, 1);
		createNPC();
		NPCToMob.put(this.npc, this);
	}
	
	public CustomAI(double armor, int damage, double health, int healRate, int attackRate, double moveSpeed, double chaseRange, double aggroRange, int respawnTime, Location spawnPoint,
			EntityType type, String name, String faction, Material weaponType, boolean closeChase, boolean fightback,
			boolean realistic, boolean ignoreLOS, int pathFindingRange) {
		super();
		this.baseArmor = armor;
		this.armor = armor;
		this.damage = damage;
		this.health = health;
		this.healRate = healRate;
		this.attackRate = attackRate;
		this.moveSpeed = moveSpeed;
		this.chaseRange = chaseRange;
		this.aggroRange = aggroRange;
		this.respawnTime = respawnTime;
		this.spawnPoint = spawnPoint;
		this.type = type;
		this.name = name;
		this.faction = faction;
		this.weaponType = weaponType;
		this.closeChase = closeChase;
		this.fightback = fightback;
		this.realistic = realistic;
		this.ignoreLOS = ignoreLOS;
		this.pathFindingRange = pathFindingRange;
		this.wayPoints = new ArrayList<Location>();
		this.weapon = new ItemStack(weaponType, 1);
		createNPC();
		NPCToMob.put(this.npc, this);
	}
	
	protected void createNPC() {
		this.npc = CitizensAPI.getNPCRegistry().createNPC(type, FactionRuleset.factionChatNameColors.get(faction)+name);
		npc.spawn(spawnPoint);
		npc.getNavigator().getDefaultParameters().range(pathFindingRange);
		npc.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, "hover");
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.damage = damage;
		sentinel.armor = armor;
		sentinel.setHealth(health);
		sentinel.health = health;
		sentinel.healRate = healRate;
		sentinel.attackRate = attackRate;
		sentinel.attackRateRanged = attackRate;
		sentinel.speed = moveSpeed;
		sentinel.chaseRange = chaseRange;
		sentinel.respawnTime = respawnTime;
		sentinel.spawnPoint = spawnPoint.clone();
		sentinel.range = aggroRange;
		sentinel.closeChase = closeChase;
		sentinel.fightback = fightback;
		sentinel.realistic = realistic;
		sentinel.ignoreLOS = ignoreLOS;
		setFaction(faction);
		
		for (String _faction : Factions.factions.keySet())
    		if (!_faction.equals(faction))
    			new SentinelTargetLabel("faction:"+_faction).addToList(sentinel.allTargets);
    	LivingEntity entity = (LivingEntity) npc.getEntity();
		RoCNPC.setFaction(entity, faction);
		entity.getEquipment().setItemInMainHand(weapon.clone());
		entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		
	}
	
	public void removeSentinel() {
		npc.removeTrait(SentinelTrait.class);
	}
	
	public void addSentinel() {
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.damage = damage;
		sentinel.armor = armor;
		sentinel.health = health;
		sentinel.healRate = healRate;
		sentinel.attackRate = attackRate;
		sentinel.attackRateRanged = attackRate;
		sentinel.speed = moveSpeed;
		sentinel.chaseRange = chaseRange;
		sentinel.respawnTime = respawnTime;
		sentinel.spawnPoint = spawnPoint.clone();
		sentinel.range = aggroRange;
		sentinel.closeChase = closeChase;
		sentinel.fightback = fightback;
		sentinel.realistic = realistic;
		sentinel.ignoreLOS = ignoreLOS;
		setFaction(faction);
	}
	
	protected void setPoseAtSpawnPoint() {
		if (!npc.hasTrait(Poses.class)) {
			npc.addTrait(Poses.class);
        }
		Poses poses = npc.getTrait(Poses.class);
		poses.addPose("default", spawnPoint, true);
		poses.setDefaultPose("default");
	}
	
	public void addWaypoint(Location loc) {
		wayPoints.add(loc);

		if (!npc.hasTrait(Waypoints.class)) {
			npc.addTrait(Waypoints.class);
        }
        Waypoints wp = npc.getTrait(Waypoints.class);
        if ((wp.getCurrentProvider() instanceof WaypointProvider.EnumerableWaypointProvider)) {
            ((List<Waypoint>) ((WaypointProvider.EnumerableWaypointProvider) wp.getCurrentProvider()).waypoints())
                    .add(new Waypoint(loc.clone()));
        }
        else if ((wp.getCurrentProvider() instanceof WanderWaypointProvider)) {
            ((WanderWaypointProvider) wp.getCurrentProvider()).getRegionCentres()
                    .add(loc.clone());
        }
	}
	
	public void removeWaypoint(Location loc) {
		wayPoints.remove(loc);

        Waypoints wp = npc.getTrait(Waypoints.class);
        if ((wp.getCurrentProvider() instanceof WaypointProvider.EnumerableWaypointProvider)) {
            ((List<Waypoint>) ((WaypointProvider.EnumerableWaypointProvider) wp.getCurrentProvider()).waypoints())
                    .remove(new Waypoint(loc.clone()));
        }
        else if ((wp.getCurrentProvider() instanceof WanderWaypointProvider)) {
            ((WanderWaypointProvider) wp.getCurrentProvider()).getRegionCentres()
                    .remove(loc.clone());
        }
	}

	public double getArmor() {
		return armor;
	}

	public int getDamage() {
		return damage;
	}

	public double getChaseRange() {
		return chaseRange;
	}

	public double getAggroRange() {
		return aggroRange;
	}

	public int getRespawnTime() {
		return respawnTime;
	}

	public Location getSpawnPoint() {
		return spawnPoint;
	}

	public ArrayList<Location> getWayPoints() {
		return wayPoints;
	}

	public boolean isCloseChase() {
		return closeChase;
	}

	public boolean isFightback() {
		return fightback;
	}

	public EntityType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getFaction() {
		return faction;
	}
	
	public Material getWeaponType() {
		return weaponType;
	}
	
	public ItemStack getWeapon() {
		return weapon;
	}

	public NPC getNpc() {
		return npc;
	}
	
	public Entity getEntity() {
		return npc.getEntity();
	}

	public double getBaseArmor() {
		return baseArmor;
	}

	public boolean isRealistic() {
		return realistic;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.health = health;
	}

	public int getHealRate() {
		return healRate;
	}

	public void setHealRate(int healRate) {
		this.healRate = healRate;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.healRate = healRate;
	}

	public int getAttackRate() {
		return attackRate;
	}

	public void setAttackRate(int attackRate) {
		this.attackRate = attackRate;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.attackRate = attackRate;
	}

	public double getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.speed = moveSpeed;
	}

	public boolean isIgnoreLOS() {
		return ignoreLOS;
	}

	public void setIgnoreLOS(boolean ignoreLOS) {
		this.ignoreLOS = ignoreLOS;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.ignoreLOS = ignoreLOS;
	}

	public void setRealistic(boolean realistic) {
		this.realistic = realistic;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.realistic = realistic;
	}

	public void setBaseArmor(double baseArmor) {
		this.baseArmor = baseArmor;
	}

	public void setArmor(double armor) {
		this.armor = armor;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.armor = armor;
	}

	public void setDamage(int damage) {
		this.damage = damage;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.damage = damage;
	}

	public void setChaseRange(double chaseRange) {
		this.chaseRange = chaseRange;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.chaseRange = chaseRange;
	}

	public void setAggroRange(double aggroRange) {
		this.aggroRange = aggroRange;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.range = aggroRange;
	}

	public void setRespawnTime(int respawnTime) {
		this.respawnTime = respawnTime;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.respawnTime = respawnTime;
	}

	public void setSpawnPoint(Location spawnPoint) {
		this.spawnPoint = spawnPoint;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.spawnPoint = spawnPoint;
	}

	public void setCloseChase(boolean closeChase) {
		this.closeChase = closeChase;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.closeChase = closeChase;
	}

	public void setFightback(boolean fightback) {
		this.fightback = fightback;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		sentinel.fightback = fightback;
	}

	public void setName(String name) {
		this.name = name;
		npc.setName(FactionRuleset.factionChatNameColors.get(faction)+name);
	}

	public void setFaction(String faction) {
		this.faction = faction;
		SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
		for (String _faction : Factions.factions.keySet())
    		if (!_faction.equals(faction)) {
    			new SentinelTargetLabel("faction:"+_faction).addToList(sentinel.allTargets);
    			new SentinelTargetLabel("faction:"+faction).removeFromList(sentinel.allIgnores);
    		}
		if (!faction.equals("Chaos")) {
	    	new SentinelTargetLabel("faction:Chaos").removeFromList(sentinel.allTargets);
	    	new SentinelTargetLabel("faction:Chaos").addToList(sentinel.allTargets);
		}
    	new SentinelTargetLabel("faction:"+faction).removeFromList(sentinel.allTargets);
    	new SentinelTargetLabel("faction:"+faction).addToList(sentinel.allIgnores);
    	
    	setName(name);

		if (!npc.isSpawned()) return;
    	LivingEntity entity = (LivingEntity) npc.getEntity();
		RoCNPC.setFaction(entity, faction);
	}
	
	public void setWeapon(ItemStack weapon) {
		this.weapon = weapon.clone();

		if (!npc.isSpawned()) return;
    	LivingEntity entity = (LivingEntity) npc.getEntity();
		entity.getEquipment().setItemInMainHand(weapon.clone());
	}
	
	public void kill() {
		if (npc.isSpawned()) {
			((LivingEntity) npc.getEntity()).setHealth(0);
		}
	}
	
}
