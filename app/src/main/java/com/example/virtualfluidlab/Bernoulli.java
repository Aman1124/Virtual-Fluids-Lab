package com.example.virtualfluidlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.TooManyListenersException;


public class Bernoulli extends AppCompatActivity {

    ConstraintLayout simulation;
    LinearLayout waterTubes;
    TableLayout table;

    ScrollView introduction;

    ProgressBar tube1,tube2,tube3,tube4,tube5,tube6,tube7,crossSection;
    SeekBar flowRateSeekBar;

    TextView height1, height2, height3, height4, height5, height6, height7;
    TextView heading1, heading2, para1, para2, para3;
    TextView flowRateText, observationCount;

    ImageView labelledDiagram, bernoulliEquation, testSectionData;
    ImageView experimentSetup;

    Button saveButton, deleteButton, resetButton;

    Point size;
    Display display;

    SQLiteDatabase observationDatabase;
    SharedPreferences sharedPreferences;

    float x1,y1,x2,y2;
    double flowRate;
    float[] tubes = new float[7];
    int choice, setupId = 2, obsCount = 0, dataSNo = 0;
    boolean seekBarVisibility = false, tableStatus = false, firstTime = true;

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

    public void startSimulation(){
        setTitle("Simulation");
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

    public void openObservation(){
        setTitle("Observation Table");
        try{
            Cursor c = observationDatabase.rawQuery("SELECT * FROM readings", null);
            c.moveToFirst();
            int index = c.getColumnIndex("sn");
            float d = c.getInt(index);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            resetButton.setVisibility(View.VISIBLE);
        }catch(Exception e){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error 404")
                    .setMessage("No readings taken")
                    .show();
        }
    }

    public void setTubesLevel() {
        tube1.setProgress((int) Math.round(tubes[0]*2.5));
        tube2.setProgress((int) Math.round(tubes[1]*2.5));
        tube3.setProgress((int) Math.round(tubes[2]*2.5));
        tube4.setProgress((int) Math.round(tubes[3]*2.5));
        tube5.setProgress((int) Math.round(tubes[4]*2.5));
        tube6.setProgress((int) Math.round(tubes[5]*2.5));
        tube7.setProgress((int) Math.round(tubes[6]*2.5));
    }

    public void setHeightsData() {
        flowRateText.setText(String.format(Locale.US, "%s = %.3f", "Q", flowRate*1000));
        height1.setText(String.format(Locale.US, "%.1f", tubes[0]));
        height2.setText(String.format(Locale.US, "%.1f", tubes[1]));
        height3.setText(String.format(Locale.US, "%.1f", tubes[2]));
        height4.setText(String.format(Locale.US, "%.1f", tubes[3]));
        height5.setText(String.format(Locale.US, "%.1f", tubes[4]));
        height6.setText(String.format(Locale.US, "%.1f", tubes[5]));
        height7.setText(String.format(Locale.US, "%.1f", tubes[6]));
    }

    public void startPump(View view) {
        if (setupId == 2) {
            experimentSetup.setImageResource(R.drawable.bernoullisetup_1);
            int csProgress = crossSection.getProgress();
            ObjectAnimator animator = ObjectAnimator.ofInt(crossSection, "progress", 0, 100);
            animator.setDuration(200);
            animator.setInterpolator(new LinearInterpolator());
            if(csProgress == 0)
                animator.start();
            if (firstTime){
                generateTubesHeight(0);
                setTubesLevel();
                setHeightsData();
                firstTime = false;
            }
            setupId = 1;
        } else {
            experimentSetup.setImageResource(R.drawable.bernoullisetup_2);
            setupId = 2;
        }
    }

    public void changeFlowRate(View view) {
        if (setupId == 1 && !seekBarVisibility) {
            flowRateSeekBar.setVisibility(View.VISIBLE);
            seekBarVisibility = true;
            flowRateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    flowRate = (progress*0.000003 + 0.0002);
                    generateTubesHeight(flowRate);
                    setTubesLevel();
                    setHeightsData();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    flowRateSeekBar.setVisibility(View.INVISIBLE);
                    seekBarVisibility = false;
                }
            });
        }
        else if (seekBarVisibility){
            flowRateSeekBar.setVisibility(View.INVISIBLE);
            seekBarVisibility = false;
        }
    }

    public void saveData(View view){
        int tag = Integer.parseInt(view.getTag().toString());
        Log.i("Button Tag", String.valueOf(tag));
        if (tag == 1){
            simulation.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            if (obsCount < 10){
                obsCount += 1; dataSNo += 1;
                observationDatabase.execSQL("INSERT INTO readings (sn, flowRate, h1, h2, h3, h4, h5, h6, h7) VALUES (" +
                        String.format(Locale.US, "%d, %.6f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f)",
                                dataSNo, flowRate, tubes[0], tubes[1], tubes[2], tubes[3], tubes[4], tubes[5], tubes[6]));
            }
        }
        else if (tag == 0 && obsCount > 0){
            observationDatabase.execSQL("DELETE FROM readings WHERE sn = " + (dataSNo));
            dataSNo -= 1; obsCount -= 1;
        }
        else if (tag == 2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else if (tag == -1){

        }

        observationCount.setText(String.format("%s of 10", obsCount));
        sharedPreferences.edit().putInt("serialNo", obsCount).apply();

        if (obsCount == 10) {
            saveButton.setText("Proceed");
            saveButton.setTag("2");
            deleteButton.setText("Reset");
            deleteButton.setTag("-1");
        }
        else {
            saveButton.setText("Save");
            saveButton.setTag("1");
        }
    }

    public void createObservationTable(){
        tableStatus = true;
        Cursor c = observationDatabase.rawQuery("SELECT * FROM readings", null);
        c.moveToFirst();
        int flowRateIndex = c.getColumnIndex("flowRate");
        int[] hIndex = new int[7]; int sno;
        sno = c.getColumnIndex("sn");
        hIndex[0] = c.getColumnIndex("h1");
        hIndex[1] = c.getColumnIndex("h2");
        hIndex[2] = c.getColumnIndex("h3");
        hIndex[3] = c.getColumnIndex("h4");
        hIndex[4] = c.getColumnIndex("h5");
        hIndex[5] = c.getColumnIndex("h6");
        hIndex[6] = c.getColumnIndex("h7");
        try {
            for (int i = 0; i < 10; i++) {
                TableRow row = new TableRow(Bernoulli.this);
                //row.setId(1 + i);
                for (int j = 0; j < 9; j++) {
                    float d;
                    if (j == 0)
                        d = (float) c.getInt(sno);
                    else if (j == 1)
                        d = c.getFloat(flowRateIndex);
                    else
                        d = c.getFloat(hIndex[j - 2]);
                    TextView tv = new TextView(Bernoulli.this);
                    tv.setTextSize(15);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tv.setText(String.valueOf(d));
                    row.addView(tv);
                }
                table.addView(row);
                c.moveToNext();
            }
        } catch (Exception e) {
            Log.i("DATA", "No more data found");
        }
    }

    public void deleteObservationTable(View view){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete observation table ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        observationDatabase.execSQL("DELETE FROM readings");
                    }
                })
                .setNegativeButton("No", null)
                .show();
        sharedPreferences.edit().putInt("serialNo", 1).apply();
        obsCount = 0;
        dataSNo = 1;

        observationCount.setText(String.format("%s of 10", obsCount));
        sharedPreferences.edit().putInt("serialNo", obsCount).apply();
    }

    public void generateTubesHeight(double Q){
        //double Qr = 0.0005, Ql = 0.0002;

        //double[] hr = new double[]{12.8, 13.2, 17.4, 78.5, 39.5, 33.6, 30.8};
        //double[] hl = new double[]{0.2, 0.5, 3.3, 5.4, 6.5, 6.0, 5.0};

        double[] A = new double[]{0.000616, 0.000452, 0.000314, 0.000201, 0.000314, 0.000452, 0.000616};

        double[] m = new double[]{42877, 44690, 420.07, 15389, 130914, 111810, 94121};
        double[] m1 = new double[]{42877, 44690, 70815, 412177, 130914, 111810, 94121};

        double[] c = new double[]{7.2104, 7.8178, -3.2277, -2.5909, 20.249, 16.52, 12.49};
        double[] c1 = new double[]{7.2104, 7.8178, 17.94, 120.15, 20.249, 16.52, 12.49};

        double[] H = new double[7];
        double[] h = new double[7];

        if(Q == 0) {
            for (int i=0;i<7;i++)
                tubes[i] = (float) 34;
        }
        else{
            for(int i = 0; i<7 ; i++){
                if( Q < 0.0003)
                    h[i] = m[i]*Q - c[i];
                else
                    h[i] = m1[i]*Q - c1[i];
                H[i] = 34 - (100*Q*Q)/(19.62*A[i]*A[i]);
                H[i] = H[i]*(1-h[i]/100);
                tubes[i] = (float) (H[i]);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(setupId == 1)
            Toast.makeText(this, "Turn OFF the power", Toast.LENGTH_SHORT).show();
        else
            super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        setupId = 2;
        experimentSetup.setImageResource(R.drawable.bernoullisetup_2);
        saveButton.setTag("1");
        if (!tableStatus)
            createObservationTable();
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            table.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            simulation.setVisibility(View.INVISIBLE);
        }
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            table.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.INVISIBLE);
            simulation.setVisibility(View.VISIBLE);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bernoulli);

        simulation = findViewById(R.id.simulation);
        introduction = findViewById(R.id.introduction);
        waterTubes = findViewById(R.id.waterTubes);
        experimentSetup = findViewById(R.id.experimentSetup);

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

        height1 = findViewById(R.id.height1);
        height2 = findViewById(R.id.height2);
        height3 = findViewById(R.id.height3);
        height4 = findViewById(R.id.height4);
        height5 = findViewById(R.id.height5);
        height6 = findViewById(R.id.height6);
        height7 = findViewById(R.id.height7);
        observationCount = findViewById(R.id.observationCount);

        flowRateText = findViewById(R.id.flowRate);
        flowRateSeekBar = findViewById(R.id.flowRateSeekBar);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        resetButton = findViewById(R.id.resetButton);

        table = findViewById(R.id.observationTable);

        try{
            observationDatabase = this.openOrCreateDatabase("Readings", MODE_PRIVATE, null);
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS readings ( sn INT(2), flowRate FLOAT, h1 FLOAT, h2 FLOAT, h3 FLOAT, h4 FLOAT, h5 FLOAT, h6 FLOAT, h7 FLOAT )");
        }catch (Exception e){
            e.printStackTrace();
        }

        sharedPreferences = this.getSharedPreferences("com.example.virtualfluidlab", Context.MODE_PRIVATE);
        dataSNo = sharedPreferences.getInt("serialNo", 0);
        obsCount = dataSNo;
        observationCount.setText(String.format("%s of 10", obsCount));

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
            case 5:
                openObservation();
                break;
            default:
                break;
        }

        size = new Point();
        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
    }

}