package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.World;
import org.bukkit.entity.Player;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("musttargettrait")
public class MustTargetTrait extends Trait {

	RealmsOfChaos plugin = null;

    public MustTargetTrait() {
        super("musttargettrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
    }

    @Override
    public void run() {
        if (!npc.isSpawned()) return;
        if (npc.getNavigator().getEntityTarget() != null) return;
        
        World npc_world = npc.getEntity().getLocation().getWorld();
        double min_distance = Double.MAX_VALUE;
        Player target = null;
        for (Player player : npc_world.getPlayers()) {
        	double distance = player.getLocation().distance(npc.getEntity().getLocation());
        	if (distance < min_distance) {
        		min_distance = distance;
        		target = player;
        	}
        }
        if (target != null)
        	npc.getNavigator().setTarget(target, true);
    }
}