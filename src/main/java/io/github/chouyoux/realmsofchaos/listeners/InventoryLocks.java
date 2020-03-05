package io.github.chouyoux.realmsofchaos.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;

public class InventoryLocks implements Listener{
    private RealmsOfChaos main;

	public InventoryLocks() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (RoCPlayers.getClass(player).compareTo("") == 0) return;
		if (event.getSlotType() != SlotType.QUICKBAR) return;
		int slot = event.getSlot();
		if (slot != 1 && slot != 2 && slot != 3) event.setCancelled(true);
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent event) {
		Player player = (Player) event.getPlayer();
		if (RoCPlayers.getClass(player).compareTo("") == 0) return;
		PlayerInventory inventory = player.getInventory();
		int slot = inventory.getHeldItemSlot();
		ItemStack handItemStack = inventory.getItemInMainHand();
		if (slot != 1 && slot != 2 && slot != 3 && handItemStack.getType() == Material.AIR)
			event.setCancelled(true);
	}

}
