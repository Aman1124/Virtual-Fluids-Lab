package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.virtualfluidlab.view.MathJaxWebView;

public class ReynoldsNumber extends AppCompatActivity {

    int choice;
    ScrollView introductionView, aboutSetupView, procedureView;
    ConstraintLayout simulationView;
    TextView rey_aimPara, rey_theoryPara1, rey_theoryPara2;
    TextView rey_aboutSetup, rey_procedure;
    MathJaxWebView rey_theoryFormula;

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

    String procedure = "1.)\tClose the drainage valve of the constant head tank if it is in open position. \n" +
            "\n2.)\tSwitch ON the main power supply and the pump. \n" +
            "\n3.)\tOpen the control valve of water supply to constant head tank and partially close the bypass valve. Wait till overflow occurs. \n" +
            "\n4.)\tBy partially opening the control valve provided at the end of the tube, the minimum flow of water through the glass tube is regulated. \n" +
            "\n5.)\tChange the flow of dye by the flow adjustment arrangement through the needle such that a fine color thread is observed.\n" +
            "\n6.)\tNote down the flow pattern observed (laminar, transition or turbulent). \n" +
            "\n7.)\tMeasure the flow rate using measuring cylinder, stop watch. Determine the flow velocity and corresponding Reynolds number. \n" +
            "\n8.)\tRepeat the steps 6 and 7 for different flow rates by adjusting the control valve. \n" +
            "\n9.)\tSwitch OFF the pump and drain the apparatus completely once the experiment is over.\n";

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
        rey_procedure.setText(procedure);
        procedureView.setVisibility(View.VISIBLE);
    }

    public void openObservation(){
        setTitle("Observation");
    }

    public void startSimulation(){
        setTitle("Simulation");
        simulationView.setVisibility(View.VISIBLE);
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
        rey_procedure = findViewById(R.id.reynolds_procedurePara);

        simulationView = findViewById(R.id.reynolds_simulation);

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