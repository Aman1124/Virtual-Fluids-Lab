package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListView extends AppCompatActivity {

    TextView introduction;
    TextView aboutSetup;
    TextView procedure;
    TextView simulation;
    TextView observationTable;
    TextView selfAssessment;
    RelativeLayout listLayout;

    ImageView bernoulli;
    ImageView vNotch;
    ImageView metaCenter;

    TextView[] textViews;

    int[] locationOfBernoulli = new int[2];
    int[] locationOfVNotch = new int[2];

    int scroll=2;
    float displacement;
    private float x1,x2,y1,y2;
    static final int MIN_DISTANCE = 150;


    public void switchToBernoulli(View view){
        Intent intent = new Intent(getApplicationContext(),Bernoulli.class);
        startActivity(intent);
    }

    public void switchToVnotch(View view){

    }

    public void switchToMetacenter(View view){

    }

    public void scrollFloatBox(boolean direction){
        if(direction && (scroll == 2 || scroll == 3)) {
            bernoulli.animate().translationXBy(displacement).setDuration(250);
            vNotch.animate().translationXBy(displacement).setDuration(250);
            metaCenter.animate().translationXBy(displacement).setDuration(250);
            if(scroll>1)
                scroll -= 1;
        }
        else if(!direction && (scroll == 2 || scroll == 1)){
            bernoulli.animate().translationXBy(-displacement).setDuration(250);
            vNotch.animate().translationXBy(-displacement).setDuration(250);
            metaCenter.animate().translationXBy(-displacement).setDuration(250);
            if(scroll < 3)
                scroll += 1;
        }
    }

    public void getCoordinate(){
        bernoulli.getLocationInWindow(locationOfBernoulli);
        vNotch.getLocationInWindow(locationOfVNotch);
        displacement = locationOfBernoulli[0]-locationOfVNotch[0];
        //Toast.makeText(this,"X: " + locationOfBernoulli[0] + "  X: " + locationOfVNotch[0] + "\nY: " + locationOfBernoulli[1] + "  Y: " + locationOfVNotch[1],Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        introduction = findViewById(R.id.introduction);
        aboutSetup = findViewById(R.id.aboutSetup);
        procedure = findViewById(R.id.procedure);
        simulation = findViewById(R.id.simulation);

        bernoulli = findViewById(R.id.bernoulliFloat);
        vNotch = findViewById(R.id.vNotch);
        metaCenter = findViewById(R.id.metaCenter);

        listLayout = findViewById(R.id.listLayout);

        textViews = new TextView[]{introduction, aboutSetup, procedure, simulation, observationTable, selfAssessment};

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;
                getCoordinate();
                if (Math.abs(deltaX) > MIN_DISTANCE && deltaX > 0)
                {
                    //getCoordinate();
                    scrollFloatBox(true);
                }
                else if (Math.abs(deltaX) > MIN_DISTANCE && deltaX < 0)
                {
                    //getCoordinate();
                    scrollFloatBox(false);
                }
                else if (Math.abs(deltaY) > MIN_DISTANCE)
                {
                    //getCoordinate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
