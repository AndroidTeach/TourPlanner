package com.sultani.erfan.tp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Database.Database;
import com.sultani.erfan.tp.Model.Order;
import com.sultani.erfan.tp.Model.Request;
import com.sultani.erfan.tp.ViewHolder.CartAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtTotalPrice;
    FButton btnPlace;

    Toolbar toolbar;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");


        recyclerView = (RecyclerView) findViewById(R.id.listcart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView) findViewById(R.id.total);
        btnPlace = (FButton) findViewById(R.id.btnplaceorder);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("ADD TO CART");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.size()>0)
                  showAlertDialoge();
                else
                    Toast.makeText(Cart.this,"Cart is empty",Toast.LENGTH_SHORT).show();



            }
        });

        loadListPackages();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialoge() {


        AlertDialog.Builder alertdialoge = new AlertDialog.Builder(Cart.this);
        alertdialoge.setTitle("Token Number for Payment to Agency");
        Random r = new Random();
        int i1 = (r.nextInt(3000) + 10000);
        String token = Integer.toString(i1);
        alertdialoge.setMessage("Token Number");
        final TextView edtCreditNum = new TextView(Cart.this);
        edtCreditNum.setText(token);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtCreditNum.setLayoutParams(lp);



        alertdialoge.setView(edtCreditNum); // add credit number

        alertdialoge.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertdialoge.setPositiveButton("continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                Request request = new Request(
                        Comman.currentUser.getPhone(),
                        Comman.currentUser.getName(),
                        edtCreditNum.getText().toString(),

                        txtTotalPrice.getText().toString(),
                        cart

                );

                //Submit to firebase we will use here System.currentTimeMillis to key
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                //delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Thank you",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertdialoge.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertdialoge.show();
    }

   private void loadListPackages() {
// getcarts from database
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        int total = 0;

        for(Order order:cart)
        total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));


     //  Locale locale = new Locale("en","PK");
     //  NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

     //  txtTotalPrice.setText(fmt.format(total));
       String tt = Integer.toString(total);
       txtTotalPrice.setText(tt);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Comman.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {
        //delete cart from location
       cart.remove(position);
              //delete data from database
        new Database(this).cleanCart();
        //now update the
        for (Order item:cart)
            new Database(this).addToCart(item);

        loadListPackages();

    }
}
