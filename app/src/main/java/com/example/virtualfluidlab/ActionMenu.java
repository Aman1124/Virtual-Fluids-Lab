package com.example.virtualfluidlab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ActionMenu extends AppCompatActivity {

    TextView textView;

    LottieAnimationView robot404, workFromHome;

    String about = "This app has been developed by 3rd year B.Tech students from Mechanical Engineering " +
            "at IIT Patna. This is a virtual lab for experiments of fluid mechanics. " +
            "Our aim is to help students who may not have access to physical apparatus seeing the current scenario " +
            "and will help them in understanding concepts via performing experiments virtually.This app contains " +
            "all the steps a student usually executes while performing experiment physically and therefore is easy " +
            "to understand. Also it contains self assessment section for every experiment which has few MCQs questions " +
            "to test their basic understanding of the experiment.\n" +
            "\nTeam:\n" +
            "Aman Kumar: (App development)\n" +
            "Akshat Jain (Content writing) \n" +
            "Ashutosh Anand: (SVG and data generation)\n" +
            "Ashutosh Maurya: (ANSYS simulation)\n" +
            "Diptanil Sarkar: (Designing of app elements)\n" +
            "\nMentors:\n" +
            "Dr. Mohd. Kaleem Khan\n" +
            "Dr. Manabendra Pathak\n" +
            "Dr. Ashwani Assam";

    String references = "1. Bernoulli's Experiment: KC Lab Manual";

    String help = "In case you are facing any difficulty or have any suggestion regarding the app, you can mail to any one of us:\n\n" +
            "1. Aman Kumar (1801me06@iitp.ac.in)\n" +
            "2. Akshat Jain (1801me05@iitp.ac.in)\n" +
            "3. Ashutosh Anand (1801me15@iitp.ac.in)\n" +
            "4. Ashutosh Maurya (1801me16@iitp.ac.in)\n" +
            "5. Diptanil Sarkar (1801me21@iitp.ac.in)";

    String displayDetailsText = "";

    Point size;
    Display display;
    DisplayMetrics metrics;

    int option;

    public void showOption(int choice){
        switch (choice){
            case 1:
                setTitle("About");
                textView.setText(about);
//                robot404.setVisibility(View.VISIBLE);
//                robot404.playAnimation();
                break;
            case 2:
                setTitle("References");
                textView.setText(references);
//                workFromHome.setVisibility(View.VISIBLE);
//                workFromHome.playAnimation();
                break;
            case 3:
                setTitle("Display Details");
                textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                textView.setText(displayDetailsText);
                break;
            case 4:
                setTitle("Help");
                textView.setText(help);
                workFromHome.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void replayAnimation(View view){
        LottieAnimationView anim = (LottieAnimationView) view;
        anim.playAnimation();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_menu);

        textView = findViewById(R.id.expanded_menu);
        //robot404 = findViewById(R.id.robot404);
        workFromHome = findViewById(R.id.workFromHome);

        Intent intent = getIntent();
        option = intent.getIntExtra("option", 0);

        size = new Point();
        display = getWindowManager().getDefaultDisplay();
        metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);
        display.getSize(size);
        displayDetailsText = "Width: " + size.x + "\nHeight: " + size.y + "\nDensity: " + densityDpi;
        display.getRealSize(size);
        displayDetailsText += "\nReal Width: " + size.x + "\nReal Height: " + size.y;

        showOption(option);
    }

}