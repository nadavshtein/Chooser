package com.example.chooser.booms.groups;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.chooser.booms.BoomMenuPiece;
import com.example.chooser.modes.groups.GroupsMode;
import com.example.chooser.activity.MainActivity;
import com.example.chooser.MainBoardTouchListener;
import com.example.chooser.booms.MyBoomBox;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.List;

public class BoomGroupsButton extends MyBoomBox {
    public BoomGroupsButton(ConstraintLayout myLayout, BoomMenuButton bmb, List<BoomMenuPiece> piecesList) {
        super(myLayout, bmb, piecesList);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void changeGameMode(int fingersMode) {

        MainActivity.gameMode=new GroupsMode(myLayout,fingersMode);
        myLayout.setOnTouchListener(new MainBoardTouchListener(myLayout));

    }
}
