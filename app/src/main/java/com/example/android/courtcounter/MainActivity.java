package com.example.android.courtcounter;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int scoreTeamA = 0;
    private int scoreTeamB = 0;
    private boolean started = false;
    private int numTeamA3Points=0,numTeamA2Points=0,numTeamA1Points=0,numTeamB3Points=0,numTeamB2Points=0,numTeamB1Points=0;
    TextView scoreViewA,scoreViewB,txt3A,txt2A,txt1A,txt3B,txt2B,txt1B;
    Button btnStart;
    long startTime=0L,timeInMilliSecond=0L,timeSwapBuff=0L,updateTime=0L;
    TextView txtTime;
    Handler h=new Handler();
    Runnable updateTimeThread=new Runnable() {
        @Override
        public void run() {
            timeInMilliSecond=SystemClock.uptimeMillis()-startTime;
            updateTime=timeSwapBuff+timeInMilliSecond;
            int secs=(int)(updateTime/1000);
            int mins=secs/60;
            secs%=60;
            int milliSecs=(int)updateTime%1000;
            txtTime.setText(" "+mins+":"+String.format("%02d",secs)+":"+String.format("%03d",milliSecs));
            h.postDelayed(this,0);

        }
    };

    public void addThreeForTeamA(View v) {
        if (started) {
            scoreTeamA += 3;
            displayForTeamA(scoreTeamA);
            numTeamA3Points++;
            txt3A.setText(String.valueOf(numTeamA3Points));

        }
    }

    public void addTwoForTeamA(View v) {
        if (started) {
            scoreTeamA += 2;
            displayForTeamA(scoreTeamA);
            numTeamA2Points++;
            txt2A.setText(String.valueOf(numTeamA2Points));
        }
    }

    public void addOneForTeamA(View v) {
        if (started) {
            scoreTeamA += 1;
            displayForTeamA(scoreTeamA);
            numTeamA1Points++;
            txt1A.setText(String.valueOf(numTeamA1Points));
        }
    }

    public void addThreeForTeamB(View v) {
        if (started) {
            scoreTeamB += 3;
            displayForTeamB(scoreTeamB);
            numTeamB3Points++;
            txt3B.setText(String.valueOf(numTeamB3Points));
        }
    }

    public void addTwoForTeamB(View v) {
        if (started) {
            scoreTeamB += 2;
            displayForTeamB(scoreTeamB);
            numTeamB2Points++;
            txt2B.setText(String.valueOf(numTeamB2Points));
        }
    }

    public void addOneForTeamB(View v) {
        if (started) {
            scoreTeamB += 1;
            displayForTeamB(scoreTeamB);
            numTeamB1Points++;
            txt1B.setText(String.valueOf(numTeamB1Points));
        }
    }

    public void reset(View v) {

        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        stopGame(v);
        h.removeCallbacks(updateTimeThread);
        timeSwapBuff=0;
        txtTime.setText("00:00:000");
        numTeamA3Points=0;numTeamA2Points=0;numTeamA1Points=0;numTeamB3Points=0;numTeamB2Points=0;numTeamB1Points=0;
        txt3A.setText(String.valueOf(numTeamA3Points));
        txt2A.setText(String.valueOf(numTeamA2Points));
        txt1A.setText(String.valueOf(numTeamA1Points));
        txt3B.setText(String.valueOf(numTeamB3Points));
        txt2B.setText(String.valueOf(numTeamB2Points));
        txt1B.setText(String.valueOf(numTeamB1Points));
    }

    public void startGame(View v) {
        started = true;
       startTime= SystemClock.uptimeMillis();

        h.postDelayed(updateTimeThread,0);
    }

    public void stopGame(View v) {
        started = false;
       timeSwapBuff+=timeInMilliSecond;
       h.removeCallbacks(updateTimeThread);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       txtTime=(TextView)findViewById(R.id.time_text);
        txt3A=(TextView)findViewById(R.id.Team_A_3_points);
        txt2A=(TextView)findViewById(R.id.Team_A_2_points);
        txt1A=(TextView)findViewById(R.id.Team_A_1_points);
        txt3B=(TextView)findViewById(R.id.Team_B_3_points);
        txt2B=(TextView)findViewById(R.id.Team_B_2_points);
        txt1B=(TextView)findViewById(R.id.Team_B_1_points);
        scoreViewB = (TextView) findViewById(R.id.team_b_score);
        scoreViewA = (TextView) findViewById(R.id.team_a_score);
        btnStart= (Button)findViewById(R.id.button_start);
    }
