package com.example.drikkelek;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PackRecyclerAdapter.OnPackListener {
    private Button btnDrinkingGame;
    private ArrayList<Pack> packList;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_menu);
        recyclerView = findViewById(R.id.recycler_view_packs);
        packList = new ArrayList<>();

        setUserInfo();
        setAdapter();

    }

    private void setAdapter() {
        PackRecyclerAdapter adapter = new PackRecyclerAdapter(packList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setUserInfo() {
        packList.add(new Pack("Blanding", "DrinkingGame"));
        packList.add(new Pack("100 Spørsmål", "100Questions"));
        packList.add(new Pack("Ring of Fire", "Ring of Fire"));
        packList.add(new Pack("Terning", "Dice"));
    }


    @Override
    public void onPackClick(int position) {
        //Checks which activity to open
        String type = packList.get(position).getActivityType();

        switch (type) {
            case "DrinkingGame": {
                Intent intent = new Intent(this, DrinkingGameActivity.class);
                intent.putExtra("gameType", "DrinkingGame");
                startActivity(intent);
                break;
            }
            case "100Questions": {
                Intent intent = new Intent(this, DrinkingGameActivity.class);
                intent.putExtra("gameType", "100Questions");
                startActivity(intent);
                break;
            }
            case "Ring of Fire": {

                break;
            }
            case "Dice": {
                Intent intent = new Intent(this, DiceActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}