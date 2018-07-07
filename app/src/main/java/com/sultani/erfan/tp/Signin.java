package com.sultani.erfan.tp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Model.User;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

/**
 * Created by Sultani on 10/16/2017.
 */

public class Signin extends AppCompatActivity {

   EditText edtPhnumber, editPassword;
    Button btnSignin;

    CheckBox ckbRemember;

    TextView txtForgetPwd;

    FirebaseDatabase database;
    DatabaseReference table_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);


        edtPhnumber = (EditText) findViewById(R.id.editphnumber);
        editPassword = (EditText) findViewById(R.id.editpassword);
        btnSignin = (Button) findViewById(R.id.btnsignin);
        ckbRemember = (CheckBox) findViewById(R.id.ckbRemember);
        txtForgetPwd = (TextView) findViewById(R.id.txtForgetPwd);
        txtForgetPwd.setPaintFlags(txtForgetPwd.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        txtForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForgetPwd();
            }
        });

        //initait paper
        Paper.init(this);

        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Comman.isConnectedToInternet(getBaseContext())) {

                    //save user and password
                    if (ckbRemember.isChecked()){

                        Paper.book().write(Comman.USER_KEY,edtPhnumber.getText().toString());
                        Paper.book().write(Comman.PWD_KEY,editPassword.getText().toString());
                    }


                    final ProgressDialog progressDialog = new ProgressDialog(Signin.this);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edtPhnumber.getText().toString()).exists()) {
                                progressDialog.dismiss();
                                User user = dataSnapshot.child(edtPhnumber.getText().toString()).getValue(User.class);
                                user.setPhone(edtPhnumber.getText().toString());  //set the phone no
                                if (user.getPassword().equals(editPassword.getText().toString())) {
                                    {
                                        Intent home = new Intent(Signin.this, Home.class);

                                        Comman.currentUser = user;
                                        startActivity(home);
                                        finish();
                                    }
                                    Toast.makeText(Signin.this, "Sign in Successfull", Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(Signin.this, "Sign in Failed", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(Signin.this, "User not Exist", Toast.LENGTH_SHORT).show();

                            }

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else {

                    Toast.makeText(Signin.this,"Please check your connection",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    //show password method

    private void showDialogForgetPwd() {
        AlertDialog.Builder builder =new  AlertDialog.Builder(this);
        builder.setTitle("Forget Password");
        builder.setMessage("Enter Phone and Secure code");

        LayoutInflater inflater = this.getLayoutInflater();
        View forget_view =inflater.inflate(R.layout.forget_password_layout,null);

        builder.setView(forget_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        final MaterialEditText edtPhone = (MaterialEditText)forget_view.findViewById(R.id.edtphoneno);
        final MaterialEditText edtSecureCode = (MaterialEditText)forget_view.findViewById(R.id.edtsecureCode);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //chech if user is avalible
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.child(edtPhone.getText().toString())
                                .getValue(User.class);
                        if (user.getSecureCode().equals(edtSecureCode.getText().toString()))
                            Toast.makeText(Signin.this,"Password is : "+user.getPassword(),Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Signin.this,"Wrong Secure Code",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();



    }
}
