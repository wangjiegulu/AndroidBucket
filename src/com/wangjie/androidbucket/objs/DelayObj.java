package com.wangjie.androidbucket.objs;

/**
 * Created by wangjie on 14-5-4.
 */
public class DelayObj<T> {
    private Class<? extends T> clazz;
    private T delayObj;

    public Class<? extends T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    public T getDelayObj() {
        return delayObj;
    }

    public void setDelayObj(T delayObj) {
        this.delayObj = delayObj;
    }

    @Override
    public String toString() {
        return "DelayObj{" +
                "clazz=" + clazz +
                ", delayObj=" + delayObj +
                '}';
    }
}
