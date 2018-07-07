package com.sultani.erfan.tp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.AlteredCharSequence;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Interface.ItemClickListener;
import com.sultani.erfan.tp.Model.GeneratePack;
import com.sultani.erfan.tp.ViewHolder.FavoritePackageShow;

public class ShowFavorite extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<GeneratePack,FavoritePackageShow> adapter;


    FirebaseDatabase database;
    DatabaseReference refFavoratie;
    TextView showTxt;

    Toolbar toolbar;

    Typeface typeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorite);

        //firebase
        database = FirebaseDatabase.getInstance();
        refFavoratie = database.getReference("Generate");

        recyclerView = (RecyclerView) findViewById(R.id.listfavorite);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        showTxt = (TextView) findViewById(R.id.details_favorite);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FAVORITE PACKAGES");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        typeFace = Typeface.createFromAsset(getAssets(),"fonts/Ka.otf");



        loadFavoritePackage(Comman.currentUser.getPhone());


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void loadFavoritePackage(String phone) {

        adapter = new FirebaseRecyclerAdapter<GeneratePack, FavoritePackageShow>(

                GeneratePack.class,
                R.layout.favorite_item,
                FavoritePackageShow.class,
                refFavoratie.orderByChild("phone").equalTo(phone)

        ) {
            @Override
            protected void populateViewHolder(FavoritePackageShow viewHolder, GeneratePack model, int position) {
                viewHolder.txtFavName.setTypeface(typeFace);


                viewHolder.txtFavName.setText(model.getName());
                viewHolder.txtFavPrice.setText(model.getPrice());
                viewHolder.txtFavPayment.setText("Progress");
                viewHolder.txtFavDays.setText(model.getDays());
                viewHolder.txtDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder =new  AlertDialog.Builder(ShowFavorite.this);

                        View mView = getLayoutInflater().inflate(R.layout.infolayout,null);
                        TextView mFoods = (TextView)mView.findViewById(R.id.textView3);
                        TextView mTransport = (TextView)mView.findViewById(R.id.textView4);
                        TextView mStay = (TextView)mView.findViewById(R.id.textView5);

                        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.setView(mView);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageViewFavorite);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });


            }
        };



          recyclerView.setAdapter(adapter);


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Comman.DELETE))
            deleteFav(adapter.getRef(item.getOrder()).getKey());

        return super.onContextItemSelected(item);
    }

    private void deleteFav(String key) {

        refFavoratie.child(key).removeValue();
    }



}
