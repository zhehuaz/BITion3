package me.zhehua.bition3.connection;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import me.zhehua.bition3.config.PreferenceHelper;

/**
 * Created by zhehuaz on 3/15/16.
 */
public class ConnCheckAlarmManger {
    public final static String CONNECTION_CHECK_ACTION = "me.zhehua.bition3.action.CONNECTION_CHECK";

    public static void startListening(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceHelper.PREFERENCE_NAME, Context.MODE_PRIVATE);
        int interval = PreferenceHelper.getListenInterval(sharedPreferences);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(CONNECTION_CHECK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pendingIntent);
    }

    public static void StopListening(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(CONNECTION_CHECK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

    }
}
