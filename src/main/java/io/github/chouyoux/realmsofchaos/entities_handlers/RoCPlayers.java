package io.github.chouyoux.realmsofchaos.entities_handlers;

import static io.github.chouyoux.realmsofchaos.RealmsOfChaos.getInstance;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.gameplay.skills.Arcane;
import io.github.chouyoux.realmsofchaos.gameplay.skills.LifeGrasp;
import io.github.chouyoux.realmsofchaos.gameplay.skills.Skill;
import io.github.chouyoux.realmsofchaos.memory.Factions;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import io.github.chouyoux.realmsofchaos.util.ArmorColors;
import io.github.chouyoux.realmsofchaos.util.Banners;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class RoCPlayers {
	
	// *** v PLAYER CLASS/FACTION v *** //

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
		
		if (getClass(p).equals("Warrior"))
			Banners.updateShield(p.getInventory().getItemInOffHand(), f);
		
		if (getClass(p).equals("Healer") || getClass(p).equals("Magician"))
			ArmorColors.updateArmorColor(p, f);
		
		PlayerInventory inventory = p.getInventory();
		inventory.setItem(1, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		inventory.setItem(2, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		inventory.setItem(3, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		inventory.setItem(4, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		inventory.setItem(5, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		
		ArmorColors.updateInventoryFaction(p);
		
		setSkin(p, FactionRuleset.factionSkin.get(getFaction(p)));
		setPrefix(p, FactionRuleset.factionHexColors.get(getFaction(p)));
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
				inventory.setItemInOffHand(new ItemStack(Material.ARROW, 1));
				ItemStack crossbow = new ItemStack(Material.CROSSBOW);
				ItemMeta crossbowMeta = crossbow.getItemMeta();
				crossbowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
				crossbow.setItemMeta(crossbowMeta);
				inventory.setItem(0, UtilFuncs.setUnbreakable(crossbow));
				
			}
			else if (f.compareTo("Persians") == 0) {
				inventory.setItemInOffHand(new ItemStack(Material.AIR));
				ItemStack item = new ItemStack(Material.TRIDENT);
				inventory.setItem(0, UtilFuncs.setUnbreakable(item));
			}
			else if(f.compareTo("Egyptians") == 0) {
				inventory.setItemInOffHand(new ItemStack(Material.ARROW, 1));
				ItemStack bow = new ItemStack(Material.BOW);
				ItemMeta bowMeta = bow.getItemMeta();
				bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
				bow.setItemMeta(bowMeta);
				inventory.setItem(0, UtilFuncs.setUnbreakable(bow));
			}
			inventory.setBoots(UtilFuncs.setUnbreakable(new ItemStack(Material.GOLDEN_BOOTS)));
			inventory.setLeggings(UtilFuncs.setUnbreakable(new ItemStack(Material.GOLDEN_LEGGINGS)));
			inventory.setChestplate(UtilFuncs.setUnbreakable(new ItemStack(Material.GOLDEN_CHESTPLATE)));
			inventory.setHelmet(UtilFuncs.setUnbreakable(new ItemStack(Material.GOLDEN_HELMET)));
		}
		else if (c.compareTo("Warrior") == 0) {
			ItemStack shield = new ItemStack(Material.SHIELD);
			Banners.updateShield(shield, f);
			inventory.setItemInOffHand(UtilFuncs.setUnbreakable(shield));
			inventory.setItem(0, UtilFuncs.setUnbreakable(new ItemStack(Material.STONE_SWORD)));
			inventory.setBoots(UtilFuncs.setUnbreakable(new ItemStack(Material.DIAMOND_BOOTS)));
			inventory.setLeggings(UtilFuncs.setUnbreakable(new ItemStack(Material.DIAMOND_LEGGINGS)));
			inventory.setChestplate(UtilFuncs.setUnbreakable(new ItemStack(Material.DIAMOND_CHESTPLATE)));
			inventory.setHelmet(UtilFuncs.setUnbreakable(new ItemStack(Material.DIAMOND_HELMET)));
		}
		else if (c.compareTo("Duelist") == 0) {
			inventory.setItemInOffHand(new ItemStack(Material.AIR));
			inventory.setItem(0, UtilFuncs.setUnbreakable(new ItemStack(Material.STONE_AXE)));
			inventory.setBoots(UtilFuncs.setUnbreakable(new ItemStack(Material.IRON_BOOTS)));
			inventory.setLeggings(UtilFuncs.setUnbreakable(new ItemStack(Material.IRON_LEGGINGS)));
			inventory.setChestplate(UtilFuncs.setUnbreakable(new ItemStack(Material.IRON_CHESTPLATE)));
			inventory.setHelmet(UtilFuncs.setUnbreakable(new ItemStack(Material.IRON_HELMET)));
		}
		else if (c.compareTo("Magician") == 0) {
			inventory.setItemInOffHand(new ItemStack(Material.AIR));
			inventory.setItem(0, Arcane.getItem(p));
			inventory.setBoots(UtilFuncs.setUnbreakable(new ItemStack(Material.LEATHER_BOOTS)));
			inventory.setLeggings(UtilFuncs.setUnbreakable(new ItemStack(Material.LEATHER_LEGGINGS)));
			inventory.setChestplate(UtilFuncs.setUnbreakable(new ItemStack(Material.LEATHER_CHESTPLATE)));
			inventory.setHelmet(UtilFuncs.setUnbreakable(new ItemStack(Material.LEATHER_HELMET)));
			ArmorColors.updateArmorColor(p, f);
		}
		else if (c.compareTo("Healer") == 0) {
			inventory.setItemInOffHand(new ItemStack(Material.AIR));
			inventory.setItem(0, LifeGrasp.getItem(p));
			inventory.setBoots(UtilFuncs.setUnbreakable(new ItemStack(Material.LEATHER_BOOTS)));
			inventory.setLeggings(UtilFuncs.setUnbreakable(new ItemStack(Material.LEATHER_LEGGINGS)));
			inventory.setChestplate(UtilFuncs.setUnbreakable(new ItemStack(Material.LEATHER_CHESTPLATE)));
			inventory.setHelmet(UtilFuncs.setUnbreakable(new ItemStack(Material.LEATHER_HELMET)));
			ArmorColors.updateArmorColor(p, f);
		}
		else if (c.equals("Builder")) {
			inventory.clear();
			inventory.setItem(0, new ItemStack(Material.WOODEN_AXE));
			inventory.setItem(1, new ItemStack(Material.WOODEN_SWORD));
			inventory.setItem(6, new ItemStack(Material.ARROW));
			inventory.setItem(7, new ItemStack(Material.GUNPOWDER));
			inventory.setItem(8, new ItemStack(Material.FIREWORK_ROCKET));
			inventory.setChestplate(UtilFuncs.setUnbreakable(new ItemStack(Material.ELYTRA)));
			p.setGameMode(GameMode.CREATIVE);
			p.sendMessage("Have fun buddy :)");
			return;
		}
		p.setGameMode(GameMode.SURVIVAL);
		inventory.setItem(1, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		inventory.setItem(2, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		inventory.setItem(3, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		inventory.setItem(4, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		inventory.setItem(5, UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null));
		inventory.setItem(6, new ItemStack(Material.APPLE, 5));
		inventory.setItem(7, new ItemStack(Material.BAKED_POTATO, 2));
		inventory.setItem(8, UtilFuncs.setUnbreakable(new ItemStack(Material.DIAMOND_PICKAXE)));

		if (getClass(p).equals("Healer") || getClass(p).equals("Magician"))
			ArmorColors.updateArmorColor(p, f);
		ArmorColors.updateInventoryFaction(p);
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
	
	// *** v PLAYER ECONOMY RELATED v *** //
	
	public static int getCoins(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "Coins");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = -1;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}
	
	public static void setCoins(Player p, int coins) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Coins");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, coins);
	}
	
	// *** v SPELL CASTING RELATED v *** //
	
	public static int getIsCasting(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "isCasting");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = -1;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}
	
	public static void setIsCasting(Player p, int casting) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "isCasting");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, casting);
	}
	
	// *** v COMBAT MEMORY RELATED v *** //

	public static int getCombatTime(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "CombatTimer");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = 0;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}
	
	public static void setCombatTime(Player p, int seconds) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "CombatTimer");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, seconds);
	}
	
	// *** v STRUCTURE RELATED PLAYER MEMORY v *** //
	
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
	


	public static double getStructureTempleBuff(String name, Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "TempleBuff"+name);
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        double p_foundvalue = 1;
        if(p_container.has(key , PersistentDataType.DOUBLE)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.DOUBLE);
        }
		return p_foundvalue;
	}

	public static void setStructureTempleBuff(String name, Player p, double buff) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "TempleBuff"+name);
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.DOUBLE, buff);
	}
	
	// *** v PRIMARY STATS MEMORY v *** //
	
	public static int getStatPoints(Player p) {
		NamespacedKey key = new NamespacedKey(getInstance(), "StatPoints");
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = 0;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}

	public static void setStatPoints(Player p, int stats) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "StatPoints");
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, stats);
		updateAllAttributes(p);
	}
	
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
		updateAllAttributes(p);
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
		updateAllAttributes(p);
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
		updateAllAttributes(p);
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
		updateAllAttributes(p);
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
		updateAllAttributes(p);
	}
	
	public static int getStat(Player p, String statKey) {
		NamespacedKey key = new NamespacedKey(getInstance(), statKey);
        PersistentDataContainer p_container = p.getPersistentDataContainer();
        int p_foundvalue = 0;
        if(p_container.has(key , PersistentDataType.INTEGER)) {
        	p_foundvalue = p_container.get(key, PersistentDataType.INTEGER);
        }
		return p_foundvalue;
	}
	
	public static void setStat(Player p, int stat, String statKey) {
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), statKey);
		PersistentDataContainer container = p.getPersistentDataContainer();
		container.set(key, PersistentDataType.INTEGER, stat);
		updateAllAttributes(p);
	}
	
	public static String getMaxStat(Player p) {
		int str = getSTR(p);
		int vit = getVIT(p);
		int dex = getDEX(p);
		int fai = getFAI(p);
		int iint = getINT(p);
		int max = Math.max(iint, Math.max(Math.max(dex, fai), Math.max(str, vit)));
		if (max == str) return "STR";
		else if (max == vit) return "VIT";
		else if (max == dex) return "DEX";
		else if (max == fai) return "FAI";
		else if (max == iint) return "INT";
		return "";
	}
	
	public static String getMinStat(Player p) {
		int str = getSTR(p);
		int vit = getVIT(p);
		int dex = getDEX(p);
		int fai = getFAI(p);
		int iint = getINT(p);
		int min = Math.min(iint, Math.min(Math.min(dex, fai), Math.min(str, vit)));
		if (min == str) return "STR";
		else if (min == vit) return "VIT";
		else if (min == dex) return "DEX";
		else if (min == fai) return "FAI";
		else if (min == iint) return "INT";
		return "";
	}
	
	public static int getStatsSum(Player p) {
		return getSTR(p)+getINT(p)+getVIT(p)+getDEX(p)+getFAI(p);
	}
	
	// *** v SECONDARY STATS CALCULATION v *** //
	
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
		return (double) (1 + getINT(p)*0.01);
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
	
	public static void setPrefix(Player p, String prefix) {
		String command1 = "tab playeruuid "+p.getName()+" tabprefix " + prefix;
		String command2 = "tab playeruuid "+p.getName()+" tagprefix " + prefix;
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command1);
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command2);
	}
	
	public static void setTitle(Player p, String title) {
		String command = "tab playeruuid "+p.getName()+" belowname " + title;
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
	}
	
	public static void setGrade(Player p, String grade) {
		String command = "tab playeruuid "+p.getName()+" abovename " + grade;
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
	}
	
	public static void setSkin(Player p, String skin) {
		String command = "skin to "+p.getName()+" from "+skin;
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
	}
	
	public static void updateAllAttributes(Player p) {
		double max_health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max_health*getMaxHP(p));
		double attack_spd = p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getDefaultValue();
		p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attack_spd*getPhysicalATKSPD(p));
		double attack = p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getDefaultValue();
		p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attack*getPhysicalATK(p));
		
		updateAllItem(p);
	}
	
	public static void updateAllItem(Player p) {
		if (RoCPlayers.getClass(p).equals("Builder")) return;
		if (RoCPlayers.getClass(p).equals("")) return;
		PlayerInventory inv = p.getInventory();
		if (Skill.MaterialToItem(inv.getItem(0).getType(), p) != null && inv.getItem(0).getType() != Material.AIR)
			if (RoCPlayers.getClass(p).equals("Magician") || RoCPlayers.getClass(p).equals("Healer"))
				inv.setItem(0, Skill.MaterialToItem(inv.getItem(0).getType(), p));
		for (int i = 1; i < 6; i++)
			if (Skill.MaterialToItem(inv.getItem(i).getType(), p) != null && inv.getItem(i).getType() != Material.AIR)
				inv.setItem(i, Skill.MaterialToItem(inv.getItem(i).getType(), p));
			
	}
	
	public static int getNbItem(Player p, Material mat) {
		int amount = 0;
		for (ItemStack item : p.getInventory().getContents())
			if (item != null && item.getType() != null && item.getType().equals(mat)) amount += item.getAmount();
		return amount;
	}
}
