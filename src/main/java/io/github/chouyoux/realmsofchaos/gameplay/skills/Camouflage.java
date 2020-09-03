package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class Camouflage extends Skill {
	
	public static String skillName = "Camouflage";
	public static Material skillMaterial = Material.HORSE_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*5;
	public static int skillCooldown = 20*60;
	public static double skillRadius = 0D;
	public static int skillEffectDuration = 0;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = true;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: Makes the user invisible");
		loreList.add("§7for its ennemies until he moves, gets hit,");
		loreList.add("§7shoot a projectile, or attack.");
		if (skillEffectValue > 0) {
			String phyOrMag = (skillPhysical) ? "Physical" : "Magical";
			String dmgOrHeal = (skillFriendly) ? "Heal" : phyOrMag+" Damage";
			String perSecOrNot = (skillDelayTicks > 0) ? "§7/"+(double)skillDelayTicks/20+" seconds" : "";
			int dmg = skillEffectValue;
			int dmg_bonus = 0;
			if (skillFriendly)
				dmg_bonus = (int) (dmg*(RoCPlayers.getHealing(player)-1));
			else
				if (skillPhysical)
					dmg_bonus = (int) (dmg*(RoCPlayers.getPhysicalATK(player)-1));
				else
					dmg_bonus = (int) (dmg*(RoCPlayers.getMagicalDMG(player)-1));
			loreList.add("§a"+dmgOrHeal+" §7: " + dmg + "§a+" + dmg_bonus + perSecOrNot);
		}
		loreList.add("§aCast Duration §7: " + (double)skillCastDuration/20 + " seconds");
		loreList.add("§aCooldown §7: " + (double)skillCooldown/20 + " seconds");
		if (skillEffectDuration > 0) {
			double duration = (double)skillEffectDuration/20;
			double duration_bonus = 0;
			if (skillFriendly) {
				duration_bonus = duration*(RoCPlayers.getEffectDurationAugmentation(player)-1);
				duration_bonus -= duration_bonus % 0.25D;
				loreList.add("§aEffect Duration §7: " + duration + "§a+" + duration_bonus + "§7 seconds");
			}
			else
				loreList.add("§aEffect Duration §7: " + duration + " seconds");
		}
		loreList.add("§aTarget §7: Self");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public Camouflage(LivingEntity source, ArrayList<LivingEntity> targets) {
		super(source, targets);
		name = skillName;
		material = skillMaterial;
		delayTicks = skillDelayTicks;
		castDuration = skillCastDuration;
		cooldown = skillCooldown;
		radius = skillRadius;
		effectDuration = skillEffectDuration;
		effectValue = skillEffectValue;
		friendly = skillFriendly;
		visionTarget = false;
		this.cast();
	}

	@Override
	public void onCastTick() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onEffect() {
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.25F, 0.5F);
		source.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 99));
		PersistentDataContainer container = source.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Camouflage");
		container.set(key, PersistentDataType.INTEGER, 1);
		
		Collection<? extends Player> online_players = Bukkit.getServer().getOnlinePlayers();
		for (Object p : online_players.toArray())
			if (p instanceof Player && !RoCPlayers.sameFaction((Player) p, (Player) source)) {
				((Player) p).hidePlayer(RealmsOfChaos.instance, (Player) source);
			}
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
	}

}
