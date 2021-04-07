package com.example.virtualfluidlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VerticalSeekBar;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.virtualfluidlab.view.MathJaxWebView;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class Pitot_Tube extends AppCompatActivity {

    TextView heading1, heading2, para1, para2, para3;
    TextView flowRateText, static_pressText, dynamic_pressText;
    TextView needleHeightTextView;
    TextView expModeText;
    TextView pinHeightTV, flowRateTV, staticHTV, dynamicHTV, observationCount, obsFlowRateTV;

    ImageView pitot_labelledDiagram, pitot_Equation, pitot_testSectionData, pitot_apparatus;
    ImageView pitot_test_section_pin;

    Button saveButton;

    SeekBar flowRateSeekBar;
    VerticalSeekBar needleHeightSeekBar;
    ProgressBar static_press_bar, dynamic_press_bar;

    ScrollView introduction;
    ConstraintLayout simulation, testSection_popUp, observation;
    LinearLayout velProfile;
    TableLayout coeff_of_vel, obsTableCV, obsTableVP;

    ConstraintLayout.LayoutParams pinParams;
    DisplayMetrics metrics;

    MathJaxWebView pitot_webView;

    int choice;
    float density;
    int pin_height = 0;
    float[] height = new float[2];
    double flowRate;
    double[] spd_height = new double[11];
    double[] percentageError = new double[11];
    double[] staticH = new double[11];
    boolean flowBar_visibility = false, power = false, ts_popUp_visibility = false, vel_profile = false;

    SQLiteDatabase observationDatabase;
    SharedPreferences sharedPreferencesCV, sharedPreferencesVP;
    int dataSNo = 0, obsCount = 0;

    String aim = "•\tTo find the point velocity at center of a tube for different flow rate.\n" +
            "•\tTo find the coefficient of pitot tube.\n" +
            "•\tTo plot the velocity profile across the cross-section of pipe.";

    String theory = "It is a device used for measuring the velocity of flow at any point in a pipe. It is based on the principle that if the velocity of flow at a point becomes zero, there is increase in pressure due to the conversion of the kinetic energy into pressure energy. The Pitot tube consists of a capillary tube, bend at right angle. The lower end is directed in the upstream direction. " +
            "The liquid rises up in the tube due to conversion of kinetic energy into pressure energy. The velocity is determined by measuring the rise of liquid in the tube. Pitot static tube is one of the application of Bernoulli’s Theorem which is valid in regions of steady, incompressible flow where net frictional forces are negligible. With the help of Pitot static tube, we can find the actual velocity at different points of a cross-section. " +
            "However, mean velocity will be same across the cross-section.\n" +
            "Since a Pitot tube measures the stagnation pressure head (or the total head) at its dipped end. The pressure head may be determined directly by connecting a differential manometer between the Pitot tube and pressure taping at the pipe surface. Consider two points 1 and 2 at the same level in such a way that point 1 is at the inlet of the pitot tube and point 2 is at the outlet. " +
            "At point 1 the pressure is p1 and the velocity of the stream is v1. However, at point 2 the fluid is brought to rest and the energy has been converted to pressure energy. Therefore the pressure at 2 is p2, the velocity v2 is zero and since 1 and 2 are in the same horizontal plane, \n" +
            "Applying Bernoulli’s equation at points (1) and (2)\n";

    String theory_formulas = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n" +
            "  \\[{P_1\\over \\rho g}+ {v_1^2 \\over 2g} + Z_1= {P_2\\over \\rho g}+ {v_2^2 \\over 2g} + Z_2\\]\n" +
            "  Since \\(Z_1 = Z_2\\) and \\(v_2 = 0\\) m/s,\n" +
            "  \\[v_1=\\sqrt{2g \\left({P_2 \\over \\rho g}-{P_1 \\over \\rho g} \\right)}\\]\n" +
            "  \\[=\\sqrt{2g(h_2-h_1)}\\]\n" +
            "  Also, for laminar flow, with the help of pipe Poiseuille's equation point velocity is\n" +
            "  \\[v_{1t}= 2v_m(1-({r \\over R})^2)\\]\n" +
            "  For turbulent flow, best approximated point velocity is calculated theoretically as follows:\n" +
            "  \\[v_{1t}= v_m({1-{r \\over R}})^{1 \\over 7}\\]\n" +
            "  To check laminar flow in pipe we use Reynolds number as follows\n" +
            "  \\[R_e = {v_md \\over \\theta}\\]\n" +
            "  To find coefficient of velocity, actual velocity is,\n" +
            "  \\[v_a = {Q_a \\over a}\\]\n" +
            "  \\[C_v = {v_a \\over v_m}\\]\n" +
            "  Where, <br>\n" +
            "  ℎ = \\(h_2 - h_1\\) = Manometric head <br>\n" +
            "  \\(\\rho\\) = Density of fluid <br>\n" +
            "  \uD835\uDF17 = Kinematic viscosity <br>\n" +
            "  \\(\uD835\uDC45_2\\)= Final level of water in measuring tank <br>\n" +
            "  \\(\uD835\uDC45_1\\)= Initial level of water in measuring tank <br>\n" +
            "  A= Area of measuring tank <br>\n" +
            "  a = Cross sectional area at test points <br>\n" +
            "  t = Time taken for R <br>\n" +
            "  g = Acceleration due to gravity <br>\n" +
            "  \\(\uD835\uDC36_\uD835\uDC63\\) = coefficient of velocity <br>\n" +
            "  \\(Q_a\\) = Discharge rate \n" +
            "\n" +
            "</p>\n";

    String procedureSteps1 = "1. Ensure that ON/OFF switch given on the panel is at OFF position.\n" +
            "2. Make sure all the drain valves are closed. \n" +
            "3. Fill sump tank ¾ with clean water and open by-pass valve V2. \n" +
            "4. Switch ON the main power supply and the pump.\n" +
            "5. Open valve V1 and allow water to flow through test section by partially closing valve V2.\n" +
            "6. Open the air release valve V5 provided on the manometer, slowly to release the air from manometer.\n" +
            "7. When no air is observed in the manometer, close the valve V5.\n" +
            "8. Position the Pitot tube at the centre of the test section by adjusting the pointer to zero by knob provided.\n" +
            "9. Adjust water flow rate with the help of control valve V1 and by pass valve V2.\n" +
            "10. Record the manometer reading, in case of pressure above scale in any tube, apply air pressure by hand pump to get readable reading.\n" +
            "11. Measure the discharge flow rate using stop watch and measuring tank.\n" +
            "12. For different positions of pitot tube (change by knob), Record the manometer reading for particular discharge to determine velocity profile.\n" +
            "13. Vary the flow rates of water by operating control valve V1 and by-pass valve V2 and repeat the experiment.\n" +
            "14. Turn OFF the pump and power supply. \n" +
            "15. Drain the apparatus using valve V3 and V4.\n";

    String procedureSteps2 = "1. Switch on the power supply by clicking on the power control box.\n" +
            "2. Get desired flow rate by clicking on the valve and then adjusting the seekbar. \n" +
            "3. Click on the knob. In the pop-up window, adjust the seekbar for getting the desired vertical position of the pointer.\n" +
            "4. Click anywhere on the screen to close the pop-up window" +
            "5. Record the readings in both arms of the manometer by clicking on the \"Save\" button.\n" +
            "6. Keep repeating step 3 and recording the reading.\n" +
            "7. Once done, turn of the power. Go back and then click on \"Observation Table\" to see the saved readings\n";

    String expSetup1 = "The apparatus consists of sump tank with centrifugal pump. A pitot tube made of copper" +
            "provided in the test section made of acrylic connected to pipeline with flow control valve." +
            "The pointer gauge is provided to measure the vertical position of pitot tube in test" +
            "section. A manometer is provided to determine the pressure difference. Discharge is" +
            "measured with the help of measuring tank and stopwatch. A control valve and by-pass valve is provided to regulate the flow of water.";

    String expSetup2 = "The following precautions have to be followed:\n" +
            "1. Never run the apparatus if power supply is less than 200 Volts and above 230 Volts\n" +
            "2. Never fully close the control valve V1 and by pass valve V2 simultaneously.\n" +
            "3. Always keep apparatus free from dust.\n" +
            "4. If pump gets heated up, switch off the main power for 30 minutes and avoid closing the flow control valve V1 and by-pass valve V2 at a time, during operation.";

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams labelled_params;

    public void openIntroduction() {
        heading1.setTextSize(27);
        heading2.setTextSize(27);
        heading1.setText("Aim:");
        heading2.setText("Theory:");
        setTitle("Introduction");
        para1.setText(aim);
        para2.setText(theory);
        pitot_webView.setLayoutParams(params);
        pitot_webView.setText(theory_formulas);
        introduction.setVisibility(View.VISIBLE);
    }

    public void openAboutSetup() {
        setTitle("About Setup");
        heading1.setVisibility(View.INVISIBLE);
        heading2.setVisibility(View.INVISIBLE);
        para1.setText(expSetup1);
        para2.setText(expSetup2);
        pitot_labelledDiagram.setLayoutParams(labelled_params);
        introduction.setVisibility(View.VISIBLE);
    }

    public void openProcedure() {
        setTitle("Procedure");
        heading1.setTextSize(27);
        heading2.setTextSize(27);
        heading1.setText("Laboratory Procedure:");
        para1.setText(procedureSteps1);
        heading2.setText("Simulation Procedure");
        para2.setText(procedureSteps2);
        introduction.setVisibility(View.VISIBLE);
    }

    public void startSimulation() {
        setTitle("Simulation");
        simulation.setVisibility(View.VISIBLE);
        if(obsCount == 10){
            saveButton.setText("Proceed");
            saveButton.setTag("2");
        }
    }

    public void openObservation() {
        setTitle("Observation");
        createObsTable();
        try{
            Cursor c = observationDatabase.rawQuery("SELECT * FROM pitotcovel", null);
            c.moveToFirst();
            int index = c.getColumnIndex("sn");
            float d = c.getInt(index);
            c.close();
            observation.setVisibility(View.VISIBLE);
        }catch(Exception e){
            try{
                Cursor c = observationDatabase.rawQuery("SELECT * FROM pitotvelpro", null);
                c.moveToFirst();
                int index = c.getColumnIndex("sn");
                float d = c.getInt(index);
                c.close();
                observation.setVisibility(View.VISIBLE);
            }catch (Exception ex) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Error 404")
                        .setMessage("No readings taken")
                        .show();
            }
        }
    }

    public void exportAsExcel(View view){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "VFL");
        if(!file.exists())
            file.mkdirs();
        SQLiteToExcel sqLiteToExcel = new SQLiteToExcel(this, "Observation", file.getAbsolutePath());
        ArrayList<String> tables = new ArrayList<>();
        tables.add("pitotcovel"); tables.add("pitotvelpro");
        sqLiteToExcel.exportSpecificTables(tables,"Pitot_Tube.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
                Toast.makeText(Pitot_Tube.this, "Exporting...", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(Pitot_Tube.this, "Successfully exported to /Downloads/VFL/", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(Pitot_Tube.this, "An error occurred!!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    public void switchExpMode(View view){
        if (!vel_profile) {
            vel_profile = true;
            expModeText.setText("Velocity Profile");
            velProfile.setVisibility(View.VISIBLE);
            coeff_of_vel.setVisibility(View.INVISIBLE);
            dataSNo = sharedPreferencesVP.getInt("serialNo", 0);
        }
        else{
            vel_profile = false;
            expModeText.setText("Co-efficient of Velocity");
            velProfile.setVisibility(View.INVISIBLE);
            coeff_of_vel.setVisibility(View.VISIBLE);
            dataSNo = sharedPreferencesCV.getInt("serialNo", 0);
        }
        setObservationData(0);
        obsCount = dataSNo;
        if(obsCount == 10){
            saveButton.setText("Proceed");
            saveButton.setTag("2");
        }else{
            saveButton.setText("Save");
            saveButton.setTag("1");
        }
        observationCount.setText(String.format("%s of 10", obsCount));
    }

    public void changeFlowRate(View view) {
        if (power && !flowBar_visibility) {
            flowRateSeekBar.setVisibility(View.VISIBLE);
            flowBar_visibility = true;
            flowRateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    flowRate = (progress * 0.0555 + 2.45);
                    generateTubesHeight(flowRate);
                    setObservationData(pin_height);
                    setTubesLevel();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    flowRateSeekBar.setVisibility(View.INVISIBLE);
                    flowBar_visibility = false;
                }
            });
        } else if (flowBar_visibility) {
            flowRateSeekBar.setVisibility(View.INVISIBLE);
            flowBar_visibility = false;
        }
    }

    public void powerUp(View view) {
        if (!power) {
            pitot_apparatus.setImageResource(R.drawable.pitot_tube_on);
            power = true;
        } else {
            pitot_apparatus.setImageResource(R.drawable.pitot_tube_off);
            power = false;
        }
    }

    public void generateTubesHeight(double flowRate) {

        if (flowRate <= 4.15)
            calculateHnPE_low(flowRate);
        else
            calculateHnPE_high(flowRate);

        double[] velMin = new double[]{0.4209, 0.4413, 0.4454, 0.4459, 0.4458, 0.4458, 0.4458, 0.4459, 0.4454, 0.4413, 0.4209};
        double[] velMax = new double[]{1.362, 1.405, 1.408, 1.408, 1.407, 1.407, 1.407, 1.408, 1.408, 1.405, 1.362};
        double[] vel = new double[11];
        double[] err = new double[11];
        double[] hDiff = new double[11];

        for (int i = 0; i < 11; i++) {
            vel[i] = velMin[i] + (flowRate*0.0001 - 0.000245) * (velMax[i] - velMin[i]) / 0.000555;
            err[i] = percentageError[i] * vel[i] / 100;
            vel[i] = vel[i] - err[i];
            hDiff[i] = Math.pow(vel[i], 2) * 100 / 19.62;
            staticH[i] = spd_height[i] - hDiff[i];
        }
        System.out.println(String.format(Locale.US, "error = %.6f,   Velocity r=-10 is %.5f", percentageError[0], vel[0]) );
    }

    private void calculateHnPE_low(double flowRate) {

        spd_height[0] = 3.4831461 * flowRate + 48.266292;
        percentageError[0] = 0.297425 * Math.pow(flowRate, 5) - 3.99143 * Math.pow(flowRate, 4) + 6.16347 * Math.pow(flowRate, 3) + 127.659 * Math.pow(flowRate, 2) - 679.181 * flowRate + 1003.41;

        spd_height[1] = 3.4831461 * flowRate + 48.366292;
        percentageError[1] = 0.770016 * Math.pow(flowRate, 5) - 9.50352 * Math.pow(flowRate, 4) + 21.1918 * Math.pow(flowRate, 3) + 167.355 * Math.pow(flowRate, 2) - 914.106 * flowRate + 1270.73;

        spd_height[2] = 3.4831461 * flowRate + 48.466292;
        percentageError[2] = 1.26049 * Math.pow(flowRate, 5) - 15.5986 * Math.pow(flowRate, 4) + 34.6234 * Math.pow(flowRate, 3) + 275.418 * Math.pow(flowRate, 2) - 1485.71 * flowRate + 2024.09;

        spd_height[3] = 3.4831461 * flowRate + 48.566292;
        percentageError[3] = 1.02128 * Math.pow(flowRate, 5) - 12.7233 * Math.pow(flowRate, 4) + 28.6429 * Math.pow(flowRate, 3) + 225.112 * Math.pow(flowRate, 2) - 1225.66 * flowRate + 1679.56;

        spd_height[4] = 3.4269663 * flowRate + 48.903933;
        percentageError[4] = -0.47262 * Math.pow(flowRate, 4) + 4.0948 * Math.pow(flowRate, 3) + 1.75069 * Math.pow(flowRate, 2) - 90.5471 * flowRate + 193.457;

        spd_height[5] = 3.4269663 * flowRate + 48.903933;
        percentageError[5] = -0.0218502 * Math.pow(flowRate, 3) + 2.59074 * Math.pow(flowRate, 2) - 22.7186 * flowRate + 51.716;

        spd_height[6] = 3.3146067 * flowRate + 49.279213;
        percentageError[6] = -0.315099 * Math.pow(flowRate, 4) + 2.67941 * Math.pow(flowRate, 3) + 1.30458 * Math.pow(flowRate, 2) - 59.0526 * flowRate + 126.559;

        spd_height[7] = 3.3146067 * flowRate + 49.179213;
        percentageError[7] = 0.638338 * Math.pow(flowRate, 5) - 7.77603 * Math.pow(flowRate, 4) + 16.694 * Math.pow(flowRate, 3) + 136.704 * Math.pow(flowRate, 2) - 722.742 * flowRate + 975.134;

        spd_height[8] = 3.3707865 * flowRate + 48.941573;
        percentageError[8] = 0.71091 * Math.pow(flowRate, 5) - 8.75471 * Math.pow(flowRate, 4) + 19.2392 * Math.pow(flowRate, 3) + 154.301 * Math.pow(flowRate, 2) - 827.645 * flowRate + 1127.08;

        spd_height[9] = 3.3707865 * flowRate + 48.841573;
        percentageError[9] = 0.71072 * Math.pow(flowRate, 5) - 9.03216 * Math.pow(flowRate, 4) + 21.2867 * Math.pow(flowRate, 3) + 160.339 * Math.pow(flowRate, 2) - 903.898 * flowRate + 1276.78;

        spd_height[10] = 3.0898876 * flowRate + 49.429775;
        percentageError[10] = 0.231872 * Math.pow(flowRate, 5) - 3.81021 * Math.pow(flowRate, 4) + 13.1503 * Math.pow(flowRate, 3) + 71.1575 * Math.pow(flowRate, 2) - 523.637 * flowRate + 857.717;

    }

    private void calculateHnPE_high(double flowRate) {

        spd_height[0] = 12.4700217 * flowRate + 10.97075819;
        percentageError[0] = -1.47059 * flowRate + 12.1024;

        spd_height[1] = 12.3439713 * flowRate + 11.59386743;
        percentageError[1] = -0.73529 * flowRate + 6.051471;

        spd_height[2] = 12.3439713 * flowRate + 11.69386743;
        percentageError[2] = -0.21008 * flowRate + 2.121849;

        spd_height[3] = 12.6380889 * flowRate + 10.5732792;
        percentageError[3] = -0.02941 * flowRate + 0.622059;

        spd_height[4] = 12.6782174 * flowRate + 10.51124068;
        percentageError[4] = -0.15546 * flowRate + 1.395168;

        spd_height[5] = 12.7202342 * flowRate + 10.33687093;
        percentageError[5] = -0.35714 * flowRate + 2.982143;

        spd_height[6] = 12.8004910 * flowRate + 9.912793882;
        percentageError[6] = -0.48739 * flowRate + 4.042689;

        spd_height[7] = 12.9265414 * flowRate + 9.289684638;
        percentageError[7] = -0.44538 * flowRate + 4.408319;

        spd_height[8] = 12.9704466 * flowRate + 9.102983665;
        percentageError[8] = -0.48319 * flowRate + 5.155252;

        spd_height[9] = 12.8443962 * flowRate + 9.526092909;
        percentageError[9] = -0.73529 * flowRate + 7.301471;

        spd_height[10] = 12.9189878 * flowRate + 8.639009536;
        percentageError[10] = -1.02941 * flowRate + 9.722059;

    }

    public void setTubesLevel() {
        static_press_bar.setProgress((int) height[0]);
        dynamic_press_bar.setProgress((int) height[1]);
    }

    private void setObservationData(int r) {
        height[0] = (float) staticH[(r+10)/2];
        height[1] = (float) spd_height[(r+10)/2];
        if(vel_profile){
            flowRateTV.setText(String.format(Locale.US, "Q = %.2fE-01 L/s", flowRate));
            pinHeightTV.setText(String.valueOf(pin_height));
            staticHTV.setText(String.format(Locale.US, "%.1f", height[0]));
            dynamicHTV.setText(String.format(Locale.US, "%.1f", height[1]));
        }
        else {
            flowRateText.setText(String.format(Locale.US, "%.2fE-01", flowRate));
            static_pressText.setText(String.format(Locale.US, "%.1f", height[0]));
            dynamic_pressText.setText(String.format(Locale.US, "%.1f", height[1]));
        }
    }

    public void openTestSectionPopUp(View view) {
        if (power && !ts_popUp_visibility && vel_profile) {
            testSection_popUp.setVisibility(View.VISIBLE);
            ts_popUp_visibility = true;
            needleHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    pin_height = progress*2 - 10;
                    float density = metrics.density;
                    pinParams.bottomMargin = (int) ((1.3 * density * progress) + (16 * density));
                    pitot_test_section_pin.setLayoutParams(pinParams);
                    needleHeightTextView.setText(String.format("%s cm", pin_height));
                    setObservationData(pin_height);
                    setTubesLevel();
//                    pitot_test_section_pin.animate().translationYBy(-pin_height);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        } else {
            testSection_popUp.setVisibility(View.INVISIBLE);
            ts_popUp_visibility = false;
        }
    }

    public void saveDelData(View view){
        int tag = Integer.parseInt(view.getTag().toString());
        Log.i("Button Tag", String.valueOf(tag));
        if (tag == 1){
            if (obsCount < 10){
                obsCount += 1; dataSNo += 1;
                if(vel_profile) {
                    observationDatabase.execSQL("INSERT INTO pitotvelpro (sn, flowrate, pinH, sheight, dheight) VALUES (" +
                            String.format(Locale.US, "%d, %f, %d, %f, %f)",
                                    dataSNo, (float) flowRate * 0.1, pin_height, (float) staticH[(pin_height+10)/2], (float) spd_height[(pin_height+10)/2]));
                }
                else {
                    observationDatabase.execSQL("INSERT INTO pitotcovel (sn, flowrate, sheight, dheight) VALUES (" +
                            String.format(Locale.US, "%d, %f, %f, %f)",
                                    dataSNo, (float) flowRate * 0.1, (float) staticH[5], (float) spd_height[5]));
                }
            }
        }
        else if (tag == 0 && obsCount > 0){
            if(vel_profile)
                observationDatabase.execSQL("DELETE FROM pitotvelpro WHERE sn = " + dataSNo);
            else
                observationDatabase.execSQL("DELETE FROM pitotcovel WHERE sn = " + (dataSNo));
            dataSNo -= 1; obsCount -= 1;
        }
        else if (tag == 2) {
            simulation.setVisibility(View.INVISIBLE);
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
        if(vel_profile)
            sharedPreferencesVP.edit().putInt("serialNo", obsCount).apply();
        else
            sharedPreferencesCV.edit().putInt("serialNo", obsCount).apply();
    }

    public void createObsTable(){
        Cursor c = observationDatabase.rawQuery("SELECT * FROM pitotcovel", null);
        c.moveToFirst();
        int sNo = c.getColumnIndex("sn");
        int frIndex = c.getColumnIndex("flowRate");
        int shIndex = c.getColumnIndex("sheight");
        int dhIndex = c.getColumnIndex("dheight");
        try {
            for (int i = 0; i < 10; i++) {
                TableRow row = new TableRow(Pitot_Tube.this);
                //row.setId(1 + i);
                for (int j = 0; j < 4; j++) {
                    float d;
                    if (j == 0)
                        d = c.getInt(sNo);
                    else if (j == 1)
                        d = c.getFloat(frIndex);
                    else if (j==2)
                        d = c.getFloat(shIndex);
                    else
                        d = c.getFloat(dhIndex);
                    TextView tv = new TextView(Pitot_Tube.this);
                    tv.setTextSize(17);
                    float initWeight = j>0?1:0.2f;
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, initWeight));
                    String format = j>0?(j==1?"%.3f":"%.1f"):"%.0f";
                    tv.setText(String.format(Locale.US, format, d));
                    row.addView(tv);
                }
                obsTableCV.addView(row);
                c.moveToNext();
            }
        } catch (Exception e) {
            Log.i("DATA", "No more data found");
        }

        //-----------Read Data from Pitot Velocity Profile Table---------------//

        c = observationDatabase.rawQuery("SELECT * FROM pitotvelpro", null);
        c.moveToFirst();
        sNo = c.getColumnIndex("sn");
        frIndex = c.getColumnIndex("flowRate");
        int phIndex = c.getColumnIndex("pinH");
        shIndex = c.getColumnIndex("sheight");
        dhIndex = c.getColumnIndex("dheight");
        try {
            for (int i = 0; i < 11; i++) {
                TableRow row = new TableRow(Pitot_Tube.this);
                //row.setId(1 + i);
                for (int j = 0; j < 5; j++) {
                    float d;
                    if (j == 0)
                        d = c.getInt(sNo);
                    else if (j == 1)
                        d = c.getFloat(frIndex);
                    else if (j == 2)
                        d = c.getInt(phIndex);
                    else if (j == 3)
                        d = c.getFloat(shIndex);
                    else
                        d = c.getFloat(dhIndex);
                    float initWeight = j>0?1:0.2f;
                    if(j!=1) {
                        TextView tv = new TextView(Pitot_Tube.this);
                        tv.setTextSize(17);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT, initWeight));
                        String format = j > 0 ? (j == 2 ? "%.0f" : "%.1f") : "%.0f";
                        tv.setText(String.format(Locale.US, format, d));
                        row.addView(tv);
                    }
                    else
                        obsFlowRateTV.setText(String.format(Locale.US, "Q = %.3f L/s", d));
                }
                obsTableVP.addView(row);
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
                .setMessage("Delete observation table for ?")
                .setPositiveButton("Co-eff. of vel.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        observationDatabase.execSQL("DELETE FROM pitotcovel");
                        sharedPreferencesCV.edit().putInt("serialNo", 0).apply();
                    }
                })
                .setNegativeButton("Velocity Profile", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        observationDatabase.execSQL("DELETE FROM pitotvelpro");
                        sharedPreferencesVP.edit().putInt("serialNo", 0).apply();
                    }
                })
                .show();

        if(vel_profile)
            obsCount = sharedPreferencesVP.getInt("serialNo", 0);
        else
            obsCount = sharedPreferencesCV.getInt("serialNo", 0);
        observationCount.setText(String.format("%s of 10", obsCount));
        dataSNo = obsCount+1;
    }

    @Override
    public void onBackPressed() {
        if(!power) {
//            Intent intent = new Intent(getApplicationContext(), ListView.class);
//            startActivity(intent);
//            finish();
            super.onBackPressed();
        } else
            Toast.makeText(this, "Turn off the power!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitot__tube);

        para1 = findViewById(R.id.pitot_para1);
        para2 = findViewById(R.id.pitot_para2);
        para3 = findViewById(R.id.pitot_para3);
        pitot_webView = findViewById(R.id.pitot_webView);
        heading1 = findViewById(R.id.pitot_heading1);
        heading2 = findViewById(R.id.pitot_heading2);

        flowRateText = findViewById(R.id.flowRateText);
        static_pressText = findViewById(R.id.static_height_text);
        dynamic_pressText = findViewById(R.id.dynamic_height_text);

        introduction = findViewById(R.id.pitot_introduction);
        observation = findViewById(R.id.pitot_observation);

        pitot_labelledDiagram = findViewById(R.id.pitot_labelledDiagram);
        pitot_Equation = findViewById(R.id.pitot_Equation);
        pitot_testSectionData = findViewById(R.id.pitot_testSectionData);
        pitot_test_section_pin = findViewById(R.id.pitot_test_section_pin);

        flowRateSeekBar = findViewById(R.id.flowRateSeekBar);
        needleHeightSeekBar = (VerticalSeekBar) findViewById(R.id.needleHeightSeekBar);
        needleHeightSeekBar.setMax(10);
        needleHeightSeekBar.setProgress(5);
        needleHeightTextView = findViewById(R.id.needleHeightTextView);
        expModeText = findViewById(R.id.pitot_experimentModeText);
        velProfile = findViewById(R.id.pitot_vel_profile_table);
        coeff_of_vel = findViewById(R.id.pitot_coeff_of_vel_table);
        pinHeightTV = findViewById(R.id.pitot_pinHeightText);
        flowRateTV = findViewById(R.id.pitot_flowRateText2);
        staticHTV = findViewById(R.id.pitot_static_height_text2);
        dynamicHTV = findViewById(R.id.pitot_dynamic_height_text2);
        observationCount = findViewById(R.id.pitot_readingsCountText);
        saveButton = findViewById(R.id.pitot_save_button);

        try{
            observationDatabase = this.openOrCreateDatabase("Observation", MODE_PRIVATE, null);
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS pitotcovel ( sn INT(2), flowRate FLOAT, sheight FLOAT, dheight FLOAT )");
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS pitotvelpro ( sn INT(2), flowRate FLOAT, pinH INT(2), sheight FLOAT, dheight FLOAT )");
        }catch (Exception e){
            e.printStackTrace();
        }

        sharedPreferencesCV = this.getSharedPreferences("com.example.virtualfluidlab.pitotCV", Context.MODE_PRIVATE);
        sharedPreferencesVP = this.getSharedPreferences("com.example.virtualfluidlab.pitotVP", Context.MODE_PRIVATE);
        dataSNo = sharedPreferencesCV.getInt("serialNo", 0);
        obsCount = dataSNo;
        observationCount.setText(String.format("%s of 10", obsCount));

        static_press_bar = findViewById(R.id.static_tube); static_press_bar.setMax(150);
        dynamic_press_bar = findViewById(R.id.dynamic_tube); dynamic_press_bar.setMax(150);
        pitot_apparatus = findViewById(R.id.pitot_apparatus);

        simulation = findViewById(R.id.simulation);
        testSection_popUp = findViewById(R.id.testSection_popUp);

        obsTableCV = findViewById(R.id.pitot_cov_observationTable);
        obsTableVP = findViewById(R.id.pitot_velPro_observationTable);
        obsFlowRateTV = findViewById(R.id.pitot_obs_flowRateTV);

        pinParams = (ConstraintLayout.LayoutParams) pitot_test_section_pin.getLayoutParams();
        metrics = getResources().getDisplayMetrics();
        density = metrics.density;
        labelled_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (300 * density));

        Intent intent = getIntent();
        choice = intent.getIntExtra("choice", 0);

        switch (choice) {
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
    }

}