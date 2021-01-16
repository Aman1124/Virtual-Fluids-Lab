package com.example.virtualfluidlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VerticalSeekBar;

import com.example.virtualfluidlab.view.MathJaxWebView;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CenterOfPressure extends AppCompatActivity {

    TextView aimText, theoryText, aboutSetupText, procedureText;
    MathJaxWebView theoryFormula1Text, theoryFormula2Text, theoryFormula3Text;
    ScrollView introduction, aboutSetup, procedure;
    ImageView weightsView, compassBubbleView;
    ConstraintLayout simulation, observation;
    ImageButton add_water;
    Button valve_V1, saveButton, delButton;
    ProgressBar waterLvl;

    ConstraintLayout.LayoutParams bubbleParams;
    DisplayMetrics metrics;
    float density;

    TextView weightText, heightText, observationCount;

    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;

    SQLiteDatabase observationDatabase;
    SharedPreferences sharedPreferences;
    int dataSNo = 0, obsCount = 0;
    TableLayout obsTable;

    int choice, water = 90, weight = 0, currHeight = 0;
    int[] weightId = new int[]{
            R.drawable.cop_weight_1,
            R.drawable.cop_weight_2,
            R.drawable.cop_weight_3,
            R.drawable.cop_weight_4,
            R.drawable.cop_weight_5,
            R.drawable.cop_weight_6,
            R.drawable.cop_weight_7,
            R.drawable.cop_weight_8,
            R.drawable.cop_weight_9,
            R.drawable.cop_weight_10
    };

    double[] idealHeights = new double[]{44.13, 63.51, 78.90, 92.28, 104.47, 116.45, 128.43, 140.41, 152.40, 164.38};
    double[] heights = new double[10];
    double[] maxErr = new double[]{5.12, 3.96, 4.44, 2.45, -3.3, -2.1, -2.55, -0.41, -2.36, -3.41};
    double[] minErr = new double[]{0.29, 0.21, 0.12, -1.8, -6.25, -5.62, -5.11, -3.97, -4.92, -5.24};
    double[] randErr = new double[10];

    String aim = "\t•\tTo determine the hydrostatic force on a plane surface under partial submerge and full submerge condition.\n" +
            "\t•\tTo determine the centre of pressure of a plane surface under partial submerge and full submerge condition.";

    String theory = "Fluid statics deal with the fluid's static properties and behaviour. It is known as hydrostatic in the case of liquids and it is called pneumatic in the case of gases. A force is exerted by the fluid on the surface, defined as total pressure, when a static mass of fluid comes into contact with a surface, either smooth or curved. Since no tangential force occurs for a fluid at rest, the total pressure acts in the normal direction to the surface. \n" +
            "Following is the conclusions about a hydrostatic condition:\n" +
            "Pressure in a continuously distributed uniform static fluid varies only with vertical distance and is independent of the shape of the container. The pressure is the same at all points on a given horizontal plane in the fluid. The pressure increases with depth in the fluid.\n" +
            "\n" +
            "Now, for the given apparatus we have, \n" +
            "\n" +
            "Total force on a vertically immersed surface: \n" +
            "\n" +
            "If a rectangular surface of length a and width b is vertically immersed in the fluid, you can measure the total force acting on the surface with the aid of the torque acting on the same surface:\n";

    String theoryFormula1 = "<p \n align=\"justify\" style=\"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070;\">\n" +
            "\\[T_t = wAx’(K-(h-h_o))\\]\n" +
            "Centre of pressure of the force (in m):\n" +
            "\\[h_o={ I_G \\over {A*x’}}+x’\\]\n" +
            "We can calculate it under two conditions,<br><br>\n" +
            "<b><u>Condition-1:</b></u> Surface is fully submerged in water(h>b)\n" +
            "</p>";

    String theoryFormula2 = "<p \n align=\"justify\" style=\"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070;\">\n" +
            "Center of Gravity (in m): \\[CG = {b\\over 2}\\]<br>\n" +
            "Area of the quadrant (in m2): \\[A={a*b}\\]<br>\n" +
            "Depth of the center of gravity from the liquid surface (in m): \\[x’={h\\over 1000} – CG\\]<br>\n" +
            "Moment of inertia for the rectangular surface (in m4): \\[I_G = {ab^3\\over 12}\\]\n" +
            "<b><u>Condition-2:</b></u> Surface is partially submerged in water(h&ltb)\n" +
            "</p";

    String theoryFormula3 = "<p \n align=\"justify\" style=\"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070;\">\n" +
            "Center of Gravity (in m): \\[CG = {h\\over 2000}\\]<br>\n" +
            "Area of the quadrant (in m2): \\[A={a*h\\over 1000}\\]<br>\n" +
            "Depth of the center of gravity from the liquid surface (in m): \\[x’={h\\over 2000} \\]<br>\n" +
            "Moment of inertia for the rectangular surface (in m4): \n" +
            "\\[I_G = {a\\over 12} * ({h\\over 1000})^3 \\]\n" +
            "We can measure the theoretical force acting on the submerged surface with the aid of torque, \n" +
            "which can be compared to the real force acting on the surface. The real force on the quadrant would be equal to the weight applied.<br> \n" +
            "Therefore, \n" +
            "Actual force acting on the surface in (\\(kg-m/sec^2 \\)) <b>\\[F_a = {w*g\\over 1000}\\]</b>\n" +
            "And theoretical force(\\(kg-m/sec^2\\)) can be calculated by \\[F_t = {T_t\\over L}\\]\n" +
            "</p>";

    String setup = "To calculate the static thrust exerted by a fluid on a submerged surface, this hydrostatic pressure apparatus was developed to allow the determined magnitude and location of this force to be compared with the theory. \n" +
            "The apparatus consists of a manufactured quadrant mounted on a balanced arm, pivoting on the tip of the knife. The knife-edge of the quadrant coincides with the center of the arc. Thus, when submerged, only the tension on the rectangular end face gives rise to a moment along the knife edge of the hydrostatic force acting on the quadrant. A balancing pan for the supplied weight and an adjustable counter balance are built into the balance arm.\n" +
            "This assembly is placed on the top of an acrylic tank that can be levelled by adjusting screw feet. A spirit level is given on the quadrant's top surface to show that the balance arm is or is not horizontal. Water is fed to the top of the tank and can be drained by a valve on the side of the tank.\n";

    String steps = "1.\tClean up the setup and make it dust free. On the base plate, set the acrylic tank. \n" +
            "\n2.\tUse the level screw given at the bottom of the base plate to level the tank horizontally. \n" +
            "\n3.\tOn the top of the tank, put the left end of the balance arm into the stopper given. \n" +
            "\n4.\tBy placing the pivot given at the arm, on the slot provided at the top of the tank, set the quadrant with the balance arm at the tank. \n" +
            "\n5.\tAt the right end of the balance arm, add the counter weight. At the left end of the balance arm, hang the weight hanger in the slot provided.\n" +
            "\n6.\tAdjust the counter weight position on the balance arm by observing the spirit level provided at the top of the quadrant to bring the quadrant assembly horizontal. \n" +
            "\n7.\tApply all the weights (10 x 50 gm) provided with the set up on the weight hanger. \n" +
            "\n8.\tAnd fill the tank with water up to that condition when arm comes to horizontal position. \n" +
            "\n9.\tRecord the height of liquid level on balanced condition. \n" +
            "\n10.\tRemove one weight from the weight hanger.\n" +
            "\n11.\tOpen the tank's V1 drain valve and allow the water to flow out of the tank before the arm returns to its horizontal position.\n" +
            "\n12.\tClose the V1 valve and note the height of the tank's water level and the weight applied. \n" +
            "\n13.\tAgain remove one weight from the weight hanger and repeat step 11 and 12.\n";

    public void openIntroduction() {
        setTitle("Introduction");
        aimText.setText(aim);
        theoryText.setText(theory);
        theoryFormula1Text.setText(theoryFormula1);
        theoryFormula2Text.setText(theoryFormula2);
        theoryFormula3Text.setText(theoryFormula3);
        introduction.setVisibility(View.VISIBLE);
    }

    public void openAboutSetup() {
        setTitle("About Setup");
        aboutSetup.setVisibility(View.VISIBLE);
        aboutSetupText.setText(setup);
    }

    public void openProcedure() {
        setTitle("Procedure");
        procedure.setVisibility(View.VISIBLE);
        procedureText.setText(steps);
    }

    public void startSimulation() {
        Random r = new Random();
        for (int i = 0; i < 10; i++){
            randErr[i] = minErr[i] + (maxErr[i] - minErr[i]) * r.nextDouble();
            heights[i] = idealHeights[i]*(1 - randErr[i]/100);
        }
        setTitle("Simulation");
        if(obsCount == 10){
            saveButton.setText("Proceed");
            saveButton.setTag("2");
        }
        simulation.setVisibility(View.VISIBLE);
        repeatUpdateHandler.post(new updateBubblePosition());
    }

    public void openObservation() {
        setTitle("Observation");
        createObsTable();

        try{
            Cursor c = observationDatabase.rawQuery("SELECT * FROM centerofpress", null);
            c.moveToFirst();
            int index = c.getColumnIndex("sn");
            float d = c.getInt(index);
            c.close();
            observation.setVisibility(View.VISIBLE);
        }catch(Exception e){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error 404")
                    .setMessage("No readings taken")
                    .show();
        }
    }

    class addWater implements Runnable {
        public void run() {
            if (mAutoIncrement) {
                water += 1;
                waterLvl.setProgress(water);
                currHeight = (20*water - 1800)/47;
                if(currHeight<0)
                    currHeight = 0;
                heightText.setText(String.format(Locale.US, "%s", currHeight));
                repeatUpdateHandler.postDelayed(new addWater(), 100);
            }
        }
    }

    class removeWater implements Runnable {
        public void run() {
            if (mAutoDecrement) {
                water -= 1;
                waterLvl.setProgress(water);
                currHeight = (20*water - 1800)/47;
                if(currHeight<0)
                    currHeight = 0;
                heightText.setText(String.format(Locale.US, "%d", currHeight));
                repeatUpdateHandler.postDelayed(new removeWater(), 100);
            }
        }
    }

    public void changeWeight(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        if (tag == 1) {
            if (weight < 10)
                weight += 1;
        } else {
            if (weight > 0)
                weight -= 1;
        }

        weightText.setText(String.format(Locale.US, "%s", weight * 50));

        if (weight != 0) {
            weightsView.setVisibility(View.VISIBLE);
            weightsView.setImageResource(weightId[weight - 1]);
        } else
            weightsView.setVisibility(View.INVISIBLE);
    }

    class updateBubblePosition implements Runnable {
        public void run() {
            double hErr = 0;
            if (weight != 0)
                hErr = (heights[weight - 1] - currHeight) * 100 / heights[weight - 1];
            if (water > 90) {
                if (weight == 0)
                    bubbleParams.leftMargin = (int)((40/11+31*water/110)*density);
                else
                    bubbleParams.leftMargin = (int) ((29 - hErr) * density);

                if (bubbleParams.leftMargin > 60*density)
                    bubbleParams.leftMargin = (int)(60*density);
            }

            compassBubbleView.setLayoutParams(bubbleParams);
            repeatUpdateHandler.postDelayed(new updateBubblePosition(), 100);
        }
    }

    public void saveDelData(View view){
        int tag = Integer.parseInt(view.getTag().toString());
        Log.i("Button Tag", String.valueOf(tag));
        if (tag == 1){
            if (obsCount < 10){
                obsCount += 1; dataSNo += 1;
                observationDatabase.execSQL("INSERT INTO centerofpress (sn, weight, height) VALUES (" +
                        String.format(Locale.US, "%d, %d, %d)",
                                dataSNo, weight*50, currHeight));
            }
        }
        else if (tag == 0 && obsCount > 0){
            observationDatabase.execSQL("DELETE FROM centerofpress WHERE sn = " + (dataSNo));
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
        sharedPreferences.edit().putInt("serialNo", obsCount).apply();
    }

    public void createObsTable(){
        Cursor c = observationDatabase.rawQuery("SELECT * FROM centerofpress", null);
        c.moveToFirst();
        int sNo = c.getColumnIndex("sn");
        int wIndex = c.getColumnIndex("weight");
        int hIndex = c.getColumnIndex("height");
        try {
            for (int i = 0; i < 10; i++) {
                TableRow row = new TableRow(CenterOfPressure.this);
                //row.setId(1 + i);
                for (int j = 0; j < 3; j++) {
                    int d;
                    if (j == 0)
                        d = c.getInt(sNo);
                    else if (j == 1)
                        d = c.getInt(wIndex);
                    else
                        d = c.getInt(hIndex);
                    TextView tv = new TextView(CenterOfPressure.this);
                    tv.setTextSize(17);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tv.setText(String.valueOf(d));
                    row.addView(tv);
                }
                obsTable.addView(row);
                c.moveToNext();
            }
        } catch (Exception e) {
            Log.i("DATA", "No more data found");
        }
    }

    public void resetObsTable(View view){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete observation table ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        observationDatabase.execSQL("DELETE FROM centerofpress");
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_of_pressure);

        aimText = findViewById(R.id.cop_aimPara);
        theoryText = findViewById(R.id.cop_theoryPara);
        theoryFormula1Text = findViewById(R.id.cop_theory_formula1);
        theoryFormula2Text = findViewById(R.id.cop_theory_formula2);
        theoryFormula3Text = findViewById(R.id.cop_theory_formula3);
        aboutSetupText = findViewById(R.id.cop_aboutSetupPara);
        procedureText = findViewById(R.id.cop_procedurePara);

        introduction = findViewById(R.id.cop_introduction);
        aboutSetup = findViewById(R.id.cop_aboutSetup);
        procedure = findViewById(R.id.cop_procedure);
        observation = findViewById(R.id.cop_observation);

        weightText = findViewById(R.id.cop_weight_text);
        heightText = findViewById(R.id.cop_height_text);
        observationCount = findViewById(R.id.cop_readingsCountText);
        simulation = findViewById(R.id.cop_simulation);
        valve_V1 = findViewById(R.id.cop_valve_V1);
        saveButton = findViewById(R.id.cop_save_button);
        delButton = findViewById(R.id.cop_delete_button);
        add_water = findViewById(R.id.cop_add_water);
        weightsView = findViewById(R.id.cop_weights_image);
        compassBubbleView = findViewById(R.id.cop_compass_bubble);
        waterLvl = findViewById(R.id.cop_tank_waterLevel);
        waterLvl.setMax(550);
        waterLvl.setProgress(water);

        metrics = getResources().getDisplayMetrics();
        density = metrics.density;
        bubbleParams = (ConstraintLayout.LayoutParams) compassBubbleView.getLayoutParams();

        try{
            observationDatabase = this.openOrCreateDatabase("Observation", MODE_PRIVATE, null);
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS centerofpress ( sn INT(2), weight INT(3), height INT(3) )");
        }catch (Exception e){
            e.printStackTrace();
        }

        sharedPreferences = this.getSharedPreferences("com.example.virtualfluidlab.cop", Context.MODE_PRIVATE);
        dataSNo = sharedPreferences.getInt("serialNo", 0);
        obsCount = dataSNo;
        observationCount.setText(String.format("%s of 10", obsCount));

        obsTable = findViewById(R.id.cop_observationTable);

        add_water.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP) && mAutoIncrement) {
                    mAutoIncrement = false;
                }
                return false;
            }
        });

        valve_V1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP) && mAutoDecrement) {
                    mAutoDecrement = false;
                }
                return false;
            }
        });

        add_water.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post(new addWater());
                        return false;
                    }
                }
        );

        valve_V1.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoDecrement = true;
                        repeatUpdateHandler.post(new removeWater());
                        return false;
                    }
                }
        );


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