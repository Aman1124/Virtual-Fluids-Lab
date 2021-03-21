package com.example.virtualfluidlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.virtualfluidlab.view.MathJaxWebView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

public class MetaCenter extends AppCompatActivity {

    ScrollView introduction, aboutSetup, procedure;
    RelativeLayout  observation;
    TextView aimText, theoryText1, theoryText2, theoryFormulaRef, aboutSetupText, procedureText1, procedureText2;
    MathJaxWebView theoryFormula, theoryFormula2;
    ImageView theoryImg;

    ConstraintLayout simulation;
    int x_weight = 0;
    LottieAnimationView anim_noWeight, anim_singleWeight, anim_doubleWeight;
    LottieAnimationView anim_transition1, anim_transition2;
    LottieAnimationView[] animations;
    int weights = 0;
    float curr_progress = 0.5f;
    TextView simX, simFwdY, simBackY;
    int[][][] y_dash = {{{107, 96}, {113, 91}, {118, 86}, {124, 81}, {129, 76}, {134, 71}, {139, 66}, {144, 60}, {147, 57}, {148, 55}},
            {{116, 92}, {127, 82}, {136, 73}, {145, 63}, {153, 59}, {164, 55}, {166, 42}, {172, 34}},
            {{176, 31}, {186, 17}}};
    int err;
    Button saveButton;

    SQLiteDatabase observationDatabase;
    SharedPreferences sharedPreferences;
    int dataSNo = 0, obsCount = 0, choice;
    TableLayout obsTable1, obsTable2, obsTable3;

    String aim = "•\tTo determine the meta-centric height with angle of heel of ship model.\n";

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

    String theory_formula2 = "<p align=\"justify\" style=\"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070;\">\n" +
            "\\[W=W_p+m_1+W_A\\]" +
            "\\(y=y''-y'\\) &nbsp; &nbsp; (For R.H.S)<br>" +
            "\\(y=y'-y''\\) &nbsp; &nbsp; (For L.H.S)<br>" +
            "\\[tanθ={y \\over L}\\]" +
            "\\[θ=tan^{-1}\\left({y \\over L}\\right) (°)\\]" +
            "\\[GM = {m_1*x \\over tanθ*W}\\]";

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

    String simProcedure = "1. To apply the load strips on the ship model press 'weight+' key (for 1st time skip this step).\n" +
            "2. Now press left or right move weight arrow key to move hanger at any side (i.e., LHS or RHS respectively) on the slots provided.\n" +
            "3. Measure the displacement of pendulum by the readings that appear on screen and save them by pressing save button.\n" +
            "4. Repeat the experiment for different position of weight applied (i.e., repeat steps 2 and 3 for different position of hanger).\n" +
            "5. Repeat the experiment for different weights.";

    private void openIntroduction() {
        setTitle("Introduction");
        aimText.setText(aim);
        theoryText1.setText(theory);
        theoryText2.setText(theory2);
        theoryImg.setImageResource(R.drawable.meta_th_diagram);
        theoryFormula.setText(theory_formula);
        theoryFormula2.setText(theory_formula2);
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
        simulation.setVisibility(View.VISIBLE);
    }

    private void openObservation() {
        setTitle("Observation");
        if(sharedPreferences.getInt("serialNo", 0) != 0) {
            observation.setVisibility(View.VISIBLE);
            createObsTable();
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error 404")
                    .setMessage("No readings taken")
                    .show();
        }
    }

    public void moveWeight(View view) {
        float final_prog;
        if (weights == 0) {
            switch (view.getTag().toString()) {
                case "1":
                    if (x_weight <= 90)
                        x_weight += 10;
                    break;
                case "2":
                    if (x_weight >= -90)
                        x_weight -= 10;
                    break;
            }
            final_prog = 0.5f - (x_weight / 10f) * 0.05f;
        } else if (weights == 1) {
            switch (view.getTag().toString()) {
                case "1":
                    if (x_weight <= 70)
                        x_weight += 10;
                    break;
                case "2":
                    if (x_weight >= -70)
                        x_weight -= 10;
                    break;
            }
            final_prog = 0.5f + (x_weight / 10f) * 0.0625f;
        } else {
            switch (view.getTag().toString()) {
                case "1":
                    if (x_weight <= 10)
                        x_weight += 10;
                    break;
                case "2":
                    if (x_weight >= -10)
                        x_weight -= 10;
                    break;
            }
            final_prog = 0.5f + (x_weight / 10f) * 0.25f;
        }

        updateAnimation(animations[weights], curr_progress, final_prog);
        curr_progress = final_prog;
        Random random = new Random();
        err = random.nextInt(3);
        if(!random.nextBoolean())
            err *= -1;
        printSimData();
    }

    private void printSimData() {
        if (x_weight > 0) {
            simFwdY.setText(String.valueOf(y_dash[weights][(int) (x_weight / 10f) - 1][0] + err));
            simBackY.setText("");
        } else if (x_weight < 0) {
            simBackY.setText(String.valueOf(y_dash[weights][(int) Math.abs(x_weight / 10f) - 1][1] + err));
            simFwdY.setText("");
        } else {
            simFwdY.setText(String.valueOf(100));
            simBackY.setText("");
        }
        simX.setText(String.valueOf(Math.abs(x_weight)));
    }

