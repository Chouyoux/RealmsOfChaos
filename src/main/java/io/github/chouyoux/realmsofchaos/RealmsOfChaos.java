package io.github.chouyoux.realmsofchaos;

import io.github.chouyoux.realmsofchaos.commands.*;
import io.github.chouyoux.realmsofchaos.data_handlers.MongoDatabase;
import io.github.chouyoux.realmsofchaos.data_handlers.YMLFiles;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import io.github.chouyoux.realmsofchaos.listeners.BuildLocks;
import io.github.chouyoux.realmsofchaos.listeners.InventoryLocks;
import io.github.chouyoux.realmsofchaos.listeners.Player_Connect;
import io.github.chouyoux.realmsofchaos.listeners.ProjectilesHandler;
import io.github.chouyoux.realmsofchaos.memory.Factions;
import io.github.chouyoux.realmsofchaos.memory.Regions;
import io.github.chouyoux.realmsofchaos.memory.Structures;
import io.github.chouyoux.realmsofchaos.ruleset.Ruleset;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class RealmsOfChaos extends JavaPlugin {
    private static RealmsOfChaos instance;

    public static RealmsOfChaos getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        //Files handling
        new YMLFiles();
        YMLFiles.setup("world_state");

        //Listeners handling
        new Player_Connect();
        new ProjectilesHandler();
        new InventoryLocks();
        new BuildLocks();
        
        //Ruleset
        new Ruleset();
        
        //Mongo
        new MongoDatabase();
        new Structures();
        new Regions();
        new Factions();
        getLogger().info(Structures.structures.toString());
        getLogger().info(Regions.regions.toString());
        getLogger().info(Factions.factions.toString());

        //Commands handling
        getCommand("message").setExecutor(new Message());
        getCommand("setmessage").setExecutor(new SetMessage());
        getCommand("setstr").setExecutor(new SetSTR());
        getCommand("setdex").setExecutor(new SetDEX());
        getCommand("setvit").setExecutor(new SetVIT());
        getCommand("roclear").setExecutor(new Roclear());
        getCommand("classleave").setExecutor(new Classleave());
        getCommand("classjoin").setExecutor(new Classjoin());
        getCommand("classinfo").setExecutor(new Classinfo());
        getCommand("factionleave").setExecutor(new FactionLeave());
        getCommand("factionjoin").setExecutor(new FactionJoin());
        getCommand("factioninfo").setExecutor(new FactionInfo());
        
        //Cleaning entities
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
        	RoCNPC.spawnNPCs();
        }, 1);

        getLogger().info("Realms of Chaos has started !");
    }

    @Override
    public void onDisable() {
		Factions.killNPCs();
		Structures.killNPCs();
        getLogger().info("Realms of Chaos has stopped !");
    }
}
