package lq2007.mcmod.isaacmod.util;

import net.minecraft.util.StringUtils;

public class StringHelper {

    public static String toUCamelCase(String name) {
        if (StringUtils.isNullOrEmpty(name)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : name.split("_")) {
            sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
        }
        return sb.toString();
    }

    public static String toUpperCaseName(String name) {
        if (StringUtils.isNullOrEmpty(name)) {
            return "";
        }
        StringBuilder sb = new StringBuilder().append(Character.toUpperCase(name.charAt(0)));
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_').append(c);
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }

    public static String toLowerCaseName(String name) {
        if (StringUtils.isNullOrEmpty(name)) {
            return "";
        }
        StringBuilder sb = new StringBuilder().append(Character.toLowerCase(name.charAt(0)));
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
