package io.github.chouyoux.realmsofchaos.listeners;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.GUIs.EnchanterGUI;
import io.github.chouyoux.realmsofchaos.GUIs.FarmerGUI;
import io.github.chouyoux.realmsofchaos.GUIs.GodGUI;
import io.github.chouyoux.realmsofchaos.GUIs.ScribeGUI;
import io.github.chouyoux.realmsofchaos.GUIs.StableGUI;
import io.github.chouyoux.realmsofchaos.GUIs.StatTrainerGUI;
import io.github.chouyoux.realmsofchaos.GUIs.TeleporterGUI;
import io.github.chouyoux.realmsofchaos.GUIs.TrainerGUI;
import io.github.chouyoux.realmsofchaos.challenge.Challenge;
import io.github.chouyoux.realmsofchaos.challenge.ChallengesList;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCHorses;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.memory.Factions;
import io.github.chouyoux.realmsofchaos.memory.Regions;
import io.github.chouyoux.realmsofchaos.memory.Structures;
import io.github.chouyoux.realmsofchaos.objects.Faction;
import io.github.chouyoux.realmsofchaos.objects.Region;
import io.github.chouyoux.realmsofchaos.objects.Structure;
import io.github.chouyoux.realmsofchaos.ruleset.ChatRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.DroppableRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.GradesRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.MaterialNamesRuleset;
import io.github.chouyoux.realmsofchaos.ticks.RaidTick;
import io.github.chouyoux.realmsofchaos.data_handlers.YMLFiles;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.raidstone.wgevents.events.RegionEnteredEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

public class Player_Connect implements Listener {
    private RealmsOfChaos main;
    
    public static HashMap<Player, LocalTime> lastInteraction;
    public static HashMap<Player, LivingEntity> targets;

