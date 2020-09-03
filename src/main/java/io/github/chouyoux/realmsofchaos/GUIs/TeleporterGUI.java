package io.github.chouyoux.realmsofchaos.GUIs;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.memory.Regions;
import io.github.chouyoux.realmsofchaos.objects.Region;
import io.github.chouyoux.realmsofchaos.ruleset.RegionIconsRuleset;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

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
    		if (region.getKey().toLowerCase().contains("chaos")) continue;
    		if (region.getValue().getFaction().equals(faction)) {
    			ItemStack item = UtilFuncs.createGuiItem(Material.SLIME_BALL, region.getValue().getDisplayName(), "§aTeleports you right there");
    			inv.addItem(RegionIconsRuleset.updateIcon(item, region.getKey()));
    		}
    		else {
    			ItemStack item = UtilFuncs.createGuiItem(Material.EGG, region.getValue().getDisplayName(), "§cNot available");
    			inv.addItem(RegionIconsRuleset.updateIcon(item, region.getKey()));
    		}
    	}
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
        if (clickedItem == null || clickedItem.getType() != Material.SLIME_BALL) return;
        
        Location tp_loc = new Location(null, 0, 0, 0);
        for (Region region : Regions.regions.values())
        	if (region.getDisplayName().compareTo(clickedItem.getItemMeta().getDisplayName()) == 0) {
        		tp_loc = region.getSpawn().clone();
        	}

        // Using slots click is a best option for your inventory click's
        if (p.getCooldown(Material.SLIME_BALL) <= 0) {
        	p.playSound(p.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0.5f);
	        p.teleport(tp_loc);
	        p.setCooldown(Material.SLIME_BALL, 20*300);
        }
    }
}