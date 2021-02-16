package com.example.virtualfluidlab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Locale;

public class ActionMenu extends AppCompatActivity {

    TextView textView;
    TextView[] manuals;

    ScrollView additional, links;

    LottieAnimationView workFromHome;

    String about = "This app has been developed by 3rd year B.Tech students from Mechanical Engineering " +
            "at IIT Patna. This is a virtual lab for experiments of fluid mechanics. " +
            "Our aim is to help students who may not have access to physical apparatus seeing the current scenario " +
            "and will help them in understanding concepts via performing experiments virtually.This app contains " +
            "all the steps a student usually executes while performing experiment physically and therefore is easy " +
            "to understand. Also it contains self assessment section for every experiment which has few MCQs questions " +
            "to test their basic understanding of the experiment.\n" +
            "\nTeam:\n" +
            "Aman Kumar (App development)\n" +
            "Akshat Jain (Content writing) \n" +
            "Ashutosh Anand (Data generation)\n" +
            "Ashutosh Maurya (ANSYS simulation)\n" +
            "Diptanil Sarkar (Designing)\n" +
            "\nMentors:\n" +
            "Dr. Mohd. Kaleem Khan\n" +
            "Dr. Manabendra Pathak\n" +
            "Dr. Ashwani Assam";

    String references = "References : \n" +
            "• KC Engineering Lab manuals\n" +
            "• Instructions Manual Armfield Limited\n" +
            "• Fluid Mechanics: Fundamentals and Application by J.Cimbala and Y.A. Cengel \n" +
            "• Fluid Mechanics and Machinery by Mohd. Kaleem Khan\n" +
            "• Fluid Mechanics by F.M White\n" +
            "• Fundamentals of Fluid Mechanics by Munson, Young, Okiishi, Huebsch";

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

    public void showOption(int choice) {
        switch (choice) {
            case 1:
                setTitle("About");
                textView.setText(about);
                break;
            case 2:
                setTitle("References");
                textView.setText(references);
                break;
            case 3:
                setTitle("Additional Resources");
                additional.setVisibility(View.INVISIBLE);
                links.setVisibility(View.VISIBLE);
                break;
            case 4:
                setTitle("Display Details");
                textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                textView.setText(displayDetailsText);
                break;
            case 5:
                setTitle("Help");
                textView.setText(help);
                workFromHome.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void replayAnimation(View view) {
        LottieAnimationView anim = (LottieAnimationView) view;
        anim.playAnimation();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_menu);

        textView = findViewById(R.id.expanded_menu);
        workFromHome = findViewById(R.id.workFromHome);
        additional = findViewById(R.id.additional_resources_text);
        links = findViewById(R.id.additional_resources_links);
        manuals = new TextView[]{
                findViewById(R.id.bernoulliManual),
                findViewById(R.id.copManual),
                findViewById(R.id.pitotManual),
                findViewById(R.id.reynoldsManual),
                findViewById(R.id.windManual),
                findViewById(R.id.vNotchManual)
        };

        manuals[0].setMovementMethod(LinkMovementMethod.getInstance());
        manuals[1].setMovementMethod(LinkMovementMethod.getInstance());
        manuals[2].setMovementMethod(LinkMovementMethod.getInstance());
        manuals[3].setMovementMethod(LinkMovementMethod.getInstance());
        manuals[4].setMovementMethod(LinkMovementMethod.getInstance());
        manuals[5].setMovementMethod(LinkMovementMethod.getInstance());

        Intent intent = getIntent();
        option = intent.getIntExtra("option", 0);

        size = new Point();
        display = getWindowManager().getDefaultDisplay();
        metrics = getResources().getDisplayMetrics();
        float densityDpi = (metrics.densityDpi);
        float density = metrics.density;
        display.getSize(size);
        displayDetailsText = "Width: " + size.x + "\nHeight: " + size.y + "\nDensity: " + densityDpi + " (" + density + ")";
        display.getRealSize(size);
        displayDetailsText += "\nReal Width: " + size.x + "\nReal Height: " + size.y;

        showOption(option);
    }

}