package com.sultani.erfan.tp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Interface.ItemClickListener;
import com.sultani.erfan.tp.Model.Request;
import com.sultani.erfan.tp.ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.listorder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("PAYMENT");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


            loadOrders(Comman.currentUser.getPhone());




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Comman.DELETE))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());

        return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {

            requests.child(key).removeValue();
    }

    private void loadOrders(String phone) {

            adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(

                    Request.class,
                    R.layout.order_layout,
                    OrderViewHolder.class,
                    requests.orderByChild("phone")
                       .equalTo(phone)
            ) {
                @Override
                protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                  //  viewHolder.textOrderId.setText(adapter.getRef(position).getKey());
                    viewHolder.textOrderId.setText(model.getTotal());
                    viewHolder.textOrderStatus.setText(Comman.convertCodeToStatus(model.getStatus()));
                    viewHolder.txtOrderCredit.setText(model.getCreditNum());
                    viewHolder.txtOrderphone.setText(model.getPhone());

                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {


                        }
                    });

                }
            };

            recyclerView.setAdapter(adapter);


    }


}
