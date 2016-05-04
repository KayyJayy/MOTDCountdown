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

package io.systemupdate.motdcountdown.bungee.listener;

import io.systemupdate.motdcountdown.bungee.MOTDCountdown;
import compact.org.apache.commons.lang.time.DurationFormatter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener{

    private MOTDCountdown plugin;

    public ProxyPingListener(MOTDCountdown plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onProxyPing(ProxyPingEvent event){
        if(event.getResponse() == null){
            return;
        }

        if(plugin.getEndTime() == -1 || plugin.getEndTime() < System.currentTimeMillis()){
            event.getResponse().setDescription(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MOTD.Completed")
                    .replace("{newLine}", "\n")));
            return;
        }

        event.getResponse().setDescription(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MOTD.Running")
                .replace("{newLine}", "\n")
                .replace("{time}", DurationFormatter.formatDurationWords(plugin.getEndTime() - System.currentTimeMillis(), true, true))));
    }
}
