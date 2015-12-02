package com.kesden.b250.groupfour.group4padsim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static java.util.Arrays.*;


public class OrbMatcher {

    public int[] threeList;
    public int[] comboSize;

    private BoardFactory factory;
    private Random rand;
    private ArrayList<ArrayList<Integer>> comboList;
    private ArrayList<ArrayList<Integer>> comboListV;
    private ArrayList<Integer> redCombo, darkCombo, healCombo, lightCombo, blueCombo, greenCombo;
    private ArrayList<Integer> redComboV, darkComboV, healComboV, lightComboV, blueComboV, greenComboV;

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean done;
    private boolean noCascade;

    MainActivity activity;

    //Constructor takes in a BoardFactory object
    public OrbMatcher(BoardFactory bFactory) {
        rand = new Random();
        this.factory = bFactory;
        threeList = new int[30];
        fill(threeList, -1);
        activity = bFactory.activity;

        //instantiation of combo lists
        comboList = new ArrayList<ArrayList<Integer>>();
        redCombo = new ArrayList<Integer>();
        darkCombo = new ArrayList<Integer>();
        healCombo = new ArrayList<Integer>();
        lightCombo = new ArrayList<Integer>();
        blueCombo = new ArrayList<Integer>();
        greenCombo = new ArrayList<Integer>();
        comboList.add(0, redCombo);
        comboList.add(1, darkCombo);
        comboList.add(2, healCombo);
        comboList.add(3, lightCombo);
        comboList.add(4, blueCombo);
        comboList.add(5, greenCombo);

        comboListV = new ArrayList<ArrayList<Integer>>();
        redComboV = new ArrayList<Integer>();
        darkComboV = new ArrayList<Integer>();
        healComboV = new ArrayList<Integer>();
        lightComboV = new ArrayList<Integer>();
        blueComboV = new ArrayList<Integer>();
        greenComboV = new ArrayList<Integer>();
        comboListV.add(0, redComboV);
        comboListV.add(1, darkComboV);
        comboListV.add(2, healComboV);
        comboListV.add(3, lightComboV);
        comboListV.add(4, blueComboV);
        comboListV.add(5, greenComboV);

        comboSize = new int[6];
        done = false;
    }

    public void sort() {
        threeSort();
        comboCheck(threeList);
        pushOrbsToBottom();

    }

    public void continueSort() {
        if (!done) {
            done = true;
            int[] result = comboSize();
            replaceMatchedOrbs();
            resetLists();

            Log.d(TAG, "Red orb: " + result[0] + " Dark orb: " + result[1] + " Heal orb: "
                    + result[2] + " light orb: " + result[3] + " blue orb: " + result[4] + " green orb: "
                    + result[5]);

            if (!noCascade) {
                activity.changeText(2);
                activity.getManager().updateGlobalTimer(activity.getManager().totalOrbs());
            }

            noCascade = true;
        }
    }


    //function to sort out combos
    public void threeSort(){
        ArrayList<OrbView> orbs = factory.orbList;
        for(int i = 0; i<30;i++) {
            OrbView orb = orbs.get(i);
            /*The algorithm uses these if-statements due to the constraint of a one-dimensional array of orbs.
            The first condition of i>=2, along with the subsequent % conditions are to take orbs from the 3rd
            column onward. The second set of conditions are to take orbs from the 3rd row onward.
             */
            if (i >= 2) {
                if (!(i % 6 == 0) && !(i % 6 == 1)) {
                    //here we go horizontal checks for combos
                    if (orbs.get(i - 2).getID() == (orbs.get(i - 1).getID())
                            && orbs.get(i - 1).getID() == orb.getID()) {
                        //tag horizontal 3s
                        int check = i;
                        int add = 0;
                        int type = orb.getID();
                        //obtain the length of the combo(for combos longer than 3)
                        while (check % 6 != 5 && orbs.get(check).getID() == orbs.get(check + 1).getID()) {
                            check++;
                            add++;
                        }
                        //will be handled by orb factory cascade, in the meantime will comment out

                        for (int x = i - 2; x < i + add +1; x++) {
                            threeList[x] = type;
                        }
                    }
                }
                //second set of conditions
                if (i > 11) {
                    //check for vertical combos
                    if (orbs.get(i - 12).getID() == (orbs.get(i - 6).getID())
                            && orbs.get(i - 6).getID() == orb.getID()) {
                        //tag vertical 3s
                        int curr = i;
                        int type = orb.getID();
                        //obtain length of combo
                        while (curr + 6 < 30 && orbs.get(curr).getID() == orbs.get(curr + 6).getID()) {
                            curr += 6;
                        }
                        for (int x = i - 12; x < curr + 1; x += 6) {
                            threeList[x] = type;
                            if(!comboListV.get(type).contains(x))
                            {
                                comboListV.get(type).add(x);
                            }
                        }
                    }
                }
            }
        }
    }


