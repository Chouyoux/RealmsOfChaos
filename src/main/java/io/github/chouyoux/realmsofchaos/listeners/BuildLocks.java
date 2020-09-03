package io.github.chouyoux.realmsofchaos.listeners;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.memory.Structures;

public class BuildLocks implements Listener{
    private RealmsOfChaos main;

	public BuildLocks() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (RoCPlayers.getClass(player).equals("Builder")) return;
		Block block = event.getBlockPlaced();
		Location location = block.getLocation();
		if (!(Structures.isAHeartLoc(location)) || block.getType() != Material.OBSIDIAN)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (RoCPlayers.getClass(player).equals("Builder")) return;
		Block block = event.getBlock();
		Location location = block.getLocation();
		if (!(Structures.isAHeartLoc(location)) || block.getType() != Material.OBSIDIAN)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onDamageBlock(BlockDamageEvent event) {
		Player player = event.getPlayer();
		if (RoCPlayers.getClass(player).equals("Builder")) return;
		Block block = event.getBlock();
		Location location = block.getLocation();
		if (!(Structures.isAHeartLoc(location)))
			event.setCancelled(true);
	}

	// ???
	@EventHandler
	public void onPlaceBlockCountThem(BlockPlaceEvent event) {
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
	
}
