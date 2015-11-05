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
    ArrayList<ImageView> orbList;
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

    public void createOrbs(ArrayList<ImageView> list)
    {
        list.add(0,(ImageView) activity.findViewById(R.id.imageView1));
        list.add(1,(ImageView) activity.findViewById(R.id.imageView2));
        list.add(2,(ImageView) activity.findViewById(R.id.imageView3));
        list.add(3,(ImageView) activity.findViewById(R.id.imageView4));
        list.add(4,(ImageView) activity.findViewById(R.id.imageView5));
        list.add(5,(ImageView) activity.findViewById(R.id.imageView6));
        list.add(6,(ImageView) activity.findViewById(R.id.imageView7));
        list.add(7,(ImageView) activity.findViewById(R.id.imageView8));
        list.add(8,(ImageView) activity.findViewById(R.id.imageView9));
        list.add(9,(ImageView) activity.findViewById(R.id.imageView10));
        list.add(10,(ImageView) activity.findViewById(R.id.imageView11));
        list.add(11,(ImageView) activity.findViewById(R.id.imageView12));
        list.add(12,(ImageView) activity.findViewById(R.id.imageView13));
        list.add(13,(ImageView) activity.findViewById(R.id.imageView14));
        list.add(14,(ImageView) activity.findViewById(R.id.imageView15));
        list.add(15,(ImageView) activity.findViewById(R.id.imageView16));
        list.add(16,(ImageView) activity.findViewById(R.id.imageView17));
        list.add(17,(ImageView) activity.findViewById(R.id.imageView18));
        list.add(18,(ImageView) activity.findViewById(R.id.imageView19));
        list.add(19,(ImageView) activity.findViewById(R.id.imageView20));
        list.add(20,(ImageView) activity.findViewById(R.id.imageView21));
        list.add(21,(ImageView) activity.findViewById(R.id.imageView22));
        list.add(22,(ImageView) activity.findViewById(R.id.imageView23));
        list.add(23,(ImageView) activity.findViewById(R.id.imageView24));
        list.add(24,(ImageView) activity.findViewById(R.id.imageView25));
        list.add(25,(ImageView) activity.findViewById(R.id.imageView26));
        list.add(26,(ImageView) activity.findViewById(R.id.imageView27));
        list.add(27,(ImageView) activity.findViewById(R.id.imageView28));
        list.add(28,(ImageView) activity.findViewById(R.id.imageView29));
        list.add(29,(ImageView) activity.findViewById(R.id.imageView30));
    }

    public void populateBoard() {
        createOrbs(orbList);

        for(int i = 0; i<orbList.size();i++) {
            ImageView orb = orbList.get(i);
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), colorList.get(rand.nextInt(6)));
            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, point.x / 7, point.x / 7, true);
            orb.setImageBitmap(newBitmap);
            orb.setOnTouchListener(activity);
            orb.setOnDragListener(activity);
        }
    }
}
