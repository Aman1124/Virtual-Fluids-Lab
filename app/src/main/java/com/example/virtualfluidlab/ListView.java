package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListView extends AppCompatActivity {

    TextView introduction;
    TextView aboutSetup;
    TextView procedure;
    TextView simulation;
    TextView observationTable;
    TextView selfAssessment;


    public void switchToBernoulli(View view){
        Intent intent = new Intent(getApplicationContext(),Bernoulli.class);
        startActivity(intent);
    }

    public void switchToVnotch(View view){

    }

    public void switchToMetacenter(View view){

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        introduction = findViewById(R.id.introduction);
        aboutSetup = findViewById(R.id.aboutSetup);
        procedure = findViewById(R.id.procedure);



    }
}
