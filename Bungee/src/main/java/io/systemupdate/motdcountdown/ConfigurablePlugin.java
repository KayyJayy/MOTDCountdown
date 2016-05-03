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

package io.systemupdate.motdcountdown;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurablePlugin extends Plugin{ //TODO Internal saveResource

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
}
