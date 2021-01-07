package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VerticalSeekBar;

import java.util.concurrent.TimeUnit;

public class CenterOfPressure extends AppCompatActivity {

    TextView aimText, theoryText, aboutSetupText, procedureText;
    ScrollView introduction, aboutSetup, procedure;
    ImageView weightsView, compassBubbleView;
    ConstraintLayout simulation;
    ImageButton add_water;
    Button valve_V1;
    ProgressBar waterLvl;

    ConstraintLayout.LayoutParams bubbleParams;
    DisplayMetrics metrics;
    float density;

    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;

    int choice, water = 75, weight = 0;
    int weightId[] = new int[]{
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

    public void openIntroduction(){
        setTitle("Introduction");
        introduction.setVisibility(View.VISIBLE);
        aimText.setText(aim);
        theoryText.setText(theory);
    }

    public void openAboutSetup(){
        setTitle("About Setup");
        aboutSetup.setVisibility(View.VISIBLE);
        aboutSetupText.setText(setup);
    }

    public void openProcedure(){
        setTitle("Procedure");
        procedure.setVisibility(View.VISIBLE);
        procedureText.setText(steps);
    }

    public void startSimulation(){
        setTitle("Simulation");
        simulation.setVisibility(View.VISIBLE);
        repeatUpdateHandler.post(new updateBubblePosition() );
    }

    public void openObservation(){
        setTitle("Observation");
    }

    class addWater implements Runnable {
        public void run() {
            if( mAutoIncrement ){
                water += 1;
                waterLvl.setProgress(water);
                repeatUpdateHandler.postDelayed( new addWater(), 100);
            }
        }
    }

    class removeWater implements Runnable {
        public void run() {
            if( mAutoDecrement ){
                water -= 1;
                waterLvl.setProgress(water);
                repeatUpdateHandler.postDelayed( new removeWater(), 100);
            }
        }
    }

    public void changeWeight(View view){
        int tag = Integer.parseInt(view.getTag().toString());
        if(tag == 1){
            if(weight<10)
                weight += 1;
        }
        else{
            if(weight>0)
                weight -= 1;
        }

        if(weight != 0) {
            weightsView.setVisibility(View.VISIBLE);
            weightsView.setImageResource(weightId[weight - 1]);
        }
        else
            weightsView.setVisibility(View.INVISIBLE);
    }

    class updateBubblePosition implements Runnable{
        public void run() {
            bubbleParams.leftMargin = (int) ((35 - (30 * water) / 375 + weight * 3) * density);
            compassBubbleView.setLayoutParams(bubbleParams);
            repeatUpdateHandler.postDelayed( new updateBubblePosition(), 100);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_of_pressure);

        aimText = findViewById(R.id.cop_aimPara);
        theoryText = findViewById(R.id.cop_theoryPara);
        aboutSetupText = findViewById(R.id.cop_aboutSetupPara);
        procedureText = findViewById(R.id.cop_procedurePara);

        introduction = findViewById(R.id.cop_introduction);
        aboutSetup = findViewById(R.id.cop_aboutSetup);
        procedure = findViewById(R.id.cop_procedure);

        simulation = findViewById(R.id.cop_simulation);
        valve_V1 = findViewById(R.id.cop_valve_V1);
        add_water = findViewById(R.id.cop_add_water);
        weightsView = findViewById(R.id.cop_weights_image);
        compassBubbleView = findViewById(R.id.cop_compass_bubble);
        waterLvl = findViewById(R.id.cop_tank_waterLevel); waterLvl.setMax(500); waterLvl.setProgress(water);

        metrics = getResources().getDisplayMetrics();
        density = metrics.density;
        bubbleParams = (ConstraintLayout.LayoutParams) compassBubbleView.getLayoutParams();

        add_water.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP) && mAutoIncrement ){
                    mAutoIncrement = false;
                }
                return false;
            }
        });

        valve_V1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if((event.getAction() == MotionEvent.ACTION_UP) && mAutoDecrement){
                    mAutoDecrement = false;
                }
                return false;
            }
        });

        add_water.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post( new addWater() );
                        return false;
                    }
                }
        );

        valve_V1.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        mAutoDecrement = true;
                        repeatUpdateHandler.post( new removeWater() );
                        return false;
                    }
                }
        );


        Intent intent = getIntent();
        choice = intent.getIntExtra("choice",0);

        switch (choice){
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