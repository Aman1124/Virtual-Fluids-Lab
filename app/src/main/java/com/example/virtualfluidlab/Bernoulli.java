package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Bernoulli extends AppCompatActivity {

    RelativeLayout simulation;
    LinearLayout introduction;

    TextView para1, para2;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bernoulli);

        simulation = findViewById(R.id.simulation);
        introduction = findViewById(R.id.introduction);

        para1 = findViewById(R.id.para1);
        para2 = findViewById(R.id.para2);

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

    }
}