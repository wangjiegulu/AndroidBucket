package com.wangjie.androidbucket.utils.mission.retry;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.wangjie.androidbucket.log.Logger;

/**
 * 可重试的Mission
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/27/15.
 */
public abstract class RetryMission {
    private static final String TAG = RetryMission.class.getSimpleName();
    private static final int EXECUTE_MISSION = 0x4863;
    private static final long DEFAULT_DELAY = 60 * 1000l;
    /**
     * 当前对象是否处于激活状态
     */
    private boolean isActive;
    /**
     * 是否中断停止
     */
    private boolean isInterrupted;
    /**
     * 总共重试次数
     */
    private int retryCounts;
    /**
     * 重试剩余次数
     */
    private int leftCounts = 1;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case EXECUTE_MISSION: // 执行mission
                    prepareRunMission();
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 根据总共重试次数和剩余重试次数来获取一个当前下次执行时间间隔为多少ms
     *
     * @param retryCounts
     * @param leftCounts
     * @return
     */
    protected long currentDelay(int currentCount, int retryCounts, int leftCounts){
        return DEFAULT_DELAY;
    }

    /**
     * 执行真正的mission
     */
    protected abstract void runMission();

    /**
     * tryCount用完（leftCount为0），并且没有成功
     */
    protected void onFail(int errorCode, String errorMessage){}

    /**
     * 中断成功后回调
     */
    protected void onInterrupted() {
    }

    /**
     * 执行mission的准备
     */
    private void prepareRunMission() {
        if (!checkLeftCountsLegally()) {
            return;
        }
        leftCounts--;
        runMission();
    }


    /**
     * 启动并执行mission
     */
    public void execute(int retryCounts) {
        execute(retryCounts, 0l);
    }

    public void execute(int retryCounts, long delay) {
        if (isActive) {
            Logger.i(TAG, "RetryMission[" + this + "] is already been Activated..." + getCountsInfo());
            return;
        }
        reset();
        isActive = true;
        this.retryCounts = retryCounts < 0 ? 0 : retryCounts;
        this.leftCounts = retryCounts < 0 ? 1 : retryCounts + 1;
        delay = delay < 0 ? 0 : delay;
        handler.sendMessageDelayed(handler.obtainMessage(EXECUTE_MISSION), delay);
    }

    /**
     * retry mission
     */
    public void retry() {
        if (!checkLeftCountsLegally()) {
            return;
        }
        long delay = currentDelay(retryCounts - leftCounts, retryCounts, leftCounts);
        Logger.i(TAG, "Mission retry after " + delay + "ms..." + getCountsInfo());
        handler.sendMessageDelayed(handler.obtainMessage(EXECUTE_MISSION), delay);
    }

    /**
     * Check whether the leftCounts legally
     *
     * @return true, if the leftCounts legally;
     */
    private boolean checkLeftCountsLegally() {
        if (leftCounts <= 0) {
            isActive = false;
            onFail(-1, "尝试完，重试次数：" + retryCounts);
            return false;
        }
        return true;
    }

    /**
     * Check whether the mission has been activated.
     *
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * reset
     */
    private void reset() {
        isActive = false;
        isInterrupted = false;
        retryCounts = 0;
        leftCounts = 1;
    }

    /**
     * Interrupted current mission
     */
    public void interrupted() {
        if (!isActive) {
            return;
        }
        handler.removeMessages(EXECUTE_MISSION);
        isActive = false;
        isInterrupted = true;
        onInterrupted();
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    private String getCountsInfo() {
        return "currentMission[retryCounts: " + retryCounts + ", leftCounts: " + leftCounts + "]";
    }

    public int getLeftCounts() {
        return leftCounts;
    }

    public int getRetryCounts() {
        return retryCounts;
    }
}
