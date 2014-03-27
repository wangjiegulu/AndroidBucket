package com.wangjie.androidbucket.customviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.Gravity;
import android.widget.EditText;


/**
 * 可选择复制的TextView
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-8-2
 * Time: 下午4:27
 */
public class TextCopy extends EditText {
    private int off; //字符串的偏移值

    public TextCopy(Context context) {
        super(context);
        initialize();
    }

    public TextCopy(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public TextCopy(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        setGravity(Gravity.TOP);
        setBackgroundColor(Color.TRANSPARENT);
//        setBackground(null);
//        setTextIsSelectable(true);
        setHighlightColor(Color.parseColor("#33B5E5"));
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        //不做任何处理，为了阻止长按的时候弹出上下文菜单
    }

    @Override
    public boolean getDefaultEditable() {
        return false;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        Layout layout = getLayout();
//        int line = 0;
//        switch(action) {
//            case MotionEvent.ACTION_DOWN:
//                line = layout.getLineForVertical(getScrollY()+ (int)event.getY());
//                off = layout.getOffsetForHorizontal(line, (int)event.getX());
//                Selection.setSelection(getEditableText(), off);
//                break;
//            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_UP:
//                line = layout.getLineForVertical(getScrollY()+(int)event.getY());
//                int curOff = layout.getOffsetForHorizontal(line, (int)event.getX());
//                Selection.setSelection(getEditableText(), off, curOff);
//                break;
//        }
//        return true;
//    }
}
