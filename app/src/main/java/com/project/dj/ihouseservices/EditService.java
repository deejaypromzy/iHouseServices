package com.project.dj.ihouseservices;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.tuyenmonkey.mkloader.MKLoader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import fr.ganfra.materialspinner.MaterialSpinner;

public class EditService extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMS_REQUEST_CODE = 213123;
    private Button addImage,addPro;
private EditText proName,proDesc,proLocation,productContact;
private ImageView proImage;
private MKLoader progressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mref;
    private FirebaseUser fireuser;
    private ProgressDialog mProgress;
    private String userid;
    final static int Gallery_Pick = 1;
    private StorageTask storageTask;
    private StorageReference UserProductImageRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DateFormat df;
    public static String markerString;
    private Date date;
    private  Uri resultUri;
    private MaterialSpinner spinner;
    ArrayAdapter<String> adapter;
    private String category;
    private int cat;
    private String serviceName;
    String[] ITEMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        serviceName=getIntent().getStringExtra("seviceName");


        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]*/
        mref= FirebaseDatabase.getInstance().getReference();
        mProgress=new ProgressDialog(this);
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        auth= FirebaseAuth.getInstance();
        fireuser=auth.getCurrentUser();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mfirebaseDatabase.getReference();
        userid=fireuser.getUid();
        UserProductImageRef = FirebaseStorage.getInstance().getReference().child("Product_Images").child(df.format(date));


        addImage=findViewById(R.id.productImageBtn);
        addPro=findViewById(R.id.addPro);
        proName=findViewById(R.id.productName);
        proDesc=findViewById(R.id.ProductDesc);
        proLocation=findViewById(R.id.productLocation);
        productContact=findViewById(R.id.productContact);
        proImage=findViewById(R.id.productImage);
        progressBar=findViewById(R.id.pbbar);

        addPro.setOnClickListener(this);
        addImage.setOnClickListener(this);


         ITEMS = new String[]{"Electrical", "Plumbing", "Locksmith",
                 "Vulcanizing", "Painting", "Lawn care", "Mechanic",
                 "Laundry", "Pool", "Nanny", "Pest Control"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner) findViewById(R.id.gender);
        spinner.setAdapter(adapter);
        spinner.setPaddingSafe(0, 0, 0, 0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selectedItemView != null) {
                    selectedItemView.setPadding(0, 0, 0, 0);//remove extra padding
                    category = ((TextView) selectedItemView).getText().toString();

//                    if (!strItems.equals("Select Product")) {
//
//                    }
//                    else {
//
                    //                      Toast.makeText(SignupActivity.this, "Scan to select product  "+mgender, Toast.LENGTH_SHORT).show();
//
//                        }
                }
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parentView.getChildAt(0)).setTextSize(20);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                spinner.setError("Select a Category");
                spinner.setErrorColor(R.color.error_color);
            }

        });


   /*                File file = getApplicationContext().getFileStreamPath(userid + ".jpg");
            if (file.exists()) {

                Log.d("file", "my_image exists!");
                proImage.setImageBitmap(loadImageBitmap(getApplicationContext(), userid + ".jpg"));
            }else {
                UserProductImageRef.child(userid + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        GlideApp.with(AddServices.this)
                                .load(uri)
                                .placeholder(R.drawable.no_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .into(proImage);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                            }
                        });

        }*/



        new CountDownTimer(3000,1000)
        {
            public void onTick(long ms)
            {   mProgress.setMessage("Please wait...");
                mProgress.show();

            }
            public void onFinish(){
                Toast.makeText(EditService.this, serviceName, Toast.LENGTH_SHORT).show();
                mref.child("products").child(serviceName).addListenerForSingleValueEvent(new ValueEventListener() {
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
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){

            UserDatabase post = dataSnapshot.getValue(UserDatabase.class);
            proName.setText(Objects.requireNonNull(post).getProName());
            proDesc.setText(Objects.requireNonNull(post).getProDesc());
            proLocation.setText(Objects.requireNonNull(post).getProLocation());
            productContact.setText(Objects.requireNonNull(post).getPhoneNumber());
            spinner.setSelection(Objects.requireNonNull(post).getCategory());

            GlideApp.with(EditService.this)
                                        .load(Objects.requireNonNull(post).getPhoto_url())
                                        .placeholder(R.drawable.no_image)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .priority(Priority.HIGH)
                                        .into(proImage);

        }

    }
//    private void showData(DataSnapshot dataSnapshot) {
//        for (DataSnapshot ds : dataSnapshot.getChildren()){
//
//
//            UserDatabase userDatabase = ds.getValue(UserDatabase.class);
//
//            proName.setText(userDatabase.getProName().toString());
//            proLocation.setText(userDatabase.getProLocation());
//            proDesc.setText(userDatabase.getProDesc());
//            productContact.setText(userDatabase.getPhoneNumber());
//          //  proImage.setImageResource(R.drawable.avatar);
//
//        }
//
//    }
    private boolean validateForm() {
        String myfname = proName.getText().toString();
        if (TextUtils.isEmpty(myfname)) {
            proName.setError("Product Name can't be empty.");
            proName.requestFocus();
            return true;
        } else {
            proName.setError(null);
        }
        String mylname = proDesc.getText().toString();
        if (TextUtils.isEmpty(mylname)) {
            proDesc.setError("Product Description can't be empty.");
           proDesc.requestFocus();
           return true;
        } else {
            proDesc.setError(null);
        }

//        String price = proPrice.getText().toString();
//        if (TextUtils.isEmpty(price)) {
//            proPrice.setError("Product Price can't be empty.");
//           proPrice.requestFocus();
//           return true;
//        } else {
//            proPrice.setError(null);
//        }
        String location = proLocation.getText().toString();
        if (TextUtils.isEmpty(location)) {
            proLocation.setError("Location can't be empty.");
           proLocation.requestFocus();
           return true;
        } else {
            proLocation.setError(null);
        }

        String productCon = productContact.getText().toString();
        if (TextUtils.isEmpty(productCon)) {
            productContact.setError("Location can't be empty.");
            productContact.requestFocus();
           return true;
        } else {
            productContact.setError(null);
        }


        if (TextUtils.isEmpty(category) || category.contains("Select Category")) {
            spinner.setError("Select a Category");
            spinner.requestFocus();
            spinner.setErrorColor(R.color.error_color);
           return true;
        } else {
            spinner.setError(null);
        }
        if (proImage.getDrawable()== null) {
            Toast.makeText(this, "Product Image Cant be empty", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (category){
            case "Electrical":
                 cat = 1;
                break;
            case "Plumbing":
                cat = 2;
                break;
             case "Locksmith":
                 cat = 3;
                 break;
             case "Vulcanizing":
                 cat = 4;
                 break;
             case "Painting":
                 cat = 5;
                 break;
             case "Lawn care":
                 cat = 6;
                 break;
             case "Mechanic":
                 cat = 7;
                 break;
            case "Laundry":
                cat = 8;
                break;
             case "Pool":
                 cat = 9;
                 break;
             case "Nanny":
                 cat = 10;
                 break;
            case "Pest Control":
                cat = 11;
                break;
        }
        switch (v.getId()){
            case R.id.addPro:
                if (validateForm()){
                    return;
                }
                showProgressDialog();
                 user = mAuth.getCurrentUser();

                if (user != null) {
                    final StorageReference filePath = UserProductImageRef.child(userid + ".jpg");
                    storageTask=filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserDatabase dbUser= new UserDatabase(
                                            proName.getText().toString(),
                                            proLocation.getText().toString(),
                                            proDesc.getText().toString(),
                                            uri.toString(),
                                            "yes",
                                            productContact.getText().toString(),
                                            "0.0",
                                            cat
                                    );
                                    Toast.makeText(EditService.this, resultUri.toString(), Toast.LENGTH_SHORT).show();
                                    mref.child("products").child(proName.getText().toString()).setValue(dbUser);
                                }
                            });

                            progressBar.setVisibility(View.GONE);
                            onBackPressed();
//                            try{
//                                new DownloadImage().execute(resultUri.toString());
//
//                            }catch (Exception ignored){
//
//                            }finally {
//                                GlideApp.with(AddServices.this)
//                                        .load(resultUri)
//                                        .placeholder(R.drawable.no_image)
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        .priority(Priority.HIGH)
//                                        .into(proImage);
//                            }





                        }

                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(EditService.this, "Error Updating , Check Internet Connectivity", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            })



                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            });


                }
                break;

            case R.id.productImageBtn:
                if(NoPermissions()){
                    requestPermission();
                    return;
                }

                if (storageTask !=null && storageTask.isInProgress())
                { Toast.makeText(EditService.this, "Uploading product picture.....", Toast.LENGTH_SHORT).show();

                }else {

                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, Gallery_Pick);

                }

                break;

        }
    }
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(("Add Products..."));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null)
        {
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1, 1)
                    .start(this);


        }

        else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK)
            {
                 resultUri = result.getUri();

                GlideApp.with(EditService.this)
                        .load(resultUri)
                        .placeholder(R.drawable.no_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .into(proImage);

            }
            else
            {
                Toast.makeText(this, "Error Occured: Image can not be cropped. Try Again.", Toast.LENGTH_SHORT).show();
                //loadingBar.dismiss();
            }
        }
       else {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }
    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "DownloadImage";
        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(Bitmap result) {
            progressBar.setVisibility(View.GONE);
            saveImage(getApplicationContext(), result, userid + ".jpg");
        }
    }
    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.JPEG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }


    public void whatsAppIntent(String phone_no,String str) {
        Uri mUri = Uri.parse("https://api.whatsapp.com/send?phone=" + phone_no + "&text=" + str);
        Intent mIntent = new Intent("android.intent.action.VIEW", mUri);
        mIntent.setPackage("com.whatsapp");
        startActivity(mIntent);

    }
    private void requestPermission() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, PERMS_REQUEST_CODE);
    }
    boolean NoPermissions() {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE};

        for (String s : permissions) {
            res = EditService.this.checkCallingOrSelfPermission(s);
            if (!(res == PackageManager.PERMISSION_GRANTED))
                return true;
        }
        return false;

    }
}
