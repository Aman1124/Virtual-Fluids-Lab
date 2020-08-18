package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Bernoulli extends AppCompatActivity {

    ConstraintLayout simulation;
    LinearLayout introduction;
    LinearLayout waterTubes;
    ProgressBar tube1,tube2,tube3,tube4,tube5,tube6,tube7,crossSection;

    TextView para1, para2;
    TextView flowRateText;

    Point size;
    Display display;

    float x1,y1,x2,y2;
    float flowRate;
    int[] tubes = new int[7];
    int choice;

    String aim = "•\tTo calculate Total Energy at different points of venturi\n" +
            "•\tTo plot the graph between Total Energy, Pressure Energy, Velocity Energy With respect to Distance";
    String theory = "Energy presents in the form of pressure, velocity, and elevation in fluids with no energy exchange due to viscous dissipation, heat transfer, or shaft work (pump or some other device). The relationship among these three forms of energy was first stated by Daniel Bernoulli (1700-1782), based upon the conservation of energy principle.\n" +
            "Bernoulli’s theorem state that when there is a continuous connection between particles of flowing mass of liquid, the total energy at any section of flow will remain the same provided there is no reduction or addition of energy at any point.\n" +
            "This is the energy equation and is based on the law of conservation of energy. This equation states that at two sections of flow field the total energy remains the same. Provided that there is no loss or gain of energy between the two sections.\n";
    String theory2 = "For applying Bernoulli’s theorem, the fluid flow should be incompressible, laminar, non-viscous, steady, and irrotational. Also, there should be no heat transfer and work done in fluid flow. The Bernoulli’s equation for flow in a duct/channel in a section is given as: \n" +
            "           \n" +
            "     P1 + V12 + Z1 = = P2 + V22 + Z2 \n" +
            "     ρg    2g                ρg    2g\n" +
            "\n" +
            "Where P is the static pressure \n" +
            "             V is the velocity of flow\n" +
            "             Z is the elevation head \n" +
            "(Note: For horizontal duct/channel the elevation head is same for different sections.)\n" +
            "\n" +
            "The above equation is valid for ideal fluid, when we are working with real fluid the losses (i.e. due to viscosity, friction, openings in duct, bending’s in duct, heat transfer etc.)  need to be taken in account for the equation to validate. \n" +
            "Bernoulli’s theorem imparts a mathematical means for understanding the fluid mechanics. It has many real-world practical applications, ranging from the aerodynamics of an airplane, calculating wind load on buildings, designing water supply and sewer networks, venturi meters and estimating seepage through soil, etc. Although the expression for Bernoulli’s theorem is simple, the principle involved in the equation plays vital roles in the technological advancements designed to improve the quality of human life.\n";
    private MotionEvent event;


    public void startSimulation(){
        simulation.setVisibility(View.VISIBLE);
    }

    public void openIntroduction(){
        setTitle("Introduction");
        para1.setText(aim);
        para2.setText(theory + theory2);
        introduction.setVisibility(View.VISIBLE);
    }

    public void openAboutSetup(){

    }

    public void openProcedure(){

    }

    public void setTubesLevel(){
        tube1.setProgress(tubes[0]);
        tube2.setProgress(tubes[1]);
        tube3.setProgress(tubes[2]);
        tube4.setProgress(tubes[3]);
        tube5.setProgress(tubes[4]);
        tube6.setProgress(tubes[5]);
        tube7.setProgress(tubes[6]);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bernoulli);

        simulation = findViewById(R.id.simulation);
        introduction = findViewById(R.id.introduction);
        waterTubes = findViewById(R.id.waterTubes);

        para1 = findViewById(R.id.para1);
        para2 = findViewById(R.id.para2);

        tube1 = findViewById(R.id.tube1);
        tube2 = findViewById(R.id.tube2);
        tube3 = findViewById(R.id.tube3);
        tube4 = findViewById(R.id.tube4);
        tube5 = findViewById(R.id.tube5);
        tube6 = findViewById(R.id.tube6);
        tube7 = findViewById(R.id.tube7);
        crossSection = findViewById(R.id.crossSection);

        flowRateText = findViewById(R.id.flowRate);

        Intent intent = getIntent();
        choice = intent.getIntExtra("choice",0);

        switch (choice){
            case 1:
                openIntroduction();
                break;
            case 2:
                openAboutSetup();
                break;
            case 3:
                openProcedure();
                break;
            case 4:
                startSimulation();
                break;
            default:
                break;
        }

        size = new Point();
        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        Toast.makeText(this,size.x + "x" + size.y, Toast.LENGTH_LONG).show();
        if(size.y == 2040) {
            crossSection.animate().translationYBy(-27f).setDuration(1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float deltaX = x2 -x1;
                if(deltaX > 0 && (y2 > 960 || y1 > 960) && Math.abs(deltaX) > 150){
                    flowRate += deltaX/100;
                    if (tubes[0] <= 100){
                        tubes[0] += 7;
                        tubes[1] += 5;
                        tubes[2] += 3;
                        tubes[3] += 1;
                        tubes[4] = tubes[2];
                        tubes[5] = tubes[1];
                        tubes[6] = tubes[0];
                        flowRateText.setText(Float.toString(flowRate));
                        if(tubes[0] >= 100)
                            flowRateText.setText("!!WARNING!!\nTubes will overflow");
                    }
                    setTubesLevel();
                }
                if(deltaX < 0 && (y2 > 960 || y1 > 960) && Math.abs(deltaX) > 150){
                    flowRate += deltaX/100;
                    if (tubes[0] > 0){
                        tubes[0] -= 7;
                        tubes[1] -= 5;
                        tubes[2] -= 3;
                        tubes[3] -= 1;
                        tubes[4] = tubes[2];
                        tubes[5] = tubes[1];
                        tubes[6] = tubes[0];
                        flowRateText.setText(Float.toString(flowRate));
                        if(tubes[0] <= 0)
                            flowRateText.setText("!!WARNING!!\nFlow rate very small");
                    }
                    setTubesLevel();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}