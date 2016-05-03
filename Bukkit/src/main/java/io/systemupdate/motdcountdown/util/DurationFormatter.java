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

package io.systemupdate.motdcountdown.util;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang.time.DurationFormatUtils.formatDuration;


public class DurationFormatter {

    public static String formatDurationWords(long durationMillis, boolean suppressLeadingZeroElements, boolean suppressTrailingZeroElements) {
        String duration = formatDuration(durationMillis, "d\' days, \'H\' hours, \'m\' minutes, \'s\' seconds\'");
        String tmp;
        if(suppressLeadingZeroElements) {
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
        }

        if(suppressTrailingZeroElements) {
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
