package com.wangjie.androidbucket.customviews.verticalmenu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.utils.ABTextUtil;

import java.util.List;

/**
 * ios中的Sheet风格对话框
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-11-27
 * Time: 下午4:46
 */
public class SheetComplexDialog extends Dialog implements AdapterView.OnItemClickListener {
    private static final String TAG = SheetComplexDialog.class.getSimpleName();
    private ListView popupLv; // 菜单ListView
    private int itemSelectorBg;
    private Context context;

    private String title;
    private String cancel;
    private List<SheetItem> items;

    private OnSheetComplexDialogListener listener;

    public static final String KEY_TITLE = "title";

    public static interface OnSheetComplexDialogListener {
        /**
         * 菜单item点击回调方法
         * @param parent
         * @param view
         * @param sheetItem
         * @param id
         */
        public void onSheetItemClick(AdapterView<?> parent, View view, SheetItem sheetItem, long id);
    }

    /**
     * 创建一个对话框
     * @param context
     * @param itemSelectorBg 如果是大于小于0，则使用默认的点击背景效果
     * @param title
     * @param cancel
     * @param items
     * @param listener
     * @return
     */
    public static Dialog createVerticalMenu(Context context, int itemSelectorBg, String title, String cancel, List<SheetItem> items, OnSheetComplexDialogListener listener){
        SheetComplexDialog verticalMenu = new SheetComplexDialog(context, itemSelectorBg, title, cancel, items, listener);
        return verticalMenu;
    }

    public SheetComplexDialog(Context context) {
        super(context);
    }

    public SheetComplexDialog(Context context, int theme) {
        super(context, theme);
    }

    public SheetComplexDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public SheetComplexDialog(Context context, int itemSelectorBg, String title, String cancel, List<SheetItem> items, OnSheetComplexDialogListener listener) {
        super(context, R.style.customDialogStyle);
        this.context = context;
        this.listener = listener;
        this.itemSelectorBg = itemSelectorBg;
        this.title = title;
        this.cancel = cancel;
        this.items = items;


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //        lp.dimAmount = 0.5f; // 设置进度条周边暗度（0.0f ~ 1.0f）
        lp.dimAmount = 0.3f; // 设置全黑
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        WindowManager.LayoutParams wl = ((Activity)context).getWindow().getAttributes();
        wl.x = 0;
        wl.y = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
        //设置显示位置
        this.onWindowAttributesChanged(wl);

        this.getWindow().setGravity(Gravity.BOTTOM);
        this.getWindow().setWindowAnimations(R.style.animDialogPushUp);

        this.setCanceledOnTouchOutside(true);

        initView();
    }


    /**
     * 初始化菜单
     */
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ab_sheet, null);
        if(ABTextUtil.isEmpty(items)){
            return;
        }

        SheetComplexAdapter adapter = new SheetComplexAdapter(context, items, itemSelectorBg);

        popupLv = (ListView) view.findViewById(R.id.ab_sheet_lv);
        TextView titleTv = (TextView) view.findViewById(R.id.ab_sheet_title_tv);
        TextView cancelTv = (TextView) view.findViewById(R.id.ab_sheet_cancel_tv);
//        cancelTv.setBackgroundResource(R.drawable.selector_bg_sheet_item);
//        int padding = ABTextUtil.dip2px(context, 12);
//        cancelTv.setPadding(padding, padding, padding, padding);

        cancelTv.setText(cancel);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SheetComplexDialog.this.dismiss();
            }
        });
        titleTv.setText(title);
        popupLv.setAdapter(adapter);
        popupLv.setOnItemClickListener(this);



        this.setContentView(view);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(null != listener){
            listener.onSheetItemClick(parent, view, items.get(position), id);
        }
        this.dismiss();
    }

    public ListView getPopupLv() {
        return popupLv;
    }

    public void setPopupLv(ListView popupLv) {
        this.popupLv = popupLv;
    }




}
