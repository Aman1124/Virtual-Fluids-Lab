package com.example.virtualfluidlab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActionMenu extends AppCompatActivity {

    TextView textView;

    String about = "Bhakk bhosdike. Itne bade ho gye ki ab tm about khologe.";
    String references = "Akshat bhosdike likh ke dega ye. Usi ko jyada chull hai.";
    String help = "Aatmanirbhar bann bhadwe!!";

    int option;

    public void showOption(int choice){
        switch (choice){
            case 1:
                setTitle("About");
                //textView.setText(about);
                break;
            case 2:
                setTitle("References");
                //textView.setText(references);
                break;
            case 3:
                setTitle("Help");
                //textView.setText(help);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_menu);

        textView = findViewById(R.id.expanded_menu);

        Intent intent = getIntent();
        option = intent.getIntExtra("option", 0);

        showOption(option);
    }

}