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

import io.systemupdate.motdcountdown.MOTDCountdown;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Messages{

    private HashMap<String, TextComponent> messages = new HashMap<>();

    public Messages(MOTDCountdown plugin){
        plugin.getDataFolder().mkdir();
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if(!messagesFile.exists()){
            plugin.saveResource("/messages.yml", false);
        }

        Configuration loadMessages;

        try{
            loadMessages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
        }catch(IOException e){
            e.printStackTrace();
            return;
        }

        //Fuck it, I couldn't figure out automatic loading with bungee's shitty configuration api
        //TODO Make automatic

        messages.put("Generic.NoPermission", group(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString("Generic.NoPermission")))));
        messages.put("Command.Invalid-Usage", group(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString("Command.Invalid-Usage")))));
        messages.put("Command.SetTime.Output", group(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString("Command.SetTime.Output")))));
        messages.put("Command.SetTime.Invalid-Usage", group(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString("Command.SetTime.Invalid-Usage")))));
        messages.put("Command.SetTime.Invalid-Input", group(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString("Command.SetTime.Invalid-Input")))));
        messages.put("Command.SetRunningMOTD.Invalid-Usage", group(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString("Command.SetRunningMOTD.Invalid-Usage")))));
        messages.put("Command.SetRunningMOTD.Output", group(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString("Command.SetRunningMOTD.Output")))));
        messages.put("Command.SetCompletedMOTD.Invalid-Usage", group(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString("Command.SetCompletedMOTD.Invalid-Usage")))));
        messages.put("Command.SetCompletedMOTD.Output", group(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString("Command.SetCompletedMOTD.Output")))));
    }

    public TextComponent getMessage(String key){
        if(!messages.containsKey(key)) System.out.println("Missing key " + key);
        return messages.get(key);
    }

    private TextComponent group(BaseComponent[] components){
        TextComponent textComponent = new TextComponent();

        for(BaseComponent component : components){
            textComponent.addExtra(component);
        }

        return textComponent;
    }
}