    public void comboCheck(int[] threeList)
    {
        for(int i = 0; i<threeList.length;i++) {
            if (threeList[i] != -1) {
                ArrayList<Integer> adj = new ArrayList<Integer>();
                adj.add(i);
                int type = threeList[i];
                while (!adj.isEmpty()) {
                    int curr = adj.get(0);
                    adj.remove(0);
                    comboList.get(type).add(curr);

                    factory.orbList.get(curr).setMatched(true);

                    if (curr % 6 != 0 && curr<30 && threeList[curr - 1] == type) {
                        if(!adj.contains(curr-1)) {
                            adj.add(curr - 1);
                        }
                    }
                    if (curr % 6 != 5 && curr<30 && threeList[curr + 1] == type) {
                        if(!adj.contains(curr+1)) {
                            adj.add(curr + 1);
                        }
                    }
                    if (curr >= 6 && curr <30 && threeList[curr - 6] == type) {
                        if(!adj.contains(curr-6)) {
                            adj.add(curr - 6);
                        }
                    }
                    if (curr < 24 && threeList[curr + 6] == type) {
                        if(!adj.contains(curr+6)) {
                            adj.add(curr + 6);
                        }
                    }
                    threeList[curr] = -1;
                }
            }
        }
    }

    public int[] comboSize()
    {
        for(int i = 0; i<comboSize.length;i++){
            comboSize[i] = comboList.get(i).size();
        }
        return comboSize;
    }

    public void pushOrbsToBottom()
    {

        noCascade = true;

        // Consider each column in the board
        for (int i = 0; i < 6; i++) {
            OrbView firstOrb = factory.orbList.get(i);
            OrbView secondOrb = factory.orbList.get(i+6);
            OrbView thirdOrb = factory.orbList.get(i+12);
            OrbView fourthOrb = factory.orbList.get(i+18);
            OrbView fifthOrb = factory.orbList.get(i+24);

            ArrayList<OrbView> nonMatchedOrbs = new ArrayList<>(5);

            // push orbs that art not matched into ArrayList
            if (!fifthOrb.isMatched())
                nonMatchedOrbs.add(fifthOrb);
            else
                fifthOrb.setAlpha(0.0f);
            if (!fourthOrb.isMatched())
                nonMatchedOrbs.add(fourthOrb);
            else
                fourthOrb.setAlpha(0.0f);
            if (!thirdOrb.isMatched())
                nonMatchedOrbs.add(thirdOrb);
            else
                thirdOrb.setAlpha(0.0f);
            if (!secondOrb.isMatched())
                nonMatchedOrbs.add(secondOrb);
            else
                secondOrb.setAlpha(0.0f);
            if (!firstOrb.isMatched())
                nonMatchedOrbs.add(firstOrb);
            else
                firstOrb.setAlpha(0.0f);

            int numNonMatchedOrbs = nonMatchedOrbs.size();
            int numMatchedOrbs = 5 - numNonMatchedOrbs;

            if (numNonMatchedOrbs != 5) {
                for (int j = 0; j < numNonMatchedOrbs; j++) {
                    // Is not the same orb
                    if (nonMatchedOrbs.get(j) != factory.orbList.get(i+(6*(4-j)))) {
                        if (numMatchedOrbs > 0) {
                            if (noCascade)
                                noCascade = false;
                            factory.cascadeExistingOrb(nonMatchedOrbs.get(j), factory.orbList.get(i + (6 * (4 - j))), BoardFactory.TARGET_BOTTOM_ORB);
                            numMatchedOrbs--;
                        } else if (factory.orbList.get(i+(6*(4-j))).isMatched()) {
                            factory.cascadeExistingOrb(nonMatchedOrbs.get(j), factory.orbList.get(i + (6 * (4 - j))), BoardFactory.HIDE_TARGET_ORB);
                        } else {
                            factory.cascadeExistingOrb(nonMatchedOrbs.get(j), factory.orbList.get(i + (6 * (4 - j))), BoardFactory.EXISTING_ORB);
                        }
                    }
                }
            }

        }

        if (noCascade)
            continueSort();
    }

