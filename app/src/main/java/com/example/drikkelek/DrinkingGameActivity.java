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
    private Button btnCloseGame;
    private Button btnHelp;
    private ConstraintLayout managePlayersLayout;
    private ConstraintLayout helpLayout;
    private TableLayout tablePlayers;
    private ConstraintLayout addPlayersLayout;
    private ConstraintLayout closeKeyboardLayout;
    private ConstraintLayout drinkingGameLayout;
    private ConstraintLayout ruleLayout;
    private TextView type;
    private TextView content;
    private TextView error;
    private String gamemode = "Get drunk";
    private static int questionCounter = 0; //What question you are on in the list of questions
    private int gameCounter = 0; //What question you are in the particular game

    private boolean menuShown = false;
    private static ArrayList<Question> questions = new ArrayList<>();
    private static ArrayList<Player> playerNames = new ArrayList<>();
    private InputStream[] categories;
    private EditText[] playerViews;
    String randomPlayer;
    String randomPlayer2;

    private RecyclerView recyclerView;
    private PlayerRecyclerAdapter adapter;
    private ArrayList<Rule> rules = new ArrayList<>();
    private RecyclerView ruleRecyclerView;
    private RuleRecyclerAdapter ruleAdapter;


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

                drinkingGameOnStart();
            }
        });

        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerNames.add(new Player());
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
    
    public void drinkingGameOnStart() {
        //Sets up new layout and variables
        playerNames = adapter.getPlayerNames();

        setContentView(R.layout.drinking_game);
        type =  findViewById(R.id.type_view);
        content = findViewById((R.id.content_view));
        drinkingGameLayout = findViewById(R.id.drinking_game_constraint_layout);
        helpLayout = findViewById(R.id.help_layout);
        btnAddPlayer = findViewById(R.id.btn_add_player);
        btnManagePlayers = findViewById(R.id.btn_manage_players);
        btnCloseGame = findViewById(R.id.btn_close_game);
        btnHelp = findViewById(R.id.btn_help);
        adapter = new PlayerRecyclerAdapter(playerNames);
        recyclerView = findViewById(R.id.recycler_view_players);
        managePlayersLayout = findViewById(R.id.manage_players_layout);
        ruleLayout = findViewById(R.id.rules_layout);
        setAdapter();
        createQuestions();
        setUpNextButton();

        ruleRecyclerView = findViewById(R.id.rules_recycler_view);
        ruleAdapter = new RuleRecyclerAdapter(rules);
        setRuleAdapter();

        btnManagePlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuShown) {
                    menuShown = false;
                    managePlayersLayout.setVisibility(View.INVISIBLE);
                    helpLayout.setVisibility(View.INVISIBLE);
                    btnCloseGame.setVisibility(View.VISIBLE);
                    btnHelp.setVisibility(View.VISIBLE);
                    btnManagePlayers.setText((getString(R.string.manage_players_btn)));
                    btnManagePlayers.setWidth(80);
                    btnManagePlayers.setHeight(80);
                    closeKeyboard(DrinkingGameActivity.this);
                }
                else {
                    menuShown = true;
                    managePlayersLayout.setVisibility(View.VISIBLE);
                    btnCloseGame.setVisibility(View.INVISIBLE);
                    btnHelp.setVisibility(View.INVISIBLE);
                    btnManagePlayers.setText("╳");
                    btnManagePlayers.bringToFront();
                    btnManagePlayers.setWidth(40);
                    btnManagePlayers.setHeight(40);
                }
            }
        });
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerNames.add(new Player());
                setAdapter();
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuShown = true;
                helpLayout.setVisibility(View.VISIBLE);
                btnCloseGame.setVisibility(View.INVISIBLE);
                btnHelp.setVisibility(View.INVISIBLE);
                btnManagePlayers.setText("╳");
                btnManagePlayers.setWidth(40);
                btnManagePlayers.setHeight(40);

            }
        });
        btnCloseGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameCounter = 0;
                finish();
            }
        });
        managePlayersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(DrinkingGameActivity.this);
            }
        });

        nextQuestion();
    }

    public void setRuleAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        ruleRecyclerView.setLayoutManager(layoutManager);
        ruleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ruleRecyclerView.setAdapter(ruleAdapter);
        rules = ruleAdapter.getRules();
    }

    public void setAdapter() {
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
    


    private void nextQuestion() {
        int x = 0;
        while (playerNames.size() < 2) {
            String name = getResources().getString(R.string.empty_name) + " " + x;
            playerNames.add(new Player(name));
            setAdapter();
        }

        if (questionCounter < questions.size() && gameCounter < gameLength) {
            randomPlayer = playerNames.get(ThreadLocalRandom.current().nextInt(0, playerNames.size())).getName();
            randomPlayer2 = playerNames.get(ThreadLocalRandom.current().nextInt(0, playerNames.size())).getName();

            while (randomPlayer.equals(randomPlayer2)) {
                randomPlayer2 = playerNames.get(ThreadLocalRandom.current().nextInt(0, playerNames.size())).getName();
            }

            if (questions.get(questionCounter).isReturnRule()) {
                rules.remove(questions.get(questionCounter).getRule());
            } else if (questions.get(questionCounter).hasRule()) {
                rules.add(questions.get(questionCounter).getRule());
                setRuleAdapter();
            }
            if (rules.size() == 0) {
                ruleLayout.setVisibility(View.INVISIBLE);
            } else {
                ruleLayout.setVisibility(View.VISIBLE);
            }

            drinkingGameLayout.setBackgroundColor(Color.parseColor(questions.get(questionCounter).getColor()));
            type.setText(questions.get(questionCounter).getTitle());
            String questionContent = questions.get(questionCounter).getContent();
            if (questionContent.contains("spiller1")) {
                questionContent = questionContent.replace("spiller1" , randomPlayer);
            }
            if (questionContent.contains("spiller2")) {
                questionContent = questionContent.replace("spiller2" , randomPlayer2);
            }

            content.setText(questionContent);
            questionCounter++;
            gameCounter++;
        } else {
            gameCounter = 0;
            finish();
        }
    }



    private void setUpNextButton() {
        btnNextQuestion = findViewById(R.id.btn_next_question);
        btnNextQuestion.setOnClickListener(v -> {
            if (menuShown) {
                closeKeyboard(this);
                menuShown = false;
                managePlayersLayout.setVisibility(View.INVISIBLE);
                helpLayout.setVisibility(View.INVISIBLE);
                btnCloseGame.setVisibility(View.VISIBLE);
                btnHelp.setVisibility(View.VISIBLE);
                btnManagePlayers.setText((getString(R.string.manage_players_btn)));
                btnManagePlayers.setWidth(80);
                btnManagePlayers.setHeight(80);
            }
            else {
                nextQuestion();
            }

        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createQuestions() {

        if ((questionCounter + gameLength) > questions.size() || questions.size() == 0) {
            questions = new ArrayList<Question>();
            questionCounter = 0;
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
                        String ruleDisplay = null;
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
                            if (type.equals("Rule")) {
                                newQuestion.setRule(new Rule(ruleDisplay));
                                newQuestion.setHasRule(true);
                            }
                        }

                        if (newQuestion.getGameMode().equals(gamemode)) {
                            questions.add(newQuestion);
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
                    Question returnRule = new Question(
                            questions.get(i).getGameMode(),
                            questions.get(i).getType(),
                            questions.get(i).getReturnTitle(),
                            questions.get(i).getReturnContent());
                    returnRule.setIsReturnRule(questions.get(i).getRule());
                    questions.add(i + questions.get(i).getReturnTime(), returnRule);
                }
                else if (questions.get(i).ifHasReturn()) {
                    if (i + 3 < questions.size()) {
                        Question returnRule = new Question(
                                questions.get(i).getGameMode(),
                                questions.get(i).getType(),
                                questions.get(i).getReturnTitle(),
                                questions.get(i).getReturnContent());
                        returnRule.setIsReturnRule(questions.get(i).getRule());
                        gameLength += 2;
                        questions.set(i + 2, returnRule);
                    }
                }
            }
        }
    }

    private void reset() {
        setContentView(R.layout.drinking_game_choose_players);
        gameCounter = 0;
        questions.clear();
    }
}