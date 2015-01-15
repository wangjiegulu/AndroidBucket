package com.wangjie.androidbucket.customviews.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/13/15.
 */
public class CircleDrawable extends Drawable {
    protected Paint paint = new Paint();
    protected float border; // 边框宽度
    protected int fillColor; // 填充颜色
    protected int borderColor; // 边框颜色

    float circleX; // 圆心
    float circleY;
    float radius; // 半径

    public CircleDrawable() {
    }

    public CircleDrawable(int borderColor, int fillColor, float border) {
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.border = border;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        circleX = (bounds.left + bounds.right) / 2;
        circleY = (bounds.top + bounds.bottom) / 2;
        radius = bounds.right - bounds.left - 2 * border;
    }

    @Override
    public void draw(Canvas canvas) {
        // fill
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);
        canvas.drawCircle(circleX, circleY, radius, paint);

        if (border > 0) {
            // border
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(border);
            canvas.drawCircle(circleX, circleY, radius, paint);
        }

    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return 0;
    }

}
