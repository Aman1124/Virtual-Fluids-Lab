package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getActionBar().hide();
        //getSupportActionBar().hide();

        Thread timer = new Thread(){
        public void run(){
            try{
                sleep(5000);
            }
            catch(InterruptedException e){
                Log.i("TAGGED", "An Error Occurred");
                e.printStackTrace();
            } finally {
                Intent intent = new Intent(getApplicationContext(), ListView.class);
                startActivity(intent);
                //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                finish();
                Log.i("TAGGED", "Opening listView activity");
            }
        }
    };
        timer.start();
}

//    @Override
//    protected void onPause(){
//        super.onPause();
//        finish();
//    }
}