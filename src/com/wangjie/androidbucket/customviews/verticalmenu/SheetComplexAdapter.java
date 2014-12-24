package com.wangjie.androidbucket.customviews.verticalmenu;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.utils.ABTextUtil;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-26
 * Time: 下午5:31
 */
public class SheetComplexAdapter extends BaseAdapter {
    Context context;
    List<SheetItem> list;
    private int itemSelectorBg;

    public SheetComplexAdapter(Context context, List<SheetItem> list, int itemSelectorBg) {
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
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ab_sheet_item, null);
        }

        TextView titleTv = (TextView) convertView.findViewById(R.id.ab_sheet_item_title_tv);

        SheetItem sheetItem = list.get(position);

        titleTv.setBackgroundResource(itemSelectorBg > 0 ? itemSelectorBg : R.drawable.selector_bg_sheet_item);
        int padding = ABTextUtil.dip2px(context, 12);
        titleTv.setPadding(padding, padding, padding, padding);
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sheetItem.getTextSizeSp());
        titleTv.setTextColor(sheetItem.getTextColor());
        titleTv.setText(sheetItem.getTitle());
        return convertView;
    }


}
