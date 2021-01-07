package com.example.virtualfluidlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class ListView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView introduction;
    TextView aboutSetup;
    TextView procedure;
    TextView simulation;
    TextView observationTable;
    TextView selfAssessment;

    DrawerLayout drawerLayout;
    RelativeLayout listLayout;
    RelativeLayout action_menu;
    LinearLayout optionsLayout;

    NavigationView navigationView;

    ImageView bernoulli;
    ImageView wind_tunnel_float, reynolds_float, centerOfPressFloat;
    ImageView pitotFloat;
    ImageView drawer;

    ImageView[] floatBoxes;

    int[] locationOfBernoulli = new int[2];
    int[] locationOfVNotch = new int[2];
    int[] drawerLocation = new int[2];

    int scroll = 2, drawerPos = 0;
    float displacementX;
    long duration = 200;
    private float x1,x2,y1,y2;
    static final int MIN_DISTANCE = 200;


    public void switchToReynolds(View view){
        Intent intent = new Intent(getApplicationContext(), ReynoldsNumber.class);
        intent.putExtra("choice", Integer.parseInt(view.getTag().toString()));
        startActivity(intent);
    }

    public void switchToBernoulli(View view){
        Intent intent = new Intent(getApplicationContext(),Bernoulli.class);
        intent.putExtra("choice", Integer.parseInt(view.getTag().toString()));
        startActivity(intent);
    }

    public void switchToPitot(View view){
        Intent intent = new Intent(getApplicationContext(),Pitot_Tube.class);
        intent.putExtra("choice", Integer.parseInt(view.getTag().toString()));
        startActivity(intent);
    }

    public void switchToWindTunnel(View view){
        Intent intent = new Intent(getApplicationContext(),WindTunnel.class);
        intent.putExtra("choice", Integer.parseInt(view.getTag().toString()));
        startActivity(intent);
    }

    public void switchToCenterofPress(View view){
        Intent intent = new Intent(getApplicationContext(),CenterOfPressure.class);
        intent.putExtra("choice", Integer.parseInt(view.getTag().toString()));
        startActivity(intent);
    }

    public void openSelfAssessment(View view){
        Intent intent = new Intent(getApplicationContext(), MCQs.class);
        intent.putExtra("choice", scroll);
        startActivity(intent);
    }

    public void openExperiment(View view){
        if(scroll == 0)
            switchToReynolds(view);
        else if(scroll == 1)
            switchToWindTunnel(view);
        else if(scroll == 2)
            switchToBernoulli(view);
        else if(scroll == 3)
            switchToPitot(view);
        else if (scroll == 4)
            switchToCenterofPress(view);
    }

    public void scrollFloatBox(boolean direction){
        //True: left to right movement
        if (drawerPos == 0) {
            if (direction && scroll > 0) {
                bernoulli.animate().translationXBy(displacementX).setDuration(duration);
                wind_tunnel_float.animate().translationXBy(displacementX).setDuration(duration);
                reynolds_float.animate().translationXBy(displacementX).setDuration(duration);
                centerOfPressFloat.animate().translationXBy(displacementX).setDuration(duration);
                pitotFloat.animate().translationXBy(displacementX).setDuration(duration);
                if (scroll >= 1)
                    scroll -= 1;
//                floatBoxes[scroll].animate().scaleX(1f).scaleY(1f).setDuration(duration);
//                for(int i=0; i<5; i++)
//                    if(i!=scroll)
//                        floatBoxes[i].animate().scaleX(0.9f).scaleY(0.9f).setDuration(duration);
            } else if (!direction && scroll < 4) {
                bernoulli.animate().translationXBy(-displacementX).setDuration(duration);
                wind_tunnel_float.animate().translationXBy(-displacementX).setDuration(duration);
                reynolds_float.animate().translationXBy(-displacementX).setDuration(duration);
                centerOfPressFloat.animate().translationXBy(-displacementX).setDuration(duration);
                pitotFloat.animate().translationXBy(-displacementX).setDuration(duration);
                if (scroll <= 3)
                    scroll += 1;
//                floatBoxes[scroll].animate().scaleX(1f).scaleY(1f).setDuration(duration);
//                for(int i=0; i<5; i++)
//                    if(i!=scroll)
//                        floatBoxes[i].animate().scaleX(0.9f).scaleY(0.9f).setDuration(duration);
            }
        }
    }

    public void pullDrawer(boolean direction){
        if( direction && drawerPos == 0 ){
            drawer.animate().translationYBy(-2.2f*locationOfBernoulli[1]).setDuration(duration);
            optionsLayout.animate().translationYBy(-2.2f*locationOfBernoulli[1]).setDuration(duration);
            bernoulli.animate().alpha(0.45f).setDuration(duration);
            wind_tunnel_float.animate().alpha(0.45f).setDuration(duration);
            pitotFloat.animate().alpha(0.45f).setDuration(duration);
            drawer.getLocationOnScreen(drawerLocation);
            //Toast.makeText(this,"Y: " + drawerLocation[1] + "\nBernoulli: " + locationOfBernoulli[1],Toast.LENGTH_SHORT).show();
        }
        else if(!direction && drawerPos == 1){
            drawer.animate().translationYBy(2.2f*locationOfBernoulli[1]).setDuration(duration);
            optionsLayout.animate().translationYBy(2.2f*locationOfBernoulli[1]).setDuration(duration);
            bernoulli.animate().alpha(1f).setDuration(duration);
            wind_tunnel_float.animate().alpha(1f).setDuration(duration);
            pitotFloat.animate().alpha(1f).setDuration(duration);
            drawer.getLocationOnScreen(drawerLocation);
            //Toast.makeText(this,"Y: " + drawerLocation[1] + "\nBernoulli: " + locationOfBernoulli[1],Toast.LENGTH_SHORT).show();
        }
    }

    public void getCoordinate(){
        bernoulli.getLocationInWindow(locationOfBernoulli);
        wind_tunnel_float.getLocationInWindow(locationOfVNotch);
        displacementX = locationOfBernoulli[0]-locationOfVNotch[0];
        drawer.getLocationInWindow(drawerLocation);
        //Toast.makeText(this,"X: " + locationOfBernoulli[0] + "  X: " + locationOfVNotch[0] + "\nY: " + locationOfBernoulli[1] + "  Y: " + locationOfVNotch[1],Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.i("Back","Pressed");
            Log.i("Drawer Status", Integer.toString(drawerPos));
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            if( drawerPos == 1) {
                pullDrawer(false);
                drawerPos = 0;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
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

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        bernoulli = findViewById(R.id.bernoulliFloat);
        wind_tunnel_float = findViewById(R.id.wind_tunnel_float);
        reynolds_float = findViewById(R.id.reynolds_float);
        pitotFloat = findViewById(R.id.pitotFloat);
        centerOfPressFloat = findViewById(R.id.centerOfPressFloat);
        drawer = findViewById(R.id.drawer);

        floatBoxes = new ImageView[]{reynolds_float, wind_tunnel_float, bernoulli, pitotFloat, centerOfPressFloat};

        listLayout = findViewById(R.id.listLayout);
        optionsLayout = findViewById(R.id.optionsLayout);
        action_menu = findViewById(R.id.action_menu_presenter);

//        setTitle("Fluids Lab");
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.actionbar_layout);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        setTitle("Fluids Lab");
        drawerLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimary));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
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
                if (Math.abs(deltaX) > MIN_DISTANCE && deltaX > 0 && x1>20 && !drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    //getCoordinate();
                    scrollFloatBox(true);
                }
                else if (Math.abs(deltaX) > MIN_DISTANCE && deltaX < 0 && x1>20 && !drawerLayout.isDrawerOpen(GravityCompat.START))
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
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Intent actionBar = new Intent(getApplicationContext(), ActionMenu.class);

        switch (menuItem.getItemId()){
            case R.id.home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.references:
                actionBar.putExtra("option",2);
                startActivity(actionBar);
                break;
            case R.id.additional_resources:
                actionBar.putExtra("option", 3);
                startActivity(actionBar);
                break;
            case R.id.displayDetails:
                actionBar.putExtra("option",4);
                startActivity(actionBar);
                break;
            case R.id.help:
                actionBar.putExtra("option",5);
                startActivity(actionBar);
                break;
            case R.id.about:
                actionBar.putExtra("option",1);
                startActivity(actionBar);
                break;
            default:
                break;
        }
        return true;
    }
}
