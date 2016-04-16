package io.systemupdate.motdcountdown.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Copyright SystemUpdate (https://systemupdate.io) to present.
 * Please see included licence file for licensing terms.
 * File created on 16/04/2016.
 */
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
