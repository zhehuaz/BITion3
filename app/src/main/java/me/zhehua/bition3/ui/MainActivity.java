package me.zhehua.bition3.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.zhehua.bition3.R;
import me.zhehua.bition3.events.LoginFailureEvent;
import me.zhehua.bition3.events.LoginSuccessEvent;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onLoginSuccess(LoginSuccessEvent event) {

    }

    @Subscribe
    public void onLoginFailure(LoginFailureEvent event) {

    }
}
