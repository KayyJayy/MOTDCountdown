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
