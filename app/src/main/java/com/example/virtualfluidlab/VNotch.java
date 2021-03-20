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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Locale;
import java.util.Random;

public class VNotch extends AppCompatActivity {

    ScrollView introduction, aboutSetup, procedure;
    TextView aimText, theoryText, aboutSetupText, procedureText1, procedureText2;

    ConstraintLayout simulation;
    RelativeLayout popUp;
    ImageView setupImage, needle, endViewNeedle, channelWaterFlow;
    TextView x1value, x2value, hValue, tValue, observationCount;
    Button x1orx2, saveButton;
    SeekBar v1, v2;
    LottieAnimationView on_anim, off_anim;
    float anim_progress = 0;
    ProgressBar channel, endView, popUpWater;
    boolean powerOn = false;
    boolean valve1 = false, valve2 = false;
    boolean x = true;
    float x1, x2, h, flow, waterLvl;
    int t, p1, p2;
    RelativeLayout.LayoutParams params1;
    ConstraintLayout.LayoutParams params2, waterParams;

    float density;

    RelativeLayout observation;
    SQLiteDatabase observationDatabase;
    SharedPreferences sharedPreferences;
    int dataSNo = 0, obsCount = 0, choice;
    TableLayout obsTable;

    String aim = "•\tTo determine the discharge coefficient of a triangular notch.\n" +
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

    String simProcedure = "1. Click the power button to turn on the motor.\n" +
            "2. Click on valve v1 (valve attached to the motor). A seekbaar will appear.\n" +
            "3. Drag the seekbar to change the inlet flow rate of water.\n" +
            "4. Adjust the flow such that water level touches the crest of the notch.\n" +
            "5. Click on the front view of the notch. A pop up will open up.\n" +
            "6. Use the +/- buttons to adjust the height of the vernier scale. Once the tip of the scale touches the water level click on the tick button to note the reading.\n" +
            "7. Change the flow rate and repeat steps 5 and 6 to take x2, h and t.\n" +
            "8. Click on save button to save the readings.\n" +
            "9. Change the flow rate and repeat steps 6, 7 and 8 to get more readings.\n" +
            "10. Once all readings are taken, turn off the motor and then exit to home screen. You can also click on the \"Proceed\" button to jump to Observation Table.\n";

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

