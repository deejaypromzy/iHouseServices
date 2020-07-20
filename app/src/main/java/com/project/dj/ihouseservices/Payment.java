package com.project.dj.ihouseservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Payment extends AppCompatActivity {
    EditText amt;
    String[] spinnerTitles;
    int[] spinnerImages;
    Spinner mSpinner;
    private boolean isUserInteracting;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
amt=findViewById(R.id.amt);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        spinnerTitles = new String[]{"MTN Mobile Money", "Vodafone Cash", "Airtel Tigo Money", "Monegram", "GN Mobile Money"};
        spinnerImages = new int[]{R.drawable.momo
                , R.drawable.voda
                , R.drawable.airtel
                , R.drawable.moneyg
                , R.drawable.gn
               };

        CustomAdapter mCustomAdapter = new CustomAdapter(Payment.this, spinnerTitles, spinnerImages);
        mSpinner.setAdapter(mCustomAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isUserInteracting) {
                    Toast.makeText(Payment.this, spinnerTitles[i], Toast.LENGTH_SHORT).show();
                    mode= spinnerTitles[i];
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void cancel(View view) {
        finish();
    }
    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(("Check your phone to confirm..."));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    public void confirm(View view) {
        if (TextUtils.isEmpty(amt.getText().toString())) {
            Toast.makeText(this, "Amount is empty", Toast.LENGTH_SHORT).show();
            hideProgressDialog();
        }else {
            showProgressDialog();

        }
    }
}
