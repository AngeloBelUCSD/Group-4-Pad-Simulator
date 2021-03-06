package com.kesden.b250.groupfour.group4padsim;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements OnDragListener, OnTouchListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Random rand;
    private GameManager manager;

    private OrbView draggedOrb, enteredOrb;
    private OrbMatcher matcher;
    private BoardFactory bFactory;
    private ProgressBar pBar;

    private int lScore;
    private int difficulty;
    private int mode;
    private boolean dragStarted;
    private boolean timerEnded = false;
    private boolean dialogDisplayed = false;

    private TextView timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Creates activity window */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Gets window size */
        Display d = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);

        /* instantiate instance variables */
        draggedOrb = null;
        enteredOrb = null;
        bFactory = new BoardFactory(10, this, p);
        matcher = new OrbMatcher(bFactory);
        pBar = (ProgressBar)findViewById(R.id.progressBar);
        lScore = 0;
        SettingsManager settings = new SettingsManager(this);
        mode = settings.getMode();
        difficulty = settings.getDifficulty();
        manager = new GameManager(matcher, difficulty, mode, pBar);
        timeText = (TextView) findViewById(R.id.textView3);
        manager.startGlobalTimer(30, timeText);

        /* Populate board and set listeners */
        bFactory.populateBoard();

        /* Add listener to main layout */
        RelativeLayout mainLayout =(RelativeLayout) findViewById(R.id.main_layout);
        mainLayout.setOnDragListener(this);

        /* Add padding to Grid Layout */
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid);

        /* Hide the Title and Status Bar */
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    /*
    When the activity ends, the high score table is updated.
    */
    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(mode == 0) return;

        HighScoreManager HSManager = new HighScoreManager(this);
        SettingsManager SManager = new SettingsManager(this);

        String player_name = SManager.getPlayerName();

        HSManager.updateHighScores(player_name, lScore);
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

    public void changeText(int changeNum){
        // Gives motivational text on top layout.
        /* We could pass something here to signify motivational text or other kinds of text to change
          and add more TextViews. i.e. pass something to signify score and change user score on top. */
        rand = new Random();

        String[] motiArray = {"You can do it!", "Believe in yourself!", "Brain power",
                "Caffeine overload","Don't give up", "Mega combo time!", "Good Luck!"};

        TextView motiText = (TextView)findViewById(R.id.textView2);
        TextView scoreText = (TextView)findViewById(R.id.textView1);
        int turnScore = manager.getScore();

        switch(changeNum){
            case 1:
                // Score change when match occurs
                // update score variable. Will use placeholder for now.
                motiText.setText(motiArray[rand.nextInt(7)]);
                break;
            case 2:
                lScore+=turnScore;
                scoreText.setText("Current Score: " + Integer.toString(lScore));

                if (turnScore > 0)
                    motiText.setText("Matched " + manager.totalOrbs() + " Orbs!");
                break;

            default:
                break;

        }

        ((View) scoreText.getParent()).invalidate();


    }
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        if(manager.getEndTimer() || manager.isGameOver()) {
            if (enteredOrb != null)
                enteredOrb.setVisibility(View.VISIBLE);
            if (draggedOrb != null)
                draggedOrb.setVisibility(View.VISIBLE);
            enteredOrb = null;
            draggedOrb = null;
            matcher.sort();

            if (manager.isGameOver()) {
                displayDialog();
            } else {
                changeText(2);
            }

            manager.updateGlobalTimer(manager.totalOrbs());
            dragStarted = false;
            manager.setEndTimer(false);
            timerEnded = true;
            pBar.setProgress(0);



        }
        else if(timerEnded){
            if(action == DragEvent.ACTION_DRAG_ENDED)
                timerEnded = false;
        }
        else{
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
                    if (dragStarted) {
                        matcher.sort();
                        changeText(2);

                        manager.updateGlobalTimer(manager.totalOrbs());

                        // Reset progress bar on drag end
                        manager.stopDragTimer();
                        pBar.setProgress(0);

                        dragStarted = false;
                    }
                    // Handle End
                default:
                    break;
            }
        }
        return true;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!manager.isGameOver()) {
                    // PRESSED
                    changeText(1); // changes text on top layout.
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
                    dragStarted = true;
                    timerEnded = false;
                    manager.setEndTimer(false);
                    manager.startTimer();
                    manager.resetScore();
                    matcher.reset();
                    draggedOrb = (OrbView) v;
                    draggedOrb.setVisibility(View.INVISIBLE);
                    Log.d(TAG, /*"Orb ID is " + draggedOrb.getID() + */ "isMatched: " + draggedOrb.isMatched() + " and (Row,Col) is (" + draggedOrb.getRow() + ", " + draggedOrb.getCol() + ")");
                    return true; // if you want to handle the touch event
                } else {
                    displayDialog();
                }
            case MotionEvent.ACTION_UP:
                if (manager.isGameOver())
                {
                    displayDialog();
                }
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

            boolean swapMatched = view1.isMatched();
            view1.setMatched(view2.isMatched());
            view2.setMatched(swapMatched);
        }
    }

    public OrbMatcher getMatcher() {
        return matcher;
    }

    public GameManager getManager () {
        return manager;
    }


    public void displayDialog () {

        if (!dialogDisplayed) {

            dialogDisplayed = true;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("You scored " + lScore + "!")
                    .setTitle("Finish!");

            AlertDialog dialog = builder.create();

            dialog.show();

            new CountDownTimer(3000, 60) {
                @Override
                public void onTick(long millisUntilFinished) {
                    pBar.setProgress(pBar.getProgress() + 1);
                }

                @Override
                public void onFinish() {
                    finish();
                }
            }.start();
        }
    }
}
