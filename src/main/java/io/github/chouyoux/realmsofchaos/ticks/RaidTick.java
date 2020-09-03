package io.github.chouyoux.realmsofchaos.ticks;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.memory.Regions;
import io.github.chouyoux.realmsofchaos.memory.Structures;
import io.github.chouyoux.realmsofchaos.objects.Region;
import io.github.chouyoux.realmsofchaos.objects.Structure;
import io.github.chouyoux.realmsofchaos.quest.Quest;
import io.github.chouyoux.realmsofchaos.quest.QuestAttackStructure;
import io.github.chouyoux.realmsofchaos.quest.QuestDefendStructure;
import io.github.chouyoux.realmsofchaos.quest.QuestRegistry;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class RaidTick extends BukkitRunnable {

    private final JavaPlugin plugin;
    private Structure structure;
    private Region region;
    private BossBar bossBar;

    public RaidTick(JavaPlugin plugin, Structure structure, Region region) {
    	structure.setRaid(true);
    	bossBar = Bukkit.createBossBar("Heart of "+structure.getDisplayName(), BarColor.BLUE, BarStyle.SOLID);
        this.plugin = plugin;
        this.structure = structure;
        this.region = region;
    }
    
    private void onSuccess(List<LivingEntity> nearby, String faction_max) {
    	this.cancel();
    	plugin.getServer().broadcastMessage(FactionRuleset.factionChatMsgColors.get(faction_max)+faction_max + "§f captured " + FactionRuleset.factionChatMsgColors.get(faction_max)+structure.getDisplayName());
    	structure.setFaction(faction_max);
    	structure.setRaid(false);
    	structure.updateNPCs();
    	structure.killGuards();
    	structure.killNPC();
    	structure.spawnNPC();
    	structure.updateBanners();
    	if (structure.getType().compareTo("Def") == 0) {
    		region.setFaction(structure.getFaction());
    		region.updateBanners();
    	}
    	bossBar.removeAll();
    	Structures.UpdateStructures();
    	Regions.UpdateRegions();

        for (LivingEntity e : nearby) if (e instanceof Player){
        	Player p = (Player) e;
        	if (!RoCPlayers.getFaction(p).equals(faction_max)) continue;
	    	for (Quest quest : QuestRegistry.instance.getFactionQuests(faction_max)) {
				if (quest instanceof QuestAttackStructure) {
					QuestAttackStructure killaQuest = (QuestAttackStructure) quest;
					if (killaQuest.hasPlayer(p) && killaQuest.structureType.equalsIgnoreCase(structure.getActType()))
						killaQuest.setProgress(p, 1);
				}
			}
        }
    }
    
    private void onFailure(List<LivingEntity> nearby) {
    	this.cancel();
    	plugin.getServer().broadcastMessage(FactionRuleset.factionChatMsgColors.get(structure.getFaction())+structure.getFaction() + "§f defended " + FactionRuleset.factionChatMsgColors.get(structure.getFaction())+structure.getDisplayName());
    	structure.setRaid(false);
    	bossBar.removeAll();
    	for (Location loc : structure.getHeart())
    		loc.getBlock().setType(Material.OBSIDIAN);
    	
    	for (LivingEntity e : nearby) if (e instanceof Player){
        	Player p = (Player) e;
        	if (!RoCPlayers.getFaction(p).equals(structure.getFaction())) continue;
	    	for (Quest quest : QuestRegistry.quests) {
				if (quest instanceof QuestDefendStructure) {
					QuestDefendStructure killdQuest = (QuestDefendStructure) quest;
					if (killdQuest.hasPlayer(p))
						killdQuest.setProgress(p, 1);
				}
			}
        }
    }

    @Override
    public void run() {
    	double r = (double) structure.getProtect_radius();
    	Location center = structure.getCenter().clone();
    	Location motion = center.clone();
        for (double j = 0; j < 360; j+=0.25) {
        	double angle = Math.toRadians(j);
        	motion.setX(center.getX() + r*Math.sin(angle));
        	motion.setZ(center.getZ() + r*Math.cos(angle));
        	while (motion.getBlock().isPassable() && motion.getY() > 0)
        		motion.setY(motion.getY()-1);
    		motion.setY(motion.getY()+1);
        	center.getWorld().spawnParticle(Particle.FLAME, motion.clone().add(0, 0.1, 0), 0, 0, 1, 0, 0.2, null, true);
        }
    	double a_block_part = (double) 1.0/structure.getHeart().size();
    	int missing_hrt_block = 0;
    	for (Location loc : structure.getHeart()) {
    		Block block = center.getWorld().getBlockAt(loc);
    		if (block.getType().toString().compareTo("AIR") == 0) {
    			missing_hrt_block++;
    		}
    	}
    	double progress = 1.0-a_block_part*(double)missing_hrt_block;
    	bossBar.setProgress(progress);
    	int nbAttackers = 0;
    	HashMap<String, Integer> factions_population = new HashMap<String, Integer>();
    	List<LivingEntity> nearby = UtilFuncs.getNearbyEntities(center, r);
        for (LivingEntity e : nearby) if (e instanceof Player){
        	Player p = (Player) e;
        	String faction = RoCPlayers.getFaction(p);
        	if (faction.compareTo(structure.getFaction()) != 0) {
        		nbAttackers++;
            	if (factions_population.containsKey(faction))
            		factions_population.put(faction, factions_population.get(faction)+1);
            	else
            		factions_population.put(faction, 1);
        	}
        	bossBar.addPlayer(p);
        }
        if (missing_hrt_block == structure.getHeart().size()) { // Raiders wins
        	String faction_max = "";
        	for (Location loc : structure.getHeart())
        		loc.getBlock().setType(Material.OBSIDIAN);
        	int max = 0;
        	for (Entry<String, Integer> a : factions_population.entrySet()) {
        		if (a.getValue() > max) {
        			faction_max = a.getKey();
        			max = a.getValue();
        		}
        	}
        	onSuccess(nearby, faction_max);
        }
        else if (nbAttackers == 0) { // Defenders wins
        	onFailure(nearby);
        }
    }

}
