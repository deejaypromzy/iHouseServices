package com.project.dj.ihouseservices;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.ganfra.materialspinner.MaterialSpinner;


public class addAdmin extends AppCompatActivity {
    private EditText fname,lname,etEmail,etphone,etaddress;
    private EditText cpassword,password;
    final int PERMS_REQUEST_CODE = 123;
    private String nam1,nam2,nam3;
    public SQLiteDatabase db;
    int flag=0;
    private MaterialSpinner spinner;
    ArrayAdapter<String> adapter;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private DatabaseReference mref;

    // [END declare_auth]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        // Views
        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        etEmail = findViewById(R.id.email);
        etphone = findViewById(R.id.phone);
        etaddress = findViewById(R.id.address);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);









        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        mref = FirebaseDatabase.getInstance().getReference();
        // [END initialize_auth]*/


        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                if(noPermissions())
                    requestPermission();

            }

        }.start();



    }

    boolean noPermissions() {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.INTERNET};

        for (String s : permissions) {
            res = checkCallingOrSelfPermission(s);
            if (!(res == PackageManager.PERMISSION_GRANTED))
                return true;
        }
        return false;

    }


    private void requestPermission() {
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.INTERNET};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, PERMS_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        switch (requestCode) {
            case PERMS_REQUEST_CODE:
                for (int res : grantResults)
                    allowed = allowed && res == PackageManager.PERMISSION_GRANTED;
                break;
            default:
                allowed = false;
                break;


        }
        if (allowed)
            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getApplicationContext(), " Permission Denied!!!", Toast.LENGTH_SHORT).show();

        }
            /*if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                Toast.makeText(getApplicationContext(), " Storage Warning not permitted...", Toast.LENGTH_SHORT).show();
            else if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS))
                Toast.makeText(getApplicationContext(), "SMS Warning not permitted...", Toast.LENGTH_SHORT).show(); */
    }



    // [START on_start_check_user]
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(addAdmin.this, "Add Admin Failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String myfname = fname.getText().toString();
        if (TextUtils.isEmpty(myfname)) {
            fname.setError("Name can't be empty.");
            valid = false;
        } else {
            fname.setError(null);
        } String mylname = lname.getText().toString();
        if (TextUtils.isEmpty(mylname)) {
            lname.setError("Name can't be empty.");
            valid = false;
        } else {
            lname.setError(null);
        } String myemail = etEmail.getText().toString();
        if (TextUtils.isEmpty(myemail)) {
            etEmail.setError("Email can't be empty..");
            valid = false;
        } else {
            etEmail.setError(null);
        } String mphone = etphone.getText().toString();
        if (TextUtils.isEmpty(mphone)) {
            etphone.setError("Phone # can't be empty..");
            valid = false;
        } else if(mphone.length()<10) {
            etphone.setError("Enter correct phone #.");
            valid = false;
        }else {
            etphone.setError(null);
        } String myaddress = etaddress.getText().toString();
        if (TextUtils.isEmpty(myaddress)) {
            etaddress.setError("Address can't be empty..");
            valid = false;
        } else {
            etaddress.setError(null);
        } String mypassword = password.getText().toString();
        if (TextUtils.isEmpty(mypassword)) {
            password.setError("Password can't be empty..");
            valid = false;
        } else if(mypassword.length()<6) {
            password.setError("Password must be more than 4 characters");
            valid = false;
        } else {
            password.setError(null);
        }
        String mycpassword = cpassword.getText().toString();
        if (TextUtils.isEmpty(mycpassword)) {
            cpassword.setError("Cornfirm password");
            valid = false;
        } else if (!mypassword.equals(mycpassword)) {
            cpassword.setError("Password do not match");
            valid = false;
        } else {
            cpassword.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
            onBackPressed();
            UserDatabase dbUser=new UserDatabase(
                    fname.getText().toString(),
                    lname.getText().toString(),
                    etphone.getText().toString(),
                    etaddress.getText().toString(),
                    "Admin"
            );

            mref.child("user").child(user.getUid()).setValue(dbUser);


    }
    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(("Please wait..."));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void CreateAccount(View view) {
        createAccount(etEmail.getText().toString(), password.getText().toString());

        // String sepnames[] = nam1.split(":");
        //Toast.makeText(getApplicationContext(),sepnames[0]+" and "+sepnames[1],Toast.LENGTH_LONG).show();
    }

    public void GoToLogin(View view) {
        finish();
    }

}
