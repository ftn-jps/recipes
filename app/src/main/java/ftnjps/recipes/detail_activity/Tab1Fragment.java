package ftnjps.recipes.detail_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;

import androidx.fragment.app.Fragment;
import ftnjps.recipes.R;
import ftnjps.recipes.data.DatabaseInstance;
import ftnjps.recipes.data.Recipe;

public class Tab1Fragment extends Fragment {

    private TextView textViewRecipeTitle;
    private TextView textViewRecipePreparationSteps;
    private TextView textViewRecipeDifficultyPersonsTime;
    private TextView textViewRecipeCreationDate;
    private TextView textViewRecipeDescription;
    private ImageView imageViewRecipe;
    private Button buttonRecipe;
    private Switch switchFavorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_one, container, false);
        final Recipe r = (Recipe) this.getArguments().getSerializable("recipe");
        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");

        textViewRecipeTitle = v.findViewById(R.id.textViewRecipeTitle);
        textViewRecipePreparationSteps = v.findViewById(R.id.textViewRecipePreparationSteps);
        textViewRecipeCreationDate = v.findViewById(R.id.textViewRecipeCreationDate);
        textViewRecipeDescription = v.findViewById(R.id.textViewRecipeDescription);
        textViewRecipeDifficultyPersonsTime = v.findViewById(R.id.textViewRecipeDifficultyPersonsTime);
        imageViewRecipe = v.findViewById(R.id.imageViewRecipe);

        String difficultyPersonsTime = "Tezina: " + r.getDifficulty() + ", ";
        difficultyPersonsTime += r.getNumberOfPeople() + " osobe, ";
        difficultyPersonsTime += r.getTimeOfPreparation() + " min";

        textViewRecipeDifficultyPersonsTime.setText(difficultyPersonsTime);
        textViewRecipeTitle.setText(r.getTitle());
        textViewRecipeDescription.setText("\"" + r.getDescription() + "\"");

        try {
            textViewRecipeCreationDate.setText(format.format(r.getCreationDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] preparationSteps = r.getPreparationSteps().split("\n");
        for(int i = 0; i< preparationSteps.length; i++) {
            int step = i + 1;
            textViewRecipePreparationSteps.append("Korak " + step + ":\n" + preparationSteps[i] + "\n");
        }
        textViewRecipeTitle.setText(r.getTitle());

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
