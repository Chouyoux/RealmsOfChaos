package io.github.chouyoux.realmsofchaos.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCNPC;
import net.minecraft.server.v1_16_R1.DataWatcher;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntityMetadata;

public class UtilFuncs {

	public enum NegativeEffects{
        CONFUSION, HARM, HUNGER, POISON, SLOW_DIGGING, SLOW, WEAKNESS, WITHER, BLINDNESS;
    }
	
	public static boolean isPositiveEffect(PotionEffectType potion) {
        for (NegativeEffects bad: NegativeEffects.values())
            if (potion.getName().equalsIgnoreCase(bad.name()))
                return false;
        return true;
	}
	
	public static List<LivingEntity> getNearbyEntitesAngle(Location loc, double range, double angle) {
		Location loc_clone = loc.clone();
		loc_clone.setY(0); // we want 2d distance (for cylinder not sphere)
        List<Entity> entities = loc.getWorld().getEntities();
        ArrayList<LivingEntity> rtrn = new ArrayList<LivingEntity>();
        for (Entity en : entities) {
        	if (!(en instanceof LivingEntity))
        		continue;
        	LivingEntity en_ = (LivingEntity) en;
        	Location en_loc = en.getLocation();
        	en_loc.setY(0); // cylinder
            if (en_loc.distance(loc_clone) > range) {
                continue;
            }
            Vector dirToEn = en_loc.toVector().subtract(loc_clone.toVector());
            Vector playerDirection = loc_clone.getDirection();
            double angle_to_en = Math.toDegrees(dirToEn.angle(playerDirection));
            if (angle_to_en > angle/2D)
            	continue;
            for (Entity en2 : en.getPassengers()) {
            	if (!(en2 instanceof LivingEntity))
            		continue;
            	LivingEntity en2_ = (LivingEntity) en2;
            	rtrn.add(en2_);
            }
            rtrn.add(en_);
        }
        return rtrn;
	}

	public static List<LivingEntity> getNearbyEntities(Location loc, double range) {
		Location loc_clone = loc.clone();
		loc_clone.setY(0); // we want 2d distance (for cylinder not sphere)
        List<Entity> entities = loc.getWorld().getEntities();
        ArrayList<LivingEntity> rtrn = new ArrayList<LivingEntity>();
        for (Entity en : entities) {
        	if (!(en instanceof LivingEntity))
        		continue;
        	LivingEntity en_ = (LivingEntity) en;
        	Location en_loc = en.getLocation();
        	en_loc.setY(0); // cylinder
            if (en_loc.distance(loc_clone) > range) {
                continue;
            }
            for (Entity en2 : en.getPassengers()) {
            	if (!(en2 instanceof LivingEntity))
            		continue;
            	LivingEntity en2_ = (LivingEntity) en2;
            	rtrn.add(en2_);
            }
            rtrn.add(en_);
        }
        return rtrn;
    }
	
	public static List<LivingEntity> getNearbyEntities3D(Location loc, double range) {
		Location loc_clone = loc.clone();
        List<Entity> entities = loc.getWorld().getEntities();
        ArrayList<LivingEntity> rtrn = new ArrayList<LivingEntity>();
        for (Entity en : entities) {
        	if (!(en instanceof LivingEntity))
        		continue;
        	LivingEntity en_ = (LivingEntity) en;
        	Location en_loc = en.getLocation();
            if (en_loc.distance(loc_clone) > range) {
                continue;
            }
            for (Entity en2 : en.getPassengers()) {
            	if (!(en2 instanceof LivingEntity))
            		continue;
            	LivingEntity en2_ = (LivingEntity) en2;
            	rtrn.add(en2_);
            }
            rtrn.add(en_);
        }
        return rtrn;
    }
	
	public static LivingEntity getNearestAlly(LivingEntity e, double range) {
		 List<LivingEntity> nearby = getNearbyEntities3D(e.getLocation(), range);
		 String faction = RoCNPC.getFaction(e);
		 double min_distance = Double.MAX_VALUE;
		 LivingEntity ret = null;
		 for (LivingEntity l : nearby) {
			 if (!RoCNPC.getFaction(e).equals(faction)) continue;
			 double distance = e.getLocation().distance(l.getLocation());
			 if (distance < min_distance) {
				 min_distance = distance;
				 ret = l;
			 }
		 }
		 return ret;
	}
	
	public static LivingEntity getNearestEnnemy(LivingEntity e, double range) {
		 List<LivingEntity> nearby = getNearbyEntities3D(e.getLocation(), range);
		 String faction = RoCNPC.getFaction(e);
		 double min_distance = Double.MAX_VALUE;
		 LivingEntity ret = null;
		 for (LivingEntity l : nearby) {
			 if (RoCNPC.getFaction(e).equals(faction)) continue;
			 double distance = e.getLocation().distance(l.getLocation());
			 if (distance < min_distance) {
				 min_distance = distance;
				 ret = l;
			 }
		 }
		 return ret;
	}
	