    private void updateAnimation(final LottieAnimationView anim, float from, float to) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to).setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                anim.setProgress(Math.abs((float) animation.getAnimatedValue()));
            }
        });
        valueAnimator.start();
    }

    public void saveDelData(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        Log.i("Button Tag", String.valueOf(tag));

        if(x_weight != 0) {
            char dir = x_weight > 0 ? 'f' : 'b';
            addRmvData(tag, weights, dir);
        }

    }

    private void addRmvData(int tag, int index, char dir) {
        int id = 0;
        if(dir == 'b')
            id = 1;
        int max;
        if (index == 0) max = 10;
        else if (index == 1) max = 8;
        else max = 2;
        if (tag == 1) {
            obsCount += 1;
            dataSNo += 1;
            observationDatabase.execSQL("INSERT INTO metacenter" + index + dir + " (sn, x, yf) VALUES (" +
                    String.format(Locale.US, "%d, %d, %d)",
                            dataSNo, Math.abs(x_weight), y_dash[weights][(int) Math.abs(x_weight / 10f) - 1][id] + err));
        } else if(tag == 0 && obsCount>0){
            observationDatabase.execSQL("DELETE FROM metacenter"+ index + dir +" WHERE sn = " + (dataSNo));
            dataSNo -= 1;
            obsCount -= 1;
        }
        sharedPreferences.edit().putInt("serialNo", obsCount).apply();
    }

    public void addWeight(View view) {
        if (weights == 0) {
            anim_noWeight.setVisibility(View.INVISIBLE);
            anim_transition1.setVisibility(View.VISIBLE);
            anim_transition1.setProgress(0f);
            anim_transition1.setSpeed(1);
            anim_transition1.playAnimation();
            switchAnimationOnComplete(anim_transition1, anim_singleWeight);
            weights += 1;
        } else if (weights == 1) {
            anim_singleWeight.setVisibility(View.INVISIBLE);
            anim_transition2.setVisibility(View.VISIBLE);
            anim_transition2.setProgress(0f);
            anim_transition2.setSpeed(1);
            anim_transition2.playAnimation();
            switchAnimationOnComplete(anim_transition2, anim_doubleWeight);
            weights += 1;
        }
        Log.i("Weight", String.valueOf(weights));
        x_weight = 0;
        printSimData();
    }

    public void rmvWeight(View view) {
        if (weights == 2) {
            anim_doubleWeight.setVisibility(View.INVISIBLE);
            anim_singleWeight.setVisibility(View.VISIBLE);
            weights -= 1;
        } else if (weights == 1) {
            anim_singleWeight.setVisibility(View.INVISIBLE);
            anim_noWeight.setVisibility(View.VISIBLE);
            weights -= 1;
        }
        Log.i("Weight", String.valueOf(weights));
        x_weight = 0;
        printSimData();
    }

    private void printVisibility() {
        Log.i("ANIM 0W", String.valueOf(anim_noWeight.getVisibility()));
        Log.i("ANIM 1W", String.valueOf(anim_singleWeight.getVisibility()));
        Log.i("ANIM 2W", String.valueOf(anim_doubleWeight.getVisibility()));
        Log.i("ANIM T01", String.valueOf(anim_transition1.getVisibility()));
        Log.i("ANIM T12", String.valueOf(anim_transition2.getVisibility()));
    }

    private void switchAnimationOnComplete(final LottieAnimationView src, final LottieAnimationView dest) {
        src.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                src.setVisibility(View.INVISIBLE);
                dest.setProgress(0.5f);
                dest.setVisibility(View.VISIBLE);
//                Toast.makeText(MetaCenter.this, "Completed", Toast.LENGTH_SHORT).show();
//                printVisibility();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void createObsTable(){
        loadObsData(0, obsTable1);
        loadObsData(1, obsTable2);
        loadObsData(2, obsTable3);
    }

    private void loadObsData(int tableIndex, TableLayout tableLayout){
        int[][] data = new int[10][3];
        Cursor c = observationDatabase.rawQuery("SELECT * FROM metacenter" + tableIndex + "f", null);
        c.moveToFirst();
        int xIndex = c.getColumnIndex("x");
        int yIndex = c.getColumnIndex("yf");
        int index = 0;
        try {
            for (int i = 0; i < 10; i++){
                boolean found = false;
                for(int j=0; j<10; j++){
                    if(c.getInt(xIndex) == data[j][0]) {
                        data[j][1] = c.getInt(yIndex);
                        found = true;
                    }
                }
                if(!found){
                    data[index][0] = c.getInt(xIndex);
                    data[index][1] = c.getInt(yIndex);
                    index += 1;
                }
                c.moveToNext();
            }
        } catch (Exception e) {
            Log.i("DATA" + tableIndex, "No more data found for fy");
        }
        c.close();
        c = observationDatabase.rawQuery("SELECT * FROM metacenter" + tableIndex + "b", null);
        c.moveToFirst();
        xIndex = c.getColumnIndex("x");
        yIndex = c.getColumnIndex("yf");
        try {
            for (int i = 0; i < 10; i++){
                boolean found = false;
                for(int j=0; j<10; j++){
                    if(c.getInt(xIndex) == data[j][0]) {
                        data[j][2] = c.getInt(yIndex);
                        found = true;
                    }
                }
                if(!found){
                    data[index][0] = c.getInt(xIndex);
                    data[index][2] = c.getInt(yIndex);
                    index += 1;
                }
                c.moveToNext();
            }
        } catch (Exception e) {
            Log.i("DATA" + tableIndex, "No more data found for by");
        }
        c.close();
        Arrays.sort(data, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
                if(first[0] > second[0]) return 1;
                else return -1;
            }
        });

        for(int i=0;i<10;i++)
            Log.i("TABLE" + tableIndex, Arrays.toString(data[i]));

        addRowsToTable(data, tableLayout);
    }

    private void addRowsToTable(int[][] data, TableLayout tableLayout){
        for (int i = 0; i < 10; i++) {
            if(data[i][0] != 0) {
                TableRow row = new TableRow(MetaCenter.this);
                for (int j = 0; j < 3; j++) {
                    int d = data[i][j];
                    TextView tv = new TextView(MetaCenter.this);
                    tv.setTextSize(17);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText(String.valueOf(d));
                    row.addView(tv);
                }
                tableLayout.addView(row);
            }
        }
    }

    public void resetObsTable(View view){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete all observation tables?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        observationDatabase.execSQL("DELETE FROM metacenter0f");
                        observationDatabase.execSQL("DELETE FROM metacenter0b");
                        observationDatabase.execSQL("DELETE FROM metacenter1f");
                        observationDatabase.execSQL("DELETE FROM metacenter1b");
                        observationDatabase.execSQL("DELETE FROM metacenter2f");
                        observationDatabase.execSQL("DELETE FROM metacenter2b");
                    }
                })
                .setNegativeButton("No", null)
                .show();
        obsCount = 0;
        dataSNo = 1;
        sharedPreferences.edit().putInt("serialNo", obsCount).apply();
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
        observation = findViewById(R.id.meta_observation);

        aimText = findViewById(R.id.meta_aimPara);
        theoryText1 = findViewById(R.id.meta_theoryPara1);
        theoryText2 = findViewById(R.id.meta_theoryPara2);
        theoryImg = findViewById(R.id.meta_theoryImg);
        theoryFormula = findViewById(R.id.meta_theory_formula);
        theoryFormula2 = findViewById(R.id.meta_theory_formula2);
        theoryFormulaRef = findViewById(R.id.meta_theory_formulaRef);
        aboutSetupText = findViewById(R.id.meta_aboutSetupPara);
        procedureText1 = findViewById(R.id.meta_procedurePara1);
        procedureText2 = findViewById(R.id.meta_procedurePara2);

        simulation = findViewById(R.id.meta_simulation);
        anim_noWeight = findViewById(R.id.meta_apparatusNoWeight);
        anim_singleWeight = findViewById(R.id.meta_apparatusSingleWeight);
        anim_doubleWeight = findViewById(R.id.meta_apparatusDoubleWeight);
        animations = new LottieAnimationView[]{anim_noWeight, anim_singleWeight, anim_doubleWeight};
        anim_transition1 = findViewById(R.id.meta_apparatusTrans1);
        anim_transition2 = findViewById(R.id.meta_apparatusTrans2);
        simX = findViewById(R.id.meta_simX);
        simFwdY = findViewById(R.id.meta_simFwdY);
        simBackY = findViewById(R.id.meta_simBackY);
        saveButton = findViewById(R.id.meta_save_button);

        obsTable1 = findViewById(R.id.meta_obsTable1);
        obsTable2 = findViewById(R.id.meta_obsTable2);
        obsTable3 = findViewById(R.id.meta_obsTable3);


        try {
            observationDatabase = this.openOrCreateDatabase("Observation", MODE_PRIVATE, null);
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS metacenter0f ( sn INT(2), x INT(3), yf INT(3))");
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS metacenter0b ( sn INT(2), x INT(3), yf INT(3))");
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS metacenter1f ( sn INT(2), x INT(3), yf INT(3))");
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS metacenter1b ( sn INT(2), x INT(3), yf INT(3))");
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS metacenter2f ( sn INT(2), x INT(3), yf INT(3))");
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS metacenter2b ( sn INT(2), x INT(3), yf INT(3))");
        } catch (Exception e) {
            e.printStackTrace();
        }

        sharedPreferences = this.getSharedPreferences("com.example.virtualfluidlab.meta", Context.MODE_PRIVATE);
        dataSNo = sharedPreferences.getInt("serialNo", 0);
        obsCount = dataSNo;

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