package com.ftn.ma_sit_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.ftn.ma_sit_project.adapters.ProgramAdapter;
import com.ftn.ma_sit_project.fragments.FrendListFragment;
import com.ftn.ma_sit_project.fragments.HomeFragment;
import com.ftn.ma_sit_project.fragments.ProfileFragment;
import com.ftn.ma_sit_project.fragments.RankListFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ListView listView;
    ProgramAdapter programAdapter;

    String[] title = {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa"};
    String[] description = {"b","bb","bbb","bbbb","bbbbb","bbbbbb","bbbbbbb","bbbbbbbb","bbbbbbbbb"};
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
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
            case R.id.nav_item_friends_list:
                replaceFragment(new FrendListFragment());
                break;
            case R.id.nav_item_rank_list:
                replaceFragment(new RankListFragment());
                break;
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