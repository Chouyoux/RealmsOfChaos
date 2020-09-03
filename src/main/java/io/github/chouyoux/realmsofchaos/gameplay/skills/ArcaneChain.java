package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class ArcaneChain extends Skill {
	
	public static String skillName = "Arcane Chain";
	public static Material skillMaterial = Material.SHULKER_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20;
	public static int skillCooldown = 20*10;
	public static double skillRadius = 30D;
	public static int skillEffectDuration = 0;
	public static int skillEffectValue = 3;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user shoots a powerful arcane");
		loreList.add("§7trail conducted to 4 other near targets.");
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

	public ArcaneChain(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		Location source_eye = source.getEyeLocation().clone().add(0, -0.25F, 0);
		Location target_eye = targets.get(0).getEyeLocation().clone().add(0, -0.25F, 0);
		int nb_particle = (int) source_eye.distance(target_eye);
		Particles.Trail(source_eye, target_eye, nb_particle, Particle.DRAGON_BREATH);
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.25F, 0.1F);
		targets.get(0).damage(skillEffectValue * RoCPlayers.getMagicalDMG((Player) source), source);
		
		LivingEntity target_source = targets.get(0);
		ArrayList<LivingEntity> targets = new ArrayList<LivingEntity>();
		targets.add(target_source);
		
		for (int i = 1; i < 4; i++) {
			List<LivingEntity> nearby = UtilFuncs.getNearbyEntities3D(target_source.getLocation(), 20);
			double min = Double.MAX_VALUE;
			LivingEntity next_target = null;
			for (LivingEntity e : nearby) {
				if (targets.contains(e)) continue;
				else if (RoCNPC.sameFaction(source, e)) continue;
				else if (e instanceof Villager) continue;
				else if (!(UtilFuncs.canSee(target_source, e))) continue;
				double distance = e.getLocation().distance(target_source.getLocation());
				if (distance < min) {
					min = distance;
					next_target = e;
				}
			}
			if (next_target == null) break;
			targets.add(next_target);
			
			Location source__eye = target_source.getEyeLocation().clone().add(0, -0.25F, 0);
			Location target__eye = next_target.getEyeLocation().clone().add(0, -0.25F, 0);
			int nb__particle = (int) source_eye.distance(target_eye)*2;
			Particles.Trail(source__eye, target__eye, nb__particle, Particle.DRAGON_BREATH);
			source.getWorld().playSound(target_source.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.25F, 0.1F);
			next_target.damage(skillEffectValue * RoCPlayers.getMagicalDMG((Player) source), source);
			
			target_source = next_target;
		}
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}