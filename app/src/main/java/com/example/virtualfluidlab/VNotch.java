package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VNotch extends AppCompatActivity {

    ScrollView introduction, aboutSetup, procedure;
    TextView aimText, theoryText, aboutSetupText, procedureText1, procedureText2;

    ConstraintLayout simulation;
    ImageView setupImage;
    boolean powerOn = false;

    SQLiteDatabase observationDatabase;
    SharedPreferences sharedPreferences;
    int dataSNo = 0, obsCount = 0, choice;
    TableLayout obsTable;

    String aim = "•\tTo determine the discharge coefficient of a triangular/rectangular notch.\n" +
            "•\tPlot Cd vs H \n" +
            "•\tPlot Qact vs H\n" +
            "•\tPlot log (Qact) vs log (H) to calculate the constants ‘K’ and ‘n’ in Qact = K x Hⁿ";

    String theory = "The flow of a liquid in a conduit with a free surface is open-channel flow. Many realistic examples are available, both artificial (flumes, spillways, canals, Weirs, ditches of drainage) and natural (streams, rivers, estuaries, floodplains). A weir is a channel obstruction over which the flow must deflect, of which the ordinary dam is an example. Weirs are also used for flow measurement and artificial channel control. \n" +
            "A sharp-crested notch is essentially a vertical sharp-edged flat plate placed across the channel in a way such that the fluid must flow across the sharp edge and drop into the pool downstream of the weir or notch plate, as is shown in Fig. The specific shape of the flow area in the plane of the notch plate is used to designate the type of notch. A rectangle and a V notch, as shown in fig below, are the two most common shapes. All should be thoroughly ventilated and not drowned out. \n" +
            "A notch is made up of a metallic plate and is placed at the end of a small channel, whereas a weir is a concrete or masonry structure in an open channel, over which the liquid flow takes place.  Occasionally notches are smaller in size in comparison to weir. The top edge of a weir or the bottom edge of a notch is called a crest or sill, over which the water flows. When its crest length is equal to the width of the channel, a rectangular notch is called a suppressed notch. However, the effect of end contraction is considered if the width is shorter than the width of the channel. Nappe is known as the sheet of water flowing through a notch or weir.\n";

    String setup = "A schematic diagram of the experimental set up is shown below:";

    String labProcedure = "1. Open the bypass valve (V1) and close all other valves.\n" +
            "2. Start the motor and close the valve (V1) until the water level reaches crest (weir) height. Then take the initial reading using Hook’s gauge.\n" +
            "3. Again open the valve (V1) and adjust a discharge in the channel by adjusting the valve ( V2 ). Note down the following:\n" +
            "\t\t(a) Final Hook’s gauge reading when the hook (needle) touches the free stream surface\n" +
            "\t\t(b) Time required for a 100 mm rise in water level in the measuring tank377\n" +
            "4. Take 4/5 readings by repeating the aforementioned procedure for different discharges by adjusting the valves ( V1 and V2 ) at different positions.\n" +
            "5. Stop the motor and open the valve ( V2 ) to drain out the remaining water out of the channel.";

    String simProcedure = "";

    private void openIntroduction() {
        setTitle("Introduction");
        aimText.setText(aim);
        theoryText.setText(theory);
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
        simulation.setVisibility(View.VISIBLE);
    }

    private void openObservation() {
        setTitle("Observation");
    }

    public void powerUp(View view){
        if(!powerOn){
            setupImage.setImageResource(R.drawable.v_notch_on);
            powerOn = true;
        } else {
            setupImage.setImageResource(R.drawable.v_notch_off);
            powerOn = false;
        }
    }

    @Override
    public void onBackPressed() {
        if(!powerOn)
            super.onBackPressed();
        else
            Toast.makeText(this, "Turn off the power", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_notch);

        introduction = findViewById(R.id.vn_introduction);
        aboutSetup = findViewById(R.id.vn_aboutSetup);
        procedure = findViewById(R.id.vn_procedure);

        aimText = findViewById(R.id.vn_aimPara);
        theoryText = findViewById(R.id.vn_theoryPara);
        aboutSetupText = findViewById(R.id.vn_aboutSetupPara);
        procedureText1 = findViewById(R.id.vn_procedurePara1);
        procedureText2 = findViewById(R.id.vn_procedurePara2);

        simulation = findViewById(R.id.vNotch_simulation);
        setupImage = findViewById(R.id.vNotch_setupImage);

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