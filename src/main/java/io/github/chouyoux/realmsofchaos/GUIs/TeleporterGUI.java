package io.github.chouyoux.realmsofchaos.GUIs;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.memory.Regions;
import io.github.chouyoux.realmsofchaos.objects.Region;

public class TeleporterGUI implements InventoryHolder, Listener {
    private Inventory inv;
	private RealmsOfChaos main;

    public TeleporterGUI(String faction) {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
        inv = Bukkit.createInventory(this, 9, "Teleporter");
        initializeItems(faction);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems(String faction) {
    	for (Entry<String, Region> region : Regions.regions.entrySet()) {
    		if (region.getValue().getFaction().compareTo(faction) == 0)
    			inv.addItem(createGuiItem(Material.GREEN_WOOL, region.getValue().getDisplayName(), "§aTeleports you right there"));
    		else
    	        inv.addItem(createGuiItem(Material.RED_WOOL, region.getValue().getDisplayName(), "§cNot available"));
    	}
    }

    // Nice little method to create a gui item with a custom name, and description
    private ItemStack createGuiItem(Material material, String name, String...lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>();

        for(String loreComments : lore) {
            metaLore.add(loreComments);
        }

        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    // You can open the inventory with this
    public void openInventory(Player p) {
        p.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) {
            return;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            e.setCancelled(true);
        }
        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.getType() == Material.RED_WOOL) return;
        
        Location tp_loc = new Location(null, 0, 0, 0);
        for (Region region : Regions.regions.values())
        	if (region.getDisplayName().compareTo(clickedItem.getItemMeta().getDisplayName()) == 0) {
        		tp_loc = region.getSpawn().clone();
        	}

        // Using slots click is a best option for your inventory click's
        p.teleport(tp_loc);
    }
}