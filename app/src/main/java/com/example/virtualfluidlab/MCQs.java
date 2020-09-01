package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MCQs extends AppCompatActivity {

    TextView questionText;
    TextView option1, option2, option3, option4;
    ImageView circle1, circle2, circle3, circle4;
    ImageView[] circles;
    TextView[] optionsText;


    String question = "The relation between pressure and velocity in an inviscid, incompressible flow is given by ______";
    String[] options = new String[]{"p = constant", "p + 0.5ρ*V*V = constant", "0.5ρ*V*V = 0", "p + 0.5ρ*V*V = 0"};

    public void changeColor(View view){
        TextView textView = (TextView) view;
        int x = Integer.parseInt((String) view.getTag());
        if(x == 2) {
            System.out.println("BLUE");
            circles[x-1].setBackgroundResource(R.drawable.blue_tick);
            textView.setBackgroundResource(R.drawable.rounded_rectangle_stroke_blue);
        }
        else {
            System.out.println("RED");
            circles[x-1].setBackgroundResource(R.drawable.cross_red_ring);
            textView.setBackgroundResource(R.drawable.rounded_rectangle_stroke_red);
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

        optionsText = new TextView[]{option1, option2, option3, option4};

        optionsText[0].setText(options[0]);
        optionsText[1].setText(options[1]);
        optionsText[2].setText(options[2]);
        optionsText[3].setText(options[3]);

        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        circle3 = findViewById(R.id.circle3);
        circle4 = findViewById(R.id.circle4);

        circles = new ImageView[]{circle1, circle2, circle3, circle4};
    }
}