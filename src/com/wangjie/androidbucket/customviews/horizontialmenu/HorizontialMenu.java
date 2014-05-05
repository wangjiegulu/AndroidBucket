package com.wangjie.androidbucket.customviews.horizontialmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.utils.ABIOUtil;
import com.wangjie.androidbucket.utils.ABTextUtil;

/**
 * Created by wangjie on 14-5-5.
 */
public class HorizontialMenu{

    private Context context;
    private PopupWindow popMenu; // 菜单控件
    private String[] items;
    private int itemSize;
    private OnHoriMenuItemListener listener;



    public static PopupWindow createHoriMenu(Context context, String[] items, OnHoriMenuItemListener listener){
        HorizontialMenu menu = new HorizontialMenu(context, items, listener);
        return menu.popMenu;
    }


    public HorizontialMenu(Context context, String[] items, OnHoriMenuItemListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        itemSize = null == items ? 0 : items.length;
        initMenu();

    }

    private void initMenu(){
        View contentView = LayoutInflater.from(context).inflate(R.layout.ab_horizontial_edit_menu, null);
        if(itemSize <= 0 || null == contentView){
            return;
        }
        ViewGroup itemsVg = (ViewGroup) contentView.findViewById(R.id.ab_horizontial_edit_menu_items_view);

        for(int i = 0; i < itemSize; i++){
            TextView im = new TextView(context);
            im.setTextColor(Color.WHITE);
            im.setText(items[i]);
            im.setTextSize(17);
            im.setPadding(ABTextUtil.dip2px(context, 8), ABTextUtil.dip2px(context, 8), ABTextUtil.dip2px(context, 8), ABTextUtil.dip2px(context, 8));
            if(null != listener){
                final int j = i;
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(view, j);
                    }
                });
            }

            itemsVg.addView(im);
        }


        popMenu = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popMenu.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc000000")));
        popMenu.setAnimationStyle(android.R.style.Animation_Dialog);
        popMenu.setFocusable(true);

    }





}
