package com.example.drikkelek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class RingOfFireActivity extends AppCompatActivity {

    ImageView cardView;
    int kingCounter = 0;
    TextView textView;
    Button closeGame;

    ArrayList<Card> cards = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_of_fire);
        cardView = findViewById(R.id.card_view);
        textView = findViewById(R.id.kings_cup_text);
        closeGame = findViewById(R.id.btn_close_game_kk);

        cards.add(new Card("2", R.drawable.card_diamonds_2, getResources().getString(R.string.card_2)));
        cards.add(new Card("3", R.drawable.card_diamonds_3, getResources().getString(R.string.card_3)));
        cards.add(new Card("4", R.drawable.card_diamonds_4, getResources().getString(R.string.card_4)));
        cards.add(new Card("5", R.drawable.card_diamonds_5, getResources().getString(R.string.card_5)));
        cards.add(new Card("6", R.drawable.card_diamonds_6, getResources().getString(R.string.card_6)));
        cards.add(new Card("7", R.drawable.card_diamonds_7, getResources().getString(R.string.card_7)));
        cards.add(new Card("8", R.drawable.card_diamonds_8, getResources().getString(R.string.card_8)));
        cards.add(new Card("9", R.drawable.card_diamonds_9, getResources().getString(R.string.card_9)));
        cards.add(new Card("10", R.drawable.card_diamonds_10, getResources().getString(R.string.card_10)));
        cards.add(new Card("J", R.drawable.card_diamonds_j, getResources().getString(R.string.card_j)));
        cards.add(new Card("Q", R.drawable.card_diamonds_q, getResources().getString(R.string.card_q)));
        cards.add(new Card("K", R.drawable.card_diamonds_k, getResources().getString(R.string.card_king)));
        cards.add(new Card("A", R.drawable.card_diamonds_a, getResources().getString(R.string.card_a)));



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cards.size() > 0 && kingCounter < 4) {
                    Collections.shuffle(cards);
                    System.out.println("Trukket en " + cards.get(0).getType());
                    if (cards.get(0).getType().equals("K")) {
                        kingCounter++;
                        cardView.setImageDrawable(getResources().getDrawable(cards.get(0).getDrawable()));
                        if (kingCounter == 4) {
                            textView.setText(getResources().getString(R.string.card_king_last));
                            return;
                        }
                    }
                    textView.setText(cards.get(0).getText());
                    cardView.setImageDrawable(getResources().getDrawable(cards.get(0).getDrawable()));
                    cards.remove(0);

                }
                else {
                    textView.setText("Ferdig");
                }
                cardView.setEnabled(false);
                cardView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardView.setEnabled(true);
                    }
                }, 200);
            }
        });

        closeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}