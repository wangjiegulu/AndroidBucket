package com.wangjie.androidbucket.customviews.dropdownmenu;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-11-27
 * Time: 下午4:44
 */
public interface IDropMenu {
    /**
     * 设置下拉菜单的每一项名称
     * @return
     */
    public String[] getDropDownMenuItems();

    /**
     * 下拉菜单item点击回调方法
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onDropDownMenuItemClick(AdapterView<?> parent, View view, int position, long id);

}
