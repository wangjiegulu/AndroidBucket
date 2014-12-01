package com.wangjie.androidbucket.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.webkit.MimeTypeMap;
import com.wangjie.androidbucket.application.ABApplication;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.services.network.exception.HippoException;
import com.wangjie.androidbucket.services.network.toolbox.PoolingByteArrayOutputStream;

import java.io.*;

public class ABFileUtil {

    public static final String TAG = ABFileUtil.class.getSimpleName();

    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().toString();

    public static String getSDPATH() {
        return SD_CARD_PATH;
    }

    public static File obtainDirF(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static String obtainDirS(String path) {
        return obtainDirF(path).getAbsolutePath();
    }


    /**
     * 在SD卡上创建文件
     *
     * @throws java.io.IOException
     */
    public static File creatSDFile(String fileRelativePath) throws IOException {
        File file = new File(SD_CARD_PATH + fileRelativePath);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirRelativePath
     */
    public static File creatSDDir(String dirRelativePath) {
        File dir = new File(SD_CARD_PATH + dirRelativePath);
        dir.mkdirs();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public static boolean isFileExist(String fileRelativePath) {
        File file = new File(SD_CARD_PATH + fileRelativePath);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public static File write2SDFromInput(String relativePath, String fileName, InputStream input) {
        if (!relativePath.endsWith("/")) {
            relativePath = relativePath + "/";
        }
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(relativePath);
            file = creatSDFile(relativePath + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            int length = 0;
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ABIOUtil.closeIO(output);
        }
        return file;
    }

    /**
     * 先质量压缩到90%，再把bitmap保存到sd卡上
     *
     * @param relativePath
     * @param fileName
     * @param bm
     * @return
     * @author com.tiantian
     */
    public static int saveBitmap2SD(String relativePath, String fileName, Bitmap bm) {
        return saveBitmap2SD(relativePath, fileName, bm, 90);
    }

    /**
     * 先质量压缩到指定百分比（0% ~ 90%），再把bitmap保存到sd卡上
     *
     * @param relativePath
     * @param fileName
     * @param bm
     * @param quality
     * @return
     */
    public static int saveBitmap2SD(String relativePath, String fileName, Bitmap bm, int quality) {
        if (!relativePath.endsWith("/")) {
            relativePath = relativePath + "/";
        }
        File file = null;
        FileOutputStream out = null;
        try {
            creatSDDir(relativePath);
            file = creatSDFile(relativePath + fileName);
            out = new FileOutputStream(file.getPath());
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            ABIOUtil.closeIO(out);
        }
    }

    /**
     * 先质量压缩到指定百分比（0% ~ 90%），再把bitmap保存到sd卡上
     *
     * @param filePath
     * @param bm
     * @param quality
     * @return
     */
    public static int saveBitmap2SDAbsolute(String filePath, Bitmap bm, int quality) {
        File file = null;
        FileOutputStream out = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file.getPath());
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            ABIOUtil.closeIO(out);
        }
    }


    /**
     * 压缩图片直到容量小于200kb，并保存到sdcard
     *
     * @param relativePath
     * @param fileName
     * @param bm
     * @return
     */
    public static int saveBitmap2SDWithCapacity(String relativePath, String fileName, Bitmap bm) {
        return saveBitmap2SDWithCapacity(relativePath, fileName, bm, 200);
    }

    /**
     * 压缩图片直到容量小于指定值(kb)，并保存到sdcard
     *
     * @param relativePath
     * @param fileName
     * @param bm
     * @param capacity
     * @return
     */
    public static int saveBitmap2SDWithCapacity(String relativePath, String fileName, Bitmap bm, int capacity) {
        if (!relativePath.endsWith("/")) {
            relativePath = relativePath + "/";
        }
        File file = null;
        FileOutputStream out = null;
        ByteArrayInputStream bais = null;
        try {
            creatSDDir(relativePath);
            file = creatSDFile(relativePath + fileName);
            out = new FileOutputStream(file.getPath());
//            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            bais = compressImage(bm, capacity);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bais.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            ABIOUtil.closeIO(out, bais);
        }

    }

    /**
     * 压缩图片直到容量小于指定值(kb)
     *
     * @param image
     * @param capacity
     * @return
     */
    public static ByteArrayInputStream compressImage(Bitmap image, int capacity) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > capacity) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            if (options < 10) {
                break;
            }
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        baos.reset();
        return bais;
    }


