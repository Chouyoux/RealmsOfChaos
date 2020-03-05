package io.github.chouyoux.realmsofchaos.listeners;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.GUIs.FarmerGUI;
import io.github.chouyoux.realmsofchaos.GUIs.StableGUI;
import io.github.chouyoux.realmsofchaos.GUIs.TeleporterGUI;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.memory.Factions;
import io.github.chouyoux.realmsofchaos.memory.Regions;
import io.github.chouyoux.realmsofchaos.memory.Structures;
import io.github.chouyoux.realmsofchaos.objects.Faction;
import io.github.chouyoux.realmsofchaos.objects.Region;
import io.github.chouyoux.realmsofchaos.objects.Structure;
import io.github.chouyoux.realmsofchaos.data_handlers.YMLFiles;
import net.raidstone.wgevents.events.RegionEnteredEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import gyurix.api.TitleAPI;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Player_Connect implements Listener {
    private RealmsOfChaos main;

	public Player_Connect() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
    }
	
	@EventHandler
	public void onPreConnect(AsyncPlayerPreLoginEvent event) {
		String fileName = "players/"+event.getUniqueId();
		YMLFiles.setup(fileName);
		if (! YMLFiles.get(fileName).contains("Last_IP")) {
			YMLFiles.get(fileName).addDefault("Last_IP", "None");
			YMLFiles.get(fileName).options().copyDefaults(true);
		}
		YMLFiles.get(fileName).set("Last_IP", event.getAddress().toString());
		YMLFiles.save(fileName);
	}
	
	@EventHandler
	public void onConnect(PlayerLoginEvent event) {
		String fileName = "players/"+event.getPlayer().getUniqueId();
		String PlayerIP = YMLFiles.get(fileName).getString("Last_IP");
		for (Entry<String, FileConfiguration> entry : YMLFiles.customFiles.entrySet()) {
		    String e_fileName = entry.getKey();
		    FileConfiguration e_customFile = entry.getValue();
			if (YMLFiles.get(e_fileName).contains("Last_IP") && e_fileName.compareTo(fileName) < 0) {
				String IP = e_customFile.getString("Last_IP");
				if (IP == PlayerIP) { event.disallow(null, "Already taken IP"); }
			}
		}
		RoCPlayers.updateAllAttributes(event.getPlayer());
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (event.getRightClicked() instanceof Villager) {
			event.setCancelled(true);
			Villager v = (Villager) event.getRightClicked();
			if (! RoCNPC.sameFaction(v, player))
				return;
			if (v.getName().compareTo("Teleporter") == 0)
				new TeleporterGUI(RoCPlayers.getFaction(player)).openInventory(player);
			if (v.getName().compareTo("Farmer") == 0)
				new FarmerGUI(RoCNPC.getStructure(v), player).openInventory(player);
			if (v.getName().compareTo("Stable Holder") == 0)
				new StableGUI(RoCNPC.getStructure(v), player).openInventory(player);
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Faction pl_faction = Factions.factions.get(RoCPlayers.getFaction(player));
		event.setRespawnLocation(pl_faction.getSpawn());
	}
	
	@EventHandler
	public void onRegionEntered(RegionEnteredEvent event) {
		String region_name = event.getRegionName();
		Player p = event.getPlayer();
		String player_faction = RoCPlayers.getFaction(p);
		if (Structures.structures.containsKey(region_name)) { // Player enters a Structure
			Structure struct = Structures.structures.get(region_name);
			String holding_faction = struct.getFaction();
			TitleAPI.set("", struct.getDisplayName()+"("+holding_faction+")", (int)(0.6*20), (int)(1.2*20), (int)(0.6*20), p);
			if (player_faction.compareTo(holding_faction) != 0 && ! struct.isRaid()) { // Player enters an enemy Structure
				Region reg = Regions.regions.get(struct.getName().split("_")[0]);
				BukkitRunnable task = new RaidTick(main, struct, reg);
				task.runTaskTimer(main, 8, 8);
				Bukkit.broadcastMessage("A Raid has started at "+struct.getDisplayName()+" !");
			}
		}
		else if (Regions.regions.containsKey(region_name)) { // Player enters a Region
			RoCPlayers.setRegion(p, region_name);
			Region reg = Regions.regions.get(region_name);
			String holding_faction = reg.getFaction();
			TitleAPI.set("", reg.getDisplayName()+"("+holding_faction+")", (int)(0.6*20), (int)(1.2*20), (int)(0.6*20), p);
		}
		for (Faction f : Factions.factions.values()) {
			String spawn_region = f.getSpawnRegion();
			String display_spawn_region = f.getDisplaySpawnRegion();
			if (spawn_region.compareTo(region_name) == 0) { // Players enters a spawn
				if (f.getName().compareTo(player_faction) != 0) { // Enemy one
					TitleAPI.set("", display_spawn_region, (int)(0.8*20), (int)(1.4*20), (int)(0.8*20), p);
				}
				else {                                           // Ally one
					TitleAPI.set("", display_spawn_region, (int)(0.8*20), (int)(1.4*20), (int)(0.8*20), p);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {
        Player eventPlayer = event.getPlayer();
        NamespacedKey key = new NamespacedKey(main, eventPlayer.getUniqueId()+"NbBlockPlaced");
        PersistentDataContainer container = eventPlayer.getPersistentDataContainer();
        int foundValue = -1;
        if(container.has(key , PersistentDataType.INTEGER)) {
            foundValue = container.get(key, PersistentDataType.INTEGER);
        }
        foundValue++;
        container.set(key, PersistentDataType.INTEGER, foundValue);
        //Bukkit.broadcastMessage(eventPlayer.getDisplayName()+" has placed "+Integer.toString(foundValue)+" blocks.");
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity attacker = event.getDamager();
		boolean was_projectile = false;

		if (victim instanceof Villager) {
			event.setCancelled(true);
			return;
		}
		
		if (attacker instanceof Projectile) {
			ProjectileSource real_attacker = ((Projectile) attacker).getShooter();
			attacker = (Entity) real_attacker;
			was_projectile = true;
		}
		
		if (attacker instanceof Player && victim instanceof Player && RoCPlayers.sameFaction((Player) attacker, (Player) victim)) {
	        event.setCancelled(true);
	        return;
		}
		
		if (attacker instanceof Player && (victim instanceof Pillager || victim instanceof Vindicator)) {
			LivingEntity v = (LivingEntity) victim;
			Player a = (Player) attacker;
			if (RoCNPC.sameFaction(v, a)) {
	        	event.setCancelled(true);
	        	return;
			}
			else {
	            v.setAI(true);
				((Mob) v).setTarget(a);
			}
		}
		
		if (attacker instanceof Player) {
			Player a = (Player) attacker;
			if (!was_projectile && a.getVelocity().getY() + 0.0784000015258789 < 0)
				event.setDamage(event.getDamage()/1.5F);
			if (Math.random() <= RoCPlayers.getCritChance(a)) {
				Location victim_pos = victim.getLocation().clone();
				World w = victim_pos.getWorld();
		    	w.spawnParticle(Particle.CRIT, victim_pos, 2, 0, 1.5, 1, 0.5);
		    	w.spawnParticle(Particle.CRIT, victim_pos, 2, 0, 1.5, -1, 0.5);
		    	w.spawnParticle(Particle.CRIT, victim_pos, 2, 1, 1.5, 0, 0.5);
		    	w.spawnParticle(Particle.CRIT, victim_pos, 2, -1, 1.5, 0, 0.5);
		    	w.spawnParticle(Particle.CRIT, victim_pos, -2, 0, 1.5, 1, 0.5);
		    	w.spawnParticle(Particle.CRIT, victim_pos, -2, 0, 1.5, -1, 0.5);
		    	w.spawnParticle(Particle.CRIT, victim_pos, -2, 1, 1.5, 0, 0.5);
		    	w.spawnParticle(Particle.CRIT, victim_pos, -2, -1, 1.5, 0, 0.5);
				w.playSound(victim_pos, Sound.ENTITY_PLAYER_ATTACK_CRIT, 1, 1);
				event.setDamage(event.getDamage()*1.5F);
			}
		}
	}
	
	@EventHandler
	public void onEggHit(ProjectileHitEvent event) {
		final ProjectileSource eventShooter = event.getEntity().getShooter();
		Projectile shot = event.getEntity();
		if (eventShooter instanceof Player && shot instanceof Egg) {
			final World w = shot.getWorld();
			final Dolphin dolphin = (Dolphin) w.spawnEntity(shot.getLocation(), EntityType.DOLPHIN);
	    	w.spawnParticle(Particle.HEART, dolphin.getLocation(), 1);
			dolphin.setVelocity(new Vector(0, 3, 0));
			final ArrayList<Player> nearby = new ArrayList<Player>();
		    double range = 50;
		    for (Entity e : dolphin.getNearbyEntities(range, range, range)){
		        if (e instanceof Player){
		            nearby.add((Player) e);
		        }
		    }
		    BukkitRunnable task = new BukkitRunnable() {
		        @Override
		        public void run() {
		        	for (int i = 0 ; i < 20 ; i++)
		        		w.spawnParticle(Particle.HEART, dolphin.getLocation(), 5, 1, 1, 1, 1, null, true);
				    for (Player temp : nearby) {
				    	Vector vectorToPlayer = temp.getLocation().toVector().subtract(dolphin.getLocation().toVector());
				    	vectorToPlayer.divide(new Vector(5, 5, 5));
				    	Egg egg = (Egg) w.spawnEntity(dolphin.getLocation(), EntityType.EGG);
						egg.setVelocity(vectorToPlayer);
				    	if (eventShooter instanceof Player && temp instanceof Player) {
					        if (RoCPlayers.sameFaction((Player) eventShooter, (Player) temp)) {
						    	temp.setHealth(20);
						    	w.spawnParticle(Particle.HEART, temp.getLocation(), 2, 0, 1.5, 1, 0.5);
						    	w.spawnParticle(Particle.HEART, temp.getLocation(), 2, 0, 1.5, -1, 0.5);
						    	w.spawnParticle(Particle.HEART, temp.getLocation(), 2, 1, 1.5, 0, 0.5);
						    	w.spawnParticle(Particle.HEART, temp.getLocation(), 2, -1, 1.5, 0, 0.5);
					        }
						}
					}
		        }
		    };
		    for (int i = 0; i < 20; i++) {
		    	BukkitRunnable task2 = new  BukkitRunnable() {
			    	@Override
			        public void run() {
		        		w.spawnParticle(Particle.HEART, dolphin.getLocation(), 5, 1, 1, 1, 1, null, true);
			    	}
			    };
		    	task2.runTaskLater(main, i);
		    }
		    task.runTaskLater(main, 20 * 1);
		}
	}
	
}
