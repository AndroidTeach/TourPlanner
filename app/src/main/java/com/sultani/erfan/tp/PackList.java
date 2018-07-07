package com.sultani.erfan.tp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StreamDownloadTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;
import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Interface.ItemClickListener;
import com.sultani.erfan.tp.Model.Pack;
import com.sultani.erfan.tp.ViewHolder.PackViewHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PackList extends AppCompatActivity  {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference packlist;

    Typeface typeface;

    String categoryId = "";

    FirebaseRecyclerAdapter<Pack,PackViewHolder> adapter;

    FirebaseRecyclerAdapter<Pack,PackViewHolder> searchAdapter;

    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_list);

        database = FirebaseDatabase.getInstance();
        packlist = database.getReference("Tour");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_pack);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        typeface = Typeface.createFromAsset(getAssets(),"fonts/Ka.otf");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PackList.this, Cart.class);
                startActivity(intent);
            }
        });


      //after we set the intent in Packlist now we want to get the intent here

        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null)
        {

                     LoadListPack(categoryId);


        }

        //search will do here

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your Pakcage");

        loadSuggest();   // function that search frrom firebase database
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //when user type name we change the text

                List<String> suggest = new ArrayList<String>();
                for (String search:suggestList)
                {

                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }

                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when searchbar is close restore orignal adapter
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when search complete show the complete result of search in adapter
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    //  search method

    private void startSearch(CharSequence text){
        searchAdapter = new FirebaseRecyclerAdapter<Pack, PackViewHolder>(
                Pack.class,
                R.layout.pack_list,
                PackViewHolder.class,
                packlist.orderByChild("name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(PackViewHolder viewHolder, Pack model, int position) {

                viewHolder.pack_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.pack_image);

                final Pack local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // we start the new activity here
                        Intent packDetail = new Intent(PackList.this,Pack_Details.class);
                        packDetail.putExtra("PackId",searchAdapter.getRef(position).getKey());
                        startActivity(packDetail);
                    }
                });


            }
        };
        recyclerView.setAdapter(searchAdapter);



    }
    private void loadSuggest() {

        packlist.orderByChild("menuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot:dataSnapshot.getChildren())
                        {
                            Pack item = postSnapShot.getValue(Pack.class);
                            suggestList.add(item.getName());// add the name of package it list




                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    private void LoadListPack(String categoryId) {

        adapter = new FirebaseRecyclerAdapter<Pack, PackViewHolder>(Pack.class,
                R.layout.pack_list,
                PackViewHolder.class,
                packlist.orderByChild("menuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(PackViewHolder packViewHolder, Pack model, int i) {
                packViewHolder.pack_name.setTypeface(typeface);
                packViewHolder.pack_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(packViewHolder.pack_image);

                final Pack local = model;
                packViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // we start the new activity here
                        Intent packDetail = new Intent(PackList.this,Pack_Details.class);
                        packDetail.putExtra("PackId",adapter.getRef(position).getKey());
                        startActivity(packDetail);
                    }
                });

            }
        };
           // set the adapter

        recyclerView.setAdapter(adapter);
    }


}
