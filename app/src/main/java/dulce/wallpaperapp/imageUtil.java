package dulce.wallpaperapp;

import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Bruce Wayne on 6/16/2015.
 */
public class imageUtil {

    public static final File filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    public static BitmapFactory.Options opts = new BitmapFactory.Options();

    public static int getHeight(String path){
        int h;
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        h = opts.outHeight;
        return h;
    }

     public static int getWidth(String path){
         int w;
         opts.inJustDecodeBounds = true;
         BitmapFactory.decodeFile(path, opts);
         w = opts.outWidth;
         return w;
     }

    public static Bitmap getCompressedImg(String path){
        Bitmap img;
        opts.inSampleSize = 4;
        //opts.inTargetDensity = 306;
        //opts.inDensity = 306;
        //.inScaled = true;
        img = BitmapFactory.decodeFile(path, opts);
        return img;
    }

    public static Bitmap getImg(String path){
        Bitmap img;
        opts.inTargetDensity = 306;
        opts.inDensity = 306;
        opts.inScaled = true;
        img = BitmapFactory.decodeFile(path);
        return img;
    }

    public static void saveImage(File file, Bitmap bm){
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(out!=null){
                    out.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        if(file.mkdirs()){
            System.out.println("Directory created");
        }else{
            System.out.println("Directory not created");
        }
    }

    public static Uri getImageUri(Context context, File file){

        String filePath = file.getAbsolutePath();

        Cursor cursor = context.getContentResolver().query(

                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] {MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=?",
                new String[] {filePath}, null);

        if (cursor != null && cursor.moveToFirst()){

            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);

        }else{
            if(file.exists()) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);

                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            }else{
                return null;
            }
        }
    }
}
