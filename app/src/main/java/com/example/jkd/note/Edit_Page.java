package com.example.jkd.note;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Edit_Page extends AppCompatActivity {

    private Button btnCreate;
    private EditText etTitle, etContent;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mdatabase;

    private String noteid;

    private boolean isExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__page);

        try {
            noteid = getIntent().getStringExtra("noteid");

            //Toast.makeText(this, noteID, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }


        firebaseAuth = FirebaseAuth.getInstance();

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(firebaseAuth.getCurrentUser().getUid());

        etTitle = (EditText) findViewById(R.id.edittitle);
        etContent = (EditText) findViewById(R.id.editdescrip);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
                    updateNote(title, content);
                } else {
                    Snackbar.make(view, "Fill empty fields", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        putData();
    }

    private void putData()
    {


            mdatabase.child(noteid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("title") && dataSnapshot.hasChild("description")) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        String content = dataSnapshot.child("description").getValue().toString();

                        etTitle.setText(title);
                        etContent.setText(content);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }


    private void updateNote(String title, String content) {

        if (firebaseAuth.getCurrentUser() != null) {

            if (isExist) {
                // UPDATE A NOTE
                Map updateMap = new HashMap();
                updateMap.put("title", etTitle.getText().toString().trim());
                updateMap.put("description", etContent.getText().toString().trim());


                mdatabase.child(noteid).updateChildren(updateMap);

                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

