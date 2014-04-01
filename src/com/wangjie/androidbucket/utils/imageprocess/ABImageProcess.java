package com.wangjie.androidbucket.utils.imageprocess;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.imageprocess.blur.FastBlur;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-28
 * Time: 上午10:12
 */
public class ABImageProcess {
    public static final String TAG = ABImageProcess.class.getSimpleName();


    /***************************************BLUR START****************************************/
    /**
     * 模糊处理图片，默认先缩小（默认缩放为原图的1/8），再模糊处理再放大（高效率）
     * @param bm
     * @param view
     * @return
     */
    public static Bitmap fastBlur(Bitmap bm, View view){
        return fastBlur(bm, view, true, 8);
    }

    /**
     * 模糊处理后，为view设置为background，默认先缩小（默认缩放为原图的1/8），再模糊处理再放大（高效率）
     * @param context
     * @param bm
     * @param view
     */
    public static void fastBlurSetBg(Context context, Bitmap bm, View view){
        fastBlurSetBg(context, bm, view, true, 8);
    }

    /**
     * 模糊处理图片
     * @param bm
     * @param view
     * @param downScale
     * @param scaleF
     * @return
     */
    public static Bitmap fastBlur(Bitmap bm, View view, boolean downScale, float scaleF){
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 20;
        if (downScale) {
            scaleFactor = scaleF;
            radius = 2;
        }

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()/scaleFactor),
                (int) (view.getMeasuredHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        // 处理bitmap缩放的时候，就可以达到双缓冲的效果，模糊处理的过程就更加顺畅了。
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bm, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        Logger.d(TAG, "fast blur takes: " + (System.currentTimeMillis() - startMs + "ms"));
        return overlay;

    }

    /**
     * 模糊处理后，为view设置为background
     * @param context
     * @param bm
     * @param view
     * @param downScale
     * @param scaleFactor
     */
    public static void fastBlurSetBg(Context context, Bitmap bm, View view, boolean downScale, float scaleFactor){
        Bitmap overlay = fastBlur(bm, view, downScale, scaleFactor);
        if(null == overlay){
            Logger.e(TAG, "fast blur error(result[overlay] is null)");
            return;
        }
        view.setBackground(new BitmapDrawable(context.getResources(), overlay));
    }
    /***************************************BLUR END****************************************/
















    /***************************************圆角/投影羽化/倒影 BEGIN****************************************/
    /**
     * 对图片进行圆角处理
     * @author com.tiantian
     * @param bitmap 要处理的Bitmap对象
     * @param roundPx 圆角半径设置
     * @return Bitmap对象
     */
    public static Bitmap roundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 图片加投影 羽化效果
     * @param originalBitmap
     * @return
     */
    public static Bitmap shadowBitmap(Bitmap originalBitmap) {
//		Bitmap originalBitmap = drawableToBitmap(drawable);
        BlurMaskFilter blurFilter = new BlurMaskFilter(10, BlurMaskFilter.Blur.OUTER);
        Paint shadowPaint = new Paint();
        shadowPaint.setMaskFilter(blurFilter);

		/*
		 * int[] offsetXY = new int[2]; Bitmap shadowImage =
		 * originalBitmap.extractAlpha(shadowPaint, offsetXY);
		 *
		 * Bitmap bmp=shadowImage.copy(Config.ARGB_8888, true); Canvas c = new
		 * Canvas(bmp); c.drawBitmap(originalBitmap, -offsetXY[0], -offsetXY[1],
		 * null); return shadowImage;
		 */
        final int w = originalBitmap.getWidth();
        final int h = originalBitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w + 20, h + 20, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawBitmap(originalBitmap, 10, 10, shadowPaint);
        c.drawBitmap(originalBitmap, 10, 10, null);
        return bmp;
    }

    /**
     * 对图片进行倒影处理
     * @author com.tiantian
     * @param bitmap
     * @return
     */
    public static Bitmap reflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
                h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }
    /***************************************圆角/投影羽化/倒影 END****************************************/

    /***********************************图片基本处理（缩放/转换）BEGIN*************************************/

    /**
     * 放缩图片处理
     * @author com.tiantian
     * @param bitmap 要放缩的Bitmap对象
     * @param width 放缩后的宽度
     * @param height 放缩后的高度
     * @return 放缩后的Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * Drawable缩放
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap oldbmp = drawable2Bitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(newbmp);
    }

    /**
     * 将Bitmap转化为Drawable
     * @author com.tiantian
     * @param bitmap
     * @return
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap){
        return new BitmapDrawable(bitmap) ;
    }
    /**
     * 将Drawable转化为Bitmap
     * @author com.tiantian
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /***********************************图片基本处理（缩放/转换）BEGIN*************************************/

}
