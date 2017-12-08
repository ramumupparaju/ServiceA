package com.incon.service.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ImageUtils {
    private static final float BITMAP_SCALE = 0.4f;
    private static Bitmap b;
    private static final float BLUR_RADIUS = 7.5f;
    private static final float BLUR_RADIUS_1 = 25.0f;

    public static Bitmap blur(Context mContext, Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(mContext);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(
                renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
       // image.recycle();
        renderScript.destroy();
        return outputBitmap;
    }

    public static Bitmap fullBlur(Context mContext, Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(mContext);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(
                renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS_1);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }



    public static Bitmap blur1(Context context, Bitmap bitmap) {
        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(
                bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(context);

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the in/out Allocations with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(25.f);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;

    }
    public static Bitmap blur(ImageView v) {
        return blur(v.getContext(), getScreenshot(v));
    }


    private static Bitmap getScreenshot(final ImageView v) {

        v.post(new Runnable() {
            @Override
            public void run() {
                b = Bitmap.createBitmap(
                        v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                v.draw(c);
            }
        });

        return b;
    }


    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage, int width, int height) {
        Bitmap targetBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        Rect rect = new Rect(0, 0, width, height);

        path.addRoundRect(new RectF(rect),
                ((float) width) / 2,
                ((float) width) / 2,
                Path.Direction.CW);
        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
                sourceBitmap.getHeight()), new Rect(0, 0, width, height), null);
        return targetBitmap;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int targetWidth, int targetHeight) {
        Bitmap output = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 40;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap getRoundedCornerWorkBitmap(Bitmap bitmap) {
        int targetWidth = 80;
        int targetHeight = 80;
        Bitmap output = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 5;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                inContext.getContentResolver(), inImage, "Camera pick url", null);
        if (path != null) {
            return Uri.parse(path);
        }
        return null;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        /*Cursor cursor = null;
        try {
            String[] proj = {MediaStore.MediaColumns.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }*/

        return FileUtils.getPath(context, contentUri);
    }

    public static Bitmap decodeSampledBitmapFromResource(byte[] bytes, int reqWidth, int reqHeight)
    {
        Bitmap bitmap = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        // Calculate inSampleSize
        if (reqHeight != -1) {
            if (bitmap.getWidth() >= bitmap.getHeight()) {
                bitmap = Bitmap.createBitmap(bitmap,
                        bitmap.getWidth() / 2 - bitmap.getHeight() / 2, 0,
                        bitmap.getHeight(), bitmap.getHeight());
            } else {
                bitmap = Bitmap.createBitmap(bitmap,
                        0, bitmap.getHeight() / 2 - bitmap.getWidth() / 2,
                        bitmap.getWidth(), bitmap.getWidth());
            }
        } else {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
        }

        return bitmap;
    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
        Bitmap bitmap = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        // Calculate inSampleSize
        if (reqHeight != -1) {
            if (bitmap.getWidth() >= bitmap.getHeight()) {
                bitmap = Bitmap.createBitmap(bitmap,
                        bitmap.getWidth() / 2 - bitmap.getHeight() / 2, 0,
                        bitmap.getHeight(), bitmap.getHeight());
            } else {
                bitmap = Bitmap.createBitmap(bitmap,
                        0, bitmap.getHeight() / 2 - bitmap.getWidth() / 2,
                        bitmap.getWidth(), bitmap.getWidth());
            }
        } else {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
        }

        return bitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static String compressImage(String filePath) {

        /*File file = new File(filePath);
        int fileSize = Integer.parseInt(String.valueOf(file.length()/1024));
        if(fileSize < 100)
        {
            return filePath;
        }*/

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels
// are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 1920.0f;
        float maxWidth = 1080.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2,
                new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out;

        String filename = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM) + "/" + UUID.randomUUID() + "_"
                + System.currentTimeMillis() + ".jpg";
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public static Bitmap writeTextOnDrawable(Context context, int drawableId, String text) {
        Bitmap bm = BitmapFactory.decodeResource(
                context.getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);
        Typeface tf = Typeface.create("Helvetica", Typeface.NORMAL);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(140);
        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);
        Canvas canvas = new Canvas(bm);
        canvas.drawText(text, canvas.getWidth() / 2,
                ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)), paint);
        return bm;
    }

    public static String resizeImage(String path) {
        String tempPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM) + "/" + UUID.randomUUID()
                + "_" + System.currentTimeMillis() + ".jpg";
        Bitmap b = decodeSampledBitmapFromResource(path, 1080, 1920);
        Bitmap out = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), false);

        File file = new File(tempPath);
        file.delete();
        FileOutputStream fOut;
        try {
            file.createNewFile();
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
            return tempPath;
        } catch (Exception e) { }
        return null;
    }

   /* public static Bitmap getResizedImage()
    {

    }*/

    public static Bitmap roundedImageCorners(final Bitmap source, int width,
                                             int height, final int radius, final int margin) {

        Bitmap resized = Bitmap.createScaledBitmap(source, width, height, true);
        Bitmap output = null;
        try {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(
                    resized, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            output = Bitmap.createBitmap(
                    resized.getWidth(), resized.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(
                    margin, margin, resized.getWidth() - margin,
                    resized.getHeight() - margin), radius, radius, paint);

            if (resized != output) {
                resized.recycle();
            }
        }
        catch (Exception e) {

        }
        return output;
    }

    /*public static int getNotificationIcon() {
        boolean useSilhouette = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        return useSilhouette ?  R.mipmap.ic_launcher : R.drawable.ic_launcher;
    }*/


    public static Bitmap createDrawableFromView(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(
                view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);

        return bitmap;
    }
}