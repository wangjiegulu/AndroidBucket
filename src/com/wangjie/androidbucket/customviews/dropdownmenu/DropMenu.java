package com.wangjie.androidbucket.customviews.dropdownmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.log.Logger;

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
public class DropMenu implements AdapterView.OnItemClickListener {
    private static final String TAG = DropMenu.class.getSimpleName();
    private ListView popupLv; // 菜单ListView
    private PopupWindow popupMenu; // 菜单控件
    private int backgroundRes;
    private int itemSelectorBg;
    private Context context;

    private IDropMenu iDropMenu;

    public static final String KEY_TITLE = "title";

    public static PopupWindow createDropDownMenu(Context context, IDropMenu iDropMenu, int backgroundRes, int itemSelectorBg){
        DropMenu dropMenu = new DropMenu(context, iDropMenu, backgroundRes, itemSelectorBg);
        return dropMenu.getPopupMenu();
    }

    public DropMenu(Context context, IDropMenu iDropMenu, int backgroundRes, int itemSelectorBg) {
        this.context = context;
        this.iDropMenu = iDropMenu;
        this.backgroundRes = backgroundRes;
        this.itemSelectorBg = itemSelectorBg;
        initPopupMenu();
    }


    /**
     * 初始化下拉菜单
     */
    private void initPopupMenu() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ab_drop_menu, null);
        List<Map<String, String>> items = getMenuData();
        if(null == items){
            return;
        }

        DropMenuAdapter adapter = new DropMenuAdapter(context, items, itemSelectorBg);

        popupLv = (ListView) view.findViewById(R.id.ab_drop_menu_lv);
        popupLv.setAdapter(adapter);
        popupLv.setOnItemClickListener(this);

        popupMenu = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupMenu.setBackgroundDrawable(context.getResources().getDrawable(backgroundRes));
        popupMenu.setAnimationStyle(android.R.style.Animation_Dialog);
        popupMenu.setFocusable(true);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        iDropMenu.onDropDownMenuItemClick(parent, view, position, id);
    }

    private List<Map<String, String>> getMenuData() {
        String[] itemTitles = iDropMenu.getDropDownMenuItems();
        if(null == itemTitles || itemTitles.length <= 0){
            Logger.d(TAG, "drop down menu items have no items!");
            return null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map;

        for(int i = 0; i < itemTitles.length; i++){
            map = new HashMap<String, String>();
            map.put(KEY_TITLE, itemTitles[i]);
            list.add(map);
        }

        return list;
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
