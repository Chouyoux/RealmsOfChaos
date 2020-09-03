package io.github.chouyoux.realmsofchaos.GUIs;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.ruleset.EnchantRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.MaterialNamesRuleset;

public class EnchanterGUI implements InventoryHolder, Listener {
    private Inventory inv;
	private RealmsOfChaos main;
	private boolean deployed = false;
	private Player player;
	
	private int helmetSlot = 4;
	private int chestplateSlot = 13;
	private int leggingsSlot = 22;
	private int bootsSlot = 31;
	//private int weaponSlot = 14;
	
	private ArrayList<Integer> pricesSlots;

    public EnchanterGUI(Player player) {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
        inv = Bukkit.createInventory(this, 45, "Enchanter");
        this.player = player;
        pricesSlots = new ArrayList<Integer>();
        pricesSlots.add(36);
        pricesSlots.add(37);
        pricesSlots.add(38);
        pricesSlots.add(39);
        pricesSlots.add(40);
        pricesSlots.add(41);
        initializeItems();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
    	inv.setItem(helmetSlot, player.getInventory().getHelmet().clone());
    	inv.setItem(chestplateSlot, player.getInventory().getChestplate().clone());
    	inv.setItem(leggingsSlot, player.getInventory().getLeggings().clone());
    	inv.setItem(bootsSlot, player.getInventory().getBoots().clone());
    	//inv.setItem(weaponSlot, player.getInventory().getItemInMainHand().clone());
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
        if (e.getRawSlot() >= 36) return;

        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null) return;
        
