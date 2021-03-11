package com.example.drikkelek;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class DrinkingGameActivity extends AppCompatActivity {

    //Config
    private int gameLength = 40; //How many questions per game


    private Button btnContinue;
    private Button btnNextQuestion;
    private Button btnAddPlayer;
    private Button btnRemovePlayer;
    private Button btnWarmUp;
    private Button btnGetDrunk;
    private Button btnHeated;
    private Button btnManagePlayers;
    private ConstraintLayout managePlayersLayout;
    private TableLayout tablePlayers;
    private ConstraintLayout addPlayersLayout;
    private ConstraintLayout closeKeyboardLayout;
    private ConstraintLayout drinkingGameLayout;
    private TextView type;
    private TextView content;
    private TextView error;
    private String gamemode = "Get drunk";
    private int counter = 0;
    private boolean managePlayersVisible = false;
    private ArrayList<Question> questions = new ArrayList<>();
    private static ArrayList<Player> playerNames = new ArrayList<>();
    private InputStream[] categories;
    private EditText[] playerViews;
    String randomPlayer;
    String randomPlayer2;

    private RecyclerView recyclerView;
    PlayerRecyclerAdapter adapter;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.drinking_game_choose_players);
        btnAddPlayer = findViewById(R.id.btn_add_player);
        btnRemovePlayer = (Button) findViewById(R.id.btn_manage_players);
        btnContinue = findViewById(R.id.btn_drinking_game_continue);
        /*tablePlayers = findViewById(R.id.table_players);*/
        error = findViewById(R.id.add_players_error);
        addPlayersLayout = findViewById(R.id.add_players_layout);
        closeKeyboardLayout = findViewById(R.id.close_keyboard);
        btnWarmUp = findViewById(R.id.warm_up);
        btnGetDrunk = findViewById(R.id.get_drunk);
        btnHeated = findViewById(R.id.heated);

        //Player recyclerView
        recyclerView = findViewById(R.id.recycler_view_players);
        adapter = new PlayerRecyclerAdapter(playerNames);
        setPlayerInfo();
        setAdapter();


        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(DrinkingGameActivity.this);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                int counter = 0;
                for (int i = 0; i < playerNames.size(); i++) {
                    if(!(playerNames.get(i).getName().equals(""))) {
                        counter++;
                    }
                }
                if (counter < 2) {
                    error.setText("Legg til flere spillere");
                    return;
                }

                //Sets up new layout and variables
                playerNames = adapter.getPlayerNames();

                setContentView(R.layout.drinking_game);
                type =  findViewById(R.id.type_view);
                content = findViewById((R.id.content_view));
                drinkingGameLayout = findViewById(R.id.drinking_game_constraint_layout);
                btnManagePlayers = findViewById(R.id.btn_manage_players);

                adapter = new PlayerRecyclerAdapter(playerNames);
                recyclerView = findViewById(R.id.recycler_view_players);
                managePlayersLayout = findViewById(R.id.manage_players_layout);
                setAdapter();
                createQuestions();
                setUpNextButton();
                newQuestion();

                btnManagePlayers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (managePlayersVisible) {
                            managePlayersVisible = false;
                            managePlayersLayout.setVisibility(View.INVISIBLE);
                            btnManagePlayers.setText((getString(R.string.manage_players_btn)));
                            closeKeyboard(DrinkingGameActivity.this);
                        }
                        else {
                            managePlayersVisible = true;
                            managePlayersLayout.setVisibility(View.VISIBLE);
                            btnManagePlayers.setText("╳");
                        }
                    }
                });
                managePlayersLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeKeyboard(DrinkingGameActivity.this);
                    }
                });
            }
        });

        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerNames.add(new Player());
                setAdapter();
            }
        });

        btnRemovePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerNames.size() <= 2) {
                    return;
                }
                for (int i = 0; i < playerNames.size(); i++) {
                    if (playerNames.get(i).getName().equals("")) {
                        playerNames.remove(i);
                        setAdapter();
                        return;
                    }
                }
                playerNames.remove(playerNames.size()-1);
                setAdapter();
            }
        });

        btnWarmUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayersLayout.setBackgroundColor(getResources().getColor(R.color.green));
                gamemode = "Warm up";
            }
        });

        btnGetDrunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayersLayout.setBackgroundColor(getResources().getColor(R.color.secondary_blue));
                gamemode = "Get drunk";
            }
        });

        btnHeated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayersLayout.setBackgroundColor(getResources().getColor(R.color.red));
                gamemode = "Heated";
            }
        });


        closeKeyboardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(DrinkingGameActivity.this);
            }
        });


    }

    private void setAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setPlayerInfo() {
        if (playerNames.size() == 0) {
            playerNames.add(new Player());
            playerNames.add(new Player());
        }
    }

    private void closeKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void openKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }
    


    private void newQuestion() {
        if (counter < questions.size() && counter < gameLength) {
            randomPlayer = playerNames.get(ThreadLocalRandom.current().nextInt(0, playerNames.size())).getName();
            randomPlayer2 = playerNames.get(ThreadLocalRandom.current().nextInt(0, playerNames.size())).getName();

            while (randomPlayer.equals(randomPlayer2)) {
                randomPlayer2 = playerNames.get(ThreadLocalRandom.current().nextInt(0, playerNames.size())).getName();
            }


            drinkingGameLayout.setBackgroundColor(Color.parseColor(questions.get(counter).getColor()));
            type.setText(questions.get(counter).getTitle());
            String questionContent = questions.get(counter).getContent();
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
            if (managePlayersVisible) {
                managePlayersVisible = false;
                managePlayersLayout.setVisibility(View.INVISIBLE);
                btnManagePlayers.setText((getString(R.string.manage_players_btn)));
            }
            else {
                newQuestion();
            }
            closeKeyboard(this);
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

            //Creates arrayList with questions
            try {
                while ((line = reader.readLine()) != null) {
                    String[] elements = line.split(",");
                    String questionGameMode = elements[0];
                    String type = elements[1];
                    String title = elements[2];
                    String content = elements[3];
                    String returnTitle = null;
                    String returnContent = null;
                    String ruleDisplay;
                    int returnTime = 0;
                    boolean hasReturn = false;

                    //Checks for return
                    if(elements.length >= 7) {
                        returnTitle = elements[4];
                        returnContent = elements[5];
                        returnTime= Integer.parseInt(elements[6]);
                        hasReturn = true;
                        if (type.equals("Rule")) {
                            ruleDisplay = elements[7];
                        }
                    }

                    Question newQuestion = new Question(questionGameMode, type, title, content);
                    if (hasReturn) {
                        newQuestion.setReturn(returnTitle, returnContent, returnTime);
                    }

                    if (newQuestion.getGameMode().equals(gamemode)) {
                        questions.add(newQuestion);
                        System.out.println("Question added");
                    }

                }
            } catch (IOException e) {
                Log.wtf("DrinkingGame", "Error reading data file", e);
                e.printStackTrace();
            }
        }

        //Randomize order
        Collections.shuffle(questions);

        //Sets return questions
        for (int i = 0; i < questions.size(); i++) {
            if(questions.get(i).ifHasReturn() && i + questions.get(i).getReturnTime() < questions.size()) {
                questions.set(i + questions.get(i).getReturnTime(), new Question(
                        questions.get(i).getGameMode(),
                        questions.get(i).getType(),
                        questions.get(i).getReturnTitle(),
                        questions.get(i).getReturnContent()));
            }
            else if (questions.get(i).ifHasReturn()) {
                if (i + 3 < questions.size()) {
                    gameLength += 2;
                    questions.set(i + 2, new Question(
                            questions.get(i).getGameMode(),
                            questions.get(i).getType(),
                            questions.get(i).getReturnTitle(),
                            questions.get(i).getReturnContent()));
                }
            }
        }


    }

    private void reset() {
        setContentView(R.layout.drinking_game_choose_players);
        counter = 0;
        questions.clear();
    }
}