package io.github.chouyoux.realmsofchaos.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.HorseInventory;
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
		if (RoCPlayers.getClass(player).equals("Builder")) return;
		if (event.getSlotType() != SlotType.QUICKBAR && event.getSlotType() != SlotType.ARMOR) return;
		int slot = event.getSlot();
		if (slot != 6 && slot != 7 && slot != 8) event.setCancelled(true);
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent event) {
		Player player = (Player) event.getPlayer();
		if (RoCPlayers.getClass(player).equals("Builder")) return;
		PlayerInventory inventory = player.getInventory();
		int slot = inventory.getHeldItemSlot();
		ItemStack handItemStack = inventory.getItemInMainHand();
		if (slot != 6 && slot != 7 && slot != 8 && handItemStack.getType() == Material.AIR)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onSwitchItem(PlayerSwapHandItemsEvent event) {
		Player player = (Player) event.getPlayer();
		if (RoCPlayers.getClass(player).equals("Builder")) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onHorseInventory(InventoryOpenEvent event) {
		Player player = (Player) event.getPlayer();
		if (RoCPlayers.getClass(player).equals("Builder")) return;
		if (!(event.getInventory() instanceof HorseInventory)) return;
		
		event.setCancelled(true);
		player.openInventory(player.getInventory());
	}

}
