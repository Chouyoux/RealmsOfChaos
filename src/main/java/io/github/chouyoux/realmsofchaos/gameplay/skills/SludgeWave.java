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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class SludgeWave extends Skill {

	public static String skillName = "Sludge Wave";
	public static Material skillMaterial = Material.MULE_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 30;
	public static int skillCooldown = 20*16;
	public static double skillRadius = 25D;
	public static int skillEffectDuration = 20*5;
	public static int skillEffectValue = 3;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: User sends a wave of");
		loreList.add("§7water magic in a cone in front");
		loreList.add("§7of him, pushing enemies away and");
		loreList.add("§7slowing them.");
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

	public SludgeWave(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		double angle = 90D;
		double distance_per_tick = 0.75F;
		Location source_eye = source.getEyeLocation();
		Particles.WavingCone(source_eye, skillRadius, angle, distance_per_tick, Particle.WATER_WAKE, 1, true);
		Particles.WavingCone(source_eye, skillRadius, angle, distance_per_tick, Particle.WATER_BUBBLE, 1, true);
		source.getWorld().playSound(source.getLocation(), Sound.AMBIENT_UNDERWATER_ENTER, 0.25F, 0.1F);
		ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) UtilFuncs.getNearbyEntitesAngle(source_eye, skillRadius, angle);
		for (LivingEntity target : targets) {
			if (target instanceof Villager || RoCNPC.sameFaction(target, source)) continue;
			if (Math.abs(target.getLocation().getY() - source.getLocation().getY()) > 10D) continue;
			double delay = source.getLocation().distance(target.getLocation()) / 0.75F;
			int ticks_delay = (int) delay;
			
			Vector beetween = UtilFuncs.vectorBeetween(source.getLocation(), target.getLocation());
			beetween.normalize();
			beetween.setY(0.28F);
			
	    	BukkitRunnable effect = new BukkitRunnable() {
		    	@Override
		        public void run() {
					int duration = skillEffectDuration;
					if (target instanceof Player)
						duration *= RoCPlayers.getEffectDurationReduction(((Player) target));
					target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 1));
					target.damage(skillEffectValue * RoCPlayers.getMagicalDMG((Player) source));
					target.setVelocity(beetween);
		    	}
		    };
	    	effect.runTaskLater(RealmsOfChaos.instance, ticks_delay);
		}
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}
	
}
