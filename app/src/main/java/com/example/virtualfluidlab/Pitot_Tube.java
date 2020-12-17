package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class Pitot_Tube extends AppCompatActivity {

    TextView heading1, heading2, para1, para2, para3;
    TextView flowRateText, static_pressText, dynamic_pressText;

    ImageView pitot_labelledDiagram, pitot_Equation, pitot_testSectionData;
    SeekBar flowRateSeekBar;
    ProgressBar static_press_bar, dynamic_press_bar;

    ScrollView introduction;
    ConstraintLayout simulation;

    int choice;
    float[] height = new float[2];
    double flowRate;
    boolean flowBar_visibility = false;

    String aim = "•\tTo find the point velocity at center of a tube for different flow rate.\n" +
                 "•\tTo find the coefficient of pitot tube.\n" +
                 "•\tTo plot the velocity profile across the cross-section of pipe.";

    String theory = "It is a device used for measuring the velocity of flow at any point in a pipe. It is based on the principle that if the velocity of flow at a point becomes zero, there is increase in pressure due to the conversion of the kinetic energy into pressure energy. The Pitot tube consists of a capillary tube, bend at right angle. The lower end is directed in the upstream direction. " +
            "The liquid rises up in the tube due to conversion of kinetic energy into pressure energy. The velocity is determined by measuring the rise of liquid in the tube. Pitot static tube is one of the application of Bernoulli’s Theorem which is valid in regions of steady, incompressible flow where net frictional forces are negligible. With the help of Pitot static tube, we can find the actual velocity at different points of a cross-section. " +
            "However, mean velocity will be same across the cross-section.\n" +
            "Since a Pitot tube measures the stagnation pressure head (or the total head) at its dipped end. The pressure head may be determined directly by connecting a differential manometer between the Pitot tube and pressure taping at the pipe surface. Consider two points 1 and 2 at the same level in such a way that point 1 is at the inlet of the pitot tube and point 2 is at the outlet. " +
            "At point 1 the pressure is p1 and the velocity of the stream is v1. However, at point 2 the fluid is brought to rest and the energy has been converted to pressure energy. Therefore the pressure at 2 is p2, the velocity v2 is zero and since 1 and 2 are in the same horizontal plane, \n" +
            "Applying Bernoulli’s equation at points (1) and (2)\n";

    String procedureSteps = "1. Ensure that ON/OFF switch given on the panel is at OFF position.\n" +
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

    String expSetup1 = "The apparatus consists of sump tank with centrifugal pump. A pitot tube made of copper" +
            "provided in the test section made of acrylic connected to pipeline with flow control valve." +
            "The pointer gauge is provided to measure the vertical position of pitot tube in test" +
            "section. A manometer is provided to determine the pressure difference. Discharge is" +
            "measured with the help of measuring tank and stopwatch. A control valve and by-pass valve is provided to regulate the flow of water.";

    String expSetup2 = "The following precautions have to be followed:\n" +
            "1 Never run the apparatus if power supply is less than 200 Volts and above 230 Volts\n" +
            "2 Never fully close the control valve V1 and by pass valve V2 simultaneously.\n" +
            "3 Always keep apparatus free from dust.\n" +
            "4 If pump gets heated up, switch off the main power for 30 minutes and avoid closing the flow control valve V1 and by-pass valve V2 at a time, during operation.";

    public void openIntroduction(){
        heading1.setTextSize(30);
        heading2.setTextSize(30);
        heading1.setText("Aim:");
        heading2.setText("Theory:");
        setTitle("Introduction");
        para1.setText(aim);
        para2.setText(theory);
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
        if(!flowBar_visibility){
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitot__tube);

        para1 = findViewById(R.id.pitot_para1);
        para2 = findViewById(R.id.pitot_para2);
        para3 = findViewById(R.id.pitot_para3);
        heading1 = findViewById(R.id.pitot_heading1);
        heading2 = findViewById(R.id.pitot_heading2);

        flowRateText = findViewById(R.id.flowRateText);
        static_pressText = findViewById(R.id.static_height_text);
        dynamic_pressText = findViewById(R.id.dynamic_height_text);

        introduction = findViewById(R.id.pitot_introduction);

        pitot_labelledDiagram = findViewById(R.id.pitot_labelledDiagram);
        pitot_Equation = findViewById(R.id.pitot_Equation);
        pitot_testSectionData = findViewById(R.id.pitot_testSectionData);
        flowRateSeekBar = findViewById(R.id.flowRateSeekBar);
        static_press_bar = findViewById(R.id.static_tube);
        dynamic_press_bar = findViewById(R.id.dynamic_tube);

        simulation = findViewById(R.id.simulation);

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