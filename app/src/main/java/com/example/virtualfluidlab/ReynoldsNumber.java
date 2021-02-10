package com.example.virtualfluidlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VerticalSeekBar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.virtualfluidlab.view.MathJaxWebView;

import java.util.Locale;
import java.util.Random;

public class ReynoldsNumber extends AppCompatActivity {

    int choice;
    ScrollView introductionView, aboutSetupView, procedureView;
    ConstraintLayout simulationView;
    TextView rey_aimPara, rey_theoryPara1, rey_theoryPara2;
    TextView rey_aboutSetup, rey_procedure1, rey_procedure2;
    MathJaxWebView rey_theoryFormula;
    ProgressBar narrowTube, exitTap;
    ImageView apparatus, water2dye;
    VerticalSeekBar flowRateSeekBar;
    LottieAnimationView animationView;

    TextView simR1, simR2, simTime, obsNote;

    Boolean powerOn = false, flowExit = false, firstTime = true;
    float density;
    int flowRate = 0;
    int r1 = 0, r2 = 0, time = 0;
    ConstraintLayout.LayoutParams dyeParams;

    SQLiteDatabase observationDatabase;
    SharedPreferences sharedPreferences;
    int dataSNo = 0, obsCount = 0;
    TableLayout obsTable;
    Button saveButton, delButton;
    TextView observationCount;
    ConstraintLayout rey_observation;

    String aim = "\t•\tTo identify the laminar, transition, and turbulent flow regimes in pipe flow using Reynolds experiment.";

    String theoryPara1 = "For a particular fluid flow, depending on the velocity, the flow shows the laminar, transition and turbulent patterns. In laminar flow, the fluid flows in a layer without disturbing the other layer, whereas in turbulent flow the fluid does not follow any regular layer and the flow is highly chaotic. The flow shows the randomness of turbulent flow due to elevated dissipation. The flow exhibits intermittent behavior in the transition regime, sometimes laminar and sometimes turbulent. You can compare the essence of the flow with a non-dimensional number called the Reynolds number. \n" +
            "For the flow classification as laminar, transition or turbulent, Osborne Reynolds (1842–1912 AD) suggested an extremely useful non-dimensional number, which was named after him as the Reynolds number (Re). Reynolds number is the ratio of the inertial force of flowing fluid to the viscous force of the fluid. \n" +
            "For the flow through pipe, the following equation is used to calculate the Reynolds number:";

    String theoryPara2 = "here, ρ is the density, V is the average flow velocity in the pipe, D is the pipe diameter and μ is the dynamic viscosity. \n" +
            "\nThe flow transits from laminar to turbulent for a specific fluid, i.e. for continuous viscosity, as the flow velocity is increased. The Reynolds number at which the flow begins to transit from laminar is called the critical number of Reynolds. The value of critical Reynolds number for the flow through pipe is usually taken as 2300. The following classification of flow regimes were given by Reynolds based on the experimental results:\n";

    String theoryFormula = "<p align=\"justify\" style = \"font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n" +
            "\\[R_e = {\\rho V D \\over \\mu}\\]" +
            "</p>";

    String aboutSetup = "The experimental set-up has a glass tube, dye cup and injector assembly, supply tank, and flow measuring tank. The apparatus consists of a glass tube with one end having bell mouth entrance connected to a constant head tank, a centrifugal pump and a sump tank. A needle is introduced centrally in the bell mouth. Dye is injected from the needle to the flow to observe the streamline of the flow. Dye is fed to the needle from a small container, placed at the top of constant head tank, through polythene tubing. A valve is provided at the other end of the glass tube to regulate the flow. Flow rate of the water is measured with the help of a measuring cylinder and stop watch.\n" +
            "\nSpecifications of setup:\n" +
            "Diameter of pipe section(d) = 0.01 m\n" +
            "Length of pipe section(L) = 0.74 m\n";

    String procedure1 = "1.)\tClose the drainage valve of the constant head tank if it is in open position. \n" +
            "\n2.)\tSwitch ON the main power supply and the pump. \n" +
            "\n3.)\tOpen the control valve of water supply to constant head tank and partially close the bypass valve. Wait till overflow occurs. \n" +
            "\n4.)\tBy partially opening the control valve provided at the end of the tube, the minimum flow of water through the glass tube is regulated. \n" +
            "\n5.)\tChange the flow of dye by the flow adjustment arrangement through the needle such that a fine color thread is observed.\n" +
            "\n6.)\tNote down the flow pattern observed (laminar, transition or turbulent). \n" +
            "\n7.)\tMeasure the flow rate using measuring cylinder, stop watch. Determine the flow velocity and corresponding Reynolds number. \n" +
            "\n8.)\tRepeat the steps 6 and 7 for different flow rates by adjusting the control valve. \n" +
            "\n9.)\tSwitch OFF the pump and drain the apparatus completely once the experiment is over.\n";

