package comv2.aunwesha.lfutil;

import java.util.List;

public final class StringUtil
{
    public static String binarySearch(final List<String> stringList, final String data) {
        final int _size = stringList.size();
        final int _checkIndex = _size / 2;
        final String _checkData = stringList.get(_checkIndex);
        final int _compareStatus = data.compareTo(_checkData);
        if (_compareStatus == 0) {
            return _checkData;
        }
        if (_size <= 1) {
            return null;
        }
        if (_compareStatus > 0) {
            return binarySearch(stringList.subList(_checkIndex, _size), data);
        }
        return binarySearch(stringList.subList(0, _checkIndex), data);
    }
    
    public static String splitCamelCase(final String string) {
        String result = null;
        if (!GenericUtil.isEmptyString(string)) {
            result = string.trim().replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
        }
        return capitalize(result);
    }
    
    public static String capitalize(final String name) {
        return capitalize(name, false);
    }
    
    public static String capitalize(final String name, final boolean forceLowerCase) {
        if (GenericUtil.isEmptyString(name)) {
            return name;
        }
        final char[] buffer = name.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < buffer.length; ++i) {
            final char c = buffer[i];
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            else if (capitalizeNext) {
                buffer[i] = Character.toUpperCase(c);
                capitalizeNext = false;
            }
            else if (forceLowerCase) {
                buffer[i] = Character.toLowerCase(c);
            }
        }
        return new String(buffer);
    }
    
    public static boolean containsAny(final String stringData, final String... matchData) {
        boolean match = false;
        for (final String string : matchData) {
            match = stringData.contains(string);
            if (match) {
                break;
            }
        }
        return match;
    }
    
    public static boolean startsWithAny(final String stringData, final String... matchData) {
        boolean match = false;
        for (final String string : matchData) {
            match = stringData.startsWith(string);
            if (match) {
                break;
            }
        }
        return match;
    }
    
    public static boolean endsWithAny(final String stringData, final String... matchData) {
        boolean match = false;
        for (final String string : matchData) {
            match = stringData.endsWith(string);
            if (match) {
                break;
            }
        }
        return match;
    }
}
