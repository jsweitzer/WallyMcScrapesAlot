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
    //seed urls to generate image url lists//
    public void nature(View view){
        String starterUrl = "http://www.reddit.com/r/earthporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void apophysis(View view){
        String starterUrl = "http://www.reddit.com/r/fractalporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void space(View view){
        String starterUrl = "http://www.reddit.com/r/spaceporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void random(View view){
        String starterUrl = "http://www.reddit.com/r/wallpapers";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void quote(View view){
        String starterUrl = "http://www.reddit.com/r/specart";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void cars(View view){
        String starterUrl = "http://www.reddit.com/r/pics";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void beaches(View view){
        String starterUrl = "http://www.reddit.com/r/beachporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void bonsai(View view){
        String starterUrl = "http://www.reddit.com/r/bonsaiporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void cities(View view){
        String starterUrl = "http://www.reddit.com/r/cityporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void clouds(View view){
        String starterUrl = "http://www.reddit.com/r/cloudporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void comics(View view){
        String starterUrl = "http://www.reddit.com/r/comicbookporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void food(View view){
        String starterUrl = "http://www.reddit.com/r/culinaryporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void design(View view){
        String starterUrl = "http://www.reddit.com/r/designporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void engineering(View view){
        String starterUrl = "http://www.reddit.com/r/engineeringporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void longexposures(View view){
        String starterUrl = "http://www.reddit.com/r/exposureporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void fire(View view){
        String starterUrl = "http://www.reddit.com/r/fireporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void fossils(View view){
        String starterUrl = "http://www.reddit.com/r/fossilporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void futuristic(View view){
        String starterUrl = "http://www.reddit.com/r/futureporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void gaming(View view){
        String starterUrl = "http://www.reddit.com/r/gamerporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void geology(View view){
        String starterUrl = "http://www.reddit.com/r/geologyporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void guns(View view){
        String starterUrl = "http://www.reddit.com/r/gunporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void heavy(View view){
        String starterUrl = "http://www.reddit.com/r/heavymind";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void ice(View view){
        String starterUrl = "http://www.reddit.com/r/iceporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void illusion(View view){
        String starterUrl = "http://www.reddit.com/r/illusionporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void cyber(View view){
        String starterUrl = "http://www.reddit.com/r/imaginarycyberpunk";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void island(View view){
        String starterUrl = "http://www.reddit.com/r/islandporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void lakes(View view){
        String starterUrl = "http://www.reddit.com/r/lakeporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void lightning(View view){
        String starterUrl = "http://www.reddit.com/r/lightningporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void light(View view){
        String starterUrl = "http://www.reddit.com/r/lightpainting";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void macro(View view){
        String starterUrl = "http://www.reddit.com/r/macroporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void maps(View view){
        String starterUrl = "http://www.reddit.com/r/mapporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void medieval(View view){
        String starterUrl = "http://www.reddit.com/r/medievalporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void megalith(View view){
        String starterUrl = "http://www.reddit.com/r/megalithporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void micro(View view){
        String starterUrl = "http://www.reddit.com/r/microporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void military(View view){
        String starterUrl = "http://www.reddit.com/r/militaryporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void mineral(View view){
        String starterUrl = "http://www.reddit.com/r/mineralporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void mtg(View view){
        String starterUrl = "http://www.reddit.com/r/mtgporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void mushrooms(View view){
        String starterUrl = "http://www.reddit.com/r/mushroomporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void pixels(View view){
        String starterUrl = "http://www.reddit.com/r/pixelart";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void rivers(View view){
        String starterUrl = "http://www.reddit.com/r/riverporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void seacreatures(View view){
        String starterUrl = "http://www.reddit.com/r/seacreatureporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void silhouettes(View view){
        String starterUrl = "http://www.reddit.com/r/silhouetteporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void starships(View view){
        String starterUrl = "http://www.reddit.com/r/starshipporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void streetart(View view){
        String starterUrl = "http://www.reddit.com/r/streetartporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void trains(View view){
        String starterUrl = "http://www.reddit.com/r/trainporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void trippy(View view){
        String starterUrl = "http://www.reddit.com/r/trippy";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void weather(View view){
        String starterUrl = "http://www.reddit.com/r/weatherporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void winter(View view){
        String starterUrl = "http://www.reddit.com/r/winterporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
    public void sculptures(View view){
        String starterUrl = "http://www.reddit.com/r/sculptureporn";
        Intent intent = new Intent(this, gridViewThumbs.class);
        intent.putExtra(EXTRA_MESSAGE, starterUrl);
        startActivity(intent);
    }
}