    String procedure2 = "1. Click on the Power box to turn on.\n" +
            "2. Once powered up a slider will appear over the valve.\n" +
            "3. Move the slider to regulate the flow of water through the glass tube.\n" +
            "4. The flow can be observed in the magnified view of the glass tube below the apparatus.\n" +
            "5. Press the save button to save the readings.\n" +
            "6. Take 10 readings.\n" +
            "7. To access the readings close the simulation and press the Observation button.\n" +
            "8. Make sure to turn off the power before leaving the simulation.";

    String observationNote = "For water at 20°C:\nDynamic viscosity, µ/ρ = 9.554e-06 m²/sec\n" +
            "Diameter, D = 0.01m";

    public void openIntroduction(){
        setTitle("Introduction");
        rey_aimPara.setText(aim);
        rey_theoryPara1.setText(theoryPara1);
        rey_theoryFormula.setText(theoryFormula);
        rey_theoryPara2.setText(theoryPara2);
        introductionView.setVisibility(View.VISIBLE);
    }

    public void openAboutSetup(){
        setTitle("About Setup");
        rey_aboutSetup.setText(aboutSetup);
        aboutSetupView.setVisibility(View.VISIBLE);
    }

    public void openProcedure(){
        setTitle("Procedure");
        rey_procedure1.setText(procedure1);
        rey_procedure2.setText(procedure2);
        procedureView.setVisibility(View.VISIBLE);
    }

    public void openObservation(){
        setTitle("Observation");
        createObsTable();
        obsNote.setText(observationNote);
        try{
            Cursor c = observationDatabase.rawQuery("SELECT * FROM reynoldsnumber", null);
            c.moveToFirst();
            int index = c.getColumnIndex("sn");
            float d = c.getInt(index);
            c.close();
            rey_observation.setVisibility(View.VISIBLE);
        }catch(Exception e){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error 404")
                    .setMessage("No readings taken")
                    .show();
        }
    }

    public void startSimulation(){
        setTitle("Simulation");
        simulationView.setVisibility(View.VISIBLE);
    }

    public void powerOnOff(View view){
        powerOn = !powerOn;
        if(powerOn){
            apparatus.setImageResource(R.drawable.reynolds_apparatus_on);
            flowRateSeekBar.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofInt(narrowTube, "progress", 0, 100);
            animator.setDuration(200);
            animator.setInterpolator(new LinearInterpolator());
            if(narrowTube.getProgress() == 0)
                animator.start();
        }
        else{
            apparatus.setImageResource(R.drawable.reynolds_apparatus);
            flowRateSeekBar.setVisibility(View.INVISIBLE);
        }
    }

    public void setSimulationData(){
        Random random = new Random();
        r1 = 5 + random.nextInt(11);
        time = 10 + random.nextInt(6);
        r2 = r1 + flowRate*time;
        simR1.setText(String.valueOf(r1));
        simR2.setText(String.valueOf(r2));
        simTime.setText(String.valueOf(time));
    }

    public void saveDelData(View view){
        int tag = Integer.parseInt(view.getTag().toString());
        Log.i("Button Tag", String.valueOf(tag));
        if (tag == 1){
            if (obsCount < 10){
                obsCount += 1; dataSNo += 1;
                observationDatabase.execSQL("INSERT INTO reynoldsnumber (sn, r1, r2, time) VALUES (" +
                        String.format(Locale.US, "%d, %d, %d, %d)",
                                dataSNo, r1, r2, time));
            }
        }
        else if (tag == 0 && obsCount > 0){
            observationDatabase.execSQL("DELETE FROM reynoldsnumber WHERE sn = " + (dataSNo));
            dataSNo -= 1; obsCount -= 1;
        }
        else if (tag == 2) {
            simulationView.setVisibility(View.INVISIBLE);
            powerOn = false;
            openObservation();
        }

        if (obsCount == 10){
            saveButton.setText("Proceed");
            saveButton.setTag("2");
        }
        else{
            saveButton.setText("Save");
            saveButton.setTag("1");
        }

        observationCount.setText(String.format("%s of 10", obsCount));
        sharedPreferences.edit().putInt("serialNo", obsCount).apply();
    }

