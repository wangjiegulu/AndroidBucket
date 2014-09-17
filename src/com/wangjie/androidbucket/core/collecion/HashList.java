package com.wangjie.androidbucket.core.collecion;

import java.util.*;

/***
 * 键值对索引排序的工具类 HashMap简单排序的一种实现
 *
 * @param <K>
 * @param <V>
 */
public class HashList<K, V> {

    /**
     * 键值集合{A, B, C...}
     * */
    private List<K> keyArr = new ArrayList<K>();
    /**
     * 键值对映射{A:add..., B:ball..., ...}
     */
    private HashMap<K, List<V>> map = new HashMap<K, List<V>>();
    /**
     * 键值分类
     */
    private KeySort<K, V> keySort;

    public HashList(KeySort<K, V> keySort) {
        this.keySort = keySort;
    }

    /**
     * 根据value值返回key
     * */

    public K getKey(V v) {
        return keySort.getKey(v);
    }

    /** 键值对排序 */
    public void sortKeyComparator(Comparator<K> comparator) {
        Collections.sort(keyArr, comparator);
    }

    /** 根据索引返回键值 */
    public K getKeyIndex(int key) {
        return keyArr.get(key);
    }

    /** 根据索引返回键值对 */
    public List<V> getValueListIndex(int key) {
        return map.get(getKeyIndex(key));
    }

    public V getValueIndex(int key, int value) {
        return getValueListIndex(key).get(value);

    }

    public int size() {
        return keyArr.size();
    }

    public void clear() {
        for (Iterator<K> it = map.keySet().iterator(); it.hasNext(); map.remove(it.next()));
    }

    public boolean contains(Object object) {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public Object remove(int location) {
        return null;
    }

    public boolean remove(Object object) {
        return false;
    }

    public boolean removeAll(Collection arg0) {
        return false;
    }

    public boolean retainAll(Collection arg0) {
        return false;
    }

    public Object set(int location, Object object) {
        return keyArr.set(location, (K) object);
    }

    public List subList(int start, int end) {
        return keyArr.subList(start, end);
    }

    public Object[] toArray() {
        return keyArr.toArray();
    }

    public Object[] toArray(Object[] array) {
        return keyArr.toArray(array);
    }

    public boolean add(V v) {
        K key = getKey(v);
        if (!map.containsKey(key)) {
            List<V> list = new ArrayList<V>();
            list.add(v);
            keyArr.add(key);
            map.put(key, list);
        } else {
            map.get(key).add(v);
        }
        return false;
    }

    public int indexOfKey(K k){
        return keyArr.indexOf(k);
    }

}