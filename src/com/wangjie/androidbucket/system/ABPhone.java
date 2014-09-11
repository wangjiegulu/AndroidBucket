package com.wangjie.androidbucket.system;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.wangjie.androidbucket.utils.ABTextUtil;

/**
 * Created by wangjie on 6/10/14.
 */
public class ABPhone {

    /**
     * 拨打电话，直接拨打
     *
     * @param context
     * @param phoneNumber
     */
    public static void call(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 拨打电话，现实拨号界面
     *
     * @param context
     * @param phoneNumber
     */
    public static void callDial(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 发送短信息，跳转到发送短信界面
     *
     * @param context
     * @param phoneNumber
     * @param content
     */
    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + (ABTextUtil.isEmpty(phoneNumber) ? "": phoneNumber));
        //Uri uri = Uri.parse("smsto:"); //不填写收件人
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", ABTextUtil.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }


}
