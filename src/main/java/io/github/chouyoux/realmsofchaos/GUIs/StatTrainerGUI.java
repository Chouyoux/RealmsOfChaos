package io.github.chouyoux.realmsofchaos.GUIs;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.ruleset.ChatRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.EnchantRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.MaterialNamesRuleset;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class StatTrainerGUI implements InventoryHolder, Listener {
	
	public static int price = 20;
	
    private Inventory inv;
	private RealmsOfChaos main;
	private Player player;

    public StatTrainerGUI(Player player) {
		this.main = RealmsOfChaos.getInstance();
		this.player = player;
        main.getServer().getPluginManager().registerEvents(this, main);
        inv = Bukkit.createInventory(this, 27, "Statistics Trainer");
        initializeItems();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
    	ArrayList<String> plusLoreList = new ArrayList<String>();
    	plusLoreList.add("§fWill increase this stat at the cost");
    	plusLoreList.add("§fof a Soul.");
    	plusLoreList.add("Placeholder");

    	ArrayList<String> minusLoreList = new ArrayList<String>();
    	minusLoreList.add("§fWill decrease this stat.");
    	minusLoreList.add("Placeholder");

		String stat = "";
    	for (int i = 0; i <= 4; i ++) {
			switch (i) {
			case 0:	stat = "DEX"; break;
			case 1:	stat = "STR"; break;
			case 2:	stat = "VIT"; break;
			case 3:	stat = "INT"; break;
			case 4:	stat = "FAI"; break;
			}
			plusLoreList.set(2, "§a+ 1 "+stat);
			minusLoreList.set(1, "§c- 1 "+stat);
			
        	inv.setItem(i+9, UtilFuncs.createGuiItemL(Material.COMPARATOR, "§a§oPlus", plusLoreList));
        	inv.setItem(i+18, UtilFuncs.createGuiItemL(Material.REPEATER, "§c§oMinus", minusLoreList));
    	}

    	setStatItems();
    	setStatPointsItems();
    }
    
    private void setStatItems() {
    	ArrayList<String> dexLoreList = new ArrayList<String>();
    	dexLoreList.add("§f+0.5% §7Critical Hit Chance");
    	dexLoreList.add("§f+0.5% §7Attack Speed");

    	ArrayList<String> strLoreList = new ArrayList<String>();
    	strLoreList.add("§f+0.5% §7Physical Attack");

    	ArrayList<String> vitLoreList = new ArrayList<String>();
    	vitLoreList.add("§f+1% §7Max Health Points");
    	vitLoreList.add("§f+1% §7Natural Regeneration");
    	vitLoreList.add("§f-0.5% §7Negative Effects Duration");
    	ArrayList<String> intLoreList = new ArrayList<String>();
    	intLoreList.add("§f+2% §7Magical Attack");

    	ArrayList<String> faiLoreList = new ArrayList<String>();
    	faiLoreList.add("§f+1% §7Healing Efficiency");
    	faiLoreList.add("§f+1% §7Positive Effects Duration");

    	ItemStack dexItem = UtilFuncs.createGuiItemL(Material.DARK_OAK_BOAT, "§b§lDEXTERITY (" + RoCPlayers.getDEX(player) + ")", dexLoreList);
    	inv.setItem(0, dexItem);
    	
    	ItemStack strItem = UtilFuncs.createGuiItemL(Material.SPRUCE_BOAT, "§4§lSTRENGTH (" + RoCPlayers.getSTR(player) + ")", strLoreList);
    	inv.setItem(1, strItem);
   
    	ItemStack vitItem = UtilFuncs.createGuiItemL(Material.JUNGLE_BOAT, "§a§lVITALITY (" + RoCPlayers.getVIT(player) + ")", vitLoreList);
    	inv.setItem(2, vitItem);
   
    	ItemStack intItem = UtilFuncs.createGuiItemL(Material.OAK_BOAT, "§5§lINTELLIGENCE (" + RoCPlayers.getINT(player) + ")", intLoreList);
    	inv.setItem(3, intItem);

    	ItemStack faiItem = UtilFuncs.createGuiItemL(Material.BIRCH_BOAT, "§f§lFAITH (" + RoCPlayers.getFAI(player) + ")", faiLoreList);
    	inv.setItem(4, faiItem);
    }
    
    private void setStatPointsItems() {
    	ArrayList<String> statPointsLoreList = new ArrayList<String>();
    	statPointsLoreList.add("§fActual usage : "+RoCPlayers.getStatsSum(player)+"/"+RoCPlayers.getStatPoints(player));

    	inv.setItem(8, UtilFuncs.createGuiItemL(EnchantRuleset.materials.get(5), "§f§uStat Points§r§f : "+RoCPlayers.getStatPoints(player), statPointsLoreList));
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

        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR || e.getSlot() > 26) return;
        
        int slot = e.getSlot();
        if (slot > 8 && slot < 18) { // Clicked plus
        	if (RoCPlayers.getStatPoints(player) <= RoCPlayers.getStatsSum(player)) {
            	player.sendMessage(ChatColor.RED+"You already have the maximum Stats you can have ("+RoCPlayers.getStatPoints(player)+").");
        		return;
        	}

        	int amount = (e.getClick().isShiftClick()) ? Math.min(RoCPlayers.getStatPoints(player)-RoCPlayers.getStatsSum(player), 10) : 1;
        	
        	if (!player.getInventory().contains(EnchantRuleset.materials.get(5), amount)) {
            	player.sendMessage(ChatColor.RED+"You don't have enough Souls to buy "+amount+" Stats.");
        		return;
        	}
        	
        	amount = Math.min(RoCPlayers.getNbItem(player, EnchantRuleset.materials.get(5)), amount);

			ItemStack itemRemove = EnchantRuleset.prices.get(5).get(0).clone();
			ItemMeta m = itemRemove.getItemMeta();
			m.setDisplayName(MaterialNamesRuleset.names.get(itemRemove.getType()));
			itemRemove.setItemMeta(m);
			itemRemove.setAmount(amount);
			player.getInventory().removeItem(itemRemove);
        	
        	if (slot - 9 == 0) { // Dexterity
        		RoCPlayers.setDEX(player, RoCPlayers.getDEX(player)+amount);
            	player.sendMessage(ChatColor.GREEN+"You gained "+amount+" "+ ChatRuleset.getClearColor("dex", ChatColor.GREEN)+".");
        	}
        	else if (slot - 9 == 1) { // Strength
        		RoCPlayers.setSTR(player, RoCPlayers.getSTR(player)+amount);
            	player.sendMessage(ChatColor.GREEN+"You gained "+amount+" "+ ChatRuleset.getClearColor("str", ChatColor.GREEN)+".");
        	}
        	else if (slot - 9 == 2) { // Vitality
        		RoCPlayers.setVIT(player, RoCPlayers.getVIT(player)+amount);
            	player.sendMessage(ChatColor.GREEN+"You gained "+amount+" "+ ChatRuleset.getClearColor("vit", ChatColor.GREEN)+".");
        	}
        	else if (slot - 9 == 3) { // Intelligence
        		RoCPlayers.setINT(player, RoCPlayers.getINT(player)+amount);
            	player.sendMessage(ChatColor.GREEN+"You gained "+amount+" "+ ChatRuleset.getClearColor("int", ChatColor.GREEN)+".");
        	}
        	else if (slot - 9 == 4) { // Faith
        		RoCPlayers.setFAI(player, RoCPlayers.getFAI(player)+amount);
            	player.sendMessage(ChatColor.GREEN+"You gained "+amount+" "+ ChatRuleset.getClearColor("fai", ChatColor.GREEN)+".");
        	}
        	else
        		return;
    		setStatItems();
    		setStatPointsItems();
        }
        else if (slot > 17 && slot < 27) { // Clicked minus
        	int amount = 1;
        	if (slot - 18 == 0) { // Dexterity
        		if (RoCPlayers.getDEX(player) <= 0) return;
            	if (e.getClick().isShiftClick()) amount = Math.min(RoCPlayers.getDEX(player), 10);
        		RoCPlayers.setDEX(player, RoCPlayers.getDEX(player)-amount);
            	player.sendMessage(ChatColor.RED+"You reduced your "+ ChatRuleset.getClearColor("dex", ChatColor.RED)+" by "+amount+".");
        	}
        	else if (slot - 18 == 1) { // Strength
        		if (RoCPlayers.getSTR(player) <= 0) return;
            	if (e.getClick().isShiftClick()) amount = Math.min(RoCPlayers.getSTR(player), 10);
        		RoCPlayers.setSTR(player, RoCPlayers.getSTR(player)-amount);
            	player.sendMessage(ChatColor.RED+"You reduced your "+ ChatRuleset.getClearColor("str", ChatColor.RED)+" by "+amount+".");
        	}
        	else if (slot - 18 == 2) { // Vitality
        		if (RoCPlayers.getVIT(player) <= 0) return;
            	if (e.getClick().isShiftClick()) amount = Math.min(RoCPlayers.getVIT(player), 10);
        		RoCPlayers.setVIT(player, RoCPlayers.getVIT(player)-amount);
            	player.sendMessage(ChatColor.RED+"You reduced your "+ ChatRuleset.getClearColor("vit", ChatColor.RED)+" by "+amount+".");
        	}
        	else if (slot - 18 == 3) { // Intelligence
        		if (RoCPlayers.getINT(player) <= 0) return;
            	if (e.getClick().isShiftClick()) amount = Math.min(RoCPlayers.getINT(player), 10);
        		RoCPlayers.setINT(player, RoCPlayers.getINT(player)-amount);
            	player.sendMessage(ChatColor.RED+"You reduced your "+ ChatRuleset.getClearColor("int", ChatColor.RED)+" by "+amount+".");
        	}
        	else if (slot - 18 == 4) { // Faith
        		if (RoCPlayers.getFAI(player) <= 0) return;
            	if (e.getClick().isShiftClick()) amount = Math.min(RoCPlayers.getFAI(player), 10);
        		RoCPlayers.setFAI(player, RoCPlayers.getFAI(player)-amount);
            	player.sendMessage(ChatColor.RED+"You reduced your "+ ChatRuleset.getClearColor("fai", ChatColor.RED)+" by "+amount+".");
        	}
        	else
        		return;
    		setStatItems();
    		setStatPointsItems();
        }
    }
}
