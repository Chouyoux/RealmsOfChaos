package io.github.chouyoux.realmsofchaos.ruleset;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;

public class FactionRuleset {

	public static HashMap<String, String> factionChatMsgColors;
	public static HashMap<String, String> factionChatNameColors;
	public static HashMap<String, String> factionChatNickColors;
	public static HashMap<String, org.bukkit.Color> factionArmorColors;
	public static HashMap<String, String> factionHexColors;
	public static HashMap<String, String> factionSkin;
	public static HashMap<String, Material> factionRelics;
	public static ArrayList<String> factionGrades;
	
	public FactionRuleset() {
		factionChatMsgColors = new HashMap<String, String>();
		factionChatMsgColors.put("Greeks", ChatColor.of(new Color(205, 106, 106))+"");
		factionChatMsgColors.put("Persians", ChatColor.of(new Color(106, 182, 205))+"");
		factionChatMsgColors.put("Egyptians", ChatColor.of(new Color(198, 205, 106))+"");
		factionChatMsgColors.put("Chaos", "§d");
		factionChatMsgColors.put("", "");

		factionChatNameColors = new HashMap<String, String>();
		factionChatNameColors.put("Greeks", ChatColor.of(new Color(220, 51, 51))+"");
		factionChatNameColors.put("Persians",ChatColor.of(new Color(51, 198, 220))+"");
		factionChatNameColors.put("Egyptians", ChatColor.of(new Color(209, 220, 51))+"");
		factionChatNameColors.put("Chaos", "§5");
		factionChatNameColors.put("", "");

		factionArmorColors = new HashMap<String, org.bukkit.Color>();
		factionArmorColors.put("Greeks", org.bukkit.Color.fromRGB(205, 106, 106));
		factionArmorColors.put("Persians", org.bukkit.Color.fromRGB(106, 182, 205));
		factionArmorColors.put("Egyptians", org.bukkit.Color.fromRGB(198, 205, 106));
		factionArmorColors.put("Chaos", org.bukkit.Color.PURPLE);
		factionArmorColors.put("", null);

		factionHexColors = new HashMap<String, String>();
		factionHexColors.put("Greeks", "#DC3333");
		factionHexColors.put("Persians", "#6AB6CD");
		factionHexColors.put("Egyptians", "#C6CD6A");
		factionHexColors.put("Chaos", "#7F00FF");
		factionHexColors.put("", "");
		
		factionSkin = new HashMap<String, String>();
		factionSkin.put("Greeks", "P60");
		factionSkin.put("Persians", "Stugace");
		factionSkin.put("Egyptians", "1_NIKoLAS_1");
		factionSkin.put("Chaos", "§5");
		factionSkin.put("", "");
		
		factionGrades = new ArrayList<String>();
		factionGrades.add("Soldier");
		factionGrades.add("Sergeant");
		factionGrades.add("Lieutenant");
		factionGrades.add("Corporal");
		factionGrades.add("Commander");
		factionGrades.add("General");

		factionRelics = new HashMap<String, Material>();
		factionRelics.put("Greeks", Material.REDSTONE_BLOCK);
		factionRelics.put("Persians", Material.LAPIS_BLOCK);
		factionRelics.put("Egyptians", Material.COAL_BLOCK);
		
	}
	
}
