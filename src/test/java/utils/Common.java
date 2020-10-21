package utils;

import java.util.*;

public class Common {

    public static String getConfigValue(String key) {
        ResourceBundle config = ResourceBundle.getBundle("config");
        return config.getString(key);
    }
}
