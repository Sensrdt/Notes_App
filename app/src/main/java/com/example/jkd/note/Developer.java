package com.example.jkd.note;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Developer extends AppCompatActivity {


    ImageButton a , b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        a = findViewById(R.id.mailbs);
        b = findViewById(R.id.fb);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "sensrdt@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Re:Notes App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello,");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"))  ;   }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "https://www.facebook.com/sridip.dutta.3"));
                startActivity(browserIntent);
            }
        });
    }
}
