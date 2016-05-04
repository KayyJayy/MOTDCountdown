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
