package com.project.dj.ihouseservices;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {
    private EditText fname,lname,phone,addres,num1,num2,num3;
    private FirebaseAuth auth;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mref;
    private FirebaseUser fireuser;
    private ProgressDialog mProgress;
    private String nam1,nam2,nam3,userid;
    int flag=0;
    private FirebaseAuth mAuth;
    private SharedPreferences pref;
    private String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        userType = pref.getString("userType", null);
        mref= FirebaseDatabase.getInstance().getReference();
        mProgress=new ProgressDialog(this);

        fname= findViewById(R.id.fname);
        lname= findViewById(R.id.lname);
        phone= findViewById(R.id.phone);
        addres= findViewById(R.id.address);

        Button update = findViewById(R.id.update);

        auth= FirebaseAuth.getInstance();
        fireuser=auth.getCurrentUser();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mfirebaseDatabase.getReference();
        userid=fireuser.getUid();

        new CountDownTimer(3000,1000)
        {
            public void onTick(long ms)
            {   mProgress.setMessage("Loading your profile.This may take a few seconds.Please wait...");
                mProgress.show();

            }

            FirebaseUser user = mAuth.getCurrentUser();

            public void onFinish(){
                mref.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         showData(dataSnapshot);
                        mProgress.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//c.close();
            }
        }.start();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireuser=auth.getCurrentUser();
                UserDatabase user=new UserDatabase(
                        fname.getText().toString(),
                        lname.getText().toString(),
                        phone.getText().toString(),
                        addres.getText().toString(),
                        userType);

                        mref.child("user").child(fireuser.getUid()).setValue(user);



                new CountDownTimer(3000,1000)
                {
                    public void onTick(long ms)
                    {
                        mProgress.setMessage("Updating your profile..");
                        mProgress.show();
                    }
                    public void onFinish()
                    {
                        mProgress.dismiss();
                        Toast.makeText(getApplicationContext(),"Updated Successfully!",Toast.LENGTH_LONG).show();
                        finish();


                    }
                }.start();


            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){

            UserDatabase post = dataSnapshot.getValue(UserDatabase.class);
            fname.setText(Objects.requireNonNull(post).getFirstName());
            lname.setText(Objects.requireNonNull(post).getLastName());
            phone.setText(Objects.requireNonNull(post).getPhoneNumber());
            addres.setText(Objects.requireNonNull(post).getDigitalAdress());
            }

    }

  
// @Override
//    protected void onStart() {
//        super.onStart();
//        auth.addAuthStateListener(mAuthStateListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(mAuthStateListener!=null){
//            auth.removeAuthStateListener(mAuthStateListener);
//        }
//    }


    @Override
    public void onBackPressed() {
        finish();
    }

}


