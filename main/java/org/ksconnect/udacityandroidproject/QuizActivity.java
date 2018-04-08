package org.ksconnect.udacityandroidproject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    LinearLayout optionsLayout;
    Typeface optionFontStyleTypeface ;
    String jsonFileName = "questions.json";
    ArrayList<QuestionData> questions = new ArrayList<>();
    LinearLayout layoutOne,layoutTwo,layoutThree;
    RelativeLayout quizStatus;
    Button getStarted,quitBtn,nextBtn,replayBtn;
    TextView questionText,resultMessage,qNoText,scoreText;
    int currentQuestion = -1;
    int currentScore = 0;
    boolean userAnswers[] = new boolean[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        optionFontStyleTypeface = Typeface.createFromAsset(getAssets(), "Nunito-Regular.ttf");
        initializeUIElements();
        parseJSONFile();
    }

    private void initializeUIElements() {
        optionsLayout = findViewById(R.id.layout_optionsLayout_quizActivity);
        layoutOne = findViewById(R.id.layout_layoutOne_quizActivity);
        layoutTwo = findViewById(R.id.layout_layoutTwo_quizActivity);
        layoutThree = findViewById(R.id.layout_layoutThree_quizActivity);
        quizStatus = findViewById(R.id.layout_quizStatus_quizActivity);
        questionText = findViewById(R.id.txt_questionText_quizActivity);
        resultMessage = findViewById(R.id.txt_resultMessage_quizActivity);
        qNoText = findViewById(R.id.txt_qnoText_quizActivity);
        scoreText = findViewById(R.id.txt_scoreText_quizActivity);

        getStarted = findViewById(R.id.btn_getStarted_quizActivity);
        quitBtn = findViewById(R.id.btn_quitBtn_quizActivity);
        nextBtn = findViewById(R.id.btn_nextBtn_quizActivity);
        replayBtn = findViewById(R.id.btn_replayBtn_quizActivity);

        getStarted.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
        replayBtn.setOnClickListener(this);
    }

    private void parseJSONFile(){
        try {
            InputStream is = getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonFile = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(jsonFile);
            questions.clear();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject JO = (JSONObject) jsonArray.get(i);
                JSONArray optionsJsonArray = JO.getJSONArray("options");
                String optionsArray[] = new String[optionsJsonArray.length()];
                for(int j=0;j<optionsJsonArray.length();j++){
                    optionsArray[j] = optionsJsonArray.getString(j);
                }

                JSONArray answersJsonArray = JO.getJSONArray("answer");
                int answersArray[] = new int[answersJsonArray.length()];
                for(int j=0;j<answersJsonArray.length();j++){
                    answersArray[j] = answersJsonArray.getInt(j);
                }

                QuestionData questionData = new QuestionData(JO.getString("question"),JO.getString("type"),optionsArray,answersArray);
                questions.add(questionData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateCheckBoxItems(String options[]){
        optionsLayout.removeAllViews();
        for (int i = 0; i < options.length; i++)
        {
            TableRow row =new TableRow(this);
            row.setId(i);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnCheckedChangeListener(this);
            row.setPadding(0,10,0,0);
            checkBox.setPadding(10,0,0,0);
            checkBox.setButtonDrawable(R.drawable.select_button_selector);
            checkBox.setTypeface(optionFontStyleTypeface);
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.textSize_recMedium));
            checkBox.setTextColor(getResources().getColor(R.color.whiteColor));
            checkBox.setTag(i);
            checkBox.setText(options[i]);
            row.addView(checkBox);
            optionsLayout.addView(row);
        }

    }

    private void populateRadioBoxItems(String options[]){
        optionsLayout.removeAllViews();
        RadioGroup group = new RadioGroup(this);
        group.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < options.length; i++)
        {
            RadioButton btn1 = new RadioButton(this);
            btn1.setText(options[i]);
            btn1.setButtonDrawable(R.drawable.radio_button_selector);
            btn1.setTag(i);
            btn1.setOnCheckedChangeListener(this);
            btn1.setPadding(10,10,0,0);
            btn1.setTypeface(optionFontStyleTypeface);
            btn1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.textSize_recMedium));
            btn1.setTextColor(getResources().getColor(R.color.whiteColor));
            //row.addView(btn1);
            group.addView(btn1);
        }
        optionsLayout.addView(group);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(compoundButton instanceof RadioButton){
            userAnswers[(int) compoundButton.getTag()] = isChecked;
        } else {
            userAnswers[(int) compoundButton.getTag()] = isChecked;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_getStarted_quizActivity :
                nextBtn.setText(R.string.nextLabel);
                showNextQuestion();
                break;
            case R.id.btn_nextBtn_quizActivity :
                //showNextQuestion();
                validateAnswer();
                break;
            case R.id.btn_quitBtn_quizActivity :
                showQuitMessage();
                break;
            case R.id.btn_replayBtn_quizActivity :
                currentQuestion = -1;
                currentScore = 0;
                nextBtn.setText(R.string.nextLabel);
                animateNext(this,layoutThree,layoutOne);
                break;
        }
    }

    private void validateAnswer() {
        if(!checkIfUserSelectedAnswer(userAnswers)){
            Toast.makeText(this, R.string.selectAnyAnswer,Toast.LENGTH_SHORT).show();
        } else if(questions.get(currentQuestion).getType().equals("radio")){
            if(userAnswers[questions.get(currentQuestion).getAnswers()[0]-1]){
                currentScore++;
            }
            showNextQuestion();
        } else {
            Arrays.sort(questions.get(currentQuestion).getAnswers());
            int i=0;
            for(;i<userAnswers.length;i++){
                if(userAnswers[i] && Arrays.binarySearch(questions.get(currentQuestion).getAnswers(), i+1) <0){
                    break;
                }
            }

            if(checkTrueCount(userAnswers) == questions.get(currentQuestion).getAnswers().length && i == userAnswers.length ){
                currentScore++;
            }
            showNextQuestion();
        }
    }

    private boolean checkIfUserSelectedAnswer(boolean[] userAnswers){
        for(boolean value: userAnswers){
            if(value){ return true;}
        }
        return false;
    }

    private int checkTrueCount(boolean[] userAnswers){
        int count = 0;
        for(boolean value: userAnswers){
            if(value){
                count++;
            }
        }
        return count;
    }

    private void setQuizStatus() {
        qNoText.setText((currentQuestion+1) + "/" + (questions.size()));
        scoreText.setText("" +currentScore);
    }

    private void showNextQuestion(){
        currentQuestion++;

        if(currentQuestion == 0){
            // set for first question
            setQuizStatus();
            setQuestionData(currentQuestion);
            animateNext(this,layoutOne,layoutTwo);
        } else if(currentQuestion == questions.size()-1){
            // set for second last question
            nextBtn.setText(R.string.submitText);
            setQuizStatus();
            setQuestionData(currentQuestion);
            animateNext(this,layoutTwo,layoutTwo);
        } else if(currentQuestion == questions.size()){
            // set for last question
            resultMessage.setText(getString(R.string.quizResultMessage,currentScore));
            animateNext(this,layoutTwo,layoutThree);
        } else {
            // set for all other questions
            setQuizStatus();
            setQuestionData(currentQuestion);
            animateNext(this,layoutTwo,layoutTwo);
        }
    }

    private void setQuestionData(int questionNo) {
        questionText.setText(questions.get(questionNo).getQuestion());
        userAnswers = new boolean[questions.get(questionNo).getOptions().length];
        if(questions.get(questionNo).getType().equals("select")){
            populateCheckBoxItems(questions.get(questionNo).getOptions());
        } else {
            populateRadioBoxItems(questions.get(questionNo).getOptions());
        }
    }

    private void showQuitMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.exitQuiz)
                .setCancelable(false)
                .setPositiveButton(R.string.okLabel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        currentQuestion = -1;
                        currentScore = 0;
                        animateNext(QuizActivity.this,layoutTwo,layoutOne);
                    }
                })
                .setNegativeButton(R.string.cancelLabel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    class QuestionData{
        String question, type;
        String options[];
        int answers[];

        private QuestionData(String question, String type, String[] options, int[] answers) {
            this.question = question;
            this.type = type;
            this.options = options;
            this.answers = answers;
        }

        private String getQuestion() {
            return question;
        }

        private String getType() {
            return type;
        }

        private String[] getOptions() {
            return options;
        }

        private int[] getAnswers() {
            return answers;
        }
    }

    public static void animateNext(Context context, LinearLayout current, LinearLayout next){
        current.setVisibility(LinearLayout.GONE);
        next.setVisibility(LinearLayout.VISIBLE);

        Animation animationInVisible = AnimationUtils.loadAnimation(context, R.anim.exit_to_left);
        Animation animationVisible = AnimationUtils.loadAnimation(context, R.anim.enter_from_right);

        animationInVisible.setDuration(500);
        animationVisible.setDuration(500);

        current.setAnimation(animationInVisible);
        next.setAnimation(animationVisible);

        current.animate();
        next.animate();

        animationVisible.start();
        animationInVisible.start();
    }
}