        if (deployed) {
        	
        	for (int slot : pricesSlots) {
        		if (inv.getItem(slot) != null)
        			if (!player.getInventory().contains(inv.getItem(slot).getType(), inv.getItem(slot).getAmount())) {
        				player.sendMessage(ChatColor.RED+"You don't have enough materials to enchant this.");
        				inv.clear();
        				initializeItems();
        				deployed = false;
        				return;
        			}
        	}
        	
        	if (clickedItem.getType().toString().toLowerCase().contains("helmet"))
        		player.getInventory().setHelmet(clickedItem);
        	if (clickedItem.getType().toString().toLowerCase().contains("chest"))
        		player.getInventory().setChestplate(clickedItem);
        	if (clickedItem.getType().toString().toLowerCase().contains("legging"))
        		player.getInventory().setLeggings(clickedItem);
        	if (clickedItem.getType().toString().toLowerCase().contains("boot"))
        		player.getInventory().setBoots(clickedItem);
        	
        	for (int slot : pricesSlots) {
        		if (inv.getItem(slot) != null)
        			if (player.getInventory().contains(inv.getItem(slot).getType(), inv.getItem(slot).getAmount())) {
        				ItemStack itemRemove = inv.getItem(slot).clone();
        				player.getInventory().removeItem(itemRemove);
        			}
        	}
			player.sendMessage(ChatColor.GREEN+"Enchantment successfully bought.");
        	
        	
        	player.closeInventory();
        }
        else if (e.getRawSlot() == helmetSlot || e.getRawSlot() == chestplateSlot || e.getRawSlot() == leggingsSlot || e.getRawSlot() == bootsSlot) {
        	int slot = 0;
        	int tier = 0;
        	if (e.getRawSlot() == helmetSlot) {
        		slot = helmetSlot - (Integer) EnchantRuleset.enchants.get(RoCPlayers.getClass(player)).get(EquipmentSlot.HEAD).entrySet().size() / 2;
        		for (Entry<Enchantment, Integer> entry : player.getInventory().getHelmet().getEnchantments().entrySet()) {
        			tier += entry.getValue();
        		}
        		if (tier >= 5) {
        			player.sendMessage(ChatColor.RED+"This piece of equipment can't hold more enchants.");
        			return;
        		}
            	inv.clear();
        		for (Entry<Enchantment, Integer> entry : EnchantRuleset.enchants.get(RoCPlayers.getClass(player)).get(EquipmentSlot.HEAD).entrySet()) {
        			if (player.getInventory().getHelmet().getEnchantmentLevel(entry.getKey()) < entry.getValue()) {
        				ItemStack item = player.getInventory().getHelmet().clone();
        				item.addEnchantment(entry.getKey(), player.getInventory().getHelmet().getEnchantmentLevel(entry.getKey())+1);
        				inv.setItem(slot, item);
        				slot++;
        			}
        		}
        	}
        	if (e.getRawSlot() == chestplateSlot) {
        		slot = chestplateSlot - (Integer) EnchantRuleset.enchants.get(RoCPlayers.getClass(player)).get(EquipmentSlot.CHEST).entrySet().size() / 2;
        		for (Entry<Enchantment, Integer> entry : player.getInventory().getChestplate().getEnchantments().entrySet()) {
        			tier += entry.getValue();
        		}
        		if (tier >= 5) {
        			player.sendMessage(ChatColor.RED+"This piece of equipment can't hold more enchants.");
        			return;
        		}
            	inv.clear();
        		for (Entry<Enchantment, Integer> entry : EnchantRuleset.enchants.get(RoCPlayers.getClass(player)).get(EquipmentSlot.CHEST).entrySet()) {
        			if (player.getInventory().getChestplate().getEnchantmentLevel(entry.getKey()) < entry.getValue()) {
        				ItemStack item = player.getInventory().getChestplate().clone();
        				item.addUnsafeEnchantment(entry.getKey(), player.getInventory().getChestplate().getEnchantmentLevel(entry.getKey())+1);
        				inv.setItem(slot, item);
        				slot++;
        			}
        		}
        	}
        	if (e.getRawSlot() == leggingsSlot) {
        		slot = leggingsSlot - (Integer) EnchantRuleset.enchants.get(RoCPlayers.getClass(player)).get(EquipmentSlot.LEGS).entrySet().size() / 2;
        		for (Entry<Enchantment, Integer> entry : player.getInventory().getLeggings().getEnchantments().entrySet()) {
        			tier += entry.getValue();
        		}
        		if (tier >= 5) {
        			player.sendMessage(ChatColor.RED+"This piece of equipment can't hold more enchants.");
        			return;
        		}
            	inv.clear();
        		for (Entry<Enchantment, Integer> entry : EnchantRuleset.enchants.get(RoCPlayers.getClass(player)).get(EquipmentSlot.LEGS).entrySet()) {
        			if (player.getInventory().getLeggings().getEnchantmentLevel(entry.getKey()) < entry.getValue()) {
        				ItemStack item = player.getInventory().getLeggings().clone();
        				item.addUnsafeEnchantment(entry.getKey(), player.getInventory().getLeggings().getEnchantmentLevel(entry.getKey())+1);
        				inv.setItem(slot, item);
        				slot++;
        			}
        		}
        	}
        	if (e.getRawSlot() == bootsSlot) {
        		slot = bootsSlot - (Integer) EnchantRuleset.enchants.get(RoCPlayers.getClass(player)).get(EquipmentSlot.FEET).entrySet().size() / 2;
        		for (Entry<Enchantment, Integer> entry : player.getInventory().getBoots().getEnchantments().entrySet()) {
        			tier += entry.getValue();
        		}
        		if (tier >= 5) {
        			player.sendMessage(ChatColor.RED+"This piece of equipment can't hold more enchants.");
        			return;
        		}
            	inv.clear();
        		for (Entry<Enchantment, Integer> entry : EnchantRuleset.enchants.get(RoCPlayers.getClass(player)).get(EquipmentSlot.FEET).entrySet()) {
        			if (player.getInventory().getBoots().getEnchantmentLevel(entry.getKey()) < entry.getValue()) {
        				ItemStack item = player.getInventory().getBoots().clone();
        				item.addUnsafeEnchantment(entry.getKey(), player.getInventory().getBoots().getEnchantmentLevel(entry.getKey())+1);
        				inv.setItem(slot, item);
        				slot++;
        			}
        		}
        	}
        	int tier_nb = 0;
        	deployed = true;
        	for (ItemStack tierprice : EnchantRuleset.prices.get(tier)) {
        		if (tierprice != null) {
        			ItemStack s = tierprice.clone();
        			ItemMeta m = s.getItemMeta();
        			m.setDisplayName(MaterialNamesRuleset.names.get(s.getType()));
        			s.setItemMeta(m);
        			inv.setItem(pricesSlots.get(tier_nb), s);
        		}
        		tier_nb++;
        	}
        }
    }
}