package com.kesden.b250.groupfour.group4padsim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.Random;


public class BoardFactory {

    private Random rand;
    public ArrayList<Integer> colorList;
    public ArrayList<OrbView> orbList;



    MainActivity activity;
    Point point;

    public BoardFactory(int size, MainActivity activity, Point point) {
        rand = new Random();

        colorList = new ArrayList<>(6);
        colorList.add(0,R.drawable.redorb);
        colorList.add(1,R.drawable.dark);
        colorList.add(2,R.drawable.heart);
        colorList.add(3,R.drawable.light);
        colorList.add(4,R.drawable.water);
        colorList.add(5,R.drawable.wood);


        this.activity = activity;
        this.point = point;

        orbList = new ArrayList<>(size);


    }

    public void createOrbs(ArrayList<OrbView> list)
    {

        list.add(0,(OrbView) activity.findViewById(R.id.imageView1));
        list.add(1,(OrbView) activity.findViewById(R.id.imageView2));
        list.add(2,(OrbView) activity.findViewById(R.id.imageView3));
        list.add(3,(OrbView) activity.findViewById(R.id.imageView4));
        list.add(4,(OrbView) activity.findViewById(R.id.imageView5));
        list.add(5,(OrbView) activity.findViewById(R.id.imageView6));
        list.add(6,(OrbView) activity.findViewById(R.id.imageView7));
        list.add(7,(OrbView) activity.findViewById(R.id.imageView8));
        list.add(8,(OrbView) activity.findViewById(R.id.imageView9));
        list.add(9,(OrbView) activity.findViewById(R.id.imageView10));
        list.add(10,(OrbView) activity.findViewById(R.id.imageView11));
        list.add(11,(OrbView) activity.findViewById(R.id.imageView12));
        list.add(12,(OrbView) activity.findViewById(R.id.imageView13));
        list.add(13,(OrbView) activity.findViewById(R.id.imageView14));
        list.add(14,(OrbView) activity.findViewById(R.id.imageView15));
        list.add(15,(OrbView) activity.findViewById(R.id.imageView16));
        list.add(16,(OrbView) activity.findViewById(R.id.imageView17));
        list.add(17,(OrbView) activity.findViewById(R.id.imageView18));
        list.add(18,(OrbView) activity.findViewById(R.id.imageView19));
        list.add(19,(OrbView) activity.findViewById(R.id.imageView20));
        list.add(20,(OrbView) activity.findViewById(R.id.imageView21));
        list.add(21,(OrbView) activity.findViewById(R.id.imageView22));
        list.add(22,(OrbView) activity.findViewById(R.id.imageView23));
        list.add(23,(OrbView) activity.findViewById(R.id.imageView24));
        list.add(24,(OrbView) activity.findViewById(R.id.imageView25));
        list.add(25,(OrbView) activity.findViewById(R.id.imageView26));
        list.add(26,(OrbView) activity.findViewById(R.id.imageView27));
        list.add(27,(OrbView) activity.findViewById(R.id.imageView28));
        list.add(28,(OrbView) activity.findViewById(R.id.imageView29));
        list.add(29,(OrbView) activity.findViewById(R.id.imageView30));

        /* The invisible Orbs */
        list.add(30,(OrbView) activity.findViewById(R.id.imageView31));
        list.add(31,(OrbView) activity.findViewById(R.id.imageView32));
        list.add(32,(OrbView) activity.findViewById(R.id.imageView33));
        list.add(33,(OrbView) activity.findViewById(R.id.imageView34));
        list.add(34,(OrbView) activity.findViewById(R.id.imageView35));
        list.add(35,(OrbView) activity.findViewById(R.id.imageView36));


    }


    private OrbView createOrb(int row, int col) {

        /**/
        int calcI = (row * 5) + col;
        OrbView orb = orbList.get(calcI);
        int orbID = rand.nextInt(6);

        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), colorList.get(orbID));
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, point.x / 6, (int) (point.y / 10.5), true);

        orb.setOrb(newBitmap, orbID);

        //set invisible here
        orb.setVisibility(View.INVISIBLE);

        return orb;
    }

    public void cascadeNewOrb(int row, int col) {


        /* targetOrb is invisible */
        OrbView targetOrb = createOrb(row, col);

        /* topOrb is initially invisible */
        int orbID = rand.nextInt(6);
        OrbView topOrb = orbList.get(30 + col);
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), colorList.get(orbID));
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, point.x / 6, (int) (point.y / 10.5), true);
        topOrb.setOrb(newBitmap, orbID);
        topOrb.setVisibility(View.VISIBLE);

        /* calls cascadeAnimation to animate from top row to target row */
        cascadeAnimation(topOrb, targetOrb);

    }

    public void cascadeExistingOrb(OrbView topOrb, OrbView targetOrb) {

        cascadeAnimation(topOrb, targetOrb);

    }

    private void cascadeAnimation(OrbView topOrb, OrbView targetOrb) {

        /* Takes two orbViews. Animating Orb and its target orb (for the cell location) */

        float topX = topOrb.getX();
        float topY = topOrb.getY();
        float targetX = targetOrb.getX();
        float targetY = targetOrb.getY();


        TranslateAnimation cascade = new TranslateAnimation(topX, targetX, topY, targetY);
        cascade.setDuration(250);

        TranslateAnimation cascadeBack = new TranslateAnimation(targetX, topX, targetY, topY);
        cascadeBack.setDuration(1);


        topOrb.startAnimation(cascade);

        /* Set invisible and quickly put it back to top row. */
        topOrb.setVisibility(View.INVISIBLE);
        topOrb.startAnimation(cascadeBack);


        /* Quickly swap after cascade animation
        *  What swaps is the invisible topOrb that went back to the top row
        *  and the invisible target orb.*/
        swapOrbImages(topOrb, targetOrb);
        targetOrb.setVisibility(View.VISIBLE);

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


    public void populateBoard() {
        createOrbs(orbList);

        for(int i = 0; i<orbList.size()-6;i++) {
            OrbView orb = orbList.get(i);
            int orbID = rand.nextInt(6);
            if(i>=2)
            {
                if(!(i%6==0) || !(i%6==1))
                {
                    while(orbList.get(i-2).getID()==(orbList.get(i-1).getID())
                        &&orbList.get(i-1).getID()==orbID)
                    {
                        orbID = rand.nextInt(6);
                    }
                }
                if(i>11)
                {
                    while(orbList.get(i-12).getID()==(orbList.get(i-6).getID())
                            &&orbList.get(i-6).getID()==orbID)
                    {
                        orbID = rand.nextInt(6);
                    }
                }
            }
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), colorList.get(orbID));
            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, point.x / 6, (int)(point.y / 10.5), true);

            orb.setOrb(newBitmap, orbID);

            orb.setOnTouchListener(activity);
            orb.setOnDragListener(activity);
        }

        /* Making the top row. Invisible */
        for(int i = orbList.size()-6; i<orbList.size(); i++) {
            OrbView orb = orbList.get(i);
            int orbID = rand.nextInt(1);
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), colorList.get(orbID));
            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, point.x / 6, (int) (point.y / 10.5), true);

            orb.setOrb(newBitmap, orbID);
            orb.setVisibility(View.INVISIBLE);

        }


    }
}
