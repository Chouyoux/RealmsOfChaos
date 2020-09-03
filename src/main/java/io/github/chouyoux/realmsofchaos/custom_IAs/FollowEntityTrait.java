package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.CustomAI;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("followentitytrait")
public class FollowEntityTrait extends Trait {
	
    private CustomAI followedNpc;

	RealmsOfChaos plugin = null;

    public FollowEntityTrait() {
        super("followentitytrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
    }

    public boolean isActive() {
        return npc.isSpawned() && followedNpc != null && followedNpc.getEntity() != null && npc.getEntity().getWorld().equals(followedNpc.getEntity().getWorld());
    }
    
    @Override
    public void onSpawn() {
        if (isActive() && followedNpc.getNpc().isSpawned()) {
        	npc.teleport(followedNpc.getEntity().getLocation(), TeleportCause.PLUGIN);
        }
    }

    @Override
    public void run() {
        if (!isActive() || !followedNpc.getNpc().isSpawned()) {
            return;
        }
        Entity entity = followedNpc.getEntity();
        if (followedNpc.getNpc().getNavigator().getEntityTarget() != null && followedNpc.getNpc().getNavigator().getEntityTarget().getTarget() != null) {
            npc.getNavigator().setTarget(followedNpc.getNpc().getNavigator().getEntityTarget().getTarget(), true);
        }
        else if (!npc.getNavigator().isNavigating()) {
            npc.getNavigator().setTarget(entity, false);
        }
    }

    public void setFollowedEntity(CustomAI followedNpc) {
        this.followedNpc = followedNpc;
        if (npc.getNavigator().isNavigating() && this.followedNpc.getEntity() != null && npc.getNavigator().getEntityTarget() != null
                && this.followedNpc.getEntity() == npc.getNavigator().getEntityTarget().getTarget()) {
            npc.getNavigator().cancelNavigation();
        }
    }
}
