package com.kesden.b250.groupfour.group4padsim;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDragListener, OnTouchListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private OrbView draggedOrb, enteredOrb;
    private BoardFactory bFactory;
    private Random rand;
    private OrbMatcher matcher;

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
        bFactory = new BoardFactory(30, this, p);
        matcher = new OrbMatcher(bFactory);

        /* Populate board and set listeners */
        bFactory.populateBoard();

        /* Add listener to main layout */
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        mainLayout.setOnDragListener(this);

        /* Add padding to Grid Layout */
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid);

        /* Hide the Title and Status Bar */
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    public void changeText(){
        // Gives motivational text on top layout.
        /* We could pass something here to signify motivational text or other kinds of text to change
          and add more TextViews. i.e. pass something to signify score and change user score on top. */
        rand = new Random();

        String[] motiArray = {"You can do it!", "Believe in yourself!", "Brain power",
                "Caffeine overload","Don't give up", "Mega combo time!", "Good Luck!"};

        TextView motiText = (TextView)findViewById(R.id.textView2);
        motiText.setText(motiArray[rand.nextInt(7)]);

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

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                // Handle Enter
                if (v instanceof ImageView) {

                    if (enteredOrb != null) {
                        draggedOrb = enteredOrb;
                    }

                    enteredOrb = (OrbView) v;
                    swapOrbImages(draggedOrb, enteredOrb);
                    draggedOrb.setVisibility(View.VISIBLE);
                    enteredOrb.setVisibility(View.INVISIBLE);
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                // Handle Exit

                break;
            case DragEvent.ACTION_DROP:
                if (enteredOrb != null)
                    enteredOrb.setVisibility(View.VISIBLE);
                if (draggedOrb != null)
                    draggedOrb.setVisibility(View.VISIBLE);

                enteredOrb = null;
                draggedOrb = null;

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                // Handle End
                matcher.threeSort();
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
                changeText(); // changes text on top layout.
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                draggedOrb = (OrbView) v;
                draggedOrb.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Orb ID is " + draggedOrb.getID());
                return true; // if you want to handle the touch event
            case MotionEvent.ACTION_UP:
                // RELEASED
                return true; // if you want to handle the touch event
        }
        return false;
    }

    public void swapOrbImages(OrbView view1, OrbView view2) {
        if (view1 != null && view2 != null) {
            Bitmap swap;
            swap = ((BitmapDrawable) view1.getDrawable()).getBitmap();
            view1.setImageBitmap(((BitmapDrawable) view2.getDrawable()).getBitmap());
            view2.setImageBitmap(swap);

            int swapID = view1.getID();
            view1.setID(view2.getID());
            view2.setID(swapID);

        }
    }
}
