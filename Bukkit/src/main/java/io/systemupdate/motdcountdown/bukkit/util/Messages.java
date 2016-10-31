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

package io.systemupdate.motdcountdown.bukkit.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Messages {
    private File file;
    private YamlConfiguration config;
    private HashMap<String, String> messages = new HashMap<String, String>();

    private Plugin plugin;

    public Messages(Plugin plugin, String fileName){
        this.plugin = plugin;

        file = new File(plugin.getDataFolder(), fileName);

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration storedConfig = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(plugin.getClass().getResourceAsStream("/" + fileName))));
        config = YamlConfiguration.loadConfiguration(file);

        boolean inConfig;

        for(String i : storedConfig.getConfigurationSection("").getKeys(true)){
            inConfig = true;

            if(!config.contains(i)){
                inConfig = false;
                config.set(i, storedConfig.getString(i));
                plugin.getLogger().info("Key " + i + " was not found in the messages file and has been added.");
            }

            messages.put(i, ChatColor.translateAlternateColorCodes('&', inConfig ? config.getString(i) : storedConfig.getString(i)));
        }

        try{
            config.save(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getText(String key){
        if(messages.containsKey(key)){
            return messages.get(key);
        }else{
            return "Error! Please contact an administrator";
        }
    }

}
