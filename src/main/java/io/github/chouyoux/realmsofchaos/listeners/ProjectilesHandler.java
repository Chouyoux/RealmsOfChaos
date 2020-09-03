package io.github.chouyoux.realmsofchaos.listeners;

import static io.github.chouyoux.realmsofchaos.RealmsOfChaos.getInstance;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;

public class ProjectilesHandler implements Listener{
    private RealmsOfChaos main;
    private HashMap<Player, ItemStack> tridentMap;

	public ProjectilesHandler() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
        tridentMap = new HashMap<Player, ItemStack>();
    }
	
	// Multiplies velocity of arrows shot by a player according to his DEX
	@EventHandler
	public void onShootingArrow(ProjectileLaunchEvent event) {
		if (!(event.getEntity() instanceof Arrow))
			return;
		Projectile projectile = (Projectile) event.getEntity();
		if (!(projectile.getShooter() instanceof Player))
			return;
		Player shooter = (Player) projectile.getShooter();


		
		ItemStack item = shooter.getInventory().getItemInMainHand();
		if (item.getType() == Material.CROSSBOW) {
			double projectile_max_speed = 3.500F;
			projectile.setVelocity(projectile.getVelocity().normalize().multiply(projectile_max_speed));
			ItemMeta item_meta = item.getItemMeta();
			NamespacedKey key = new NamespacedKey(getInstance(), "Loading");
			PersistentDataContainer container = item_meta.getPersistentDataContainer();
			container.set(key, PersistentDataType.INTEGER, 0);
			item.setItemMeta(item_meta);
		}
		
		if (item.getType() == Material.BOW) {
			double shooter_attack_speed = RoCPlayers.getPhysicalATKSPD(shooter)*1.25;
			double projectile_speed = projectile.getVelocity().length();
			double projectile_max_speed = 3.000F;
			double projectile_load_rate = projectile_speed / projectile_max_speed;
			double new_projectile_load_rate = Math.min(1, projectile_load_rate*shooter_attack_speed);
			projectile.setVelocity(projectile.getVelocity().normalize().multiply(projectile_max_speed*new_projectile_load_rate));
		}
	}
	
	@EventHandler
	public void onShootingTrident(ProjectileLaunchEvent event) {
		if (!(event.getEntity() instanceof Trident)) return;
		Projectile projectile = (Projectile) event.getEntity();
		if (!(projectile.getShooter() instanceof Player)) return;
		Player shooter = (Player) projectile.getShooter();
		if (shooter.getCooldown(Material.TRIDENT) > 0) {
			event.setCancelled(true);
			return;
		}
		Trident trident = (Trident) event.getEntity();
		trident.setPickupStatus(PickupStatus.DISALLOWED);
		PlayerInventory inventory = shooter.getInventory();
		inventory.setItem(0, tridentMap.get(shooter));
		int load_ticks = (int) ((25) / (RoCPlayers.getPhysicalATKSPD(shooter)*1.25));
		shooter.setCooldown(Material.TRIDENT, load_ticks);
	}
	
	@EventHandler
	public void onLoadingTrident(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getItem() == null) return;
		if (event.getItem().getType() != Material.TRIDENT) return;
		Player player = event.getPlayer();
		tridentMap.put(player, event.getItem());
	}
	
	@EventHandler
	public void onLoadingBow(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getItem() == null) return;
		if (event.getItem().getType() != Material.BOW) return;
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		if (inventory.getItemInOffHand().getType() != Material.ARROW) {
			event.setCancelled(true);
			return;
		}
		int load_ticks = (int) ((25) / (RoCPlayers.getPhysicalATKSPD(player)*1.25));
		player.setCooldown(Material.ARROW, load_ticks);
	}
	
	@EventHandler
	public void onLoadingCrossbow(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getItem() == null) return;
		if (event.getItem().getType() != Material.CROSSBOW) return;
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		ItemStack item = event.getItem();
		CrossbowMeta item_meta = ((CrossbowMeta) item.getItemMeta());
		if (inventory.getItemInOffHand().getType() != Material.ARROW && !item_meta.hasChargedProjectiles()) {
			event.setCancelled(true);
			return;
		}
		if (item_meta.hasChargedProjectiles() || player.getCooldown(Material.ARROW) > 0) return;
		BukkitRunnable charge_crossbow = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		if (inventory.getItemInMainHand().equals(item) && inventory.getItemInOffHand().getType() == Material.ARROW) {
	    			item_meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
	    			//inventory.getItemInOffHand().setAmount(inventory.getItemInOffHand().getAmount()-1);
	    			item.setItemMeta(item_meta);
	    		}
	    	}
	    };
		int load_ticks = (int) ((30) / (RoCPlayers.getPhysicalATKSPD(player)*1.25));
		player.setCooldown(Material.ARROW, load_ticks);
	    charge_crossbow.runTaskLater(main, load_ticks);
	}
	
	@EventHandler
	public void onLandingTrident(ProjectileHitEvent event) {
		if (!(event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof Trident)) return;
		Trident trident = (Trident) event.getEntity();
		BukkitRunnable remove_trident = new BukkitRunnable() {
	    	@Override
	        public void run() {
	    		trident.remove();
	    	}
	    };
	    remove_trident.runTaskLater(main, 20);
		
	}
}
