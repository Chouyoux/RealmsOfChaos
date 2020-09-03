package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.List;

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

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class SongOfAdrenaline extends Skill {
	
	public static String skillName = "Song Of Adrenaline";
	public static Material skillMaterial = Material.TURTLE_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*3;
	public static int skillCooldown = 20*60;
	public static double skillRadius = 20D;
	public static int skillEffectDuration = 20*30;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = true;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: Increase the target's movement");
		loreList.add("§7speed by one stage.");
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
		loreList.add("§aTarget §7: Allies around (sphere)");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public SongOfAdrenaline(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		List<LivingEntity> nearby = UtilFuncs.getNearbyEntities(source.getLocation(), radius);
		for (LivingEntity e : nearby) {
			if (!(RoCNPC.sameFaction(e, source))) continue;
			if (e instanceof Villager) continue;
			if (UtilFuncs.collisionsOnVector(e.getEyeLocation(), UtilFuncs.vectorBeetween(source.getEyeLocation(), e.getEyeLocation()))) continue;
			
    		PotionEffect effect = e.getPotionEffect(PotionEffectType.SPEED);
    		if (effect != null && (effect.getAmplifier() == 0 || effect.getAmplifier() == 2)) continue;
    		// ^ If effect exists and amplifier is stage 1 or 3 (since Duelist's Adrenaline grants +2)
			
			int nb_particle = (int) source.getLocation().distance(e.getEyeLocation());
			if (!(e.getName().equals(source.getName())))
				Particles.Trail(e.getEyeLocation().clone().add(0, -0.25F, 0), source.getLocation().clone().add(0, -0.25F, 0), nb_particle, Particle.NOTE);

			int duration = skillEffectDuration;
			if (source instanceof Player)
				duration *= RoCPlayers.getEffectDurationAugmentation(((Player) source));
			UtilFuncs.buffEntity(e, PotionEffectType.SPEED, 1, duration);

		    BukkitRunnable note1 = new  BukkitRunnable() {
			    @Override
			    public void run() {
					e.getWorld().playSound(e.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 0.2F, 0.8F);
			    }
			};
		    BukkitRunnable note2 = new  BukkitRunnable() {
			    @Override
			    public void run() {
			    	e.getWorld().playSound(e.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 0.2F, 0.2F);
			    }
			};
		    BukkitRunnable note3 = new  BukkitRunnable() {
			    @Override
			    public void run() {
					e.getWorld().playSound(e.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 0.2F, 0.6F);
			    }
			};
		    BukkitRunnable note4 = new  BukkitRunnable() {
			    @Override
			    public void run() {
			    	e.getWorld().playSound(e.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 0.2F, 0.8F);
			    }
			};
		    BukkitRunnable note5 = new  BukkitRunnable() {
			    @Override
			    public void run() {
					e.getWorld().playSound(e.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 0.2F, 1F);
			    }
			};
			note1.runTaskLater(RealmsOfChaos.getInstance(), 1);
			note2.runTaskLater(RealmsOfChaos.getInstance(), 3);
			note3.runTaskLater(RealmsOfChaos.getInstance(), 5);
			note4.runTaskLater(RealmsOfChaos.getInstance(), 7);
			note5.runTaskLater(RealmsOfChaos.getInstance(), 9);
		}
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
