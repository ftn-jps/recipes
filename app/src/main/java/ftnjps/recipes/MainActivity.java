package ftnjps.recipes;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView mListView = (ListView) findViewById(R.id.listView);

        Recipe r1 = new Recipe("drawable://" + R.drawable.beer_ribs, "Rebarca pecena u rerni sa crnim pivom",
                "Pripremati sa srcem, paziti da ne pregori i da se ne popije pivo.",
                "Srednje",
                3,
                50,
                "Prelijemo rebarca marinadom\nUbacimo u rernu\nIzvadimo iz rerne",
                new Date()
                );
        Recipe r2 = new Recipe("drawable://" + R.drawable.oven_ribs, "Svinjska rebarca u marinadi od piva",
                "Pazljivo pripremati sa dusom, paziti da ne pregori nista i da se ne popije svo pivo.",
                "Srednje",
                4,
                30,
                "Kupimo 1kg rebarca i 1L crnog piva po izboru\nPrelijemo rebarca marinadom i pivom\nUbacimo u rernu\nIzvadimo iz rerne",
                new Date()
        );
        Recipe r3 = new Recipe("drawable://" + R.drawable.peas_ribs, "BBQ rebarca sa zapecenim graskom",
                "Dobro spremiti BBQ od njega ce zavisiti krajnji rezultat.",
                "Tesko",
                10,
                70,
                "Napravimo BBQ\nGrilamo rebarca\nJedemo rebarca",
                new Date()
        );
        Recipe r4 = new Recipe("drawable://" + R.drawable.roasted_pork_knukle, "Slasna kolenica",
                "Izabrati dobar komad mesa u mesari.",
                "Lako",
                2,
                65,
                "Pazljivo poslozimo kolenicu u rernu\nPremazemo marinadom\nIzvadimo iz rerne",
                new Date()
        );
        Recipe r5 = new Recipe("drawable://" + R.drawable.sauce_pork_knuckle, "Kolenica u sacu",
                "Biti uz sac i mesati bez prestanka za bolji ukus.",
                "Srednje",
                5,
                95,
                "Spremimo materijal za sac\nNapravimo sac\nUbacimo kolenicu\nIzvadimo posle krckanja",
                new Date()
        );

        ArrayList<Recipe> recipesList = new ArrayList<>();
        recipesList.add(r1);
        recipesList.add(r2);
        recipesList.add(r3);
        recipesList.add(r4);
        recipesList.add(r5);
        recipesList.add(r1);
        recipesList.add(r2);
        recipesList.add(r3);
        recipesList.add(r4);
        recipesList.add(r5);

        RecipesListAdapter adapter = new RecipesListAdapter(this, R.layout.adapter_view_layout, recipesList);
        mListView.setAdapter(adapter);

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
        getMenuInflater().inflate(R.menu.main, menu);
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
