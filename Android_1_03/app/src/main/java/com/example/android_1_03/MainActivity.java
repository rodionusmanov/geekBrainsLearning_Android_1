package com.example.android_1_03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int avatarId = R.drawable.homer_work;
    private static boolean startScreenWorked = false;
    private static String userName = "Пользователь";
    Bundle intent = new Bundle();
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        intent = getIntent().getExtras();
        if (intent != null) {
            avatarId = intent.getInt("AvatarId");
            startScreenWorked = intent.getBoolean("StartScreenWorked");
            userName = intent.getString("NameStr");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initStartScreen();

        initToolbarAndDrawer();

        /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            NoteExtendFragment nef = NoteExtendFragment.newInstance(0);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_right, nef)
                    .addToBackStack(null)
                    .commit();
        }*/

    }

    private void initStartScreen() {

        TextView startTV = findViewById(R.id.start_text);
        ImageView startIV = findViewById(R.id.start_picture);
        FrameLayout startFL = findViewById(R.id.start_picture_background);
        if (startScreenWorked) {
            initNotesApp();
            startFL.setVisibility(View.GONE);
            startTV.setVisibility(View.GONE);
            startIV.setVisibility(View.GONE);
        }

        startIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initNotesApp();
                startFL.setVisibility(View.GONE);
                startTV.setVisibility(View.GONE);
                startIV.setVisibility(View.GONE);
            }
        });

        startTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initNotesApp();
                startFL.setVisibility(View.GONE);
                startTV.setVisibility(View.GONE);
                startIV.setVisibility(View.GONE);
            }
        });

        startFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initNotesApp();
                startFL.setVisibility(View.GONE);
                startTV.setVisibility(View.GONE);
                startIV.setVisibility(View.GONE);
            }
        });
    }

    private void initToolbarAndDrawer() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView avatarIV = (ImageView) headerView.findViewById(R.id.avatar);
        TextView userNameTV = headerView.findViewById(R.id.userName);
        avatarIV.setImageResource(avatarId);
        userNameTV.setText(userName);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_drawer_choose_avatar:
                        openChooseAvatarFragment();
                        drawer.closeDrawers();
                        return true;
                    case R.id.action_drawer_change_name:
                        openChangeUserNameFragment();
                        drawer.closeDrawers();
                        return true;
                    case R.id.action_drawer_theme_switcher:
                        openThemeSwitcherFragment();
                        drawer.closeDrawers();
                        return true;
                    case R.id.action_drawer_exit:
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void openChangeUserNameFragment() {
        ChangeNameFragment changeNameFragment = new ChangeNameFragment();
        Bundle enterBundle = makeBundle();
        changeNameFragment.setArguments(enterBundle);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, changeNameFragment)
                .commit();
    }


    private void openChooseAvatarFragment() {
        ChooseAvatarFragment chooseAvatarFragment = new ChooseAvatarFragment();
        Bundle enterBundle = makeBundle();
        chooseAvatarFragment.setArguments(enterBundle);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, chooseAvatarFragment)
                .commit();
    }

    private void openThemeSwitcherFragment() {
        ThemeSwitcherFragment themeSwitcherFragment = new ThemeSwitcherFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, themeSwitcherFragment)
                .commit();
    }

    private Bundle makeBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("AvatarId", avatarId);
        bundle.putString("UserName", userName);
        bundle.putBoolean("StartScreenWorked", startScreenWorked);
        return bundle;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                boolean isAboutOpen = false;
                for (Fragment fragment : fragments) {
                    if (fragment instanceof AboutFragment && fragment.isVisible()) {
                        isAboutOpen = true;
                    }
                }
                if (!isAboutOpen) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.fragment_container, new AboutFragment())
                            .commit();
                }
                return true;
            case R.id.action_exit:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private void initNotesApp() {
        NotesListFragment notesListFragment = new NotesListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, notesListFragment)
                .commit();
    }

}