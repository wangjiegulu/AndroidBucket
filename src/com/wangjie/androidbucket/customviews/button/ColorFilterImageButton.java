package com.wangjie.androidbucket.customviews.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;
import com.wangjie.androidbucket.R;

/**
 * 图片按钮，如果按下，则会改变图片颜色、亮度、透明度等
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/31/14.
 */
public class ColorFilterImageButton extends ImageButton {
    private static final String TAG = ColorFilterImageButton.class.getSimpleName();

    public ColorFilterImageButton(Context context) {
        super(context);
        init(context);
    }

    public ColorFilterImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        matrixVector = new MatrixVector();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorFilterImageButton);
        for (int i = 0, indexCount = a.getIndexCount(); i < indexCount; i++) {
            int attrIndex = a.getIndex(i);
            if (attrIndex == R.styleable.ColorFilterImageButton_filterRedVector) {
                matrixVector.red = a.getFloat(attrIndex, 1);
            } else if (attrIndex == R.styleable.ColorFilterImageButton_filterGreenVector) {
                matrixVector.green = a.getFloat(attrIndex, 1);
            } else if (attrIndex == R.styleable.ColorFilterImageButton_filterBlueVector) {
                matrixVector.blue = a.getFloat(attrIndex, 1);
            } else if (attrIndex == R.styleable.ColorFilterImageButton_filterAlphaVector) {
                matrixVector.alpha = a.getFloat(attrIndex, 1);
            } else if (attrIndex == R.styleable.ColorFilterImageButton_filterBrightnessVector) {
                matrixVector.brightness = a.getFloat(attrIndex, 0);
            }
        }
        a.recycle();

        init(context);
    }

    public ColorFilterImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private Context context;
    private ColorMatrix matrix;
    private MatrixVector matrixVector;

    private void init(Context context) {
        this.context = context;
        matrix = new ColorMatrix();

        matrixVector = null == matrixVector ? new MatrixVector() : matrixVector;

        matrix.set(
                new float[]{
                        matrixVector.red, 0, 0, 0, matrixVector.brightness,
                        0, matrixVector.green, 0, 0, matrixVector.brightness,
                        0, 0, matrixVector.blue, 0, matrixVector.brightness,
                        0, 0, 0, matrixVector.alpha, 0
                });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.setColorFilter(new ColorMatrixColorFilter(matrix));
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                // 滑出范围，则恢复原状
                if (x < 0 || y < 0 || x > getMeasuredWidth() || y > getMeasuredHeight()) {
                    this.clearColorFilter();
                }
                break;
            case MotionEvent.ACTION_UP:
                this.clearColorFilter();
                break;
        }
        return super.onTouchEvent(event);
    }

    class MatrixVector {
        float red = 1f;
        float green = 1f;
        float blue = 1f;
        float alpha = 1f;
        float brightness = 0f;

        @Override
        public String toString() {
            return "MatrixVector{" +
                    "red=" + red +
                    ", green=" + green +
                    ", blue=" + blue +
                    ", alpha=" + alpha +
                    ", brightness=" + brightness +
                    '}';
        }
    }

}
