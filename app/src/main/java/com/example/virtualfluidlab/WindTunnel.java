package com.example.virtualfluidlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.w3c.dom.Text;

public class WindTunnel extends AppCompatActivity {

    TextView aimText, para1, para2, para3, para4, aboutSetupPara, procedurePara;
    TextView[] para;
    ImageView para1_arrow, para2_arrow, para3_arrow, para4_arrow;
    ImageView[] para_arrow;

    ScrollView introduction, aboutSetup, procedure;
    ConstraintLayout simulation;
    LinearLayout para3_images;

    ImageView ballImage;
    LottieAnimationView[] ballBox;
    String selectedBall = "";

    LinearLayout.LayoutParams openParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams closeParams = new LinearLayout.LayoutParams(0, 0);

    int choice;

    String aim = "• Comparison of drag for shapes of equal equatorial diameter\n" +
            "• Visualisation of flow around different body shapes\n" +
            "• Measurement of the wake profile behind different shapes (requires C14-15)";

    String theory1 = "Two kinds of drag force are encountered by a body moving through a fluid: pressure drag, " +
            "which is a result of shifting the movement of the air particles and causing eddies and wake, and friction drag, " +
            "which is the result of shear forces between the body and the air layer moving around it.  \n" +
            "Among them, the overall drag on the body is result of pressure drag and friction drag. The proportion of each of them depends on the body's design. When the main component of the total drag is friction drag, the body is defined as streamlined. " +
            "The body is known as a bluff if the main component is pressure drag (or blunt). With the Reynolds number of the flow, both forms of drag differ, but the friction drag is much more susceptible to changes in the Reynolds number. Consequently, " +
            "friction drag appears to become more important at higher flow speeds.\n";

    String theory2 = "Flow around a body can move in smooth layers with little to no mixing between layers, which is known as laminar flow. Alternatively, flow may shift to its velocity with a large lateral portion, with eddies, blending, and even some flow to the average in the reverse direction. " +
            "This is called turbulent flow. The cumulative flow around a body can show all kinds of flow in certain cases.\n";

    String theory3 = "The growth of friction drag means that on the body there is a force exerted by the fluid. The fluid itself is slowed when exerting this force. At the body surface, the slower fluid particles then exert a drag force further away from the body on the faster fluid particles. While the boundary layer is infinite in principle, the importance of the slowing of fluid particles (the viscosity effect) decreases with distance from the body in practice. The boundary layer is called the area close to the body where viscous effects are meaningful. The area in which the flow velocity is less than 99 percent of the free stream velocity is generally considered to be the boundary layer. \n" +
            "Flow inside the boundary layer may be laminar or turbulent, and when it moves through the body, it may change from laminar to turbulent. A change from one form to the other is known as the boundary layer transition. The development of the boundary layer is influenced by the flow form within the boundary layer, the number of Reynolds, the body shape, and the body's surface roughness. The Reynolds number, the surface roughness, and the presence of large irregularities in the shape or surface of the body also affect the type of boundary layer. \n" +
            "In a narrow wake, the boundary layer may wrap right around the body and then travel downstream, or it may at some point detach from the body and travel downstream in a wide wake. The Reynolds number, the shape of the body, and the type of flow influence the point at which separation occurs. Below, some examples are illustrated.\n";

    String theory4 = "The drag experienced by the body is greatly influenced by boundary layer type and separation. The ability to monitor the characteristics of the boundary layer is therefore of great importance to engineers and designers in fields where drag is a significant factor, such as aircraft design. The variables that can be regulated are the shape of a body and its surface finish, as the Reynolds number is often set by the conditions under which an object must function. By inserting a deliberate barrier on an otherwise smooth surface, such as a series of bumps or a wire, the boundary layer may even be 'tripped' into turbulent conditions.\n";

    String[] theory = new String[]{theory1, theory2, theory3, theory4};

