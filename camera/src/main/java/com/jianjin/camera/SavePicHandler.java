package com.jianjin.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateFormat;

import com.jianjin.camera.utils.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import static com.jianjin.camera.utils.Consts.SAVE_IMG_PATH;
import static com.jianjin.camera.utils.Consts.SAVE_PICTURE_FAILURE;
import static com.jianjin.camera.utils.Consts.SAVE_PICTURE_SUCCESS;

/**
 * Created by Administrator on 2018/5/10.
 */
public class SavePicHandler extends Handler {

    private static final String TAG = SavePicHandler.class.getSimpleName();
    private Handler handler;
    private byte[] data;
    private boolean isBackCamera;
    private boolean sampleSizeSuggested;
    private boolean ioExceptionRetried;     // 寻找合适的bitmap发生io异常  允许一次重试

    /**
     * Use the provided {@link Looper} instead of the default one.
     *
     * @param looper The looper, must not be null.
     */
    public SavePicHandler(Looper looper, Handler handler, byte[] data, boolean isBackCamera) {
        super(looper);
        this.handler = handler;
        sampleSizeSuggested = false;
        ioExceptionRetried = false;
        this.data = data;
        this.isBackCamera = isBackCamera;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        String imgPath = saveToSDCard(data);
        Message m = Message.obtain();
        if (imgPath == null) {
            m.what = SAVE_PICTURE_FAILURE;
        } else {
            m.what = SAVE_PICTURE_SUCCESS;
            Bundle bundle = new Bundle();
            bundle.putString(SAVE_IMG_PATH, imgPath);
            // bundle.putString(SAVE_IMG_PARENT_PATH, mFilePath);
            m.setData(bundle);
        }
        handler.sendMessage(m);
    }

    private String saveToSDCard(byte[] data) {
        File pictureDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (pictureDir == null) {
            return null;
        }

        String mParentFilePath = FileUtils.getPhotoPathForLockWallPaper();

        String mImagePath = mParentFilePath + File.separator
                + new DateFormat().format("yyyyMMddHHmmss",
                new Date()).toString()
                + ".jpg";

        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        //先进行等比压缩 宽1240， 高等比例，a4纸宽和高的比例为7：10
        bmp = compressBitmap(bmp, false, 1772, 1240);
        bmp = rotateReversalBitmap(bmp, isBackCamera);
        boolean isSuccess = save(bmp, mImagePath);

        if (!isSuccess) {
            return null;
        }
        setPictureDegreeZero(mImagePath);
        /*Bitmap bitmap = findFitBitmap(data, getCropRect(data), 1);

        if (bitmap == null) {
            return false;
        }
        return saveBitmap(bitmap, mImagePath, 100, Bitmap.CompressFormat.JPEG);*/
        return mImagePath;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm 需要旋转的图片
     * @return 旋转后的图片
     */
    public static Bitmap rotateReversalBitmap(Bitmap bm, boolean isBackCamera) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(isBackCamera ? 90 : 270);
        // 前置摄像头，处理镜像问题
        if (!isBackCamera) {
            matrix.postScale(-1, 1);
        }
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    private boolean save(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // 创建文件夹
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos); // 向缓冲区之中压缩图片
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 等比压缩（宽高等比缩放）
     *
     * @param bitmap
     * @param needRecycle
     * @param targetWidth
     * @param targeHeight
     * @return
     */
    public Bitmap compressBitmap(Bitmap bitmap, boolean needRecycle, int targetWidth, int targeHeight) {
        float sourceWidth = bitmap.getWidth();
        float sourceHeight = bitmap.getHeight();

        float scaleWidth = targetWidth / sourceWidth;
        float scaleHeight = targeHeight / sourceHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight); //长和宽放大缩小的比例
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (needRecycle) {
            bitmap.recycle();
        }
        bitmap = bm;
        return bitmap;
    }

    /**
     * 将图片的旋转角度置为0  ，此方法可以解决某些机型拍照后图像，出现了旋转情况
     *
     * @param path
     * @return void
     */
    private void setPictureDegreeZero(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            // 修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
            // 例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
            if (isBackCamera) {
                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "ExifInterface.ORIENTATION_ROTATE_90");
            } else {
                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "ExifInterface.ORIENTATION_ROTATE_270");
            }
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
