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

public class ActionMenu extends AppCompatActivity {

    TextView textView;
    LinearLayout waterTubes;
    ProgressBar crossSection;

    String about = "About will be updated soon.";
    String references = "Will be updated.";
    String help = "Will contact you soon.";
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
                break;
            case 2:
                setTitle("References");
                textView.setText(references);
                break;
            case 3:
                setTitle("Display Details");
                textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                textView.setText(displayDetailsText);
                break;
            case 4:
                setTitle("Help");
                textView.setText(help);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_menu);

        textView = findViewById(R.id.expanded_menu);
        waterTubes = findViewById(R.id.waterTubes);
        crossSection = findViewById(R.id.crossSection);

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