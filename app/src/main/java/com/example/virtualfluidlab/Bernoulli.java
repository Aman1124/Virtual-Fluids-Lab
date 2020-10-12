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
import android.widget.ImageView;
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

    TextView heading1, heading2, para1, para2, para3;
    TextView flowRateText;

    ImageView labelledDiagram, bernoulliEquation, testSectionData;

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
    String theory2_1 = "For applying Bernoulli’s theorem, the fluid flow should be incompressible, laminar," +
            " non-viscous, steady, and irrotational. Also, there should be no heat transfer and work done in fluid flow." +
            " The Bernoulli’s equation for flow in a duct/channel in a section is given as: \n\n";

    String theory2_2 = "\nwhere, P is the static pressure \n" +
            "             V is the velocity of flow\n" +
            "             Z is the elevation head \n" +
            "(Note: For horizontal duct/channel the elevation head is same for different sections.)\n" +
            "\n" +
            "The above equation is valid for ideal fluid, when we are working with real fluid the losses (i.e. due to viscosity, friction, openings in duct, bending’s in duct, heat transfer etc.)  need to be taken in account for the equation to validate. \n" +
            "Bernoulli’s theorem imparts a mathematical means for understanding the fluid mechanics. It has many real-world practical applications, ranging from the aerodynamics of an airplane, calculating wind load on buildings, designing water supply and sewer networks, venturi meters and estimating seepage through soil, etc. Although the expression for Bernoulli’s theorem is simple, the principle involved in the equation plays vital roles in the technological advancements designed to improve the quality of human life.";

    String noteProcedure = "Note: The below mentioned procedure is for hands on Experiment and many of the steps mentioned below are also followed in simulation part of this virtual lab but not all.";

    String procedureSteps = "1.\tFirst of all, make sure that all on/Off switches given on the panel / setup are at OFF position.\n\n" +
            "2.\tNow close all the valve V1 to V5.\n\n" +
            "3.\tFill the Sump tank with water.\n\n" +
            "4.\tOpen by pass valve V2.\n\n" +
            "5.\tNow Switch ON main power supply and Switch On the pump.\n\n" +
            "6.\tPartially close by pass valve V2, so as to fill overhead tank and wait until overflow occurs in overhead Tank.\n\n" +
            "7.\tControl the flow of water through test section with the help of control valve V1 provided at the end of test section.\n\n" +
            "8.\tMake sure that the water level is maintained in overhead tank i.e. overflow is still occurring. If not partially close the valve V2.\n\n" +
            "9.\tMeasure flow rate with the help of measuring tank provided in setup and a stop watch.\n\n" +
            "10.\tMeasure pressure head (i.e. height of water level in tubes) by piezometer tubes.\n\n" +
            "11.\tRepeat steps 7 to 10 for different flow rates.\n\n" +
            "12.\tWhen Experiment is over Switch off the Pump.\n\n" +
            "13.\tTurn off the power supply and drain the water from all the tanks with the help of drain valves (V3, V4, V5).";

    String expSetup1 = "The present experimental set-up for Bernoulli’s Theorem is self-contained re-circulating unit. The set-up accompanies the sump tank, overhead tank, centrifugal Pump for water circulation. Control valve and by-pass valve is provided to regulate the flow of water in constant head tank. A test section made of Perspex, of varying cross section is provided, which is having converging and diverging section. Piezometer tubes are fitted on this test section at specified points to measure pressure heads. The inlet of the conduit is connected to overhead tank. Discharge through test section can be measured with the help of measuring tank and stop watch. A rough 2D fig of the actual setup is shown below with all the components labelled.";
    String expSetup2 = "The following precautions should be taken while handling the above mentioned setup.\n" +
            "•\tNever run the apparatus if power supply is less than 200 volts and above 230 volts.\n" +
            "•\tAlways use clean water.\n" +
            "•\tKeep apparatus free from dust.\n" +
            "•\tTo avoid unnecessary clogging of components run the pump at least once in fortnight.\n" +
            "•\tWhile performing experiment always maintain the water in overhead tank.\n" +
            "•\tAfter experiment is complete drain the apparatus and Switch Off the power supply.\n" +
            "•\tAvoid parallax error while noting down the reading from tubes.";

    private MotionEvent event;

    public void startSimulation(){
        simulation.setVisibility(View.VISIBLE);
    }

    public void openIntroduction(){
        heading1.setTextSize(30);
        heading2.setTextSize(30);
        heading1.setText("Aim:");
        heading2.setText("Theory:");
        setTitle("Introduction");
        para1.setText(aim);
        para2.setText(theory + theory2_1);
        bernoulliEquation.requestLayout();
        bernoulliEquation.setImageResource(R.drawable.bernoulli_equation);
        bernoulliEquation.getLayoutParams().height = 250;
        para3.setText(theory2_2);
        introduction.setVisibility(View.VISIBLE);
    }

    public void openAboutSetup(){
        setTitle("About Setup");
        heading1.setVisibility(View.INVISIBLE);
        heading2.setVisibility(View.INVISIBLE);
        para1.setText(expSetup1);
        para2.setText(expSetup2);
        labelledDiagram.requestLayout();
        labelledDiagram.setImageResource(R.drawable.bernoulli_labelled);
        labelledDiagram.getLayoutParams().height = 800;
        testSectionData.setImageResource(R.drawable.test_section_data);
        testSectionData.requestLayout();
        testSectionData.getLayoutParams().height = 650;
        introduction.setVisibility(View.VISIBLE);
    }

    public void openProcedure(){
        setTitle("Procedure");
        heading1.setVisibility(View.INVISIBLE);
        heading2.setVisibility(View.INVISIBLE);
        testSectionData.setImageResource(R.drawable.bernoulli_labelled);
        testSectionData.requestLayout();
        testSectionData.getLayoutParams().height = 800;
        para1.setTextSize(15);
        para1.setText(noteProcedure);
        para2.setText(procedureSteps);
        introduction.setVisibility(View.VISIBLE);
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
        para3 = findViewById(R.id.para3);
        heading1 = findViewById(R.id.heading1);
        heading2 = findViewById(R.id.heading2);
        labelledDiagram = findViewById(R.id.labelledDiagram);
        bernoulliEquation = findViewById(R.id.bernoulliEquation);
        testSectionData = findViewById(R.id.testSectionData);

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
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);
        display.getSize(size);
        if(size.y == 2040) {
            crossSection.animate().translationYBy(-27f).setDuration(1);
            waterTubes.animate().translationYBy(-27f).setDuration(1);
        }
        else if(size.y >= 2130 && size.y <=2134){
            crossSection.animate().translationYBy(36f).setDuration(1);
            waterTubes.animate().translationYBy(36f).setDuration(1);
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