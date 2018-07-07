package com.sultani.erfan.tp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sultani.erfan.tp.Interface.ItemClickListener;
import com.sultani.erfan.tp.R;

/**
 * Created by Sultani on 10/18/2017.
 */

public class PackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView pack_name;
    public ImageView pack_image;

    private ItemClickListener itemClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PackViewHolder(View itemView) {
        super(itemView);

        pack_name =(TextView)itemView.findViewById(R.id.pack_name);
        pack_image = (ImageView)itemView.findViewById(R.id.pack_image);

        itemView.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
