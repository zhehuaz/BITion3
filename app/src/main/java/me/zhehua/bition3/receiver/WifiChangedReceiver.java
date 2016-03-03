package me.zhehua.bition3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by Administrator on 2016/2/28.
 */
public class WifiChangedReceiver extends BroadcastReceiver {
    public final static String TAG = "WifiChangedReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.wifi.supplicant.CONNECTION_CHANGE")) {
            Log.i(TAG, intent.getAction() + " received: is wifi enabled " + intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false));
        } else if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
            Log.i(TAG, intent.getAction() + " received: " + intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN));
        } else if (intent.getAction().equals("android.net.wifi.STATE_CHANGED")) {
//            int type = intent.getIntExtra(ConnectivityManager.EXTRA_NETWORK_TYPE, -1);
//            intent.getIntExtra();
//            NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).get;
            Log.i(TAG, intent.getAction() + " received.");
        } else if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            Log.i(TAG, intent.getAction() + " received.");
        }
    }
}
