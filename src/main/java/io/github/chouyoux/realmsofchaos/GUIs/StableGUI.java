package io.github.chouyoux.realmsofchaos.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCHorses;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.GradesRuleset;
import io.github.chouyoux.realmsofchaos.util.ArmorColors;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

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
    	if (current_horse < 2) return;
    	for (int i = 0 ; i < current_horse ; i+=2) {
    		inv.addItem(UtilFuncs.createGuiItem(Material.SADDLE, "§fClaim Mount", null));
    	}
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
        
        RoCPlayers.setStructureHorse(structure, p, 0);
        
        Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
        RoCNPC.setFaction(horse, RoCPlayers.getFaction(p));
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
        horse.getInventory().setArmor(new ItemStack(Material.LEATHER_HORSE_ARMOR));
        ArmorColors.updateArmorColor(horse, RoCPlayers.getFaction(p));
        horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue( GradesRuleset.stableSpeed.get(p.getLevel()) );
        horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue( GradesRuleset.stableJump.get(p.getLevel()) );
        horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue( GradesRuleset.stableHealth.get(p.getLevel()) );
        horse.setTamed(true);
        horse.setOwner(p);
        horse.setAI(false);
        horse.setCustomName(FactionRuleset.factionChatMsgColors.get(RoCPlayers.getFaction(p))+p.getName()+"'s Horse");
        horse.addPassenger(p);
        
        RoCHorses.killPlayerHorse(p);
        RoCHorses.setOwner(horse, p.getUniqueId().toString());
    }
}