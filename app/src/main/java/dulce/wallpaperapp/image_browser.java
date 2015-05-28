package dulce.wallpaperapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
    String url = null;
    Context context = this;
    public ArrayList<String> urlList = new ArrayList<String>();
    private int select = 0;
    private ProgressBar pd;
    private String starterUrl = "http://wwww.reddit.com/r/pics";
    private Bitmap currentBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browser);
        //textView = (TextView) findViewById(R.id.textView);
        pd = (ProgressBar) findViewById(R.id.progress);
        nextButton = (Button) findViewById(R.id.next);
        previousButton = (Button) findViewById(R.id.previous);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        starterUrl = intent.getStringExtra(category_select.EXTRA_MESSAGE);
        getUrlTask task = new getUrlTask();
        //Toast.makeText(this, starterUrl, Toast.LENGTH_LONG).show();
        task.execute(starterUrl);
    }

    private class getUrlTask extends AsyncTask<String, Void, ArrayList<String>> {
        private Document doc = null;
        public ArrayList<String> linkList = new ArrayList<String>();
        @Override
        protected ArrayList<String> doInBackground(String... params){
            String rooturl = params[0];
            try{
                //pull full source
                doc = Jsoup.connect(rooturl)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .timeout(3000)
                        .get();
                //select and store links to .jpg files on imgur
                Elements aList = doc.select("a");
                for(Element link : aList){
                    String href = link.attr("href");
                    if(href.contains("imgur.com")&&!href.contains("domain")&&href.contains(".jp")){
                        if(!linkList.contains(href)){
                            linkList.add(href);
                        }
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }

            return linkList;
        }
        @Override
        protected void onPostExecute(ArrayList<String> result){
            //set textView for testing purposes
            //textView.setText(result.get(0));
            url = linkList.get(0);
            urlList = linkList;
            setWallpaper initial = new setWallpaper();
            initial.execute(url);
            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //download wallpaper and set as background
    private class setWallpaper extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute(){
            pd.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
            ImageLoader.getInstance().init(config.build());
            Bitmap result = ImageLoader.getInstance().loadImageSync(url);
            return result;
        }
        @Override
        protected void onPostExecute(Bitmap result){
            /*try{
                WallpaperManager.getInstance(context).setBitmap(result);
            }catch(IOException e){
                e.printStackTrace();
            }*/
            currentBitmap = result;
            imageView.setImageBitmap(result);
            pd.setVisibility(View.INVISIBLE);
        }
    }

    /*public void onClick(View view) {
        getUrlTask task = new getUrlTask();
        task.execute("http://reddit.com/r/iwallpaper");
    }*/

    /*public void onClickSet(View view) {
        setWallpaper set = new setWallpaper();
        set.execute(url);
    }*/

    public void onClickNext(View view){
        if(select < (urlList.size() - 1)) {
            select = select + 1;

            url = urlList.get(select);
            setWallpaper setNext = new setWallpaper();
            setNext.execute(url);
        }else{
            Toast.makeText(getApplicationContext(), R.string.end_list, Toast.LENGTH_LONG).show();
        }
    }

    public void onClickPrevious(View view){
        if(select>0) {
            select = select - 1;

            url = urlList.get(select);
            setWallpaper setPrevious = new setWallpaper();
            setPrevious.execute(url);
        }else{
            Toast.makeText(getApplicationContext(), R.string.begin_list, Toast.LENGTH_LONG).show();
        }
    }

    /*public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }*/

    public void onClickSave(View view){
        try {
            WallpaperManager.getInstance(context).setBitmap(currentBitmap);
        }catch(IOException e){
            e.printStackTrace();
        }
        /*Uri u = getImageUri(this, currentBitmap);
        Intent intent = new Intent (this, image_browser.class);
        WallpaperManager.getInstance(context).getCropAndSetWallpaperIntent(u);
        //Toast.makeText(getApplicationContext(), R.string.toast_save_message, Toast.LENGTH_LONG).show();*/
    }
}
