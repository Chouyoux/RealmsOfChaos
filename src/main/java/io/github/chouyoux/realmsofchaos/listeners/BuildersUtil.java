package io.github.chouyoux.realmsofchaos.listeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;

public class BuildersUtil implements Listener{
	
    private RealmsOfChaos main;
    
    private HashMap<Player, Location> leftClicks;

	public BuildersUtil() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
        this.leftClicks = new HashMap<Player, Location>();
	}
	
	/*@EventHandler
	public void onRightClickBanner(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getItem() != null) return;
		if (!(event.getClickedBlock().getState() instanceof Banner)) return;
		if (!(RoCPlayers.getClass(event.getPlayer()).equals("Builder"))) return;

		Banner banner = (Banner) event.getClickedBlock().getState();
		if (banner != null) {
			for (Pattern pattern : banner.getPatterns())
				Bukkit.getServer().broadcastMessage("Color : " + pattern.getColor().toString() + " | Pattern : " + pattern.getPattern().toString());
		}
			
	}*/
	
	@EventHandler
	public void onRightClickWoodSword(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK) return;
		if (event.getItem() == null) return;
		if (event.getItem().getType() != Material.WOODEN_SWORD) return;
		Player player = event.getPlayer();
		if (!(RoCPlayers.getClass(player).equals("Builder"))) return;

		Location loc = event.getClickedBlock().getLocation();
		
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			this.leftClicks.put(player, loc);
			event.getPlayer().sendMessage("Distance calc anchor point set to X : " + loc.getX() + ", Y : " + loc.getY() + ", Z : " + loc.getZ());
		}
		
		else if (leftClicks.get(player) != null){
			Location loc_diff = loc.clone().subtract(leftClicks.get(player));
			event.getPlayer().sendMessage("Distance from anchor point X : " + loc_diff.getX() + ", Y : " + loc_diff.getY() + ", Z : " + loc_diff.getZ());
		}
	}

}
