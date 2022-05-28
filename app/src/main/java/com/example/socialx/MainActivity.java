package com.example.socialx;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RelativeLayout rl_1, rl_2;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting Up Custom ToolBar/Action Bar
        toolbar = findViewById(R.id.Custom_Toolbar);
        setSupportActionBar(toolbar);

        //Change Status Bar Color
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.theme));

        //Setting Fragments using Frame Layout
        rl_1 = (RelativeLayout) findViewById(R.id.rl_1);
        rl_2 = (RelativeLayout) findViewById(R.id.rl_2);

        //Setting Default Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Login_Fragment()).commit();
            rl_1.setBackground(getDrawable(R.drawable.red_outlook));
            TextView text = (TextView) findViewById(R.id.Login);
            text.setTextColor(getResources().getColor(R.color.white));
            rl_2.setBackground(getDrawable(R.drawable.white_outlook_right));
        }

        //Login Fragment
        rl_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new Login_Fragment());
                rl_1.setBackground(getDrawable(R.drawable.red_outlook));
                TextView text = (TextView) findViewById(R.id.Login);
                text.setTextColor(getResources().getColor(R.color.white));
                rl_2.setBackground(getDrawable(R.drawable.white_outlook_right));
                TextView text_1 = (TextView) findViewById(R.id.Sign_Up);
                text_1.setTextColor(getResources().getColor(R.color.text_color));
            }
        });

        //Sign-Up Fragment
        rl_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SignUp_Fragment());
                rl_1.setBackground(getDrawable(R.drawable.white_outlook_left));
                TextView text = (TextView) findViewById(R.id.Login);
                text.setTextColor(getResources().getColor(R.color.text_color));
                rl_2.setBackground(getDrawable(R.drawable.red_outlook));
                TextView text_1 = (TextView) findViewById(R.id.Sign_Up);
                text_1.setTextColor(getResources().getColor(R.color.white));
            }
        });
    }

    //Controlling Fragments
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}