package io.github.chouyoux.realmsofchaos.GUIs;

import java.util.ArrayList;
import java.util.HashMap;

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

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.quest.Quest;
import io.github.chouyoux.realmsofchaos.quest.QuestRegistry;
import io.github.chouyoux.realmsofchaos.ruleset.GradesRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.MaterialNamesRuleset;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class ScribeGUI implements InventoryHolder, Listener {
    private Inventory inv;
	private RealmsOfChaos main;
	private Player player;
	
	HashMap<ItemStack, Quest> itemsQuests;
	ItemStack itemReward;
	
    public ScribeGUI(Player player) {
		this.main = RealmsOfChaos.getInstance();
		this.player = player;
        main.getServer().getPluginManager().registerEvents(this, main);
        inv = Bukkit.createInventory(this, 9, "Quests");
        itemsQuests = new HashMap<ItemStack, Quest>();
        initializeItems();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
    	int i = 0;
    	itemsQuests.clear();
    	for (Quest quest : QuestRegistry.instance.getFactionQuests(RoCPlayers.getFaction(player))) {
        	ArrayList<String> loreList = new ArrayList<String>();
        	loreList.add(ChatColor.GOLD+quest.objectiveName+" ("+quest.objectiveCount+")");
        	loreList.add(ChatColor.GOLD+"Rewards :");
        	for (ItemStack reward : quest.rewards)
        		loreList.add(ChatColor.GOLD + "" + reward.getAmount() + " " + MaterialNamesRuleset.names.get(reward.getType()));
        	ItemStack questItem = null;
        	if (QuestRegistry.instance.hasReachedMaxQuests(player)) {
            	questItem = UtilFuncs.createGuiItemL(Material.WHEAT_SEEDS, "§c§lMAX QUESTS REACHED", loreList);
        	}
        	else if (quest.hasBeenRewarded(player)) {
            	questItem = UtilFuncs.createGuiItemL(Material.WHEAT_SEEDS, "§c§lQUEST DONE", loreList);
        	}
        	else if (!quest.hasPlayer(player)) {
            	questItem = UtilFuncs.createGuiItemL(Material.FEATHER, "§e§lTAKE QUEST", loreList);
            	itemsQuests.put(questItem, quest);
        	}
        	else if (quest.hasPlayer(player)) {
            	questItem = UtilFuncs.createGuiItemL(Material.WHEAT_SEEDS, "§c§lREMOVE QUEST", loreList);
            	itemsQuests.put(questItem, quest);
        	}
        	inv.setItem(i, questItem);
        	i++;
    		
    	}
    	ArrayList<String> loreList = new ArrayList<String>();
    	itemReward = UtilFuncs.createGuiItemL(Material.GUNPOWDER, "§a§lGET REWARDS", loreList);
    	inv.setItem(8, itemReward);
    }

    // You can open the inventory with this
    public void openInventory() {
    	player.openInventory(inv);
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
        if (!p.equals(player)) return;
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        
        if (itemsQuests.containsKey(clickedItem)) {
        	Quest quest = itemsQuests.get(clickedItem);
        	if (quest.hasPlayer(p))
        		quest.removePlayer(p);
        	else
        		quest.addPlayer(p);
        	initializeItems();
        }
        
        if (clickedItem.equals(itemReward)) {
        	ArrayList<ItemStack> rewards = new ArrayList<ItemStack>();
        	for (Quest quest : itemsQuests.values())
        		if (quest.hasPlayer(p) && quest.isDone(p) && !quest.hasBeenRewarded(p)) {
        			quest.grantReward(p);
        			QuestRegistry.instance.addQuestGiven(p);
        			for (ItemStack reward : quest.rewards) {
        				boolean isIn = false;
        				for (ItemStack reward_in : rewards) {
        					if (reward.getType().equals(reward_in.getType())) {
        						reward_in.add(reward.getAmount());
        						isIn = true;
        					}
        				}
        				if (!isIn)
        					rewards.add(reward);
        			}
        		}
        	if (rewards.size() > 0) {
        		player.closeInventory();
        		new RewardGUI(p, rewards).openInventory();
        		if (player.getLevel() < 6)
	        		for (int i = 0; i < rewards.size(); i++) {
	        			float exp = (float) Math.min(1.0, player.getExp()+(200/GradesRuleset.neededExp.get(player.getLevel()+1)));
	        			if (exp > 1.0)
	        				player.sendMessage(ChatColor.GREEN+"Yay! You've been promoted to the grade of " + GradesRuleset.grades.get(player.getLevel())+".");
	        			player.setExp(exp);
	        		}
        	}
        	else
        		p.sendMessage(ChatColor.RED+"You don't have any quest reward to claim.");
        }
        
        
    }
}