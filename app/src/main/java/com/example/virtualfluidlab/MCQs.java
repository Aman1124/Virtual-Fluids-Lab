package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.security.PublicKey;
import java.util.Objects;

public class MCQs extends AppCompatActivity {

    TextView questionText;
    TextView option1, option2, option3, option4;
    ImageView circle1, circle2, circle3, circle4;
    ProgressBar timerProgress; TextView timerText;
    ImageView[] circles;
    TextView[] optionsText;
    boolean pressed = false;
    int questionID = 0;


    String[] questions = new String[]{
            "The relation between pressure and velocity in an inviscid, incompressible flow is given by ______",
            "In the Venturi Section of the setup, the pressure of the fluid ______",
            "Bernoulli’s equation is applicable only for which flow?",
            "Energy line in plot of Energy vs position of holes for inviscid & incompressible fluid will be ________",
            "Bernoulli’s principle is derived from which of the following?"
    };

    String[][] options = new String[][]{
            {"p = constant", "p + 0.5ρ*V*V = constant", "0.5ρ*V*V = 0", "p + 0.5ρ*V*V = 0"},
            {"increases", "decreases", "increases then decreases", "decreases then increases"},
            {"Irrotaional", "Viscous", "Inviscid & incompressible", "Compressible"},
            {"Horizontal", "Vertical", "Arbitrary (zig-zag)", "None of the above"},
            {"Conservation of mass", "Conservation of energy", "Conservation of motion", "Conservation of force"}
    };

    int[] correctAnswer = new int[]{2, 4, 3, 1, 2};
    char[] answerStatus = new char[]{'N', 'N', 'N', 'N', 'N'};

    public void changeColor(View view){
        TextView textView = (TextView) view;
        int x = Integer.parseInt((String) view.getTag());
        if(!pressed) {
            if (x == correctAnswer[questionID]) {
                circles[x - 1].setBackgroundResource(R.drawable.blue_tick);
                textView.setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
            } else {
                circles[x - 1].setBackgroundResource(R.drawable.cross_red);
                textView.setBackgroundResource(R.drawable.rounded_rectangle_stroke_red);
                circles[correctAnswer[questionID] - 1].setBackgroundResource(R.drawable.blue_tick);
                optionsText[correctAnswer[questionID] - 1].setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
            }
            answerStatus[questionID] = (char)x;
            pressed = true;
        }
    }

    public void resetColor(){
        for(int i=0;i<4;i++){
            optionsText[i].setBackgroundResource(R.drawable.rounded_rectangle_stroke);
            circles[i].setBackgroundResource(R.drawable.circle_stroke);
        }
    }

    public void changeQuestion(View view){
        LottieAnimationView anim = (LottieAnimationView) view;
        anim.playAnimation();

        pressed = false;
        resetColor();

        int tag = Integer.parseInt((String) view.getTag());

        if(tag == 1) {
            if (questionID < 4)
                questionID++;
        }
        else{
            if(questionID > 0)
                questionID--;
        }
        createQuestion();
        checkAnswered();
    }

    public void checkAnswered(){
        if(answerStatus[questionID] != 'N') {
            int x = answerStatus[questionID];
            resetColor();
            if (x != correctAnswer[questionID]) {
                circles[x - 1].setBackgroundResource(R.drawable.cross_red);
                optionsText[x - 1].setBackgroundResource(R.drawable.rounded_rectangle_stroke_red);
                circles[correctAnswer[questionID] - 1].setBackgroundResource(R.drawable.blue_tick);
                optionsText[correctAnswer[questionID] - 1].setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
            } else {
                circles[x - 1].setBackgroundResource(R.drawable.blue_tick);
                optionsText[x - 1].setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
            }
        }
    }

    public void createQuestion(){
        questionText.setText(questions[questionID]);
        optionsText[0].setText(options[questionID][0]);
        optionsText[1].setText(options[questionID][1]);
        optionsText[2].setText(options[questionID][2]);
        optionsText[3].setText(options[questionID][3]);

        timerProgress.setProgress((questionID+1)*20);
        timerText.setText(String.valueOf(questionID + 1));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_c_qs);

        Objects.requireNonNull(getSupportActionBar()).hide();

        questionText = findViewById(R.id.questionBox);
        timerProgress = findViewById(R.id.timerProgress);
        timerText = findViewById(R.id.timerText);


        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        optionsText = new TextView[]{option1, option2, option3, option4};

        createQuestion();

        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        circle3 = findViewById(R.id.circle3);
        circle4 = findViewById(R.id.circle4);

        circles = new ImageView[]{circle1, circle2, circle3, circle4};
    }
}