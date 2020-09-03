package io.github.chouyoux.realmsofchaos.listeners;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.gameplay.skills.*;
import net.minecraft.server.v1_16_R1.EntityLiving;

public class SkillItemsUsage implements Listener{
    private RealmsOfChaos main;

	public SkillItemsUsage() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onLeftClickDuringCast(PlayerInteractEvent event) {
		if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) return;
		Player player = event.getPlayer();
		if (event.getItem() == null) return;
		if (! (event.getItem().getType().toString().contains("EGG") || (event.getItem().getType().toString().contains("STICK"))) ) return;
		if (RoCPlayers.getIsCasting(player) == -1) return;
		event.setCancelled(true);
		RoCPlayers.setIsCasting(player, -1);
	}
	
	@EventHandler
	public void onCasterBeingHurt(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		if (!(victim instanceof Player)) return;
		Player player = (Player) victim;
		if (RoCPlayers.getIsCasting(player) == -1) return;
		EntityLiving el = ((CraftPlayer)player).getHandle();
		
		if (el.getAbsorptionHearts() > 0) return;
		RoCPlayers.setIsCasting(player, -1);
	}
	
	@EventHandler
	public void onMoveDuringCast(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (RoCPlayers.getIsCasting(player) == -1) return;
		
		if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {
            Location loc = event.getFrom();
            event.getPlayer().teleport(loc.setDirection(event.getTo().getDirection()));
            event.setCancelled(true);
        }
	}
	
	@EventHandler
	public void onUseMonsterEgg(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		Player player = event.getPlayer();
		if (event.getItem() == null) return;
		if (player.getOpenInventory().getType() != InventoryType.CRAFTING) return;
		if (event.getClickedBlock() != null && event.getClickedBlock().getType().isInteractable() && !(event.getClickedBlock().getType().toString().contains("STAIR"))
				&& !(event.getClickedBlock().getType().toString().contains("TRAP")) && !(event.getClickedBlock().getType().toString().contains("SLAB"))
				&& !(event.getClickedBlock().getType().toString().contains("FENCE")) ) return;
		if (! (event.getItem().getType().toString().contains("EGG") ||
				(event.getItem().getType().toString().contains("STICK") ||
					(event.getItem().getType().toString().contains("BAMBOO"))) )) return;
		event.setCancelled(true);
		if (player.getVehicle() != null) return;
		if (RoCPlayers.getIsCasting(player) != -1) return;
		//Magician
		if (RoCPlayers.getClass(player).equals("Magician")) {
			switch (event.getItem().getType()) {
				case STICK:
					new Arcane(player, null);
					break;
				case SHULKER_SPAWN_EGG:
					new ArcaneChain(player, null);
					break;
				case HUSK_SPAWN_EGG:
					new Polymorphism(player, null);
					break;
				case WITHER_SKELETON_SPAWN_EGG:
					new StoneSkin(player, null);
					break;
				case PANDA_SPAWN_EGG:
					new Blink(player, null);
					break;
				case RAVAGER_SPAWN_EGG:
					new Swap(player, null);
					break;
				case SPIDER_SPAWN_EGG:
					new Fireball(player, null);
					break;
				case PARROT_SPAWN_EGG:
					new FireBomb(player, null);
					break;
				case CAVE_SPIDER_SPAWN_EGG:
					new FirePropagation(player, null);
					break;
				case MULE_SPAWN_EGG:
					new SludgeWave(player, null);
					break;
				case MAGMA_CUBE_SPAWN_EGG:
					new Thunderstrike(player, null);
					break;
				case WITCH_SPAWN_EGG:
					new NaturesAnger(player, null);
					break;
				case ELDER_GUARDIAN_SPAWN_EGG:
					new ArcaneWard(player, null);
					break;
				case TRADER_LLAMA_SPAWN_EGG:
					new ArcaneOverflow(player, null);
					break;
				default:
					break;
			};
		}
		//Archer
		if (RoCPlayers.getClass(player).equals("Archer")) {
			switch (event.getItem().getType()) {
				case CREEPER_SPAWN_EGG:
					new InfiltratorsArrow(player, null);
					break;
				case PIGLIN_SPAWN_EGG:
					new Poison(player, null);
					break;
				case VEX_SPAWN_EGG:
					new SoulRead(player, null);
					break;
				case WOLF_SPAWN_EGG:
					new AttackOrder(player, null);
					break;
				case HORSE_SPAWN_EGG:
					new Camouflage(player, null);
					break;
				case CAT_SPAWN_EGG:
					new Knockback(player, null);
					break;
				case ENDERMITE_SPAWN_EGG:
					new PullingShot(player, null);
					break;
				case MOOSHROOM_SPAWN_EGG:
					new RainOfArrows(player, null);
					break;
				case CHICKEN_SPAWN_EGG:
					new Fire(player, null);
					break;
				case SALMON_SPAWN_EGG:
					new ParalysingPoison(player, null);
					break;
				case GUARDIAN_SPAWN_EGG:
					new BouncingLance(player, null);
				break;
				default:
					break;
			};
		}
		//Warrior
		if (RoCPlayers.getClass(player).equals("Warrior")) {
			switch (event.getItem().getType()) {
				case COD_SPAWN_EGG:
					new Abnegation(player, null);
					break;
				case STRAY_SPAWN_EGG:
					new MindCleanse(player, null);
					break;
				case LLAMA_SPAWN_EGG:
					new Provoke(player, null);
					break;
				case FOX_SPAWN_EGG:
					new Empathy(player, null);
					break;
				case BEE_SPAWN_EGG:
					new GolemShape(player, null);
					break;
				case ZOMBIE_SPAWN_EGG:
					new Charge(player, null);
					break;
				case BLAZE_SPAWN_EGG:
					new IntimidatingShout(player, null);
					break;
				case EVOKER_SPAWN_EGG:
					new Grab(player, null);
					break;
				case ENDERMAN_SPAWN_EGG:
					new SlowingStance(player, null);
					break;
				case CAVE_SPIDER_SPAWN_EGG:
					new FireTouch(player, null);
					break;
				case PARROT_SPAWN_EGG:
					new FireEruption(player, null);
					break;
				default:
					break;
			};
		}
		//Duelist
		if (RoCPlayers.getClass(player).equals("Duelist")) {
			switch (event.getItem().getType()) {
				case PIG_SPAWN_EGG:
					new Devotion(player, null);
					break;
				case SHEEP_SPAWN_EGG:
					new Adrenaline(player, null);
					break;
				case RAVAGER_SPAWN_EGG:
					new Cowardise(player, null);
					break;
				case SPIDER_SPAWN_EGG:
					new DemonStance(player, null);
					break;
				case ZOMBIE_SPAWN_EGG:
					new StunningStance(player, null);
					break;
				default:
					break;
			};
		}
		//Healer
		if (RoCPlayers.getClass(player).equals("Healer")) {
			switch (event.getItem().getType()) {
				case BAMBOO:
					new LifeGrasp(player, null);
					break;
				case GHAST_SPAWN_EGG:
					new DeepMeditation(player, null);
					break;
				case SKELETON_SPAWN_EGG:
					new EntraveToEvil(player, null);
					break;
				case TURTLE_SPAWN_EGG:
					new SongOfAdrenaline(player, null);
					break;
				case BAT_SPAWN_EGG:
					new DarknessVision(player, null);
					break;
				case PIG_SPAWN_EGG:
					new SongOfExcitement(player, null);
					break;
				case PILLAGER_SPAWN_EGG:
					new WindWall(player, null);
					break;
				case PUFFERFISH_SPAWN_EGG:
					new SongOfAquaticGrace(player, null);
					break;
				case SALMON_SPAWN_EGG:
					new SludgeWall(player, null);
					break;
				case SKELETON_HORSE_SPAWN_EGG:
					new SongOfFeathers(player, null);
					break;
				case PARROT_SPAWN_EGG:
					new FireWall(player, null);
					break;
				default:
					break;
			};
		}
	}
	
}
