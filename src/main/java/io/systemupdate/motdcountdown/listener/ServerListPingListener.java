package io.systemupdate.motdcountdown.listener;

import io.systemupdate.motdcountdown.MOTDCountdown;
import io.systemupdate.motdcountdown.util.DurationFormatter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Copyright SystemUpdate (https://systemupdate.io) to present.
 * Please see included licence file for licensing terms.
 * File created on 15/04/2016.
 */
public class ServerListPingListener implements Listener{

    public MOTDCountdown motdCountdown;

    public ServerListPingListener(MOTDCountdown motdCountdown){
        this.motdCountdown = motdCountdown;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onServerListPing(ServerListPingEvent event){
        if(motdCountdown.getEndTime() == -1 || motdCountdown.getEndTime() < System.currentTimeMillis()){
            event.setMotd(ChatColor.translateAlternateColorCodes('&', motdCountdown.getConfig().getString("MOTD.Completed")
                    .replace("{newLine}", "\n")));
            return;
        }

        event.setMotd(ChatColor.translateAlternateColorCodes('&', motdCountdown.getConfig().getString("MOTD.Running")
                .replace("{newLine}", "\n")
                .replace("{time}", DurationFormatter.formatDurationWords(motdCountdown.getEndTime() - System.currentTimeMillis(), true, true))));
    }
}
