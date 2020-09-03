package io.github.chouyoux.realmsofchaos.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.Guard;
import io.github.chouyoux.realmsofchaos.custom_mobs.Patroller;
import net.citizensnpcs.api.event.NPCSpawnEvent;

public class NPCsEvent implements Listener{
    private RealmsOfChaos main;

	public NPCsEvent() {
		this.main = RealmsOfChaos.getInstance();
        main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onNPCSpawn(NPCSpawnEvent event) {
		Guard guard = Guard.getGuard(event.getNPC());
		if (guard != null) {
			guard.setFaction(guard.getFaction());
			guard.setWeapon(guard.getWeapon());
		}
		

		Patroller patroller = Patroller.getPatroller(event.getNPC());
		if (patroller != null) {
			patroller.setFaction(patroller.getFaction());
			patroller.setWeapon(patroller.getWeapon());
		}
	}
	
	

}
