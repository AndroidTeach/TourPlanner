package com.sultani.erfan.tp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;
import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Interface.ItemClickListener;
import com.sultani.erfan.tp.Model.GeneratePack;
import com.sultani.erfan.tp.Model.Pack;

import com.sultani.erfan.tp.Service.ListenOrder;
import com.sultani.erfan.tp.ViewHolder.CustomViewHolder;
import com.sultani.erfan.tp.ViewHolder.PackViewHolder;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import info.hoang8f.widget.FButton;

public class Pack_Generate extends AppCompatActivity {




    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase db;
    DatabaseReference searchByPrice;



    FirebaseDatabase database;
    DatabaseReference genratePack;
    EditText editName;

    Typeface typeface;

    String userId;
    String packName;

    FButton generateBtn;

    String price;

    FirebaseRecyclerAdapter<Pack,CustomViewHolder> adapter;

    Toolbar toolbar;

    BetterSpinner betterSpinnerPrice;
    BetterSpinner betterSpinnerDays;

    String[] spinnerListPrice = {"3000","4000",
            "4500","5000","6000","6500","7000","8000","8500","9000","10000",};

    String[] spinnerListDays = {"1","2",
            "3","4","5","6","7","8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack__generate);


        database = FirebaseDatabase.getInstance();
        genratePack = database.getReference("Generate");

        db = FirebaseDatabase.getInstance();
        searchByPrice = db.getReference("Tour");


        editName = (EditText) findViewById(R.id.edtName);
        generateBtn = (FButton) findViewById(R.id.genBtn);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_price);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("GENERATE PACKAGE");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,spinnerListPrice);
         betterSpinnerPrice = (BetterSpinner) findViewById(R.id.spinnerprice);
        betterSpinnerPrice.setAdapter(arrayAdapterPrice);


        ArrayAdapter<String> arrayAdapterDays = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,spinnerListDays);
        betterSpinnerDays = (BetterSpinner) findViewById(R.id.spinnerdays);
        betterSpinnerDays.setAdapter(arrayAdapterDays);





       // typeface = Typeface.createFromAsset(getAssets(), "fonts/Ka.otf");






        generateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editName.getText().toString().isEmpty() || betterSpinnerDays.getText().toString().isEmpty() || betterSpinnerPrice.getText().toString().isEmpty())
                            Toast.makeText(Pack_Generate.this,"Please Fill Information To Generate Package",Toast.LENGTH_SHORT).show();
                        else
                          loadShowFavoritePack();


                    }
                });//end the button



    }

    private void loadShowFavoritePack() {

        packName = editName.getText().toString();
        userId = genratePack.push().getKey();

        price = betterSpinnerPrice.getText().toString();

        if (packName.equals("kashmir") || packName.equals("Kashmir")|| packName.equals("KASHMIR"))
            packName = "https://firebasestorage.googleapis.com/v0/b/tour-1e86a.appspot.com/o/kashmir%2FAzad_kashmir.jpg?alt=media&token=dedef85e-54a4-471b-b142-e7b952a05081";
        else if (packName.equals("gilgit") || packName.equals("Gilgit") || packName.equals("gilgit baltistan") || packName.equals("Gilgit Baltistan"))
            packName = "https://firebasestorage.googleapis.com/v0/b/tour-1e86a.appspot.com/o/gilgit%20(13).jpg?alt=media&token=8722a31f-56c0-477d-8d3d-34aef82660c1";
        else if (packName.equals("kpk") || packName.equals("KPK") || packName.equals("Khyber Pakhtunkha") || packName.equals("khyber pakhtunkha"))
            packName = "https://firebasestorage.googleapis.com/v0/b/tour-1e86a.appspot.com/o/shar-dara-swat-valley-pakistan-by-murtaza-mahmud-48244.jpg?alt=media&token=c911cdc1-5200-40c8-b63e-a83e75b861f6";
        else
            packName = "https://firebasestorage.googleapis.com/v0/b/tour-1e86a.appspot.com/o/gilgit%20(6).jpg?alt=media&token=86315484-0d2e-4743-9a13-7003a8edad13";


        genratePack.child(userId).child("days").setValue(betterSpinnerDays.getText().toString());
        genratePack.child(userId).child("price").setValue(betterSpinnerPrice.getText().toString());
        genratePack.child(userId).child("name").setValue(editName.getText().toString().toUpperCase());
        genratePack.child(userId).child("phone").setValue(Comman.currentUser.getPhone());
        genratePack.child(userId).child("status").setValue("0");
        genratePack.child(userId).child("image").setValue(packName);

        Toast.makeText(Pack_Generate.this,"Favorite Package Has Generate",Toast.LENGTH_SHORT).show();


        loadPackagesByPrice(price);



        editName.setText("");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void loadPackagesByPrice(String price) {

        adapter = new FirebaseRecyclerAdapter<Pack, CustomViewHolder>(Pack.class,
                R.layout.nearpackage,
                CustomViewHolder.class,
                searchByPrice.orderByChild("price").startAt(price).limitToFirst(4)

        ) {
            @Override
            protected void populateViewHolder(CustomViewHolder viewHolder, Pack model, int position) {

                viewHolder.txtPriceName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent packDetail = new Intent(Pack_Generate.this,Pack_Details.class);
                        packDetail.putExtra("PackId",adapter.getRef(position).getKey());
                        startActivity(packDetail);

                    }
                });



            }
        };

             recyclerView.setAdapter(adapter);

    }







}
