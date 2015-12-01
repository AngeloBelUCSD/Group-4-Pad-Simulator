package com.kesden.b250.groupfour.group4padsim;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.graphics.Path;
import android.animation.AnimatorListenerAdapter;
import android.animation.Animator;
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
        /* I do not think we need to create a new orb. */
        OrbView targetOrb = createOrb(row, col);

        /* There is a row of invisible orbs at the top of the screen.
        *  The invisible orbs will randomly get a color and turn itself visible.
        *  Then it switches with the target orb (which is invisible). */
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

    private void cascadeAnimation(final OrbView topOrb, final OrbView targetOrb) {

        /* Takes two orbViews. Animating Orb and its target orb (for the cell location) */

        final int[] topOrbCoord= new int[2];
        topOrb.getLocationOnScreen(topOrbCoord);

        final int[] targetOrbCoord = new int[2];
        targetOrb.getLocationOnScreen(targetOrbCoord);

        //double topX = (double)topOrbCoord[0] + topOrb.getWidth()/2.0;
        final double topY = (double)topOrbCoord[1] + topOrb.getHeight()/2.0;
        //double targetX = (double)targetOrbCoord[0]  + targetOrb.getWidth()/2.0;
        final double targetY = (double)targetOrbCoord[1]  + targetOrb.getHeight()/2.0;
        final double travelDist = topY - targetY;

        /* Start Animation */
        final ObjectAnimator topOrbAnimator = ObjectAnimator.ofFloat(topOrb,
                OrbView.TRANSLATION_Y, (int)(-travelDist));
        topOrbAnimator.setDuration(300);
        topOrbAnimator.setRepeatCount(1);
        topOrbAnimator.setRepeatMode(ValueAnimator.REVERSE);
        topOrbAnimator.start();

        topOrbAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                topOrb.setVisibility(View.INVISIBLE);
                topOrbAnimator.setDuration(1);
                swapOrbImages(topOrb, targetOrb);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                targetOrb.setVisibility(View.VISIBLE);
            }
        });

        /* Hidden animation. Invisible orb that has been matched will replace the orb that cascaded
        * down.
        * Scenario:
        *   If existing orb cascaded, then targetOrb will switch with existing Orb, then
        *   top row (invisible orbs) will replace the targetOrb at the call of cascadeNewOrb(). */

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

    public void testCascade() {
        /* FOR TESTING PURPOSES */

        final OrbView topOrb = orbList.get(10);
        final OrbView targetOrb = orbList.get(22);
        //targetOrb.setVisibility(View.INVISIBLE);
        //cascadeNewOrb(2, 5);


        int[] img_coordinates = new int[2];
        //ImageView image = (ImageView) findViewById(R.id.myImage);

        topOrb.getLocationOnScreen(img_coordinates);

        int[] img_targetCoord = new int[2];
        targetOrb.getLocationOnScreen(img_targetCoord);

        final double topX = (double)img_coordinates[0] + topOrb.getWidth()/2.0;
        final double topY = (double)img_coordinates[1] + topOrb.getHeight()/2.0;
        final double targetX = (double)img_targetCoord[0]  + targetOrb.getWidth()/2.0;
        final double targetY = (double)img_targetCoord[1]  + targetOrb.getHeight()/2.0;
        final double travelDist = topY-targetY;

        /*TranslateAnimation testCasc = new TranslateAnimation((float)topX, (float)targetX, (float)topY, (float)targetY);

        testCasc.setDuration(50);

        topOrb.startAnimation(testCasc);*/

        Path path = new Path();

        path.moveTo((float) topX, (float) topY);
        path.lineTo((float) (targetX + 50), (float) (targetY + 50));

        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topOrb, OrbView.TRANSLATION_Y, (int)-travelDist);



        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(targetOrb, OrbView.TRANSLATION_Y, (int)travelDist);
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(1);

        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
        /*objectAnimator1.setDuration(10);
        objectAnimator1.start();
        objectAnimator1.setRepeatCount(1);
        objectAnimator1.setRepeatMode(ValueAnimator.REVERSE);*/


        targetOrb.setVisibility(View.VISIBLE);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                topOrb.setVisibility(View.INVISIBLE);
                objectAnimator.setDuration(10);

                swapOrbImages(topOrb, targetOrb);
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                /*ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(topOrb, OrbView.TRANSLATION_Y, (int)travelDist);
                objectAnimator2.setDuration(5000);*/

                /*ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(targetOrb, OrbView.TRANSLATION_Y, (int)-travelDist);
                objectAnimator3.setDuration(5000);*/
                //swapOrbImages(topOrb, targetOrb);
                //topOrb.setVisibility(View.INVISIBLE);
                //objectAnimator2.start();
                //objectAnimator3.start();
                //swapOrbImages(topOrb, targetOrb);
                topOrb.setVisibility(View.VISIBLE);

            }
        });


        //topOrb.setVisibility(View.INVISIBLE);


        //swapOrbImages(topOrb, targetOrb);


        /*ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(topOrb, OrbView.TRANSLATION_Y, (int)((targetY-topY)));
        objectAnimator2.setDuration(10);
        objectAnimator2.start();*/

    }
}
