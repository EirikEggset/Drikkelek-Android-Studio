package com.example.drikkelek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ThreadLocalRandom;

public class DiceActivity extends AppCompatActivity {

    private DiceGLSurfaceView diceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        diceView = new DiceGLSurfaceView(this);
        setContentView(diceView);



    }
}