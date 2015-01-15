package com.wangjie.androidbucket.customviews.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/13/15.
 */
public class CornerDrawable extends InnerBorderDrawable {
    public CornerDrawable() {
    }
    public CornerDrawable(int borderColor, int fillColor, float corner, float border) {
        super(borderColor, fillColor, corner, border);
    }

    @Override
    public void draw(Canvas canvas) {
        // fill
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);
        canvas.drawRoundRect(drawArea, corner, corner, paint);

        if (border > 0) {
            // border
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(border);
            canvas.drawRoundRect(drawArea, corner, corner, paint);
        }

    }


}
