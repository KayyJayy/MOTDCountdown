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
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Messages{

    private HashMap<String, TextComponent> messages = new HashMap<>();

    public Messages(MOTDCountdown plugin){
        plugin.getDataFolder().mkdir();
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if(!messagesFile.exists()){
            try{
                FileUtils.copyURLToFile(getClass().getResource("/messages.yml"), new File(plugin.getDataFolder(), "messages.yml"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        Configuration loadMessages;

        try{
            loadMessages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
        }catch(IOException e){
            e.printStackTrace();
            return;
        }

        for(String i : loadMessages.getKeys()){
            TextComponent message = new TextComponent();

            for(BaseComponent component : TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', loadMessages.getString(i)))){
                message.addExtra(component);
            }

            messages.put(i, message);
        }
    }

    public TextComponent getMessage(String key){
        return messages.get(key);
    }
}
