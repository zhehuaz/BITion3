package me.zhehua.bition3.config;


import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Set;

public class PreferenceHelper {
    public final static String PREFERENCE_NAME = "preference_name";

    private final static String KEY_CURRENT_STATUS = "current_status";
    private final static String KEY_USER_NAME = "user_name";
    private final static String KEY_USER_PWD = "user_password";
    private final static String KEY_IS_AUTO_LOGIN = "is_auto_login";
    private final static String KEY_IS_AUTO_LOGOUT = "is_auto_logout";
    private final static String KEY_IS_SILENT = "is_silent";
    private final static String KEY_AUTO_SSIDS = "auto_ssids";
    private final static String KEY_RELOG_INTERVAL = "relog_interval";
    private final static String KEY_LISTEN_INTERVAl = "listen_interval";

    public static void saveCurrentStatus(@NonNull SharedPreferences sp, int status) {
        sp.edit().putInt(KEY_CURRENT_STATUS, status).commit();
    }

    public static int getCurrentStatus(@NonNull SharedPreferences sp) {
        return sp.getInt(KEY_CURRENT_STATUS, 0);
    }

    public static void saveUserInfo(@NonNull SharedPreferences sp, String userName, String pwd) {
        sp.edit().putString(KEY_USER_NAME, userName)
                .putString(KEY_USER_PWD, pwd)
                .apply();
    }

    public static void getUsername(@NonNull SharedPreferences sp) {
        sp.getString(KEY_USER_NAME, "");
    }

    public static void saveIsAutoLogin(@NonNull SharedPreferences sp, boolean isAutoLogin) {
        sp.edit().putBoolean(KEY_IS_AUTO_LOGIN, isAutoLogin).apply();
    }

    public static boolean isAutoLogin(@NonNull SharedPreferences sp) {
        return sp.getBoolean(KEY_IS_AUTO_LOGIN, true);
    }

    public static void saveIsAutoLogout(@NonNull SharedPreferences sp, boolean isAutoLogout) {
        sp.edit().putBoolean(KEY_IS_AUTO_LOGOUT, isAutoLogout).apply();
    }

    public static boolean isAutoLogout(@NonNull SharedPreferences sp) {
        return sp.getBoolean(KEY_IS_AUTO_LOGOUT, false);
    }

    public static void saveIsSilent(@NonNull SharedPreferences sp, boolean isSilent) {
        sp.edit().putBoolean(KEY_IS_SILENT, isSilent).apply();
    }

    public static boolean isSilent(@NonNull SharedPreferences sp) {
        return sp.getBoolean(KEY_IS_SILENT, false);
    }

    /**
     * Override the previous SSIDs.
     * @param sp
     * @param ssidSet
     */
    public static void saveAutoSsid(@NonNull SharedPreferences sp, Set<String> ssidSet) {
        sp.edit().putStringSet(KEY_AUTO_SSIDS, ssidSet).apply();
    }

    public static boolean addAutoSsid(@NonNull SharedPreferences sp, String ssid) {
        Set<String> ssidSet = sp.getStringSet(KEY_AUTO_SSIDS, null);
        if (ssidSet != null && !ssidSet.contains(ssid)) {
            ssidSet.add(ssid);
            return true;
        }
        return false;
    }

    public static void removeAutoSsid(@NonNull SharedPreferences sp, String ssid) {
        Set<String> ssidSet = sp.getStringSet(KEY_AUTO_SSIDS, null);
        if (ssidSet != null) {
            ssidSet.remove(ssid);
        }
        sp.edit().putStringSet(KEY_AUTO_SSIDS, ssidSet);
    }

    public static Set<String> getAutoSsids(@NonNull SharedPreferences sp) {
        return sp.getStringSet(KEY_AUTO_SSIDS, null);
    }

    public static boolean isAutoSsid(@NonNull SharedPreferences sp, String ssid) {
        Set<String> autoSsids = getAutoSsids(sp);
        if (autoSsids != null) {
            if (autoSsids.contains(trimSsid(ssid)))
                return true;
        }
        return false;
    }

    public static String trimSsid(String ssid) {
        if(ssid != null && ssid.length() > 0 && !ssid.equals("<unknown ssid>")) {
            if(ssid.length() > 2 && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                return ssid.substring(1, ssid.length() - 1);
            } else {
                return ssid;
            }
        }
        return "";
    }


    public static void saveRelogInterval(@NonNull SharedPreferences sp, int interval) {
        sp.edit().putInt(KEY_RELOG_INTERVAL, interval).apply();
    }

    public static int getRelogInterval(@NonNull SharedPreferences sp) {
        return sp.getInt(KEY_RELOG_INTERVAL, 10000);
    }

    public static void saveListenInterval(@NonNull SharedPreferences sp, int interval) {
        sp.edit().putInt(KEY_LISTEN_INTERVAl, interval).apply();
    }

    public static int getListenInterval(@NonNull SharedPreferences sp) {
        return sp.getInt(KEY_LISTEN_INTERVAl, 15000);
    }

}
