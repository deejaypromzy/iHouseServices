package com.project.dj.ihouseservices;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Requests extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mref;
    private FirebaseUser fireuser;
    private String userid;
    private requestAdapter mAdapter;
    private com.tuyenmonkey.mkloader.MKLoader mProgress;

    private ArrayList<Child> mSportsData;
    List<String> a;
    private SharedPreferences sharedpreferences;
    private TextView UserProfileEmail;
    private StorageReference UserProductImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        fireuser = auth.getCurrentUser();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mfirebaseDatabase.getReference();
        userid = fireuser.getUid();
        mProgress = findViewById(R.id.MKLoader);

        sharedpreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("id", userid);
        editor.apply();


        ;
//Initialize the RecyclerView
        final RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        //Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initialize the adapter and set it ot the RecyclerView


        UserProductImageRef = FirebaseStorage.getInstance().getReference();


        //Initialize the ArrayList that will contain the data
        mSportsData = new ArrayList<>();
        mAdapter = new requestAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);
        //Get the data


//        //Helper class for creating swipe to dismiss and drag and drop functionality
//        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
//                (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN
//                        | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
//
////            @Override
////            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
////                                  RecyclerView.ViewHolder target) {
////
////                //Get the from and to position
////                int from = viewHolder.getAdapterPosition();
////                int to = target.getAdapterPosition();
////
////                //Swap the items and notify the adapter
////                Collections.swap(mSportsData, from, to);
////                mAdapter.notifyItemMoved(from, to);
////                return true;
////            }
//
//
////            @Override
////            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
////
////                //Remove the item from the dataset
////                mSportsData.remove(viewHolder.getAdapterPosition());
////
////                //Notify the adapter
////                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
////            }
//        });

        //Attach the helper to the RecyclerView
        //   helper.attachToRecyclerView(mRecyclerView);


//// My top posts by number of stars
//        mref.child("user").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//               a = new ArrayList<String>();
//                for (DataSnapshot ds: dataSnapshot.getChildren()) {
//                    // TODO: handle the post
//                    Log.w("error", ds.toString());
//
//
//                    UserDatabase proDatabase = new UserDatabase();
//
//                   proDatabase.setProName((ds.getValue(UserDatabase.class)).getProName());
//                   proDatabase.setProDesc((ds.getValue(UserDatabase.class)).getProDesc());
//
//                    a.add(proDatabase.getProName());
//                    a.add(proDatabase.getProDesc());
//
//
//
//                }
//
//                String[] myArray = new String[a.size()];
//                a.toArray(myArray);
//
//                for (String arr:myArray) {
//                    Toast.makeText(MainActivity.this, arr, Toast.LENGTH_SHORT).show();
//
//                }
//
//
//
//            }
//
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("error", "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        });
//

        new CountDownTimer(2000, 1000) {
            public void onTick(long ms) {
                mProgress.setVisibility(View.VISIBLE);
            }

            public void onFinish() {
                mref.child("contracts").orderByChild("contractString").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        showData(dataSnapshot);
                        mProgress.setVisibility(View.GONE);


                        mref.child("contracts").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mCartItemCount = (int) dataSnapshot.getChildrenCount();
                                setupBadge();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


//                        String[] myArray = new String[a.size()];
//                a.toArray(myArray);
//
//                for (String arr:myArray) {
//                    Toast.makeText(MainActivity.this, arr, Toast.LENGTH_SHORT).show();
//
//                }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//c.close();
            }
        }.start();
        // initializeData();
    }


    private void showData(DataSnapshot dataSnapshot) {
        //Create the ArrayList of Sports objects with the titles, images
        // and information about each sport
        mSportsData.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            UserDatabase userDatabase = new UserDatabase();

            userDatabase.setContractStatus((ds.getValue(UserDatabase.class)).getContractStatus());
            userDatabase.setContractString((ds.getValue(UserDatabase.class)).getContractString());
             userDatabase.setContractRemarks((ds.getValue(UserDatabase.class)).getContractRemarks());
             userDatabase.setContractDate((ds.getValue(UserDatabase.class)).getContractDate());


            mSportsData.add(new Child(userDatabase.getContractStatus(), userDatabase.getContractString(), userDatabase.getContractDate(), userDatabase.getContractRemarks()
            ));


//            a.add(userDatabase.getFirstName());
//            a.add(userDatabase.getLastName());

        }

        //Recycle the typed array

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }


    private void initializeData() {


        //Get the resources from the XML file
        String[] childList = {"one", "two", "three"};
        String[] childInfo = {"one", "two", "three"};
        String[] childDetail = {"one", "two", "three"};
        String[] childNews = {"one", "two", "three"};
        //Clear the existing data (to avoid duplication)
        mSportsData.clear();


        //Create the ArrayList of Sports objects with the titles, images
        // and information about each sport
//        for(int i=0; i<childList.length; i++){
//            mSportsData.add(new Child(childList[i], childInfo[i], childDetail[i],childNews[i],
//                    childImageResources.getResourceId(i,0)));
//        }

        //Recycle the typed array

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {

            //startActivity(new Intent(FarmerPurchase.this,CartItems.class));
//            {
//                Intent intent = new Intent(Requests.this, MainActivity.class);
//                intent.putExtra("ID", "userIDPLacedhere.........");
//                startActivity(intent);
//            }


            //startActivity(new Intent(getApplicationContext(), Cart.class));

        }


        return super.onOptionsItemSelected(item);


    }
}