package com.example.jkd.note;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Page extends AppCompatActivity {


    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private ImageButton deletepage;
    private String noteid ;




    private TextView titlepage, descrippage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        try{
            noteid = getIntent().getStringExtra("noteid");
        } catch (Exception e){
            e.printStackTrace();
        }

        firebaseAuth = FirebaseAuth.getInstance();

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(firebaseAuth.getCurrentUser().getUid());




        deletepage = findViewById(R.id.delete);

        titlepage = findViewById(R.id.titlepage);
        descrippage = findViewById(R.id.decrippage);

        deletepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deletenotes();
            }
        });

        mdatabase.child(noteid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String titles = (String) dataSnapshot.child("title").getValue().toString();
                String decrip = (String) dataSnapshot.child("description").getValue().toString();

                titlepage.setText(titles);
                descrippage.setText(decrip);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

    private void deletenotes()
    {
        mdatabase.child(noteid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
                    Intent main = new Intent(Page.this,MainActivity.class);
                    startActivity(main);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Error: " + task.getException(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
