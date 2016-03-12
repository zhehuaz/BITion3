package me.zhehua.bition3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import me.zhehua.bition3.BuildConfig;
import me.zhehua.bition3.events.WifiStateChangeEvent;
import me.zhehua.bition3.ui.MainActivity;

/**
 * Created by Administrator on 2016/2/28.
 */
public class WifiChangedReceiver extends BroadcastReceiver {
    public final static String TAG = "WifiChangedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            Log.i(TAG, "network info is null");
            EventBus.getDefault().post(new WifiStateChangeEvent());
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            NetworkInfo.State state = networkInfo.getState();
            Log.i(TAG, "state is " + state.toString());
            EventBus.getDefault().post(new WifiStateChangeEvent());
        }
    }
}
