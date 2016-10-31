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

package io.systemupdate.motdcountdown.bungee.util;

import io.systemupdate.motdcountdown.bungee.MOTDCountdown;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Messages{

    private Configuration messages;

    public Messages(MOTDCountdown plugin){
        plugin.getDataFolder().mkdir();
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if(!messagesFile.exists()){
            plugin.saveResource("/messages.yml", false);
        }

         try{
            messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
        }catch(IOException e){
            e.printStackTrace();
            return;
        }
    }

    public String getMessage(String key){
        return ChatColor.translateAlternateColorCodes('&', messages.getString(key));
    }
}
