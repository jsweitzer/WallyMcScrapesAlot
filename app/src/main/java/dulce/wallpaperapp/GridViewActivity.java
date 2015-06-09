package dulce.wallpaperapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class GridViewActivity extends Activity {

    public static String URL_TO_BROWSER = category_select.EXTRA_MESSAGE;
    public static int index = 0;
    Context context = this;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_layout);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                index = position;
                list = gridViewThumbs.bigList;
                Intent intent = new Intent(context, image_browser.class);
                intent.putExtra(URL_TO_BROWSER, list.get(position));
                startActivity(intent);


            }
        });
    }
}
