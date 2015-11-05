package com.kesden.b250.groupfour.group4padsim;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDragListener, OnTouchListener {

    private ImageView draggedOrb, enteredOrb;
    private BoardFactory bFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Creates activity window */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Gets window size */
        Display d = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);

        /* instantiate instance variables */
        draggedOrb = null;
        enteredOrb = null;
        bFactory = new BoardFactory(this, p);

        /* Populate list of ImageViews */
        ArrayList<ImageView> orbList = new ArrayList<>(30);
        createOrbs(orbList);

        /* Populate board and set listeners */
        bFactory.populateBoard(orbList);

        /* Add listener to main layout */
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        mainLayout.setOnDragListener(this);
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
        list.add(0,(ImageView) findViewById(R.id.imageView1));
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
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                // Handle Enter
                if (v instanceof ImageView) {

                    if (enteredOrb != null) {
                        draggedOrb = enteredOrb;
                    }

                    enteredOrb = (ImageView) v;
                    swapOrbImages(draggedOrb, enteredOrb);
                    enteredOrb.setVisibility(View.INVISIBLE);
                    draggedOrb.setVisibility(View.VISIBLE);
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                // Handle Exit
                break;
            case DragEvent.ACTION_DROP:
                if (enteredOrb != null)
                    enteredOrb.setVisibility(View.VISIBLE);

                enteredOrb = null;
                draggedOrb = null;

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                // Handle End
            default:
                break;
        }
        return true;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // PRESSED
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                draggedOrb = (ImageView)v;
                draggedOrb.setVisibility(View.INVISIBLE);
                return true; // if you want to handle the touch event
            case MotionEvent.ACTION_UP:
                // RELEASED
                return true; // if you want to handle the touch event
        }
        return false;
    }

    public void swapOrbImages(ImageView view1, ImageView view2) {
        if (view1 != null && view2 != null) {
            Bitmap swap;
            swap = ((BitmapDrawable) view1.getDrawable()).getBitmap();
            view1.setImageBitmap(((BitmapDrawable) view2.getDrawable()).getBitmap());
            view2.setImageBitmap(swap);
        }
    }
}
