package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class HomePageActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentStateAdapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // set welcome label with name
        String welcomeText = "Welcome, " + getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(welcomeText);

        // create tabs
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentPageAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Mobiles"));
        tabLayout.addTab(tabLayout.newTab().setText("Cameras"));
        tabLayout.addTab(tabLayout.newTab().setText("Laptops"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // to update selected tab if I click on a specific one
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // to update selected tab if I'm sliding between tabs
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    // options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.cartmenu:
                Intent intent2 = new Intent(HomePageActivity.this, CartActivity.class);
                CartActivity.cartList.clear();
                startActivity(intent2);
                return true;

            case R.id.searchmenu:
                Intent intent3 = new Intent(HomePageActivity.this, SearchActivity.class);
                startActivity(intent3);
                return true;

            /*case R.id.ordersmenu:
                Intent intent4 = new Intent(HomePageActivity.this, OrdersActivity.class);
                startActivity(intent4);
                return true;*/

            case  R.id.logoutmenu:
                // clear remember me
                sharedPreferences = getSharedPreferences("remember me",MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("username", "");
                editor.putString("fullName", "");
                editor.putString("password", "");
                editor.putBoolean("login", false);
                editor.apply();

                // Back to login page
                Intent intent5 = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent5);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}