package dulce.wallpaperapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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


        @Override
        protected void onPreExecute(){
            //spinny loading thing//
            pd.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
            ImageLoader.getInstance().init(config.build());
            Bitmap result = ImageLoader.getInstance().loadImageSync(starterUrl);
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

