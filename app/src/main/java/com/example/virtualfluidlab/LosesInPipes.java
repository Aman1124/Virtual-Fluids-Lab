package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;

import java.io.File;

public class LosesInPipes extends AppCompatActivity {

    ScrollView introduction, aboutSetup, procedure;
    TextView aimText, theoryText, aboutSetupText, procedureText1, procedureText2;

    SQLiteDatabase observationDatabase;
    SharedPreferences sharedPreferences;
    int dataSNo = 0, obsCount = 0, choice;
    TableLayout obsTable;

    String aim = "";
    String theory = "";
    String setup = "";
    String labProcedure = "";
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
    }

    private void openObservation() {
        setTitle("Observation");
    }

    public void exportAsExcel(View view){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "VFL");
        if(!file.exists())
            file.mkdirs();
        SQLiteToExcel sqLiteToExcel = new SQLiteToExcel(this, "Observation", file.getAbsolutePath());
        sqLiteToExcel.exportSingleTable("losesinpipes","Loses_in_Pipes.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
                Toast.makeText(LosesInPipes.this, "Exporting...", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(LosesInPipes.this, "Successfully exported to /Downloads/VFL/", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(LosesInPipes.this, "An error occurred!!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(getApplicationContext(), ListView.class);
//        startActivity(intent);
//        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loses_in_pipes);

        introduction = findViewById(R.id.lip_introduction);
        aboutSetup = findViewById(R.id.lip_aboutSetup);
        procedure = findViewById(R.id.lip_procedure);

        aimText = findViewById(R.id.lip_aimPara);
        theoryText = findViewById(R.id.lip_theoryPara);
        aboutSetupText = findViewById(R.id.lip_aboutSetupPara);
        procedureText1 = findViewById(R.id.lip_procedurePara1);
        procedureText2 = findViewById(R.id.lip_procedurePara2);

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