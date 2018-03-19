package org.ksconnect.udacityandroidproject;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CourtCounterActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout firstLayout,secondLayout,thirdLayout,fourthLayout,
            fifthLayout,teamIndia,teamPak,tossWon,tossLoss,
            tossWonBatting,tossWonBowling,statisticsRunsLeft;
    ArrayList<LinearLayout> cricketLayouts = new ArrayList<>();
    Button playBtnFirst,contBtnSecond,contBtnThird,score1Run,score2Run,score3Run,score4Run,score6Run,wicket,replay;
    ImageView teamIndiaSelect,teamPakSelect,tossWonBatSelect,tossWonBowlSelect,userTeamIndiaForth,userTeamPakForth;
    TextView tossLossStatus,scoreboardTeamName,scoreboardRuns,scoreboardWickets,
            scoreboardBalls,statisticksRLeft,statisticsWLeft,statisticsBLeft,userTeamStatus,
            userGameStatus;
    int currentLayout = 0;
    int teamSelected = 1;   // 1 for india , 0 for pak
    int userPlayStatus = 1; // 1 for batting, 0 for bowling
    int inning = 0;
    int runsScored=0,wickets=0,firstInnnigScore=0,balls=0;
    final int TOTAL_BALLS = 12, TOTAL_WICKETS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_counter);
        initializeUIElements();
    }

    private void initializeUIElements() {
        firstLayout = findViewById(R.id.layout_cricket_one);
        cricketLayouts.add(firstLayout);
        secondLayout = findViewById(R.id.layout_cricket_two);
        cricketLayouts.add(secondLayout);
        thirdLayout = findViewById(R.id.layout_cricket_three);
        cricketLayouts.add(thirdLayout);
        fourthLayout = findViewById(R.id.layout_cricket_four);
        cricketLayouts.add(fourthLayout);
        fifthLayout = findViewById(R.id.layout_cricket_five);
        cricketLayouts.add(fifthLayout);
        teamIndia = findViewById(R.id.layout_cricket_second_teamIndiaSelect);
        teamPak = findViewById(R.id.layout_cricket_second_teamPakSelect);
        tossLoss = findViewById(R.id.layout_cricket_tossloss_three);
        tossWon = findViewById(R.id.layout_cricket_tosswon_three);
        statisticsRunsLeft = findViewById(R.id.layout_cricket_statistics_runsLeft_forth);
        tossWonBatting = findViewById(R.id.layout_cricket_tosswon_statusChoose_batting_third);
        tossWonBowling = findViewById(R.id.layout_cricket_tosswon_statusChoose_bowling_third);
        tossWonBatSelect = findViewById(R.id.img_cricket_tosswon_statusChoose_batting_third);
        tossWonBowlSelect = findViewById(R.id.img_cricket_tosswon_statusChoose_bowling_third);

        teamIndiaSelect = findViewById(R.id.img_cricket_second_teamIndiaSelect);
        teamPakSelect = findViewById(R.id.img_cricket_second_teamPakSelect);
        tossLossStatus = findViewById(R.id.txt_cricket_tossloss_status_three);
        userTeamIndiaForth = findViewById(R.id.img_cricket_teamIndiaSelect_forth);
        userTeamPakForth = findViewById(R.id.img_cricket_teamPakSelect_forth);
        scoreboardRuns = findViewById(R.id.txt_cricket_scoreboard_runs_forth);
        scoreboardWickets = findViewById(R.id.txt_cricket_scoreboard_wickets_forth);
        scoreboardBalls = findViewById(R.id.txt_cricket_scoreboard_balls_forth);
        statisticksRLeft = findViewById(R.id.txt_cricket_statistics_runsLeft_forth);
        statisticsWLeft = findViewById(R.id.txt_cricket_statistics_wicketsLeft_forth);
        statisticsBLeft = findViewById(R.id.txt_cricket_statistics_ballsLeft_forth);
        userTeamStatus = findViewById(R.id.txt_cricket_userTeamStatus_forth);
        scoreboardTeamName = findViewById(R.id.txt_cricket_scoreboard_teamName_forth);
        userGameStatus = findViewById(R.id.txt_cricket_userGameStatus_fifth);

        playBtnFirst = findViewById(R.id.btn_cricket_first_play);
        playBtnFirst.setOnClickListener(this);
        contBtnSecond = findViewById(R.id.btn_cricket_second_continue);
        contBtnSecond.setOnClickListener(this);
        contBtnThird = findViewById(R.id.btn_cricket_continue_third);
        contBtnThird.setOnClickListener(this);
        score1Run = findViewById(R.id.btn_cricket_score1Run_forth);
        score1Run.setOnClickListener(this);
        score2Run = findViewById(R.id.btn_cricket_score2Run_forth);
        score2Run.setOnClickListener(this);
        score3Run = findViewById(R.id.btn_cricket_score3Run_forth);
        score3Run.setOnClickListener(this);
        score4Run = findViewById(R.id.btn_cricket_score4Run_forth);
        score4Run.setOnClickListener(this);
        score6Run = findViewById(R.id.btn_cricket_score6Run_forth);
        score6Run.setOnClickListener(this);
        wicket = findViewById(R.id.btn_cricket_wicket_forth);
        wicket.setOnClickListener(this);
        replay = findViewById(R.id.btn_cricket_raplay_fifth);
        replay.setOnClickListener(this);


        teamIndia.setOnClickListener(this);
        teamPak.setOnClickListener(this);
        tossWonBatting.setOnClickListener(this);
        tossWonBowling.setOnClickListener(this);

        currentLayout=0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cricket_first_play :
            case R.id.btn_cricket_second_continue :
            case R.id.btn_cricket_continue_third :
            case R.id.btn_cricket_raplay_fifth:
                navigateNext();
                break;
            case R.id.layout_cricket_second_teamIndiaSelect :
                teamIndiaSelect.setImageDrawable(getResources().getDrawable(R.drawable.radio_checked));
                teamPakSelect.setImageDrawable(getResources().getDrawable(R.drawable.radio_unchecked));
                teamSelected = 1;
                break;
            case R.id.layout_cricket_second_teamPakSelect :
                teamPakSelect.setImageDrawable(getResources().getDrawable(R.drawable.radio_checked));
                teamIndiaSelect.setImageDrawable(getResources().getDrawable(R.drawable.radio_unchecked));
                teamSelected = 0;
                break;
            case R.id.layout_cricket_tosswon_statusChoose_batting_third :
                tossWonBatSelect.setImageDrawable(getResources().getDrawable(R.drawable.radio_checked));
                tossWonBowlSelect.setImageDrawable(getResources().getDrawable(R.drawable.radio_unchecked));
                userPlayStatus = 1;
                break;
            case R.id.layout_cricket_tosswon_statusChoose_bowling_third :
                tossWonBowlSelect.setImageDrawable(getResources().getDrawable(R.drawable.radio_checked));
                tossWonBatSelect.setImageDrawable(getResources().getDrawable(R.drawable.radio_unchecked));
                userPlayStatus = 0;
                break;
            case R.id.btn_cricket_score1Run_forth :
                scoreRun(1);
                break;
            case R.id.btn_cricket_score2Run_forth :
                scoreRun(2);
                break;
            case R.id.btn_cricket_score3Run_forth :
                scoreRun(3);
                break;
            case R.id.btn_cricket_score4Run_forth :
                scoreRun(4);
                break;
            case R.id.btn_cricket_score6Run_forth :
                scoreRun(6);
                break;
            case R.id.btn_cricket_wicket_forth :
                setWicket();
                break;
        }
    }

    private void scoreRun(int runs) {
        runsScored += runs;
        balls++;
        Toast.makeText(this,"Scored : " + runs + " runs.",Toast.LENGTH_SHORT).show();
        resetUserDashboard();
    }

    private void setWicket(){
        wickets++;
        balls++;
        Toast.makeText(this,"Ohh, Its a Wicket",Toast.LENGTH_SHORT).show();
        resetUserDashboard();
    }

    private void navigateNext(){
        if(isCurrentStepValid()){
            if(currentLayout==0){
                animateNext(this,cricketLayouts.get(currentLayout),cricketLayouts.get(++currentLayout));
            } else if(currentLayout ==1){
                animateNext(this,cricketLayouts.get(currentLayout),cricketLayouts.get(++currentLayout));
                setTossUI(getTossStatus());
            } else if(currentLayout==2){
                if(teamSelected==1){
                    userTeamIndiaForth.setVisibility(View.VISIBLE);
                    userTeamPakForth.setVisibility(View.INVISIBLE);
                } else {
                    userTeamIndiaForth.setVisibility(View.INVISIBLE);
                    userTeamPakForth.setVisibility(View.VISIBLE);
                }

                if(userPlayStatus==1){
                    userTeamStatus.setText("Batting");
                } else {
                    userTeamStatus.setText("Bowling");
                }

                setTeamPlayName();
                resetUserDashboard();
                animateNext(this,cricketLayouts.get(currentLayout),cricketLayouts.get(++currentLayout));
            }  else if(currentLayout==3){

                animateNext(this,cricketLayouts.get(currentLayout),cricketLayouts.get(++currentLayout));
            } else if(currentLayout==4){
                animateNext(this,cricketLayouts.get(currentLayout),cricketLayouts.get(0));
                currentLayout=0;
            }
        }
    }

    private void setTeamPlayName() {
        if(teamSelected==1&&userPlayStatus==1){
            scoreboardTeamName.setText("IND ");
        } else if(teamSelected==0&&userPlayStatus==1){
            scoreboardTeamName.setText("PAK ");
        } else if(teamSelected==1&&userPlayStatus==0){
            scoreboardTeamName.setText("PAK ");
        } else {
            scoreboardTeamName.setText("IND ");
        }
    }

    private void resetUserDashboard() {
        scoreboardRuns.setText(Integer.toString(runsScored));
        scoreboardWickets.setText(Integer.toString(wickets));
        scoreboardBalls.setText(Integer.toString(balls));

        statisticsWLeft.setText(Integer.toString(TOTAL_WICKETS-wickets));
        statisticsBLeft.setText(Integer.toString(TOTAL_BALLS-balls));

        if(inning==0){
            statisticsRunsLeft.setVisibility(View.GONE);
        } else {
            statisticsRunsLeft.setVisibility(View.VISIBLE);
            statisticksRLeft.setText(Integer.toString(firstInnnigScore+1-runsScored));
        }

        gameValidations();
    }

    private void gameValidations() {
        if(wickets>=TOTAL_WICKETS){
            terminateInning();
        } else if(balls >=TOTAL_BALLS){
            terminateInning();
        } else if(inning==1 && runsScored>firstInnnigScore){
            terminateInning();
        }
    }

    private void terminateInning() {
        if(inning==0){
            if(userPlayStatus==1){
                showDialog("Opponent has target of " + (runsScored+1) + " runs.");
            } else {
                showDialog("You have a target of " + (runsScored+1) + " runs.");
            }
        } else {
            setGameStatus();
            navigateNext();
        }

    }

    private void setGameStatus() {
        if(userPlayStatus==1){
            if(runsScored>firstInnnigScore){
               userGameStatus.setText(getResources().getString(R.string.userWinsMsg));
            } else if(runsScored<firstInnnigScore){
                userGameStatus.setText(getResources().getString(R.string.userLooseMsg));
            } else {
                userGameStatus.setText(getResources().getString(R.string.matchTieMsg));
            }
        } else {
            if(runsScored>firstInnnigScore){
                userGameStatus.setText(getResources().getString(R.string.userLooseMsg));
            } else if(runsScored<firstInnnigScore){
                userGameStatus.setText(getResources().getString(R.string.userWinsMsg));
            } else {
                userGameStatus.setText(getResources().getString(R.string.matchTieMsg));
            }
        }
    }

    private void setTossUI(boolean tossStatus) {
        if(tossStatus){
            tossWon.setVisibility(View.VISIBLE);
            tossLoss.setVisibility(View.GONE);
        } else {
            tossWon.setVisibility(View.GONE);
            tossLoss.setVisibility(View.VISIBLE);
            if(getTossStatus()){
                userPlayStatus = 1;
                tossLossStatus.setText("Batting");
            } else {
                userPlayStatus = 0;
                tossLossStatus.setText("Bowling");
            }
        }
    }

    private boolean getTossStatus(){
        return (int)(Math.random() * 2)==1;
    }


    private boolean isCurrentStepValid(){
        try{
            if(currentLayout==0){
                return true;
            } else if(currentLayout==1){

            } else if(currentLayout==2){

            } else if(currentLayout==3){

            } else if(currentLayout==4) {

            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
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

    private void showDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        firstInnnigScore = runsScored;
                        runsScored=0;
                        wickets=0;
                        balls=0;
                        inning=1;
                        if(userPlayStatus==0){
                            userPlayStatus=1;
                        } else {
                            userPlayStatus=0;
                        }

                        if(userPlayStatus==1){
                            userTeamStatus.setText("Batting");
                        } else {
                            userTeamStatus.setText("Bowling");
                        }
                        setTeamPlayName();
                        resetUserDashboard();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {

        if(currentLayout==0){
            showMessage("Do you want to quit Application?");
        } else {
            showMessage("Quit current game?");
        }

    }

    private void showMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(currentLayout==0){
                            finish();
                        } else {
                            animateNext(CourtCounterActivity.this,cricketLayouts.get(currentLayout),cricketLayouts.get(0));
                            currentLayout=0;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
