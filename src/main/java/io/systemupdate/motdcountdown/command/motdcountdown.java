package io.systemupdate.motdcountdown.command;

import io.systemupdate.motdcountdown.MOTDCountdown;
import io.systemupdate.motdcountdown.util.DurationFormatter;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Copyright SystemUpdate (https://systemupdate.io) to present.
 * Please see included licence file for licensing terms.
 * File created on 15/04/2016.
 */
public class motdcountdown implements CommandExecutor{

    private MOTDCountdown plugin;

    public motdcountdown(MOTDCountdown plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]){
        if(cmd.getName().equalsIgnoreCase("motdcountdown")){
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
        }
        return false;
    }
}
