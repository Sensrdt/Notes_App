package com.example.jkd.note;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private long backPressed;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private DatabaseReference dbref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        firebaseAuth=FirebaseAuth.getInstance();




        updateui();

        logout = findViewById(R.id.logout);

       try
       {
           dbref= FirebaseDatabase.getInstance().getReference().child("Notes").child(firebaseAuth.getCurrentUser().getUid());

       } catch (Exception e){
           e.printStackTrace();
       }

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent move = new Intent(MainActivity.this,StartActivity.class);
                startActivity(move);
                finish();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(intent);
            }
        });

        }


    @Override
    protected void onStart() {


        super.onStart();
        FirebaseRecyclerAdapter<Notemodel,Noteviewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Notemodel, Noteviewholder>(

                Notemodel.class,
                R.layout.single_notes,
                Noteviewholder.class,
                dbref

        ) {
            @Override
            protected void populateViewHolder(final Noteviewholder viewHolder, final Notemodel model, int position) {
                final String key = getRef(position).getKey();

               dbref.child(key).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {


                       viewHolder.settitle(model.getTitle());
                       viewHolder.setdescrip(model.getDescription());


                       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent news = new Intent(MainActivity.this,Page.class);
                               news.putExtra("noteid",key);
                               startActivity(news);

                           }
                       });


                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });

            }
        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void updateui(){
        if (firebaseAuth.getCurrentUser()!=null){
            Toast.makeText(MainActivity.this,"Loading !!!!",Toast.LENGTH_SHORT).show();
            Log.i("Mainactivity","fauth!=null");
        }else
            {
                Intent intent =new Intent(MainActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
                Log.i("MainActovity","fauth == null");
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
