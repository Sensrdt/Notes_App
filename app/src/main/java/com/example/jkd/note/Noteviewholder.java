package com.example.jkd.note;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class Noteviewholder extends RecyclerView.ViewHolder {

    View view;

    TextView textitle ,  textdescrip;

    public Noteviewholder(View itemView) {
        super(itemView);

        view = itemView;

        textitle = view.findViewById(R.id.titlesn);
        textdescrip = view.findViewById(R.id.descripn);


    }

    public void settitle(String title){

        textitle.setText(title);
    }

    public void setdescrip(String description){
        textdescrip.setText(description);
    }
}
