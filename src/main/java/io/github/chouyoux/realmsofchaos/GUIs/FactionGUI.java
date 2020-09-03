package io.github.chouyoux.realmsofchaos.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class FactionGUI implements InventoryHolder, Listener {
    private Inventory inv;
	private RealmsOfChaos main;

    public FactionGUI() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
        inv = Bukkit.createInventory(this, 9, "Faction");
        initializeItems();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
    	inv.addItem(UtilFuncs.createGuiItem(Material.YELLOW_WOOL, "Egyptians", "§aMakes you an Egyptian"));
    	inv.addItem(UtilFuncs.createGuiItem(Material.RED_WOOL, "Greeks", "§aMakes you a Greek"));
    	inv.addItem(UtilFuncs.createGuiItem(Material.BLUE_WOOL, "Persians", "§aMakes you a Persian"));
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
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        
        switch(clickedItem.getType()) {
        case YELLOW_WOOL:
        	RoCPlayers.setFaction(p, "Egyptians");
        	break;
        case RED_WOOL:
        	RoCPlayers.setFaction(p, "Greeks");
        	break;
        case BLUE_WOOL:
        	RoCPlayers.setFaction(p, "Persians");
        	break;
		default:
			break;
        }
    	p.closeInventory();
    }
}