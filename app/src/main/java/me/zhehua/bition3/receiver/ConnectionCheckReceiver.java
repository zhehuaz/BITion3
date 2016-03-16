package me.zhehua.bition3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import me.zhehua.bition3.config.PreferenceHelper;
import me.zhehua.bition3.connection.ConnCheckAlarmManger;
import me.zhehua.bition3.connection.LoginHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectionCheckReceiver extends BroadcastReceiver {
    private final static String TAG = "ConnectionCheckReceiver";
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
                ConnCheckAlarmManger.stopListening(context);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "Check connect response.");
                String responseContent = response.body().string();
                response.body().close();
                Log.i(TAG, "Response: " + responseContent);
                if (responseContent.length() > 200 /* && !responseContent.matches("^\\{\"status\"") */) {
                    // in the Intranet
                    SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceHelper.PREFERENCE_NAME, Context.MODE_PRIVATE);
                    String username = PreferenceHelper.getUsername(sharedPreferences);
                    String password = PreferenceHelper.getPassword(sharedPreferences);
                    LoginHelper.asyncLogin(username, password);
                }
            }
        });


    }
}
