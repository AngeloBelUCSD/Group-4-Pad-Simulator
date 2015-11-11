package com.kesden.b250.groupfour.group4padsim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class BoardFactory {


    Random rand;
    ArrayList<Integer> colorList;
    ArrayList<OrbView> orbList;
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
        list.add(0, (OrbView) activity.findViewById(R.id.imageView1));
        list.add(1, (OrbView) activity.findViewById(R.id.imageView2));
        list.add(2, (OrbView) activity.findViewById(R.id.imageView3));
        list.add(3, (OrbView) activity.findViewById(R.id.imageView4));
        list.add(4, (OrbView) activity.findViewById(R.id.imageView5));
        list.add(5, (OrbView) activity.findViewById(R.id.imageView6));
        list.add(6, (OrbView) activity.findViewById(R.id.imageView7));
        list.add(7, (OrbView) activity.findViewById(R.id.imageView8));
        list.add(8, (OrbView) activity.findViewById(R.id.imageView9));
        list.add(9, (OrbView) activity.findViewById(R.id.imageView10));
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
    }

    public void populateBoard() {
        createOrbs(orbList);

        for(int i = 0; i<orbList.size();i++) {
            OrbView orb = orbList.get(i);
            int randInt = rand.nextInt(6);
            int orbColorID = colorList.get(randInt);
            int orbID = 0;

            switch (randInt) {
                case 0:
                    orbID = OrbView.RED_ORB;
                    break;
                case 1:
                    orbID = OrbView.DARK_ORB;
                    break;
                case 2:
                    orbID = OrbView.HEAL_ORB;
                    break;
                case 3:
                    orbID = OrbView.LIGHT_ORB;
                    break;
                case 4:
                    orbID = OrbView.BLUE_ORB;
                    break;
                case 5:
                    orbID = OrbView.GREEN_ORB;
                    break;
            }
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), orbColorID);
            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, (int)(point.x / 6.1), (int)(point.x / 6.1), true);
            orb.setImageBitmap(newBitmap);
            orb.setID(orbID);
            orb.setOnTouchListener(activity);
            orb.setOnDragListener(activity);
        }
    }
}
