package com.wangjie.androidbucket.utils.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/11/14.
 */
public class ABCollectionPicker {
    /**
     * 指定摘选规则
     *
     * @param <T>
     */
    public static interface ABICollectionPicker<T> {
        boolean isPicked(T target);
    }

    /**
     * 从collection中摘选满足条件的对象，对应的摘选条件由ABICollectionPicker的isPicked()来制定，只取第一个
     *
     * @param expect
     * @param collection
     * @param <T>
     * @return
     */
    public static <T extends ABICollectionPicker<T>> T pickFirst(T expect, Collection<T> collection) {
        T picked = null;
        for (T t : collection) {
            if (t.isPicked(expect)) {
                picked = t;
                break;
            }
        }
        return picked;
    }

    /**
     * 从collection中摘选满足条件的对象，对应的摘选条件由ABICollectionPicker的isPicked()来制定
     *
     * @param expect
     * @param collection
     * @param <T>
     * @return
     */
    public static <T extends ABICollectionPicker<T>> List<T> pick(T expect, Collection<T> collection) {
        List<T> picked = new ArrayList<>();
        for (T t : collection) {
            if (t.isPicked(expect)) {
                picked.add(t);
            }
        }
        return picked;
    }

}
