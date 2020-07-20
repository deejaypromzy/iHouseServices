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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
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

public class ManageServices extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
    private ManageAdapter mAdapter;
    private com.tuyenmonkey.mkloader.MKLoader mProgress;

    private ArrayList<Child> mSportsData;
    List<String> a;
    private SharedPreferences sharedpreferences;
    private TextView UserProfileEmail;
    private StorageReference UserProductImageRef;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        auth= FirebaseAuth.getInstance();
        fireuser=auth.getCurrentUser();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mfirebaseDatabase.getReference();
        userid=fireuser.getUid();
        mProgress=findViewById(R.id.MKLoader);

        sharedpreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("id", userid);
        editor.apply();


//Initialize the RecyclerView
        final RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        //Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initialize the adapter and set it ot the RecyclerView


        UserProductImageRef = FirebaseStorage.getInstance().getReference();





        //Initialize the ArrayList that will contain the data
        mSportsData = new ArrayList<>();
        mAdapter = new ManageAdapter(this, mSportsData);
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

        new CountDownTimer(2000,1000)
        {
            public void onTick(long ms)
            {
                mProgress.setVisibility(View.VISIBLE);
            }
            public void onFinish(){
                mref.child("products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        showData(dataSnapshot);
                        mProgress.setVisibility(View.GONE);

                        mref.child("user").child(userid).child("carts").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mCartItemCount= (int) dataSnapshot.getChildrenCount();
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
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                new CountDownTimer(2000,1000)
                {
                    public void onTick(long ms)
                    {
                        mProgress.setVisibility(View.VISIBLE);
                    }
                    public void onFinish(){
                        mref.child("products").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                showData(dataSnapshot);
                                mProgress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }.start();
                // initializeData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void showData(DataSnapshot dataSnapshot) {
        //Create the ArrayList of Sports objects with the titles, images
        // and information about each sport
        mSportsData.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            UserDatabase userDatabase = new UserDatabase();

            userDatabase.setProName((ds.getValue(UserDatabase.class)).getProName());
            userDatabase.setProPrice((ds.getValue(UserDatabase.class)).getProPrice());
            userDatabase.setProLocation((ds.getValue(UserDatabase.class)).getProLocation());
            userDatabase.setProDesc((ds.getValue(UserDatabase.class)).getProDesc()) ;
            userDatabase.setPhoto_url((ds.getValue(UserDatabase.class)).getPhoto_url());
            userDatabase.setApproval((ds.getValue(UserDatabase.class)).getApproval());
            userDatabase.setPhoneNumber((ds.getValue(UserDatabase.class)).getPhoneNumber());
            userDatabase.setCategory((ds.getValue(UserDatabase.class)).getCategory());

//            mref.child("user").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    for (DataSnapshot ds : dataSnapshot.getChildren()){
//                        UserDatabase userDatabase = new UserDatabase();
//
//                        userDatabase.setFirstName((ds.getValue(UserDatabase.class)).getFirstName());
//                        userDatabase.setLastName((ds.getValue(UserDatabase.class)).getLastName());
//                        userDatabase.setPhoneNumber((ds.getValue(UserDatabase.class)).getPhoneNumber());
//
//                        mSportsData.add(new Child(userDatabase.getProName(), userDatabase.getProPrice(), userDatabase.getProLocation(),userDatabase.getProDesc(),userDatabase.getPhoto_url(),userDatabase.getApproval(),userDatabase.getFirstName(), userDatabase.getLastName(), userDatabase.getPhoneNumber()
//                        ));
////            a.add(userDatabase.getFirstName());
////            a.add(userDatabase.getLastName());
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
            if (!Objects.equals(userDatabase.getApproval().trim(), "no")){

                        if (tabLayout.getSelectedTabPosition()==userDatabase.getCategory()-1) {
                            mSportsData.add(new Child(userDatabase.getProName(), userDatabase.getProPrice(), userDatabase.getProLocation(),userDatabase.getProDesc(),userDatabase.getPhoto_url(),userDatabase.getApproval(),userDatabase.getPhoneNumber()));
                        }


           }







//            a.add(userDatabase.getFirstName());
//            a.add(userDatabase.getLastName());

        }

        //Recycle the typed array

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }


    private void initializeData() {



        //Get the resources from the XML file
            String[]childList = {"one","two","three"};
        String[] childInfo = {"one","two","three"};
        String[] childDetail = {"one","two","three"};
        String[] childNews = {"one","two","three"};
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
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private String tab;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main3, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            tab= String.valueOf(getArguments().getInt(ARG_SECTION_NUMBER));
           // textView.setText(tab);
            return rootView;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            {
//                Intent intent = new Intent(ManageServices.this, ManageServices.class);
//                intent.putExtra("ID", "userIDPLacedhere.........");
//                startActivity(intent);
            }



            //startActivity(new Intent(getApplicationContext(), Cart.class));

        }else
        if (id == R.id.action_settings) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManageServices.this);
            alertDialog.setTitle("iHouse Services");
            alertDialog.setIcon(R.mipmap.ic_launcher_round);
            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to log out?");


            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Write your code here to invoke YES event
                    signOut();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sell) {
          startActivity(new Intent(this,AddServices.class));
        } else if (id == R.id.addAdmin) {
            startActivity(new Intent(this,addAdmin.class));

        } else if (id == R.id.approve) {
            startActivity(new Intent(this,approveService.class));
        } else if (id == R.id.changePassword){
            startActivity(new Intent(this,PasswordReset.class));
        } else if (id == R.id.edit) {
            startActivity(new Intent(this,EditActivity.class));
        }else if (id == R.id.requests) {
            startActivity(new Intent(this,Requests.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {

//        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
//        // [END initialize_auth]*/
//        fireuser = mAuth.getCurrentUser();
//        mfirebaseDatabase = FirebaseDatabase.getInstance();
//        mref = mfirebaseDatabase.getReference();
//        userid = fireuser.getUid();
        mAuth.signOut();
        super.finish();

    }

    public void carts(View view) {
        startActivity(new Intent(this,ManageServices.class));
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 11;
        }
    }

}
