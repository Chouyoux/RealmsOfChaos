package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class NaturesAnger extends Skill {
	
	public static String skillName = "Nature's Anger";
	public static Material skillMaterial = Material.WITCH_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*1;
	public static int skillCooldown = 20*16;
	public static double skillRadius = 20D;
	public static int skillEffectDuration = 20*2;
	public static int skillEffectValue = 4;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: Summons nature's anger");
		loreList.add("§7as claws biting your ennemies and");
		loreList.add("§7rooting them for a short time.");
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

	public NaturesAnger(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		for (LivingEntity e : nearby)
			if (!(RoCNPC.sameFaction(e, source) || (e instanceof Villager) || e.isInvulnerable())) {
				EvokerFangs claws = (EvokerFangs) e.getWorld().spawnEntity(e.getLocation(), EntityType.EVOKER_FANGS);
				claws.setOwner((Player) source);
    			source.getWorld().playSound(e.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 0.25F, 0.5F);
    			int duration = skillEffectDuration;
    			if (e instanceof Player)
    				duration *= RoCPlayers.getEffectDurationReduction(((Player) e));
    			e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 99));
			}
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
