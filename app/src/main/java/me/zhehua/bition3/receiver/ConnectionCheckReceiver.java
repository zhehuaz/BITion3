package me.zhehua.bition3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
        Log.i(TAG, "Check broadcast received.");
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(tryUrl)
                .get().build();
        final SharedPreferences sharedPreferences
                = context.getSharedPreferences(PreferenceHelper.PREFERENCE_NAME, Context.MODE_PRIVATE);

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
                if (!responseContent.matches("\\{\"status\".*")) {
                    /* not signed in */
                    String username = PreferenceHelper.getUsername(sharedPreferences);
                    String password = PreferenceHelper.getPassword(sharedPreferences);
                    LoginHelper.asyncLogin(username, password, new LoginHelper.LoginCallBack() {
                        @Override
                        public void onSuccess() {
                            ConnCheckAlarmManger.startListening(context);
                        }

                        @Override
                        public void onFail(@Nullable String response) {
                            if (response != null) {
                                switch (response) {
                                    case LoginHelper.RESPONSE_ONLINE_NUM_ERROR:
                                        if (PreferenceHelper.isAutoLogout(sharedPreferences)) {
                                            LoginHelper.asyncLogout();
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                ConnCheckAlarmManger.stopListening(context);
                            }
                        }
                    });
                }
            }
        });
    }

}