	public static LivingEntity getFarestAlly(LivingEntity e, double range) {
		 List<LivingEntity> nearby = getNearbyEntities3D(e.getLocation(), range);
		 String faction = RoCNPC.getFaction(e);
		 double max_distance = 0;
		 LivingEntity ret = null;
		 for (LivingEntity l : nearby) {
			 if (!RoCNPC.getFaction(e).equals(faction)) continue;
			 double distance = e.getLocation().distance(l.getLocation());
			 if (distance < max_distance) {
				 max_distance = distance;
				 ret = l;
			 }
		 }
		 return ret;
	}
	
	public static LivingEntity getFarestEnnemy(LivingEntity e, double range) {
		 List<LivingEntity> nearby = getNearbyEntities3D(e.getLocation(), range);
		 String faction = RoCNPC.getFaction(e);
		 double max_distance = 0;
		 LivingEntity ret = null;
		 for (LivingEntity l : nearby) {
			 if (RoCNPC.getFaction(e).equals(faction)) continue;
			 double distance = e.getLocation().distance(l.getLocation());
			 if (distance > max_distance) {
				 max_distance = distance;
				 ret = l;
			 }
		 }
		 return ret;
	}
	
	public static Location rotateLocationAroundY(Location loc, double rad) {
		
		double degrees = Math.toDegrees(rad);
	   
		World world = loc.getWorld();
		
	    double currentX = loc.getX();
	    double currentZ = loc.getZ();
	    double currentYaw = loc.getYaw();
	    
	   
	    double cosine = Math.cos(rad);
	    double sine = Math.sin(rad);
	   
	    return new Location(world, (cosine * currentX - sine * currentZ), loc.getY(), (sine * currentX + cosine * currentZ), (int)(currentYaw+degrees), 0);
	}
	
	public static boolean isLookingAt(LivingEntity e, LivingEntity e2) {
		if (e.getUniqueId().equals(e2.getUniqueId()))
			return false;

		Location e_eye = e.getEyeLocation();
		Location e2_eye = e2.getEyeLocation();
		Vector beetween = vectorBeetween(e_eye, e2_eye);
		Vector direction = e2_eye.toVector().subtract(e_eye.toVector()).normalize();

		double dot = direction.dot(e_eye.getDirection());
		
		double aim = 0.99D;
		if (e_eye.distance(e2_eye) < 3)
			aim = 0.94D;
		
		return dot > aim && !collisionsOnVector(e_eye, beetween);
	}
	
	public static LivingEntity getLookingAtRange(LivingEntity e, double range, boolean catch_ally, boolean catch_ennemy, boolean catch_full_life) {
		double min_distance = 99999999D;
		LivingEntity closest_entity = null;
		for (LivingEntity e2 : getNearbyEntities3D(e.getEyeLocation(), range)) {
			if (!((catch_ally && RoCNPC.sameFaction(e, e2)) ||
					(catch_ennemy && !RoCNPC.sameFaction(e, e2)) ||
					(catch_full_life && e2.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= e2.getHealth())
					)) continue;
			if (isLookingAt(e, e2) && e.getLocation().distance(e2.getLocation()) < min_distance) {
				closest_entity = e2;
				min_distance = e.getLocation().distance(e2.getLocation());
			}
		}
		return closest_entity;
	}

	public static void removeAllNegativePotions(LivingEntity e){
        for (PotionEffect effects: e.getActivePotionEffects())
            for (NegativeEffects bad: NegativeEffects.values())
	            if (effects.getType().getName().equalsIgnoreCase(bad.name()))
	                e.removePotionEffect(effects.getType());
    }
	
	public static void removeAllPotions(LivingEntity e){
        for (PotionEffect effect: e.getActivePotionEffects())
        	e.removePotionEffect(effect.getType());
    }
    
    public static void buffEntity(LivingEntity entity, PotionEffectType buffed_effect, int amplifier, int duration) {
    	Collection<PotionEffect> list_effects = entity.getActivePotionEffects();
    	boolean already_has = false;
    	for (PotionEffect effect: list_effects)
    		if (effect.getType().equals(buffed_effect)) {
    	    	already_has = true;
    			int old_amplifier = effect.getAmplifier();
    			int old_duration = effect.getDuration();
    			
    			if (duration < old_duration) {
        			entity.addPotionEffect(new PotionEffect(buffed_effect, duration, old_amplifier+amplifier, true, true, true));
			    	BukkitRunnable set_old_effect = new  BukkitRunnable() {
				    	@Override
				        public void run() {
				    		entity.addPotionEffect(new PotionEffect(buffed_effect, old_duration-duration, old_amplifier, true, true, true));
				    	}
				    };
				    set_old_effect.runTaskLater(RealmsOfChaos.getInstance(), duration);
    			}
    			else {
        			entity.addPotionEffect(new PotionEffect(buffed_effect, old_duration, old_amplifier+amplifier, true, true, true));
			    	BukkitRunnable set_old_effect = new  BukkitRunnable() {
				    	@Override
				        public void run() {
				    		entity.addPotionEffect(new PotionEffect(buffed_effect, duration-old_duration, amplifier-1, true, true, true));
				    	}
				    };
				    set_old_effect.runTaskLater(RealmsOfChaos.getInstance(), old_duration);
    			}
    		}
    	if (!already_has)
    		entity.addPotionEffect(new PotionEffect(buffed_effect, duration, amplifier-1, true, true, true));
    }
	

