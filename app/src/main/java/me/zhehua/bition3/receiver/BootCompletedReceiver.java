package me.zhehua.bition3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2016/2/28.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    public final static String TAG = "BootCompletedReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, intent.getAction() + " received");
    }
}
