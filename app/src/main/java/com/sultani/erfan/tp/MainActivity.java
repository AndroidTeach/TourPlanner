package com.sultani.erfan.tp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Model.Pack;
import com.sultani.erfan.tp.Model.User;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn,btnSignUp;
    TextView textsLogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.signin);
        btnSignUp = (Button) findViewById(R.id.signup);
        textsLogan = (TextView) findViewById(R.id.txtslogan);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Nabila.ttf");
        textsLogan.setTypeface(typeface);

        //initait paper
        Paper.init(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signin = new Intent(MainActivity.this,Signin.class);
                startActivity(signin);

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(MainActivity.this,SignUp.class);
                startActivity(signup);
            }
        });

        //check remember

        String user = Paper.book().read(Comman.USER_KEY);
        String pwd =  Paper.book().read(Comman.PWD_KEY);

        if (user!=null && pwd != null){


            if (!user.isEmpty() && !pwd.isEmpty())
                login(user,pwd);
        }


    }

    private void login(final String phone, final String pwd) {


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        if (Comman.isConnectedToInternet(getBaseContext())) {


            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(phone).exists()) {
                        progressDialog.dismiss();
                        User user = dataSnapshot.child(phone).getValue(User.class);
                        user.setPhone(phone);  //set the phone no
                        if (user.getPassword().equals(pwd)) {
                            {
                                Intent home = new Intent(MainActivity.this, Home.class);

                                Comman.currentUser = user;
                                startActivity(home);
                                finish();
                            }
                            Toast.makeText(MainActivity.this, "Sign in Successfull", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(MainActivity.this, "Sign in Failed", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(MainActivity.this, "User not Exist", Toast.LENGTH_SHORT).show();

                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {

            Toast.makeText(MainActivity.this,"Please check your connection",Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
