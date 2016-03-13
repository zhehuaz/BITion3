package me.zhehua.bition3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import me.zhehua.bition3.events.BootCompleteEvent;
import me.zhehua.bition3.service.LoginService;

/**
 * Created by Administrator on 2016/2/28.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    public final static String TAG = "BootCompletedReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, intent.getAction() + " received");
        Intent serviceIntent = new Intent(context, LoginService.class);
        context.startService(serviceIntent);
        EventBus.getDefault().post(new BootCompleteEvent());
    }
}
