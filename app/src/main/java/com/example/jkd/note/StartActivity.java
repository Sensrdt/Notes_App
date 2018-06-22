package com.example.jkd.note;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jkd.note.user_sign.LoginActivity;
import com.example.jkd.note.user_sign.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {


    private Button login , signup;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);

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
    }
    private void register(){
        Intent registerint = new Intent(StartActivity.this, RegisterActivity.class);
        startActivity(registerint);

    }

    private void login(){
        Intent rlogisterint = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(rlogisterint);

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
}
