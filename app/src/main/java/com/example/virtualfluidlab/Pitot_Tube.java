package com.example.virtualfluidlab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VerticalSeekBar;

import com.example.virtualfluidlab.view.MathJaxWebView;

import java.util.Locale;

public class Pitot_Tube extends AppCompatActivity {

    TextView heading1, heading2, para1, para2, para3;
    TextView flowRateText, static_pressText, dynamic_pressText;
    TextView needleHeightTextView;

    ImageView pitot_labelledDiagram, pitot_Equation, pitot_testSectionData, pitot_apparatus;
    ImageView pitot_test_section_pin;

    SeekBar flowRateSeekBar;
    VerticalSeekBar needleHeightSeekBar;
    ProgressBar static_press_bar, dynamic_press_bar;

    ScrollView introduction;
    ConstraintLayout simulation, testSection_popUp;

    ConstraintLayout.LayoutParams pinParams;
    DisplayMetrics metrics;

    MathJaxWebView pitot_webView;

    int choice;
    float density;
    float[] height = new float[2];
    double flowRate;
    boolean flowBar_visibility = false, power = false, ts_popUp_visibility = false;

    String aim = "•\tTo find the point velocity at center of a tube for different flow rate.\n" +
                 "•\tTo find the coefficient of pitot tube.\n" +
                 "•\tTo plot the velocity profile across the cross-section of pipe.";

    String theory = "It is a device used for measuring the velocity of flow at any point in a pipe. It is based on the principle that if the velocity of flow at a point becomes zero, there is increase in pressure due to the conversion of the kinetic energy into pressure energy. The Pitot tube consists of a capillary tube, bend at right angle. The lower end is directed in the upstream direction. " +
            "The liquid rises up in the tube due to conversion of kinetic energy into pressure energy. The velocity is determined by measuring the rise of liquid in the tube. Pitot static tube is one of the application of Bernoulli’s Theorem which is valid in regions of steady, incompressible flow where net frictional forces are negligible. With the help of Pitot static tube, we can find the actual velocity at different points of a cross-section. " +
            "However, mean velocity will be same across the cross-section.\n" +
            "Since a Pitot tube measures the stagnation pressure head (or the total head) at its dipped end. The pressure head may be determined directly by connecting a differential manometer between the Pitot tube and pressure taping at the pipe surface. Consider two points 1 and 2 at the same level in such a way that point 1 is at the inlet of the pitot tube and point 2 is at the outlet. " +
            "At point 1 the pressure is p1 and the velocity of the stream is v1. However, at point 2 the fluid is brought to rest and the energy has been converted to pressure energy. Therefore the pressure at 2 is p2, the velocity v2 is zero and since 1 and 2 are in the same horizontal plane, \n" +
            "Applying Bernoulli’s equation at points (1) and (2)\n";

    String theory_formulas = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 20px; font-style:bold; font-weight: 400;color:#707070\">\n" +
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

    String procedureSteps = "1. Ensure that ON/OFF switch given on the panel is at OFF position.\n" +
            "\n2. Make sure all the drain valves are closed. \n" +
            "\n3. Fill sump tank ¾ with clean water and open by-pass valve V2. \n" +
            "\n4. Switch ON the main power supply and the pump.\n" +
            "\n5. Open valve V1 and allow water to flow through test section by partially closing valve V2.\n" +
            "\n6. Open the air release valve V5 provided on the manometer, slowly to release the air from manometer.\n" +
            "\n7. When no air is observed in the manometer, close the valve V5.\n" +
            "\n8. Position the Pitot tube at the centre of the test section by adjusting the pointer to zero by knob provided.\n" +
            "\n9. Adjust water flow rate with the help of control valve V1 and by pass valve V2.\n" +
            "\n10. Record the manometer reading, in case of pressure above scale in any tube, apply air pressure by hand pump to get readable reading.\n" +
            "\n11. Measure the discharge flow rate using stop watch and measuring tank.\n" +
            "\n12. For different positions of pitot tube (change by knob), Record the manometer reading for particular discharge to determine velocity profile.\n" +
            "\n13. Vary the flow rates of water by operating control valve V1 and by-pass valve V2 and repeat the experiment.\n" +
            "\n14. Turn OFF the pump and power supply. \n" +
            "\n15. Drain the apparatus using valve V3 and V4.\n";

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

    public void openIntroduction(){
        heading1.setTextSize(30);
        heading2.setTextSize(30);
        heading1.setText("Aim:");
        heading2.setText("Theory:");
        setTitle("Introduction");
        para1.setText(aim);
        para2.setText(theory);
        pitot_webView.setLayoutParams(params);
        pitot_webView.setText(theory_formulas);
//        pitot_Equation.requestLayout();
//        pitot_Equation.setImageResource(R.drawable.pitot_equation);
//        pitot_Equation.getLayoutParams().height = 250;
//        para3.setText(theory2_2);
        introduction.setVisibility(View.VISIBLE);
    }

