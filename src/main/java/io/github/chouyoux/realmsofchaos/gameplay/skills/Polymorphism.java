package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class Polymorphism extends Skill {
	
	public static String skillName = "Polymorphism";
	public static Material skillMaterial = Material.HUSK_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20;
	public static int skillCooldown = 20*18;
	public static double skillRadius = 30D;
	public static int skillEffectDuration = 20*4;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: User uses arcane magic");
		loreList.add("§7to transform the target into a bunny");
		loreList.add("§7for a short time.");
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

	public Polymorphism(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		Location source_eye = source.getEyeLocation().clone().add(0, -0.4F, 0);
		Location target_eye = targets.get(0).getEyeLocation().clone().add(0, -0.25F, 0);
		int nb_particle = (int) source_eye.distance(target_eye);
		Particles.Trail(source_eye, target_eye, nb_particle, Particle.CLOUD);
		Particles.Trail(source_eye, target_eye, nb_particle, Particle.FIREWORKS_SPARK);
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_RABBIT_ATTACK, 0.5F, 1F);
		targets.get(0).getWorld().playSound(targets.get(0).getLocation(), Sound.ENTITY_RABBIT_ATTACK, 0.5F, 1F);
		
		MobDisguise mobDisguise = new MobDisguise(DisguiseType.RABBIT);
		mobDisguise.setHideHeldItemFromSelf(true);
		mobDisguise.setEntity(targets.get(0));
		mobDisguise.startDisguise();
		
		int duration = skillEffectDuration;
		if (targets.get(0) instanceof Player)
			duration *= RoCPlayers.getEffectDurationReduction((Player) targets.get(0));
		
		BukkitRunnable end = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		mobDisguise.removeDisguise();
	    	}
	    };
    	end.runTaskLater(RealmsOfChaos.instance, duration);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}


}
