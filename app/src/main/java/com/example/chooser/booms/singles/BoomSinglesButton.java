package com.example.chooser.booms.singles;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.chooser.booms.BoomMenuPiece;
import com.example.chooser.activity.MainActivity;
import com.example.chooser.MainBoardTouchListener;
import com.example.chooser.modes.singles.SinglesMode;
import com.example.chooser.booms.MyBoomBox;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.List;

public class BoomSinglesButton extends MyBoomBox {

    public BoomSinglesButton(ConstraintLayout myLayout, BoomMenuButton bmb, List<BoomMenuPiece> piecesList) {
        super(myLayout, bmb, piecesList);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void changeGameMode(int fingersMode) {

        // reset MainBoard and game mode
        MainActivity.gameMode=new SinglesMode(myLayout, fingersMode);
        myLayout.setOnTouchListener(new MainBoardTouchListener(myLayout));
    }
}
