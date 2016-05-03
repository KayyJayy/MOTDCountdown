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

import io.systemupdate.motdcountdown.listener.ProxyPingListener;
import io.systemupdate.motdcountdown.util.Messages;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class MOTDCountdown extends ConfigurablePlugin{

    @Getter private long endTime = -1L;
    @Getter private Messages messages;

    @Override
    public void onEnable(){
        loadConfig();


        getProxy().getPluginManager().registerListener(this, new ProxyPingListener(this));


        /*
        getProxy().getPluginManager().registerListener(this, new ChatListener());
        getProxy().getPluginManager().registerCommand(this, new prefix());
         */
        //register event
        //register command
    }

    @Override
    public void onDisable(){}

    public void loadConfig(){
        getDataFolder().mkdir();

        File configFile = new File(getDataFolder(), "config.yml");

        if(!configFile.exists()){
            try{
                FileUtils.copyURLToFile(getClass().getResource("/config.yml"), new File(getDataFolder(), "config.yml"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        super.loadConfig();

        if(getConfig().get("End-Time") != null){
            endTime = getConfig().getLong("End-Time");
        }

        messages = new Messages(this);
    }

    public void setEndTime(long endTime){
        this.endTime = endTime;

        getConfig().set("End-Time", endTime);
        saveConfigAsync();
    }

    public void setRunningMOTD(String runningMOTD){
        getConfig().set("MOTD.Running", runningMOTD);
        saveConfigAsync();
    }

    public void setCompletedMOTD(String completedMOTD){
        getConfig().set("MOTD.Completed", completedMOTD);
        saveConfigAsync();
    }

    private void saveConfigAsync(){
        getProxy().getScheduler().runAsync(this, this::saveConfig);
    }
}