    public void createObsTable(){
        Cursor c = observationDatabase.rawQuery("SELECT * FROM reynoldsnumber", null);
        c.moveToFirst();
        int sNo = c.getColumnIndex("sn");
        int r1Index = c.getColumnIndex("r1");
        int r2Index = c.getColumnIndex("r2");
        int timeIndex = c.getColumnIndex("time");
        try {
            for (int i = 0; i < 10; i++) {
                TableRow row = new TableRow(ReynoldsNumber.this);
                //row.setId(1 + i);
                for (int j = 0; j < 4; j++) {
                    int d;
                    if (j == 0)
                        d = c.getInt(sNo);
                    else if (j == 1)
                        d = c.getInt(r1Index);
                    else if (j==2)
                        d = c.getInt(r2Index);
                    else
                        d = c.getInt(timeIndex);
                    TextView tv = new TextView(ReynoldsNumber.this);
                    tv.setTextSize(17);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tv.setText(String.valueOf(d));
                    tv.setGravity(Gravity.CENTER);
                    row.addView(tv);
                }
                obsTable.addView(row);
                c.moveToNext();
            }
        } catch (Exception e) {
            Log.i("DATA", "No more data found");
        }
        c.close();
    }

    public void resetObsTable(View view){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete observation table ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        observationDatabase.execSQL("DELETE FROM reynoldsnumber");
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

    @Override
    public void onBackPressed() {
        if(!powerOn)
            super.onBackPressed();
        else
            Toast.makeText(this, "Please turn off the power", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reynolds_number);

        introductionView = findViewById(R.id.reynolds_introduction);
        rey_aimPara = findViewById(R.id.reynolds_aimPara);
        rey_theoryPara1 = findViewById(R.id.reynolds_theoryPara1);
        rey_theoryPara2 = findViewById(R.id.reynolds_theoryPara2);
        rey_theoryFormula = findViewById(R.id.reynolds_theory_formula);

        aboutSetupView = findViewById(R.id.reynolds_aboutSetup);
        rey_aboutSetup = findViewById(R.id.reynolds_aboutSetupPara);

        procedureView = findViewById(R.id.reynolds_procedure);
        rey_procedure1 = findViewById(R.id.reynolds_procedurePara1);
        rey_procedure2 = findViewById(R.id.reynolds_procedurePara2);

        simulationView = findViewById(R.id.reynolds_simulation);
        narrowTube = findViewById(R.id.reynolds_narrowTube);
        exitTap = findViewById(R.id.reynolds_exitTap);
        apparatus = findViewById(R.id.reynolds_apparatus);
        flowRateSeekBar = findViewById(R.id.reynolds_flowRate_seekBar);
        flowRateSeekBar.setMax(50);
        animationView = findViewById(R.id.reynolds_flow_animation);
        water2dye = findViewById(R.id.reynolds_water_to_dye);
        simR1 = findViewById(R.id.reynolds_simR1);
        simR2 = findViewById(R.id.reynolds_simR2);
        simTime = findViewById(R.id.reynolds_simTime);
        dyeParams = (ConstraintLayout.LayoutParams) water2dye.getLayoutParams();
        obsNote = findViewById(R.id.reynolds_obsNote);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        density = metrics.density;


        rey_observation = findViewById(R.id.reynolds_observation);
        saveButton = findViewById(R.id.reynolds_saveButton);
        delButton = findViewById(R.id.reynolds_deleteButton);
        observationCount = findViewById(R.id.reynolds_obsCount);
        try{
            observationDatabase = this.openOrCreateDatabase("Observation", MODE_PRIVATE, null);
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS reynoldsnumber ( sn INT(2), r1 INT(2), r2 INT(2), time INT(2) )");
        }catch (Exception e){
            e.printStackTrace();
        }
        sharedPreferences = this.getSharedPreferences("com.example.virtualfluidlab.reynolds", Context.MODE_PRIVATE);
        dataSNo = sharedPreferences.getInt("serialNo", 0);
        obsCount = dataSNo;
        observationCount.setText(String.format("%s of 10", obsCount));
        obsTable = findViewById(R.id.cop_observationTable);


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

        flowRateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                flowRate = progress + 10;
                flowExit = progress != 0;
                if(flowExit && firstTime){
                    ObjectAnimator animator = ObjectAnimator.ofInt(exitTap, "progress", 0, 100);
                    animator.setDuration(200);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.start();
                    firstTime = false;
                }
                else if (progress == 0){
                    ObjectAnimator animator = ObjectAnimator.ofInt(exitTap, "progress", 100, 0);
                    animator.setDuration(200);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.start();
                    firstTime = true;
                }
                if(progress > 5)
                    animationView.setProgress((progress/5f - 1)/8f);
                else
                    animationView.setProgress(0f);
                dyeParams.leftMargin = (int)((-8*progress + 360)*density);
                water2dye.setLayoutParams(dyeParams);
                setSimulationData();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}