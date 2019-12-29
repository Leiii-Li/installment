package com.william.installment;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import cn.jpush.sms.SMSSDK;

/**
 * <pre>
 *      @author  : Nelson
 *      @since   : 2019/12/29
 *      github  : https://github.com/Nelson-KK
 *      desc    :
 * </pre>
 */
public class CustomeApplication extends Application {

    public static final String TAG = CustomeApplication.class.getSimpleName();
    public static Context mContext;
    private static final int CHECK_THREAD = 1001;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SMSSDK.getInstance().initSdk(mContext);
    }

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == CHECK_THREAD) {
                Log.i(TAG, "[nelson] -- Check");
                Utils.getWebsiteDatetime().observeForever(new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean response) {
                        Log.i(TAG, "[nelson] -- onChanged : " + response);
                        if (!response) {
                            System.exit(0);
                        }
                        mHandler.sendEmptyMessageDelayed(CHECK_THREAD, 10000);
                    }
                });
            }
        }
    };

    public static void check() {
        mHandler.sendEmptyMessage(CHECK_THREAD);
    }
}
