package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.security.PublicKey;
import java.text.AttributedString;
import java.util.Objects;

public class MCQs extends AppCompatActivity {

    TextView questionText;
    TextView option1, option2, option3, option4;
    TextView completion, correctQs, wrongQs;
    ImageView circle1, circle2, circle3, circle4;
    ProgressBar timerProgress;
    TextView timerText;
    ConstraintLayout resultScreen;
    TextView scoreText;
    ImageView[] circles;
    TextView[] optionsText;
    boolean pressed = false;
    int expNo, questionID = 0, correct = 0, wrong = 0;

    /*
        0 -- Reynolds number
        1 -- Wind Tunnel
        2 -- Bernoulli
        3 -- Pitot Tube
        4 -- Center of Pressure
    */

    String[][] questions = new String[][]{
            {   //V-Notch
                    "The ratio of inertia force and gravitational force is called as ______",
                    "The Froude’s number for a flow in a channel section is 1. What type of flow is it?",
                    "In an open-channel flow, the specific energy is defined as",
                    "The weir is used to measure",
                    "The dimension of Darcy friction factor is:"
            },
            {   //Reynolds
                    "In Reynolds experiment we observe which of the following flowlines",
                    "If observed streakline is like continuous thread then flow can be said as ____",
                    "For steady flow, streakline is the instantaneous locus of all fluid particles ____",
                    "Which of the factors primarily decide whether the flow in a circular pipe is laminar or turbulent?",
                    "In flow through a pipe, the transition from laminar to turbulent flow does not depend on ____"
            },
            {   //Wind Tunnel
                    "Frictional drag is due to ___",
                    "Which is more dominant in low velocity ?",
                    "Pressure drag is due to ___",
                    "Air is not blown but get sucked by fan. Why?",
                    "Diffuser is longer than contraction nozzle. Why?"
            },
            {   //Bernoulli
                    "The relation between pressure and velocity in an inviscid, incompressible flow for same datum height is given by ______",
                    "In the Venturi Section of the setup, the pressure of the fluid ______",
                    "Bernoulli’s equation is applicable only for which flow?",
                    "Energy line in plot of Energy vs position of holes for inviscid & incompressible fluid will be ________",
                    "Bernoulli’s principle is derived from which of the following?"
            },
            {   //Pitot Tube
                    "Dynamic pressure is:",
                    "For laminar flow velocity profile is ___",
                    "Average value of Coefficient of discharge of a cylindrical pipe is ___",
                    "Total pressure at a point",
                    "For flow in cylindrical pipe, volume flow rate ___"
            },
            {   //Center of Pressure
                    "Fluid pressure always acts ___",
                    "",
                    "",
                    "",
                    ""
            },
            {   //Loses in Pipes
                    "",
                    "",
                    "",
                    "",
                    ""
            }
    };

    String[][][] options = new String[][][]{
            {   //V-Notch
                    {"Reynolds number","Stokes number","Froude's number","Euler's number"},
                    {"Sub Critical","Critical","Super Critical","Tranquil"},
                    {"the total energy per unit","the total energy per unit mass","the total energy per unit specific volume weight","the total energy with respect to the channel bottom as reference"},
                    {"discharge through a small channel","discharge through a large channel","velocity through a small channel","discharge through a small pipe"},
                    {"M⁰L¹T⁰","M⁰L¹T¹","M¹L¹T¹","M⁰L⁰T⁰"}
            },
            {   //Reynolds
                    {"pathline", "streamline", "streakline", "timeline"},
                    {"laminar", "turbulent", "transition", "none of the above"},
                    {"passed through a fixed point", "that is tangential to velocity vector", "both 1 and 2", "none of the above"},
                    {"Prandtl number", "Pressure gradient along the length of the pipe", "Dynamic viscosity coefficient", "Reynolds number"},
                    {"the velocity of the fluid", "the density of the fluid", "the diameter of the pipe", "the length of the pipe"}
            },
            {   //Wind Tunnel
                    {"Velocity of fluid", "Density of fluid", "Viscosity of fluid", "All"},
                    {"Frictional drag", "Pressure drag", "Both", "None"},
                    {"Velocity difference", "Flow separation", "Turbulence in flow", "All"},
                    {"This is just a convention.", "Blowing fan can’t provide enough speed", "Blowing can introduce turbulence", "None"},
                    {"Suction fan require this type of construction.", "Power required by fan in this type of construction is less.", "Turbulence can be introduced in pitot tube due to backflow.", "All"}
            },
            {   //Bernoulli
                    {"P = constant", "P + ρV²/2 = constant", "ρV²/2 = 0", "P + ρV²/2 = 0"},
                    {"increases", "decreases", "increases then decreases", "decreases then increases"},
                    {"Irrotaional", "Viscous", "Inviscid & incompressible", "Compressible"},
                    {"Horizontal", "Vertical", "Arbitrary (zig-zag)", "None of the above"},
                    {"Conservation of mass", "Conservation of energy", "Conservation of motion", "Conservation of force"}
            },
            {   //Pitot Tube
                    {"ρV²/2", "P + ρV²/2", "P - ρV²/2", "None of above"},
                    {"Parabolic", "Circular", "Elliptic", "Linear combination of all above"},
                    {"0.836", "0.843", "0.855", "0.872"},
                    {"< static pressure", ">= static pressure", "= static pressure", "None"},
                    {"is more in turbulent flow", "is more in laminar flow", "does not depend on flow type", "None"}
            },
            {   //Center of Pressure
                    {"Normal to surface", "Tangent to surface", "At 45 ⁰ from Normal", "None"},
                    {"", "", "", ""},
                    {"", "", "", ""},
                    {"", "", "", ""},
                    {"", "", "", ""}
            },
            {   //Loses in Pipes
                    {"","","",""},
                    {"","","",""},
                    {"","","",""},
                    {"","","",""},
                    {"","","",""}
            }
    };