        try {
            Cursor c = observationDatabase.rawQuery("SELECT * FROM vnotch", null);
            c.moveToFirst();
            int index = c.getColumnIndex("sn");
            float d = c.getInt(index);
            c.close();
            observation.setVisibility(View.VISIBLE);
            createObsTable();
        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error 404")
                    .setMessage("No readings taken")
                    .show();
        }
    }

    public void powerUp(View view) {
        if (!powerOn) {
//            setupImage.setImageResource(R.drawable.v_notch_on);
            on_anim.setVisibility(View.VISIBLE);
            off_anim.setVisibility(View.INVISIBLE);
            powerOn = true;
        } else {
//            setupImage.setImageResource(R.drawable.v_notch_off);
            on_anim.setVisibility(View.GONE);
            off_anim.setProgress(0);
            off_anim.setVisibility(View.VISIBLE);
//            off_anim.reverseAnimationSpeed();
//            off_anim.setSpeed(-1);
//            off_anim.playAnimation();
            powerOn = false;
            setWaterLvl(0);
        }
    }

    public void openValve(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        if (powerOn) {
            if (tag == 1) {
                if (valve1)
                    v1.setVisibility(View.GONE);
                else
                    v1.setVisibility(View.VISIBLE);
                valve1 = !valve1;
            } else if (tag == 2) {
                if (valve2)
                    v2.setVisibility(View.GONE);
                else
                    v2.setVisibility(View.VISIBLE);
                valve2 = !valve2;
            }
        }
    }

    public void openPopUp(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        if (x) {
            x1orx2.setText("x₁");
        } else {
            x1orx2.setText("x₂");
        }

        if (tag == 2)
            x = false;

        if (popUp.getVisibility() == View.GONE && powerOn)
            popUp.setVisibility(View.VISIBLE);
        else
            popUp.setVisibility(View.GONE);

        displaySimData();
    }

    public void changeNeedleHeight(View view) {

        int tag = Integer.parseInt(view.getTag().toString());
        int max_topMargin = (int) (95 * density);
        int min_topMargin = (int) (5 * density);

        if (tag == 1 && params1.topMargin <= max_topMargin) {
            params1.topMargin += (int) (1 * density);
            needle.setLayoutParams(params1);
        } else if (params1.topMargin >= min_topMargin) {
            params1.topMargin -= (int) (1 * density);
            needle.setLayoutParams(params1);
        }

        params2.topMargin = Math.round(params1.topMargin / 3.8f);
        endViewNeedle.setLayoutParams(params2);

        if (x) {
            x1 = 6.72f + (max_topMargin - params1.topMargin) * 0.036f;
            x1orx2.setText(String.format(Locale.US, "x₁ = %.2f", x1));
        } else {
            x2 = 6.72f + (max_topMargin - params1.topMargin) * 0.036f;
            x1orx2.setText(String.format(Locale.US, "x₂ = %.2f", x2));
        }

        Log.i("TAGGED", String.format(Locale.US, "%d %d", params1.topMargin, max_topMargin));
    }

    public void switchX1toX2(View view) {
        Button button = (Button) view;
        if (x) {
            button.setText("x₂");
        } else {
            button.setText("x₁");
        }
        x = !x;
    }

    public void saveDelData(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        Log.i("Button Tag", String.valueOf(tag));
        if (tag == 1) {
            if (obsCount < 10) {
                obsCount += 1;
                dataSNo += 1;
                observationDatabase.execSQL("INSERT INTO vnotch (sn, x1, x2, h, t) VALUES (" +
                        String.format(Locale.US, "%d, %.2f, %.2f, %.2f, %d)",
                                dataSNo, x1, x2, h, t));
            }
        } else if (tag == 0 && obsCount > 0) {
            observationDatabase.execSQL("DELETE FROM vnotch WHERE sn = " + (dataSNo));
            dataSNo -= 1;
            obsCount -= 1;
        } else if (tag == 2) {
            simulation.setVisibility(View.INVISIBLE);
            powerOn = false;
            openObservation();
        }

        if (obsCount == 10) {
            saveButton.setText("Proceed");
            saveButton.setTag("2");
        } else {
            saveButton.setText("Save");
            saveButton.setTag("1");
        }

        observationCount.setText(String.format("%s of 10", obsCount));
        sharedPreferences.edit().putInt("serialNo", obsCount).apply();
    }


    private void calculateData(float flow) {
        float H = (float) (-0.00001268 * Math.pow(flow, 2) + 0.01086 * flow + 0.90044);
        int maxErr = (int) ((0.01151 + 0.04681 * H) * 100);
        float err = (float) ((new Random().nextInt(maxErr)) * 0.01);
        int sign = new Random().nextInt(10);
        if (sign % 2 == 0)
            H += err;
        else
            H -= err;
        t = 10 + new Random().nextInt(5);
        h = flow * t * 0.00111f;

        if (flow > 0)
            waterLvl = H * 23.4375f;
        else
            waterLvl = 0;
        setWaterLvl(Math.round(waterLvl));
        Log.i("DATA", "flow = " + flow + " waterLvl = " + waterLvl + " H = " + H);

        displaySimData();
    }

    private void displaySimData() {
        x1value.setText(String.format(Locale.US, "%.2f", x1));
        x2value.setText(String.format(Locale.US, "%.2f", x2));
        hValue.setText(String.format(Locale.US, "%.2f", h));
        tValue.setText(String.format(Locale.US, "%d", t));
    }

    private void setWaterLvl(int level) {
//        channel.setProgress(level);
        endView.setProgress(level);
        popUpWater.setProgress(level);
//        if(level>32) {
//            float topMargin = (float) ((-0.0125f * Math.pow(level, 2) + 1.025f * level + 63.438f)*density);
//            float rightMargin = (127.5f - 0.9f * level)*density;
//            waterParams.topMargin = Math.round(topMargin);
//            waterParams.rightMargin = Math.round(rightMargin);
//            channelWaterFlow.setLayoutParams(waterParams);
//            Log.i("TAGGED", level + "  TM = " + waterParams.topMargin + " RM = " + waterParams.rightMargin);
//        } else {
//            waterParams.topMargin = Math.round(88*density);
//            waterParams.rightMargin = Math.round(105*density);
//            channelWaterFlow.setLayoutParams(waterParams);
//            Log.i("TAGGED", level + "  TM = " + waterParams.topMargin + " RM = " + waterParams.rightMargin);
//        }
        on_anim.setProgress(anim_progress);
    }

    private void createObsTable() {
        Cursor c = observationDatabase.rawQuery("SELECT * FROM vnotch", null);
        c.moveToFirst();
        int sNo = c.getColumnIndex("sn");
        int x1Index = c.getColumnIndex("x1");
        int x2Index = c.getColumnIndex("x2");
        int hIndex = c.getColumnIndex("h");
        int tIndex = c.getColumnIndex("t");
        try {
            for (int i = 0; i < 10; i++) {
                TableRow row = new TableRow(VNotch.this);
                //row.setId(1 + i);
                int d = 0;
                float x = 0;
                for (int j = 0; j < 5; j++) {
                    if (j == 0)
                        d = c.getInt(sNo);
                    else if (j == 1)
                        x = c.getFloat(x1Index);
                    else if (j == 2)
                        x = c.getFloat(x2Index);
                    else if (j == 3)
                        x = c.getFloat(hIndex);
                    else
                        x = c.getFloat(tIndex);
                    TextView tv = new TextView(VNotch.this);
                    tv.setTextSize(17);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    if (j == 0)
                        tv.setText(String.valueOf(d));
                    else
                        tv.setText(String.valueOf(x));
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

    public void resetObsTable(View view) {
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

    @Override
    public void onBackPressed() {
        if (!powerOn) {
//            Intent intent = new Intent(getApplicationContext(), ListView.class);
//            startActivity(intent);
//            finish();
            super.onBackPressed();
        }
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
        popUp = findViewById(R.id.vNotch_endView_popUp);
        setupImage = findViewById(R.id.vNotch_setupImage);
        v1 = findViewById(R.id.vNotch_seekBarV1);
        v1.setMax(400);
        v2 = findViewById(R.id.vNotch_seekBarV2);
        x1value = findViewById(R.id.vNotch_simX1);
        x2value = findViewById(R.id.vNotch_simX2);
        hValue = findViewById(R.id.vNotch_simH);
        tValue = findViewById(R.id.vNotch_simT);
        needle = findViewById(R.id.vNotch_needle);
        on_anim = findViewById(R.id.vNotch_on_anim);
        off_anim = findViewById(R.id.vNotch_off_anim);
        endViewNeedle = findViewById(R.id.vNotch_endView_needle);
        channelWaterFlow = findViewById(R.id.vNotch_waterFlow);
        x1orx2 = findViewById(R.id.vNotch_x1orx2);
        x1orx2.setText("x₁");
        channel = findViewById(R.id.vNotch_channel_waterLvl);
        channel.setProgress(0);
        endView = findViewById(R.id.vNotch_endView_waterLvl);
        endView.setProgress(0);
        popUpWater = findViewById(R.id.vNotch_popUp_waterLvl);
        popUpWater.setProgress(0);

        saveButton = findViewById(R.id.vNotch_save_button);
        observationCount = findViewById(R.id.vNotch_readingsCountText);

        observation = findViewById(R.id.vNotch_observation);
        obsTable = findViewById(R.id.vNotch_observationTable);

        params1 = (RelativeLayout.LayoutParams) needle.getLayoutParams();
        params2 = (ConstraintLayout.LayoutParams) endViewNeedle.getLayoutParams();
        waterParams = (ConstraintLayout.LayoutParams) channelWaterFlow.getLayoutParams();

        v1.setOnSeekBarChangeListener(changeListener);
        v2.setOnSeekBarChangeListener(changeListener);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        density = metrics.density;


        try {
            observationDatabase = this.openOrCreateDatabase("Observation", MODE_PRIVATE, null);
            observationDatabase.execSQL("CREATE TABLE IF NOT EXISTS vnotch ( sn INT(2), x1 FLOAT, x2 FLOAT, h FLOAT, t INT(2) )");
        } catch (Exception e) {
            e.printStackTrace();
        }

        sharedPreferences = this.getSharedPreferences("com.example.virtualfluidlab.vNotch", Context.MODE_PRIVATE);
        dataSNo = sharedPreferences.getInt("serialNo", 0);
        obsCount = dataSNo;
        observationCount.setText(String.format("%s of 10", obsCount));

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

    SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(seekBar.getTag().toString().equals("1"))
                anim_progress = progress/400f;
            if (progress >= 25) {
                if (seekBar.getTag().toString().equals("1")) {
                    p1 = Math.round(progress * 1.039f - 15.585f);
                } else if (progress > 40)
                    p2 = progress;
                flow = (p1 - p2) + (float) ((10 + (new Random().nextInt(90))) * 0.01);
                calculateData(flow);
            } else {
                calculateData(0);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.i("TAGGED", "Water lvl = " + Math.round(waterLvl));
            seekBar.setVisibility(View.GONE);
        }
    };
}