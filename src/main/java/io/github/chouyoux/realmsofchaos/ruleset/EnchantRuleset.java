package io.github.chouyoux.realmsofchaos.ruleset;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;



public class EnchantRuleset {
	
	public static HashMap<String, HashMap<EquipmentSlot, HashMap<Enchantment, Integer>>> enchants;
	public static HashMap<Integer, ArrayList<ItemStack>> prices;
	public static ArrayList<Material> materials;

	
	public EnchantRuleset() {
		
		enchants = new HashMap<String, HashMap<EquipmentSlot, HashMap<Enchantment, Integer>>>();
		
		enchants.put("Warrior", new HashMap<EquipmentSlot, HashMap<Enchantment, Integer>>());
		enchants.put("Duelist", new HashMap<EquipmentSlot, HashMap<Enchantment, Integer>>());
		enchants.put("Archer", new HashMap<EquipmentSlot, HashMap<Enchantment, Integer>>());
		enchants.put("Healer", new HashMap<EquipmentSlot, HashMap<Enchantment, Integer>>());
		enchants.put("Magician", new HashMap<EquipmentSlot, HashMap<Enchantment, Integer>>());
		
		enchants.get("Warrior").put(EquipmentSlot.HEAD, new HashMap<Enchantment, Integer>());
		enchants.get("Warrior").put(EquipmentSlot.CHEST, new HashMap<Enchantment, Integer>());
		enchants.get("Warrior").put(EquipmentSlot.LEGS, new HashMap<Enchantment, Integer>());
		enchants.get("Warrior").put(EquipmentSlot.FEET, new HashMap<Enchantment, Integer>());
		
		enchants.get("Duelist").put(EquipmentSlot.HEAD, new HashMap<Enchantment, Integer>());
		enchants.get("Duelist").put(EquipmentSlot.CHEST, new HashMap<Enchantment, Integer>());
		enchants.get("Duelist").put(EquipmentSlot.LEGS, new HashMap<Enchantment, Integer>());
		enchants.get("Duelist").put(EquipmentSlot.FEET, new HashMap<Enchantment, Integer>());
		
		enchants.get("Archer").put(EquipmentSlot.HEAD, new HashMap<Enchantment, Integer>());
		enchants.get("Archer").put(EquipmentSlot.CHEST, new HashMap<Enchantment, Integer>());
		enchants.get("Archer").put(EquipmentSlot.LEGS, new HashMap<Enchantment, Integer>());
		enchants.get("Archer").put(EquipmentSlot.FEET, new HashMap<Enchantment, Integer>());
		
		enchants.get("Healer").put(EquipmentSlot.HEAD, new HashMap<Enchantment, Integer>());
		enchants.get("Healer").put(EquipmentSlot.CHEST, new HashMap<Enchantment, Integer>());
		enchants.get("Healer").put(EquipmentSlot.LEGS, new HashMap<Enchantment, Integer>());
		enchants.get("Healer").put(EquipmentSlot.FEET, new HashMap<Enchantment, Integer>());
		
		enchants.get("Magician").put(EquipmentSlot.HEAD, new HashMap<Enchantment, Integer>());
		enchants.get("Magician").put(EquipmentSlot.CHEST, new HashMap<Enchantment, Integer>());
		enchants.get("Magician").put(EquipmentSlot.LEGS, new HashMap<Enchantment, Integer>());
		enchants.get("Magician").put(EquipmentSlot.FEET, new HashMap<Enchantment, Integer>());
		
		enchants.get("Warrior").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Warrior").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Warrior").get(EquipmentSlot.HEAD).put(Enchantment.THORNS, 3);
		enchants.get("Warrior").get(EquipmentSlot.HEAD).put(Enchantment.OXYGEN, 1);
		enchants.get("Warrior").get(EquipmentSlot.HEAD).put(Enchantment.WATER_WORKER, 1);
		
