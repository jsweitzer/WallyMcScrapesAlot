package dulce.wallpaperapp;

import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;

public class image_browser extends ActionBarActivity {

    private Button nextButton;
    private Button previousButton;
    private ImageView imageView;

    //getUrlTask will scrape urls leading to .jpg files from starterUrl//
    private String starterUrl = "http://wwww.reddit.com/r/pics";
    //the urls found by getUrlTask will be stored and assigned to url//
    String url = null;

    Context context = this;
    public ArrayList<String> urlList = new ArrayList<String>();

    //int select is used to keep track of position in urlList and prevent//
    //arrayOutOfBounds exceptions//
    private int select = -1;
    private ProgressBar pd;
    private Bitmap currentBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browser);
        pd = (ProgressBar) findViewById(R.id.progress);
        nextButton = (Button) findViewById(R.id.next);
        previousButton = (Button) findViewById(R.id.previous);
        imageView = (ImageView) findViewById(R.id.imageView);
        urlList = gridViewThumbs.bigList;
        Toast.makeText(context, urlList.get(0), Toast.LENGTH_SHORT).show();
        //onCreate receives intent from category_select and//
        //passes the string extra to getUrlTask//
        Intent intent = getIntent();
        starterUrl = intent.getStringExtra(GridViewActivity.URL_TO_BROWSER);
        getImage task = new getImage();
        task.execute(starterUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.//
        getMenuInflater().inflate(R.menu.menu_image_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will//
        // automatically handle clicks on the Home/Up button, so long//
        // as you specify a parent activity in AndroidManifest.xml.//
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement//
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //getImage gets the image and stores it as bitmap//
    private class getImage extends AsyncTask<String, Void, Bitmap> {

        BitmapFactory bf = new BitmapFactory();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap result;
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File img = new File(path, "image.jpg");
        String fullPath;
        int h;
        int w;


        @Override
        protected void onPreExecute(){
            //spinny loading thing//
            pd.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {

                URL url = new URL(starterUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(connection.getInputStream());
                OutputStream out = new FileOutputStream(img);

                byte data[] = new byte[1024];
                long total = 0;
                int count;

                while ((count = in.read(data)) != -1) {
                    total += count;
                    // for progress update
                    out.write(data, 0, count);
                }

                out.flush();
                out.close();
                in.close();



                opts.inJustDecodeBounds = true;

                fullPath = path+"/image.jpg";

                bf.decodeFile(fullPath, opts);

                h = opts.outHeight;
                w = opts.outWidth;

                if(h > 2480 || w > 2480){
                    opts.inSampleSize = 2;
                    opts.inJustDecodeBounds = false;
                    result = bf.decodeFile(fullPath, opts);
                }

                System.out.println("height = "+h +"\nwidth = "+ w);


            }catch(IOException e){
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(Bitmap result){
            if(select == -1){
                select = GridViewActivity.index;
            }
            currentBitmap = result;
            //the image is shown in the image view, but not set as the wallpaper//
            imageView.setImageBitmap(result);
            pd.setVisibility(View.INVISIBLE);
            Toast.makeText(context, starterUrl, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickNext(View view){
        //this if else sends the url from the next index to getImage and//
        //splashes a toast message if user reaches the end of the url list//
        if(select < (urlList.size() - 1)) {
            select = select + 1;
            starterUrl = urlList.get(select);
            getImage setNext = new getImage();
            setNext.execute(starterUrl);
        }else{
            Toast.makeText(getApplicationContext(), R.string.end_list, Toast.LENGTH_LONG).show();
        }
    }

    public void onClickPrevious(View view){
        if(select>0) {
            select = select - 1;
            starterUrl = urlList.get(select);
            getImage setPrevious = new getImage();
            setPrevious.execute(starterUrl);
        }else{
            Toast.makeText(getApplicationContext(), R.string.begin_list, Toast.LENGTH_LONG).show();
        }
    }
    //onClickSave sets the image currently being displayed as the background//
    public void onClickSave(View view){
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(path, "currentPic.png");
        try {
            path.mkdirs();
            OutputStream out = new FileOutputStream(file);
            currentBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            Uri uri = Uri.fromFile(file);
            Uri content_uri = getImageContentUri(this, file);
            //WallpaperManager.getInstance(context).setBitmap(currentBitmap);
            Intent intent = WallpaperManager.getInstance(context).getCropAndSetWallpaperIntent(content_uri);
            startActivity(intent);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static Uri getImageContentUri(Context context, File imageFile){

        String filePath = imageFile.getAbsolutePath();

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
            if(imageFile.exists()) {

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

