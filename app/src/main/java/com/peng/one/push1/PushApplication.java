package com.peng.one.push1;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.myncic.push.OnePush;
import java.util.List;

/**
 * 这个是自定义的Application
 * Created by pyt on 2017/5/16.
 */

public class PushApplication extends Application {

    private static final String TAG = "PushApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        String currentProcessName = getCurrentProcessName();
        //只在主进程中注册(注意：umeng推送，除了在主进程中注册，还需要在channel中注册)
        if (BuildConfig.APPLICATION_ID.equals(currentProcessName) || BuildConfig.APPLICATION_ID.concat(":channel").equals(currentProcessName)) {
            //platformCode和platformName就是在<meta/>标签中，对应的"平台标识码"和平台名称
            OnePush.init(this, ((platformCode, platformName) -> {
                boolean result = false;
               if (RomUtils.isMiuiRom()) {
                    result=  platformCode == 101; //小米
                } else if (RomUtils.isHuaweiRom()) {
                    result= platformCode == 107; //华为
                } else if(RomUtils.isFlymeRom()){
                    result = platformCode == 103; //魅族
                }
                else {
//                    result= platformCode == 106; //极光
//                    result= platformCode == 105;  //个推
                    // OnePush拓展的友盟推送是版本v3.1.1a。
                    //使用的时候可能会出问题,关于utdid重复引入的问题或者SO的问题
                    result= platformCode == 104; //友盟
                }

                Log.e(TAG, "Register-> code: "+platformCode+" name: "+platformName+" result: "+result);
                //这里只能是一个被执行
                return result;
//                return platformCode == 101;
            }));
            OnePush.register();
        }
      Log.e(TAG, "onCreate: isFlymeRom:"+RomUtils.isFlymeRom());
    }

    /**
     * 获取当前进程名称
     *
     * @return processName
     */
    public String getCurrentProcessName() {
        int currentProcessId = Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            if (runningAppProcess.pid == currentProcessId) {
                return runningAppProcess.processName;
            }
        }
        return null;
    }

}
