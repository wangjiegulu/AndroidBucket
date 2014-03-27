package com.wangjie.androidbucket.customviews.dropdownmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wangjie.androidbucket.R;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-26
 * Time: 下午5:31
 */
public class DropMenuAdapter extends BaseAdapter{
    Context context;
    List<Map<String, String>> list;
    private int itemSelectorBg;

    public DropMenuAdapter(Context context, List<Map<String, String>> list, int itemSelectorBg) {
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
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.ab_drop_menu_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.ab_drop_menu_item_title);
            holder.title.setBackgroundResource(itemSelectorBg);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).get(DropMenu.KEY_TITLE));
        return convertView;
    }

    class ViewHolder{
        TextView title;
    }

}
