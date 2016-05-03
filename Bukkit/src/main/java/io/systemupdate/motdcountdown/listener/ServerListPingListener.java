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

package io.systemupdate.motdcountdown.listener;

import io.systemupdate.motdcountdown.MOTDCountdown;
import io.systemupdate.motdcountdown.util.DurationFormatter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener{

    public MOTDCountdown motdCountdown;

    public ServerListPingListener(MOTDCountdown motdCountdown){
        this.motdCountdown = motdCountdown;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onServerListPing(ServerListPingEvent event){
        if(motdCountdown.getEndTime() == -1 || motdCountdown.getEndTime() < System.currentTimeMillis()){
            event.setMotd(ChatColor.translateAlternateColorCodes('&', motdCountdown.getConfig().getString("MOTD.Completed")
                    .replace("{newLine}", "\n")));
            return;
        }

        event.setMotd(ChatColor.translateAlternateColorCodes('&', motdCountdown.getConfig().getString("MOTD.Running")
                .replace("{newLine}", "\n")
                .replace("{time}", DurationFormatter.formatDurationWords(motdCountdown.getEndTime() - System.currentTimeMillis(), true, true))));
    }
}
