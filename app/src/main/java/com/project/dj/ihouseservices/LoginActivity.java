package com.project.dj.ihouseservices;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final int PERMS_REQUEST_CODE =123456 ;
    // UI references.
    private EditText mEmailView,mPasswordView;


    // [START declare_auth]
    private FirebaseAuth mAuth;
    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mref;
    private String userType;
    private SharedPreferences pref;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);


        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);


// [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mfirebaseDatabase.getReference();
        // [END initialize_auth]

    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
  //   [END on_start_check_user]


    private void signIn(String email, String password) {
            if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                   /*     // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication Denied.",
                                    Toast.LENGTH_SHORT).show();                        }*/
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailView.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Email can't be empty.");
            mEmailView.requestFocus();
            return false;
        }  else if (!Patterns.EMAIL_ADDRESS.matcher(mEmailView.getText().toString().trim()).matches()) {
        mEmailView.setError("Email correct email.");
            mEmailView.requestFocus();
            return false;
        } else {
            mEmailView.setError(null);
        }
        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Password can't be empty.");
            mPasswordView.requestFocus();
            return false;
        } else {
            mPasswordView.setError(null);
        }
        return true;
    }
    boolean noPermissions() {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE};

        for (String s : permissions) {
            res = checkCallingOrSelfPermission(s);
            if (!(res == PackageManager.PERMISSION_GRANTED))
                return true;
        }
        return false;

    }

    private void requestPermission() {
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, PERMS_REQUEST_CODE);
    }
    private void updateUI(final FirebaseUser user) {
        if (user != null) {
            if (pref.getString("userType", null)!=null){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user", pref.getString("userType", null));
                startActivity(intent);
                finish();
            }else {
                mref.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        showData(dataSnapshot);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user", userType);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }



        }

    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            UserDatabase post = dataSnapshot.getValue(UserDatabase.class);
            userType=Objects.requireNonNull(post).getRole();

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("userType",userType);
            editor.apply();
        }

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



    public void Signinmeton(View view) {

        if (noPermissions()){
            requestPermission();
        }else {
            signIn(mEmailView.getText().toString(), mPasswordView.getText().toString());
        }




      //startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    public void GoToSignup(View view) {
        startActivity(new Intent(LoginActivity.this,SignupActivity.class));
    }

    public void GoToForgotPassword(View view) {
        //startActivity(new Intent(LoginActivity.this,PasswordReset.class));
        startActivity(new Intent(LoginActivity.this,PasswordReset.class));
    }


}

