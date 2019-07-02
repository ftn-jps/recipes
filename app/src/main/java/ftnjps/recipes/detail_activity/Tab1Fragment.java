package ftnjps.recipes.detail_activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.fragment.app.Fragment;
import ftnjps.recipes.R;
import ftnjps.recipes.data.DatabaseInstance;
import ftnjps.recipes.data.Recipe;
import ftnjps.recipes.main_activity.RecipesListAdapter;

public class Tab1Fragment extends Fragment {

    private TextView textViewRecipeTitle;
    private TextView textViewRecipePreparationSteps;
    private TextView textViewDifficulty;
    private TextView textViewPersons;
    private TextView textViewTime;
    private TextView textViewRecipeCreationDate;
    private TextView textViewRecipeDescription;
    private ImageView imageViewRecipe;
    private Button buttonRecipe;
    private Switch switchFavorite;
    private ListView listViewIngredients;
    private Map<String,String> mIngredients;
    private IngredientListAdapter adapter;
    private ListView listViewPreparationSteps;
    private Map<String,String> mPreparationSteps;
    private PreparationListAdapter preparationAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_one, container, false);
        final Recipe r = (Recipe) this.getArguments().getSerializable("recipe");
        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");

        List<Map.Entry<String,String>> ingredients = new ArrayList<>();
        adapter = new IngredientListAdapter(getActivity(), R.layout.adapter_ingredient_layout, ingredients);
        for (String key : r.getIngredients().keySet()){
            Map.Entry<String,String> entry = new AbstractMap.SimpleEntry<String,String>(key, r.getIngredients().get(key));
            adapter.add(entry);
        }

        ArrayList<String> steps = new ArrayList<>();
        preparationAdapter = new PreparationListAdapter(getActivity(), R.layout.adapter_preparation_layout, steps);
        for (String step : r.getPreparationSteps()){
            preparationAdapter.add(step);
        }

        listViewIngredients = v.findViewById(R.id.listViewIngredients);
        listViewIngredients.setAdapter(adapter);

        listViewPreparationSteps = v.findViewById(R.id.listViewPreparationSteps);
        listViewPreparationSteps.setAdapter(preparationAdapter);

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) listViewIngredients.getLayoutParams();
        lp.height = 142 * ingredients.size();
        listViewIngredients.setLayoutParams(lp);

        int height = 0;
        for (String i : steps) {
            height += i.length();
        }
        ViewGroup.LayoutParams lp1 = (ViewGroup.LayoutParams) listViewPreparationSteps.getLayoutParams();
        lp1.height = 3 * height + 100* steps.size();
        listViewPreparationSteps.setLayoutParams(lp1);

        textViewRecipeTitle = v.findViewById(R.id.textViewRecipeTitle);
//        textViewRecipePreparationSteps = v.findViewById(R.id.textViewRecipePreparationSteps);
        textViewRecipeCreationDate = v.findViewById(R.id.textViewRecipeCreationDate);
        textViewRecipeDescription = v.findViewById(R.id.textViewRecipeDescription);

        textViewDifficulty = v.findViewById(R.id.difficulty);
        textViewPersons = v.findViewById(R.id.persons);
        textViewTime = v.findViewById(R.id.time);

        imageViewRecipe = v.findViewById(R.id.imageViewRecipe);
        listViewIngredients = v.findViewById(R.id.listViewIngredients);

        textViewDifficulty.setText(r.getDifficulty());
        switch (r.getDifficulty()) {
            case "Tesko":
                textViewDifficulty.setTextColor(Color.rgb(139,0,0));
                break;

            case "Srednje":
                textViewDifficulty.setTextColor(Color.rgb(204,204,0));
                break;

            case "Lako":
                textViewDifficulty.setTextColor(Color.GREEN);
                break;

            default: textViewDifficulty.setTextColor(Color.DKGRAY);
        }
        textViewPersons.setText(String.format("Osoba: %s", r.getNumberOfPeople()));
        textViewTime.setText(String.format("%s", r.getTimeOfPreparation() + " min"));

        textViewRecipeTitle.setText(r.getTitle());
        textViewRecipeDescription.setText("\"" + r.getDescription() + "\"");

        try {
            textViewRecipeCreationDate.setText(format.format(r.getCreationDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }




//        String[] preparationSteps = r.getPreparationSteps().split("\n");
//        for(int i = 0; i< preparationSteps.length; i++) {
//            int step = i + 1;
//            textViewRecipePreparationSteps.append("Korak " + step + ":\n" + preparationSteps[i] + "\n");
//        }
//        textViewRecipeTitle.setText(r.getTitle());

        //create the imageloader object
        ImageLoader imageLoader = ImageLoader.getInstance();

        //int defaultImage = this.getResources().getIdentifier("@drawable/image_failed",null, this.getPackageName());

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.pic)
                .showImageOnFail(R.drawable.pic)
                .showImageOnLoading(R.drawable.pic).build();

        //download and display image from url
        imageLoader.displayImage(r.getImgURL(), imageViewRecipe, options);

        buttonRecipe = (Button) v.findViewById(R.id.buttonRecipe);
        buttonRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent( getContext(), YoutubeActivity.class);
                myIntent.putExtra("RECIPE_YOUTUBEURL", r.getYoutubeURL());
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                getContext().startActivity(myIntent);
            }
        });

        switchFavorite = v.findViewById(R.id.switchFavorite);
        switchFavorite.setChecked(r.isFavorite());
        switchFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                r.setFavorite(isChecked);
                DatabaseInstance
                        .getInstance(getActivity().getApplicationContext())
                        .recipeDao()
                        .update(r);
            }
        });

        return v;
    }
}
