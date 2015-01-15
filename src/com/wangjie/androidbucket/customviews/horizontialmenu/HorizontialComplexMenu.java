package com.wangjie.androidbucket.customviews.horizontialmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.utils.ABTextUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjie on 14-5-5.
 */
public class HorizontialComplexMenu implements View.OnClickListener {
    public static interface OnHorizontialComplexMenuListener {
        public abstract void onItemClick(View view, int position, HorizontialMenuItem item);
    }

    private Context context;
    private PopupWindow popMenu; // 菜单控件
    private Drawable background;
    private List<HorizontialMenuItem> items;
    private int itemSize;
    private OnHorizontialComplexMenuListener listener;

    public static PopupWindow createHoriMenu(Context context, Drawable background, List<HorizontialMenuItem> items, OnHorizontialComplexMenuListener listener) {
        HorizontialComplexMenu menu = new HorizontialComplexMenu(context, background, items, listener);
        return menu.popMenu;
    }


    public HorizontialComplexMenu(Context context, Drawable background, List<HorizontialMenuItem> items, OnHorizontialComplexMenuListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.background = background;
        itemSize = null == items ? 0 : items.size();
        initMenu();

    }

    private void initMenu() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.ab_horizontial_edit_menu, null);
        if (itemSize <= 0 || null == contentView) {
            return;
        }
        ViewGroup itemsVg = (ViewGroup) contentView.findViewById(R.id.ab_horizontial_edit_menu_items_view);

        for (int i = 0; i < itemSize; i++) {
            HorizontialMenuItem item = items.get(i);
            TextView im = new TextView(context);
            im.setTextColor(Color.WHITE);
            im.setText(item.getTitle());
            im.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.getTextSizeSp());
            im.setPadding(ABTextUtil.dip2px(context, 8), ABTextUtil.dip2px(context, 3), ABTextUtil.dip2px(context, 8), ABTextUtil.dip2px(context, 3));
            im.setTag(i);
            if (null != listener) {
                im.setOnClickListener(this);
            }

            itemsVg.addView(im);
        }


        popMenu = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popMenu.setBackgroundDrawable(null == background ? new ColorDrawable(Color.parseColor("#cc000000")) : background);
        popMenu.setAnimationStyle(android.R.style.Animation_Dialog);
        popMenu.setFocusable(true);

    }

    @Override
    public void onClick(View v) {
        int index = (Integer) v.getTag();
        listener.onItemClick(v, index, items.get(index));
        popMenu.dismiss();
    }


    public static class HorizontialMenuItem implements Serializable {
        private String title;
        private int textColor = Color.WHITE;
        private int textSizeSp = 15;
        private int action;

        @Override
        public String toString() {
            return "SheetItem{" +
                    "title='" + title + '\'' +
                    ", textColor=" + textColor +
                    ", textSizeSp=" + textSizeSp +
                    ", action=" + action +
                    '}';
        }

        public String getTitle() {
            return title;
        }

        public HorizontialMenuItem setTitle(String title) {
            this.title = title;
            return this;
        }

        public int getTextColor() {
            return textColor;
        }

        public HorizontialMenuItem setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public int getTextSizeSp() {
            return textSizeSp;
        }

        public HorizontialMenuItem setTextSizeSp(int textSizeSp) {
            this.textSizeSp = textSizeSp;
            return this;
        }

        public int getAction() {
            return action;
        }

        public HorizontialMenuItem setAction(int action) {
            this.action = action;
            return this;
        }
    }


}
