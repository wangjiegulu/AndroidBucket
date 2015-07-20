package com.wangjie.androidbucket.system.appinfo;

import android.content.Context;
import android.os.Build;
import com.wangjie.androidbucket.utils.ABAppUtil;

import java.io.Serializable;

/**
 * Created by zoe on 15/1/6.
 */
public class ABAppInfo implements Serializable {
    /**
     * deviceId <设备ID，可以去IMEI值, 只要用于确定该设备即可>
     */
    private String deviceId;

    /**
     * appVer:   <app版本>
     */
    private String appVer;

    /**
     * os:       <app运行系统>
     */
    private String os;

    /**
     * osVer:    <app运行系统版本>
     */
    private String osVer;

    /**
     * model:    <手机型号>
     */
    private String model;

    /**
     * nw: 网络状况
     */
    private Integer nw;

    /**
     * 渠道
     */
    private String channel;

    @Override
    public String toString() {
        return "AppInfo{" +
                "deviceId='" + deviceId + '\'' +
                ", appVer='" + appVer + '\'' +
                ", os='" + os + '\'' +
                ", osVer='" + osVer + '\'' +
                ", model='" + model + '\'' +
                ", nw=" + nw +
                ", channel='" + channel + '\'' +
                '}';
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNw() {
        return nw;
    }

    public void setNw(int nw) {
        this.nw = nw;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public static ABAppInfo getCurrentDeviceAppInfo(Context context) {
        ABAppInfo ABAppInfo = new ABAppInfo();
        ABAppInfo.setDeviceId(ABAppUtil.getDeviceIMEI(context));
        ABAppInfo.setAppVer(ABAppUtil.getAppVersion());
        ABAppInfo.setOs("android");
        ABAppInfo.setOsVer(Build.VERSION.RELEASE);
        ABAppInfo.setModel(Build.MODEL);
        ABAppInfo.setNw(ABAppUtil.getNetWorkStatus(context));
        ABAppInfo.setChannel("default");
        return ABAppInfo;
    }
}
