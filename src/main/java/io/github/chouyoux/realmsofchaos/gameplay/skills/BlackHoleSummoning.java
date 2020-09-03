package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class BlackHoleSummoning extends Skill {
	
	public static String skillName = "Black Hole Summoning";
	public static Material skillMaterial = Material.ENDERMAN_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*4;
	public static int skillCooldown = 20*12;
	public static double skillRadius = 30D;
	public static int skillEffectDuration = 20*3;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: Crap...");
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
		loreList.add("§aTarget §7: Ennemies around (sphere)");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public BlackHoleSummoning(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		List<LivingEntity> choosen = new ArrayList<LivingEntity>();
		for (LivingEntity e : nearby) {
			if (!(RoCNPC.sameFaction(e, source) || (e instanceof Villager) || e.isInvulnerable())) {
    			e.setGravity(false);
    			source.getWorld().playSound(e.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 0.25F, 0.5F);
	    		Vector v = new Vector(0, 0.5, 0);
	    		e.setVelocity(v);
	    		choosen.add(e);
			}
		}
    	BukkitRunnable effect = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		for (LivingEntity e : choosen) {
		    		Vector v = new Vector(0, -1, 0);
		    		e.setVelocity(v.multiply(10));
		    		source.getWorld().playSound(e.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.25F, 0.5F);
		    		e.setGravity(true);
	    		}
	    	}
	    };
	    effect.runTaskLater(RealmsOfChaos.getInstance(), skillEffectDuration);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
