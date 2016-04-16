package io.systemupdate.motdcountdown;

import io.systemupdate.motdcountdown.command.motdcountdown;
import io.systemupdate.motdcountdown.listener.ServerListPingListener;
import io.systemupdate.motdcountdown.util.Messages;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright SystemUpdate (https://systemupdate.io) to present.
 * Please see included licence file for licensing terms.
 * File created on 02/03/2016.
 */
public class MOTDCountdown extends JavaPlugin{

    private long endTime = -1L;

    private Messages messages;

    @Override
    public void onEnable(){
        this.registerEvents();
    }

    @Override
    public void onDisable(){

    }

    private void registerEvents(){
        this.saveDefaultConfig();
        messages = new Messages(this, "messages.yml");

        if(getConfig().get("End-Time") != null){
            endTime = getConfig().getLong("End-Time");
        }

        this.getServer().getPluginManager().registerEvents(new ServerListPingListener(this), this);
        getCommand("motdcountdown").setExecutor(new motdcountdown(this));
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
