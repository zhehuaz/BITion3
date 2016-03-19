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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ConnCheckReceiver extends BroadcastReceiver {
    private final static String TAG = "ConnCheckReceiver";
    private final static String tryUrl = "http://news-at.zhihu.com/api/4/version/android/2.3.0";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(TAG, "Check broadcast received.");
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(tryUrl)
                .get().build();
        final SharedPreferences sharedPreferences
                = context.getSharedPreferences(PreferenceHelper.PREFERENCE_NAME, Context.MODE_PRIVATE);

        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Response response = client.newCall(request).execute();
                    subscriber.onNext(response.body().string());
                    response.body().close();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<String>>() {

                    @Override
                    public Observable<String> call(String responseContent) {
                        Log.i(TAG, "check response: " + responseContent);
                        if (!responseContent.matches("\\{\"status\".*")) {
                            String username = PreferenceHelper.getUsername(sharedPreferences);
                            String password = PreferenceHelper.getPassword(sharedPreferences);
                            return LoginHelper.asyncLogin(username, password);
                        }
                        return Observable.empty();
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {

                    @Override
                    public Observable<String> call(String s) {
                        switch (s) {
                            case LoginHelper.RESPONSE_LOGIN_OK:
                                ConnCheckAlarmManger.startListening(context);
                                return Observable.empty();
                            case LoginHelper.RESPONSE_ONLINE_NUM_ERROR:
                                if (PreferenceHelper.isAutoLogout(sharedPreferences)) {
                                    return LoginHelper.asyncLogout();
                                } else {
                                    // TODO notification
                                }
                                break;
                            case LoginHelper.RESPONSE_USERNAME_ERROR:
                                Log.w(TAG, "handle username error");
                                break;
                        }
                        return Observable.empty();
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "final completed");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.i(TAG, "final error");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "final next:" + s);

                        if (s.equals(LoginHelper.RESPONSE_LOGOUT_OK)) {
                            try {
                                Log.i(TAG, "current thread:" + Thread.currentThread().getName());
                                Thread.sleep(PreferenceHelper.getRelogInterval(sharedPreferences));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            context.sendBroadcast(new Intent(ConnCheckAlarmManger.CONNECTION_CHECK_ACTION));
                        }

                    }
                });
    }

}
