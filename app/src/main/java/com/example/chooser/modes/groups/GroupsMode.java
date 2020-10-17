package com.example.chooser.modes.groups;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.TransitionManager;

import com.example.chooser.MainBoardTouchListener;
import com.example.chooser.R;
import com.example.chooser.modes.GameMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.chooser.activity.MainActivity.gameMode;

public class GroupsMode extends GameMode {

    private Drawable groupsVector;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GroupsMode(ConstraintLayout layout, int groupsNum) {

        super(layout, groupsNum);
        initializeDrawableList();
        groupsVector = myLayout.getContext().getDrawable(R.drawable.groups_vector);

    }

    @Override
    public void onUp(int pointerId) { // lift finger from screen

        TransitionManager.beginDelayedTransition((ViewGroup) myLayout, animationSet);
        imageViewsByPointers.get(pointerId).setVisibility(View.GONE);
        imageViewsByPointers.remove(pointerId);
    }


    @Override
    public void endScene() {

        List<Integer> peoplePerGroup = new ArrayList<>(); // peoplePerGroup.get(i)=j implies there are j people in group i
        int groupsNum = modeNumber;
        int fingersNum = imageViewsByPointers.size();
        int numInGroup;
        for (int i = 0; i < modeNumber; i++) {

            numInGroup = fingersNum / groupsNum;
            peoplePerGroup.add(numInGroup);
            fingersNum -= numInGroup;
            groupsNum--;
        }

        createGroups(peoplePerGroup);
    }

    private void createGroups(List<Integer> peoplePerGroup) {

        List<ImageView> imageViewList = new ArrayList<>();
        imageViewList.addAll(imageViewsByPointers.values());
        int randomIndex;
        ImageView chosenImageView;
        int groupsNum = peoplePerGroup.size();

        for (int i = 0; i < groupsNum; i++) {

            for (int j = 0; j < peoplePerGroup.get(i); j++) { // number of people in group i

                randomIndex = random.nextInt(imageViewList.size());
                chosenImageView = imageViewList.get(randomIndex);
                imageViewList.remove(randomIndex);
                chosenImageView.setImageDrawable(drawableList.get(i));
            }
        }
    }


    @Override
    protected void colorsControl(ImageView iv) {

        iv.setImageDrawable(groupsVector);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void cancelAndReset() {

        for (int pointerId : imageViewsByPointers.keySet()) { //concealing views

            TransitionManager.beginDelayedTransition((ViewGroup) myLayout, animationSet);
            imageViewsByPointers.get(pointerId).setVisibility(View.GONE);
        }

        gameMode = new GroupsMode(myLayout, modeNumber);
        myLayout.setOnTouchListener(new MainBoardTouchListener(myLayout));
    }


}
