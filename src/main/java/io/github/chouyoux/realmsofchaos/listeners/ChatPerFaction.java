package io.github.chouyoux.realmsofchaos.listeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.GradesRuleset;

public class ChatPerFaction implements Listener{
    private RealmsOfChaos main;

	public ChatPerFaction() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if (RoCPlayers.getClass(player).equals("Builder")) return;
		if (RoCPlayers.getFaction(player).equals("")) return;
		if (player.isOp()) return;
		event.setCancelled(true);
		
		
		String msg_color = FactionRuleset.factionChatMsgColors.get(RoCPlayers.getFaction(player));
		String name_color = FactionRuleset.factionChatNameColors.get(RoCPlayers.getFaction(player));
		
		for (Player other : Bukkit.getServer().getOnlinePlayers()) {
			if (RoCPlayers.getFaction(player).equals(RoCPlayers.getFaction(other)) || other.isOp())
				other.sendMessage(name_color+ GradesRuleset.grades.get(player.getLevel())+" "+event.getPlayer().getName()+"§f > "+msg_color+event.getMessage());
		}
		
	}

} 