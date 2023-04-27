package com.ftn.ma_sit_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.ftn.ma_sit_project.fragments.HomeFragment;
import com.ftn.ma_sit_project.fragments.ProfileFragment;
import com.ftn.ma_sit_project.fragments.RegistrationFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_now, R.string.close_now);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .setReorderingAllowed(true)
//                   .addToBackStack("home")
                    .commit();

            navigationView.setCheckedItem(R.id.nav_item_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_item_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.nav_item_profile:
                replaceFragment(new ProfileFragment());
                break;
            case R.id.nav_item_register:
                replaceFragment(new RegistrationFragment());
                break;
//            case R.id.aaaa:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  new NazivFragmenta()).commit();
//                break;
//            case R.id.aaaaa:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  new NazivFragmenta()).commit();
//                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (!(currentFragment instanceof HomeFragment)){
            replaceFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_item_home);
        }
        else {
            super.onBackPressed();
        }

    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setReorderingAllowed(true)
                .commit();
    }
}