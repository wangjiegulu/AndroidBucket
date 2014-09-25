package com.wangjie.androidbucket.customviews;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABImageProcess;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 9/19/14.
 */
public class TagRadioButton extends RadioButton {
    public static final String TAG = TagRadioButton.class.getSimpleName();

    private Paint paint;

    public TagRadioButton(Context context) {
        super(context);
        init();
    }

    public TagRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }


    Drawable buttonDrawable;
    private boolean shouldShowTag = true;
    private int tagNumber = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null == paint) {
            paint = new Paint();
            paint.setColor(Color.parseColor("#FF6600"));
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(20f);
        }

        if (shouldShowTag) {
            int width = getWidth();
            int height = getHeight();
            Logger.d(TAG, "tagRadioButton, width: " + width + ", height: " + height);

//            setCompoundDrawables(null, null, null, null);

//            canvas.drawCircle(width / 2, height / 2, ABTextUtil.dip2px(getContext(), 4), paint);
//            canvas.drawRect(0, 0, width, height, paint);
            Drawable[] drawables = this.getCompoundDrawables();
            Drawable topDrawable;
            if (!ABTextUtil.isEmpty(drawables) && drawables.length > 2) {
                topDrawable = drawables[1];
//                Bitmap drawBm = ABImageProcess.drawable2Bitmap(topDrawable);

                Bitmap drawableBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas cv = new Canvas(drawableBm);
                cv.drawColor(Color.WHITE);
                topDrawable.setBounds(0, 0,
                        topDrawable.getIntrinsicWidth(), topDrawable.getIntrinsicHeight());
                topDrawable.draw(cv);
                setCompoundDrawables(null, topDrawable, null, null);
                shouldShowTag = false;

//                cv.drawCircle(100, 100, 10, paint);
//                ABImageProcess.bitmap2Drawable(drawableBm).draw(canvas);
//                setCompoundDrawables(null, new BitmapDrawable(drawableBm), null, null);
//                shouldShowTag = false;

//                Logger.d(TAG, "drawBm width: " + drawBm.getWidth() + ", height: " + drawBm.getHeight());
//                Canvas cv = new Canvas(drawBm);
//                int w = ABTextUtil.dip2 px(getContext(), 4);
//                cv.drawCircle(width / 2f, height / 2f, w, paint);
//                Drawable drawable = ABImageProcess.bitmap2Drawable(drawBm);
//                drawable.draw(canvas);


//                int offset = width / 2;
//                int bound = ABTextUtil.dip2px(getContext(), 4);
//                ShapeDrawable drawable = ABShape.generateCornerShapeDrawable(Color.parseColor("#ffffff"), ABTextUtil.dip2px(getContext(), 5));
////                BitmapDrawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
//                drawable.setBounds(0, 0, width, height);
////                drawable.draw(canvas);
//                setCompoundDrawables(null, drawable, null, null);
//                shouldShowTag = false;


            }
        }

//            canvas.drawCircle(width / 2f, height / 2f, ABTextUtil.dip2px(getContext(), 4), paint);


    }


    public void showTag(int number) {
        this.tagNumber = number;
        shouldShowTag = true;
        invalidate();
    }

    public void showTag() {
        this.tagNumber = 0;
        shouldShowTag = true;
        invalidate();
    }

    public void dismissTag() {
        shouldShowTag = false;
        invalidate();
    }


}
