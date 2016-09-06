package com.example.fragmenttransitions;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ViewHolder for kitten cells in our grid
 *
 * @author bherbst
 */
public class KittenViewHolder extends RecyclerView.ViewHolder {
    CardView container;
    ImageView image;
    TextView title;

    public KittenViewHolder(View itemView) {
        super(itemView);
        container = (CardView) itemView;
        image = (ImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.text_title);
    }
}
