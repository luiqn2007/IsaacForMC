package lq2007.mcmod.isaacmod.debug;

import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Debugger {

    public static Map<String, Object> debugData = new HashMap<>();
    public static Logger logger;

    public static void putValue(String key, Object value) {
        debugData.put(key, value);
    }
    public static boolean updateValue(String key, Object value) {
        Object oldValue = debugData.put(key, value);
        return !Objects.equals(oldValue, value);
    }
    public static void removeValue(String key) {
        debugData.remove(key);
    }
    public static <T> T getValue(String key) {
        Object o = debugData.get(key);
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }
    public static <T> T getValue(String key, T defaultValue) {
        Object o = debugData.getOrDefault(key, defaultValue);
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public static void warn(String message, Object... parameters) {
        if (logger != null) {
            logger.warn(message, parameters);
        } else {
            System.out.println(buildMessage(message, parameters));
        }
    }
    public static void error(String message, Object... parameters) {
        if (logger != null) {
            logger.error(message, parameters);
        } else {
            System.err.println(buildMessage(message, parameters));
        }
    }

    private static String buildMessage(String message, Object... parameters) {
        String newValue = message.replaceAll("\\{}", "%s");
        String[] parameterValues = new String[parameters.length];
        for (int i = 0; i < parameterValues.length; i++) {
            parameterValues[i] = String.valueOf(parameters[i]);
        }
        return String.format(newValue, (Object[]) parameterValues);
    }
}
