package io.github.chouyoux.realmsofchaos.listeners;
import static io.github.chouyoux.realmsofchaos.RealmsOfChaos.getInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.gameplay.skills.BouncingLance;
import io.github.chouyoux.realmsofchaos.gameplay.skills.Fire;
import io.github.chouyoux.realmsofchaos.gameplay.skills.FireBomb;
import io.github.chouyoux.realmsofchaos.gameplay.skills.NaturesAnger;
import io.github.chouyoux.realmsofchaos.gameplay.skills.ParalysingPoison;
import io.github.chouyoux.realmsofchaos.gameplay.skills.Poison;
import io.github.chouyoux.realmsofchaos.gameplay.skills.RainOfArrows;
import io.github.chouyoux.realmsofchaos.gameplay.skills.SlowingStance;
import io.github.chouyoux.realmsofchaos.particle_effects.SplitDamageEffect;
import io.github.chouyoux.realmsofchaos.util.UtilFuncs;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;

public class SkillEffects implements Listener{
    private RealmsOfChaos main;

	public SkillEffects() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	// Golem Shape
	
	@EventHandler
	public void onGolemHit(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity attacker = event.getDamager();
		
		Disguise disguise = DisguiseAPI.getDisguise(attacker);
		
		if (disguise == null) return;
		if (disguise.getType() != DisguiseType.IRON_GOLEM) return;
		
		Bukkit.getServer().broadcastMessage(disguise.getType().toString());
		victim.setVelocity(new Vector(0, 5, 0));
	}
	
	// Fire Touch
	@EventHandler
	public void onFireTouchHit(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity attacker = event.getDamager();
		
		if (RoCNPC.sameFaction(victim, attacker)) return;
		
		PersistentDataContainer container = attacker.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "NextHitEffect");
		
		if (!(container.has(key, PersistentDataType.STRING))) return;
		if (!(container.get(key, PersistentDataType.STRING).equals("Fire"))) return;
		