    int[][] correctAnswer = new int[][]{
            {3, 2, 4, 2, 4}, //v-notch
            {3, 1, 3, 4, 4}, //reynolds
            {3, 2, 2, 3, 3}, //wind tunnel
            {2, 4, 3, 1, 2}, //bernoulli
            {1, 1, 3, 2, 1}, //pitot
            {1, 1, 1, 1, 1}, //center of pressure
            {1, 1, 1, 1, 1}  //loses in pipes
    };
    char[][] answerStatus = new char[][]{
            {'N', 'N', 'N', 'N', 'N'}, //v-notch
            {'N', 'N', 'N', 'N', 'N'}, //reynolds
            {'N', 'N', 'N', 'N', 'N'}, //wind tunnel
            {'N', 'N', 'N', 'N', 'N'}, //bernoulli
            {'N', 'N', 'N', 'N', 'N'}, //pitot
            {'N', 'N', 'N', 'N', 'N'}, //center of pressure
            {'N', 'N', 'N', 'N', 'N'}  //loses in pipes
    };

    public void changeColor(View view) {
        TextView textView = (TextView) view;
        int x = Integer.parseInt((String) view.getTag());
        if (!pressed) {
            if (x == correctAnswer[expNo][questionID]) {
                correct += 1;
                circles[x - 1].setBackgroundResource(R.drawable.blue_tick);
                textView.setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
            } else {
                wrong += 1;
                circles[x - 1].setBackgroundResource(R.drawable.cross_red);
                textView.setBackgroundResource(R.drawable.rounded_rectangle_stroke_red);
                circles[correctAnswer[expNo][questionID] - 1].setBackgroundResource(R.drawable.blue_tick);
                optionsText[correctAnswer[expNo][questionID] - 1].setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
            }
            answerStatus[expNo][questionID] = (char) x;
            pressed = true;
        }
    }

    public void resetColor() {
        for (int i = 0; i < 4; i++) {
            optionsText[i].setBackgroundResource(R.drawable.rounded_rectangle_stroke);
            circles[i].setBackgroundResource(R.drawable.circle_stroke);
        }
    }

    public void changeQuestion(View view) {
        LottieAnimationView anim = (LottieAnimationView) view;
        anim.playAnimation();

        int tag = Integer.parseInt((String) view.getTag());

        if (questionID == 4 && tag == 1) {
            System.out.println("Correct = " + correct + "  Wrong = " + wrong);
            resultScreen.setVisibility(View.VISIBLE);
            int score = calculateScore();
            scoreText.setText(String.valueOf(score));
            correctQs.setText(String.valueOf(correct));
            wrongQs.setText(String.valueOf(wrong));
            completion.setText(String.format("%s%%", (correct + wrong) * 20));
        }

        pressed = false;
        resetColor();

        if (tag == 1) {
            if (questionID < 4)
                questionID++;
        } else {
            if (questionID > 0)
                questionID--;
        }
        createQuestion();
        checkAnswered();
    }

    public void checkAnswered() {
        if (answerStatus[expNo][questionID] != 'N') {
            int x = answerStatus[expNo][questionID];
            resetColor();
            if (x != correctAnswer[expNo][questionID]) {
                circles[x - 1].setBackgroundResource(R.drawable.cross_red);
                optionsText[x - 1].setBackgroundResource(R.drawable.rounded_rectangle_stroke_red);
                circles[correctAnswer[expNo][questionID] - 1].setBackgroundResource(R.drawable.blue_tick);
                optionsText[correctAnswer[expNo][questionID] - 1].setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
            } else {
                circles[x - 1].setBackgroundResource(R.drawable.blue_tick);
                optionsText[x - 1].setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
            }
        }
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < 5; i++)
            if ((int) answerStatus[expNo][i] == correctAnswer[expNo][i]) {
                score += 30;
            }
        return score;
    }

    public void createQuestion() {
        questionText.setText(questions[expNo][questionID]);
        optionsText[0].setText(options[expNo][questionID][0]);
        optionsText[1].setText(options[expNo][questionID][1]);
        optionsText[2].setText(options[expNo][questionID][2]);
        optionsText[3].setText(options[expNo][questionID][3]);

        timerProgress.setProgress((questionID + 1) * 20);
        timerText.setText(String.valueOf(questionID + 1));
    }

    public void playAgain(View view) {
        questionID = 0;
        resetColor();
        for (int i = 0; i < 5; i++)
            answerStatus[expNo][i] = 'N';
        LottieAnimationView animationView = (LottieAnimationView) view;
        animationView.playAnimation();
        resultScreen.setVisibility(View.INVISIBLE);
        createQuestion();
    }

    public void backToDrawer(View view) {
        LottieAnimationView animationView = (LottieAnimationView) view;
        animationView.playAnimation();
        super.onBackPressed();
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

        completion = findViewById(R.id.percent_complete);
        correctQs = findViewById(R.id.correct_questions);
        wrongQs = findViewById(R.id.wrong_questions);

        resultScreen = findViewById(R.id.resultScreen);
        scoreText = findViewById(R.id.scoreText);

        optionsText = new TextView[]{option1, option2, option3, option4};

        Intent intent = getIntent();
        expNo = intent.getIntExtra("choice", 3);

        createQuestion();

        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        circle3 = findViewById(R.id.circle3);
        circle4 = findViewById(R.id.circle4);

        circles = new ImageView[]{circle1, circle2, circle3, circle4};
    }
}