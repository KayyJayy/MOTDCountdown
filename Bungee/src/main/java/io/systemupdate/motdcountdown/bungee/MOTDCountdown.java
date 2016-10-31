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

package io.systemupdate.motdcountdown.bungee;

import io.systemupdate.motdcountdown.bungee.util.Messages;
import io.systemupdate.motdcountdown.bungee.command.MOTDCountdownCommand;
import io.systemupdate.motdcountdown.bungee.listener.ProxyPingListener;
import lombok.Getter;

import java.io.File;

public class MOTDCountdown extends ConfigurablePlugin{

    @Getter private long endTime = -1L;
    @Getter private Messages messages;

    @Override
    public void onEnable(){
        loadConfig();

        getProxy().getPluginManager().registerListener(this, new ProxyPingListener(this));
        getProxy().getPluginManager().registerCommand(this, new MOTDCountdownCommand(this));
    }

    @Override
    public void onDisable(){}

    public void loadConfig(){
        getDataFolder().mkdir();

        File configFile = new File(getDataFolder(), "config.yml");

        if(!configFile.exists()){
            saveResource("/config.yml", false);
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
