package io.github.chouyoux.realmsofchaos.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;

public class Particles {
	
	public static void Trail(Location source, Location target, int nb_particles, Particle particle) {
		World world = source.getWorld();
		double ratio = 1D / (double) nb_particles;
		Vector line = target.toVector().subtract(source.toVector()).multiply(ratio);
		Location particle_loc = source.clone();
		for (int i = 0; i < nb_particles; i++) {
			world.spawnParticle(particle, particle_loc, 1, 0, 0, 0, 0, null, true);
			particle_loc.add(line);
		}
	}
	
	public static void Trail(Location source, Location target, int nb_particles, Particle particle, double note) {
		World world = source.getWorld();
		double ratio = 1D / (double) nb_particles;
		Vector line = target.toVector().subtract(source.toVector()).multiply(ratio);
		Location particle_loc = source.clone();
		for (int i = 0; i < nb_particles; i++) {
			world.spawnParticle(Particle.NOTE, particle_loc, 0, note, 0, 0, 1);
			particle_loc.add(line);
		}
	}
	
	public static void Circle(Location center, double radius, double ratio, int nb_particles, Particle particle, boolean onGround) {
		if (nb_particles == 0 || ratio == 0D) return;
		World world = center.getWorld();
		double angleToCover = 360D*ratio;
		double anglePerParticle = angleToCover / (double) nb_particles;
		
		for (int i = 0; i < nb_particles+1; i++) {
			Location particle_loc = center.clone();
			double angle = Math.toRadians(center.getYaw() + anglePerParticle*i);
			double x = Math.cos(angle);
			double z = Math.sin(angle);
			Vector radius_line = new Vector(x, 0, z).multiply(radius);
			particle_loc.add(radius_line);
			if (onGround) UtilFuncs.setLocOnGround(particle_loc);
			world.spawnParticle(particle, particle_loc, 1, 0, 0, 0, 0, null, true);
		}
	}
	
	public static void Cone(Location source, double radius, double angle, double distance_between, Particle particle, boolean onGround) {
		Location source_ = source.clone();
		source_.setYaw((float) (source.getYaw()+(angle/2)));
		double ratio = angle/360D;
		double distance = distance_between;
		while (distance < radius) {
			Circle(source_, distance, ratio, (int) distance*10, particle, onGround);
			distance += distance_between;
		}
		
	}
	
	public static void WavingCone(Location source, double radius, double angle, double distance_between, Particle particle, int delay, boolean onGround) {
		Location source_ = source.clone();
		source_.setYaw((float) (source.getYaw()+(angle/2)));
		double ratio = angle/360D;
		BukkitRunnable wave = new  BukkitRunnable() {
			
			double distance = distance_between;
			
	    	@Override
	        public void run() {
	    		if (distance >= radius) {
	    			this.cancel();
	    			return;
	    		}
	    		Circle(source_, distance, ratio, (int) distance*10, particle, onGround);
	    		distance += distance_between;
	    	}
	    };
    	wave.runTaskTimer(RealmsOfChaos.instance, 0, delay);
		
		
	}
	
	public static void DirectionalWall(Location source, Particle particle, boolean x, int nb_blocks, int duration) {
    	BukkitRunnable firewall = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		Location loc = source.clone();
	    		UtilFuncs.setLocOnGround(loc);
	    		UtilFuncs.setLocInBlockXZCenter(loc);
	    		
	    		int enlargement = (nb_blocks - 1)/2;
	    		
	    		if (x) loc.setX(loc.getX() - enlargement);
	    		else loc.setZ(loc.getZ() - enlargement);
	    		
	    		for (int i = 0; i < nb_blocks; i++) {
	    			if (x) {
	    				Location loc_for_this_block = loc.clone();
	    				loc_for_this_block.setX((int) loc.getX());
	    				for (double offset = 0; offset < 1; offset+=0.1D) {
	    					loc_for_this_block.getWorld().spawnParticle(particle, loc_for_this_block.clone().add(offset, 0, 0), 0, 0, 1, 0, 0.2, null, true);
	    				}
	    				loc.add(1, 0, 0);
	    			}
	    			else {
	    				Location loc_for_this_block = loc.clone();
	    				loc_for_this_block.setZ((int) loc.getZ());
	    				for (double offset = 0; offset < 1; offset+=0.1D) {
	    					loc_for_this_block.getWorld().spawnParticle(particle, loc_for_this_block.clone().add(0, 0, offset), 0, 0, 1, 0, 0.2, null, true);
	    				}
	    				loc.add(0, 0, 1);
	    			}
	    			UtilFuncs.setLocOnGround(loc);
	    		}
	    	}
	    };
    	BukkitRunnable firewall_end = new  BukkitRunnable() {
	    	@Override
	        public void run() {
	    		firewall.cancel();
	    	}
	    };
	    firewall.runTaskTimer(RealmsOfChaos.instance, 0, 5);
	    firewall_end.runTaskLater(RealmsOfChaos.instance, duration);
	}
	
	public static void FireEruption(Location source) {
		Location loc = source.clone();
		source.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0.8, 0, 0.2, null, true);
		source.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 0, 0, 1, 0, 0.15, null, true);
		source.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0, 0.9, 0, 0.2, null, true);
		loc = source.clone();
		loc.add(0.1, 0, 0.1);
		source.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0.8, 0, 0.2, null, true);
		source.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0, 0.9, 0, 0.2, null, true);
		loc = source.clone();
		loc.add(-0.1, 0, 0.1);
		source.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0.8, 0, 0.2, null, true);
		source.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 0, 0, 1, 0, 0.15, null, true);
		source.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0, 0.9, 0, 0.2, null, true);
		loc = source.clone();
		loc.add(0.1, 0, -0.1);
		source.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0.8, 0, 0.2, null, true);
		source.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0, 0.9, 0, 0.2, null, true);
		loc = source.clone();
		loc.add(-0.1, 0, -0.1);
		source.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0.8, 0, 0.2, null, true);
		source.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0, 0.9, 0, 0.2, null, true);
		loc = source.clone();
		loc.add(0.15, 0, 0.15);
		source.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0.8, 0, 0.2, null, true);
		source.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0, 0.9, 0, 0.2, null, true);
		loc = source.clone();
		loc.add(-0.15, 0, 0.15);
		source.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0.8, 0, 0.2, null, true);
		source.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 0, 0, 1, 0, 0.15, null, true);
		source.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0, 0.9, 0, 0.2, null, true);
		loc = source.clone();
		loc.add(0.15, 0, -0.15);
		source.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0.8, 0, 0.2, null, true);
		source.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0, 0.9, 0, 0.2, null, true);
		loc = source.clone();
		loc.add(-0.15, 0, -0.15);
		source.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0.8, 0, 0.2, null, true);
		source.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0, 0.9, 0, 0.2, null, true);
	}

}
