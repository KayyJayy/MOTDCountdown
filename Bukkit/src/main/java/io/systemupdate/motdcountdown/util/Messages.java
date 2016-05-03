/*
 *   COPYRIGHT NOTICE
 *
 *   Copyright (C) 2016, SystemUpdate, <admin@systemupdate.io>.
 *
 *   All rights reserved.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT OF THIRD PARTY RIGHTS. IN
 *   NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 *   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *   OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 *   OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *   Except as contained in this notice, the name of a copyright holder shall not
 *   be used in advertising or otherwise to promote the sale, use or other dealings
 *   in this Software without prior written authorization of the copyright holder.
 */

package io.systemupdate.motdcountdown.util;

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
