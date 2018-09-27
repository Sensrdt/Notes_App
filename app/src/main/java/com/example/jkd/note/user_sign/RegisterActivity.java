package com.example.jkd.note.user_sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jkd.note.MainActivity;
import com.example.jkd.note.R;
import com.example.jkd.note.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button registerb;
    private EditText namew, emailw, passw;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firedata;

    private ProgressDialog progressDialog;

    private ImageButton backrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerb = findViewById(R.id.register);
        namew = findViewById(R.id.name);
        emailw = findViewById(R.id.email);
        passw = findViewById(R.id.pass);
        backrega=findViewById(R.id.backreg);

        firebaseAuth = FirebaseAuth.getInstance();
        firedata = FirebaseDatabase.getInstance().getReference().child("Users");

        backrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = namew.getText().toString().trim();
                String uemail = emailw.getText().toString().trim();
                String upass = passw.getText().toString().trim();

                registeruser(uname,uemail,upass);
            }
        });
    }

    private void registeruser(final String name , String email , String password){


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request, please wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email ,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information

                    firedata.child(firebaseAuth.getCurrentUser().getUid()).child("basic").child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                                Toast.makeText(getApplicationContext(),"User registered",Toast.LENGTH_SHORT).show();

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Error :- "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}
