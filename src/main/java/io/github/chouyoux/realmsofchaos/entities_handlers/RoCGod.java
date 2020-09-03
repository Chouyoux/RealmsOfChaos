package io.github.chouyoux.realmsofchaos.entities_handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Type;

import de.tr7zw.nbtapi.NBTEntity;

public class RoCGod {
	
	public static void spawn() {
		
		String npc_name = "God";
		Location npc_loc = new Location(Bukkit.getWorld("Realms"), 253.5, 157, 665.5, 90, 0);
		Villager npc = (Villager) npc_loc.getWorld().spawnEntity(npc_loc, EntityType.VILLAGER);
		npc.setVillagerType(Type.SNOW);
        npc.setCustomName(npc_name);
        npc.setCustomNameVisible(true);
        npc.setRemoveWhenFarAway(false);
        npc.setInvulnerable(true);
        NBTEntity nbtent = new NBTEntity(npc);
        nbtent.setBoolean("NoAI", true);
        npc.getWorld().setChunkForceLoaded(npc.getLocation().getChunk().getX(), npc.getLocation().getChunk().getZ(), true);
		
	}

}
