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

package io.brkmc.motdcountdown.bukkit.util;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang.time.DurationFormatUtils.formatDuration;


public class DurationFormatter {

    public static String formatDurationWords(long durationMillis) {
        String duration = formatDuration(durationMillis, "d\' days, \'H\' hours, \'m\' minutes, \'s\' seconds\'");
        String tmp;
        duration = " " + duration;
        tmp = StringUtils.replaceOnce(duration, " 0 days,", "");
        if(tmp.length() != duration.length()) {
            duration = tmp;
            tmp = StringUtils.replaceOnce(tmp, " 0 hours,", "");
            if(tmp.length() != duration.length()) {
                tmp = StringUtils.replaceOnce(tmp, " 0 minutes,", "");
                duration = tmp;
                if(tmp.length() != tmp.length()) {
                    duration = StringUtils.replaceOnce(tmp, " 0 seconds", "");
                }
            }
        }

        if(duration.length() != 0) {
            duration = duration.substring(1);
        }

        tmp = StringUtils.replaceOnce(duration, " 0 seconds", "");
        if(tmp.length() != duration.length()) {
            duration = tmp;
            tmp = StringUtils.replaceOnce(tmp, " 0 minutes,", "");
            if(tmp.length() != duration.length()) {
                duration = tmp;
                tmp = StringUtils.replaceOnce(tmp, " 0 hours,", "");
                if(tmp.length() != duration.length()) {
                    duration = StringUtils.replaceOnce(tmp, " 0 days,", "");
                }
            }
        }

        duration = " " + duration;
        duration = StringUtils.replaceOnce(duration, " 1 seconds", " 1 second");
        duration = StringUtils.replaceOnce(duration, " 1 minutes", " 1 minute");
        duration = StringUtils.replaceOnce(duration, " 1 hours", " 1 hour");
        duration = StringUtils.replaceOnce(duration, " 1 days", " 1 day");
        return duration.trim();
    }

    public static long parse(String input) {
        if(input != null && !input.isEmpty()) {
            long result = 0L;
            StringBuilder number = new StringBuilder();

            for(int i = 0; i < input.length(); ++i) {
                char c = input.charAt(i);
                if(Character.isDigit(c)) {
                    number.append(c);
                } else {
                    String str;
                    if(Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                        result += convert(Integer.parseInt(str), c);
                        number = new StringBuilder();
                    }
                }
            }

            return result;
        } else {
            return -1L;
        }
    }

    private static long convert(int value, char unit) {
        switch(unit) {
            case 'M':
                return (long)value * TimeUnit.DAYS.toMillis(30L);
            case 'd':
                return (long)value * TimeUnit.DAYS.toMillis(1L);
            case 'h':
                return (long)value * TimeUnit.HOURS.toMillis(1L);
            case 'm':
                return (long)value * TimeUnit.MINUTES.toMillis(1L);
            case 's':
                return (long)value * TimeUnit.SECONDS.toMillis(1L);
            case 'y':
                return (long)value * TimeUnit.DAYS.toMillis(365L);
            default:
                return -1L;
        }
    }
}
