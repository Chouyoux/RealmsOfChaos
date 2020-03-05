package io.github.chouyoux.realmsofchaos.GUIs;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;

public class FarmerGUI implements InventoryHolder, Listener {
    private Inventory inv;
    private String structure;
	private RealmsOfChaos main;

    public FarmerGUI(String structure, Player player) {
		this.main = RealmsOfChaos.getInstance();
		this.structure = structure;
        main.getServer().getPluginManager().registerEvents(this, main);
        inv = Bukkit.createInventory(this, 9, "Farmer");
        initializeItems(structure, player);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems(String structure, Player player) {
    	double current_food = RoCPlayers.getStructureFood(structure, player);
    	for (int i = 0 ; i < current_food ; i++) {
    		inv.addItem(new ItemStack(Material.APPLE));
    	}
    }

    // Nice little method to create a gui item with a custom name, and description
    @SuppressWarnings("unused")
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
        if (e.getInventory().getHolder() != this) return;
        e.setCancelled(true);
        if (e.getRawSlot() >= 9) return;

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() != Material.APPLE) return;
        
        int nb_food = clickedItem.getAmount();
        p.getInventory().addItem(new ItemStack(clickedItem));
        inv.remove(clickedItem);
        double current_food = RoCPlayers.getStructureFood(structure, p);
        RoCPlayers.setStructureFood(structure, p, Math.max(0, current_food-nb_food));
    }
}