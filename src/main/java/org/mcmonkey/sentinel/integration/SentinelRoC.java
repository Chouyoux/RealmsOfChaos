package org.mcmonkey.sentinel.integration;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.mcmonkey.sentinel.SentinelIntegration;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import net.citizensnpcs.api.CitizensAPI;

public class SentinelRoC extends SentinelIntegration {

    @Override
    public String getTargetHelp() {
        return "faction:FACTION_NAME";
    }

    @Override
    public String[] getTargetPrefixes() {
        return new String[] { "faction" };
    }

    @Override
    public boolean isTarget(LivingEntity ent, String prefix, String value) {
    	if (ent instanceof Villager)
    		return false;
        try {
            if (prefix.equals("faction"))
            	if (ent instanceof Player && !(CitizensAPI.getNPCRegistry().isNPC(ent))) {
            		return RoCPlayers.getFaction((Player) ent).equals(value);
            	}
            	else {
            		return RoCNPC.getFaction(ent).equals(value);
            	}
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}