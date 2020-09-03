package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class SludgeWall extends Skill {
	
	public static String skillName = "Sludge Wall";
	public static Material skillMaterial = Material.SALMON_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*2;
	public static int skillCooldown = 20*60;
	public static double skillRadius = 9D;
	public static int skillEffectDuration = 20*10;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: User summons a sludge wall");
		loreList.add("§7that setting sludge on ally projectiles");
		loreList.add("§7crossing it.");
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

	public SludgeWall(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		boolean x = (source.getFacing() == BlockFace.NORTH || source.getFacing() == BlockFace.SOUTH);
		Particles.DirectionalWall(target_loc, Particle.WATER_WAKE, x, (int) skillRadius, skillEffectDuration);
		Particles.DirectionalWall(target_loc, Particle.WATER_BUBBLE, x, (int) skillRadius, skillEffectDuration);
		
		ArrayList<Location> locs = new ArrayList<Location>();
		Location loc = target_loc.clone();
		UtilFuncs.setLocOnGround(loc);
		UtilFuncs.setLocInBlockXZCenter(loc);
		int enlargement = (int) ((skillRadius - 1)/2);
		if (x) loc.setX(loc.getX() - enlargement);
		else loc.setZ(loc.getZ() - enlargement);
		
		for (int i = 0; i < skillRadius; i++) {
			locs.add(loc.clone());
			if (x) loc.add(1, 0, 0);
			else loc.add(0, 0, 1);
			
			UtilFuncs.setLocOnGround(loc);
			UtilFuncs.setLocInBlockXZCenter(loc);
		}
		
		BukkitRunnable firewall = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		for (Projectile p : loc.getWorld().getEntitiesByClass(Projectile.class)) {
	    			for (Location one_loc : locs) {
	    				if (p.getLocation().distance(one_loc) > 5D) continue;
	    				if (Math.abs(p.getLocation().getY() - one_loc.getY()) > 4.5D) continue;
	    				if (Math.abs(p.getLocation().getX() - one_loc.getX()) > 1.2D) continue;
	    				if (Math.abs(p.getLocation().getZ() - one_loc.getZ()) > 1.2D) continue;
	    				if (RoCNPC.sameFaction((LivingEntity) p.getShooter(), source)) {
	    					NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
	    					PersistentDataContainer container = p.getPersistentDataContainer();
	    					container.set(key, PersistentDataType.STRING, "SlowSoft");
	    				}
	    			}
	    		}
	    	}
	    };
    	BukkitRunnable firewall_end = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		firewall.cancel();
	    	}
	    };
	    firewall.runTaskTimer(RealmsOfChaos.instance, 0, 1);
	    firewall_end.runTaskLater(RealmsOfChaos.instance, skillEffectDuration);
		
		
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}
