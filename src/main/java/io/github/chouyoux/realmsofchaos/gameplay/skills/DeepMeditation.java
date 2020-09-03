package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class DeepMeditation extends Skill {
	
	public static String skillName = "Deep Meditation";
	public static Material skillMaterial = Material.GHAST_SPAWN_EGG;
	public static int skillDelayTicks = 10;
	public static int skillCastDuration = 20*5;
	public static int skillCooldown = 20*20;
	public static double skillRadius = 20D;
	public static int skillEffectDuration = 0;
	public static int skillEffectValue = 1;
	public static boolean skillFriendly = true;
	public static boolean skillPhysical = false;
	public static boolean skillIsHeal = true;

	private int castTickEffectCount;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user empties his mind");
		loreList.add("§7of everything but the wounds on his");
		loreList.add("§7surrounding allies.");
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
		loreList.add("§aCanalisation Time §7: " + (double)skillCastDuration/20 + " seconds");
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
	
	public DeepMeditation(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		isHeal = skillIsHeal;
		visionTarget = false;
		interruptTriggersCD = true;
		castTickEffectCount = 0;
		this.cast();
	}
	
	@Override
	public void onCastTick() {
		castTickEffectCount++;
		if (castTickEffectCount == delayTicks) {
			castTickEffectCount = 0;
			List<LivingEntity> nearby = UtilFuncs.getNearbyEntities3D(source.getLocation(), radius);
			for (LivingEntity e : nearby) {
				if (e instanceof Villager || !RoCNPC.sameFaction(source, e) || e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() == e.getHealth())
					continue;
				e.setHealth(Math.min(e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue(), e.getHealth()+(skillEffectValue*RoCPlayers.getHealing((Player) source))));
				World w = e.getWorld();
			    w.spawnParticle(Particle.HEART, e.getLocation(), 2, 0, 1.5, 1, 0.5);
			    w.spawnParticle(Particle.HEART, e.getLocation(), 2, -1, 1.5, 0, 0.5);
			}
		}
	}

}
