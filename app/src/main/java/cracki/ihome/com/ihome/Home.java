package cracki.ihome.com.ihome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fragment.DoorFragment;
import fragment.SwitchFragment;
import fragment.TempFragment;
import fragment.WaterFragment;

public class Home extends AppCompatActivity {
    SessionManger sessionManger;
    BottomNavigationView navigation;
    Toolbar toolbar;
    ImageView img_title;
    TextView title;
    ImageView btnMenu;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_temp:
                    img_title.setImageResource(R.drawable.ic_celsius);
                    title.setText(R.string.title_temp);
                    loadFragment(TempFragment.newInstance());
                    return true;
                case R.id.navigation_switch:
                    img_title.setImageResource(R.drawable.ic_lighting);
                    title.setText(R.string.title_switch);
                    loadFragment(SwitchFragment.newInstance());
                    return true;
                case R.id.navigation_water:
                    img_title.setImageResource(R.drawable.ic_tank);
                    title.setText(R.string.title_water);
                    loadFragment(WaterFragment.newInstance());
                    return true;
                case R.id.navigation_door:
                    img_title.setImageResource(R.drawable.ic_lock_door);
                    title.setText(R.string.title_door);
                    loadFragment(DoorFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");


        img_title = findViewById(R.id.title_logo);
        title = findViewById(R.id.title_txt);
        btnMenu = findViewById(R.id.menu);


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(v);
                v.showContextMenu();
            }
        });
        setSupportActionBar(toolbar);

        sessionManger = new SessionManger(this);
        sessionManger.checkLogin();



        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setChecked(true);
        img_title.setImageResource(R.drawable.ic_celsius);
        title.setText(R.string.title_temp);
        loadFragment(TempFragment.newInstance());

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Settings");
        menu.setHeaderIcon(R.drawable.ic_settings);

        menu.add(0,v.getId(),0,"General");
        menu.add(0,v.getId(),0,"Register");
        menu.add(0,v.getId(),0,"About");
        menu.add(0,v.getId(),0,"Logout");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "General"){
            startActivity(new Intent(Home.this,Settings.class));
        }else if(item.getTitle() == "Register"){
            startActivity(new Intent(Home.this,Signup.class));
        }else if(item.getTitle() == "About"){
            startActivity(new Intent(Home.this,About.class));
        }else if(item.getTitle() == "Logout"){
            sessionManger.logout();
        }
        return true;
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commitNow();
    }

    @Override
    public void onBackPressed() {
        if (navigation.getSelectedItemId() == R.id.navigation_temp){
            super.onBackPressed();
        }else{
            navigation.setSelectedItemId(R.id.navigation_temp);
        }
    }
}
