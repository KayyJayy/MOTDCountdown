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

package io.systemupdate.motdcountdown.bungee.listener;

import io.systemupdate.motdcountdown.bungee.MOTDCountdown;
import compact.org.apache.commons.lang.time.DurationFormatter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener{

    private MOTDCountdown plugin;

    public ProxyPingListener(MOTDCountdown plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onProxyPing(ProxyPingEvent event){
        if(event.getResponse() == null){
            return;
        }

        if(plugin.getEndTime() == -1 || plugin.getEndTime() < System.currentTimeMillis()){
            event.getResponse().setDescription(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MOTD.Completed")
                    .replace("{newLine}", "\n")));
            return;
        }

        event.getResponse().setDescription(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MOTD.Running")
                .replace("{newLine}", "\n")
                .replace("{time}", DurationFormatter.formatDurationWords(plugin.getEndTime() - System.currentTimeMillis(), true, true))));
    }
}
