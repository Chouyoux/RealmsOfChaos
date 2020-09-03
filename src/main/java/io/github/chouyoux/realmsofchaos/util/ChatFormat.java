package io.github.chouyoux.realmsofchaos.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class ChatFormat {
	
	public static TextComponent HoverMessage(String message, ChatColor color, String outputmessage, String hovermessage) {
		TextComponent var = new TextComponent();
        var.setText(message);
        var.setColor(color);
        var.setClickEvent(new ClickEvent(Action.RUN_COMMAND, outputmessage));
        var.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hovermessage).create()));
        return var;
    }

}
