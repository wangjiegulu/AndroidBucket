package com.wangjie.androidbucket.customviews.dropdownmenu.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.customviews.dropdownmenu.DropMenuAdapter;
import com.wangjie.androidbucket.customviews.dropdownmenu.IDropMenu;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.ABTextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-11-27
 * Time: 下午4:46
 */
public class CommonDropMenu<T> implements AdapterView.OnItemClickListener {
    private static final String TAG = CommonDropMenu.class.getSimpleName();

    public static interface ICommonDropMenu{
        /**
         * 下拉菜单item点击回调方法
         * @param parent
         * @param view
         * @param position
         * @param id
         */
        public void onDropDownMenuItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    private ListView popupLv; // 菜单ListView
    private PopupWindow popupMenu; // 菜单控件
    private int backgroundRes;
    private Context context;

    private ICommonDropMenu iDropMenu;

    public static <T> CommonDropMenu<T> createCommonDropDownMenu(Context context, ICommonDropMenu iDropMenu, int backgroundRes){
        return new CommonDropMenu<>(context, iDropMenu, backgroundRes);
    }

    public CommonDropMenu(Context context, ICommonDropMenu iDropMenu, int backgroundRes) {
        this.context = context;
        this.iDropMenu = iDropMenu;
        this.backgroundRes = backgroundRes;
        initPopupMenu();
    }


    /**
     * 初始化下拉菜单
     */
    private void initPopupMenu() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ab_drop_menu, null);
        if(null == iDropMenu){
            Logger.e(TAG, "iDropMenu is null!");
            return;
        }

        popupLv = (ListView) view.findViewById(R.id.ab_drop_menu_lv);
//        popupLv.setAdapter(adapter);
        popupLv.setOnItemClickListener(this);

        popupMenu = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupMenu.setBackgroundDrawable(context.getResources().getDrawable(backgroundRes));
        popupMenu.setAnimationStyle(android.R.style.Animation_Dialog);
        popupMenu.setFocusable(true);

    }

    private BaseAdapter adapter;
    public void setAdapter(BaseAdapter adapter){
        this.adapter = adapter;
        popupLv.setAdapter(this.adapter);
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        iDropMenu.onDropDownMenuItemClick(parent, view, position, id);
    }


    public ListView getPopupLv() {
        return popupLv;
    }

    public void setPopupLv(ListView popupLv) {
        this.popupLv = popupLv;
    }

    public PopupWindow getPopupMenu() {
        return popupMenu;
    }

    public void setPopupMenu(PopupWindow popupMenu) {
        this.popupMenu = popupMenu;
    }

}
