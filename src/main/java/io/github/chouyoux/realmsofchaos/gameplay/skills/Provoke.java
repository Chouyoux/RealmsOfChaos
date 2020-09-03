package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.glow.GlowAPI;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.particle_effects.ProvokeEffect;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class Provoke extends Skill {
	
	public static String skillName = "Provoke";
	public static Material skillMaterial = Material.LLAMA_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 10;
	public static int skillCooldown = 20*20;
	public static double skillRadius = 15D;
	public static int skillEffectDuration = 20*5;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user provokes the target");
		loreList.add("§7causing it to only see the user for");
		loreList.add("§7the duration.");
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

	public Provoke(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.1F, 1F);
		int duration = (int) ((targets.get(0) instanceof Player) ? skillEffectDuration*RoCPlayers.getEffectDurationReduction(((Player) targets.get(0))) : skillEffectDuration);

		BukkitRunnable player_end_effect = new BukkitRunnable() {
	    	@Override
	        public void run() {
				GlowAPI.setGlowing((Player) source, false, (Player) targets.get(0));
	    	}
	    };
	    BukkitRunnable mob_effect = new BukkitRunnable() {
	    	@Override
	        public void run() {
                if (targets.get(0).isDead())
                	this.cancel();
    			((Mob)targets.get(0)).setAI(true);
    			((Mob)targets.get(0)).setTarget((Player) source);
	    	}
	    };
	    BukkitRunnable mob_end_effect = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		mob_effect.cancel();
	    	}
	    };
		
		if (targets.get(0) instanceof Player) {
			GlowAPI.setGlowing((Player) source, GlowAPI.Color.RED, (Player) targets.get(0));
    	    player_end_effect.runTaskLater(RealmsOfChaos.getInstance(), duration);
    	    
		}
		else {
    	    mob_effect.runTaskTimer(RealmsOfChaos.getInstance(), 0, 1);
    	    mob_end_effect.runTaskLater(RealmsOfChaos.getInstance(), duration);
		}
		if (targets.get(0) instanceof HumanEntity) ((HumanEntity) targets.get(0)).setCooldown(Material.SHIELD, duration);
		targets.get(0).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, 99));
		new ProvokeEffect(RealmsOfChaos.effectManager, targets.get(0));
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
