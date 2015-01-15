package com.wangjie.androidbucket.customviews.drawable;

import android.graphics.*;
import android.graphics.drawable.Drawable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/13/15.
 */
public class InnerBorderDrawable extends Drawable {
    protected Paint paint = new Paint();
    protected RectF drawArea = new RectF();
    protected float border; // 边框宽度
    protected float corner; // 圆角大小
    protected int fillColor; // 填充颜色
    protected int borderColor; // 边框颜色

    public InnerBorderDrawable() {
    }

    public InnerBorderDrawable(int borderColor, int fillColor, float corner, float border) {
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.corner = corner;
        this.border = border;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        drawArea.left = bounds.left + border;
        drawArea.top = bounds.top + border;
        drawArea.right = bounds.right - border;
        drawArea.bottom = bounds.bottom - border;
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

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public void setCorner(float corner) {
        this.corner = corner;
    }

    public void setBorder(float border) {
        this.border = border;
    }
}
