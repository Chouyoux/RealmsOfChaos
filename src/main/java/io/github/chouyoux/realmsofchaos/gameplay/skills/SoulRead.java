package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.glow.GlowAPI;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class SoulRead extends Skill {
	
	public static String skillName = "Soul Read";
	public static Material skillMaterial = Material.VEX_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*1;
	public static int skillCooldown = 20*60;
	public static double skillRadius = 50D;
	public static int skillEffectDuration = 20*20;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = false;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: Grants all the players in the");
		loreList.add("§7user's faction extra-sensorial vision");
		loreList.add("§7over the target.");
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
		loreList.add("§aTarget §7: Looking At (Only players)");
		if (skillRadius > 0) loreList.add("§aRange §7: " + skillRadius + " meters");
		return UtilFuncs.createGuiItemL(skillMaterial, "§f§n"+skillName, loreList);
	}

	public SoulRead(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		onlyTargetPlayer = true;
		this.cast();
	}
	
	@Override
	public void onEffect() {
		targets.get(0).getWorld().playSound(source.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 0.25F, 0.5F);
		int duration = skillEffectDuration;
		if (targets.get(0) instanceof Player)
			duration *= RoCPlayers.getEffectDurationReduction(((Player) targets.get(0)));
		Collection<? extends Player> online_players = Bukkit.getServer().getOnlinePlayers();
		ArrayList<Player> choosen = new ArrayList<Player>();
		for (Object p : online_players.toArray())
			if (p instanceof Player && RoCPlayers.sameFaction((Player) p, (Player) source)) {
				choosen.add((Player) p);
    			GlowAPI.setGlowing((Player) targets.get(0), GlowAPI.Color.DARK_GREEN, (Player) p);
			}
    	BukkitRunnable end_effect = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		for (Player p : choosen)
	    			GlowAPI.setGlowing((Player) targets.get(0), false, (Player) p);
	    	}
	    };
	    end_effect.runTaskLater(RealmsOfChaos.getInstance(), duration);
	}
	

}
