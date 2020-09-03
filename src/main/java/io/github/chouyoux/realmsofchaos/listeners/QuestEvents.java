package io.github.chouyoux.realmsofchaos.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.quest.*;

public class QuestEvents implements Listener {
	
    private RealmsOfChaos main;

	public QuestEvents() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		QuestRegistry.instance.onJoin(event.getPlayer());
	}
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if (killer == null) return;
		
		for (Quest quest : QuestRegistry.quests) {
			if (quest instanceof QuestKillMonsters) {
				QuestKillMonsters killmQuest = (QuestKillMonsters) quest;
				if (killmQuest.hasPlayer(killer) && killmQuest.monster.equals(event.getEntity().getType())) {
					killmQuest.addProgress(killer, 1);
				}
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		Player victim = event.getEntity();
		Player killer = event.getEntity().getKiller();
		if (killer == null) return;
		
		for (Quest quest : QuestRegistry.quests) {
			if (quest instanceof QuestKillPlayers) {
				QuestKillPlayers killpQuest = (QuestKillPlayers) quest;
				if (killpQuest.hasPlayer(killer) && killpQuest.grade <= victim.getLevel()) {
					killpQuest.addProgress(killer, 1);
				}
			}
		}
		
	}
	
}
