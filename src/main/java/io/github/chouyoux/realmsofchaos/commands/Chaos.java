package io.github.chouyoux.realmsofchaos.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.Mob;
import io.github.chouyoux.realmsofchaos.custom_mobs.MobType;
import io.github.chouyoux.realmsofchaos.particle_effects.ChaosSpawnVortexEffect;
import io.github.chouyoux.realmsofchaos.ruleset.Ruleset;

public class Chaos implements TabExecutor {
	
	private HashMap<String, MobType> availableKeys;
	
	public Chaos() {
		availableKeys = new HashMap<String, MobType>();
		availableKeys.put("Antalock", MobType.ANTALOCK);
		availableKeys.put("Buldavax", MobType.BULDAVAX);
		availableKeys.put("Gurafar", MobType.GURAFAR);
		availableKeys.put("Khrodekai", MobType.KHROKEDAI);
		availableKeys.put("Kredekai", MobType.KREDEKAI);
		availableKeys.put("Skylek", MobType.SKYLEK);
		availableKeys.put("Zaross", MobType.ZAROSS);
		availableKeys.put("Garamin", MobType.GARAMIN);
		availableKeys.put("Elabus", MobType.ELABUS);
		availableKeys.put("Azuthot", MobType.AZUTHOT);
		availableKeys.put("Berocius", MobType.BEROCIUS);
		availableKeys.put("Guron", MobType.GURON);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (args.length > 2 || args.length < 1) return false;
			Player player = (Player) sender;
			
			if (availableKeys.containsKey(args[0])) {
				int value = 1;
				if (args.length == 2) {
					try {
						value = Integer.valueOf(args[1]);
					}
					catch (NumberFormatException e) {
						return false;
					}
				}
				for (int copy_value = value; copy_value > 0; copy_value--) {
					Mob mob = Mob.spawnCustomAI(availableKeys.get(args[0]), player.getTargetBlock(120).getLocation().add(0, 1, 0));
					new ChaosSpawnVortexEffect(RealmsOfChaos.effectManager, mob.getEntity());
				}
				
				player.sendMessage(value + " " + args[0] + "(s) Summoned.");
			}

			else
				return false;
		}
	
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String alias, @NotNull String[] args) {
		
		List<String> arguments = new ArrayList<String>();
		
		if (args.length == 1) {
			for (String mobName : availableKeys.keySet()) {
				arguments.add(mobName);
			}
		}
		
		return arguments;
	}
}