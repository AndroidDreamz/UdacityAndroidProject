package org.ksconnect.udacityandroidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    int ques_no = 1;
    int score_total = 0;
    boolean op1;
    boolean op2;
    boolean op3;
    String ques = "Ques no. ";
    TextView question ;
    TextView option1;
    TextView option2;
    TextView option3;
    TextView score;
    TextView quesno;
    LinearLayout li ;
    LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

         ques_no = 1;
         score_total = 0;

         ques = "Ques no. ";
         question = (TextView) findViewById(R.id.ques);
         option1 = (TextView) findViewById(R.id.op1);
         option2 = (TextView) findViewById(R.id.op2);
         option3 = (TextView) findViewById(R.id.op3);
         score = (TextView) findViewById(R.id.value);
         quesno = (TextView) findViewById(R.id.ques_no);
         li = (LinearLayout) findViewById(R.id.linearlayout);
         main = (LinearLayout) findViewById(R.id.main);
    }

    public void op1(View view)
    {
        op1 = true;
        checkAns(view);
    }
    public void op2(View view)
    {
        op2 = true;
        checkAns(view);
    }
    public void op3(View view)
    {
        op3 = true;
        checkAns(view);
    }

    public void winner()
    {
        li.setVisibility(LinearLayout.INVISIBLE);
        //main.setBackgroundResource(R.drawable.winner);
    }

    public void quit()
    {
        //question.setText(getResources().getString(R.string.over));
        //question.setTextColor(getResources().getColor(R.color.wrong));

        //option1.setText(getResources().getString(R.string.score));
        option1.setTextSize(10);

        option2.setText("" + score_total);
        option2.setTextSize(30);

        option3.setVisibility(TextView.INVISIBLE);

        score.setVisibility(TextView.INVISIBLE);
    }

    public void next()
    {

        //question.setText(getResources().getString(R.string.q2));

       // option1.setText(getResources().getString(R.string.q2_op1));

        //option2.setText(getResources().getString(R.string.q2_op2));

        //option3.setText(getResources().getString(R.string.q2_op3));

        score.setText("" + score_total);

        ques = ques + ques_no;
        quesno.setText(ques);

        if(ques_no == 3)
        {
            //li.setBackgroundResource(R.drawable.innovation);
        }
    }

    public void checkAns(View view)
    {

        if(ques_no == 1)
        {
            if(op2)
            {
                ques_no++;
                score_total +=50;
                Button v = (Button) findViewById(R.id.op2);
               // v.setText(R.string.right);
                //v.setBackgroundColor(getResources().getColor(R.color.right));
                op2 = false;
                next();
            }
            else
                quit();
        }
        if(ques_no == 2)
        {
            if(op1)
            {
                ques_no++;
                score_total +=50;
                Button v = (Button) findViewById(R.id.op1);
                //v.setText(R.string.right);
                //v.setBackgroundColor(getResources().getColor(R.color.right));
                op1 = false;
                next();
            }
            else
                quit();
        }
        if(ques_no == 3)
        {
            if(op2)
                winner();
            else
                quit();
        }
    }
}
