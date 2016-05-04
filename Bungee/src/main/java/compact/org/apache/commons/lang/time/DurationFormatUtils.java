package compact.org.apache.commons.lang.time;

import compact.org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class DurationFormatUtils {
    private static final Object y = "y";
    private static final Object M = "M";
    private static final Object d = "d";
    private static final Object H = "H";
    private static final Object m = "m";
    private static final Object s = "s";
    private static final Object S = "S";

    public static String formatDuration(long durationMillis, String format) {
        return formatDuration(durationMillis, format, true);
    }

    public static String formatDuration(long durationMillis, String format, boolean padWithZeros) {
        Token[] tokens = lexx(format);

        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int milliseconds = 0;

        if(Token.containsTokenWithValue(tokens, d)) {
            days = (int)(durationMillis / 86400000L);
            durationMillis -= (long)days * 86400000L;
        }

        if(Token.containsTokenWithValue(tokens, H)) {
            hours = (int)(durationMillis / 3600000L);
            durationMillis -= (long)hours * 3600000L;
        }

        if(Token.containsTokenWithValue(tokens, m)) {
            minutes = (int)(durationMillis / 60000L);
            durationMillis -= (long)minutes * 60000L;
        }

        if(Token.containsTokenWithValue(tokens, s)) {
            seconds = (int)(durationMillis / 1000L);
            durationMillis -= (long)seconds * 1000L;
        }

        if(Token.containsTokenWithValue(tokens, S)) {
            milliseconds = (int)durationMillis;
        }

        return format(tokens, 0, 0, days, hours, minutes, seconds, milliseconds, padWithZeros);
    }

    private static String format(Token[] tokens, int years, int months, int days, int hours, int minutes, int seconds, int milliseconds, boolean padWithZeros) {
        StringBuffer buffer = new StringBuffer();
        boolean lastOutputSeconds = false;
        int sz = tokens.length;

        for(int i = 0; i < sz; ++i){
            Token token = tokens[i];
            Object value = token.getValue();
            int count = token.getCount();
            if(value instanceof StringBuffer) {
                buffer.append(value.toString());
            } else if(value == y) {
                buffer.append(padWithZeros? StringUtils.leftPad(Integer.toString(years), count, '0'):Integer.toString(years));
                lastOutputSeconds = false;
            } else if(value == M) {
                buffer.append(padWithZeros?StringUtils.leftPad(Integer.toString(months), count, '0'):Integer.toString(months));
                lastOutputSeconds = false;
            } else if(value == d) {
                buffer.append(padWithZeros?StringUtils.leftPad(Integer.toString(days), count, '0'):Integer.toString(days));
                lastOutputSeconds = false;
            } else if(value == H) {
                buffer.append(padWithZeros?StringUtils.leftPad(Integer.toString(hours), count, '0'):Integer.toString(hours));
                lastOutputSeconds = false;
            } else if(value == m) {
                buffer.append(padWithZeros?StringUtils.leftPad(Integer.toString(minutes), count, '0'):Integer.toString(minutes));
                lastOutputSeconds = false;
            } else if(value == s) {
                buffer.append(padWithZeros?StringUtils.leftPad(Integer.toString(seconds), count, '0'):Integer.toString(seconds));
                lastOutputSeconds = true;
            } else if(value == S) {
                if(lastOutputSeconds) {
                    milliseconds += 1000;
                    String str = padWithZeros?StringUtils.leftPad(Integer.toString(milliseconds), count, '0'):Integer.toString(milliseconds);
                    buffer.append(str.substring(1));
                } else {
                    buffer.append(padWithZeros?StringUtils.leftPad(Integer.toString(milliseconds), count, '0'):Integer.toString(milliseconds));
                }

                lastOutputSeconds = false;
            }
        }

        return buffer.toString();
    }

    private static Token[] lexx(String format) {
        char[] array = format.toCharArray();
        ArrayList list = new ArrayList(array.length);
        boolean inLiteral = false;
        StringBuffer buffer = null;
        Token previous = null;
        int sz = array.length;

        for(int i = 0; i < sz; ++i) {
            char ch = array[i];
            if(inLiteral && ch != 39) {
                buffer.append(ch);
            } else {
                Object value = null;
                switch(ch) {
                    case '\'':
                        if(inLiteral) {
                            buffer = null;
                            inLiteral = false;
                        } else {
                            buffer = new StringBuffer();
                            list.add(new Token(buffer));
                            inLiteral = true;
                        }
                        break;
                    case 'H':
                        value = H;
                        break;
                    case 'M':
                        value = M;
                        break;
                    case 'S':
                        value = S;
                        break;
                    case 'd':
                        value = d;
                        break;
                    case 'm':
                        value = m;
                        break;
                    case 's':
                        value = s;
                        break;
                    case 'y':
                        value = y;
                        break;
                    default:
                        if(buffer == null) {
                            buffer = new StringBuffer();
                            list.add(new Token(buffer));
                        }

                        buffer.append(ch);
                }

                if(value != null) {
                    if(previous != null && previous.getValue() == value) {
                        previous.increment();
                    } else {
                        Token token = new Token(value);
                        list.add(token);
                        previous = token;
                    }

                    buffer = null;
                }
            }
        }

        return (Token[])list.toArray(new Token[list.size()]);
    }

    static class Token {
        private Object value;
        private int count;

        static boolean containsTokenWithValue(Token[] tokens, Object value) {
            int sz = tokens.length;

            for (int i = 0; i < sz; ++i) {
                if (tokens[i].getValue() == value) {
                    return true;
                }
            }

            return false;
        }

        Token(Object value) {
            this.value = value;
            this.count = 1;
        }

        Token(Object value, int count) {
            this.value = value;
            this.count = count;
        }

        void increment() {
            ++this.count;
        }

        int getCount() {
            return this.count;
        }

        Object getValue() {
            return this.value;
        }

        public boolean equals(Object obj2) {
            if (obj2 instanceof Token) {
                Token tok2 = (Token) obj2;
                if (this.value.getClass() == tok2.value.getClass()) if (this.count == tok2.count)
                    if ((this.value instanceof StringBuffer) ? this.value.toString().equals(tok2.value.toString()) : ((this.value instanceof Number) ? this.value.equals(tok2.value) : (this.value == tok2.value)))
                        return true;
                return false;
            } else {
                return false;
            }
        }
    }
}
