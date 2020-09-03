package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class FireEruption extends Skill {

	public static String skillName = "Fire Eruption";
	public static Material skillMaterial = Material.PARROT_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 0;
	public static int skillCooldown = 20*15;
	public static double skillRadius = 12D;
	public static int skillEffectDuration = 20*8;
	public static int skillEffectValue = 3;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: User raises fire");
		loreList.add("§7eruptions from the ground in");
		loreList.add("§7a line in from of him, bumping");
		loreList.add("§7enemies players up, and setting");
		loreList.add("§7them on fire.");
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

	public FireEruption(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		Location source_eye = source.getEyeLocation().clone();
		Vector direction = source_eye.getDirection();
		Vector leftDirection = direction.clone().rotateAroundY(90);
		Vector rightDirection = direction.clone().rotateAroundY(-90);
		direction.setY(0);
		
		ArrayList<Location> effectLocs = new ArrayList<Location>();
		Location propagationLoc = source_eye.clone();
		for (int i = 0; i < skillRadius; i++) {
			
			propagationLoc.add(direction);
			effectLocs.add(propagationLoc.clone());
			effectLocs.add(propagationLoc.clone().add(leftDirection));
			effectLocs.add(propagationLoc.clone().add(rightDirection));
			
		}
		
		for (Location effectLoc : effectLocs) {
			
			UtilFuncs.setLocOnGround(effectLoc);
			UtilFuncs.setLocInBlockXZCenter(effectLoc);
			
			effectLoc = effectLoc.getBlock().getLocation();
			
			effectLoc.getWorld().playSound(effectLoc, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.25F, 0.1F);
			Particles.FireEruption(effectLoc);
			
			Collection<? extends Player> online_players = Bukkit.getServer().getOnlinePlayers();
			for (Object p : online_players.toArray())
				if (p instanceof Player && !(RoCPlayers.sameFaction((Player) p, (Player) source))) {
					Location player_block_loc = ((Player) p).getLocation().getBlock().getLocation();
					if (effectLoc.equals(player_block_loc))
						Effect((LivingEntity) p);
						
				}
			
			for (Entity e : source_eye.getWorld().getEntities()) {
				if (!(e instanceof Villager) && !(RoCNPC.sameFaction(e, source) && e instanceof LivingEntity)) {
					Location player_block_loc = e.getLocation().getBlock().getLocation();
					if (effectLoc.equals(player_block_loc))
						Effect((LivingEntity) e);
				}
			}
			
		}
	}
	
	private void Effect(LivingEntity e) {
		e.setVelocity(new Vector(0, 1, 0));
		e.damage(skillEffectValue, source);
		int duration = skillEffectDuration;
		if (e instanceof Player)
			duration *= RoCPlayers.getEffectDurationReduction((Player) e);
		e.setFireTicks(duration);
	}

}