		container.remove(key);
		int duration = SlowingStance.skillEffectDuration;
		if (event.getEntity() instanceof Player)
			duration *= RoCPlayers.getEffectDurationReduction(((Player) event.getEntity()));
		((LivingEntity) victim).setFireTicks(duration);
	}
	
	// Slowing Stance
	@EventHandler
	public void onSlowingStanceHit(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity attacker = event.getDamager();
		
		if (RoCNPC.sameFaction(victim, attacker)) return;
		
		PersistentDataContainer container = attacker.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "NextHitEffect");
		
		if (!(container.has(key, PersistentDataType.STRING))) return;
		if (!(container.get(key, PersistentDataType.STRING).equals("Slow"))) return;
		
		container.remove(key);
		int duration = SlowingStance.skillEffectDuration;
		if (event.getEntity() instanceof Player)
			duration *= RoCPlayers.getEffectDurationReduction(((Player) event.getEntity()));
		((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 1));
	}
	
	// Demon Stance
	@EventHandler
	public void onDemonStanceHit(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity attacker = event.getDamager();
		
		if (RoCNPC.sameFaction(victim, attacker)) return;
			
		PersistentDataContainer container = attacker.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "NextHitEffect");
			
		if (!(container.has(key, PersistentDataType.STRING))) return;
		if (!(container.get(key, PersistentDataType.STRING).equals("Lifesteal"))) return;
			
		container.remove(key);
		((Damageable) attacker).setHealth(Math.min(((Attributable) attacker).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue(), ((Damageable) attacker).getHealth()+(event.getDamage())));
	}
		
	// Stunning Stance
	@EventHandler
	public void onStunningStanceHit(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity attacker = event.getDamager();
		
		if (RoCNPC.sameFaction(victim, attacker)) return;
			
		PersistentDataContainer container = attacker.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "NextHitEffect");
			
		if (!(container.has(key, PersistentDataType.STRING))) return;
		if (!(container.get(key, PersistentDataType.STRING).equals("Nausea"))) return;
			
		container.remove(key);
		int duration = SlowingStance.skillEffectDuration;
		if (event.getEntity() instanceof Player)
			duration *= RoCPlayers.getEffectDurationReduction(((Player) event.getEntity()));
		((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, 1));
	}

	// Arcane Ward Explode Prevention
	@EventHandler
	public void onArcaneWardExplode(EntityExplodeEvent event) {
		if (event.getEntity().getType() != EntityType.ENDER_CRYSTAL) return;
		
		PersistentDataContainer container = event.getEntity().getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "ArcaneWard");
		if (!(container.has(key, PersistentDataType.INTEGER))) return;
		
		event.setCancelled(true);
		
		event.getLocation().getWorld().createExplosion(event.getLocation(), 2F, false, false);
		
	}
	
	// Arcane Ward Explode Prevention
	@EventHandler
	public void onArcaneWardExplodeDmg(EntityDamageByEntityEvent event) {
		if (event.getCause() != DamageCause.ENTITY_EXPLOSION) return;
		if (event.getDamager().getType() != EntityType.ENDER_CRYSTAL) return;

		PersistentDataContainer container = event.getDamager().getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "ArcaneWard");
		if (!(container.has(key, PersistentDataType.INTEGER))) return;
			
		event.setCancelled(true);
	}
	
	// Fireball
	@EventHandler
	public void onFireBallHit(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Fireball)) return;
		ProjectileSource real_attacker = ((Projectile) event.getDamager()).getShooter();
		
		PersistentDataContainer container = event.getDamager().getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "FireBall");
		if (!(container.has(key, PersistentDataType.INTEGER))) return;
		
		if (!(real_attacker instanceof Player)) return;
		Player attacker = (Player) real_attacker;
		
		if (RoCNPC.sameFaction((LivingEntity) event.getEntity(), (LivingEntity) attacker)) {
			event.setCancelled(true);
			return;
		}
		
		event.setDamage(io.github.chouyoux.realmsofchaos.gameplay.skills.Fireball.skillEffectValue*RoCPlayers.getMagicalDMG(attacker));
		
		int duration = io.github.chouyoux.realmsofchaos.gameplay.skills.Fireball.skillEffectDuration;
		if (event.getEntity() instanceof Player)
			duration *= RoCPlayers.getEffectDurationReduction(((Player) event.getEntity()));
		event.getEntity().setFireTicks(duration);
	}
	
	// Firebomb
	@EventHandler
	public void onFireBombHit(EntityDamageByEntityEvent event) {
		if (event.getCause() != DamageCause.ENTITY_EXPLOSION) return;
		if (!(event.getDamager() instanceof Player || event.getDamager() instanceof Fireball)) return;
		if (!(event.getEntity() instanceof LivingEntity)) return;
			
		PersistentDataContainer container = event.getDamager().getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.instance, "FireBomb");
			
		Player source = null;
			
		if (event.getDamager() instanceof Fireball) {
			if (!(container.has(key, PersistentDataType.INTEGER))) return;
			source = (Player) ((Fireball) event.getDamager()).getShooter();
		}
		else
			source = (Player) event.getDamager();
		LivingEntity victim = (LivingEntity) event.getEntity();
		if (RoCNPC.sameFaction((LivingEntity) source, victim)) {
			event.setCancelled(true);
			return;
		}
		event.setDamage(FireBomb.skillEffectValue * RoCPlayers.getMagicalDMG(source));
		int duration = FireBomb.skillEffectDuration;
		if (event.getEntity() instanceof Player)
			duration *= RoCPlayers.getEffectDurationReduction(((Player) event.getEntity()));
		victim.setFireTicks(duration);
	}
	
	// Nature's Anger
	@EventHandler
	public void onFangsHit(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof EvokerFangs)) return;
		EvokerFangs fangs = (EvokerFangs) event.getDamager();
		Player player = ((Player) fangs.getOwner());
		event.setDamage(NaturesAnger.skillEffectValue*RoCPlayers.getMagicalDMG(player));
	}
	
	// Poison Shoot
	@EventHandler
	public void onPoisonShot(ProjectileLaunchEvent event) {
		if (!(event.getEntity() instanceof Arrow || event.getEntity() instanceof Trident)) return;
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		Player player = (Player) event.getEntity().getShooter();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
		PersistentDataContainer container = player.getPersistentDataContainer();
		if (container.has(key, PersistentDataType.STRING) && container.get(key, PersistentDataType.STRING).equals("Poison")) {
			PersistentDataContainer proj_container = event.getEntity().getPersistentDataContainer();
			proj_container.set(key, PersistentDataType.STRING, "Poison");
			container.remove(key);
		}
	}
	
	// Poison Land
	@EventHandler
	public void onPoisonHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow || event.getEntity() instanceof Trident)) return;
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
		PersistentDataContainer proj_container = event.getEntity().getPersistentDataContainer();
		if (proj_container.has(key, PersistentDataType.STRING) && proj_container.get(key, PersistentDataType.STRING).equals("Poison")) {
			if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity) {
				int duration = Poison.skillEffectDuration;
				if (event.getHitEntity() instanceof Player)
					duration *= RoCPlayers.getEffectDurationReduction(((Player) event.getHitEntity()));
				if (!(RoCNPC.sameFaction((LivingEntity) event.getHitEntity(), (LivingEntity) event.getEntity().getShooter())))
					((LivingEntity) event.getHitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration, 1, true, true, true));
			}
		}
	}
	
	// Slow Shoot
	@EventHandler
	public void onSlowShot(ProjectileLaunchEvent event) {
		if (!(event.getEntity() instanceof Arrow || event.getEntity() instanceof Trident)) return;
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		Player player = (Player) event.getEntity().getShooter();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
		PersistentDataContainer container = player.getPersistentDataContainer();
		if (container.has(key, PersistentDataType.STRING) && container.get(key, PersistentDataType.STRING).equals("Slow")) {
			PersistentDataContainer proj_container = event.getEntity().getPersistentDataContainer();
			proj_container.set(key, PersistentDataType.STRING, "Slow");
			container.remove(key);
		}
	}
		
	// Slow Land
	@EventHandler
	public void onSlowHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow || event.getEntity() instanceof Trident)) return;
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
		PersistentDataContainer proj_container = event.getEntity().getPersistentDataContainer();
		if (proj_container.has(key, PersistentDataType.STRING) && proj_container.get(key, PersistentDataType.STRING).equals("Slow")) {
			if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity) {
				int duration = ParalysingPoison.skillEffectDuration;
				if (event.getHitEntity() instanceof Player)
					duration *= RoCPlayers.getEffectDurationReduction(((Player) event.getHitEntity()));
				if (!(RoCNPC.sameFaction((LivingEntity) event.getHitEntity(), (LivingEntity) event.getEntity().getShooter())))
					((LivingEntity) event.getHitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 2, true, true, true));
			}
		}
	}
	
	// Slow Land
		@EventHandler
		public void onSlowSoftHit(ProjectileHitEvent event) {
			if (!(event.getEntity() instanceof Arrow || event.getEntity() instanceof Trident)) return;
			if (!(event.getEntity().getShooter() instanceof Player)) return;
			NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
			PersistentDataContainer proj_container = event.getEntity().getPersistentDataContainer();
			if (proj_container.has(key, PersistentDataType.STRING) && proj_container.get(key, PersistentDataType.STRING).equals("SlowSoft")) {
				if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity) {
					int duration = ParalysingPoison.skillEffectDuration;
					if (event.getHitEntity() instanceof Player)
						duration *= RoCPlayers.getEffectDurationReduction(((Player) event.getHitEntity()));
					if (!(RoCNPC.sameFaction((LivingEntity) event.getHitEntity(), (LivingEntity) event.getEntity().getShooter())))
						((LivingEntity) event.getHitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 1, true, true, true));
				}
			}
		}
	
	// Fire Shoot
	@EventHandler
	public void onFireShot(ProjectileLaunchEvent event) {
		if (!(event.getEntity() instanceof Arrow || event.getEntity() instanceof Trident)) return;
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		Player player = (Player) event.getEntity().getShooter();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
		PersistentDataContainer container = player.getPersistentDataContainer();
		if (container.has(key, PersistentDataType.STRING) && container.get(key, PersistentDataType.STRING).equals("Fire")) {
			PersistentDataContainer proj_container = event.getEntity().getPersistentDataContainer();
			proj_container.set(key, PersistentDataType.STRING, "Fire");
			container.remove(key);
		}
	}
		
	// Fire Land
	@EventHandler
	public void onFireHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow || event.getEntity() instanceof Trident)) return;
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
		PersistentDataContainer proj_container = event.getEntity().getPersistentDataContainer();
		if (proj_container.has(key, PersistentDataType.STRING) && proj_container.get(key, PersistentDataType.STRING).equals("Fire")) {
			if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity) {
				int duration = Fire.skillEffectDuration;
				if (event.getHitEntity() instanceof Player)
					duration *= RoCPlayers.getEffectDurationReduction(((Player) event.getHitEntity()));
				if (!(RoCNPC.sameFaction((LivingEntity) event.getHitEntity(), (LivingEntity) event.getEntity().getShooter())))
					((LivingEntity) event.getHitEntity()).setFireTicks(duration);
			}
		}
	}
	
	// KB Shoot
	@EventHandler
	public void onKBShot(ProjectileLaunchEvent event) {
		if (!(event.getEntity() instanceof Arrow || event.getEntity() instanceof Trident)) return;
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		Player player = (Player) event.getEntity().getShooter();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
		PersistentDataContainer container = player.getPersistentDataContainer();
		if (container.has(key, PersistentDataType.STRING) && container.get(key, PersistentDataType.STRING).equals("KB")) {
			PersistentDataContainer proj_container = event.getEntity().getPersistentDataContainer();
			proj_container.set(key, PersistentDataType.STRING, "KB");
			container.remove(key);
		}
	}
			
	// KB Land
	@EventHandler
	public void onKBHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow || event.getEntity() instanceof Trident)) return;
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "ProjectileEffect");
		PersistentDataContainer proj_container = event.getEntity().getPersistentDataContainer();
		if (proj_container.has(key, PersistentDataType.STRING) && proj_container.get(key, PersistentDataType.STRING).equals("KB")) {
			if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity) {
				if (!(RoCNPC.sameFaction((LivingEntity) event.getHitEntity(), (LivingEntity) event.getEntity().getShooter()))) {
					Vector beetween = UtilFuncs.vectorBeetween(event.getHitEntity().getLocation(), ((Entity) event.getEntity().getShooter()).getLocation());
					beetween.normalize();
					beetween.multiply(-1);
					beetween.setY(0.14F);
					beetween.multiply(4);
					event.getHitEntity().setVelocity(beetween);
				}
			}
		}
	}
	
	// Infiltrator's Arrow
	@EventHandler
	public void onInfiltratorArrowHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow)) return;

		NamespacedKey key = new NamespacedKey(getInstance(), "Infiltrator");
	    PersistentDataContainer p_container = event.getEntity().getPersistentDataContainer();
	    if (!(p_container.has(key , PersistentDataType.STRING))) return;
			
	    if (!(event.getEntity().getShooter() instanceof Player)) return;
	        
	    Player player = ((Player) event.getEntity().getShooter());
	    player.teleport(event.getEntity().getLocation().setDirection(player.getLocation().getDirection()));
	    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1, 0.5F);
	    event.getEntity().remove();
	}
	
	//Camouflage end routine
	private void camouflageEnd(LivingEntity e) {
		PersistentDataContainer container = e.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Camouflage");
		container.remove(key);
		e.removePotionEffect(PotionEffectType.INVISIBILITY);
		Collection<? extends Player> online_players = Bukkit.getServer().getOnlinePlayers();
		for (Object p : online_players.toArray())
			if (p instanceof Player) {
				((Player) p).showPlayer(RealmsOfChaos.instance, (Player) e);
			}
	}
	
	//Camouflage end (damage)
	@EventHandler
	public void onDamageWithCamouflage(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity attacker = event.getDamager();
		
		if (attacker instanceof Projectile) {
			ProjectileSource real_attacker = ((Projectile) attacker).getShooter();
			attacker = (Entity) real_attacker;
		}
		
		if (!(victim instanceof Player && attacker instanceof Player)) return;
		
		if (RoCNPC.sameFaction((LivingEntity) victim, (LivingEntity) attacker)) return;
		
		PersistentDataContainer v_container = victim.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Camouflage");
		if (v_container.has(key, PersistentDataType.INTEGER) && v_container.get(key, PersistentDataType.INTEGER) == 1) {
			camouflageEnd((LivingEntity) victim);
		}
		
		PersistentDataContainer a_container = attacker.getPersistentDataContainer();
		if (a_container.has(key, PersistentDataType.INTEGER) && a_container.get(key, PersistentDataType.INTEGER) == 1) {
			camouflageEnd((LivingEntity) attacker);
		}
	}
	
	//Camouflage end (moving)
	@EventHandler
	public void onMoveWithCamouflage(PlayerMoveEvent event) {
		if (event.getFrom().distance(event.getTo()) < 0.1) return;
		Player player = event.getPlayer();
		
		PersistentDataContainer container = player.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Camouflage");
		if (container.has(key, PersistentDataType.INTEGER) && container.get(key, PersistentDataType.INTEGER) == 1) {
			camouflageEnd((LivingEntity) player);
		}
	}
	
	//Camouflage end (disconnect)
	@EventHandler
	public void onDisconnectWithCamouflage(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		PersistentDataContainer container = player.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Camouflage");
		if (container.has(key, PersistentDataType.INTEGER) && container.get(key, PersistentDataType.INTEGER) == 1) {
			camouflageEnd((LivingEntity) player);
		}
	}

	//Camouflage update (connect)
	@EventHandler
	public void onConnectWithCamouflage(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		NamespacedKey key = new NamespacedKey(RealmsOfChaos.getInstance(), "Camouflage");
		
		Collection<? extends Player> online_players = Bukkit.getServer().getOnlinePlayers();
		for (Object p : online_players.toArray())
			if (p instanceof Player && !RoCPlayers.sameFaction((Player) p, player)) {
				PersistentDataContainer container = ((Player) p).getPersistentDataContainer();
				if (container.has(key, PersistentDataType.INTEGER) && container.get(key, PersistentDataType.INTEGER) == 1)
					player.hidePlayer(RealmsOfChaos.instance, ((Player)p));
			}
	}
	
	// Rain of Arrows
	@EventHandler
	public void onRainArrowHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow)) return;

		NamespacedKey key = new NamespacedKey(getInstance(), "RainArrow");
		PersistentDataContainer container = event.getEntity().getPersistentDataContainer();
		if (!(container.has(key , PersistentDataType.STRING))) return;
				
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		        
		Player player = ((Player) event.getEntity().getShooter());
		container.remove(key);

		NamespacedKey key_effect = new NamespacedKey(getInstance(), "ProjectileEffect");
		
		String effect = "";
		if (container.has(key_effect, PersistentDataType.STRING))
			effect = container.get(key_effect, PersistentDataType.STRING);
		
		final String final_effect = effect;
		
		Location arrow_loc = event.getEntity().getLocation().clone();
		Location rain_loc = event.getEntity().getLocation().clone().add(0, 25, 0);
		ArrayList<Arrow> arrows = new ArrayList<Arrow>();
		BukkitRunnable rain = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		Vector to_ground = arrow_loc.clone().subtract(rain_loc).toVector();
	    		double plus_or_minus_x = -1D;
	    		double plus_or_minus_z = -1D;
	    		if (Math.random() > 0.5F)
	    			plus_or_minus_x = 1D;
	    		if (Math.random() > 0.5F)
	    			plus_or_minus_z = 1D;
	    		to_ground.setX(to_ground.getX()+(plus_or_minus_x*Math.random()*(RainOfArrows.skillRadius/2D)));
	    		to_ground.setZ(to_ground.getZ()+(plus_or_minus_z*Math.random()*(RainOfArrows.skillRadius/2D)));
	    		Arrow arrow = player.getWorld().spawnArrow(rain_loc, to_ground, 2F, 0.2F);
	    		PersistentDataContainer a_container = arrow.getPersistentDataContainer();
				if (final_effect != "")
					a_container.set(key_effect, PersistentDataType.STRING, final_effect);
	    		arrow.setShooter(player);
	    		arrow.setPickupStatus(PickupStatus.DISALLOWED);
	    		arrows.add(arrow);
	    	}
	    };
	    BukkitRunnable end_rain = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		rain.cancel();
	    	}
	    };
	    BukkitRunnable remove_arrows = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		for (Arrow arrow : arrows)
	    			arrow.remove();
	    	}
	    };
    	rain.runTaskTimer(main, 20, 1);
    	end_rain.runTaskLater(main, RainOfArrows.skillEffectDuration);
    	remove_arrows.runTaskLater(main, RainOfArrows.skillEffectDuration+20*4);
		event.getEntity().remove();
	}
	
	// Pulling Shot
	@EventHandler
	public void onPullingShotHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow)) return;
		if (event.getHitEntity() == null || !(event.getHitEntity() instanceof LivingEntity)) return;

		NamespacedKey key = new NamespacedKey(getInstance(), "PullingShot");
		PersistentDataContainer container = event.getEntity().getPersistentDataContainer();
		if (!(container.has(key , PersistentDataType.STRING))) return;

		if (!(event.getEntity().getShooter() instanceof Player)) return;
		Player player = ((Player) event.getEntity().getShooter());
		LivingEntity hit = (LivingEntity) event.getHitEntity();
		
		Vector beetween = UtilFuncs.vectorBeetween(hit.getLocation(), player.getLocation());
		beetween.normalize().multiply(5F);
		event.getHitEntity().setVelocity(beetween);
	}
	
	// Bouncing Lance
	@EventHandler
	public void onBouncingLanceHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Trident)) return;
		if (event.getHitEntity() == null || !(event.getHitEntity() instanceof LivingEntity)) return;

		NamespacedKey key = new NamespacedKey(getInstance(), "BouncingLance");
		NamespacedKey key_count = new NamespacedKey(getInstance(), "BouncingLanceCount");
		PersistentDataContainer container = event.getEntity().getPersistentDataContainer();
		if (!(container.has(key , PersistentDataType.STRING))) return;
		
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		Player player = ((Player) event.getEntity().getShooter());
		LivingEntity hit = (LivingEntity) event.getHitEntity();
		
		NamespacedKey key_effect = new NamespacedKey(getInstance(), "ProjectileEffect");

		String effect = "";

		if (container.has(key_effect, PersistentDataType.STRING))
			effect = container.get(key_effect, PersistentDataType.STRING);
		
		int count = 0;
		if (container.has(key_count, PersistentDataType.INTEGER))
			count = container.get(key_count, PersistentDataType.INTEGER);
		else {
			NamespacedKey key_target = new NamespacedKey(getInstance(), "Target0");
			container.set(key_target, PersistentDataType.STRING, event.getHitEntity().getUniqueId().toString());
		}
		
		count += 1;
		container.set(key_count, PersistentDataType.INTEGER, count);
		
		if (count < 4) {
			LivingEntity next_target = null;
			List<LivingEntity> nearby = UtilFuncs.getNearbyEntities(event.getEntity().getLocation(), BouncingLance.skillRadius);
			double min = Double.MAX_VALUE;
			for (LivingEntity en : nearby) {
				double distance = en.getLocation().distance(event.getEntity().getLocation());
				if (distance > 0.2F && distance < min && !(en.getUniqueId().equals(event.getHitEntity().getUniqueId())) && !RoCNPC.sameFaction(en, (LivingEntity) player) && UtilFuncs.canSee((LivingEntity) event.getHitEntity(), en)) {
					boolean has_already_been_targeted = false;
					for (int i = 0; i < 4; i++) {
						NamespacedKey key_target = new NamespacedKey(getInstance(), "Target"+i);
						if (container.has(key_target, PersistentDataType.STRING) && container.get(key_target, PersistentDataType.STRING).equals(en.getUniqueId().toString())) {
							has_already_been_targeted = true;
							break;
						}
					}
					if (has_already_been_targeted)
						continue;
					min = distance;
					next_target = en;
				}
			}
			
			if (next_target == null) return;
			
			final LivingEntity final_next_target = next_target;
			final int final_count = count;
			final String final_effect = effect;
			
			BukkitRunnable send_trident = new BukkitRunnable() {
		    	@Override
		        public void run() {
					Vector between = UtilFuncs.vectorBeetween(hit.getEyeLocation(), final_next_target.getEyeLocation()).normalize();
					Trident trident = (Trident) player.getWorld().spawnEntity(hit.getEyeLocation().add(between.clone().multiply(0.5F)), EntityType.TRIDENT);
					trident.setVelocity(between.multiply(2F));
					trident.setShooter(player);
					PersistentDataContainer t_container = trident.getPersistentDataContainer();
					t_container.set(key, PersistentDataType.STRING, player.getUniqueId().toString());
					t_container.set(key_count, PersistentDataType.INTEGER, final_count);
					for (int i = 0; i < final_count; i++) {
						NamespacedKey key_target_ =  new NamespacedKey(getInstance(), "Target"+i);
						if (container.has(key_target_, PersistentDataType.STRING))
							t_container.set(key_target_, PersistentDataType.STRING, container.get(key_target_, PersistentDataType.STRING));
					}
					NamespacedKey key_target = new NamespacedKey(getInstance(), "Target"+final_count);
					t_container.set(key_target, PersistentDataType.STRING, final_next_target.getUniqueId().toString());
					if (final_effect != "")
						t_container.set(key_effect, PersistentDataType.STRING, final_effect);
					event.getHitEntity().getWorld().playSound(hit.getLocation(), Sound.ITEM_TRIDENT_THROW, 0.25F, 0.5F);
		    	}
		    };
		    send_trident.runTaskLater(RealmsOfChaos.instance, 5);
		}
		
		event.getEntity().remove();
	}
	
	// Empathy
	@EventHandler
	public void onTakingDamageUnderEmpathy(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) return;
		LivingEntity e = (LivingEntity) event.getEntity();

		NamespacedKey key = new NamespacedKey(getInstance(), "Empathy");
        PersistentDataContainer p_container = e.getPersistentDataContainer();
        if (!(p_container.has(key , PersistentDataType.STRING) && p_container.get(key , PersistentDataType.STRING).compareTo("") != 0)) return;
        
        String p_foundvalue = p_container.get(key, PersistentDataType.STRING);
        Player source = Bukkit.getServer().getPlayer(p_foundvalue);
        
        new SplitDamageEffect(RealmsOfChaos.effectManager, event.getEntity(), source);
        event.setDamage(event.getDamage()/2);
        source.damage(event.getDamage()/2);
	}

}
