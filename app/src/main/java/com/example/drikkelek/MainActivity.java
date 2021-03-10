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
        packList.add(new Pack("Blanding", "DrinkingGame", new String[]{"category", "normal", "point", "rule", "thumbs_up_or_down"}));
        packList.add(new Pack("100 Spørsmål", "DrinkingGame", new String[]{"normal", "thumbs_up_or_down"}));
        packList.add(new Pack("Pekelek", "DrinkingGame", new String[]{"point"}));
        packList.add(new Pack("Terning", "Dice", new String[0]));
    }

    private void openDrinkingGame() {
        Intent intent = new Intent(this, DrinkingGameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPackClick(int position) {
        //Checks which activity to open
        String type = packList.get(position).getActivityType();

        if (type.equals("DrinkingGame")) {
            Intent intent = new Intent(this, DrinkingGameActivity.class);
            startActivity(intent);
        }

        if (type.equals("Dice")) {
            Intent intent = new Intent(this, DiceActivity.class);
            startActivity(intent);
        }
    }
}