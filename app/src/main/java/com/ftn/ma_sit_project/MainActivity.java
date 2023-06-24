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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.adapters.ProgramAdapter;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;
import com.ftn.ma_sit_project.fragments.FrendListFragment;
import com.ftn.ma_sit_project.fragments.HomeFragment;
import com.ftn.ma_sit_project.fragments.LoginFragment;
import com.ftn.ma_sit_project.fragments.ProfileFragment;
import com.ftn.ma_sit_project.fragments.RankListFragment;
import com.ftn.ma_sit_project.fragments.RegistrationFragment;
import com.google.android.material.navigation.NavigationView;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ProgramAdapter programAdapter;

    String[] title = {"a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa"};
    String[] description = {"b", "bb", "bbb", "bbbb", "bbbbb", "bbbbbb", "bbbbbbb", "bbbbbbbb", "bbbbbbbbb"};
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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .setReorderingAllowed(true)
                    .commit();

            navigationView.setCheckedItem(R.id.nav_item_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.nav_item_profile:
                if (Data.loggedInUser == null) {
                    replaceFragment(new LoginFragment());
                    navigationView.getMenu().findItem(R.id.nav_item_log_in).setChecked(true);
                } else {
                    replaceFragment(new ProfileFragment());
                }
                break;
            case R.id.nav_item_friends_list:
                replaceFragment(new FrendListFragment());
                break;
            case R.id.nav_item_rank_list:
                replaceFragment(new RankListFragment());
                break;
            case R.id.nav_item_log_in:
                replaceFragment(new LoginFragment());
                break;
            case R.id.nav_item_register:
                replaceFragment(new RegistrationFragment());
                break;
            case R.id.nav_item_log_out:
                replaceFragment(new HomeFragment());
                navigationView.getMenu().findItem(R.id.nav_item_home).setChecked(true);
                Data.loggedInUser = null;
                invalidateOptionsMenu();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!(currentFragment instanceof HomeFragment)) {
            replaceFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_item_home);
        } else {
            super.onBackPressed();
        }

    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        Menu menu1 = navigationView.getMenu();
        MenuItem logout = menu1.findItem(R.id.nav_item_log_out);
        MenuItem login = menu1.findItem(R.id.nav_item_log_in);
        MenuItem register = menu1.findItem(R.id.nav_item_register);

        if (Data.loggedInUser == null) {
            logout.setVisible(false);
            login.setVisible(true);
            register.setVisible(true);
        } else {
            login.setVisible(false);
            register.setVisible(false);
            logout.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}