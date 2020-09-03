package io.github.chouyoux.realmsofchaos.util;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class Banners {
	
	public static void updateBanner(Banner banner, String faction) {
		if (faction.contentEquals("Greeks"))
			greeksBanner(banner);
		else if (faction.contentEquals("Persians"))
			persiansBanner(banner);
		else if (faction.contentEquals("Egyptians"))
			egyptiansBanner(banner);
		else if (faction.contentEquals("Chaos"))
			chaosBanner(banner);
	}
	
	public static void updateShield(ItemStack shield, String faction) {
        BlockStateMeta meta = (BlockStateMeta) shield.getItemMeta();
        BlockState state = meta.getBlockState();
        Banner bannerState = (Banner) state;
        Banners.updateBanner(bannerState, faction);
        meta.setBlockState(bannerState);
        shield.setItemMeta(meta);
	}

	public static void greeksBanner(Banner banner) {
		banner.setBaseColor(DyeColor.RED);
		banner.update();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(new Pattern(DyeColor.RED, PatternType.BASE));
		patterns.add(new Pattern(DyeColor.YELLOW, PatternType.FLOWER));
		patterns.add(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL));
		patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
		patterns.add(new Pattern(DyeColor.RED, PatternType.SQUARE_BOTTOM_LEFT));
		patterns.add(new Pattern(DyeColor.RED, PatternType.SQUARE_BOTTOM_RIGHT));
		patterns.add(new Pattern(DyeColor.YELLOW, PatternType.TRIANGLES_BOTTOM));
		patterns.add(new Pattern(DyeColor.RED, PatternType.CURLY_BORDER));
		banner.setPatterns(patterns);
		banner.update();
	}
	
	public static void persiansBanner(Banner banner) {
		banner.setBaseColor(DyeColor.BLACK);
		banner.update();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.BASE));
		patterns.add(new Pattern(DyeColor.CYAN, PatternType.STRIPE_TOP));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_TOP));
		patterns.add(new Pattern(DyeColor.CYAN, PatternType.RHOMBUS_MIDDLE));
		patterns.add(new Pattern(DyeColor.BLUE, PatternType.STRIPE_MIDDLE));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.BORDER));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
		patterns.add(new Pattern(DyeColor.CYAN, PatternType.FLOWER));
		patterns.add(new Pattern(DyeColor.CYAN, PatternType.SKULL));
		patterns.add(new Pattern(DyeColor.BLUE, PatternType.GRADIENT_UP));
		banner.setPatterns(patterns);
		banner.update();
		
	}
	
	public static void egyptiansBanner(Banner banner) {
		banner.setBaseColor(DyeColor.YELLOW);
		banner.update();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(new Pattern(DyeColor.YELLOW, PatternType.BASE));
		patterns.add(new Pattern(DyeColor.PINK, PatternType.STRIPE_MIDDLE));
		patterns.add(new Pattern(DyeColor.ORANGE, PatternType.GRADIENT_UP));
		patterns.add(new Pattern(DyeColor.YELLOW, PatternType.FLOWER));
		patterns.add(new Pattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE));
		patterns.add(new Pattern(DyeColor.ORANGE, PatternType.CIRCLE_MIDDLE));
		patterns.add(new Pattern(DyeColor.BROWN, PatternType.TRIANGLE_BOTTOM));
		banner.setPatterns(patterns);
		banner.update();
	}
	
	public static void chaosBanner(Banner banner) {
		banner.setBaseColor(DyeColor.WHITE);
		banner.update();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(new Pattern(DyeColor.PURPLE, PatternType.GRADIENT_UP));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.STRAIGHT_CROSS));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.GRADIENT_UP));
		patterns.add(new Pattern(DyeColor.PURPLE, PatternType.GRADIENT));
		patterns.add(new Pattern(DyeColor.PURPLE, PatternType.FLOWER));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE));
		patterns.add(new Pattern(DyeColor.PURPLE, PatternType.FLOWER));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.FLOWER));
		patterns.add(new Pattern(DyeColor.PURPLE, PatternType.CIRCLE_MIDDLE));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
		banner.setPatterns(patterns);
		banner.update();
	}

}
