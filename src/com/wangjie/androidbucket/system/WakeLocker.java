package com.wangjie.androidbucket.system;

import android.content.Context;
import android.os.PowerManager;
import com.wangjie.androidbucket.log.Logger;

/**
 * 唤醒和释放手机
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-7-31
 * Time: 上午10:24
 */
public abstract class WakeLocker {
    private static final String TAG = WakeLocker.class.getSimpleName();
    private static PowerManager.WakeLock wakeLock;

    public static void acquire(Context ctx) {
        if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
//        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
//                PowerManager.ACQUIRE_CAUSES_WAKEUP |
//                PowerManager.ON_AFTER_RELEASE, "TAG");
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
        wakeLock.acquire();
    }

    public static void release() {

        synchronized (WakeLocker.class) {
            // sanity check for null as this is a public method
            if (wakeLock != null) {
                Logger.v(TAG, "Releasing wakelock");
                try {
                    wakeLock.release();
                } catch (Throwable th) {
                    // ignoring this exception, probably wakeLock was already released
                    Logger.e(TAG, "ignoring this exception, probably wakeLock was already released, ", th);
                }
            } else {
                // should never happen during normal workflow
                Logger.w(TAG, "Wakelock reference is null");
            }
            wakeLock = null;
        }

    }
}
