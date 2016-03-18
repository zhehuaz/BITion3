package me.zhehua.bition3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.xml.sax.helpers.LocatorImpl;

import me.zhehua.bition3.config.PreferenceHelper;
import me.zhehua.bition3.connection.ConnCheckAlarmManger;
import me.zhehua.bition3.events.WifiStateChangeEvent;

public class WifiChangedReceiver extends BroadcastReceiver {
    public final static String TAG = "WifiChangedReceiver";

    SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive()");
        sharedPreferences = context.getSharedPreferences(PreferenceHelper.PREFERENCE_NAME, Context.MODE_PRIVATE);
        NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

        if (networkInfo == null) {
            Log.i(TAG, "network info is null");
            EventBus.getDefault().post(new WifiStateChangeEvent());
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            NetworkInfo.State state = networkInfo.getState();
            Log.i(TAG, networkInfo.getTypeName() + " state is " + state.toString());
            EventBus.getDefault().post(new WifiStateChangeEvent());

            if (PreferenceHelper.isAutoLogin(sharedPreferences)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                String currentSsid = wifiManager.getConnectionInfo().getSSID();
                if (PreferenceHelper.isAutoSsid(sharedPreferences, currentSsid)) {
                    Intent checkIntent = new Intent(ConnCheckAlarmManger.CONNECTION_CHECK_ACTION);
                    context.sendBroadcast(checkIntent);
                    Log.i(TAG, "Check broadcast sent.");
                }
            } else {

            }
        }
    }
}
