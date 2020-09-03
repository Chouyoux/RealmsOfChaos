package io.github.chouyoux.realmsofchaos.ruleset;

import java.util.HashMap;

import org.bukkit.ChatColor;

public class ChatRuleset {
	
	public static HashMap<String, String> charCodes;
	
	public static String getClearColor(String key, ChatColor after) {
		return ChatColor.WHITE+charCodes.get(key)+after;
	}
	
	public ChatRuleset() {
		
		charCodes = new HashMap<String, String>();
		
		charCodes.put("persians_greeks", "\u0500");
		charCodes.put("persians_egyptians", "\u0501");
		charCodes.put("greeks_persians", "\u0502");
		charCodes.put("greeks_egyptians", "\u0503");
		charCodes.put("egyptians_greeks", "\u0504");
		charCodes.put("egyptians_persians", "\u0505");
		charCodes.put("kyllene", "\u0506");
		charCodes.put("pateras", "\u0507");
		charCodes.put("marathon", "\u0508");
		charCodes.put("arabic-desert", "\u0509");
		charCodes.put("sinai", "\u050A");
		charCodes.put("fayoum", "\u050B");
		charCodes.put("euphrate", "\u050C");
		charCodes.put("yehoud", "\u050D");
		charCodes.put("parthyene", "\u050E");
		charCodes.put("chaos", "\u0600");
		charCodes.put("str", "\u050F");
		charCodes.put("dex", "\u0510");
		charCodes.put("int", "\u0511");
		charCodes.put("vit", "\u0512");
		charCodes.put("fai", "\u0513");
	}

}
