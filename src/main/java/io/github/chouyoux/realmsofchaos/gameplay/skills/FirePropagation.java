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
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.particle_effects.FirePropagationHelixEffect;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class FirePropagation extends Skill {
	public static String skillName = "Fire Propagation";
	public static Material skillMaterial = Material.CAVE_SPIDER_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*1;
	public static int skillCooldown = 20*12;
	public static double skillRadius = 15D;
	public static int skillEffectDuration = 20*6;
	public static int skillEffectValue = 3;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: User summons a fire magic");
		loreList.add("§7totem that shoots Fire trails");
		loreList.add("§7to ennemies in range.");
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
		loreList.add("§aTarget §7: Looking At (Block)");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public FirePropagation(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		visionTargetBlock = true;
		this.cast();
	}

	@Override
	public void onCastTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEffect() {
		FirePropagationHelixEffect effect = new FirePropagationHelixEffect(RealmsOfChaos.effectManager, target_loc);
    	BukkitRunnable end_fire = new  BukkitRunnable() {
	    	@Override
	        public void run() {

	    		
	    		Location source_eye = effect.getLocation().clone().add(0, 4, 0); 
	    		List<LivingEntity> nearby = UtilFuncs.getNearbyEntities3D(effect.getLocation(), FirePropagation.skillRadius);
				for (LivingEntity e : nearby) {
					if (UtilFuncs.collisionsOnVector(source_eye, UtilFuncs.vectorBeetween(source_eye, e.getLocation()))) continue;
					if (RoCNPC.sameFaction(source, e)) continue;
					else if (e instanceof Villager) continue;
		    		Location target_eye = e.getEyeLocation().clone().add(0, -0.25F, 0);
		    		int nb_particle = (int) source_eye.distance(target_eye);
		    		Particles.Trail(source_eye, target_eye, nb_particle, Particle.FLAME);
		    		source.getWorld().playSound(source.getLocation(), Sound.ITEM_FIRECHARGE_USE, 0.45F, 0.5F);
		    		e.setFireTicks(skillEffectDuration);
		    		if (source instanceof Player)
		    			e.damage(skillEffectValue * RoCPlayers.getMagicalDMG((Player) source), source);
		    		else
		    			e.damage(skillEffectValue, source);
				}
	    	}
	    };
	    end_fire.runTaskLater(RealmsOfChaos.instance, 2*20);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}
}
