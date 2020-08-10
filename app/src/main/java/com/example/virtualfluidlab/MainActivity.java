package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getActionBar().hide();
        getSupportActionBar().hide();

        Thread timer = new Thread(){
        public void run(){
            try{
                sleep(7500);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            } finally {
                Intent intent = new Intent(getApplicationContext(), ListView.class);
                startActivity(intent);
            }
        }
    };
        timer.start();
}

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}