	public Player_Connect() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
        lastInteraction = new HashMap<Player, LocalTime>();
        targets = new HashMap<Player, LivingEntity>();
    }
	
	@EventHandler
	public void reduceLag(ChunkLoadEvent event) {
		Bukkit.getServer().getWorld("Realms").setChunkForceLoaded(event.getChunk().getX(), event.getChunk().getZ(), false);
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if (RoCPlayers.getCombatTime(player) > 0) {
			player.setHealth(0);
		}

    	BukkitRunnable disconnectionTask = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		if (player.isOnline()) return;
	    		
	            RoCHorses.killPlayerHorse(player);
	    	}
	    };
	    disconnectionTask.runTaskLater(main, 20*60);
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
		Player p = event.getPlayer();
		String fileName = "players/"+p.getUniqueId();
		String PlayerIP = YMLFiles.get(fileName).getString("Last_IP");
		for (Entry<String, FileConfiguration> entry : YMLFiles.customFiles.entrySet()) {
		    String e_fileName = entry.getKey();
		    FileConfiguration e_customFile = entry.getValue();
			if (YMLFiles.get(e_fileName).contains("Last_IP") && e_fileName.compareTo(fileName) < 0) {
				String IP = e_customFile.getString("Last_IP");
				if (IP == PlayerIP) { event.disallow(null, "Already taken IP"); }
			}
		}
		
		Bukkit.getScheduler().runTaskLater(RealmsOfChaos.getInstance(), new Runnable() {
	        @Override
	        public void run() {
	        	RoCPlayers.updateAllAttributes(p);
	        	
	        	if (RoCPlayers.getFaction(p).equals(""))
	        		p.teleport(new Location(Bukkit.getWorld("Realms"), 238.5, 157, 661.5, -90, 0));
        		
        		RoCPlayers.setSkin(p, FactionRuleset.factionSkin.get(RoCPlayers.getFaction(p)));
        		RoCPlayers.setPrefix(p, FactionRuleset.factionHexColors.get(RoCPlayers.getFaction(p)));
        		RoCPlayers.setGrade(event.getPlayer(), GradesRuleset.grades.get(event.getPlayer().getLevel()));
	        }
	    }, 20);
	}
	
	@EventHandler
	public void onLevelUp(PlayerLevelChangeEvent event) {
		if (event.getPlayer().getLevel() > 6) return;
		RoCPlayers.setGrade(event.getPlayer(), GradesRuleset.grades.get(event.getPlayer().getLevel()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEntityEvent event) {
		if (event.getHand() != null && event.getHand().equals(EquipmentSlot.HAND)) return;
		Player player = event.getPlayer();
		if (event.getRightClicked() instanceof Villager) {
			Villager v = (Villager) event.getRightClicked();
			if (v.getName().contains("God"))
				new GodGUI().openInventory(player);
		}
		
		LocalTime now = LocalTime.now();
		if (lastInteraction.containsKey(player)) {
			long millis = lastInteraction.get(player).until(now, ChronoUnit.MILLIS);
		    if (millis <= 2)
		    	return;
			
		}
		lastInteraction.put(player, now);
		
		if (event.getRightClicked() instanceof Villager && RoCNPC.sameFaction(player, (LivingEntity) event.getRightClicked())) {
			Villager v = (Villager) event.getRightClicked();
			if (v.getName().contains("Teleporter"))
				new TeleporterGUI(RoCPlayers.getFaction(player)).openInventory(player);
			else if (v.getName().contains("StatTrainer"))
				new StatTrainerGUI(player).openInventory(player);
			else if (v.getName().contains("Trainer"))
				new TrainerGUI(player, RoCPlayers.getClass(player), RoCPlayers.getFaction(player)).openInventory(player);
			else if (v.getName().contains("Enchanter"))
				new EnchanterGUI(player).openInventory(player);
			else if (v.getName().contains("Farmer"))
				new FarmerGUI(RoCNPC.getStructure(v), player).openInventory(player);
			else if (v.getName().contains("Scribe"))
				new ScribeGUI(player).openInventory();
			else if (v.getName().contains("Stable Holder")) {
				if (player.getLevel() == 0) {
					player.sendMessage(ChatColor.RED+"No horse available for "+GradesRuleset.grades.get(player.getLevel())+".");
				}
				else
					new StableGUI(RoCNPC.getStructure(v), player).openInventory(player);
			}
			else if (v.getName().contains("Keeper"))
				player.openInventory(player.getEnderChest());
			else if (v.getName().contains("Priest")) {
				String name = RoCNPC.getStructure(v);
				Structure structure = Structures.structures.get(RoCNPC.getStructure(v));
				if (player.getLevel() == 0) {
					player.sendMessage(ChatColor.RED+"No buff available for "+GradesRuleset.grades.get(player.getLevel())+".");
				}
				else if (RoCPlayers.getStructureTempleBuff(name, player) >= structure.production_needed) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, GradesRuleset.templeDuration.get(player.getLevel()), GradesRuleset.templeAmplifier.get(player.getLevel())));
					RoCPlayers.setStructureTempleBuff(name, player, 0.0);
					player.sendMessage(ChatColor.GREEN+"The Priest gave you a benediction.");
					return;
				}
				else {
					double toLoad = 1 - (RoCPlayers.getStructureTempleBuff(name, player)/structure.production_needed);
					player.sendMessage(ChatColor.RED+"No buff available until " + new DecimalFormat("##.##").format(toLoad*60) + " minute(s).");
				}
			}
		}
		if (event.getRightClicked() instanceof Horse && player.getVehicle() == null) {
			if (RoCNPC.sameFaction(player, (LivingEntity) event.getRightClicked()) && RoCHorses.getOwner((Horse) event.getRightClicked()).equals(player.getUniqueId().toString())) {
				Horse h = (Horse) event.getRightClicked();
				h.addPassenger(player);
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if (RoCPlayers.getFaction(player).equals("")) return;

		Challenge challenge = ChallengesList.getPlayerChallenge(event.getPlayer());
		if (challenge != null) return;
		
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
			p.sendTitle("", FactionRuleset.factionChatMsgColors.get(holding_faction)+struct.getDisplayName(), (int)(0.8*20), (int)(1.4*20), (int)(0.8*20));
			if (player_faction.compareTo(holding_faction) != 0 && ! struct.isRaid()) { // Player enters an enemy Structure
				Region reg = null;
				if (struct.getName().toLowerCase().contains("chaos")) {
					String[] parts = struct.getName().split("_");
					String reg_name = parts[0] + "_" + parts[1] + "_" + parts[2];
					reg = Regions.regions.get(reg_name);
				}
				else
					reg = Regions.regions.get(struct.getName().split("_")[0]);
				if (reg != null) {
					BukkitRunnable task = new RaidTick(main, struct, reg);
					task.runTaskTimer(main, 8, 8);
					String key = holding_faction.toLowerCase()+"_"+player_faction.toLowerCase();
					Bukkit.broadcastMessage(FactionRuleset.factionChatMsgColors.get(player_faction)+player_faction+" "+ChatRuleset.getClearColor(key, ChatColor.WHITE)+" "+FactionRuleset.factionChatMsgColors.get(holding_faction)+struct.getDisplayName());
				}
			}
		}
		else if (Regions.regions.containsKey(region_name)) { // Player enters a Region
			RoCPlayers.setRegion(p, region_name);
			Region reg = Regions.regions.get(region_name);
			String holding_faction = reg.getFaction();
			p.sendTitle("", FactionRuleset.factionChatMsgColors.get(holding_faction)+reg.getDisplayName()+" "+ChatRuleset.getClearColor(reg.getName().split("_")[0], ChatColor.WHITE), (int)(0.8*20), (int)(1.4*20), (int)(0.8*20));
		}
		for (Faction f : Factions.factions.values()) {
			String spawn_region = f.getSpawnRegion();
			String display_spawn_region = f.getDisplaySpawnRegion();
			if (spawn_region.compareTo(region_name) == 0) { // Players enters a spawn
				p.sendTitle("", FactionRuleset.factionChatMsgColors.get(f.getName())+display_spawn_region, (int)(0.8*20), (int)(1.4*20), (int)(0.8*20));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity attacker = event.getDamager();
		boolean was_projectile = false;
		boolean was_phys_proj = false;

		if (victim instanceof Villager) {
			event.setCancelled(true);
			return;
		}
		
		if (attacker instanceof Projectile) {
			if (attacker instanceof Arrow || attacker instanceof Trident)
				was_phys_proj = true;
			ProjectileSource real_attacker = ((Projectile) attacker).getShooter();
			attacker = (Entity) real_attacker;
			was_projectile = true;
		}
		
		if (RoCNPC.sameFaction(attacker, victim)) {
	        event.setCancelled(true);
	        return;
		}
		
		if (attacker instanceof Player && !attacker.hasMetadata("NPC")) {
			Player a = (Player) attacker;
			if (!was_projectile && a.getVelocity().getY() + 0.0784000015258789 < 0)
				event.setDamage(event.getDamage()/1.5F);
			if (was_phys_proj)
				event.setDamage(event.getDamage()*RoCPlayers.getPhysicalATK(a));
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
		
		if (!victim.hasMetadata("NPC") && victim instanceof Player && !attacker.hasMetadata("NPC") && attacker instanceof Player) {
			RoCPlayers.setCombatTime((Player) victim, 12);
			RoCPlayers.setCombatTime((Player) attacker, 12);
		}
	}
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) return;
		
		LivingEntity player = (LivingEntity) event.getEntity();
		
		if (player instanceof Player)
			event.setAmount(event.getAmount()*RoCPlayers.getRegenHP((Player)player));
			
			
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		double random = Math.random();
		
		if (!player.getLocation().getWorld().getName().toLowerCase().contains("realms")) return;
		
		HashMap<Material, Integer> material_dropped = new HashMap<Material, Integer>();
		
		for (ItemStack item : player.getInventory()) {
			if (item == null || item.getType().equals(Material.AIR)) continue;
			for (Entry<Material, Double> material_rate : DroppableRuleset.droppables.entrySet()) {
				if (item.getType().equals(material_rate.getKey())) {
					int dropped = Math.max(1, (int) (item.getAmount() * material_rate.getValue()));
					Material mat = item.getType();
					item.setAmount(item.getAmount() - dropped);
					ItemStack dropped_item = new ItemStack(mat, dropped);
					ItemMeta m = dropped_item.getItemMeta();
					m.setDisplayName(MaterialNamesRuleset.names.get(dropped_item.getType()));
					dropped_item.setItemMeta(m);
					player.getWorld().dropItemNaturally(player.getLocation(), dropped_item);
					if (material_dropped.get(mat) != null) {
						material_dropped.put(mat, material_dropped.get(mat)+dropped);
					}
					else {
						material_dropped.put(mat, dropped);
					}
				}
			}
		}
		for (Entry<Material, Integer> mat : material_dropped.entrySet())
			player.sendMessage(ChatColor.RED+"Upon death, you lost " + mat.getValue() + " " + MaterialNamesRuleset.names.get(mat.getKey()) + ".");
		
		if (RoCPlayers.getCombatTime(player) > 0) {
			ItemStack soul = new ItemStack(Material.EMERALD);
			ItemMeta m = soul.getItemMeta();
			m.setDisplayName(MaterialNamesRuleset.names.get(soul.getType()));
			soul.setItemMeta(m);
			player.getWorld().dropItemNaturally(player.getLocation(), soul);
		}
		
		if (random < 0.25 && player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getEnchantments().entrySet() != null) {
			int size = player.getInventory().getHelmet().getEnchantments().entrySet().size();
			if (size <= 0) return;
			int item = new Random().nextInt(size);
			int i = 0;
			Entry<Enchantment, Integer> randomEnchant = null;
			for(Entry<Enchantment, Integer> obj : player.getInventory().getHelmet().getEnchantments().entrySet())
			{
			    if (i == item)
			    	randomEnchant = obj;
			    i++;
			}
			if (randomEnchant != null) {
				player.getInventory().getHelmet().removeEnchantment(randomEnchant.getKey());
				if (randomEnchant.getValue() > 1)
					player.getInventory().getHelmet().addUnsafeEnchantment(randomEnchant.getKey(), randomEnchant.getValue()-1);
			}
			player.sendMessage(ChatColor.RED+"Upon death, your helmet lost a level of "+randomEnchant.getKey()+".");
		}
		else if (random < 0.5 && player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getEnchantments().entrySet() != null) {
			int size = player.getInventory().getChestplate().getEnchantments().entrySet().size();
			if (size <= 0) return;
			int item = new Random().nextInt(size);
			int i = 0;
			Entry<Enchantment, Integer> randomEnchant = null;
			for(Entry<Enchantment, Integer> obj : player.getInventory().getChestplate().getEnchantments().entrySet())
			{
			    if (i == item)
			    	randomEnchant = obj;
			    i++;
			}
			if (randomEnchant != null) {
				player.getInventory().getChestplate().removeEnchantment(randomEnchant.getKey());
				if (randomEnchant.getValue() > 1)
					player.getInventory().getChestplate().addUnsafeEnchantment(randomEnchant.getKey(), randomEnchant.getValue()-1);
			}
			player.sendMessage(ChatColor.RED+"Upon death, your chestplate lost a level of "+randomEnchant.getKey().toString()+".");
		}
		else if (random < 0.75 && player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getEnchantments().entrySet() != null) {
			int size = player.getInventory().getLeggings().getEnchantments().entrySet().size();
			if (size <= 0) return;
			int item = new Random().nextInt(size);
			int i = 0;
			Entry<Enchantment, Integer> randomEnchant = null;
			for(Entry<Enchantment, Integer> obj : player.getInventory().getLeggings().getEnchantments().entrySet())
			{
			    if (i == item)
			    	randomEnchant = obj;
			    i++;
			}
			if (randomEnchant != null) {
				player.getInventory().getLeggings().removeEnchantment(randomEnchant.getKey());
				if (randomEnchant.getValue() > 1)
					player.getInventory().getLeggings().addUnsafeEnchantment(randomEnchant.getKey(), randomEnchant.getValue()-1);
			}
			player.sendMessage(ChatColor.RED+"Upon death, your leggings lost a level of "+randomEnchant.getKey().toString()+".");
		}
		else if (random <= 1 && player.getInventory().getBoots() != null && player.getInventory().getBoots().getEnchantments().entrySet() != null) {
			int size = player.getInventory().getBoots().getEnchantments().entrySet().size();
			if (size <= 0) return;
			int item = new Random().nextInt(size);
			int i = 0;
			Entry<Enchantment, Integer> randomEnchant = null;
			for(Entry<Enchantment, Integer> obj : player.getInventory().getBoots().getEnchantments().entrySet())
			{
			    if (i == item)
			    	randomEnchant = obj;
			    i++;
			}
			if (randomEnchant != null) {
				player.getInventory().getBoots().removeEnchantment(randomEnchant.getKey());
				if (randomEnchant.getValue() > 1)
					player.getInventory().getBoots().addUnsafeEnchantment(randomEnchant.getKey(), randomEnchant.getValue()-1);
			}
			player.sendMessage(ChatColor.RED+"Upon death, your boots lost a level of "+randomEnchant.getKey().toString()+".");
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
						    	w.spawnParticle(Particle.HEART, temp.getLocation(), 2, -1, 1.5, 0, 0.5);
					        }
						}
					}
		        }
		    };
		    for (int i = 0; i < 20; i++) {
		    	BukkitRunnable task2 = new BukkitRunnable() {
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
