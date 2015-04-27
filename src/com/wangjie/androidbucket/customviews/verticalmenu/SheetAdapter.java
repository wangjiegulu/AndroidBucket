package com.wangjie.androidbucket.customviews.verticalmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.utils.ABTextUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-26
 * Time: 下午5:31
 */
public class SheetAdapter extends BaseAdapter {
    Context context;
    List<String> list;
    private int itemSelectorBg;

    public SheetAdapter(Context context, List<String> list, int itemSelectorBg) {
        this.context = context;
        this.list = list;
        this.itemSelectorBg = itemSelectorBg;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.ab_sheet_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.ab_sheet_item_title_tv);
            holder.title.setBackgroundResource(itemSelectorBg > 0 ? itemSelectorBg : R.drawable.selector_bg_sheet_item);
            int padding = ABTextUtil.dip2px(context, 12);
            holder.title.setPadding(padding, padding, padding, padding);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView title;
    }

}
