package io.github.chouyoux.realmsofchaos.entities_handlers;

import static io.github.chouyoux.realmsofchaos.RealmsOfChaos.getInstance;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.memory.Factions;

public class RoCPlayers {
	
	// *** v PLAYER CLASS/FACTION v ***

	public static String getFaction(Player e2) {
		NamespacedKey key = new NamespacedKey(getInstance(), "Faction");
        PersistentDataContainer p_container = e2.getPersistentDataContainer();
        String p_foundvalue = "";
        if(p_container.has(key , PersistentDataType.STRING)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.STRING);
        }
		return p_foundvalue;
	}
	
	public static void setFaction(Player p, String f) {
		String old_faction = getFaction(p);
		if (old_faction != "") Factions.factions.get(old_faction).getPlayersUuids().remove(p.getUniqueId().toString());
		if (f != "") Factions.factions.get(f).getPlayersUuids().add(p.getUniqueId().toString());
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Faction");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.STRING, f);
	}

	public static boolean sameFaction(Player p1, Player p2) {
		return (RoCPlayers.getFaction(p1).compareTo(RoCPlayers.getFaction(p2)) == 0);
	}
	
	public static String getClass(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "Class");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        String p_foundvalue = "";
        if(p_container.has(key , PersistentDataType.STRING)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.STRING);
        }
		return p_foundvalue;
	}
	
	public static void setClass(Player p, String c) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Class");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.STRING, c);
		if (c.compareTo("") == 0) return;
		String f = getFaction(p);

		PlayerInventory inventory = p.getInventory();
		if (c.compareTo("Archer") == 0) {
			if (f.compareTo("Greeks") == 0) {
				inventory.setItemInOffHand(new ItemStack(Material.ARROW, 64));
				inventory.setItem(0, new ItemStack(Material.CROSSBOW));
			}
			else if (f.compareTo("Persians") == 0) {
				inventory.setItemInOffHand(new ItemStack(Material.AIR));
				inventory.setItem(0, new ItemStack(Material.TRIDENT));
			}
			else if(f.compareTo("Egyptians") == 0) {
				inventory.setItemInOffHand(new ItemStack(Material.ARROW, 64));
				inventory.setItem(0, new ItemStack(Material.BOW));
			}
			inventory.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
			inventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			inventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			inventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		}
		else if (c.compareTo("Warrior") == 0) {
			inventory.setItemInOffHand(new ItemStack(Material.SHIELD));
			inventory.setItem(0, new ItemStack(Material.IRON_SWORD));
			inventory.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			inventory.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			inventory.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			inventory.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		}
		else if (c.compareTo("Duelist") == 0) {
			inventory.setItemInOffHand(new ItemStack(Material.AIR));
			inventory.setItem(0, new ItemStack(Material.IRON_AXE));
			inventory.setBoots(new ItemStack(Material.IRON_BOOTS));
			inventory.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			inventory.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			inventory.setHelmet(new ItemStack(Material.IRON_HELMET));
		}
		else if (c.compareTo("Magician") == 0) {
			inventory.setItemInOffHand(new ItemStack(Material.AIR));
			inventory.setItem(0, new ItemStack(Material.STICK));
			inventory.setBoots(new ItemStack(Material.LEATHER_BOOTS));
			inventory.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			inventory.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			inventory.setHelmet(new ItemStack(Material.LEATHER_HELMET));
		}
		else if (c.compareTo("Healer") == 0) {
			inventory.setItemInOffHand(new ItemStack(Material.AIR));
			inventory.setItem(0, new ItemStack(Material.BAMBOO));
			inventory.setBoots(new ItemStack(Material.LEATHER_BOOTS));
			inventory.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			inventory.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			inventory.setHelmet(new ItemStack(Material.LEATHER_HELMET));
		}
		inventory.setItem(1, new ItemStack(Material.APPLE, 5));
		inventory.setItem(2, new ItemStack(Material.BAKED_POTATO, 2));
		inventory.setItem(3, new ItemStack(Material.GOLDEN_APPLE));
		inventory.setItem(4, new ItemStack(Material.CREEPER_BANNER_PATTERN));
		inventory.setItem(5, new ItemStack(Material.FLOWER_BANNER_PATTERN));
		inventory.setItem(6, new ItemStack(Material.MOJANG_BANNER_PATTERN));
		inventory.setItem(7, new ItemStack(Material.GLOBE_BANNER_PATTERN));
		inventory.setItem(8, new ItemStack(Material.SKULL_BANNER_PATTERN));
	}
	
	public static String getRegion(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "Region");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        String p_foundvalue = "";
        if(p_container.has(key , PersistentDataType.STRING)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.STRING);
        }
		return p_foundvalue;
	}
	
	public static void setRegion(Player p, String f) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Region");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.STRING, f);
	}
	
	// *** v STRUCTURE RELATED PLAYER MEMORY v ***
	
	public static double getStructureFood(String name, Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "Food"+name);
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        double p_foundvalue = -1;
        if(p_container.has(key , PersistentDataType.DOUBLE)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.DOUBLE);
        }
		return p_foundvalue;
	}
	
	public static void setStructureFood(String name, Player p, double food) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Food"+name);
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.DOUBLE, food);
	}

	public static double getStructureHorse(String name, Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "Horse"+name);
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        double p_foundvalue = -1;
        if(p_container.has(key , PersistentDataType.DOUBLE)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.DOUBLE);
        }
		return p_foundvalue;
	}

	public static void setStructureHorse(String name, Player p, double horse) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Horse"+name);
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.DOUBLE, horse);
	}
	
	// *** v PRIMARY STATS MEMORY v ***
	
	public static int getSTR(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "STR");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = 0;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}

	public static void setSTR(Player p, int str) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "STR");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, str);
	}
	
	public static int getDEX(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "DEX");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = 0;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}

	public static void setDEX(Player p, int dex) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "DEX");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, dex);
	}
	
	public static int getINT(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "INT");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = 0;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}

	public static void setINT(Player p, int _int) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "INT");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, _int);
	}
	
	public static int getVIT(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "VIT");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = 0;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}

	public static void setVIT(Player p, int vit) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "VIT");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, vit);
	}
	
	public static int getFAI(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "FAI");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = 0;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}

	public static void setFAI(Player p, int fai) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "FAI");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, fai);
	}
	
	// *** v SECONDARY STATS CALCULATION v ***
	
	public static double getPhysicalATK(Player p) {
		return (double) (1 + getSTR(p)*0.005);
	}
	
	public static double getSpeedMalus(Player p) {
		return (double) (1 - getSTR(p)*0.01);
	}
	
	public static double getPhysicalATKSPD(Player p) {
		return (double) (1 + getDEX(p)*0.005);
	}
	
	public static double getCritChance(Player p) {
		return (double) getDEX(p)*0.005;
	}
	
	public static double getMagicalDMG(Player p) {
		return (double) (1 + getINT(p)*0.015);
	}
	
	public static double getMaxHP(Player p) {
		return (double) (1 + getVIT(p)*0.01);
	}
	
	public static double getRegenHP(Player p) {
		return (double) (1 + getVIT(p)*0.01);
	}
	
	public static double getEffectDurationReduction(Player p) {
		return (double) (1 - getVIT(p)*0.005);
	}
	
	public static double getEffectDurationAugmentation(Player p) {
		return (double) (1 + getFAI(p)*0.01);
	}
	
	public static double getHealing(Player p) {
		return (double) (1 + getFAI(p)*0.01);
	}
	
	public static void updateAllAttributes(Player p) {
		double max_health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max_health*getMaxHP(p));
		double attack_spd = p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getDefaultValue();
		p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attack_spd*getPhysicalATKSPD(p));
		double attack = p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getDefaultValue();
		p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attack*getPhysicalATK(p));
	}

}
