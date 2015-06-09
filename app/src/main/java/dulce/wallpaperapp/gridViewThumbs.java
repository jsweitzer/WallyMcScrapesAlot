package dulce.wallpaperapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class gridViewThumbs extends Activity {

    private ProgressBar pb;
    public static ArrayList<String> thumbList = new ArrayList<String>();
    public static ArrayList<String> bigList = new ArrayList<String>();
    Context context = this;
    public static ArrayList<Uri> thumbFilePaths = new ArrayList<Uri>();
    String starterUrl = "http://www.reddit.com/r/pics";

    private static final String DISK_CACHE_SUBDIR = "thumbnails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browser);
        //pd = (ProgressBar) findViewById(R.id.progress);
        //onCreate receives intent from category_select and//
        //passes the string extra to getUrlTask//
        Intent intent = getIntent();
        starterUrl = intent.getStringExtra(category_select.EXTRA_MESSAGE);
        getBigSmallUrlList task = new getBigSmallUrlList();
        task.execute(starterUrl);
    }

    private class getBigSmallUrlList extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params){

            String rootUrl = params[0];
            Document doc = null;

            try{
                doc = Jsoup.connect(starterUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .timeout(0)
                        .get();
                Elements aList = doc.select("a");
                for(Element link : aList){

                    String bigLink = link.attr("href");
                    String thumbLink = link.select("img").attr("src");

                    if(bigList.contains(bigLink)|bigList.contains(bigLink+".jpg")|bigLink.contains("https")){
                        continue;
                    }else if(bigLink.contains("imgur")&&!bigLink.contains(".jpg")&&!bigLink.contains("domain")){
                        bigList.add(bigLink+".jpg");
                        thumbList.add("http:"+thumbLink);
                    }else if(!bigLink.contains("domain")&&bigLink.contains(".jpg")){
                        bigList.add(bigLink);
                        thumbList.add("http:"+thumbLink);
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }

            return thumbList;

        }

        @Override
        protected void onPostExecute(ArrayList<String> result){

            downloadThumbs dThumb = new downloadThumbs();
            dThumb.execute(result);

        }
    }
    private class downloadThumbs extends AsyncTask<ArrayList<String>, Void, ArrayList<Uri>>{

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Bitmap result;

        @Override
        protected ArrayList<Uri> doInBackground(ArrayList<String>... params){

            ArrayList<Uri> thumbPaths = new ArrayList<Uri>();
            String thumbName = null;
            ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
            ImageLoader.getInstance().init(config.build());

            for(int i = 0 ; i < thumbList.size() ; i ++) {

                thumbName = "thumb"+i;
                File file = new File(path, thumbName);
                Uri uri = Uri.fromFile(file);
                thumbPaths.add(uri);
                result = ImageLoader.getInstance().loadImageSync(thumbList.get(i));

                try{
                    path.mkdirs();
                    OutputStream out = new FileOutputStream(file);
                    result.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.close();
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
            return thumbPaths;
        }

        @Override
        protected void onPostExecute(ArrayList<Uri> result){

                thumbFilePaths = result;
                Intent intent = new Intent(context, GridViewActivity.class);
                startActivity(intent);

        }
    }
}
