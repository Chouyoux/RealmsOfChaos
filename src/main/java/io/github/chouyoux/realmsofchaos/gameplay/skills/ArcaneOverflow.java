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
import io.github.chouyoux.realmsofchaos.particle_effects.ArcaneOverflowEffect;
import io.github.chouyoux.realmsofchaos.particle_effects.FirePropagationHelixEffect;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class ArcaneOverflow extends Skill {
	
	public static String skillName = "Arcane Overflow";
	public static Material skillMaterial = Material.TRADER_LLAMA_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 15;
	public static int skillCooldown = 20*14;
	public static double skillRadius = 25D;
	public static int skillEffectDuration = 0;
	public static int skillEffectValue = 4;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user overflows arcane");
		loreList.add("§7magic within himself causing it to");
		loreList.add("§7explode around him.");
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
		loreList.add("§aTarget §7: Self");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public ArcaneOverflow(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		this.cast();
	}

	@Override
	public void onCastTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEffect() {
		ArcaneOverflowEffect effect = new ArcaneOverflowEffect(RealmsOfChaos.effectManager, source);
    	BukkitRunnable end_arcane = new  BukkitRunnable() {
	    	@Override
	        public void run() {

	    		
	    		Location source_eye = effect.getLocation(); 
	    		List<LivingEntity> nearby = UtilFuncs.getNearbyEntities3D(effect.getLocation(), ArcaneOverflow.skillRadius);
				for (LivingEntity e : nearby) {
					if (UtilFuncs.collisionsOnVector(source_eye, UtilFuncs.vectorBeetween(source_eye, e.getLocation()))) continue;
					if (RoCNPC.sameFaction(source, e)) continue;
					else if (e instanceof Villager) continue;
		    		e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ENDERMAN_AMBIENT, 0.45F, 0.5F);
		    		if (source instanceof Player)
		    			e.damage(skillEffectValue * RoCPlayers.getMagicalDMG((Player) source), source);
		    		else
		    			e.damage(skillEffectValue, source);
				}
	    	}
	    };
	    end_arcane.runTaskLater(RealmsOfChaos.instance, 2*20);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
