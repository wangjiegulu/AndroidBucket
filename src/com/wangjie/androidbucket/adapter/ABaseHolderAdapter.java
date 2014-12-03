package com.wangjie.androidbucket.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 实现对BaseAdapter中ViewHolder相关的简化
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-4-2
 * Time: 下午5:54
 */
public abstract class ABaseHolderAdapter extends BaseAdapter{
    Context context;

    protected ABaseHolderAdapter(Context context) {
        this.context = context;
    }

    protected ABaseHolderAdapter() {
    }

    /**
     * 各个控件的缓存
     */
    class ViewHolder{
        public SparseArray<View> views = new SparseArray<View>();

        /**
         * 指定resId和类型即可获取到相应的view
         * @param convertView
         * @param resId
         * @param <T>
         * @return
         */
        <T extends View> T obtainView(View convertView, int resId){
            View v = views.get(resId);
            if(null == v){
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T)v;
        }

    }

    /**
     * 改方法需要子类实现，需要返回item布局的resource id
     * @return
     */
    public abstract int itemLayoutRes();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(itemLayoutRes(), null);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return getView(position, convertView, parent, holder);
    }

    /**
     * 使用该getView方法替换原来的getView方法，需要子类实现
     * @param position
     * @param convertView
     * @param parent
     * @param holder
     * @return
     */
    public abstract View getView(int position, View convertView, ViewGroup parent, ViewHolder holder);


}
