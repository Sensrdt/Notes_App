package com.example.jkd.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jkd.note.user_sign.LoginActivity;
import com.example.jkd.note.user_sign.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {


    private Button login , signup , developer;
    private FirebaseAuth firebaseAuth;
    private long backPressed;
    private ImageButton share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        developer=findViewById(R.id.developer);
        share=findViewById(R.id.share);

        firebaseAuth=FirebaseAuth.getInstance();

        updateui();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,Developer.class);
                startActivity(intent);

            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                String sharebody = "Description or link";
                String sharesub = "Notes App";

                intent.putExtra(Intent.EXTRA_SUBJECT,sharebody);
                intent.putExtra(Intent.EXTRA_TEXT,sharebody);
                startActivity(Intent.createChooser(intent,"Share using"));

            }
        });
    }
    private void register(){
        Intent registerint = new Intent(StartActivity.this, RegisterActivity.class);
        startActivity(registerint);
        finish();

    }

    private void login(){
        Intent rlogisterint = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(rlogisterint);
        finish();

    }


    private void updateui(){
        if (firebaseAuth.getCurrentUser()!=null){
            Log.i("Mainactivity","fauth!=null");
            Intent intent =new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }else{

            Log.i("MainActivity","fauth == null");
        }
    }
    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        //during moving out of the app .i.e. onBackpressed

        if (exit) {

            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create().show();
        }
        else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
        }

    }




}