    public void openAboutSetup(){
        setTitle("About Setup");
        heading1.setVisibility(View.INVISIBLE);
        heading2.setVisibility(View.INVISIBLE);
        para1.setText(expSetup1);
        para2.setText(expSetup2);
        pitot_labelledDiagram.setLayoutParams(labelled_params);
//        pitot_labelledDiagram.requestLayout();
//        pitot_labelledDiagram.setImageResource(R.drawable.bernoulli_labelled);
//        pitot_labelledDiagram.getLayoutParams().height = 800;
//        pitot_testSectionData.setImageResource(R.drawable.test_section_data);
//        pitot_testSectionData.requestLayout();
//        pitot_testSectionData.getLayoutParams().height = 650;
        introduction.setVisibility(View.VISIBLE);
    }

    public void openProcedure(){
        setTitle("Procedure");
        heading1.setVisibility(View.INVISIBLE);
        heading2.setVisibility(View.INVISIBLE);
//        pitot_testSectionData.setImageResource(R.drawable.bernoulli_labelled);
//        pitot_testSectionData.requestLayout();
//        pitot_testSectionData.getLayoutParams().height = 800;
//        para1.setTextSize(15);
//        para1.setText(noteProcedure);
        para2.setText(procedureSteps);
        introduction.setVisibility(View.VISIBLE);
    }

    public void startSimulation(){
        setTitle("Simulation");
        simulation.setVisibility(View.VISIBLE);
    }

    public void openObservation(){

    }

    public void changeFlowRate(View view){
        if(power && !flowBar_visibility){
            flowRateSeekBar.setVisibility(View.VISIBLE);
            flowBar_visibility = true;
            flowRateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    flowRate = (progress*0.000003 + 0.0002);
                    generateTubesHeight(progress);
                    setTubesLevel();
                    setObservationData();
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
        }
        else if(flowBar_visibility){
            flowRateSeekBar.setVisibility(View.INVISIBLE);
            flowBar_visibility = false;
        }
    }

    public void powerUp(View view){
        if(!power){
            pitot_apparatus.setImageResource(R.drawable.pitot_tube_on);
            power = true;
        }
        else{
            pitot_apparatus.setImageResource(R.drawable.pitot_tube_off);
            power = false;
        }
    }

    public void generateTubesHeight(double flowRate){
        height[0] = (float) flowRate - 20;
        height[1] = (float) flowRate - 5;
    }

    public void setTubesLevel(){
        static_press_bar.setProgress((int)height[0]);
        dynamic_press_bar.setProgress((int)height[1]);
    }

    public void setObservationData(){
        flowRateText.setText(String.format(Locale.US, "%.3f", flowRate*1000));
        static_pressText.setText(String.format(Locale.US, "%.1f", height[0]));
        dynamic_pressText.setText(String.format(Locale.US, "%.1f", height[1]));
    }

    public void openTestSectionPopUp(View view){
        if(!ts_popUp_visibility) {
            testSection_popUp.setVisibility(View.VISIBLE);
            ts_popUp_visibility = true;
            needleHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    float pin_height = (progress - 50f) / 5f;
                    float density = metrics.density;
                    pinParams.bottomMargin = (int) ((0.13*density*progress) + (16*density));
                    pitot_test_section_pin.setLayoutParams(pinParams);
                    needleHeightTextView.setText(String.format("%s cm", pin_height));
//                    pitot_test_section_pin.animate().translationYBy(-pin_height);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        else{
            testSection_popUp.setVisibility(View.INVISIBLE);
            ts_popUp_visibility = false;
        }
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

        pitot_labelledDiagram = findViewById(R.id.pitot_labelledDiagram);
        pitot_Equation = findViewById(R.id.pitot_Equation);
        pitot_testSectionData = findViewById(R.id.pitot_testSectionData);
        pitot_test_section_pin = findViewById(R.id.pitot_test_section_pin);

        flowRateSeekBar = findViewById(R.id.flowRateSeekBar);
        needleHeightSeekBar = (VerticalSeekBar) findViewById(R.id.needleHeightSeekBar);
        needleHeightSeekBar.setProgress(50);
        needleHeightTextView = findViewById(R.id.needleHeightTextView);

        static_press_bar = findViewById(R.id.static_tube);
        dynamic_press_bar = findViewById(R.id.dynamic_tube);
        pitot_apparatus = findViewById(R.id.pitot_apparatus);

        simulation = findViewById(R.id.simulation);
        testSection_popUp = findViewById(R.id.testSection_popUp);

        pinParams = (ConstraintLayout.LayoutParams) pitot_test_section_pin.getLayoutParams();
        metrics  = getResources().getDisplayMetrics();
        density = metrics.density;
        labelled_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (300*density));

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
    }

}