package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ListView extends AppCompatActivity {

    Button bernoulli;
    Button VNotch;
    Button MetaCenter;

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

        bernoulli = findViewById(R.id.bernoulli);
        VNotch = findViewById(R.id.vnotch);
        MetaCenter = findViewById(R.id.metacenter);

    }
}
