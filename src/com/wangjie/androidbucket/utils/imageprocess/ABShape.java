package com.wangjie.androidbucket.utils.imageprocess;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 9/12/14.
 */
public class ABShape {

    /**
     * 生成圆角的背景
     * @param color
     * @return
     */
    public static ShapeDrawable generateCornerShapeDrawable(int color, int corner){
        return generateCornerShapeDrawable(color, corner, corner, corner, corner);
    }
    public static ShapeDrawable generateCornerShapeDrawable(int color, int topLeftCorner, int topRightCorner, int bottomRightCorner, int bottomLeftCorner){
        Shape shape = new RoundRectShape(new float[]{topLeftCorner, topLeftCorner, topRightCorner, topRightCorner, bottomRightCorner, bottomRightCorner, bottomLeftCorner, bottomLeftCorner}, null, null);
        ShapeDrawable sd = new ShapeDrawable(shape);
        sd.getPaint().setColor(color);
        sd.getPaint().setStyle(Paint.Style.FILL);
        return sd;
    }

    public static ShapeDrawable generateCornerStrokeDrawable(int color, float width, int corner){
        return generateCornerStrokeDrawable(color, width, corner, corner, corner, corner);
    }
    public static ShapeDrawable generateCornerStrokeDrawable(int color, float width, int topLeftCorner, int topRightCorner, int bottomRightCorner, int bottomLeftCorner){
        Shape shape = new RoundRectShape(new float[]{topLeftCorner, topLeftCorner, topRightCorner, topRightCorner, bottomRightCorner, bottomRightCorner, bottomLeftCorner, bottomLeftCorner}, null, null);
        ShapeDrawable sd = new ShapeDrawable(shape);
        sd.getPaint().setColor(color);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        sd.getPaint().setAntiAlias(true);
        sd.getPaint().setStrokeWidth(width);
        return sd;
    }


}
