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

public class GodGUI implements InventoryHolder, Listener {
    private Inventory inv;
	private RealmsOfChaos main;

    public GodGUI() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
        inv = Bukkit.createInventory(this, 9, "God");
        initializeItems();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
    	inv.addItem(UtilFuncs.createGuiItem(Material.WHITE_WOOL, "Faction", "§aOpens Faction UI"));
    	inv.addItem(UtilFuncs.createGuiItem(Material.GRAY_WOOL, "Class", "§aOpens Class UI"));
    	inv.addItem(UtilFuncs.createGuiItem(Material.BLACK_WOOL, "Trainer", "§aOpens Trainer UI"));
    	inv.addItem(UtilFuncs.createGuiItem(Material.RED_WOOL, "Leave Tutorial", "§aOnce you're ready"));
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
        case WHITE_WOOL:
        	p.closeInventory();
    		new FactionGUI().openInventory(p);
        	break;
        case GRAY_WOOL:
        	p.closeInventory();
    		new ClassGUI().openInventory(p);
        	break;
        case BLACK_WOOL:
        	if ( !(RoCPlayers.getFaction(p).equals("") || RoCPlayers.getClass(p).equals("")) )
        		new TrainerGUI(p, RoCPlayers.getClass(p), RoCPlayers.getFaction(p)).openInventory(p);
        	break;
        case RED_WOOL:
        	if ( !(RoCPlayers.getFaction(p).equals("") || RoCPlayers.getClass(p).equals("")) )
        		p.setHealth(0);
        	break;
		default:
			break;
        }
    }
}