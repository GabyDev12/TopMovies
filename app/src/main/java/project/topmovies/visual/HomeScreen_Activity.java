package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.ViewPager_Adapter;
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

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class HomeScreen_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ViewPager contentPage;
    TabLayout tabLayoutContainer;
    TabItem item_billboard, item_comingsoon;

    ViewPager_Adapter pAdapter;

    FrameLayout frag_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        frag_container = findViewById(R.id.fragment_container);

        contentPage = findViewById(R.id.viewPager_Content);
        tabLayoutContainer = findViewById(R.id.tabLayout_Container);
        item_billboard = findViewById(R.id.tabItem_Billboard);
        item_comingsoon = findViewById(R.id.tabItem_ComingSoon);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.nav_Home) {

            tabLayoutContainer.setVisibility(View.VISIBLE);
            contentPage.setVisibility(View.VISIBLE);

            frag_container.setVisibility(View.INVISIBLE);

        }

        if (item.getItemId() == R.id.nav_About) {

            tabLayoutContainer.setVisibility(View.INVISIBLE);
            contentPage.setVisibility(View.INVISIBLE);

            frag_container.setVisibility(View.VISIBLE);

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new About_Fragment());
            fragmentTransaction.commit();

        }

        return false;

    }

}