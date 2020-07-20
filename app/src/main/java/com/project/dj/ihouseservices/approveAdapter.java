/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.project.dj.ihouseservices;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/***
 * The adapter class for the RecyclerView, contains the sports data
 */
class approveAdapter extends RecyclerView.Adapter<approveAdapter.ChildViewHolder>  {

    //Member variables
    private GradientDrawable mGradientDrawable;
    private ArrayList<Child> mSportsData;
    private Context mContext;

    /**
     * Constructor that passes in the sports data and the context
     * @param sportsData ArrayList containing the sports data
     * @param context Context of the application
     */
    approveAdapter(Context context, ArrayList<Child> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;

        //Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext,R.drawable.avatar);
        if(drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }


    /**
     * Required method for creating the viewholder objects.
     * @param parent The ViewGroup into which the new View is added after it is
     *               bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly create ChildViewHolder.
     */
    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildViewHolder(mContext, LayoutInflater.from(mContext).
                inflate(R.layout.approve_template, parent, false), mGradientDrawable);
    }

    /**
     * Required method that binds the data to the viewholder.
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {

        //Get the current sport
        Child currentChild = mSportsData.get(position);

        //Bind the data to the views
        holder.bindTo(currentChild);

    }


    /**
     * Required method for determining the size of the data set.
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mSportsData.size();
    }


    /**
     * ChildViewHolder class that represents each row of data in the RecyclerView
     */
    static class ChildViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final SharedPreferences sharedpreferences;
        private final StorageReference UserProductImageRef;
        private DateFormat df;
        private Date date;        //Member Variables for the holder data
      //  private RatingBar price;
        private TextView location,desc;
        private TextView name,subtractMeal,quantityText,addMeal;
        private ImageView mSportsImage;
        private Context mContext;
        private Child mCurrentChild;
        private GradientDrawable mGradientDrawable;
        private DatabaseReference mref;
        private FirebaseDatabase mfirebaseDatabase;
        private FirebaseUser fireuser;
        private FirebaseAuth auth;
        private String userid;
        private String URL;
        private Button call,chat;
        private LinearLayout button4;


        /**
         * Constructor for the ChildViewHolder, used in onCreateViewHolder().
         * @param itemView The rootview of the list_item.xml layout file
         */
        ChildViewHolder(final Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
           // price = itemView.findViewById(R.id.ratingBar);
            button4 = itemView.findViewById(R.id.button4);
            call = itemView.findViewById(R.id.btnCall);
            chat = itemView.findViewById(R.id.btnChat);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            desc = itemView.findViewById(R.id.desc);
            mSportsImage = itemView.findViewById(R.id.img);

            mContext = context;
            mGradientDrawable = gradientDrawable;


            sharedpreferences = mContext.getSharedPreferences("userid", Context.MODE_PRIVATE);
            userid=sharedpreferences.getString("id", "");

            mfirebaseDatabase = FirebaseDatabase.getInstance();
            mref = mfirebaseDatabase.getReference();

            UserProductImageRef = FirebaseStorage.getInstance().getReference();


            //Set the OnClickListener to the whole view
            button4.setOnClickListener(this);
//            subtractMeal.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int qty = Integer.parseInt(quantityText.getText().toString().trim());
//                    if (qty>=1 & qty<=100){
//                        qty--;
//                        quantityText.setText(qty+"");
//                    }
//
//
//                    UserDatabase currentChild = new UserDatabase(
//                            name.getText().toString(),
//                            price.getText().toString(),
//                            location.getText().toString(),
//                            desc.getText().toString(),URL,
//                            quantityText.getText().toString()
//                    );
//
//                    Toast.makeText(mContext, qty+"", Toast.LENGTH_SHORT).show();
//                    mref.child("user").child(userid).child("carts").child(name.getText().toString()).setValue(currentChild);
//
//                }
//            });
//
//            addMeal.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int qty = Integer.parseInt(quantityText.getText().toString().trim());
//                    if (qty>=0 & qty<100){
//                        qty++;
//                        quantityText.setText(qty+"");
//                    }
//
//                    UserDatabase child = new UserDatabase(
//                            name.getText().toString(),
//                            price.getText().toString(),
//                            location.getText().toString(),
//                            desc.getText().toString(),URL,
//                            quantityText.getText().toString()
//                            );
//
//                    mref.child("user").child(userid).child("carts").child(name.getText().toString()).setValue(child);
//
//                }
//            });

            df = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            date = new Date();


            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("iHouse Services");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    // Setting Dialog Message
                    alertDialog.setMessage("Do you really want to remove \""+mCurrentChild.getName()+"\" service provider?");


                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            mref.child("products").child(mCurrentChild.getName()).removeValue();

                        }
                    });


                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
            });

            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("iHouse Services");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    // Setting Dialog Message
                    alertDialog.setMessage("Do you really want to approve \""+mCurrentChild.getName()+"\" service provider?");


                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            mref.child("products").child(mCurrentChild.getName()).child("approval").setValue("yes");



                            new CountDownTimer(3000,1000)
                            {
                                public void onTick(long ms)
                                {
                                    call.setText("Please Wait...");

                                }
                                public void onFinish()
                                {
                                    call.setText("Service Approved");

                                }
                            }.start();



                        }
                    });


                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
            });
        }
        public String removeFirstChar(String s){
            return s.substring(1);
        }
        public void whatsAppIntent(String phone_no,String str) {
            Uri mUri = Uri.parse("https://api.whatsapp.com/send?phone=" + phone_no + "&text=" + str);
            Intent mIntent = new Intent("android.intent.action.VIEW", mUri);
            mIntent.setPackage("com.whatsapp");
            mContext.startActivity(mIntent);

        }

        private void setRatingStarColor(Drawable drawable, @ColorInt int color)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                DrawableCompat.setTint(drawable, color);
            }
            else
            {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        }

        void bindTo(Child currentChild){
            //Populate the textviews with data
            name.setText(currentChild.getName());
            location.setText((currentChild.getLocation()));
            desc.setText((currentChild.getDesc()));
            URL=currentChild.getImageResource();

            //Get the current sport
            mCurrentChild = currentChild;

            GlideApp.with(mContext)
                    .load(currentChild.
                            getImageResource())
                    .placeholder(R.drawable.no_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(mSportsImage);

            //Load the images into the ImageView using the Glide library
//            Glide.with(mContext).load(currentChild.
//                    getImageResource()).placeholder(mGradientDrawable).into(mSportsImage);
        }

        @Override
        public void onClick(View view) {

            //Set up the detail intent
            Intent detailIntent = Child.starter(mContext,
                    mCurrentChild.getName(),
                    mCurrentChild.getPrice(),
                    mCurrentChild.getDesc(),
                    mCurrentChild.getLocation(),
                    mCurrentChild.getImageResource());


            //Start the detail activity
            mContext.startActivity(detailIntent);
        }
    }
}
