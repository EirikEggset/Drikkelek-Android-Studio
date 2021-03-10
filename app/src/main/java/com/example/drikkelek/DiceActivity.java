package com.example.drikkelek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ThreadLocalRandom;

public class DiceActivity extends AppCompatActivity {

    private Button diceBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        diceBtn = (Button) findViewById(R.id.dice_btn);

        diceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNumber = ThreadLocalRandom.current().nextInt(1, 7);
                diceBtn.setText(randomNumber + "");
            }
        });

    }
}