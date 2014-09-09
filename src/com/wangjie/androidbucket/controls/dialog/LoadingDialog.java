package com.wangjie.androidbucket.controls.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.wangjie.androidbucket.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wangjie
 * @version 创建时间：2012-10-14 下午3:25:56
 */
public class LoadingDialog extends Dialog {

    private static HashMap<Context, LoadingDialog> dialogs = new HashMap<Context, LoadingDialog>();

    /**
     * 获取读取进度对话框实例
     *
     * @param context
     * @return LoadingDialog
     */
    public synchronized static LoadingDialog getInstance(Context context) {
        LoadingDialog dialog = dialogs.get(context);
        if (null == dialog) {
            dialog = new LoadingDialog(context);
            dialogs.put(context, dialog);
        }
        return dialog;
    }

    /**
     * 显示LoadingDialog
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        show(context, "正在加载");
    }

    /**
     * 显示LoadingDialog
     *
     * @param context 上下文
     * @param content 提示信息
     */
    public static void show(Context context, String content) {
        if (null == content || content.equals("")) {
            show(context);
            return;
        }
        LoadingDialog dialog = LoadingDialog.getInstance(context);
        dialog.getContent().setText(content);
        dialog.show();
    }

    /**
     * 取消进度条
     *
     * @param context
     */
    public static void cancel(Context context) {
        LoadingDialog.getInstance(context).cancel();
    }

    public static void cancelAllLoadingDialog() {
        Set<Context> set = dialogs.keySet();
        for (Iterator itr = set.iterator(); itr.hasNext(); ) {
            cancel((Context) itr.next());
        }
    }

    private TextView content;

    private LoadingDialog(Context context) {
        super(context, R.style.LoadingDialogStyle);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.15f; // 设置进度条周边暗度（0.0f ~ 1.0f）
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        this.setCanceledOnTouchOutside(false);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.loading_dialog, null);
        content = (TextView) view.findViewById(R.id.dialog_content_tv);
        this.setContentView(view);
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public TextView getContent() {
        return content;
    }

    public void setContent(TextView content) {
        this.content = content;
    }
}
