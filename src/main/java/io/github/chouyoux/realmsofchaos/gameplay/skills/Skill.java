package io.github.chouyoux.realmsofchaos.gameplay.skills;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.listeners.Player_Connect;
import io.github.chouyoux.realmsofchaos.util.Particles;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;

public class Skill {

	public String name;
	public Material material;
	public LivingEntity source;
	public ArrayList<LivingEntity> targets; // Target list may be define during any phase of the Skill, or not at all (AoE)
	public Location target_loc;
	public int castDuration; // Represented in ticks, 0 won't trigger cast
	public int cooldown;
	public int delayTicks;
	public int effectDuration;
	public int effectValue;
	public boolean friendly;
	public boolean neutral;
	public double radius;
	public boolean visionTarget = false;
	public boolean visionTargetBlock = false;
	public boolean interruptTriggersCD = false;
	public boolean isADash = false;
	public boolean onlyTargetPlayer = false;
	public boolean isHeal = false;

	public void onCastTick() {}
	public void onEffect() {}
	public void onEnd() {}

	public Skill(LivingEntity source, ArrayList<LivingEntity> targets) {
		this.source = source;
		this.targets = new ArrayList<LivingEntity>();
		this.neutral = false;
		if (targets != null)
			for (LivingEntity e : targets)
				this.targets.add(e);
	}
	