    String about_setup = "Equipment Required: \n" +
            "\t•\tC15-10 Wind Tunnel with IFD7 \n" +
            "\t•\tPC running C15-304 software \n" +
            "\t•\tC15-22 Drag Models \n" +
            "\t•\tC15-13 Lift and Drag balance \n" +
            "\nOptional equipment: \n" +
            "\t•\tC15-15 Wake Survey Rake \n" +
            "\t•\tCamera (and tripod) for recording flow visualisation results \n" +
            "\n" +
            "Equipment Set Up : \n" +
            "The tunnel should be set up with a flow visualization tube connected to the upstream roof tapping. In the working area, position the arm supporting the thread a bit above half the height (closer to the roof than to the floor). Blanking plugs should be fitted with the other two roof taps. \n" +
            "The lift and drag balance should be mounted to the flat circular disk, and the balance should then be fitted into the wide circular hatch, with the face of the model flat in the working section's flow direction. The wake survey rake can be fitted into the small hatch if available. If the wake survey rake is not used, the simple hatch cover should be fitted with the small hatch. \n" +
            "Ensure that the floor is in place. Check the environment to ensure that the inlet or exit of the tunnel is not obstructed and there are no loose objects nearby that could pose a threat. \n" +
            "The single pressure tapping tube on the side wall of the working section (near the inlet) should be connected to the 1-way quick-release fitting on the black box attached to the tunnel frame. Attach the cable to the front of the IFD7 from the lift and drag balance. Attach the tubes from the rake to the manometer used when using the wake survey rake. If you use an electronic manometer, use the IFD cable to connect the manometer to an appropriate PC. \n" +
            "Check that the IFD7 is connected to an appropriate electrical power supply and to the appropriate PC's USB port. It is important to turn the PC on and run the appropriate software version (C15-11 version or C15-12 version depending on the manometer used). Pick 'Exercise D' and make sure that in the bottom right-hand corner 'IFD: OK' is shown.\n" +
            "Using the mains switch on the front to switch on the IFD7.\n" +
            "\n" +
            "Camera setup:\n" +
            "The camera should be positioned in front of the round hatch to provide a good field of view around the cylinder if a still or video camera and tripod are usable. A clear backdrop behind the wind tunnel is suggested (e.g. a plain sheet of white paper may be attached to the back of the tunnel, on the outside). Choose camera settings that offer the fastest shutter speed possible and then the best possible field depth at that speed. The use of flash can create inconvenient reflections on the sides of the working section, so adequate lighting to eliminate the need for flash can be used where available. \n" +
            "The flow visualization thread should be in sharp focus for the best images, which can be difficult to achieve, particularly if autofocus is used. If the camera can be pre-focused, via the roof tapping, a focus guide such as the Pitot tube arm or a similar narrow object can be inserted temporarily. The guide should be removed and the blanking plug replaced until the camera is fixed on the guide. Take a test shot if possible and display the results at a reasonable size to verify that the images will be acceptable. \n";

    String procedureText = "1)\tBy clicking the 'Fan On' button on the mimic diagram, make sure the fan is set to 0 percent, then switch it out of standby mode.\n" +
            "\n2)\tAt zero velocity, verify that the manometer readings are all the same. In the software, pick the results sheet and rename it to '20%.'\n" +
            "\n3)\tMeasure the ambient temperature in Celsius and the laboratory pressure in Pascals and enter the results into the mimic diagram in the necessary boxes.\n" +
            "\n4)\tIn the selection box on the mimic diagram, pick the body fitted with the drag balance.  \n" +
            "\n5)\tSet the 'Rake' selection to 'Yes' when using the wake survey rake. If the rake is not used, verify that the list is set to 'No'.\n" +
            "\n6)\tUsing the up arrows, progressively set the fan to 20 %. Check that all fittings on the tunnel remain secure and that due to the inlet and outlet air streams there is no safety danger. Enable time for the stabilisation of the fan.\n" +
            "\n7)\tTo provide a good curve above the model, change the height of the flow visualisation tube and the length of the thread. Sketch the curve or photograph it. Using the Notes facility, a brief written summary of the findings may be inserted into the results sheet, but this is probably best used to insert a reference number or code that fits the sketch or photograph produced.\n" +
            "\n8)\tShorten the thread in the cylinder wake until the end trails instantly, examining potential turbulence (it is difficult to visually record this unless a motion camera is available). To explain what is observed, make written observations and sketches as needed. \n" +
            "\n9)\tTake a reading of the water level in all the columns by using the C15-11 inclined manometer and the wake survey rake and enter the findings on the mimic diagram. To fit the readings, it is also possible to shift the cursors along the tubes, providing a better visual representation of the difference in pressure across the cylinder. \n" +
            "\n10)\tLog the readings to the sensor by selecting the \"Go\" symbol.\n" +
            "\n11)\tSet the fan to 30%. Allow time for the fan to stabilise, then take a new set of readings using the \"Go\" icon- if using the wake survey rake and the C1-11 then enter the manometer readings on the mimic diagram first. \n" +
            "\n12)\tRepeat 40%, 50%, and so on for fan speeds up to 100% .\n" +
            "\n13)\tWith a final reading at 20%, repeat while reducing the fan speed in steps of 10%.\n" +
            "\n14)\tSet the fan to 0% and before continuing, allow the fan to stop.\n" +
            "\n15)\tSave the results sheet by choosing 'Save As...' from the 'File' menu, so that in the case of a computer malfunction, the data will not be lost. Offer the file an acceptable name so that it can be easily identified at a later date, such as the code of the unit, the letter of the experiment, and the date. \n" +
            "\n16)\tUse the icon to create a new results sheet and pick the 'Concave disk' from the selection box for the body form.\n" +
            "\n17)\tRemove the balance of the lift and drag, and substitute the concave disk for the flat disk.\n" +
            "\n18)\tRepeat the visualization of flow (and wake measurements if the rake is used) for the same fan velocity range as the flat disk. Use 'Save' to save the results. This will connect the new data to the current file. \n" +
            "\n19)\tShut down the fan and allow it to stop. Repeat for the convex disk, sphere and streamlined body, create a new results sheet for each of them, and pick a suitable model in the selection box for the body type. Always stop the fan and save it between each set of results before adjusting the model.\n" +
            "\n20)\tInto the working section, fit the small smooth sphere. Over the previous range of fan speeds, repeat the results. Give careful attention to the form of the wake.\n" +
            "\n21)\tRepeat for the tiny dimpled sphere (golf ball), paying careful attention again to the type of the wake.\n" +
            "\n22)\tBy selecting the 'Fan On button in the software, shut down the fan and turn it to Standby.\n" +
            "\n23)\tSwitching off the mains switch on an IFD7\n";

