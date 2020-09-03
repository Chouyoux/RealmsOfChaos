package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class Empathy extends Skill {
	
	public static String skillName = "Empathy";
	public static Material skillMaterial = Material.FOX_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*1;
	public static int skillCooldown = 20*25;
	public static double skillRadius = 20D;
	public static int skillEffectDuration = 20*6;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = true;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user takes half the damage");
		loreList.add("§7of its target for him.");
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
		loreList.add("§aTarget §7: Looking At");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public Empathy(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_SLIME_JUMP, 0.5F, 0.5F);
		

		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Empathy");
		PersistentDataContainer container = targets.get(0).getPersistentDataContainer();
		container.set(key, PersistentDataType.STRING, source.getName());
		
		BukkitRunnable end_effect = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Empathy");
				PersistentDataContainer container = targets.get(0).getPersistentDataContainer();
				container.set(key, PersistentDataType.STRING, "");
	    	}
	    };
	    end_effect.runTaskLater(RealmsOfChaos.getInstance(), (int) (skillEffectDuration*RoCPlayers.getEffectDurationAugmentation(((Player) source))));
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}