		enchants.get("Warrior").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Warrior").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Warrior").get(EquipmentSlot.CHEST).put(Enchantment.THORNS, 3);
		
		enchants.get("Warrior").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Warrior").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Warrior").get(EquipmentSlot.LEGS).put(Enchantment.THORNS, 3);
		
		enchants.get("Warrior").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Warrior").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Warrior").get(EquipmentSlot.FEET).put(Enchantment.THORNS, 3);
		enchants.get("Warrior").get(EquipmentSlot.FEET).put(Enchantment.DEPTH_STRIDER, 2);
		enchants.get("Warrior").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_FALL, 2);
		
		
		
		enchants.get("Duelist").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Duelist").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Duelist").get(EquipmentSlot.HEAD).put(Enchantment.THORNS, 3);
		enchants.get("Duelist").get(EquipmentSlot.HEAD).put(Enchantment.OXYGEN, 1);
		enchants.get("Duelist").get(EquipmentSlot.HEAD).put(Enchantment.WATER_WORKER, 1);
		
		enchants.get("Duelist").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Duelist").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Duelist").get(EquipmentSlot.CHEST).put(Enchantment.THORNS, 3);
		
		enchants.get("Duelist").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Duelist").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Duelist").get(EquipmentSlot.LEGS).put(Enchantment.THORNS, 3);
		
		enchants.get("Duelist").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Duelist").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Duelist").get(EquipmentSlot.FEET).put(Enchantment.THORNS, 3);
		enchants.get("Duelist").get(EquipmentSlot.FEET).put(Enchantment.DEPTH_STRIDER, 2);
		enchants.get("Duelist").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_FALL, 2);
		
		
		
		enchants.get("Archer").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Archer").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Archer").get(EquipmentSlot.HEAD).put(Enchantment.THORNS, 3);
		enchants.get("Archer").get(EquipmentSlot.HEAD).put(Enchantment.OXYGEN, 1);
		enchants.get("Archer").get(EquipmentSlot.HEAD).put(Enchantment.WATER_WORKER, 1);
		
		enchants.get("Archer").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Archer").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Archer").get(EquipmentSlot.CHEST).put(Enchantment.THORNS, 3);
		
		enchants.get("Archer").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Archer").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Archer").get(EquipmentSlot.LEGS).put(Enchantment.THORNS, 3);
		
		enchants.get("Archer").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Archer").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Archer").get(EquipmentSlot.FEET).put(Enchantment.THORNS, 3);
		enchants.get("Archer").get(EquipmentSlot.FEET).put(Enchantment.DEPTH_STRIDER, 2);
		enchants.get("Archer").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_FALL, 2);
		
		
		
		enchants.get("Healer").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Healer").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Healer").get(EquipmentSlot.HEAD).put(Enchantment.THORNS, 3);
		enchants.get("Healer").get(EquipmentSlot.HEAD).put(Enchantment.OXYGEN, 1);
		enchants.get("Healer").get(EquipmentSlot.HEAD).put(Enchantment.WATER_WORKER, 1);
		
		enchants.get("Healer").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Healer").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Healer").get(EquipmentSlot.CHEST).put(Enchantment.THORNS, 3);
		
		enchants.get("Healer").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Healer").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Healer").get(EquipmentSlot.LEGS).put(Enchantment.THORNS, 3);
		
		enchants.get("Healer").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Healer").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Healer").get(EquipmentSlot.FEET).put(Enchantment.THORNS, 3);
		enchants.get("Healer").get(EquipmentSlot.FEET).put(Enchantment.DEPTH_STRIDER, 2);
		enchants.get("Healer").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_FALL, 2);
		
		
		
		enchants.get("Magician").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Magician").get(EquipmentSlot.HEAD).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Magician").get(EquipmentSlot.HEAD).put(Enchantment.THORNS, 3);
		enchants.get("Magician").get(EquipmentSlot.HEAD).put(Enchantment.OXYGEN, 1);
		enchants.get("Magician").get(EquipmentSlot.HEAD).put(Enchantment.WATER_WORKER, 1);
		
		enchants.get("Magician").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Magician").get(EquipmentSlot.CHEST).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Magician").get(EquipmentSlot.CHEST).put(Enchantment.THORNS, 3);
		
		enchants.get("Magician").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Magician").get(EquipmentSlot.LEGS).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Magician").get(EquipmentSlot.LEGS).put(Enchantment.THORNS, 3);
		
		enchants.get("Magician").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		enchants.get("Magician").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_PROJECTILE, 3);
		enchants.get("Magician").get(EquipmentSlot.FEET).put(Enchantment.THORNS, 3);
		enchants.get("Magician").get(EquipmentSlot.FEET).put(Enchantment.DEPTH_STRIDER, 2);
		enchants.get("Magician").get(EquipmentSlot.FEET).put(Enchantment.PROTECTION_FALL, 2);
		
		materials = new ArrayList<Material>();
		materials.add(Material.GLOWSTONE_DUST);
		materials.add(Material.PRISMARINE_SHARD);
		materials.add(Material.PRISMARINE_CRYSTALS);
		materials.add(Material.QUARTZ);
		materials.add(Material.DIAMOND);
		materials.add(Material.EMERALD);
		
		prices = new HashMap<Integer, ArrayList<ItemStack>>();
		
		prices.put(0, new ArrayList<ItemStack>());
		prices.get(0).add(new ItemStack(materials.get(0), 10));
		prices.get(0).add(null);
		prices.get(0).add(null);
		prices.get(0).add(null);
		prices.get(0).add(null);
		prices.get(0).add(new ItemStack(materials.get(5), 1));
		
		prices.put(1, new ArrayList<ItemStack>());
		prices.get(1).add(new ItemStack(materials.get(0), 20));
		prices.get(1).add(new ItemStack(materials.get(1), 10));
		prices.get(1).add(null);
		prices.get(1).add(null);
		prices.get(1).add(null);
		prices.get(1).add(new ItemStack(materials.get(5), 2));
		
		prices.put(2, new ArrayList<ItemStack>());
		prices.get(2).add(new ItemStack(materials.get(0), 30));
		prices.get(2).add(new ItemStack(materials.get(1), 20));
		prices.get(2).add(new ItemStack(materials.get(2), 10));
		prices.get(2).add(null);
		prices.get(2).add(null);
		prices.get(2).add(new ItemStack(materials.get(5), 3));
		
		prices.put(3, new ArrayList<ItemStack>());
		prices.get(3).add(new ItemStack(materials.get(0), 40));
		prices.get(3).add(new ItemStack(materials.get(1), 30));
		prices.get(3).add(new ItemStack(materials.get(2), 20));
		prices.get(3).add(new ItemStack(materials.get(3), 10));
		prices.get(3).add(null);
		prices.get(3).add(new ItemStack(materials.get(5), 4));
		
		prices.put(4, new ArrayList<ItemStack>());
		prices.get(4).add(new ItemStack(materials.get(0), 50));
		prices.get(4).add(new ItemStack(materials.get(1), 40));
		prices.get(4).add(new ItemStack(materials.get(2), 30));
		prices.get(4).add(new ItemStack(materials.get(3), 20));
		prices.get(4).add(new ItemStack(materials.get(4), 10));
		prices.get(4).add(new ItemStack(materials.get(5), 5));
		
		// Statistics up
		prices.put(5, new ArrayList<ItemStack>());
		prices.get(5).add(new ItemStack(materials.get(5), 1));
		
	}
	
}