    public void openIntroduction() {
        introduction.setVisibility(View.VISIBLE);
        setTitle("Introduction");
        aimText.setText(aim);
    }

    public void intro_para(View view) {
        int tag = Integer.parseInt(view.getTag().toString()) - 1;
        if (para[tag].getText() == "") {
            para[tag].setLayoutParams(openParams);
            para[tag].setText(theory[tag]);
            para_arrow[tag].animate().rotation(180f).setDuration(50);
            if (tag == 2)
                para3_images.setLayoutParams(openParams);
        } else {
            para[tag].setLayoutParams(closeParams);
            para[tag].setText("");
            para_arrow[tag].animate().rotation(0f).setDuration(50);
            if (tag == 2)
                para3_images.setLayoutParams(closeParams);
        }
    }

    public void openProcedure() {
        setTitle("Procedure");
        procedure.setVisibility(View.VISIBLE);
        procedurePara.setText(procedureText);
    }

    public void openAboutSetup() {
        setTitle("About Setup");
        aboutSetupPara.setText(about_setup);
        aboutSetup.setVisibility(View.VISIBLE);
    }

    public void openObservation() {
        setTitle("Observation");
    }

    public void startSimulation() {
        setTitle("Simulation");
        simulation.setVisibility(View.VISIBLE);
    }

    public void changeBall(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        if (tag == 1) {
            if (!selectedBall.equals("smooth")) {
                selectedBall = "smooth";
                ballImage.setImageResource(R.drawable.wt_smooth_ball);
                ballBox[0].playAnimation();
                ballBox[1].setProgress(0f);
            } else {
                selectedBall = "";
                ballImage.setImageResource(0);
                ballBox[0].setProgress(0f);
            }
        } else {
            if (!selectedBall.equals("dimpled")) {
                selectedBall = "dimpled";
                ballImage.setImageResource(R.drawable.wt_dimpled_ball);
                ballBox[0].setProgress(0f);
                ballBox[1].playAnimation();
            } else {
                selectedBall = "";
                ballImage.setImageResource(0);
                ballBox[1].setProgress(0f);
            }
        }
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
        setContentView(R.layout.activity_wind_tunnel);

        introduction = findViewById(R.id.windTunnel_introduction);
        aboutSetup = findViewById(R.id.windTunnel_aboutSetup);
        procedure = findViewById(R.id.windTunnel_procedure);

        aimText = findViewById(R.id.aimText);
        para1 = findViewById(R.id.para1);
        para2 = findViewById(R.id.para2);
        para3 = findViewById(R.id.para3);
        para4 = findViewById(R.id.para4);
        aboutSetupPara = findViewById(R.id.aboutSetupPara);
        procedurePara = findViewById(R.id.procedurePara);

        para = new TextView[]{para1, para2, para3, para4};

        para1_arrow = findViewById(R.id.para1_arrow);
        para2_arrow = findViewById(R.id.para2_arrow);
        para3_arrow = findViewById(R.id.para3_arrow);
        para4_arrow = findViewById(R.id.para4_arrow);

        para3_images = findViewById(R.id.windTunnel_para3_images);

        simulation = findViewById(R.id.windTunnel_simulation);
        ballImage = findViewById(R.id.windTunnel_ballImage);
        ballBox = new LottieAnimationView[]{
                findViewById(R.id.windTunnel_smoothBallBox),
                findViewById(R.id.windTunnel_dimpledBallBox)};

        para_arrow = new ImageView[]{para1_arrow, para2_arrow, para3_arrow, para4_arrow};

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