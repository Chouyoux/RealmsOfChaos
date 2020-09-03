package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class ArcaneWard extends Skill {
	
	public static String skillName = "Arcane Ward";
	public static Material skillMaterial = Material.ELDER_GUARDIAN_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*3;
	public static int skillCooldown = 20*60;
	public static double skillRadius = 10D;
	public static int skillEffectDuration = 20*30;
	public static int skillEffectValue = 2;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: User summons an arcane ward");
		loreList.add("§7that shoots Arcane trails every 3 seconds");
		loreList.add("§7to the closest ennemy.");
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
		loreList.add("§aTarget §7: Looking At (Block)");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public ArcaneWard(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		visionTargetBlock = true;
		this.cast();
	}

	@Override
	public void onCastTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEffect() {
		EnderCrystal crystal = (EnderCrystal) target_loc.getWorld().spawnEntity(target_loc, EntityType.ENDER_CRYSTAL);
		RoCNPC.setFaction((Entity) crystal, RoCNPC.getFaction(source));
		PersistentDataContainer container = crystal.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "ArcaneWard");
		container.set(key, PersistentDataType.INTEGER, 1);
    	BukkitRunnable arcane = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		
	    		if (crystal.isDead()) {
	    			this.cancel();
	    			return;
	    		}
	    		
	    		List<LivingEntity> nearby = UtilFuncs.getNearbyEntities3D(crystal.getLocation(), Arcane.skillRadius);
				double min = Double.MAX_VALUE;
				LivingEntity next_target = null;
				for (LivingEntity e : nearby) {
					if (RoCNPC.sameFaction(source, e)) continue;
					else if (e instanceof Villager) continue;
					else if (!(UtilFuncs.canSee(crystal, e))) continue;
					double distance = e.getLocation().distance(crystal.getLocation());
					if (distance < min) {
						min = distance;
						next_target = e;
					}
				}
	    		
	    		Location source_eye = crystal.getLocation().clone().add(0, 0.5, 0);
	    		Location target_eye = next_target.getEyeLocation().clone().add(0, -0.25F, 0);
	    		int nb_particle = (int) source_eye.distance(target_eye);
	    		Particles.Trail(source_eye, target_eye, nb_particle, Particle.DRAGON_BREATH);
	    		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.25F, 0.1F);
	    		next_target.damage(skillEffectValue * RoCPlayers.getMagicalDMG((Player) source), source);
	    	}
	    };
    	BukkitRunnable end_arcane = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		if (!(arcane.isCancelled())) {
	    			crystal.remove();
	    			arcane.cancel();
	    		}
	    	}
	    };
	    arcane.runTaskTimer(RealmsOfChaos.instance, 0, 20*3);
	    end_arcane.runTaskLater(RealmsOfChaos.instance, skillEffectDuration);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}
}
