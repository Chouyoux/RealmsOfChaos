package io.github.chouyoux.realmsofchaos;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcmonkey.sentinel.SentinelPlugin;
import org.mcmonkey.sentinel.integration.SentinelRoC;

import de.slikey.effectlib.EffectManager;
import io.github.chouyoux.realmsofchaos.challenge.ChallengesList;
import io.github.chouyoux.realmsofchaos.commands.*;
import io.github.chouyoux.realmsofchaos.custom_IAs.*;
import io.github.chouyoux.realmsofchaos.data_handlers.MongoDatabase;
import io.github.chouyoux.realmsofchaos.data_handlers.YMLFiles;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCGod;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.listeners.*;
import io.github.chouyoux.realmsofchaos.memory.*;
import io.github.chouyoux.realmsofchaos.quest.QuestRegistry;
import io.github.chouyoux.realmsofchaos.ruleset.*;
import io.github.chouyoux.realmsofchaos.ticks.NPCTick;
import io.github.chouyoux.realmsofchaos.ticks.PlayersTick;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;

public class RealmsOfChaos extends JavaPlugin {
    public static RealmsOfChaos instance;
    public static String mainWorld = "Realms";
    public static EffectManager effectManager;

	public static RealmsOfChaos getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        effectManager = new EffectManager(this);

        // Files handling
        new YMLFiles();
        YMLFiles.setup("world_state");
        
        // Util listeners
        new BuildersUtil(); //not game related (for devs)
        
        // Rules listeners
        new ForceRessourcePack();
        new ChatPerFaction();
        new InventoryLocks();
        new BuildLocks();
        
        // Gameplay listeners
        new Player_Connect();
        new ProjectilesHandler();
        new SkillItemsUsage();
        new SkillEffects();
        new CooldownsRelated();
        
        // NPCs
        new NPCsEvent();
        SentinelPlugin.instance.registerIntegration(new SentinelRoC());
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(MustTargetTrait.class).withName("musttargettrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(FollowEntityTrait.class).withName("followentitytrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(AntalockTrait.class).withName("antalocktrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(BuldavaxTrait.class).withName("buldavaxtrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(GurafarTrait.class).withName("gurafartrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(GaraminTrait.class).withName("garamintrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(ElabusTrait.class).withName("elabustrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(AzuthotTrait.class).withName("azuthottrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(BerociusTrait.class).withName("berociustrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(KhrokedaiTrait.class).withName("khrokedaitrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(KredekaiTrait.class).withName("kredekaitrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(ZarossTrait.class).withName("zarosstrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(SkylekTrait.class).withName("skylektrait"));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(GuronTrait.class).withName("gurontrait"));
        
        // Ruleset
        new Ruleset();
        new GradesRuleset();
        new FactionRuleset();
        new EnchantRuleset();
        new DroppableRuleset();
        new MaterialNamesRuleset();
        new RegionIconsRuleset();
        new PatrollersRuleset();
        new ChatRuleset();
        
        // Mongo
        new MongoDatabase();
        new Structures();
        new Regions();
        new Factions();
        getLogger().info(Structures.structures.toString());
        getLogger().info(Regions.regions.toString());
        getLogger().info(Factions.factions.toString());
        
        // Challenge
        new ChallengesList();
        new ChallengeEvents();
        
        // Quests
		new QuestRegistry();
        new QuestEvents();

        // Commands handling
        getCommand("challengestats").setExecutor(new ChallengeStats());
        getCommand("accept").setExecutor(new Accept());
        getCommand("join").setExecutor(new Join());
        getCommand("get").setExecutor(new Get());
        getCommand("set").setExecutor(new Set());
        getCommand("tuto").setExecutor(new Tuto());
        getCommand("roclear").setExecutor(new Roclear());
        getCommand("it").setExecutor(new It());
        getCommand("chaos").setExecutor(new Chaos());
        getCommand("cd").setExecutor(new CD());
        
        // Cleaning entities
        Bukkit.getScheduler().runTaskLater(this, () -> {
        	boolean loaded = false;
        	while (!loaded)
	        	for(World w: Bukkit.getServer().getWorlds())
	        	{
	        	  if(w.getName().equals("Realms"))
	        	  {
	        	    loaded = true;
	        	    break;
	        	  }
	        	}

        	CitizensAPI.getNPCRegistry().deregisterAll();
        	RoCNPC.spawnNPCs();
        	RoCGod.spawn();
        	Regions.updateBanner();
        	Structures.updateBanners();
            new PlayersTick();
            new NPCTick();
        }, 1);

        getLogger().info("Realms of Chaos has started !");
    }

    @Override
    public void onDisable() {
    	effectManager.dispose();
		Factions.killNPCs();
		Structures.killNPCs();
		String command = "kill @e[type=!minecraft:player]";
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
        getLogger().info("Realms of Chaos has stopped !");
    }
}