    /**
     * 获取某个文件夹的大小 ，单位是kb
     *
     * @param relativePath
     * @return 返回-1表示这是个文件而不是文件夹
     * @author com.tiantian
     */
    public static int getFolderSize(String relativePath) {
        int fileLength = 0;
//		File dir = new File(path);
        File dir = creatSDDir(relativePath);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                fileLength += file.length();
            }
        } else {
            return -1;
        }
        return fileLength / 1024;
    }

    /**
     * 清空指定文件夹
     *
     * @param relativePath
     * @author com.tiantian
     */
    public static void deleteFiles(String relativePath) {
//		File dir = new File(path);
        File dir = creatSDDir(relativePath);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                file.delete();
            }
        }
    }


    /**
     * 把uri转为File对象
     *
     * @param context
     * @param uri
     * @return
     */
    public static File uri2File(Context context, Uri uri) {

        // 在api level 11前可以用以下代码
//        String[] proj = { MediaStore.Images.Media.DATA };
//        Cursor actualimagecursor = context.managedQuery(uri,proj,null,null,null);
//        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        actualimagecursor.moveToFirst();
//        String img_path = actualimagecursor.getString(actual_image_column_index);
//        File file = new File(img_path);

        // 而managedquery在api 11 被弃用，所以要转为使用CursorLoader,并使用loadInBackground来返回
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();
            if (null == cursor) {
                return null;
            }
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return new File(cursor.getString(column_index));
        } catch (Exception ex) {
            Logger.e(TAG, ex);
        }
        return null;
    }
    public static File uri2FileInteral(Context context, Uri uri){
        if(null == uri){
            return null;
        }
        File file = uri2File(context, uri);
        return null == file ? new File(uri.getPath()) : file;
    }


    public static void openFile(Context context, String path) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = getMimeType(path);
        //参数二type见下面
        intent.setDataAndType(Uri.fromFile(new File(path)), type);
        context.startActivity(intent);
    }

    public static String getMimeType(String uri) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(uri);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static void deleteFile(File... files) {
        if (!ABTextUtil.isEmpty(files)) {
            for (File file : files) {
                try {
                    file.delete();
                } catch (RuntimeException e) {
                    Logger.e(TAG, e);
                }
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param file
     */
    public static void deleteFolder(File file) {
        if (file.exists() && file.isDirectory()) {//判断是文件还是目录
            if (file.listFiles().length > 0) {//若目录下没有文件则直接删除
                //若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = file.listFiles();
                int i = file.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        deleteFolder(delFile[j]);//递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();//删除文件  
                }
            }
            file.delete();
        }
    }

    /**
     * 删除文件夹
     *
     * @param filePath
     */
    public static void deleteFolder(String filePath) {
        File file = new File(filePath);
        deleteFolder(file);
    }


    /**
     * 从文件中读取Byte数组
     * Add by Hubert
     *
     * @param file
     * @return byte[]
     * @throws IOException
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        // 获取文件大小
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            // 文件太大，无法读取
            throw new IOException("File is to large " + file.getName());
        }

        // 创建一个数据来保存文件数据
        byte[] bytes = new byte[(int) length];

        // 读取数据到byte数组中
        int offset = 0;

        int numRead = 0;

        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // 确保所有数据均被读取
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();

        return bytes;
    }

    public static byte[] getBytesFromInputStream(InputStream in) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int count;
        while ((count = in.read(data, 0, 4096)) != -1)
            outStream.write(data, 0, count);
        return outStream.toByteArray();
    }

    public static File getFileFromInputStream(File file, InputStream ins) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    /**
     * 获取拍照的图片路径
     *
     * @return
     */
    public static File getDCIMFile() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }


}