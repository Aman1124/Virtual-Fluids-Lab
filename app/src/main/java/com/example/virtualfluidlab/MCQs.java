package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MCQs extends AppCompatActivity {

    TextView questionText;
    TextView option1, option2, option3, option4;
    ImageView circle1, circle2, circle3, circle4;
    ImageView[] circles;

    boolean a = true;

    String question = "The relation between pressure and velocity in an inviscid, incompressible flow is given by ______";
    String optionA = "p = constant";
    String optionB = "p + 0.5ρ*V*V = constant";
    String optionC = "0.5ρ*V*V = 0";
    String optionD = "p + 0.5ρ*V*V = 0";

    public void changeColor(View view){
        TextView textView = (TextView) view;
        int x = Integer.parseInt((String) view.getTag());
        if(a) {
            System.out.println("BLUE");
            circles[x-1].setBackgroundResource(R.drawable.circle_stroke_blue);
            textView.setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
            a = false;
        }
        else {
            System.out.println("RED");
            circles[x-1].setBackgroundResource(R.drawable.circle_stroke_red);
            textView.setBackgroundResource(R.drawable.rounded_rectangle_stroke_red);
            a = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_c_qs);

        Objects.requireNonNull(getSupportActionBar()).hide();

        questionText = findViewById(R.id.questionBox);
        questionText.setText(question);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setText(optionA);
        option2.setText(optionB);
        option3.setText(optionC);
        option4.setText(optionD);

        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        circle3 = findViewById(R.id.circle3);
        circle4 = findViewById(R.id.circle4);

        circles = new ImageView[]{circle1, circle2, circle3, circle4};

    }
}