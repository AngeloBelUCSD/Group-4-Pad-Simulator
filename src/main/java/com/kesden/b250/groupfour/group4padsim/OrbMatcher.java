package com.kesden.b250.groupfour.group4padsim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.util.Arrays.*;


public class OrbMatcher {
    private BoardFactory factory;
    private int[] threeList;
    private ArrayList<Integer> fillList;
    private Random rand;

    MainActivity activity;

    public OrbMatcher(BoardFactory bFactory){
        this.factory = bFactory;
        threeList = new int[35];
        fill(threeList, 0);
        activity = bFactory.activity;
    }

    public void threeSort(){
        ArrayList<OrbView> orbs = factory.orbList;
        for(int i = 0; i<orbs.size();i++) {
            OrbView orb = orbs.get(i);
            if(i>=2)
            {
                if(!(i%6==0) || !(i%6==1))
                {
                    if(orbs.get(i-2).getID()==(orbs.get(i-1).getID())
                            &&orbs.get(i-1).getID()==orbs.get(i).getID())
                    {
                        //tag horizontal 3s
                        threeList[i]=orbs.get(i).getID();
                        threeList[i-1]=orbs.get(i-1).getID();
                        threeList[i-2]=orbs.get(i-2).getID();
                        //replace with new orbs
                        for(int x = i-2;i<=i;i++) {
                            OrbView newOrb = orbs.get(x);
                            int drawableID = factory.colorList.get(rand.nextInt(6));
                            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), drawableID);
                            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, factory.point.x / 6, factory.point.y / 10, true);
                            newOrb.setImageBitmap(newBitmap);
                            newOrb.setID(drawableID);
                            //add to fillList
                            fillList.add(i);
                        }
                    }
                }
                if(i>11)
                {
                    if(orbs.get(i-12).getID()==(orbs.get(i-6).getID())
                            &&orbs.get(i-6).getID()==orbs.get(i).getID())
                    {
                        //tag vertical 3s
                        threeList[i]=orbs.get(i).getID();
                        threeList[i-6]=orbs.get(i-6).getID();
                        threeList[i-12]=orbs.get(i-12).getID();
                        //replace with new orbs
                        for(int x = i-12;i<=i;i+=6) {
                            OrbView newOrb = orbs.get(x);
                            int drawableID = factory.colorList.get(rand.nextInt(6));
                            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), drawableID);
                            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, factory.point.x / 6, factory.point.y / 10, true);
                            newOrb.setImageBitmap(newBitmap);
                            newOrb.setID(drawableID);
                            //add to fillList
                            fillList.add(i);
                        }
                        fillList.add(i);
                    }
                }
            }
        }
    }

}
