package com.example.jkd.note;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class NewNoteActivity extends AppCompatActivity {

    EditText addnt , addnd;
    Button addn;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        addnt = findViewById(R.id.titlen);
        addnd = findViewById(R.id.descripn);
        addn = findViewById(R.id.addnote);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notes").child(firebaseAuth.getCurrentUser().getUid());


        addn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = addnt.getText().toString().trim();
                String descrip = addnd.getText().toString().trim();


                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(descrip)) {
                    createnote(title,descrip);

                } else {
                    Toast.makeText(getApplicationContext(), "Fill empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        private void createnote(String title , String descrip ){
            if (firebaseAuth.getCurrentUser() != null){
                DatabaseReference noteref = databaseReference.push();
                Map notemap = new HashMap();
                notemap.put("title",title);
                notemap.put("description",descrip);
                notemap.put("timesapp", ServerValue.TIMESTAMP);

                noteref.setValue(notemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Note added to your database",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewNoteActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(getApplicationContext(),"Error: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

