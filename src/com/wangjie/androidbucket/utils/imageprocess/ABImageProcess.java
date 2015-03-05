package com.wangjie.androidbucket.utils.imageprocess;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.ABIOUtil;
import com.wangjie.androidbucket.utils.ABViewUtil;
import com.wangjie.androidbucket.utils.imageprocess.blur.FastBlur;

import java.io.*;

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
     *
     * @param bm
     * @param view
     * @return
     */
    public static Bitmap fastBlur(Bitmap bm, View view) {
        return fastBlur(bm, view, true, 8);
    }

    /**
     * 模糊处理后，为view设置为background，默认先缩小（默认缩放为原图的1/8），再模糊处理再放大（高效率）
     *
     * @param context
     * @param bm
     * @param view
     */
    public static void fastBlurSetBg(Context context, Bitmap bm, View view) {
        fastBlurSetBg(context, bm, view, true, 8);
    }

    /**
     * 模糊处理图片
     *
     * @param bm
     * @param view
     * @param downScale
     * @param scaleF
     * @return
     */
    public static Bitmap fastBlur(Bitmap bm, View view, boolean downScale, float scaleF) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 20;
        if (downScale) {
            scaleFactor = scaleF;
            radius = 2;
        }

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        // 处理bitmap缩放的时候，就可以达到双缓冲的效果，模糊处理的过程就更加顺畅了。
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bm, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        Logger.d(TAG, "fast blur takes: " + (System.currentTimeMillis() - startMs + "ms"));
        return overlay;

    }

    public static Bitmap fastBlur(Bitmap bm, float scaleFactor, float radius, int width, int height) {
        long startMs = System.currentTimeMillis();
        scaleFactor = scaleFactor > 0 ? scaleFactor : 1;
        radius = radius > 0 ? radius : 20;

        Bitmap overlay = Bitmap.createBitmap((int) (width / scaleFactor), (int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
//        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        // 处理bitmap缩放的时候，就可以达到双缓冲的效果，模糊处理的过程就更加顺畅了。
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bm, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        Logger.d(TAG, "fast blur takes: " + (System.currentTimeMillis() - startMs + "ms"));
        return overlay;
    }

    public static Bitmap fastBlur(Bitmap bm, float scaleFactor, float radius) {
        return fastBlur(bm, scaleFactor, radius, bm.getWidth(), bm.getHeight());
    }

    /**
     * 模糊处理后，为view设置为background
     *
     * @param context
     * @param bm
     * @param view
     * @param downScale
     * @param scaleFactor
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public static void fastBlurSetBg(Context context, Bitmap bm, View view, boolean downScale, float scaleFactor) {
        Bitmap overlay = fastBlur(bm, view, downScale, scaleFactor);
        if (null == overlay) {
            Logger.e(TAG, "fast blur error(result[overlay] is null)");
            return;
        }
        ABViewUtil.setBackgroundDrawable(view, new BitmapDrawable(context.getResources(), overlay));
    }
    /***************************************BLUR END****************************************/


    /***************************************图片压缩计算 BEGIN****************************************/
    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static String bitmapToString(String filePath, int w, int h) {

        Bitmap bm = getSmallBitmap(filePath, w, h);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        String str = Base64.encodeToString(b, Base64.DEFAULT);
        ABIOUtil.recycleBitmap(bm);
        return str;

    }

    /**
     * 计算图片的缩放值
     *
     * @param o
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options o,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image

//        if (height > reqHeight || width > reqWidth) {
//
//            // Calculate ratios of height and width to requested height and
//            // width
//            final int heightRatio = Math.round((float) height / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
////            final int heightRatio = height / reqHeight;
////            final int widthRatio = width / reqWidth;
//
//            // Choose the smallest ratio as inSampleSize value, this will
//            // guarantee
//            // a final image with both dimensions larger than or equal to the
//            // requested height and width.
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }

//        while((h = h / 2) > reqHeight && (w = w / 2) > reqWidth){
//            inSampleSize *= 2;
//        }

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
//                        if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize){
//                            break;
//                        }
//                        width_tmp /= 2;
//                        height_tmp /= 2;
//                        scale *= 2;
            if (width_tmp <= reqWidth || height_tmp <= reqHeight) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;

        }

        return scale;
    }


    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int w, int h) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, w, h);
//        options.inSampleSize = computeSampleSize(options, -1, w * h);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

//        Bitmap b = BitmapFactory.decodeFile(filePath, options);

//        OperatePic.zoomBitmap(b, w, h);
        Bitmap resultBm = null;
        try {
            Bitmap proBm = decodeFile(filePath, options);
            if (null == proBm) {
                return null;
            }
            resultBm = formatCameraPictureOriginal(filePath, proBm);
        } catch (Throwable ex) {
            Logger.e(TAG, ex);
        }
        return resultBm;
    }

    public static Bitmap getSmallBitmapZoom(String filePath, int w, int h) {
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, w, h);
////        options.inSampleSize = computeSampleSize(options, -1, w * h);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//
//        Bitmap b = BitmapFactory.decodeFile(filePath, options);
        Bitmap smallBm = getSmallBitmap(filePath, w, h);
        Bitmap zoomBm = zoomBitmap(smallBm, w, h);
        ABIOUtil.recycleBitmap(smallBm);
        return zoomBm;
    }

    public static Bitmap getSmallBitmapQuality(String filePath, int w, int h, int quality) {
        Bitmap bm = getSmallBitmap(filePath, w, h);

        if (null == bm || quality >= 100 || quality <= 0) {
            return bm;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        ABIOUtil.recycleBitmap(bm);
        return bitmap;
    }


    /**
     * 压缩到指定大小容量
     *
     * @param image
     * @param size
     * @return
     */
    public static ByteArrayInputStream compressImage(Bitmap image, int size) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > size) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return isBm;
    }

    public static void compressImage2SD(File file, String srcPath, float ww, float hh, int size) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        decodeFile(srcPath, newOpts);//此时返回bm为空

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        Bitmap bitmap = decodeFile(srcPath, newOpts);
        if (null == bitmap) {
            return;
        }

        bitmap = formatCameraPictureOriginal(srcPath, bitmap); // 保证图片方向正常

        ByteArrayInputStream isBm = compressImage(bitmap, size);//把压缩后的数据baos存放到ByteArrayInputStream中
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            byte[] buffer = new byte[isBm.available()];
            isBm.read(buffer);
            os.write(buffer, 0, buffer.length);
            os.flush();
        } catch (Exception e) {
            Logger.e(TAG, e);
        } finally {
            ABIOUtil.closeIO(isBm, os);
            ABIOUtil.recycleBitmap(bitmap);
        }

    }

    /**
     * Compress image
     *
     * @param filePath
     * @param ww
     * @param hh
     * @param size
     * @return
     */
    public static InputStream compressImage(String filePath, float ww, float hh, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        decodeFile(filePath, newOpts);//此时返回bm为空

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;

        Bitmap bitmap = decodeFile(filePath, newOpts);
        if (null == bitmap) {
            return null;
        }
        InputStream resultIs = compressImage(bitmap, size);
        ABIOUtil.recycleBitmap(bitmap);
        return resultIs;
    }


    public static Bitmap getCompressedImage(String srcPath, float ww, float hh, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        decodeFile(srcPath, newOpts);//此时返回bm为空

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;

        Bitmap bitmap = null;
        ByteArrayInputStream commpressedBm = null;
        Bitmap resultBm = null;
        try {
            bitmap = decodeFile(srcPath, newOpts);
            if (null == bitmap) {
                return null;
            }
            commpressedBm = compressImage(bitmap, size);
            resultBm = BitmapFactory.decodeStream(
                    commpressedBm, // 缩小到指定容量
                    null, null);//把ByteArrayInputStream数据生成图片
        } finally {
            ABIOUtil.recycleBitmap(bitmap);
            ABIOUtil.closeIO(commpressedBm);
        }
        return resultBm;
    }

    /***************************************图片压缩计算 END****************************************/


    /***************************************圆角/投影羽化/倒影 BEGIN****************************************/
    /**
     * 对图片进行圆角处理
     *
     * @param bitmap  要处理的Bitmap对象
     * @param roundPx 圆角半径设置
     * @return Bitmap对象
     * @author com.tiantian
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
     *
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
     *
     * @param bitmap
     * @return
     * @author com.tiantian
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
     *
     * @param bitmap 要放缩的Bitmap对象
     * @param width  放缩后的宽度
     * @param height 放缩后的高度
     * @return 放缩后的Bitmap对象
     * @author com.tiantian
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
     * 放缩图片处理
     *
     * @param bitmap      要放缩的Bitmap对象
     * @param widthScale  放缩率
     * @param heightScale 放缩率
     * @return 放缩后的Bitmap对象
     * @author com.tiantian
     */
    public static Bitmap zoomBitmapScale(Bitmap bitmap, float widthScale, float heightScale) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = widthScale;
        float scaleHeight = heightScale;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * Drawable缩放
     *
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
        ABIOUtil.recycleBitmap(oldbmp);
        return new BitmapDrawable(newbmp);
    }

    /**
     * 将Bitmap转化为Drawable
     *
     * @param bitmap
     * @return
     * @author com.tiantian
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     * @return
     * @author com.tiantian
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


    /***********************************图片基本基本信息 BEGIN*************************************/

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static int readPictureDegreeFromExif(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            Logger.e(TAG, e);
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImage(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        ABIOUtil.recycleBitmap(bitmap);
        return resizedBitmap;
    }

    /**
     * 处理相机照片旋转角度
     *
     * @param path 用于获取原图的信息
     * @return 原图的bitmap（可以是被压缩过的）
     */
    public static Bitmap formatCameraPictureOriginal(String path, Bitmap bitmap) {
        /**
         * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
         */
        int degree = ABImageProcess.readPictureDegreeFromExif(path);
        if (0 == degree) {
            return bitmap;
        }
        /**
         * 把图片旋转为正的方向
         */
        Bitmap newbitmap = rotaingImage(degree, bitmap);
        ABIOUtil.recycleBitmap(bitmap);
        return newbitmap;
    }

    /**
     * 处理相机照片旋转角度
     *
     * @param path
     * @return
     */
    public static Bitmap formatCameraPicture(String path) {
        /**
         * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
         */
        int degree = ABImageProcess.readPictureDegreeFromExif(path);
        BitmapFactory.Options opts = new BitmapFactory.Options();//获取缩略图显示到屏幕上
        opts.inSampleSize = 2;
        Bitmap cbitmap = decodeFile(path, opts);
        if (null == cbitmap) {
            return null;
        }
        /**
         * 把图片旋转为正的方向
         */
        Bitmap newbitmap = rotaingImage(degree, cbitmap);
        ABIOUtil.recycleBitmap(cbitmap);
        return newbitmap;
    }

    /***********************************图片基本基本信息 END*************************************/


    /***********************************图片初始化 BEGIN*************************************/
    /**
     * 从Resource中获取Drawable，并初始化bound
     *
     * @param context
     * @param drawableResId
     * @param bound
     * @return
     */
    public static Drawable getResourceDrawableBounded(Context context, int drawableResId, int bound) {
        Drawable drawable = null;
        try {
            drawable = context.getResources().getDrawable(drawableResId);
            drawable.setBounds(0, 0, bound, bound);
        } catch (Exception ex) {
            Logger.e(TAG, ex);
        }
        return drawable;
    }

    /**
     * ********************************图片初始化 END************************************
     */

    private static Bitmap decodeFile(String pathName, BitmapFactory.Options options) {
        Bitmap resultBm = null;
        try {
            resultBm = BitmapFactory.decodeFile(pathName, options);
        } catch (Throwable throwable) {
            Logger.e(TAG, throwable);
        }
        return resultBm;
    }

}
