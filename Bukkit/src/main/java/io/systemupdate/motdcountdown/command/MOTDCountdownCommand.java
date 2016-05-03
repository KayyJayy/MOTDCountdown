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

package io.systemupdate.motdcountdown.command;

import io.systemupdate.motdcountdown.MOTDCountdown;
import io.systemupdate.motdcountdown.util.DurationFormatter;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MOTDCountdownCommand implements CommandExecutor{

    private MOTDCountdown plugin;

    public MOTDCountdownCommand(MOTDCountdown plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]){
        if(!sender.hasPermission("motdcountdown.command") && sender instanceof Player){
            sender.sendMessage(plugin.getMessages().getText("Generic.NoPermission"));
            return true;
        }

        if(!(args.length >= 1)){
            sender.sendMessage(plugin.getMessages().getText("Command.Invalid-Usage"));
            return true;
        }

        StringBuilder stringBuilder = new StringBuilder();
        String subcommand = args[0];

        switch (subcommand.toLowerCase()){
            case "settime":
                if(!(args.length >= 2)){
                    sender.sendMessage(plugin.getMessages().getText("Command.SetTime.Invalid-Usage"));
                    return true;
                }

                long duration = DurationFormatter.parse(args[1]);

                if(duration == -1){
                    sender.sendMessage(plugin.getMessages().getText("Command.SetTime.Invalid-Input"));
                    return true;
                }

                sender.sendMessage(plugin.getMessages().getText("Command.SetTime.Output")
                        .replace("{time}", DurationFormatUtils.formatDurationWords(duration, true, true)));
                plugin.setEndTime(duration + System.currentTimeMillis());
                break;
            case "setrunningmotd":
                if(!(args.length > 1)){
                    sender.sendMessage(plugin.getMessages().getText("Command.SetRunningMOTD.Invalid-Usage"));
                    return true;
                }

                for(int i = 1; i < args.length; i++){
                    stringBuilder.append(args[i]).append(" ");
                }

                sender.sendMessage(plugin.getMessages().getText("Command.SetRunningMOTD.Output"));
                plugin.setRunningMOTD(stringBuilder.toString()
                        .replace("\\n", "{newLine}"));
                break;
            case "setcompletedmotd":
                if(!(args.length > 1)){
                    sender.sendMessage(plugin.getMessages().getText("Command.SetCompletedMOTD.Invalid-Usage"));
                    return true;
                }

                for(int i = 1; i < args.length; i++){
                    stringBuilder.append(args[i]).append(" ");
                }

                sender.sendMessage(plugin.getMessages().getText("Command.SetCompletedMOTD.Output"));
                plugin.setCompletedMOTD(stringBuilder.toString()
                        .replace("\\n", "{newLine}"));
                break;
            default:
                sender.sendMessage(plugin.getMessages().getText("Command.Invalid-Usage"));
                break;
        }
        return false;
    }
}
