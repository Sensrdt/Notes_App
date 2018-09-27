package com.example.jkd.note;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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

import java.util.HashMap;
import java.util.Map;

public class Page extends AppCompatActivity {


    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private ImageButton deletepage , sharepagedet,editpa,copyb;
    private String noteid ;
    private ProgressBar progressBar;




    private EditText titlepage, descrippage;


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
         copyb = findViewById(R.id.copy);

        editpa=findViewById(R.id.editpage);
        titlepage = findViewById(R.id.titlepage);
        descrippage = findViewById(R.id.decrippage);

        sharepagedet=findViewById(R.id.sharepage);

        deletepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialognow();
            }
        });


        mdatabase.child(noteid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                String titles = (String) dataSnapshot.child("title").getValue().toString();
                String decrip = (String) dataSnapshot.child("description").getValue().toString();

                titlepage.setText(titles);
                descrippage.setText(decrip);

                    sharepagedet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");

                            String sharebody = dataSnapshot.child("title").getValue().toString();
                            String sharesub = dataSnapshot.child("description").getValue().toString();

                            intent.putExtra(Intent.EXTRA_SUBJECT,sharebody);
                            intent.putExtra(Intent.EXTRA_TEXT,sharesub);
                            startActivity(Intent.createChooser(intent,"Share using"));
                        }
                    });

                    copyb.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {
                         ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Label", dataSnapshot.child("description").getValue().toString());
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(Page.this,"Item copied",Toast.LENGTH_SHORT).show();
         }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        editpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = titlepage.getText().toString().trim();
                String content = descrippage.getText().toString().trim();


                titlepage.setText(title);
                descrippage.setText(content);
                ProgressDialog progressDialog = new ProgressDialog(Page.this);
                progressDialog.setMessage("Processing your request, please wait....");

                progressDialog.show();

                editnote(title, content);

                progressDialog.dismiss();
            }
        });




        }

    private void editnote(String title, String content) {




        Map updateMap = new HashMap();
        updateMap.put("title", titlepage.getText().toString().trim());
        updateMap.put("description", descrippage.getText().toString().trim());


        mdatabase.child(noteid).updateChildren(updateMap);

       Toast.makeText(getApplicationContext(),"Your note is updated",Toast.LENGTH_SHORT).show();

    }

    private void dialognow(){
        new AlertDialog.Builder(this)
                .setTitle("Delete !!")
                .setMessage("Are you sure you want to delete?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletenotes();
                    }
                }).create().show();
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