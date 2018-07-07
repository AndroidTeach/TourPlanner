package com.sultani.erfan.tp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sultani.erfan.tp.Interface.ItemClickListener;
import com.sultani.erfan.tp.R;

/**
 * Created by Erfan on 12/16/2017.
 */

public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtPriceName;
    public ImageView imageView;
    private ItemClickListener itemClickListener;


    public CustomViewHolder(View itemView) {
        super(itemView);

        txtPriceName = (TextView)itemView.findViewById(R.id.near_name);
        imageView = (ImageView)itemView.findViewById(R.id.near_image);

        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);



    }
}
