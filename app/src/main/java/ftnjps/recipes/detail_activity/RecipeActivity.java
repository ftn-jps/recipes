package ftnjps.recipes.detail_activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import ftnjps.recipes.R;
import ftnjps.recipes.data.Recipe;
import ftnjps.recipes.settings_activity.SettingsActivity;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

        String recipeId = getIntent().getStringExtra("RECIPE_ID");
        String recipeTitle = getIntent().getStringExtra("RECIPE_TITLE");
        String recipeDescription = getIntent().getStringExtra("RECIPE_DESCRIPTION");
        String recipeDifficulty = getIntent().getStringExtra("RECIPE_DIFFICULTY");
        ArrayList<String> recipePreparationSteps = (ArrayList<String>) getIntent().getSerializableExtra("RECIPE_PREPARATION_STEPS");
        String recipeNumberOfPeople = getIntent().getStringExtra("RECIPE_NUMBER_OF_PEOPLE");
        String recipeTimeOfPreparation = getIntent().getStringExtra("RECIPE_TIME_OF_PREPARATION");
        String recipeCreationDate = getIntent().getStringExtra("RECIPE_CREATION_DATE");
        String recipeImgURL = getIntent().getStringExtra("RECIPE_IMAGE_URL");
        String longitude = getIntent().getStringExtra("RECIPE_LONGITUDE");
        String latitude = getIntent().getStringExtra("RECIPE_LATITUDE");
        String youtubeURL = getIntent().getStringExtra("RECIPE_YOUTUBEURL");
        String isFavorite = getIntent().getStringExtra("RECIPE_IS_FAVORITE");
        HashMap<String,String> ingredients = (HashMap<String, String>) getIntent().getSerializableExtra("RECIPE_INGREDIENTS");

        Recipe r = new Recipe(recipeImgURL, recipeTitle, recipeDescription, recipeDifficulty, Integer.parseInt(recipeNumberOfPeople), Integer.parseInt(recipeTimeOfPreparation),
                recipePreparationSteps, new Date(), Double.parseDouble(latitude), Double.parseDouble(longitude), youtubeURL, ingredients);
        r.setId(Long.parseLong(recipeId));
        r.setFavorite(Boolean.parseBoolean(isFavorite));

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());

        Tab1Fragment tab1Fragment = new Tab1Fragment();
        Tab2Fragment tab2Fragment = new Tab2Fragment();
        Tab3Fragment tab3Fragment = new Tab3Fragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", r);
        tab1Fragment.setArguments(bundle);
        tab3Fragment.setArguments(bundle);
        tab2Fragment.setArguments(bundle);

        adapter.addFragment(tab1Fragment, "Recept");
        adapter.addFragment(tab2Fragment, "Poreklo jela");
        adapter.addFragment(tab3Fragment, "Komentari");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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
