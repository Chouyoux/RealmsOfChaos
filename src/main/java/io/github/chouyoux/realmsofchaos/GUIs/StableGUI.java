package io.github.chouyoux.realmsofchaos.GUIs;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCHorses;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;

public class StableGUI implements InventoryHolder, Listener {
    private Inventory inv;
    private String structure;
	private RealmsOfChaos main;

    public StableGUI(String structure, Player player) {
		this.main = RealmsOfChaos.getInstance();
		this.structure = structure;
        main.getServer().getPluginManager().registerEvents(this, main);
        inv = Bukkit.createInventory(this, 9, "Stable");
        initializeItems(structure, player);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems(String structure, Player player) {
    	double current_horse = RoCPlayers.getStructureHorse(structure, player);
    	if (current_horse < 1) return;
    	for (int i = 0 ; i < current_horse ; i++) {
    		inv.addItem(createGuiItem(Material.SADDLE, "Claim mount"));
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
        if (e.getInventory().getHolder() != this) return;
        e.setCancelled(true);
        if (e.getRawSlot() >= 9) return;

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() != Material.SADDLE) return;
        
        inv.remove(clickedItem);
        
        double current_horses = RoCPlayers.getStructureHorse(structure, p);
        RoCPlayers.setStructureHorse(structure, p, Math.max(0, current_horses-1));
        
        Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
        horse.setTamed(true);
        horse.setOwner(p);
        horse.addPassenger(p);
        
        for (Entity entity : p.getWorld().getEntities()) {
        	if (entity instanceof Horse && RoCHorses.getOwner((Horse) entity).compareTo(p.getDisplayName()) == 0)
        		((Horse) entity).setHealth(0);
        }
        
        RoCHorses.setOwner(horse, p.getDisplayName());
    }
}