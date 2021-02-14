package com.example.drikkelek;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DrinkingGame extends AppCompatActivity {
    private Button btnContinue;
    private Button btnNextQuestion;
    private Button btnAddPlayer;
    private TableLayout tablePlayers;
    private ConstraintLayout addPlayersLayout;
    private ConstraintLayout closeKeyboardLayout;
    private ConstraintLayout drinkingGameLayout;
    private TextView type;
    private TextView content;
    private TextView error;
    private int counter = 0;
    private int playerCounter = 2;
    private boolean enoughPlayers = true;
    private Question[] questions = new Question[0];
    private Question[] questionsTemp;
    private String[] playerNames = new String[0];
    private InputStream[] categories;
    private EditText[] playerViews;
    String randomPlayer;
    String randomPlayer2;

    EditText player1;
    EditText player2;
    EditText player3;
    EditText player4;
    EditText player5;
    EditText player6;
    EditText player7;
    EditText player8;
    EditText player9;
    EditText player10;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drinking_game_choose_players);
        btnAddPlayer = findViewById(R.id.btn_add_player);
        btnContinue = findViewById(R.id.btn_drinking_game_continue);
        tablePlayers = findViewById(R.id.table_players);
        error = findViewById(R.id.add_players_error);
        addPlayersLayout = findViewById(R.id.add_players_layout);
        closeKeyboardLayout = findViewById(R.id.close_keyboard);

         player1 = findViewById(R.id.player_name_1);
         player2 = findViewById(R.id.player_name_2);
         player3 = findViewById(R.id.player_name_3);
         player4 = findViewById(R.id.player_name_4);
         player5 = findViewById(R.id.player_name_5);
         player6 = findViewById(R.id.player_name_6);
         player7 = findViewById(R.id.player_name_7);
         player8 = findViewById(R.id.player_name_8);
         player9 = findViewById(R.id.player_name_9);
         player10 = findViewById(R.id.player_name_10);

        playerViews = new EditText[]{
                player1,
                player2,
                player3,
                player4,
                player5,
                player6,
                player7,
                player8,
                player9,
                player10
        };


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                setUpPlayers();
                if (!enoughPlayers || playerCounter < 2) {
                    playerCounter = 2;
                    return;
                }
                createQuestions();

                setContentView(R.layout.drinking_game);
                type =  findViewById(R.id.type_view);
                content = findViewById((R.id.content_view));
                drinkingGameLayout = findViewById(R.id.drinking_game_constraint_layout);

                //Randomizes question order
                Random rnd = ThreadLocalRandom.current();
                for (int i = questions.length - 1; i > 0; i--) {
                    int index = rnd.nextInt(i + 1);
                    Question a = questions[index];
                    questions[index] = questions[i];
                    questions[i] = a;
                }
                setUpNextButton();
                newQuestion();
            }
        });

        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerCounter < 10) {
                    playerViews[playerCounter].setVisibility(View.VISIBLE);
                    playerViews[playerCounter - 1].setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    playerViews[playerCounter].setImeOptions(EditorInfo.IME_ACTION_DONE);
                    playerViews[playerCounter].requestFocus();
                    playerCounter++;
                    openKeyboard();
                }
            }
        });


        closeKeyboardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });


    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void openKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }
    


    private void newQuestion() {
        if (counter < questions.length) {
            randomPlayer = playerNames[ThreadLocalRandom.current().nextInt(0, playerCounter)];
            randomPlayer2 = playerNames[ThreadLocalRandom.current().nextInt(0, playerCounter)];

            while (randomPlayer == randomPlayer2) {
                randomPlayer2 = playerNames[ThreadLocalRandom.current().nextInt(0, playerCounter)];
            }



            drinkingGameLayout.setBackgroundColor(Color.parseColor(questions[counter].getColor()));
            type.setText(questions[counter].getTitle());
            String questionContent = questions[counter].getContent();
            if (questionContent.contains("spiller1")) {
                questionContent = questionContent.replace("spiller1" , randomPlayer);
            }
            if (questionContent.contains("spiller2")) {
                questionContent = questionContent.replace("spiller2" , randomPlayer2);
            }

            content.setText(questionContent);
            counter++;
        } else {
            finish();
        }
    }



    private void setUpNextButton() {
        btnNextQuestion = findViewById(R.id.btn_next_question);
        btnNextQuestion.setOnClickListener(v -> {
            newQuestion();
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createQuestions() {
        Question question;

        InputStream rule = getResources().openRawResource(R.raw.rule);
        InputStream thumbs = getResources().openRawResource(R.raw.thumbs_up_or_down);
        InputStream point = getResources().openRawResource(R.raw.point);
        InputStream normal = getResources().openRawResource(R.raw.normal);
        InputStream category = getResources().openRawResource(R.raw.category);

        InputStream is = getResources().openRawResource(R.raw.rule);

        categories = new InputStream[]{rule, thumbs, point, normal, category};


        String line = "";

        for (InputStream file : categories) {

            BufferedReader reader = new BufferedReader (
                    new InputStreamReader(file, Charset.defaultCharset())
            );

            try {
                while ((line = reader.readLine()) != null) {
                    String[] elements = line.split(",");
                    String type = elements[0];
                    String title = elements[1];
                    String content = elements[2];
                    String help;

                    int length = questions.length;
                    questionsTemp = questions;
                    questions = new Question[questionsTemp.length + 1];
                    for (int i = 0; i < questions.length; i++) {
                        if (i == questionsTemp.length) {
                            questions[i] = new Question(type, title, content);
                        }
                        else {
                            questions[i] = questionsTemp[i];
                        }
                    }

                }
            } catch (IOException e) {
                Log.wtf("DrinkingGame", "Error reading data file", e);
                e.printStackTrace();
            }
        }

    }

    private void reset() {
        setContentView(R.layout.drinking_game_choose_players);
        counter = 0;
        playerCounter = 2;
        questions = new Question[0];
    }

    private void setUpPlayers() {
        String[] playersTemp = {
            player1.getText().toString().trim(),
            player2.getText().toString().trim(),
            player3.getText().toString().trim(),
            player4.getText().toString().trim(),
            player5.getText().toString().trim(),
            player6.getText().toString().trim(),
            player7.getText().toString().trim(),
            player8.getText().toString().trim(),
            player9.getText().toString().trim(),
            player10.getText().toString().trim()
            };

        playerCounter = 0;

        error.setText("");

        int nameCounter;
        for (String name : playersTemp) {
            nameCounter = 0;
            for (String name2 : playersTemp) {
                if (name.equals(name2) && name.length() != 0) {
                    nameCounter++;
                    if (nameCounter == 2) {
                        error.setText("Flere spillere kan ikke hete det samme");
                        playerCounter = 2;
                        enoughPlayers = false;
                        return;
                    }
                }
            }
            if (!(name.isEmpty())) {
                playerCounter++;
            }
        }
        playerNames = new String[playerCounter];
        System.out.println("playerCounter:" + playerCounter);
        for (int i = 0; i < playerCounter; i++) {
            playerNames[i] = playersTemp[i];
        }


        enoughPlayers = true;
    }
}