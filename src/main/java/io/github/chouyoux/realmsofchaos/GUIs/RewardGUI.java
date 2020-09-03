package io.github.chouyoux.realmsofchaos.GUIs;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.util.Materials;

public class RewardGUI implements InventoryHolder, Listener {
    private Inventory inv;
    private Player player;

    public RewardGUI(Player player, ArrayList<ItemStack> rewards) {
    	this.player = player;
        RealmsOfChaos.instance.getServer().getPluginManager().registerEvents(this, RealmsOfChaos.instance);
        inv = Bukkit.createInventory(this, 9, ChatColor.GREEN+""+ChatColor.BOLD+"Rewards !");
        initializeItems(rewards);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void initializeItems(ArrayList<ItemStack> rewards) {
    	int i = 0;
    	for (ItemStack reward : rewards) {
    		inv.setItem(i, Materials.RoCItem(reward));
    		i++;
    	}
    }

    public void openInventory() {
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) return;
        e.setCancelled(true);
        if (e.getRawSlot() >= 9) return;

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null) return;
        
        p.getInventory().addItem(new ItemStack(clickedItem));
        inv.remove(clickedItem);
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
    	if (e.getInventory().getHolder() != this) return;
    	for (ItemStack item : inv.getContents()) {
    		if (item == null) continue;
    		player.getInventory().addItem(item);
    	}
    		
    }
}