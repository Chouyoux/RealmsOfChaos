package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class AttackOrder extends Skill {
	
	public static String skillName = "Attack Order";
	public static Material skillMaterial = Material.WOLF_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 10;
	public static int skillCooldown = 20*20;
	public static double skillRadius = 30D;
	public static int skillEffectDuration = 20*8;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user summons three angry");
		loreList.add("§7wolves rushing the target, attacking it.");
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

	public AttackOrder(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		ArrayList<Wolf> wolves = new ArrayList<Wolf>();
		wolves.add((Wolf) source.getWorld().spawnEntity(source.getLocation(), EntityType.WOLF));
		wolves.add((Wolf) source.getWorld().spawnEntity(source.getLocation(), EntityType.WOLF));
		wolves.add((Wolf) source.getWorld().spawnEntity(source.getLocation(), EntityType.WOLF));
		for (Wolf wolf : wolves) {
	        RoCNPC.setFaction(wolf, RoCNPC.getFaction(source));
			double move_speed = wolf.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
			double attack = wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();
			wolf.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(move_speed*1.5);
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attack/2);
		}
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_WOLF_GROWL, 0.25F, 0.5F);
		
		BukkitRunnable firstBark = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_WOLF_AMBIENT, 0.25F, 0.5F);
	    	}
	    };
		BukkitRunnable secondBark = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_WOLF_AMBIENT, 0.25F, 0.5F);
	    	}
	    };
	    BukkitRunnable turnIntoBeast = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		for (Wolf wolf : wolves) {
		    		wolf.setAngry(true);
		    		if (targets.get(0).isDead())
			    		wolf.setHealth(0);
		    		else
			    		wolf.setTarget(targets.get(0));
	    		}
	    	}
	    };
	    BukkitRunnable end_effect = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		turnIntoBeast.cancel();
	    		for (Wolf wolf : wolves)
	    			wolf.setHealth(0);
	    	}
	    };
	    turnIntoBeast.runTaskTimer(RealmsOfChaos.getInstance(), 1, 1);
	    firstBark.runTaskLater(RealmsOfChaos.getInstance(), 2);
	    secondBark.runTaskLater(RealmsOfChaos.getInstance(), 4);
	    end_effect.runTaskLater(RealmsOfChaos.getInstance(), skillEffectDuration);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