/**
 * To save on rotating
 */
@Override
public void onSaveInstanceState(Bundle savedInstanceState) {
    // Save the user's current game state
    savedInstanceState.putInt("scoreTeamA", scoreTeamA);
    savedInstanceState.putInt("scoreTeamB", scoreTeamB);
    savedInstanceState.putInt("numTeamA3Points", numTeamA3Points);
    savedInstanceState.putInt("numTeamA2Points", numTeamA2Points);
    savedInstanceState.putInt("numTeamA1Points", numTeamA1Points);
    savedInstanceState.putInt("numTeamB3Points", numTeamB3Points);
    savedInstanceState.putInt("numTeamB2Points", numTeamB2Points);
    savedInstanceState.putInt("numTeamB1Points", numTeamB1Points);
    savedInstanceState.putBoolean("started", started);
    savedInstanceState.putLong("timeInMilliSecond", timeInMilliSecond);

    savedInstanceState.putLong("timeSwapBuff", timeSwapBuff);
    savedInstanceState.putLong("updateTime", updateTime);

    // Always call the superclass so it can save the view hierarchy state
    super.onSaveInstanceState(savedInstanceState);
}
/**
 * To restore
 */
public void onRestoreInstanceState(Bundle savedInstanceState) {
    // Always call the superclass so it can restore the view hierarchy
    super.onRestoreInstanceState(savedInstanceState);


    // Restore state members from saved instance
    scoreTeamA = savedInstanceState.getInt("scoreTeamA");
    scoreTeamB = savedInstanceState.getInt("scoreTeamB");
    scoreViewA.setText(String.valueOf(scoreTeamA));
    scoreViewB.setText(String.valueOf(scoreTeamB));
    numTeamA3Points = savedInstanceState.getInt("numTeamA3Points");
    numTeamA2Points = savedInstanceState.getInt("numTeamA2Points");
    numTeamA1Points = savedInstanceState.getInt("numTeamA1Points");
    numTeamB3Points = savedInstanceState.getInt("numTeamB3Points");
    numTeamB2Points = savedInstanceState.getInt("numTeamB2Points");
    numTeamB1Points = savedInstanceState.getInt("numTeamB1Points");
    txt3A.setText(String.valueOf(numTeamA3Points));
    txt2A.setText(String.valueOf(numTeamA2Points));
    txt1A.setText(String.valueOf(numTeamA1Points));
    txt3B.setText(String.valueOf(numTeamB3Points));
    txt2B.setText(String.valueOf(numTeamB2Points));
    txt1B.setText(String.valueOf(numTeamB1Points));
    started = savedInstanceState.getBoolean("started");
    timeSwapBuff = savedInstanceState.getLong("timeSwapBuff");
    updateTime = savedInstanceState.getLong("updateTime");
    timeInMilliSecond = savedInstanceState.getLong("timeInMilliSecond");


        if (started) {
            timeSwapBuff+=timeInMilliSecond;
            startTime = SystemClock.uptimeMillis();
            h.postDelayed(updateTimeThread, 0);
        } else if(timeSwapBuff!=0) {
            int secs=(int)(updateTime/1000);
            int mins=secs/60;
            secs%=60;
            int milliSecs=(int)updateTime%1000;
            txtTime.setText(" "+mins+":"+String.format("%02d",secs)+":"+String.format("%03d",milliSecs));
            updateTime=0;
        }

}
    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {

        scoreViewA.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score) {

        scoreViewB.setText(String.valueOf(score));
    }
}
