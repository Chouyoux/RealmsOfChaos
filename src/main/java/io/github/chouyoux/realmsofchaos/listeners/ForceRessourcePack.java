package io.github.chouyoux.realmsofchaos.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;

public class ForceRessourcePack  implements Listener {
	
	private RealmsOfChaos main;

	public ForceRessourcePack() {
		this.main = RealmsOfChaos.getInstance();
	    main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerConnect(PlayerResourcePackStatusEvent event) {
		Player p = event.getPlayer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Downloading");
		PersistentDataContainer container = p.getPersistentDataContainer();
		if (event.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED){
			container.set(key, PersistentDataType.INTEGER, 1);
			BukkitRunnable stop_dl = new  BukkitRunnable() {
		    	@Override
		        public void run() {
					container.remove(key);
		    	}
		    };
		    stop_dl.runTaskLater(main, 20*30);
		}
		else if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED){
			event.getPlayer().kickPlayer("You declined Realms of Chaos' resource pack which is required.\n\n To enable the resource pack, select the Realms of Chaos in your server list, click \"Edit\", and set \"Server Resource Packs\" to \"Enabled\" or \"Prompt\".");
		}
		else if (event.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD && !container.has(key, PersistentDataType.INTEGER)){
			event.getPlayer().kickPlayer("You declined Realms of Chaos' resource pack which is required.\n\n To enable the resource pack, select the Realms of Chaos in your server list, click \"Edit\", and set \"Server Resource Packs\" to \"Enabled\" or \"Prompt\".");
		}
	}
	
}