	public static ItemStack MaterialToItem(Material material, Player player) {
		//Magician
		if (RoCPlayers.getClass(player).equals("Magician")) {
			switch (material) {
				case STICK:
					return Arcane.getItem(player);
				case SHULKER_SPAWN_EGG:
					return ArcaneChain.getItem(player);
				case HUSK_SPAWN_EGG:
					return Polymorphism.getItem(player);
				case WITHER_SKELETON_SPAWN_EGG:
					return StoneSkin.getItem(player);
				case RAVAGER_SPAWN_EGG:
					return Swap.getItem(player);
				case PANDA_SPAWN_EGG:
					return Blink.getItem(player);
				case MULE_SPAWN_EGG:
					return SludgeWave.getItem(player);
				case MAGMA_CUBE_SPAWN_EGG:
					return Thunderstrike.getItem(player);
				case WITCH_SPAWN_EGG:
					return NaturesAnger.getItem(player);
				case ELDER_GUARDIAN_SPAWN_EGG:
					return ArcaneWard.getItem(player);
				case TRADER_LLAMA_SPAWN_EGG:
					return ArcaneOverflow.getItem(player);
				case SPIDER_SPAWN_EGG:
					return Fireball.getItem(player);
				case PARROT_SPAWN_EGG:
					return FireBomb.getItem(player);
				case CAVE_SPIDER_SPAWN_EGG:
					return FirePropagation.getItem(player);
				default:
					return UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null);
			}
		}
		//Archer
		if (RoCPlayers.getClass(player).equals("Archer")) {
			switch (material) {
				case CREEPER_SPAWN_EGG:
					return InfiltratorsArrow.getItem(player);
				case PIGLIN_SPAWN_EGG:
					return Poison.getItem(player);
				case VEX_SPAWN_EGG:
					return SoulRead.getItem(player);
				case WOLF_SPAWN_EGG:
					return AttackOrder.getItem(player);
				case HORSE_SPAWN_EGG:
					return Camouflage.getItem(player);
				case CAT_SPAWN_EGG:
					return Knockback.getItem(player);
				case ENDERMITE_SPAWN_EGG:
					return PullingShot.getItem(player);
				case MOOSHROOM_SPAWN_EGG:
					return RainOfArrows.getItem(player);
				case CHICKEN_SPAWN_EGG:
					return Fire.getItem(player);
				case SALMON_SPAWN_EGG:
					return ParalysingPoison.getItem(player);
				case GUARDIAN_SPAWN_EGG:
					return BouncingLance.getItem(player);
				default:
					return UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null);
			}
		}
		//Warrior
		if (RoCPlayers.getClass(player).equals("Warrior")) {
			switch (material) {
				case COD_SPAWN_EGG:
					return Abnegation.getItem(player);
				case STRAY_SPAWN_EGG:
					return MindCleanse.getItem(player);
				case LLAMA_SPAWN_EGG:
					return Provoke.getItem(player);
				case FOX_SPAWN_EGG:
					return Empathy.getItem(player);
				case BEE_SPAWN_EGG:
					return GolemShape.getItem(player);
				case ZOMBIE_SPAWN_EGG:
					return Charge.getItem(player);
				case BLAZE_SPAWN_EGG:
					return IntimidatingShout.getItem(player);
				case EVOKER_SPAWN_EGG:
					return Grab.getItem(player);
				case ENDERMAN_SPAWN_EGG:
					return SlowingStance.getItem(player);
				case CAVE_SPIDER_SPAWN_EGG:
					return FireTouch.getItem(player);
				case PARROT_SPAWN_EGG:
					return FireEruption.getItem(player);
				default:
					return UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null);
			}
		}
		//Duelist
		if (RoCPlayers.getClass(player).equals("Duelist")) {
			switch (material) {
				case PIG_SPAWN_EGG:
					return Devotion.getItem(player);
				case SHEEP_SPAWN_EGG:
					return Adrenaline.getItem(player);
				case RAVAGER_SPAWN_EGG:
					return Cowardise.getItem(player);
				case SPIDER_SPAWN_EGG:
					return DemonStance.getItem(player);
				case ZOMBIE_SPAWN_EGG:
					return StunningStance.getItem(player);
				default:
					return UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null);
			}
		}
		//Healer
		if (RoCPlayers.getClass(player).equals("Healer")) {
			switch (material) {
				case BAMBOO:
					return LifeGrasp.getItem(player);
				case GHAST_SPAWN_EGG:
					return DeepMeditation.getItem(player);
				case SKELETON_SPAWN_EGG:
					return EntraveToEvil.getItem(player);
				case TURTLE_SPAWN_EGG:
					return SongOfAdrenaline.getItem(player);
				case BAT_SPAWN_EGG:
					return DarknessVision.getItem(player);
				case PIG_SPAWN_EGG:
					return SongOfExcitement.getItem(player);
				case PILLAGER_SPAWN_EGG:
					return SludgeWall.getItem(player);
				case PUFFERFISH_SPAWN_EGG:
					return SongOfAquaticGrace.getItem(player);
				case SALMON_SPAWN_EGG:
					return SludgeWall.getItem(player);
				case SKELETON_HORSE_SPAWN_EGG:
					return SongOfFeathers.getItem(player);
				case PARROT_SPAWN_EGG:
					return FireWall.getItem(player);
				default:
					return UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null);
			}
		}
		return UtilFuncs.createGuiItem(Material.DOLPHIN_SPAWN_EGG, "No Skill Assigned", null);
	}
	
	protected void cast() {
		BukkitRunnable inner_cast = new BukkitRunnable() {
			    		
			private boolean isValid = true;
			private int progress = 0;
			private int nameLen = name.length();
			
			private Location loc;
			private boolean sourceHasTouchedGround = false;
			    		
			@Override
			public void run() {
				if (!(source instanceof Player)) return;
			    Player p = (Player) source;
				boolean isRooted = false;
				Collection<PotionEffect> potions = p.getActivePotionEffects();
				for (PotionEffect potion : potions)
					if (potion.getType().equals(PotionEffectType.SLOW) && potion.getAmplifier() == 99)
						isRooted = true;
				if (p.getCooldown(material) > 0 || (isADash && isRooted)) {
				    isValid = false;
		    		RoCPlayers.setIsCasting(p, -1);
			    	p.setGravity(true);
					this.cancel();
					return;
				}
				if (!sourceHasTouchedGround)
					if (source.getVelocity().getY() == -0.009800000190734863 || source.getVelocity().getY() == -0.0784000015258789 || source.getLocation().getBlock().getType() == Material.WATER) {
						loc = p.getLocation();
						p.setGravity(false);
						sourceHasTouchedGround = true;
					}
				    else
				    	return;
				if (castDuration > 0 && ((progress > 0 && RoCPlayers.getIsCasting(p) == -1) || p.getLocation().distance(loc) > 0.1 || p.getInventory().getItemInMainHand().getType() != material)) {
				    isValid = false;
			    	RoCPlayers.setIsCasting(p, -1);
			    	p.setGravity(true);
			    	this.cancel();
			    	p.sendActionBar("§c§m"+name);
			    	if (interruptTriggersCD)
				    	p.setCooldown(material, cooldown);
				}
				if (isValid) {
				    this.progress++;
			    	RoCPlayers.setIsCasting(p, this.progress);
				    
				    //Cast text
				    String text = "";
				    double progress_ratio = ((double)this.progress/(double)Math.max(1, castDuration));
				    text += "§f";
			    	if (interruptTriggersCD)
					    text += "§a";
				    int i = 0;
				    for (; i < progress_ratio*(nameLen-1); i++) {
				    	text += name.charAt(i);
				    }
				    text += "§7§k";
				    for(; i < nameLen ; i++) {
				    	text += name.charAt(i);
				    }
				    p.sendActionBar(text);
				    
				    //Particle effect
				    Particles.Circle(loc.clone().add(0, 0.2, 0), 1D, progress_ratio, (int)(progress_ratio*20), Particle.ENCHANTMENT_TABLE, false);
				    
				    //Target acquiring
				    if (visionTarget) {
					    LivingEntity target = UtilFuncs.getLookingAtRange(source, radius, (friendly || neutral), (!friendly || neutral), !isHeal);
					    if (target != null)
					    	if (!(target instanceof Villager) &&
					    			((neutral || !friendly && !RoCNPC.sameFaction(source, target)) || (friendly && RoCNPC.sameFaction(source, target))) &&
					    			(!onlyTargetPlayer || target instanceof Player))
						    	if (targets.size() > 0)
						    		targets.set(0, target);
						    	else
						    		targets.add(target);
				    }
				    
				    //Cast effect
				    onCastTick();
				    
				    //End of cast
				    if (progress+1 >= castDuration) {
				    	RoCPlayers.setIsCasting(p, -1);
				    	p.setGravity(true);
				    	boolean spell_valid = true;
				    	
				    	//Target Block acquiring
					    if (visionTargetBlock) {
					    	Block target_block = source.getTargetBlockExact((int) radius);
					    	if (target_block == null)  spell_valid = false;
					    	else {
					    		Location target_block_loc = target_block.getLocation().add(0.5, 1.1, 0.5);
					    		int _i = 0;
					    		while (!(target_block_loc.getBlock().isPassable())) {
					    			if (_i > 10) {
					    				spell_valid = false;
					    				break;
					    			}
					    			target_block_loc.add(0, 1, 0);
					    			_i++;
					    		}
					    		if (spell_valid) {
					    			_i = 0;
					    			while (target_block_loc.clone().add(0, -1, 0).getBlock().isPassable()) {
					    				if (_i > 10) {
						    				spell_valid = false;
						    				break;
						    			}
						    			target_block_loc.add(0, -1, 0);
						    			_i++;
					    			}
					    			if (spell_valid) {
					    				target_loc = target_block_loc;
					    			}
					    		}
					    	}
					    	
					    }
				    	if (visionTarget && targets.size() <= 0) {
				    		spell_valid = false;
				    	}
				    	if (spell_valid) {
				    		p.sendActionBar("§a"+name);
					    	p.setCooldown(material, cooldown);
					    	onEffect();
				    	}
				    	else
				    		p.sendActionBar("§c§m"+name);
				    	this.cancel();
				    }
				}
			}
		};
		inner_cast.runTaskTimer(RealmsOfChaos.getInstance(), 0, 1);
	}

}
