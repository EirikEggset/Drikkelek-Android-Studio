package com.example.drikkelek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private Button btnDrinkingGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.main_menu);


        btnDrinkingGame = findViewById((R.id.btn_drinking_game));

        btnDrinkingGame.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openDrinkingGame();
            }
        });

    }

    private void openDrinkingGame() {
        Intent intent = new Intent(this, DrinkingGame.class);
        startActivity(intent);
    }
}