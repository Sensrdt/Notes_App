package com.example.jkd.note.user_sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jkd.note.MainActivity;
import com.example.jkd.note.R;
import com.example.jkd.note.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText logemail , logpass;
    private Button btnlog;

    private FirebaseAuth firebaseAuth;

    private ImageButton backloga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        logemail = findViewById(R.id.emaillo) ;
        logpass = findViewById(R.id.passlo);
        btnlog = findViewById(R.id.loginup);
        backloga=findViewById(R.id.backlog);

        firebaseAuth = FirebaseAuth.getInstance();

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = logemail.getText().toString().trim();
                String pass = logpass.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
                    login(email , pass);
                }
            }
        });

        backloga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private  void login(String email , String pass){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email , pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            Intent logintent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(logintent);
                            finish();
                            Toast.makeText(getApplicationContext(),"Successfull!!",Toast.LENGTH_SHORT).show();

                       }else{
                            Toast.makeText(getApplicationContext(),"Error:- "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
