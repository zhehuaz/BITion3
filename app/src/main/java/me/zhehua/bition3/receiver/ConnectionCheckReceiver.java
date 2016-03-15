package me.zhehua.bition3.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.IOException;

import me.zhehua.bition3.config.PreferenceHelper;
import me.zhehua.bition3.connection.LoginHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Created by zhehuaz on 3/15/16.
 */
public class ConnectionCheckReceiver extends BroadcastReceiver {
    private final static String tryUrl = "http://news-at.zhihu.com/api/4/version/android/2.3.0";

    @Override
    public void onReceive(final Context context, Intent intent) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(tryUrl)
                .get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceHelper.PREFERENCE_NAME, Context.MODE_PRIVATE);
                String username = PreferenceHelper.getUsername(sharedPreferences);
                String password = PreferenceHelper.getPassword(sharedPreferences);
                LoginHelper.asyncLogin(username, password);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }
}
