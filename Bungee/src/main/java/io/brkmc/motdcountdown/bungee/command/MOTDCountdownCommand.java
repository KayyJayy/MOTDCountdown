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

package io.brkmc.motdcountdown.bungee.command;

import compact.org.apache.commons.lang.time.DurationFormatter;
import io.brkmc.motdcountdown.bungee.MOTDCountdown;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class MOTDCountdownCommand extends Command{

    private MOTDCountdown plugin;

    public MOTDCountdownCommand(MOTDCountdown plugin){
        super("motdcountdown", "motdcountdown.command", "motd", "motdcd", "motdc");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(args.length >= 1)){
            sender.sendMessage(TextComponent.fromLegacyText(plugin.getMessages().getMessage("Command.Invalid-Usage")));
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        String subcommand = args[0];

        switch (subcommand.toLowerCase()){
            case "settime":
                if(!(args.length >= 2)){
                    sender.sendMessage(TextComponent.fromLegacyText(plugin.getMessages().getMessage("Command.SetTime.Invalid-Usage")));
                    return;
                }

                long duration = DurationFormatter.parse(args[1]);

                if(duration == -1){
                    sender.sendMessage(TextComponent.fromLegacyText(plugin.getMessages().getMessage("Command.SetTime.Invalid-Input")));
                    return;
                }

                sender.sendMessage(TextComponent.fromLegacyText(plugin.getMessages().getMessage("Command.SetTime.Output")
                        .replace("{time}", DurationFormatter.formatDurationWords(duration, true, true))));
                plugin.setEndTime(duration + System.currentTimeMillis());
                break;
            case "setrunningmotd":
                if(!(args.length > 1)){
                    sender.sendMessage(TextComponent.fromLegacyText(plugin.getMessages().getMessage("Command.SetRunningMOTD.Invalid-Usage")));
                    return;
                }

                for(int i = 1; i < args.length; i++){
                    stringBuilder.append(args[i]).append(" ");
                }

                sender.sendMessage(TextComponent.fromLegacyText(plugin.getMessages().getMessage("Command.SetRunningMOTD.Output")));
                plugin.setRunningMOTD(stringBuilder.toString()
                        .replace("\\n", "{newLine}"));
                break;
            case "setcompletedmotd":
                if(!(args.length > 1)){
                    sender.sendMessage(TextComponent.fromLegacyText(plugin.getMessages().getMessage("Command.SetCompletedMOTD.Invalid-Usage")));
                    return;
                }

                for(int i = 1; i < args.length; i++){
                    stringBuilder.append(args[i]).append(" ");
                }

                sender.sendMessage(TextComponent.fromLegacyText(plugin.getMessages().getMessage("Command.SetCompletedMOTD.Output")));
                plugin.setCompletedMOTD(stringBuilder.toString()
                        .replace("\\n", "{newLine}"));
                break;
            default:
                sender.sendMessage(TextComponent.fromLegacyText(plugin.getMessages().getMessage("Command.Invalid-Usage")));
                break;
        }
    }
}
