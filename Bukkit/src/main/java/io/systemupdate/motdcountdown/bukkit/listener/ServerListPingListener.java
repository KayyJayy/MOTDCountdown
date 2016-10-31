/*
 *     Simple plugin to allow countdowns in MOTD. Works with BungeeCord or Bukkit.
 *     Copyright (C) 2016  SystemUpdate
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.systemupdate.motdcountdown.bukkit.listener;

import io.systemupdate.motdcountdown.bukkit.MOTDCountdown;
import io.systemupdate.motdcountdown.bukkit.util.DurationFormatter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

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
