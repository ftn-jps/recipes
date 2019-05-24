package ftnjps.recipes;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

public class RecipeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_recipe);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Recept");
        adapter.addFragment(new Tab2Fragment(), "Poreklo jela");
        adapter.addFragment(new Tab3Fragment(), "Komentari");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("recipes");

        /*
        Recipe r1 = new Recipe("drawable://" + R.drawable.pic, "Rebarca pecena u rerni sa crnim pivom",
                "Pripremati sa srcem, paziti da ne pregori i da se ne popije pivo.",
                "Srednje",
                3,
                50,
                "Prelijemo rebarca marinadom\nUbacimo u rernu\nIzvadimo iz rerne",
                new Date()
        );
        Recipe r2 = new Recipe("drawable://" + R.drawable.pic, "Svinjska rebarca u marinadi od piva",
                "Pazljivo pripremati sa dusom, paziti da ne pregori nista i da se ne popije svo pivo.",
                "Srednje",
                4,
                30,
                "Kupimo 1kg rebarca i 1L crnog piva po izboru\nPrelijemo rebarca marinadom i pivom\nUbacimo u rernu\nIzvadimo iz rerne",
                new Date()
        );
        Recipe r3 = new Recipe("drawable://" + R.drawable.pic, "BBQ rebarca sa zapecenim graskom",
                "Dobro spremiti BBQ od njega ce zavisiti krajnji rezultat.",
                "Tesko",
                10,
                70,
                "Napravimo BBQ\nGrilamo rebarca\nJedemo rebarca",
                new Date()
        );
        Recipe r4 = new Recipe("drawable://" + R.drawable.pic, "Slasna kolenica",
                "Izabrati dobar komad mesa u mesari.",
                "Lako",
                2,
                65,
                "Pazljivo poslozimo kolenicu u rernu\nPremazemo marinadom\nIzvadimo iz rerne",
                new Date()
        );
        Recipe r5 = new Recipe("drawable://" + R.drawable.pic, "Kolenica u sacu",
                "Biti uz sac i mesati bez prestanka za bolji ukus.",
                "Srednje",
                5,
                95,
                "Spremimo materijal za sac\nNapravimo sac\nUbacimo kolenicu\nIzvadimo posle krckanja",
                new Date()
        ); */

        // ADDING TO FIREBASE TO RECIPES LIST
        //myRef.push().setValue(r4);
        //myRef.push().setValue(r5);

        // adding to Firebase database
        // myRef.setValue(r1);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_favorites) {
            // Handle the favorites action
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
