package dulce.wallpaperapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
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
    private int select = 0;
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
        //onCreate receives intent from category_select and//
        //passes the string extra to getUrlTask//
        Intent intent = getIntent();
        starterUrl = intent.getStringExtra(category_select.EXTRA_MESSAGE);
        getUrlTask task = new getUrlTask();
        task.execute(starterUrl);
    }
        //getUrlTask parses some html and generates a list of urls
    private class getUrlTask extends AsyncTask<String, Void, ArrayList<String>> {
        private Document doc = null;
        public ArrayList<String> linkList = new ArrayList<String>();
        @Override
        protected ArrayList<String> doInBackground(String... params){
            //starterUrl comes here
            String rooturl = params[0];
            try{
                //pull full source of starterUrl. Thank you Jsoup
                doc = Jsoup.connect(rooturl)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .timeout(3000)
                        .get();
                //select and store links to .jpg files on imgur
                Elements aList = doc.select("a");
                for(Element link : aList){
                    String href = link.attr("href");
                    if(!href.contains("domain")&&href.contains(".jpg")){
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
            //the url in the first index of the generated list is passed//
            //to setWallpaper and the next and previous//
            //buttons are made visible.//
            url = linkList.get(0);
            urlList = linkList;
            getImage initial = new getImage();
            initial.execute(url);
            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
        }
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
            Bitmap result = ImageLoader.getInstance().loadImageSync(url);
            return result;
        }
        @Override
        protected void onPostExecute(Bitmap result){
            currentBitmap = result;
            //the image is shown in the image view, but not set as the wallpaper//
            imageView.setImageBitmap(result);
            pd.setVisibility(View.INVISIBLE);
        }
    }

    public void onClickNext(View view){
        //this if else sends the url from the next index to getImage and//
        //splashes a toast message if user reaches the end of the url list//
        if(select < (urlList.size() - 1)) {
            select = select + 1;
            url = urlList.get(select);
            getImage setNext = new getImage();
            setNext.execute(url);
        }else{
            Toast.makeText(getApplicationContext(), R.string.end_list, Toast.LENGTH_LONG).show();
        }
    }

    public void onClickPrevious(View view){
        if(select>0) {
            select = select - 1;
            url = urlList.get(select);
            getImage setPrevious = new getImage();
            setPrevious.execute(url);
        }else{
            Toast.makeText(getApplicationContext(), R.string.begin_list, Toast.LENGTH_LONG).show();
        }
    }
    //onClickSave sets the image currently being displayed as the background//
    public void onClickSave(View view){
        try {
            WallpaperManager.getInstance(context).setBitmap(currentBitmap);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