    /**
     * cascades new orbs row by row
     */
    public void replaceMatchedOrbs() {
        // Consider each row in the board
        for (int i = 0; i < factory.orbList.size(); i++) {
            OrbView orb = factory.orbList.get(i);
            if (orb.isMatched()) {
                factory.setRandomOrb(orb);
                orb.setMatched(false);
            }
        }

    }

    public void replaceOrbs()
    {
        int test = 0;
        for(ArrayList<Integer> x:comboList)
        {
            if(!x.isEmpty())
            {
                ArrayList<Integer> xV = comboListV.get(test);
                Collections.sort(x);
                for(int i = 0; i<x.size();i++)
                {
                    int location = x.get(i);
                    if(!xV.isEmpty())
                    {
                        for(int y = 0; y<xV.size();y++)
                        {
                            int verticalLocation = xV.get(y);
                            if(location==verticalLocation)
                            {
                                if(i<x.size()) {
                                    x.remove(i);
                                }
                            }
                        }
                    }
                }
                //horizontal animation
                Log.d(TAG, "horizontal combo size: " + x.size());
                for(int location:x)
                {
                    if(x.size()>=1) {
                        int id = test;
                        int curr = location;
                        int top = curr;
                        OrbView matchedOrb = factory.orbList.get(curr);
                        matchedOrb.setAlpha(0.0f);
                        while (top - 6 >= 0) {
                            top = top - 6;
                            OrbView newOrb = factory.orbList.get(curr);
                            OrbView topOrb = factory.orbList.get(top);
                            factory.cascadeExistingOrb(topOrb, newOrb, BoardFactory.EXISTING_ORB);
                            curr = curr - 6;
                        }
                        factory.cascadeNewOrb(top, BoardFactory.NEW_ORB);
                    }
                }
                //vertical animation
                if(!xV.isEmpty()) {
                    Collections.sort(xV);
                    ArrayList<Integer> horizontal = new ArrayList<Integer>();
                    for(int i:xV)
                    {
                        OrbView tmp = factory.orbList.get(i);
                        //tmp.setAlpha(0.0f);
                    }

                    int highest = xV.get(0);
                    int lowest = xV.get(xV.size() - 1);
                    horizontal.add(lowest);
                    if(xV.get(xV.size() - 2) != lowest - 6) {
                        horizontal.add(xV.get(xV.size() - 2));
                    }
                    while(!horizontal.isEmpty()) {
                        Log.d(TAG, "Lowest orb at: " + lowest + ", highest orb at: " + highest);
                        lowest = horizontal.get(0);
                        int check = lowest;
                        horizontal.remove(0);
                        while (highest - 6 >= 0) {
                            highest = highest - 6;
                            OrbView topOrb = factory.orbList.get(highest);
                            OrbView target = factory.orbList.get(lowest);
                            Log.d(TAG, "CascadeExisting call: top: " + highest + ", target: " + lowest);
                            factory.cascadeExistingOrb(topOrb, target, BoardFactory.EXISTING_ORB);
                            lowest = lowest - 6;
                        }
                        while (lowest - 6 >= 0) {
                            factory.cascadeNewOrb(lowest, BoardFactory.NEW_ORB);
                            Log.d(TAG, "CascadeNew call at orb: " + lowest);
                            lowest = lowest - 6;
                        }
                        Log.d(TAG, "Final cascadeNew call at:  " + lowest);
                        factory.cascadeNewOrb(lowest, BoardFactory.NEW_ORB);
                    }
                }
            }
            test++;
        }
    }

    public void resetLists()
    {
        threeList = new int[30];
        fill(threeList, -1);
        for(ArrayList<Integer> x:comboList) {
            x.clear();
        }
        for(ArrayList<Integer> xV:comboListV){
            xV.clear();
        }
    }

    public void reset()
    {
        done = false;
    }


}
