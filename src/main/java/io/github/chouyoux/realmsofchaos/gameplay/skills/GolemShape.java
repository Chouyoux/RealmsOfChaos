package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class GolemShape extends Skill {
	
	public static String skillName = "Golem Shape";
	public static Material skillMaterial = Material.BEE_SPAWN_EGG;
	public static int skillDelayTicks = 0;
	public static int skillCastDuration = 20*2;
	public static int skillCooldown = 20*20;
	public static double skillRadius = 0D;
	public static int skillEffectDuration = 20*20;
	public static int skillEffectValue = 0;
	public static boolean skillFriendly = true;
	public static boolean skillPhysical = false;
	
	public static ItemStack getItem(Player player) {
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("§aEffect §7: User takes the shape of");
		loreList.add("§7an ancient golem, gaining bonus health");
		loreList.add("§7and attack and decreasing his move");
		loreList.add("§7speed. User's attacks also bump their");
		loreList.add("§7target in the air. Can't cast any spell");
		loreList.add("§7for the duration.");
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

	public GolemShape(LivingEntity source, ArrayList<LivingEntity> targets) {
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
		source.getWorld().playSound(source.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.25F, 0.5F);
		source.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, skillEffectDuration, 1));
		source.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, skillEffectDuration, 4));
		source.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, skillEffectDuration, 2));
		MobDisguise mobDisguise = new MobDisguise(DisguiseType.IRON_GOLEM);
		mobDisguise.setHideHeldItemFromSelf(true);
		mobDisguise.setEntity(source);
		
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "disguiseplayer " + source.getName() +  " Iron_Golem");
		
		/*ProtocolManager pm = ProtocolLibrary.getProtocolManager();
		PacketContainer packet = pm.createPacket(PacketType.Play.Server.CAMERA);
		packet.getModifier().writeDefaults();
		packet.getIntegers().write(0, mobDisguise.getEntity().getEntityId());
		try {
			pm.sendServerPacket((Player) source, packet);
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}*/
		
		
	    BukkitRunnable end = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "disguiseplayer " + source.getName() +  " Player " + source.getName());
	    	}
	    };
    	end.runTaskLater(RealmsOfChaos.instance, skillEffectDuration);
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
	}

}
