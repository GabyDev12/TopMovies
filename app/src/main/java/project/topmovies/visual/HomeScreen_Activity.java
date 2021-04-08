package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.adapters.ViewPager_Adapter;
import project.topmovies.logic.statusApp;
import project.topmovies.visual.fragments.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class HomeScreen_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // VARIABLES //

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FrameLayout frag_container;

    ImageView nav_UserImage;
    TextView nav_Username;
    View nav_Header;
    Menu nav_Menu;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ViewPager contentPage;
    ViewPager_Adapter pAdapter;
    TabLayout tabLayoutContainer;
    TabItem item_billboard, item_comingSoon;


    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;


    // ACTIVITY ACTIONS //

    @Override
    protected void onRestart() {

        super.onRestart();

        if (statusApp.getInstance().myFilms == false
                && statusApp.getInstance().settings == false
                && statusApp.getInstance().about == false) {

            navigationView.setCheckedItem(R.id.nav_Home);

        }

        this.recreate();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);


        // Configuration for Toolbar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.titleHome);
        setSupportActionBar(toolbar);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Container for all fragments to load
        frag_container = findViewById(R.id.fragment_container);


        // Configuration for NavigationDrawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavView, R.string.closeNavView);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setCheckedItem(R.id.nav_Home);

        // If a user is logged in or not
        if (statusApp.getInstance().loggedIn) {

            // Change header of the NavigationView
            nav_Header = navigationView.getHeaderView(0);

            navigationView.removeHeaderView(nav_Header);
            navigationView.inflateHeaderView(R.layout.drawer_header_logged);

            // Update the data of the header
            nav_Header = navigationView.getHeaderView(0);

            nav_UserImage = nav_Header.findViewById(R.id.imageView_UserImage);
            nav_Username = nav_Header.findViewById(R.id.textView_UserName);


            // Load the profile image of the user
            if (mAuth.getCurrentUser().getPhotoUrl() != null) {

                Picasso.with(HomeScreen_Activity.this)
                        .load(mAuth.getCurrentUser().getPhotoUrl())
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.img_loading)
                        .into(nav_UserImage);

            }


            // Set email of user (Google user)
            if (statusApp.getInstance().gAuth == true) {

                nav_Username.setText(mAuth.getCurrentUser().getDisplayName());

            }

            // Set name of user (Normal user)
            else {

                FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        nav_Username.setText(snapshot.child("name").getValue(String.class) + " " + snapshot.child("lastName").getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(HomeScreen_Activity.this, R.string.problemLoadingUserData, Toast.LENGTH_LONG).show();

                    }

                });

            }

            // Change menu of the NavigationView
            nav_Menu = navigationView.getMenu();

            nav_Menu.setGroupVisible(R.id.group_userOptions, true);

        }


        // Configuration for ViewPager
        contentPage = findViewById(R.id.viewPager_Content);
        tabLayoutContainer = findViewById(R.id.tabLayout_Container);
        item_billboard = findViewById(R.id.tabItem_Billboard);
        item_comingSoon = findViewById(R.id.tabItem_ComingSoon);


        pAdapter = new ViewPager_Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayoutContainer.getTabCount());
        contentPage.setAdapter(pAdapter);

        tabLayoutContainer.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                contentPage.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        contentPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutContainer));


        // If it comes from the PDFViewer, load the MyFilms menu option
        if (statusApp.getInstance().myFilms == true) {

            getSupportActionBar().setTitle(R.string.titleMyFilms);

            tabLayoutContainer.setVisibility(View.INVISIBLE);
            contentPage.setVisibility(View.INVISIBLE);

            frag_container.setVisibility(View.VISIBLE);

            navigationView.setCheckedItem(R.id.nav_MyFilms);

            statusApp.getInstance().myFilms = false;

        }


        // If it comes from the any settings option, load the Settings menu option
        if (statusApp.getInstance().settings == true) {

            getSupportActionBar().setTitle(R.string.titleSettings);

            tabLayoutContainer.setVisibility(View.INVISIBLE);
            contentPage.setVisibility(View.INVISIBLE);

            frag_container.setVisibility(View.VISIBLE);

            navigationView.setCheckedItem(R.id.nav_Settings);

            statusApp.getInstance().settings = false;

        }


        // If it comes from the GitHub link option, load the About menu option
        if (statusApp.getInstance().about == true) {

            getSupportActionBar().setTitle(R.string.titleAbout);

            tabLayoutContainer.setVisibility(View.INVISIBLE);
            contentPage.setVisibility(View.INVISIBLE);

            frag_container.setVisibility(View.VISIBLE);

            navigationView.setCheckedItem(R.id.nav_About);

            statusApp.getInstance().about = false;

        }

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);

        }

        else if (navigationView.getCheckedItem().getItemId() != R.id.nav_Home) {

            getSupportActionBar().setTitle(R.string.titleHome);

            tabLayoutContainer.setVisibility(View.VISIBLE);
            contentPage.setVisibility(View.VISIBLE);

            frag_container.setVisibility(View.INVISIBLE);

            navigationView.setCheckedItem(R.id.nav_Home);

        }

        else {

            super.onBackPressed();

        }

    }


    // Configuration for sign up/sign in buttons

    public void nav_btnSignUp(View v) {

        Intent intentSignUp = new Intent(HomeScreen_Activity.this, SignUp_Activity.class);

        startActivity(intentSignUp);

    }

    public void nav_btnSignIn(View v) {

        Intent intentSignIn = new Intent(HomeScreen_Activity.this, SignIn_Activity.class);

        startActivity(intentSignIn);

    }


    // DRAWER MENU ACTION //

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {

            case R.id.nav_Home:

                getSupportActionBar().setTitle(R.string.titleHome);

                tabLayoutContainer.setVisibility(View.VISIBLE);
                contentPage.setVisibility(View.VISIBLE);

                frag_container.setVisibility(View.INVISIBLE);

                navigationView.setCheckedItem(R.id.nav_Home);

                break;

            case R.id.nav_MyFilms:

                getSupportActionBar().setTitle(R.string.titleMyFilms);

                tabLayoutContainer.setVisibility(View.INVISIBLE);
                contentPage.setVisibility(View.INVISIBLE);

                frag_container.setVisibility(View.VISIBLE);

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new MyFilms_Fragment());
                fragmentTransaction.commit();

                navigationView.setCheckedItem(R.id.nav_MyFilms);

                break;

            case R.id.nav_Profile:

                getSupportActionBar().setTitle(R.string.titleProfile);

                tabLayoutContainer.setVisibility(View.INVISIBLE);
                contentPage.setVisibility(View.INVISIBLE);

                frag_container.setVisibility(View.VISIBLE);

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Profile_Fragment());
                fragmentTransaction.commit();

                navigationView.setCheckedItem(R.id.nav_Profile);

                break;

            case R.id.nav_SignOut:

                // Firebase sign out
                mAuth.signOut();

                statusApp.getInstance().loggedIn = false;


                // Google sign out
                if (statusApp.getInstance().gAuth == true) {

                    // Configure Google Sign In
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken("209581455260-2qkd36kiu1vi8gqsumbrqrn6o7noi8gf.apps.googleusercontent.com")
                            .requestEmail()
                            .build();

                    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) { }

                            });

                    mGoogleSignInClient.revokeAccess()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) { }

                            });

                    statusApp.getInstance().gAuth = false;

                }


                // Reload the activity for apply the changes
                this.recreate();


                // Inform the user
                Toast.makeText(HomeScreen_Activity.this, R.string.signedOut, Toast.LENGTH_LONG).show();

                break;

            case R.id.nav_Settings:

                getSupportActionBar().setTitle(R.string.titleSettings);

                tabLayoutContainer.setVisibility(View.INVISIBLE);
                contentPage.setVisibility(View.INVISIBLE);

                frag_container.setVisibility(View.VISIBLE);

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Settings_Fragment());
                fragmentTransaction.commit();

                navigationView.setCheckedItem(R.id.nav_Settings);

                break;

            case R.id.nav_About:

                getSupportActionBar().setTitle(R.string.titleAbout);

                tabLayoutContainer.setVisibility(View.INVISIBLE);
                contentPage.setVisibility(View.INVISIBLE);

                frag_container.setVisibility(View.VISIBLE);

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new About_Fragment());
                fragmentTransaction.commit();

                navigationView.setCheckedItem(R.id.nav_About);

                break;

        }

        return false;

    }

}