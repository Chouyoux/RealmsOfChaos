package io.github.chouyoux.realmsofchaos.listeners;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.challenge.Challenge;
import io.github.chouyoux.realmsofchaos.challenge.ChallengeEntrance;
import io.github.chouyoux.realmsofchaos.challenge.ChallengesList;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.memory.Factions;
import io.github.chouyoux.realmsofchaos.objects.Faction;

public class ChallengeEvents implements Listener {
	
    private RealmsOfChaos main;

	public ChallengeEvents() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onRightClickJoinCreateButton(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getClickedBlock() == null) return;
		if (!event.getClickedBlock().getType().toString().contains("BUTTON")) return;
		if (!event.getClickedBlock().getLocation().getWorld().getName().toLowerCase().contains("realms")) return;
		Location clickLoc = event.getClickedBlock().getLocation();
		Player player = event.getPlayer();
		
		for (ChallengeEntrance challengeEntrance : ChallengesList.getList()) {
			String faction = RoCPlayers.getFaction(player);
			String region_faction = ChallengesList.getChallengeEntranceRegion(challengeEntrance).getFaction();
			if (faction.equals(region_faction)) {
				if ((challengeEntrance.createLoc.distance(clickLoc) < 1 || challengeEntrance.joinLoc.distance(clickLoc) < 1) &&
						challengeEntrance.cooldowns.containsKey(player.getUniqueId()) && challengeEntrance.cooldowns.get(player.getUniqueId()) > 0)
					player.sendMessage(ChatColor.RED+"You still have "+new DecimalFormat("##.##").format(challengeEntrance.cooldowns.get(player.getUniqueId())/3600)+" hours of cooldown on " + challengeEntrance.name + ".");
				else if (challengeEntrance.createLoc.distance(clickLoc) < 1)
					challengeEntrance.createChallenge(player);
				else if (challengeEntrance.joinLoc.distance(clickLoc) < 1)
					challengeEntrance.sendGroupListToPlayer(player);
			}
			else if (challengeEntrance.createLoc.distance(clickLoc) < 1 || challengeEntrance.joinLoc.distance(clickLoc) < 1)
				player.sendMessage(ChatColor.RED+"Your faction must control "+ChallengesList.getChallengeEntranceRegion(challengeEntrance).getDisplayName()+" to run this Challenge.");
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onRightClickStartButton(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getClickedBlock() == null) return;
		if (!event.getClickedBlock().getType().toString().contains("BUTTON")) return;
		if (event.getClickedBlock().getLocation().getWorld().getName().toLowerCase().contains("realms")) return;
		Location clickLoc = event.getClickedBlock().getLocation();
		Player player = event.getPlayer();
		
		Challenge challenge = ChallengesList.getPlayerChallenge(player);
		
		if (challenge == null) return;
		if (challenge.getStartLoc().distance(clickLoc) > 1) return;
		
		if (!challenge.getPlayers().get(0).getUniqueId().equals(player.getUniqueId()))
			player.sendMessage(ChatColor.RED+"Only "+challenge.getPlayers().get(0).getName()+" can start the Challenge.");
		
		else if (!challenge.isStarted())
			challenge.start();
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		if (RoCPlayers.getFaction(player).equals("")) return;
		if (ChallengesList.getPlayerChallenge(player) == null) return;
		
		Faction pl_faction = Factions.factions.get(RoCPlayers.getFaction(player));
		player.teleport(pl_faction.getSpawn());
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof Player) return;
		Challenge challenge = ChallengesList.getMapChallenge(event.getEntity().getLocation().getWorld());
		if (challenge == null) return;
		challenge.setKills(challenge.getKills()+1);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) return;
		if (event.isCancelled()) return;
		
		LivingEntity victim = (LivingEntity) event.getEntity();
		Challenge challenge = ChallengesList.getMapChallenge(event.getEntity().getLocation().getWorld());
		if (challenge == null) return;
		
		if (victim instanceof Player && !(event.getDamager() instanceof Player))
			challenge.setDamageTaken(challenge.getDamageTaken()+event.getDamage());
		else if (!(victim instanceof Player))
			challenge.setDamageDealt(challenge.getDamageDealt()+event.getDamage());
			
	}
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) return;
		
		LivingEntity victim = (LivingEntity) event.getEntity();
		Challenge challenge = ChallengesList.getMapChallenge(event.getEntity().getLocation().getWorld());
		if (challenge == null) return;
		
		if (victim instanceof Player)
			challenge.setHealDealt(challenge.getDamageTaken()+event.getAmount());
			
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Challenge challenge = ChallengesList.getPlayerChallenge(event.getEntity());
		if (challenge == null) return;
		challenge.setDeathCount(challenge.getDeathCount()+1);
		
		if (challenge.getLives() > 0)
			challenge.setLives(challenge.getLives()-1);
		else
			event.getEntity().setGameMode(GameMode.SPECTATOR);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Challenge challenge = ChallengesList.getPlayerChallenge(event.getPlayer());
		if (challenge == null) return;

		event.setRespawnLocation(challenge.getStartLoc());
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent event)
	{
	    if (event.getPlayer().getGameMode() != GameMode.SPECTATOR) return;
	    if (!event.getTo().getBlock().isPassable()) event.setCancelled(true);
	}
	
}
