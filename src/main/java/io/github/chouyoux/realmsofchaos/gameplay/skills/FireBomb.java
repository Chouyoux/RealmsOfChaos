package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class FireBomb extends Skill {
	
	public static String skillName = "Fire Bomb";
	public static Material skillMaterial = Material.PARROT_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*3;
	public static int skillCooldown = 20*16;
	public static double skillRadius = 30D;
	public static int skillEffectDuration = 20*5;
	public static int skillEffectValue = 6;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: The user shoots a slow");
		loreList.add("§7projectile of fire magic that increases");
		loreList.add("§7in intensity then explodes, setting targets");
		loreList.add("§7on fire.");
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

	public FireBomb(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		Location source_eye = source.getEyeLocation();
		Vector direction = source_eye.getDirection();
		org.bukkit.entity.Fireball fire = source.launchProjectile(org.bukkit.entity.Fireball.class, direction.clone().multiply(0.5));
		PersistentDataContainer container = fire.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "FireBomb");
		container.set(key, PersistentDataType.INTEGER, 1);
		fire.setInvulnerable(true);
		fire.setBounce(false);
		fire.setGravity(false);
		fire.setShooter(source);
		source.getWorld().playSound(source.getLocation(), Sound.ITEM_FIRECHARGE_USE, 0.25F, 0.5F);
		
		BukkitRunnable effect = new BukkitRunnable() {
	    	@Override
	        public void run() {
		    	if (fire.isDead())
		    		this.cancel();
		    	Location fire_loc = fire.getLocation();

		    	double rand_x = 6D;
		    	if (Math.random() < 0.5D) rand_x *= -1;
		    	double rand_y = Math.random()*6D;
		    	if (Math.random() < 0.5D) rand_y *= -1;
		    	double rand_z = Math.random()*6D;
		    	if (Math.random() < 0.5D) rand_z *= -1;
		    	Location particle_loc = fire_loc.clone().add(rand_x, rand_y, rand_z);

		    	Vector from_particle_to_bomb = UtilFuncs.vectorBeetween(particle_loc, fire_loc).normalize();
		    	
		    	fire_loc.getWorld().spawnParticle(Particle.FLAME, particle_loc.clone().add(direction.clone()), 0, from_particle_to_bomb.getX(), from_particle_to_bomb.getY(), from_particle_to_bomb.getZ(), 0.5, null, true);
		    	
		    	fire.setVelocity(direction.clone().multiply(0.5));
	    	}
	    };
	    BukkitRunnable explosion = new BukkitRunnable() {
	    	@Override
	        public void run() {
		    	if (fire.isDead())
		    		return;
		    	fire.getWorld().createExplosion(fire.getLocation(), 6F, false, false, source);
		    	fire.remove();
	    	}
	    };

	    effect.runTaskTimer(RealmsOfChaos.getInstance(), 0, 1);
	    explosion.runTaskLater(RealmsOfChaos.getInstance(), 20*3);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

}