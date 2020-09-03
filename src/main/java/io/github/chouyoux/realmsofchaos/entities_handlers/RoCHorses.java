package io.github.chouyoux.realmsofchaos.entities_handlers;

import static io.github.chouyoux.realmsofchaos.RealmsOfChaos.getInstance;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;

public class RoCHorses {

	public static String getOwner(Horse h) {
		NamespacedKey key = new NamespacedKey(getInstance(), "Owner");
        PersistentDataContainer h_container = h.getPersistentDataContainer();
        String h_foundvalue = "";
        if(h_container.has(key , PersistentDataType.STRING)) {
        	h_foundvalue = h_container.get(key, PersistentDataType.STRING);
        }
		return h_foundvalue;
	}

	public static void setOwner(Horse h, String playerUUID) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Owner");
		PersistentDataContainer container = h.getPersistentDataContainer();
		container.set(key, PersistentDataType.STRING, playerUUID);
	}
	
	public static Horse getPlayerHorse(Player p) {
		for (Entity entity : p.getWorld().getEntities()) {
        	if (entity instanceof Horse && RoCHorses.getOwner((Horse) entity).compareTo(p.getUniqueId().toString()) == 0)
        		return ((Horse) entity);
        }
		return null;
	}
	
	public static void killPlayerHorse(Player p) {
        Horse ancient_horse = RoCHorses.getPlayerHorse(p);
        if (ancient_horse != null)
        	ancient_horse.setHealth(0);
	}
	
}
