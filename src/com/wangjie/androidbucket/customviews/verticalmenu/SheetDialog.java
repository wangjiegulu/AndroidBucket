package com.wangjie.androidbucket.customviews.verticalmenu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.log.Logger;

import java.util.*;

/**
 * ios中的Sheet风格对话框
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-11-27
 * Time: 下午4:46
 */
public class SheetDialog extends Dialog implements AdapterView.OnItemClickListener {
    private static final String TAG = SheetDialog.class.getSimpleName();
    private ListView popupLv; // 菜单ListView
    private int itemSelectorBg;

    private String title;
    private String cancel;
    private String[] items;

    private OnVertMenuItemClickListener listener;

    public interface OnVertMenuItemClickListener {
        /**
         * 菜单item点击回调方法
         *
         * @param parent
         * @param view
         * @param position
         * @param id
         */
        public void onDropDownMenuItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    /**
     * 创建一个对话框
     *
     * @param context
     * @param itemSelectorBg 如果是大于小于0，则使用默认的点击背景效果
     * @param title
     * @param cancel
     * @param items
     * @param listener
     * @return
     */
    public static Dialog createVerticalMenu(Context context, int itemSelectorBg, String title, String cancel, String[] items, OnVertMenuItemClickListener listener) {
        SheetDialog verticalMenu = new SheetDialog(context, itemSelectorBg, title, cancel, items, listener);
        return verticalMenu;
    }

    public SheetDialog(Context context) {
        super(context, R.style.customDialogStyle);
        initialWindow((Activity) context);
    }

    public SheetDialog(Context context, int itemSelectorBg, String title, String cancel, String[] items, OnVertMenuItemClickListener listener) {
        super(context, R.style.customDialogStyle);
        this.listener = listener;
        this.itemSelectorBg = itemSelectorBg;
        this.title = title;
        this.cancel = cancel;
        this.items = items;

        initialWindow((Activity) context);

        initView();
    }

    private void initialWindow(Activity context) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //        lp.dimAmount = 0.5f; // 设置进度条周边暗度（0.0f ~ 1.0f）
        lp.dimAmount = 0.3f; // 设置全黑
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        WindowManager.LayoutParams wl = context.getWindow().getAttributes();
        wl.x = 0;
        wl.y = context.getWindowManager().getDefaultDisplay().getHeight();
        //设置显示位置
        this.onWindowAttributesChanged(wl);

        this.getWindow().setGravity(Gravity.BOTTOM);
        this.getWindow().setWindowAnimations(R.style.animDialogPushUp);

        this.setCanceledOnTouchOutside(true);
    }


    /**
     * 初始化菜单
     */
    protected void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.ab_sheet, null);

        SheetAdapter adapter = new SheetAdapter(getContext(), getMenuData(), itemSelectorBg);

        popupLv = (ListView) view.findViewById(R.id.ab_sheet_lv);
        TextView titleTv = (TextView) view.findViewById(R.id.ab_sheet_title_tv);
        TextView cancelTv = (TextView) view.findViewById(R.id.ab_sheet_cancel_tv);
//        cancelTv.setBackgroundResource(R.drawable.selector_bg_sheet_item);
//        int padding = ABTextUtil.dip2px(context, 12);
//        cancelTv.setPadding(padding, padding, padding, padding);

        cancelTv.setText(getCancel());
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SheetDialog.this.dismiss();
            }
        });
        titleTv.setText(getTitle());
        popupLv.setAdapter(adapter);
        popupLv.setOnItemClickListener(this);

        this.setContentView(view);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    public String getTitle() {
        return title;
    }

    public String getCancel() {
        return cancel;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != listener) {
            listener.onDropDownMenuItemClick(parent, view, position, id);
        }

    }

    protected List<String> getMenuData() {
        if (null == items || items.length <= 0) {
            Logger.d(TAG, "menu items have no items!");
            return new ArrayList<>();
        }
        return Arrays.asList(items);
    }

    public ListView getPopupLv() {
        return popupLv;
    }

    public void setPopupLv(ListView popupLv) {
        this.popupLv = popupLv;
    }


}
