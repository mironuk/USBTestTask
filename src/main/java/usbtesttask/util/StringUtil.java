package usbtesttask.util;

public class StringUtil {

    private StringUtil() {
    }

    public static String removeUnderscores(String str) {
        return str.replaceAll("_", "");
    }

    public static String getText(char[] chars, int start, int length) {
        return new String(chars, start, length);
    }

}
