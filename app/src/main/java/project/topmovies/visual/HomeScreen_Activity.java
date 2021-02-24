package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.adapters.ViewPager_Adapter;
import project.topmovies.visual.fragments.*;
import static project.topmovies.logic.statusApp.*;

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

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


public class HomeScreen_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // VARIABLES //

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FrameLayout frag_container;
    View nav_Header;
    Menu nav_Menu;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ViewPager contentPage;
    ViewPager_Adapter pAdapter;
    TabLayout tabLayoutContainer;
    TabItem item_billboard, item_comingsoon;

    private FirebaseAuth mAuth;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Container for all fragments to load
        frag_container = findViewById(R.id.fragment_container);


        // Configuration for NavigationDrawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        // If a user is logged in or not
        if (loggedIn == true) {

            // Change header of the NavigationView
            nav_Header = navigationView.getHeaderView(0);

            navigationView.removeHeaderView(nav_Header);
            navigationView.inflateHeaderView(R.layout.drawer_header_logged);

            // Change menu of the NavigationView
            nav_Menu = navigationView.getMenu();

            nav_Menu.setGroupVisible(R.id.group_userOptions, true);

        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavView, R.string.closeNavView);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setCheckedItem(R.id.nav_Home);


        // Configuration for ViewPager
        contentPage = findViewById(R.id.viewPager_Content);
        tabLayoutContainer = findViewById(R.id.tabLayout_Container);
        item_billboard = findViewById(R.id.tabItem_Billboard);
        item_comingsoon = findViewById(R.id.tabItem_ComingSoon);


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

    public void nav_btnGSignIn(View v) {

        Intent intentGSignIn = new Intent(HomeScreen_Activity.this, gSignIn_Activity.class);

        startActivity(intentGSignIn);

    }


    // DRAWER MENU ACTION //

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {

            case R.id.nav_Home:

                tabLayoutContainer.setVisibility(View.VISIBLE);
                contentPage.setVisibility(View.VISIBLE);

                frag_container.setVisibility(View.INVISIBLE);

                navigationView.setCheckedItem(R.id.nav_Home);

                break;

            case R.id.nav_MyFilms:

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

                // Sign out action

                break;

            case R.id.nav_Settings:

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