    public static ItemStack createGuiItemL(Material material, String name, ArrayList<String> loreList) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>();

        for(String loreComments : loreList)
            metaLore.add(loreComments);

        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack createGuiItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>();

        metaLore.add(lore);

        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static Vector vectorBeetween(Location source, Location target) {
		return target.toVector().subtract(source.toVector());
    }
    
    public static boolean collisionsOnVector(Location source, Vector v) {
    	double distance = v.length();
    	Vector direction = v.normalize();
    	for (double d = 0.25D; d <= distance; d+=0.25D) {
    		if (!(source.clone().add(direction.clone().multiply(d)).getBlock().isPassable()))
    			return true;
    	}
    	return false;
    }
    
    public static boolean canSee(LivingEntity e, LivingEntity e2) {
    	return !collisionsOnVector(e.getEyeLocation(), vectorBeetween(e.getEyeLocation(), e2.getEyeLocation()));
    }
    
    public static boolean canSee(Entity e, LivingEntity e2) {
    	return !collisionsOnVector(e.getLocation(), vectorBeetween(e.getLocation(), e2.getEyeLocation()));
    }
    
    public static boolean canSee(LivingEntity e, Entity e2) {
    	return !collisionsOnVector(e.getEyeLocation(), vectorBeetween(e.getEyeLocation(), e2.getLocation()));
    }
    
    public static ItemStack setUnbreakable(ItemStack item) {
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		itemCompound.setInt("Unbreakable", 1);
		nmsItem.setTag(itemCompound);
		return CraftItemStack.asBukkitCopy(nmsItem);
    }
    
    @SuppressWarnings("unchecked")
    public static void setGlowing(Player glowingPlayer, Player sendPacketPlayer, boolean glow) {
        try {
            EntityPlayer entityPlayer = ((CraftPlayer) glowingPlayer).getHandle();

            DataWatcher dataWatcher = entityPlayer.getDataWatcher();

            entityPlayer.glowing = glow; // For the update method in EntityPlayer to prevent switching back.

            // The map that stores the DataWatcherItems is private within the DataWatcher Object.
            // We need to use Reflection to access it from Apache Commons and change it.
            Map<Integer, DataWatcher.Item<?>> map = (Map<Integer, DataWatcher.Item<?>>) FieldUtils.readDeclaredField(dataWatcher, "d", true);

            // Get the 0th index for the BitMask value. http://wiki.vg/Entities#Entity
            DataWatcher.Item item = map.get(0);
            byte initialBitMask = (Byte) item.b(); // Gets the initial bitmask/byte value so we don't overwrite anything.
            byte bitMaskIndex = (byte) 0x40; // The index as specified in wiki.vg/Entities
            if (glow) {
                item.a((byte) (initialBitMask | 1 << bitMaskIndex));
            } else {
                item.a((byte) (initialBitMask & ~(1 << bitMaskIndex))); // Inverts the specified bit from the index.
            }

            PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(glowingPlayer.getEntityId(), dataWatcher, true);

            ((CraftPlayer) sendPacketPlayer).getHandle().playerConnection.sendPacket(metadataPacket);
        } catch (IllegalAccessException e) { // Catch statement necessary for FieldUtils.readDeclaredField()
            e.printStackTrace();
        }
    }
    
    public static void setLocOnGround(Location loc) {
    	World world = loc.getWorld();
    	while (world.getBlockAt(loc.clone().add(0, -1, 0)).isPassable() && loc.getY() > 1)
    		loc.add(0, -1, 0);
		while (!(world.getBlockAt(loc).isPassable()) && loc.getY() < 255)
			loc.add(0, 1, 0);
		double y = (int) loc.getY() + 0.05D;
		loc.setY(y);
    }
    
    public static void setLocInBlockXZCenter(Location loc) {
    	double x = (int) loc.getX() + 0.5D;
    	double z = (int) loc.getZ() + 0.5D;
    	loc.setX(x);
    	loc.setZ(z);
    }
    
}
