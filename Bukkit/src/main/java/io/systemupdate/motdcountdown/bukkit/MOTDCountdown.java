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

package io.systemupdate.motdcountdown.bukkit;

import io.systemupdate.motdcountdown.bukkit.listener.ServerListPingListener;
import io.systemupdate.motdcountdown.bukkit.util.Messages;
import io.systemupdate.motdcountdown.bukkit.command.MOTDCountdownCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MOTDCountdown extends JavaPlugin{

    private long endTime = -1L;

    private Messages messages;

    @Override
    public void onEnable(){
        this.registerEvents();
    }

    @Override
    public void onDisable(){}

    private void registerEvents(){
        this.saveDefaultConfig();
        messages = new Messages(this, "messages.yml");

        if(getConfig().get("End-Time") != null){
            endTime = getConfig().getLong("End-Time");
        }

        this.getServer().getPluginManager().registerEvents(new ServerListPingListener(this), this);
        getCommand("motdcountdown").setExecutor(new MOTDCountdownCommand(this));
    }

    public long getEndTime(){
        return endTime;
    }

    public Messages getMessages(){
        return messages;
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
        this.getServer().getScheduler().runTaskAsynchronously(this, () -> saveConfig());
    }
}
