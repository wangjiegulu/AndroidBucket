package com.wangjie.androidbucket.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmial.com
 * Date: 13-12-10
 * Time: 下午5:07
 */
public class NestedGridView extends GridView{

    public NestedGridView(Context context) {
        super(context);
    }

    public NestedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 该自定义控件只是重写了GridView的onMeasure方法，使其不会出现滚动条，ScrollView嵌套ListView也是同样的道理，不再赘述。
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
