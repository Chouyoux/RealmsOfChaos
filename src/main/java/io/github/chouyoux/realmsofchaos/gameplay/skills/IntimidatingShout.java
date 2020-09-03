package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class IntimidatingShout extends Skill {

	public static String skillName = "Intimidating Shout";
	public static Material skillMaterial = Material.BLAZE_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 0;
	public static int skillCooldown = 20*16;
	public static double skillRadius = 20D;
	public static int skillEffectDuration = 20*5;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: User shouts really loud");
		loreList.add("§7and intimidates targets in a cone in");
		loreList.add("§7from of him, pushing them away and");
		loreList.add("§7reducing their physical attack damages.");
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
		loreList.add("§aTarget §7: Looking At");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public IntimidatingShout(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		this.cast();
	}

	@Override
	public void onCastTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEffect() {
		double angle = 90D;
		double distance_per_tick = 0.75F;
		Location source_eye = source.getEyeLocation().clone().add(0, -0.5F, 0);
		Particles.Cone(source_eye, skillRadius, angle, distance_per_tick, Particle.END_ROD, false);
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_GHAST_SCREAM, 2F, 0.2F);
		ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) UtilFuncs.getNearbyEntitesAngle(source_eye, skillRadius, angle);
		for (LivingEntity target : targets) {
			if (target instanceof Villager || RoCNPC.sameFaction(target, source)) continue;
			if (Math.abs(target.getLocation().getY() - source.getLocation().getY()) > 10D) continue;
			
			Vector beetween = UtilFuncs.vectorBeetween(source.getLocation(), target.getLocation());
			beetween.normalize();
			beetween.setY(0.28F);
			int duration = skillEffectDuration;
			if (target instanceof Player)
				duration *= RoCPlayers.getEffectDurationReduction(((Player) target));
			target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration, 1));
			target.setVelocity(beetween);
		}
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
