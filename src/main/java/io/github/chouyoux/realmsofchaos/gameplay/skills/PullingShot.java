package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.particle_effects.HookTraceEffect;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class PullingShot extends Skill {
	
	public static String skillName = "Pulling Shot";
	public static Material skillMaterial = Material.ENDERMITE_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*1;
	public static int skillCooldown = 20*15;
	public static double skillRadius = 20D;
	public static int skillEffectDuration = 0;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = true;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user shoots an arrow");
		loreList.add("§7unaffected by gravity that pulls its");
		loreList.add("§7target towards the user on a short");
		loreList.add("§7distance.");
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
		loreList.add("§aTarget §7: Projectile's Target");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public PullingShot(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		
		Vector looking_at = source.getEyeLocation().getDirection().multiply(2F);
		Arrow arrow = (Arrow) source.getWorld().spawnEntity(source.getEyeLocation().add(source.getEyeLocation().getDirection().multiply(1.5F)), EntityType.ARROW);
		arrow.setVelocity(looking_at);
		arrow.setShooter(source);
		arrow.setGravity(false);
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "PullingShot");
		PersistentDataContainer container = arrow.getPersistentDataContainer();
		container.set(key, PersistentDataType.STRING, source.getUniqueId().toString());
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_ARROW_SHOOT, 0.25F, 0.5F);
		new HookTraceEffect(RealmsOfChaos.effectManager, arrow);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}
}
