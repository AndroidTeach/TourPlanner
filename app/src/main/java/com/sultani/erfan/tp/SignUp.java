package com.sultani.erfan.tp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sultani.erfan.tp.Comman.Comman;
import com.sultani.erfan.tp.Model.User;

public class SignUp extends AppCompatActivity {

    MaterialEditText editphNum,editName,editPassword,editSecureCodee;
    Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        editphNum = (MaterialEditText) findViewById(R.id.editphnumber);
        editName = (MaterialEditText) findViewById(R.id.editname);
        editPassword = (MaterialEditText) findViewById(R.id.editpassword);
        editSecureCodee = (MaterialEditText) findViewById(R.id.edtSecureCode);
        btnSignUp = (Button) findViewById(R.id.btnsignup);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Comman.isConnectedToInternet(getBaseContext())) {
                    final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(editphNum.getText().toString()).exists()) {

                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, "Phone Number exist", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                User user = new User(editName.getText().toString(),
                                        editPassword.getText().toString(),
                                        editSecureCodee.getText().toString());
                                user.setIsStaff("false");
                                table_user.child(editphNum.getText().toString()).setValue(user);
                                Toast.makeText(SignUp.this, "Sign Up Successfull", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else {

                    Toast.makeText(SignUp.this,"Please check your connection",Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });


    }
}
