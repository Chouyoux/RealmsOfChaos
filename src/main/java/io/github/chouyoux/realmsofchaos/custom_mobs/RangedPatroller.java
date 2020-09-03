package io.github.chouyoux.realmsofchaos.custom_mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import io.github.chouyoux.realmsofchaos.custom_IAs.FollowEntityTrait;

public class RangedPatroller extends Patroller {
	
	private CustomAI leader;

	public RangedPatroller(Location spawnPoint, String faction, String structure, CustomAI leader) {
		super(0.40, 6, 30, 30, 20*60, spawnPoint, EntityType.PILLAGER, "Ranged Patroller", faction, Material.CROSSBOW, structure);
		this.leader = leader;
		if (!getNpc().hasTrait(FollowEntityTrait.class)) {
			getNpc().addTrait(FollowEntityTrait.class);
        }
		FollowEntityTrait follow = getNpc().getTrait(FollowEntityTrait.class);
		follow.setFollowedEntity(leader);
	}

	public CustomAI getLeader() {
		return leader;
	}

	public void setLeader(CustomAI leader) {
		this.leader = leader;
	}

}