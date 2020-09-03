package io.github.chouyoux.realmsofchaos.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.inventivetalent.glow.GlowAPI;

import io.github.chouyoux.realmsofchaos.RealmsOfChaos;
import io.github.chouyoux.realmsofchaos.custom_mobs.Guard;
import io.github.chouyoux.realmsofchaos.entities_handlers.RoCPlayers;
import io.github.chouyoux.realmsofchaos.ruleset.FactionRuleset;
import io.github.chouyoux.realmsofchaos.ruleset.Ruleset;

public class Relic implements Listener {
	
	private String o_faction;
	
	private Structure structure;
	private Block relicBlock;
	private ItemStack relicItem;
	
	public Relic(Structure structure) {
		
		if (!structure.getActType().equals("Castle")) return;

		RealmsOfChaos.getInstance().getServer().getPluginManager().registerEvents(this, RealmsOfChaos.getInstance());
		
		this.structure = structure;
		this.o_faction = structure.getFaction();
		Location location = (Location) Ruleset.Structures_ruleset.get(structure.getFaction()).get(structure.getActType()).get(structure.getTier()-1).get("reliqua");
		this.relicBlock = location.getBlock();
		this.relicBlock.setType(FactionRuleset.factionRelics.get(structure.getFaction()));
		
		applyEffect();
		
	}
	
	@EventHandler
	public void onRelicBreak(BlockBreakEvent event) {
		if (!event.getBlock().getLocation().equals(this.relicBlock.getLocation())) return;
		Player player = event.getPlayer();
		if (RoCPlayers.getFaction(player).equals(this.structure.getFaction())) return;
		
		event.setDropItems(false);
		this.relicItem = generateRelicItem();
		player.getInventory().addItem(this.relicItem);
		
		GlowAPI.setGlowing(player, true, Bukkit.getServer().getOnlinePlayers());
		
		negateEffect();
		this.structure = null;
		this.relicBlock = null;
	}
	
	private ItemStack generateRelicItem() {
		ItemStack item = new ItemStack(FactionRuleset.factionRelics.get(structure.getFaction()));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BOLD+FactionRuleset.factionChatNameColors.get(o_faction)+o_faction+" Relic");
		item.setItemMeta(meta);
		return item;
	}
	
	private void negateEffect() {
		if (structure == null) return;
		for (Guard guard : structure.getGuard().values())
			guard.setRespawnTime(guard.getRespawnTime()*2);
	}
	
	private void applyEffect() {
		if (structure == null) return;
		for (Guard guard : structure.getGuard().values())
			guard.setRespawnTime(guard.getRespawnTime()/2);
	}

	public Structure getStructure() {
		return structure;
	}

	public Block getBlock() {
		return relicBlock;
	}

	public boolean isBlockState() {
		return this.relicBlock == null;
	}

}
