package com.sultani.erfan.tp;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Database.Database;
import com.sultani.erfan.tp.Model.Order;
import com.sultani.erfan.tp.Model.Pack;

public class Pack_Details extends AppCompatActivity {

    TextView pack_name,pack_price,pack_description,number_of_days,point_place;
    TextView p_food,p_place,p_transport;
    ImageView pack_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String packId = "";
    FirebaseDatabase database;
    DatabaseReference packs;
    Pack currentPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pack_details);

        database = FirebaseDatabase.getInstance();
        packs = database.getReference("Tour");


        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btn_cart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        packId,
                        currentPackages.getName(),
                        numberButton.getNumber(),
                        currentPackages.getPrice(),
                        currentPackages.getDiscount(),
                        currentPackages.getDays()


                ));

                Toast.makeText(Pack_Details.this,"Add To Cart",Toast.LENGTH_SHORT).show();
            }
        });

        pack_description = (TextView) findViewById(R.id.pack_description);
        pack_name = (TextView) findViewById(R.id.pack_name);
        pack_price = (TextView) findViewById(R.id.pack_price);
        number_of_days = (TextView) findViewById(R.id.num_days);
        pack_image = (ImageView) findViewById(R.id.img_pack);
        p_food = (TextView) findViewById(R.id.pack_food);
        p_place = (TextView) findViewById(R.id.pack_place_stay);
        p_transport = (TextView) findViewById(R.id.pack_transport);
        point_place = (TextView) findViewById(R.id.tour_points);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        //after we set the intent in Pack_details now we want to get the intent here

        if (getIntent() != null)
            packId = getIntent().getStringExtra("PackId");
        if (!packId.isEmpty())
        {
                 getDetailPack(packId);


        }




    }

    private void getDetailPack(String packId) {

        packs.child(packId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentPackages = dataSnapshot.getValue(Pack.class);
                //we want to set the image
                Picasso.with(getBaseContext()).load(currentPackages.getImage()).into(pack_image);

                collapsingToolbarLayout.setTitle(currentPackages.getName());
                pack_price.setText(currentPackages.getPrice());
                pack_name.setText(currentPackages.getName());
                number_of_days.setText(currentPackages.getDays());
                pack_description.setText(currentPackages.getDescription());
                p_food.setText(currentPackages.getFoods());
                p_place.setText(currentPackages.getPlace());
                p_transport.setText(currentPackages.getTransport());
                point_place.setText(currentPackages.getTourpoints());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
