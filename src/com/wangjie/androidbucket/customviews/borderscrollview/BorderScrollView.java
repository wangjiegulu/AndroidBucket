package com.wangjie.androidbucket.customviews.borderscrollview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-9-6
 * Time: 下午2:06
 */
public class BorderScrollView extends ScrollView{
    private long millis;
    // 滚动监听者
    private OnScrollChangedListener onScrollChangedListener;

    public BorderScrollView(Context context) {
        super(context);
    }

    public BorderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        long now = System.currentTimeMillis();

        if(null == onScrollChangedListener){
            return;
        }

        // 通知监听者
        onScrollChangedListener.onScrollChanged(l, t, oldl, oldt);

        if(now - millis > 1000l){
            // 滚动到底部（前提：从不是底部滚动到底部）
            if((this.getHeight() + oldt) != this.getTotalVerticalScrollRange()
                    && (this.getHeight() + t) == this.getTotalVerticalScrollRange()){
                onScrollChangedListener.onScrollBottom();

                millis = now;
            }
        }


        if(oldt != 0 && t == 0){
            onScrollChangedListener.onScrollTop();
        }


    }

    public OnScrollChangedListener getOnScrollChangedListener() {
        return onScrollChangedListener;
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    /**
     * 获得滚动总长度
     * @return
     */
    public int getTotalVerticalScrollRange() {
        return this.computeVerticalScrollRange();
    }


    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0; // 禁止ScrollView在子控件的布局改变时自动滚动
    }
}
