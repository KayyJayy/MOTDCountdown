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
            sender.sendMessage(plugin.getMessages().getMessage("Command.Invalid-Usage"));
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        String subcommand = args[0];

        switch (subcommand.toLowerCase()){
            case "settime":
                if(!(args.length >= 2)){
                    sender.sendMessage(plugin.getMessages().getMessage("Command.SetTime.Invalid-Usage"));
                    return;
                }

                long duration = DurationFormatter.parse(args[1]);

                if(duration == -1){
                    sender.sendMessage(plugin.getMessages().getMessage("Command.SetTime.Invalid-Input"));
                    return;
                }

                //TextComponent message = plugin.getMessages().getMessage("Command.SetTime.Output");
                //message.setText(message.getText().replace("{time}", DurationFormatUtils.formatDurationWords(duration, true, true)));

                sender.sendMessage(new TextComponent("Set time"));
                plugin.setEndTime(duration + System.currentTimeMillis());
                break;
            case "setrunningmotd":
                if(!(args.length > 1)){
                    sender.sendMessage(plugin.getMessages().getMessage("Command.SetRunningMOTD.Invalid-Usage"));
                    return;
                }

                for(int i = 1; i < args.length; i++){
                    stringBuilder.append(args[i]).append(" ");
                }

                sender.sendMessage(plugin.getMessages().getMessage("Command.SetRunningMOTD.Output"));
                plugin.setRunningMOTD(stringBuilder.toString()
                        .replace("\\n", "{newLine}"));
                break;
            case "setcompletedmotd":
                if(!(args.length > 1)){
                    sender.sendMessage(plugin.getMessages().getMessage("Command.SetCompletedMOTD.Invalid-Usage"));
                    return;
                }

                for(int i = 1; i < args.length; i++){
                    stringBuilder.append(args[i]).append(" ");
                }

                sender.sendMessage(plugin.getMessages().getMessage("Command.SetCompletedMOTD.Output"));
                plugin.setCompletedMOTD(stringBuilder.toString()
                        .replace("\\n", "{newLine}"));
                break;
            default:
                sender.sendMessage(plugin.getMessages().getMessage("Command.Invalid-Usage"));
                break;
        }
    }
}
