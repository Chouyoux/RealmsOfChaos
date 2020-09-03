package io.github.chouyoux.realmsofchaos.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.gameplay.skills.*;

public class TrainerGUI implements InventoryHolder, Listener {
    private Inventory inv;
	private RealmsOfChaos main;
	private int nb_click;
	private Player player;

    public TrainerGUI(Player player, String cl, String fac) {
		this.main = RealmsOfChaos.getInstance();
		this.nb_click = 0;
		this.player = player;
        main.getServer().getPluginManager().registerEvents(this, main);
        inv = Bukkit.createInventory(this, 18, "Trainer");
        initializeItems(cl, fac);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems(String cl, String fac) {
    	if (cl.compareTo("Warrior") == 0) {
    		inv.addItem(MindCleanse.getItem(player));
    		inv.addItem(Abnegation.getItem(player));
    		inv.addItem(Provoke.getItem(player));
    		inv.addItem(Empathy.getItem(player));
    		//inv.addItem(GolemShape.getItem(player));
    		if (fac.equals("Greeks")) {
        		inv.setItem(9, Charge.getItem(player));
        		inv.setItem(10, IntimidatingShout.getItem(player));
    		}
    		if (fac.equals("Persians")) {
        		inv.setItem(9, Grab.getItem(player));
        		inv.setItem(10, SlowingStance.getItem(player));
    		}
    		if (fac.equals("Egyptians")) {
        		inv.setItem(9, FireTouch.getItem(player));
        		inv.setItem(10, FireEruption.getItem(player));
    		}
    	}
    	else if (cl.compareTo("Archer") == 0) {
    		inv.addItem(InfiltratorsArrow.getItem(player));
    		inv.addItem(Poison.getItem(player));
    		inv.addItem(SoulRead.getItem(player));
    		inv.addItem(AttackOrder.getItem(player));
    		inv.addItem(Camouflage.getItem(player));
    		if (fac.equals("Greeks")) {
        		inv.setItem(9, Knockback.getItem(player));
        		inv.setItem(10, PullingShot.getItem(player));
    		}
    		if (fac.equals("Persians")) {
        		inv.setItem(9, BouncingLance.getItem(player));
        		inv.setItem(10, ParalysingPoison.getItem(player));
    		}
    		if (fac.equals("Egyptians")) {
        		inv.setItem(9, RainOfArrows.getItem(player));
        		inv.setItem(10, Fire.getItem(player));
    		}
    	}
    	else if (cl.compareTo("Magician") == 0) {
    		inv.addItem(ArcaneChain.getItem(player));
    		inv.addItem(Polymorphism.getItem(player));
    		inv.addItem(StoneSkin.getItem(player));
    		inv.addItem(Blink.getItem(player));
    		inv.addItem(Swap.getItem(player));
    		if (fac.equals("Greeks")) {
        		inv.setItem(9, NaturesAnger.getItem(player));
        		inv.setItem(10, ArcaneWard.getItem(player));
        		inv.setItem(11, ArcaneOverflow.getItem(player));
    		}
    		if (fac.equals("Persians")) {
    			inv.setItem(9, Thunderstrike.getItem(player));
    			inv.setItem(10, SludgeWave.getItem(player));
    		}
    		if (fac.equals("Egyptians")) {
    			inv.setItem(9, Fireball.getItem(player));
    			inv.setItem(10, FireBomb.getItem(player));
    			inv.setItem(11, FirePropagation.getItem(player));
    		}
    	}
    	else if (cl.compareTo("Healer") == 0) {
    		inv.addItem(DeepMeditation.getItem(player));
    		inv.addItem(EntraveToEvil.getItem(player));
    		inv.addItem(SongOfAdrenaline.getItem(player));
    		inv.addItem(DarknessVision.getItem(player));
    		if (fac.equals("Greeks")) {
        		inv.setItem(9, SongOfExcitement.getItem(player));
        		inv.setItem(10, WindWall.getItem(player));
    		}
    		if (fac.equals("Persians")) {
        		inv.setItem(9, SongOfAquaticGrace.getItem(player));
        		inv.setItem(10, SludgeWall.getItem(player));
    		}
    		if (fac.equals("Egyptians")) {
        		inv.setItem(9, SongOfFeathers.getItem(player));
        		inv.setItem(10, FireWall.getItem(player));
    		}
    	}
    	else if (cl.compareTo("Duelist") == 0) {
    		inv.addItem(Adrenaline.getItem(player));
    		inv.addItem(Devotion.getItem(player));
    		inv.addItem(Cowardise.getItem(player));
    		inv.addItem(DemonStance.getItem(player));
    		inv.addItem(StunningStance.getItem(player));
    		if (fac.equals("Greeks")) {
    		}
    		if (fac.equals("Persians")) {
    		}
    		if (fac.equals("Egyptians")) {
    		}
    	}
    }

    // You can open the inventory with this
    public void openInventory(Player p) {
        p.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) {
            return;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            e.setCancelled(true);
        }
        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerInventory inventory = p.getInventory();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR || e.getRawSlot() > 17) return;
        
        inventory.setItem(this.nb_click+1, clickedItem);
        
        this.nb_click++;
        
        if (this.nb_click > 4)
        	p.closeInventory();
    }
}