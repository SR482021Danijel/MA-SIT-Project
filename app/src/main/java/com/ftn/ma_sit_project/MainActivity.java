package com.ftn.ma_sit_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_now, R.string.close_now);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.a);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.a:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  new HomeFragment()).commit();
                break;
            case R.id.aa:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  new ProfileFragment()).commit();
                break;
//            case R.id.aaa:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  new NazivFragmenta()).commit();
//                break;
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

}