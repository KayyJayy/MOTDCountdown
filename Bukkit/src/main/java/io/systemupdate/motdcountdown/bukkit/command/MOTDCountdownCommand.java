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

package io.systemupdate.motdcountdown.bukkit.command;

import io.systemupdate.motdcountdown.bukkit.MOTDCountdown;
import io.systemupdate.motdcountdown.bukkit.util.DurationFormatter;
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
