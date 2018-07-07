package com.sultani.erfan.tp.ViewHolder;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Interface.ItemClickListener;
import com.sultani.erfan.tp.R;

/**
 * Created by Erfan on 12/1/2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
   View.OnCreateContextMenuListener
{

    public TextView textOrderId,textOrderStatus,txtOrderphone,txtOrderCredit;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);
        textOrderId = (TextView)itemView.findViewById(R.id.order_id);
        textOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtOrderphone = (TextView)itemView.findViewById(R.id.order_phone);
        txtOrderCredit = (TextView)itemView.findViewById(R.id.order_credit);

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
        menu.setHeaderTitle("Select action");
        menu.add(0,0,getAdapterPosition(),Comman.DELETE);
    }
}
