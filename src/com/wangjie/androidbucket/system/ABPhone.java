package com.wangjie.androidbucket.system;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by wangjie on 6/10/14.
 */
public class ABPhone {

    /**
     * 拨打电话，直接拨打
     * @param context
     * @param phoneNumber
     */
    public static void call(Context context, String phoneNumber){
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 拨打电话，现实拨号界面
     * @param context
     * @param phoneNumber
     */
    public static void callDial(Context context, String phoneNumber){
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }





}
