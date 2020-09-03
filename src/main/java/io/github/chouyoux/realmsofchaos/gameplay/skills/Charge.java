package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class Charge extends Skill {
	
	public static String skillName = "Charge";
	public static Material skillMaterial = Material.ZOMBIE_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 0;
	public static int skillCooldown = 20*8;
	public static double skillRadius = 0D;
	public static int skillEffectDuration = 0;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user leaps forward.");
		if (skillEffectValue > 0) {
			String phyOrMag = (skillPhysical) ? "Physical" : "Magical";
			String dmgOrHeal = (skillFriendly) ? "Heal" : phyOrMag+" Damage";
			String perSecOrNot = (skillDelayTicks > 0) ? "§7/"+(double)skillDelayTicks/20+" seconds" : "";
			double dmg = (double)skillEffectValue;
			double dmg_bonus = 0;
			if (skillFriendly)
				dmg_bonus = dmg*(RoCPlayers.getHealing(player)-1);
			else
				if (skillPhysical)
					dmg_bonus = dmg*(RoCPlayers.getPhysicalATK(player)-1);
				else
					dmg_bonus = dmg*(RoCPlayers.getMagicalDMG(player)-1);
			dmg_bonus -= dmg_bonus % 1D;
			loreList.add("§a"+dmgOrHeal+" §7: " + dmg + "§a+" + dmg_bonus + perSecOrNot);
		}
		loreList.add("§aCast Duration §7: Instant");
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

	public Charge(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		isADash = true;
		this.cast();
	}

	@Override
	public void onCastTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEffect() {
		Location location = source.getLocation().clone();
		location.setPitch(0);
		source.setVelocity(location.getDirection().setY(0.14).multiply(3));
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_SLIME_JUMP, 0.5F, 0.5F);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}