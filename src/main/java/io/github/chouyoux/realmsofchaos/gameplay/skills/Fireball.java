package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class Fireball extends Skill {
	
	public static String skillName = "Fireball";
	public static Material skillMaterial = Material.SPIDER_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*1;
	public static int skillCooldown = 20*2;
	public static double skillRadius = 30D;
	public static int skillEffectDuration = 20*3;
	public static int skillEffectValue = 4;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user shoots a fireball");
		loreList.add("§7towards the target, burning it.");
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
		loreList.add("§aTarget §7: Looking At");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public Fireball(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		visionTarget = true;
		this.cast();
	}

	@Override
	public void onCastTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEffect() {
		Location source_eye = source.getEyeLocation();
		Location target_eye = targets.get(0).getEyeLocation();
		Vector direction = target_eye.toVector().subtract(source_eye.toVector()).normalize();
		org.bukkit.entity.Fireball fire = source.launchProjectile(org.bukkit.entity.Fireball.class, direction.multiply(1.2));
		PersistentDataContainer container = fire.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "FireBall");
		container.set(key, PersistentDataType.INTEGER, 1);
		fire.setShooter(source);
		source.getWorld().playSound(source.getLocation(), Sound.ITEM_FIRECHARGE_USE, 0.25F, 0.5F);
		
		BukkitRunnable effect = new BukkitRunnable() {
	    	@Override
	        public void run() {
		    	if (fire.isDead())
		    		this.cancel();
		    	else {
		    		Location new_target_eye = targets.get(0).getEyeLocation();
		    		Vector new_direction = new_target_eye.toVector().subtract(fire.getLocation().toVector()).normalize();
		    		fire.setDirection(new_direction);
		    	}
	    	}
	    };
	    effect.runTaskTimer(RealmsOfChaos.getInstance(), 0, 1);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
