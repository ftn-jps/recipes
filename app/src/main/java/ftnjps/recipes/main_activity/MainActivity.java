package ftnjps.recipes.main_activity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ftnjps.recipes.R;
import ftnjps.recipes.detail_activity.RecipeActivity;
import ftnjps.recipes.settings_activity.SettingsActivity;
import ftnjps.recipes.data.DatabaseInstance;
import ftnjps.recipes.data.Recipe;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mListView;
    private ArrayList<Recipe> mRecipes = new ArrayList<Recipe>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView mSearchView;
    private RecipesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        System.out.println("MAIN ACTIVITY STARTED");

        // KREIRANJE LISTE RECEPATA POKUPLJENIH IZ BAZE
        mListView = findViewById(R.id.recipesListView);


        mSearchView = findViewById(R.id.simpleSearchView);
        mSearchView.requestFocus(0, null);
//        mSearchView.setIconified(false);


        mSearchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            @SuppressWarnings("unchecked")
            public boolean onQueryTextSubmit(String query) {
                mRecipes = new ArrayList(
                    DatabaseInstance
                        .getInstance(getApplicationContext())
                        .recipeDao()
                        .getWithFilter("%"+query+"%")
                );
                adapter = new RecipesListAdapter(MainActivity.this, R.layout.adapter_view_layout, mRecipes);
                mListView.setAdapter(adapter);

                return true;
            }
        });


        mSearchView = findViewById(R.id.simpleSearchView);
        mSearchView.requestFocus(0, null);
//        mSearchView.setIconified(false);


        mSearchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            @SuppressWarnings("unchecked")
            public boolean onQueryTextSubmit(String query) {
                mRecipes = new ArrayList(
                    DatabaseInstance
                        .getInstance(getApplicationContext())
                        .recipeDao()
                        .getWithFilter("%"+query+"%")
                );

               for( Recipe r: mRecipes) {
                   System.out.println(r.getTitle());
               }

                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // NA REFRESH SE UPDATE PRIKAZ
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);


        /*Recipe r1 = new Recipe("drawable://" + R.drawable.beer_ribs, "Rebarca pecena u rerni sa crnim pivom",
                "Pripremati sa srcem, paziti da ne pregori i da se ne popije pivo.",
                "Srednje",
                3,
                50,
                "Prelijemo rebarca marinadom\nUbacimo u rernu\nIzvadimo iz rerne",
                new Date(),
                45.2671,
                19.8335

        );
        Recipe r2 = new Recipe("drawable://" + R.drawable.oven_ribs, "Svinjska rebarca u marinadi od piva",
                "Pazljivo pripremati sa dusom, paziti da ne pregori nista i da se ne popije svo pivo.",
                "Srednje",
                4,
                30,
                "Kupimo 1kg rebarca i 1L crnog piva po izboru\nPrelijemo rebarca marinadom i pivom\nUbacimo u rernu\nIzvadimo iz rerne",
                new Date(),
                38.7223,
                9.1393

        );
        Recipe r3 = new Recipe("drawable://" + R.drawable.peas_ribs, "BBQ rebarca sa zapecenim graskom",
                "Dobro spremiti BBQ od njega ce zavisiti krajnji rezultat.",
                "Tesko",
                10,
                70,
                "Napravimo BBQ\nGrilamo rebarca\nJedemo rebarca",
                new Date(),
                41.3851,
                2.1734
        );
        Recipe r4 = new Recipe("drawable://" + R.drawable.roasted_pork_knukle, "Slasna kolenica",
                "Izabrati dobar komad mesa u mesari.",
                "Lako",
                2,
                65,
                "Pazljivo poslozimo kolenicu u rernu\nPremazemo marinadom\nIzvadimo iz rerne",
                new Date(),
                47.8864,
                106.9057
        );
        Recipe r5 = new Recipe("drawable://" + R.drawable.sauce_pork_knuckle, "Kolenica u sacu",
                "Biti uz sac i mesati bez prestanka za bolji ukus.",
                "Srednje",
                5,
                95,
                "Spremimo materijal za sac\nNapravimo sac\nUbacimo kolenicu\nIzvadimo posle krckanja",
                new Date(),
                18.8792,
                47.5079
        );*/

        Handler mHandler = new Handler();
        if(DatabaseInstance.getInstance(getApplicationContext()).recipeDao().getAll().size() == 0) {
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mRecipes = new ArrayList(DatabaseInstance.getInstance(getApplicationContext()).recipeDao().getAll());
                    adapter = new RecipesListAdapter(MainActivity.this, R.layout.adapter_view_layout, mRecipes);
                    mListView.setAdapter(adapter);

                    // NA KLIK JEDNOG RECEPTA IZ LISTE OTVARA SE DETAILVIEW RECEPTA
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent myIntent = new Intent(MainActivity.this, RecipeActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            MainActivity.this.startActivity(myIntent);
                        }
                    });
                }

            }, 2000L);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mRecipes = new ArrayList(DatabaseInstance.getInstance(getApplicationContext()).recipeDao().getAll());
        adapter = new RecipesListAdapter(this, R.layout.adapter_view_layout, mRecipes);
        mListView.setAdapter(adapter);

        // NA KLIK JEDNOG RECEPTA IZ LISTE OTVARA SE DETAILVIEW RECEPTA
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, RecipeActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                MainActivity.this.startActivity(myIntent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mRecipes = new ArrayList(DatabaseInstance.getInstance(getApplicationContext()).recipeDao().getAll());
                adapter = new RecipesListAdapter(MainActivity.this, R.layout.adapter_view_layout, mRecipes);
                mListView.setAdapter(adapter);

                // NA KLIK JEDNOG RECEPTA IZ LISTE OTVARA SE DETAILVIEW RECEPTA
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent myIntent = new Intent(MainActivity.this, RecipeActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        MainActivity.this.startActivity(myIntent);
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
