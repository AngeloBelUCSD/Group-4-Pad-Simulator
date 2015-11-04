package com.kesden.b250.groupfour.group4padsim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Display d = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);

        int totalOrbs = 36;

        ArrayList<Integer> colorList = new ArrayList<Integer>();
        colorList.add(0,R.drawable.redorb);
        colorList.add(1,R.drawable.dark);
        colorList.add(2,R.drawable.heart);
        colorList.add(3,R.drawable.light);
        colorList.add(4,R.drawable.water);
        colorList.add(5,R.drawable.wood);

        ArrayList<ImageView> orbList = new ArrayList<ImageView>();

        Random rand = new Random();

        createOrbs(orbList);
        for(int i = 0; i<totalOrbs;i++) {
            ImageView orb = orbList.get(i);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), colorList.get(rand.nextInt(6)));
            //Bitmap newBitmap = bitmap.createScaledBitmap(bitmap, (p.x)/6, (p.y)/10, true);
            Bitmap newBitmap = bitmap.createScaledBitmap(bitmap, p.x / 7, p.x / 7, true);
            orb.setImageBitmap(newBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void createOrbs(ArrayList<ImageView> list)
    {
        list.add(0,(ImageView) findViewById(R.id.imageView));
        list.add(1,(ImageView) findViewById(R.id.imageView2));
        list.add(2,(ImageView) findViewById(R.id.imageView3));
        list.add(3,(ImageView) findViewById(R.id.imageView4));
        list.add(4,(ImageView) findViewById(R.id.imageView5));
        list.add(5,(ImageView) findViewById(R.id.imageView6));
        list.add(6,(ImageView) findViewById(R.id.imageView7));
        list.add(7,(ImageView) findViewById(R.id.imageView8));
        list.add(8,(ImageView) findViewById(R.id.imageView9));
        list.add(9,(ImageView) findViewById(R.id.imageView10));
        list.add(10,(ImageView) findViewById(R.id.imageView11));
        list.add(11,(ImageView) findViewById(R.id.imageView12));
        list.add(12,(ImageView) findViewById(R.id.imageView13));
        list.add(13,(ImageView) findViewById(R.id.imageView14));
        list.add(14,(ImageView) findViewById(R.id.imageView15));
        list.add(15,(ImageView) findViewById(R.id.imageView16));
        list.add(16,(ImageView) findViewById(R.id.imageView17));
        list.add(17,(ImageView) findViewById(R.id.imageView18));
        list.add(18,(ImageView) findViewById(R.id.imageView19));
        list.add(19,(ImageView) findViewById(R.id.imageView20));
        list.add(20,(ImageView) findViewById(R.id.imageView21));
        list.add(21,(ImageView) findViewById(R.id.imageView22));
        list.add(22,(ImageView) findViewById(R.id.imageView23));
        list.add(23,(ImageView) findViewById(R.id.imageView24));
        list.add(24,(ImageView) findViewById(R.id.imageView25));
        list.add(25,(ImageView) findViewById(R.id.imageView26));
        list.add(26,(ImageView) findViewById(R.id.imageView27));
        list.add(27,(ImageView) findViewById(R.id.imageView28));
        list.add(28,(ImageView) findViewById(R.id.imageView29));
        list.add(29,(ImageView) findViewById(R.id.imageView30));
        list.add(30,(ImageView) findViewById(R.id.imageView31));
        list.add(31,(ImageView) findViewById(R.id.imageView32));
        list.add(32,(ImageView) findViewById(R.id.imageView33));
        list.add(33,(ImageView) findViewById(R.id.imageView34));
        list.add(34,(ImageView) findViewById(R.id.imageView35));
        list.add(35,(ImageView) findViewById(R.id.imageView36));

    }
}
