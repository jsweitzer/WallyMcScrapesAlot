package dulce.wallpaperapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class category_select extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.desnuts.AsyncPracticeProject.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_select, menu);
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
    //these three onClicks send the appropriate starterUrls over to image_browser//
    public void nature(View view){
        String starterUrl = "http://www.reddit.com/r/earthporn";
        Intent intent = new Intent(this, image_browser.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void aww(View view){
        String starterUrl = "http://www.reddit.com/r/aww";
        Intent intent = new Intent(this, image_browser.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void space(View view){
        String starterUrl = "http://www.reddit.com/r/spaceporn";
        Intent intent = new Intent(this, image_browser.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
}
