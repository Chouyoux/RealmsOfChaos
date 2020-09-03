package io.github.chouyoux.realmsofchaos.ticks;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.Guard;
import io.github.chouyoux.realmsofchaos.custom_mobs.MeleeGuard;
import io.github.chouyoux.realmsofchaos.custom_mobs.MeleePatroller;
import io.github.chouyoux.realmsofchaos.custom_mobs.Patroller;
import io.github.chouyoux.realmsofchaos.custom_mobs.RangedGuard;
import io.github.chouyoux.realmsofchaos.custom_mobs.RangedPatroller;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.memory.Factions;

public class NPCTick extends BukkitRunnable {

    public NPCTick() {
    	this.runTaskTimer(RealmsOfChaos.instance, 0, 20);
    }

    @Override
    public void run() {
    	
    	HashMap<String, Double> factionrpr = new HashMap<String, Double>();
    	for (String faction : Factions.factions.keySet())
    		factionrpr.put(faction, 0.0);
    	
    	for (Player p : Bukkit.getServer().getOnlinePlayers())
    		if (RoCPlayers.getFaction(p) == null) factionrpr.put(RoCPlayers.getFaction(p), factionrpr.get(RoCPlayers.getFaction(p))+1);
    	
    	int total = Bukkit.getServer().getOnlinePlayers().size();
    	
    	for (Double part : factionrpr.values())
    		part = part / total;
    	
    	for (Guard guard : Guard.spawnedGuards) {
    		if (guard == null) continue;
    		guard.setArmor(guard.getBaseArmor() * (1-factionrpr.get(guard.getFaction())));
    	}
    	
    	for (ArrayList<Patroller> patroller_list : Patroller.spawnedPatrollers.values()) {
    		if (patroller_list == null) continue;
        	for (Patroller patroller : patroller_list) {
        		if (patroller == null) continue;
	    		patroller.setArmor(patroller.getBaseArmor() * (1-factionrpr.get(patroller.getFaction())));
        	}
    	}
    	
    }

}

