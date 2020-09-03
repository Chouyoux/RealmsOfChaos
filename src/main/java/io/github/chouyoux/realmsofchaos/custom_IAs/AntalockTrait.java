package io.github.chouyoux.realmsofchaos.custom_IAs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("antalocktrait")
public class AntalockTrait extends Trait {

	RealmsOfChaos plugin = null;

    public AntalockTrait() {
        super("antalocktrait");
		plugin = RealmsOfChaos.getPlugin(RealmsOfChaos.class);
    }

    @Override
    public void run() {
        if (!npc.isSpawned()) return;
        if (npc.getNavigator().getEntityTarget() == null) return;
        
        Entity ent = npc.getNavigator().getEntityTarget().getTarget();
        if (!(ent instanceof LivingEntity)) return;
        LivingEntity lent = (LivingEntity) ent;
        lent.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*5, 0));
    }
}