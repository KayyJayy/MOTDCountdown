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

package io.brkmc.motdcountdown.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

public class ConfigurablePlugin extends Plugin{

    private Configuration configuration;
    private File configFile;

    public void loadConfig() {
        if(configFile != null || configuration != null){
            throw new RuntimeException("Configuration already loaded");
        }
        this.configFile = new File(getDataFolder(), "config.yml");

        if(!configFile.exists()){
            try{
                configFile.createNewFile();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        try{
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void reloadConfig(){
        if(configFile == null || configuration == null){
            throw new RuntimeException("Configuration not loaded");
        }

        try{
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void saveConfig(){
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Configuration getConfig(){
        return this.configuration;
    }

    //From org.bukkit.java.plugin
    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + getFile());
        }

        File outFile = new File(getDataFolder(), resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } else {
                getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

    //From org.bukkit.java.plugin
    private InputStream getResource(String filename){
        if(filename == null){
            throw new IllegalArgumentException("Filename cannot be null");
        }

        try{
            URL url = getClass().getResource(filename);

            if (url == null){
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        }catch(IOException ignored){
            return null;
        }
    }
}
