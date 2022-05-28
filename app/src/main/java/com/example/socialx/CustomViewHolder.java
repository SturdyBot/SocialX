package com.example.socialx;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder{
    TextView text_Time,text_Source, text_Title, text_Description;
    ImageView img_headline;
    CardView cardView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        text_Source = itemView.findViewById(R.id.text_Source);
        text_Time = itemView.findViewById(R.id.text_Time);
        text_Title = itemView.findViewById(R.id.text_Title);
        text_Description = itemView.findViewById(R.id.text_Description);
        img_headline = itemView.findViewById(R.id.img_headline);
        cardView = itemView.findViewById(R.id.main_container);
    }
}
