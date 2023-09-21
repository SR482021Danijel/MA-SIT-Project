package com.ftn.ma_sit_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.adapters.ProgramAdapter;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;
import com.ftn.ma_sit_project.fragments.FrendListFragment;
import com.ftn.ma_sit_project.fragments.HomeFragment;
import com.ftn.ma_sit_project.fragments.LoginFragment;
import com.ftn.ma_sit_project.fragments.MyViewModel;
import com.ftn.ma_sit_project.fragments.ProfileFragment;
import com.ftn.ma_sit_project.fragments.RankListFragment;
import com.ftn.ma_sit_project.fragments.RegistrationFragment;
import com.ftn.ma_sit_project.repository.UserRepository;
import com.google.android.material.navigation.NavigationView;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MyViewModel sharedViewModel;

    ListView listView;
    ProgramAdapter programAdapter;

    List<User> users = new ArrayList<>();
    String[] title = {"a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa"};
    String[] description = {"b", "bb", "bbb", "bbbb", "bbbbb", "bbbbbb", "bbbbbbb", "bbbbbbbb", "bbbbbbbbb"};
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    TextView p2UserName, p2Score, p1UserName, p1Score;
    MqttHandler mqttHandler = new MqttHandler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Get the current time in milliseconds
        long currentTime = System.currentTimeMillis();

        // Get the last time the player received tokens (0 if it's their first time playing)
        long lastTokenTime = sharedPref.getLong("lastTokenTime", 0);

        // Get the current number of tokens the player has (0 if it's their first time playing)
        int currentTokens = sharedPref.getInt("currentTokens", 0);

        // Check if a day has passed since the last time the player received tokens
        if (currentTime - lastTokenTime >= 86400000) {
            // Add 5 tokens to the player's current token count
            currentTokens += 5;

            // Update the stored values
            editor.putLong("lastTokenTime", currentTime);
            editor.putInt("currentTokens", currentTokens);
            editor.apply();
        }

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

        p1UserName = findViewById(R.id.player_1_user_name);
        p1Score = findViewById(R.id.player_1_score);
        p2UserName = findViewById(R.id.player_2_user_name);
        p2Score = findViewById(R.id.player_2_score);
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
            try {
                mqttHandler.disconnect();
                p1Score.setText("0");
                p2Score.setText("0");
                p1UserName.setText("Guest");
                p2UserName.setText("Guest");
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public int getCurrentTokens() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt("currentTokens", 0);
    }

    public void subtractOneToken() {
        // Get the current number of tokens
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int currentTokens = sharedPref.getInt("currentTokens", 0);

        // Subtract one token from the current token count
        currentTokens -= 1;

        if(currentTokens == 0){
            Toast.makeText(this, "Nemate dovoljno tokena", Toast.LENGTH_SHORT).show();
            currentTokens = 0;
        }

        // Update the stored value with the new token count
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("currentTokens", currentTokens);
        editor.apply();
    }
}