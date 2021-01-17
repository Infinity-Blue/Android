package com.example.trivia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bawp.trivia.controller.AppController;
import com.bawp.trivia.data.AnswerListAsyncResponse;
import com.bawp.trivia.model.Question;
import com.bawp.trivia.util.AppUtil;
import com.bawp.trivia.util.Prefs;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;


public class quizAct extends AppCompatActivity implements View.OnClickListener {
    final static int RESULT_CLOSE_ALL=0;
    int catIndex;
    int category;
    int correctNbr=0;
    private TextView q;
    private TextView counter;
    private TextView scoreView;
    private TextView highScore;
    private Button opt1;
    private Button opt2;
    private Button opt3;
    private Button opt4;
    private ImageButton prev;
    private ImageButton next;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private AdView mAdView;
    private Prefs pref;
    AppUtil appUtil;
    private ProgressBar progress;
    ConstraintLayout quizScreen;
    ArrayList<Question> questions = new ArrayList<Question>();
    ArrayList<Question> answerData= new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_quiz);

        //initialize mobile Ad format
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Bundle bundle = getIntent().getExtras();
        category = bundle.getInt("category");
        Log.d("categoryrec", String.valueOf(category));

        opt1 = findViewById(R.id.opt1);
        q = findViewById(R.id.question_textview);
        scoreView = findViewById(R.id.scoreView);
        highScore = findViewById(R.id.highScore);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        opt4 = findViewById(R.id.opt4);
        prev = findViewById(R.id.prev_button);
        next = findViewById(R.id.next_button);
        counter = findViewById(R.id.counter_text);
        quizScreen=(ConstraintLayout)findViewById(R.id.quizView);

        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        opt1.setOnClickListener(this);
        opt2.setOnClickListener(this);
        opt3.setOnClickListener(this);
        opt4.setOnClickListener(this);

        pref = new Prefs(quizAct.this);
        appUtil = new AppUtil(this);

        getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> question) {
                //Decode HTML entities
                q.setText(Html.fromHtml(questions.get(0).getQuestion()));
                opt1.setText(Html.fromHtml(question.get(0).getOpt1()));
                opt2.setText(Html.fromHtml(question.get(0).getOpt2()));
                opt3.setText(Html.fromHtml(question.get(0).getOpt3()));
                opt4.setText(Html.fromHtml(question.get(0).getOpt4()));
                counter.setText(currentQuestionIndex + " / " + questions.size());
                scoreView.setText(MessageFormat.format("Current score: {0}", String.valueOf(score))); //"current score : " + score + " / " +100
                highScore.setText(MessageFormat.format("Highest score: {0}", pref.getHighScore()));
            }
        });
    }
   
    
    public void onClick(View v) {
        int optnbr = 0;
        switch (v.getId()) {
            case R.id.next_button:
                if (currentQuestionIndex == 4) {
                    //   show finalScore();
                    Intent i= new Intent(quizAct.this, finalScore.class);
                    i.putExtra("score", score);
                    i.putExtra("correctNbr", correctNbr);
                    startActivity(i);
                }
                else {
                    currentQuestionIndex++;
                    updateQuestion();
                }
                break;
            case R.id.prev_button:
                if (currentQuestionIndex <= 0) {
                }
                currentQuestionIndex--;
                updateQuestion();
                break;
            case R.id.opt1:
                checkAnswer(questions.get(currentQuestionIndex).getOpt1(), 1);
                break;
            case R.id.opt2:
                checkAnswer(questions.get(currentQuestionIndex).getOpt2(), 2);
                break;
            case R.id.opt3:
                checkAnswer(questions.get(currentQuestionIndex).getOpt3(), 3);
                break;
            case R.id.opt4:
                checkAnswer(questions.get(currentQuestionIndex).getOpt4(), 4);
                break;
        }
    }

    /*
    @brief  Check whether the user presses the Correct answer(button) and update Score
    */
    private void checkAnswer(String optText, int optnbr) {
        String answer = answerData.get(currentQuestionIndex).getCorrectAnswer();
        switch (optnbr) {
            case 1:
            case 2:
            case 3:
            case 4:
                if (optText == answer) {
                    fadeView();
                    score = score + 500;
                    pref.saveHighestScore(score);
                    scoreView.setText(MessageFormat.format("Current score: {0}", String.valueOf(score)));
                    correctNbr++;
                    break;
                } else {
                    viewAnswer();
                    break;
                }
        }
    }

    /*
    @brief  Pass the Button where Answer is
    */
    private void viewAnswer() {
        int optAns = answerData.get(currentQuestionIndex).getAnsOpt();
        switch (optAns) {
            case 1:
                shakeAnimation(opt1);
                break;
            case 2:
                shakeAnimation(opt2);
                break;
            case 3:
                shakeAnimation(opt3);
                break;
            case 4:
                shakeAnimation(opt4);
                break;
        }
    }

    /*
    @breif  Cardview fades once the user presses Correct button.
    */
    private void fadeView() {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                if (currentQuestionIndex == 4) {
                    //   show finalScore();
                    Intent i = new Intent(quizAct.this, finalScore.class);
                    i.putExtra("score", score);
                    i.putExtra("correctNbr", correctNbr);
                    startActivity(i);
                }
                else {
                    currentQuestionIndex++;
                    updateQuestion();
                }
                }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /*
    @brief  Cardview and the Button pressed shake if the user presses wrong button.
    @param  the button the user pressed
     */
    private void shakeAnimation(Button ansButton) {
        Animation shake = AnimationUtils.loadAnimation(quizAct.this,
                R.anim.shake_animation);
        final CardView cardView= findViewById(R.id.cardView);
        final Button finalAnsButton = ansButton;
        cardView.startAnimation(shake);
       finalAnsButton.startAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
                finalAnsButton.setBackgroundColor(Color.RED);

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                finalAnsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                if (currentQuestionIndex == 4) {
                    //   show finalScore();
                    Intent i = new Intent(quizAct.this, finalScore.class);
                    i.putExtra("score", score);
                    i.putExtra("correctNbr", correctNbr);
                    startActivity(i);
                }
                else {
                    currentQuestionIndex++;
                    updateQuestion();
                }
                }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /*
    @brief  Show New questions and Answer options(Button) on the screen
     */
    private void updateQuestion() {
        q.setText(Html.fromHtml(questions.get(currentQuestionIndex).getQuestion()));
        counter.setText(currentQuestionIndex + " / " + questions.size());
        opt1.setText(Html.fromHtml(questions.get(currentQuestionIndex).getOpt1()));
        opt2.setText(Html.fromHtml(questions.get(currentQuestionIndex).getOpt2()));
        opt3.setText(Html.fromHtml(questions.get(currentQuestionIndex).getOpt3()));
        opt4.setText(Html.fromHtml(questions.get(currentQuestionIndex).getOpt4()));
    }

    /*
    @brief  Fetch Json data if the network is connected.
    @param  Interface callback to make sure fetching Json data is completed.
     */
    public void getQuestions(final AnswerListAsyncResponse callBack) {
        quizScreen.setVisibility(View.INVISIBLE);
        //check Network connection before fetching Json data
        if(appUtil.isNetworkConnectionAvailable()==false){
            netWorkAlertDialog();
        }

        else {
            String url = "https://opentdb.com/api.php?amount=5&category=" + category + "&type=multiple";
            final ProgressDialog dialog = ProgressDialog.show(this, null, "Loading..Please Wait");

            final JsonObjectRequest jsObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dialog.dismiss();
                    quizScreen.setVisibility(View.VISIBLE);
                    try {
                        parseResponse(response);
                        callBack.processFinished(questions);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(quizAct.this, "Loading... Please wait...", Toast.LENGTH_LONG).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(quizAct.this, "Loading...", Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    });

            AppController.getInstance().addToRequestQueue(jsObjectRequest);
        }
    }

    /*
    @brief  AlertDialog where the User choose whether to retry connection or Exit app.
    */
    public void netWorkAlertDialog(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Network error occur: Retry or Close?");
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("onclick", "onclick starts");
                dialog.dismiss();
                //Reload activity
                finish();
                startActivity(getIntent());
            }
        }).show();

        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                setResult(RESULT_CLOSE_ALL);
                finish();
            }
        }).show();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /*
    @brief  Parse fetched Json data
    @param  Fetched Json data
    */
    public void parseResponse(JSONObject response) throws JSONException {
        int code = Integer.parseInt(response.getString("response_code"));
        if (code == 0) {
            JSONArray jsonData = response.getJSONArray("results");
            int length = jsonData.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObj = jsonData.getJSONObject(i);
                String question = jsonObj.getString("question");
                String correctAns = jsonObj.getString("correct_answer");
                //getting inner array ingredients
                JSONArray jsonInans = jsonObj.getJSONArray("incorrect_answers");
                String opt1 = jsonInans.getString(0);
                String opt2 = jsonInans.getString(1);
                String opt3 = jsonInans.getString(2);

                Random randNbr = new Random();

                int nbr = randNbr.nextInt(4) + 1;
                switch (nbr) {
                    case 1:
                        questions.add(new Question(question, opt1, opt2, opt3, correctAns));
                        answerData.add(new Question(4,correctAns));
                        break;
                    case 2:
                        questions.add(new Question(question, correctAns, opt2, opt1, opt3));
                        answerData.add(new Question(1,correctAns));
                        break;
                    case 3:
                        questions.add(new Question(question, opt1, opt3, correctAns, opt2));
                        answerData.add(new Question(3,correctAns));
                        break;
                    case 4:
                        questions.add(new Question(question, opt3, correctAns, opt2, opt1));
                        answerData.add(new Question(2,correctAns));
                        break;
                }
            }
        }
    }

    @Override
    protected void onPause() {
        pref.getHighScore();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Intent new_intent = new Intent(this, categoryPick.class);
        new_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(new_intent);
    }
}
