package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.virtualfluidlab.view.MathJaxWebView;

public class MetaCenter extends AppCompatActivity {

    ScrollView introduction, aboutSetup, procedure;
    TextView aimText, theoryText1, theoryText2, theoryFormulaRef, aboutSetupText, procedureText1, procedureText2;
    MathJaxWebView theoryFormula;
    ImageView  theoryImg;

    SQLiteDatabase observationDatabase;
    SharedPreferences sharedPreferences;
    int dataSNo = 0, obsCount = 0, choice;
    TableLayout obsTable;

    String aim = "•\tTo determine the meta-centric height with angle of heel of ship model.\n" +
                "•\tTo study the change in meta-centre height as the C.G of the vessel varies.";

    String theory = "If a body is rotated by a slight angle, it begins to oscillate at the point around which it is " +
            "inclined and that point is known as meta-centre. It can also be defined as the point at which the line of " +
            "action of the force of buoyancy meets the regular axis of the body while the body is given a minor angular " +
            "displacement is also known as the meta-centre. \n" +
            "The distance between the meta-centre (denoted by ‘M’) of a floating body and the centre of gravity " +
            "(denoted by ‘G’) of the body is called meta-centric height.\n";

    String theory2 = "From fig.1,\n" +
            "For a body to be in equilibrium on the liquid surface the two forces gravity force (W) and buoyant force (Fb) " +
            "must lie in the same vertical line. If the point M is above G, the floating body will be in stable equilibrium. " +
            "If slight angular displacement is given to the floating body in clockwise direction, the center of buoyancy shifts " +
            "from B to B₁ such that the line of action of Fb through B₁ cuts the axis at M, which is called the meta-center and " +
            "the distance GM is called the meta-centric height. \n" +
            "The buoyant force Fb through B₁ and weight m₁ through G constitute a couple acting in anti- clockwise direction and " +
            "thus bringing the floating body in the original position. To determine the meta-centric height of a floating body, we " +
            "know the center of gravity of floating body. Place the hanger (m₁) over the center of the body. The hanger is moved across " +
            "the vessel towards right through a distance x. The body will be tilted. The angle of tilt(θ) is measured using " +
            "trigonometry. The new center of gravity of the body will shift to G₁ as the hanger has been moved towards the right and " +
            "the center of buoyancy will change to B₁ as the body has tilted. Under equilibrium, the moment caused by the movement of " +
            "the hanger through a distance x must be equal to the moment caused by the shift of the center of gravity from G to G₁.\n" +
            "Therefore, we can write,";

    String theory_formula = "<p align=\"justify\" style=\"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070;\">\n" +
            "\\[CG = {b \\over 2}\\]\n" +
            "\\[GG_1*W = m_1*x\\]\n" +
            "\\[GM*tanθ = m_1*x\\]\n" +
            "Hence, \\[GM = {m_1*x \\over tanθ*W}\\]";

    String theoryFormula_ref = "Where,\n" +
            "W = Weight of body including m₁\n" +
            "G = Centre of gravity of body\n" +
            "B = Centre of buoyancy of the body\n" +
            "M = Meta-centre of the body\n" +
            "m₁ = Weight of hanger\n" +
            "x = Distance moved by weight m₁ \n" +
            "θ = Angle of tilt\n";

    String setup = "A ship model made up of acrylic is allowed to float in a small tank having water. For tilting the model, a cross bar with " +
            "movable hanger is fixed on the model. By means of a pendulum tilting of vessel can be measured on a graduated scale in the form of " +
            "linear displacement of pendulum. For tilting the ship model, a cross bar with movable hanger is fixed on the model. A set of weights is " +
            "supplied with the apparatus.";

    String labProcedure = "1. Firstly close the drain valve.\n" +
            "2. Fill tank 3/4th with clean water and ensure that no foreign particles are there.\n" +
            "3. Float the ship model in water and wait till it became stable.\n" +
            "4. Apply the load strips on the ship model and wait till the ship becomes stable.\n" +
            "5. Apply weight with hanger at any side (i.e., LHS or RHS) on the slot provided.\n" +
            "6. Measure the displacement of pendulum by the scale provided.\n" +
            "7. Repeat the experiment for different position of weight applied.\n" +
            "8. Repeat the experiment for different weights.\n" +
            "9. Remove the weight with hanger from the ship model.\n" +
            "10. Drain water from tank with the help of given drain valve V1.";

    String simProcedure = "";

    private void openIntroduction() {
        setTitle("Introduction");
        aimText.setText(aim);
        theoryText1.setText(theory);
        theoryText2.setText(theory2);
        theoryImg.setImageResource(R.drawable.meta_th_diagram);
        theoryFormula.setText(theory_formula);
        theoryFormulaRef.setText(theoryFormula_ref);
        introduction.setVisibility(View.VISIBLE);
    }

    private void openAboutSetup() {
        setTitle("About Setup");
        aboutSetupText.setText(setup);
        aboutSetup.setVisibility(View.VISIBLE);
    }

    private void openProcedure() {
        setTitle("Procedure");
        procedureText1.setText(labProcedure);
        procedureText2.setText(simProcedure);
        procedure.setVisibility(View.VISIBLE);
    }

    private void startSimulation() {
        setTitle("Simulation");
    }

    private void openObservation() {
        setTitle("Observation");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_center);

        introduction = findViewById(R.id.meta_introduction);
        aboutSetup = findViewById(R.id.meta_aboutSetup);
        procedure = findViewById(R.id.meta_procedure);

        aimText = findViewById(R.id.meta_aimPara);
        theoryText1 = findViewById(R.id.meta_theoryPara1);
        theoryText2 = findViewById(R.id.meta_theoryPara2);
        theoryImg = findViewById(R.id.meta_theoryImg);
        theoryFormula = findViewById(R.id.meta_theory_formula);
        theoryFormulaRef = findViewById(R.id.meta_theory_formulaRef);
        aboutSetupText = findViewById(R.id.meta_aboutSetupPara);
        procedureText1 = findViewById(R.id.meta_procedurePara1);
        procedureText2 = findViewById(R.id.meta_procedurePara2);

        /*
        try{
            observationDatabase = this.openOrCreateDatabase("Observation", MODE_PRIVATE, null);
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS losesinpipes ( sn INT(2), weight INT(3), height INT(3) )");
        }catch (Exception e){
            e.printStackTrace();
        }

        sharedPreferences = this.getSharedPreferences("com.example.virtualfluidlab.cop", Context.MODE_PRIVATE);
        dataSNo = sharedPreferences.getInt("serialNo", 0);
        obsCount = dataSNo;
        */

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