package com.example.virtualfluidlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.MergeCursor;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListView extends AppCompatActivity {

    TextView introduction;
    TextView aboutSetup;
    TextView procedure;
    TextView simulation;
    TextView observationTable;
    TextView selfAssessment;


    RelativeLayout listLayout;
    RelativeLayout action_menu;
    LinearLayout optionsLayout;

    ImageView bernoulli;
    ImageView vNotch;
    ImageView metaCenter;
    ImageView drawer;


    int[] locationOfBernoulli = new int[2];
    int[] locationOfVNotch = new int[2];
    int[] drawerLocation = new int[2];

    int scroll = 2, drawerPos = 0;
    float displacementX;
    long duration = 200;
    private float x1,x2,y1,y2;
    static final int MIN_DISTANCE = 300;


    public void switchToBernoulli(View view){
        Intent intent = new Intent(getApplicationContext(),Bernoulli.class);
        intent.putExtra("choice", Integer.parseInt(view.getTag().toString()));
        startActivity(intent);
    }

    public void openSelfAssessment(View view){
        Intent intent = new Intent(getApplicationContext(), MCQs.class);
        startActivity(intent);
    }

    public void switchToPitot(View view){
        Intent intent = new Intent(getApplicationContext(),Pitot_Tube.class);
        intent.putExtra("choice", Integer.parseInt(view.getTag().toString()));
        startActivity(intent);
    }

    public void switchToMetacenter(View view){

    }

    public void openExperiment(View view){
        if(scroll == 1)
            switchToMetacenter(view);
        else if(scroll == 2)
            switchToBernoulli(view);
        else if(scroll == 3)
            switchToPitot(view);
    }

    public void scrollFloatBox(boolean direction){
        //True: left to right movement
        if (drawerPos == 0) {
            if (direction && (scroll == 2 || scroll == 3)) {
                bernoulli.animate().translationXBy(displacementX).setDuration(duration);
                vNotch.animate().translationXBy(displacementX).setDuration(duration);
                metaCenter.animate().translationXBy(displacementX).setDuration(duration);
                if (scroll > 1)
                    scroll -= 1;
            } else if (!direction && (scroll == 2 || scroll == 1)) {
                bernoulli.animate().translationXBy(-displacementX).setDuration(duration);
                vNotch.animate().translationXBy(-displacementX).setDuration(duration);
                metaCenter.animate().translationXBy(-displacementX).setDuration(duration);
                if (scroll < 3)
                    scroll += 1;
            }
        }
    }

    public void pullDrawer(boolean direction){
        if( direction && drawerPos == 0 ){
            drawer.animate().translationYBy(-2.2f*locationOfBernoulli[1]).setDuration(duration);
            optionsLayout.animate().translationYBy(-2.2f*locationOfBernoulli[1]).setDuration(duration);
            bernoulli.animate().alpha(0.45f).setDuration(duration);
            vNotch.animate().alpha(0.45f).setDuration(duration);
            metaCenter.animate().alpha(0.45f).setDuration(duration);
            drawer.getLocationOnScreen(drawerLocation);
            //Toast.makeText(this,"Y: " + drawerLocation[1] + "\nBernoulli: " + locationOfBernoulli[1],Toast.LENGTH_SHORT).show();
        }
        else if(!direction && drawerPos == 1){
            drawer.animate().translationYBy(2.2f*locationOfBernoulli[1]).setDuration(duration);
            optionsLayout.animate().translationYBy(2.2f*locationOfBernoulli[1]).setDuration(duration);
            bernoulli.animate().alpha(1f).setDuration(duration);
            vNotch.animate().alpha(1f).setDuration(duration);
            metaCenter.animate().alpha(1f).setDuration(duration);
            drawer.getLocationOnScreen(drawerLocation);
            //Toast.makeText(this,"Y: " + drawerLocation[1] + "\nBernoulli: " + locationOfBernoulli[1],Toast.LENGTH_SHORT).show();
        }
    }

    public void getCoordinate(){
        bernoulli.getLocationInWindow(locationOfBernoulli);
        vNotch.getLocationInWindow(locationOfVNotch);
        displacementX = locationOfBernoulli[0]-locationOfVNotch[0];
        drawer.getLocationInWindow(drawerLocation);
        //Toast.makeText(this,"X: " + locationOfBernoulli[0] + "  X: " + locationOfVNotch[0] + "\nY: " + locationOfBernoulli[1] + "  Y: " + locationOfVNotch[1],Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.i("Back","Pressed");
            Log.i("Drawer Status", Integer.toString(drawerPos));
            if( drawerPos == 1) {
                pullDrawer(false);
                drawerPos = 0;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent actionBar = new Intent(getApplicationContext(), ActionMenu.class);
        switch(item.getItemId()){
            case R.id.about:
                actionBar.putExtra("option",1);
                startActivity(actionBar);
                break;
            case R.id.references:
                actionBar.putExtra("option",2);
                startActivity(actionBar);
                break;
            case R.id.displayDetails:
                actionBar.putExtra("option",3);
                startActivity(actionBar);
                break;
            case R.id.help:
                actionBar.putExtra("option",4);
                startActivity(actionBar);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        introduction = findViewById(R.id.introduction);
        aboutSetup = findViewById(R.id.aboutSetup);
        procedure = findViewById(R.id.procedure);
        simulation = findViewById(R.id.simulation);
        observationTable = findViewById(R.id.observation);
        selfAssessment = findViewById(R.id.selfAssessment);

        bernoulli = findViewById(R.id.bernoulliFloat);
        vNotch = findViewById(R.id.vNotch);
        metaCenter = findViewById(R.id.pitotFloat);
        drawer = findViewById(R.id.drawer);

        listLayout = findViewById(R.id.listLayout);
        optionsLayout = findViewById(R.id.optionsLayout);
        action_menu = findViewById(R.id.action_menu_presenter);

        setTitle("Fluids Lab");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                getCoordinate();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;
                if (Math.abs(deltaX) > MIN_DISTANCE && deltaX > 0)
                {
                    //getCoordinate();
                    scrollFloatBox(true);
                }
                else if (Math.abs(deltaX) > MIN_DISTANCE && deltaX < 0)
                {
                    //getCoordinate();
                    scrollFloatBox(false);
                }
                else if (Math.abs(deltaY) > MIN_DISTANCE && deltaY < 0)
                {
                    pullDrawer(true);
                    drawerPos = 1;
                    //Toast.makeText(this,"DrawerUp " + drawerPos,Toast.LENGTH_SHORT).show();
                }
                else if (Math.abs(deltaY) > MIN_DISTANCE && deltaY > 0)
                {
                    pullDrawer(false);
                    drawerPos = 0;
                    //Toast.makeText(this,"DrawerDown " + drawerPos,Toast.LENGTH_SHORT).show();
                }
                break;
        }
        //Toast.makeText(this, "X:"+ (x2-x1) + "  Y:" + (y2-y1), Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
}
