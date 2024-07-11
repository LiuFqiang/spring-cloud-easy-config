package icu.liufuqiang.config;

/**
 * @author liufuqiang
 * @Date 2024-07-11 10:44:07
 */
public class GlobalConfig {

    private static String applicationName;

    private String profiles;

    public static String getApplicationName() {
        return applicationName;
    }

    public static void setApplicationName(String applicationName) {
        GlobalConfig.applicationName = applicationName;
    }
}
