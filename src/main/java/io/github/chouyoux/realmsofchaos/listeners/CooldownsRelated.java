package io.github.chouyoux.realmsofchaos.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.inventivetalent.glow.GlowAPI;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;

public class CooldownsRelated implements Listener{
    private RealmsOfChaos main;

	public CooldownsRelated() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		saveCooldowns(player);
	}
	
	@EventHandler
	public void onPlayerDies(PlayerDeathEvent event) {
		Player player = event.getEntity();
		saveCooldowns(player);
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		setCooldowns(player);
		for (Object p : Bukkit.getServer().getOnlinePlayers().toArray())
			if (p instanceof Player)
				GlowAPI.setGlowing(((Player)p), false, player);
	}

	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		setCooldowns(player);
	}
	
	private void saveCooldowns(Player player) {
		PlayerInventory inv = player.getInventory();
		PersistentDataContainer container = player.getPersistentDataContainer();
		ItemStack[] items = inv.getContents();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getType().toString().contains("_EGG")) {
	    		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "CoolDown"+items[i].getType().toString());
	    		container.set(key, PersistentDataType.INTEGER, player.getCooldown(items[i].getType()));
			}
		}
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "CoolDownTP");
		container.set(key, PersistentDataType.INTEGER, player.getCooldown(Material.GREEN_WOOL));
	}
	
	private void setCooldowns(Player player) {
	    //Delay the update by a few ticks until the player is actually on the server
		Bukkit.getScheduler().runTaskLater(RealmsOfChaos.getInstance(), new Runnable() {
	        @Override
	        public void run() {
	    		player.setGravity(true);
	    		PlayerInventory inv = player.getInventory();
	    		PersistentDataContainer container = player.getPersistentDataContainer();
	    		ItemStack[] items = inv.getContents();
	    		for (int i = 0; i < items.length; i++) {
	    			if (items[i] != null && items[i].getType().toString().contains("_EGG")) {
	    	    		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "CoolDown"+items[i].getType().toString());
	    	    		int cooldown = (container.has(key, PersistentDataType.INTEGER)) ? container.get(key, PersistentDataType.INTEGER) : -1;
	    	    		if (cooldown != -1)
	    	    			player.setCooldown(items[i].getType(), cooldown);
	    			}
	    		}
	    		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "CoolDownTP");
	    		int cooldown = (container.has(key, PersistentDataType.INTEGER)) ? container.get(key, PersistentDataType.INTEGER) : -1;
	    		if (cooldown != -1) {
	    			player.setCooldown(Material.GREEN_WOOL, cooldown);
	    		}
	        }
	    }, 10);
	}
}
