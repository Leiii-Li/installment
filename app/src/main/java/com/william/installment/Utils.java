package com.william.installment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * <pre>
 *      @author  : Nelson
 *      @since   : 2019/12/29
 *      github  : https://github.com/Nelson-KK
 *      desc    :
 * </pre>
 */
public class Utils {

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String END_TIME = "2019-12-31 12:00:12";
    public static final String PHONE_NUMBER_PATTERN = "^((13[0-9])|(14[1456789])|(15[012356789])|(16[567])|(17[01345678])|(18[0-9])|(19[189]))\\d{8}$";

    public static LiveData<Boolean> getWebsiteDatetime() {
        final MutableLiveData<Boolean> response = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
                    URL url = new URL("http://www.baidu.com");// 取得资源对象
                    URLConnection uc = url.openConnection();// 生成连接对象
                    uc.connect();// 发出连接
                    long ld = uc.getDate();// 读取网站日期时间
                    Date date = new Date(ld);// 转换为标准时间对象
                    long endTimeMillions = simpleDateFormat.parse(Utils.END_TIME).getTime();

                    if (endTimeMillions < date.getTime()) {
                        response.postValue(false);
                    } else {
                        response.postValue(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return response;
    }

    public static boolean isPhoneNumber(String phoneNumber){
        return Pattern.matches(PHONE_NUMBER_PATTERN,phoneNumber);
    }
}
