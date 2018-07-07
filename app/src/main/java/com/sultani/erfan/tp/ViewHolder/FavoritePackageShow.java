package com.sultani.erfan.tp.ViewHolder;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Interface.ItemClickListener;
import com.sultani.erfan.tp.R;

/**
 * Created by Erfan on 12/16/2017.
 */

public class FavoritePackageShow  extends RecyclerView.ViewHolder implements View.OnClickListener,
View.OnCreateContextMenuListener{

    public TextView txtFavName;
    public TextView txtFavPrice;
    public TextView txtFavDays;
    public TextView txtFavPayment;
    public ImageView imageViewFavorite;
    public TextView txtDetails;
    private ItemClickListener itemClickListener;



    public FavoritePackageShow(View itemView) {
        super(itemView);

        txtFavName = (TextView)itemView.findViewById(R.id.fav_name);
        txtFavDays = (TextView)itemView.findViewById(R.id.fav_days);
        txtFavPrice =  (TextView)itemView.findViewById(R.id.fav_price);
        txtFavPayment = (TextView)itemView.findViewById(R.id.fav_payemnt);
        txtDetails = (TextView)itemView.findViewById(R.id.details_favorite);
        txtDetails.setPaintFlags(txtDetails.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        imageViewFavorite = (ImageView)itemView.findViewById(R.id.fav_image);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle("Select Action");
        menu.add(0,0,getAdapterPosition(), Comman.DELETE);

    }
}
