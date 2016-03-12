package me.zhehua.bition3.events;

/**
 * Created by Administrator on 2016/3/12.
 */
public class WifiStateChangeEvent {

    public final static int WIFI_STATE_CONNECTED = 0x0;
    public final static int WIFI_STATE_NO_CONNECT = 0x1;

    int state = WIFI_STATE_NO_CONNECT;


    public WifiStateChangeEvent() {
    }

    public WifiStateChangeEvent(int state) {
        this.state = state;
    }

    public int state() {
        return state;
    }
}
