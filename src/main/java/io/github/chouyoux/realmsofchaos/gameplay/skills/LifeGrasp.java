package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class LifeGrasp extends Skill {
	
	public static String skillName = "Life Grasp";
	public static Material skillMaterial = Material.BAMBOO;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 8;
	public static int skillCooldown = 15;
	public static double skillRadius = 30D;
	public static int skillEffectDuration = 0;
	public static int skillEffectValue = 2;
	public static boolean skillFriendly = true;
	public static boolean skillNeutral = true;
	public static boolean skillPhysical = false;
	public static boolean skillIsHeal = true;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user shoots a life grasp trail");
		loreList.add("§7towards the target, healing allies or damaging");
		loreList.add("§7ennemies.");
		if (skillEffectValue > 0) {
			String phyOrMag = (skillPhysical) ? "Physical" : "Magical";
			String dmgOrHeal = "Heal/"+phyOrMag+" Damage";
			if (!skillNeutral)
				dmgOrHeal = (skillFriendly) ? "Heal" : phyOrMag+" Damage";
			int dmg = skillEffectValue;
			int dmg_bonus = 0;
			int heal_bonus = 0;
			heal_bonus = (int) (dmg*(RoCPlayers.getHealing(player)-1));
			if (skillPhysical)
				dmg_bonus = (int) (dmg*(RoCPlayers.getPhysicalATK(player)-1));
			else
				dmg_bonus = (int) (dmg*(RoCPlayers.getMagicalDMG(player)-1));
			loreList.add("§a"+dmgOrHeal+" §7: " + dmg + "§a+" + heal_bonus + "§7/" + dmg + "§a+" + dmg_bonus);
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

	public LifeGrasp(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		neutral = skillNeutral;
		isHeal = skillIsHeal;
		visionTarget = true;
		this.cast();
	}

	@Override
	public void onCastTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEffect() {
		Location source_eye = source.getEyeLocation().clone().add(0, -0.25F, 0);
		Location target_eye = targets.get(0).getEyeLocation().clone().add(0, -0.25F, 0);
		int nb_particle = (int) source_eye.distance(target_eye)*2;
		
		if (RoCNPC.sameFaction(targets.get(0), source)) {
			Particles.Trail(source_eye, target_eye, nb_particle, Particle.VILLAGER_HAPPY);
			source.getWorld().playSound(source.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 0.2F, 0.8F);
			if (targets.get(0).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() != targets.get(0).getHealth()) {
				targets.get(0).setHealth(Math.min(targets.get(0).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue(), targets.get(0).getHealth()+(skillEffectValue*RoCPlayers.getHealing((Player) source))));
				source.getWorld().spawnParticle(Particle.HEART, targets.get(0).getLocation(), 2, 0, 1.5, 1, 0.5);
			}
		}
		else {
			Particles.Trail(source_eye, target_eye, nb_particle, Particle.SPELL_WITCH);
			source.getWorld().playSound(source.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.2F, 0.1F);
			targets.get(0).damage(skillEffectValue * RoCPlayers.getMagicalDMG((Player) source), source);
		}
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
