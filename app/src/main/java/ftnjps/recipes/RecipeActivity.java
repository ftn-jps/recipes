package ftnjps.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RecipeActivity extends AppCompatActivity {

    private TextView textViewRecipePreparationSteps;
    private TextView textViewRecipeTitle;
    private TextView textViewRecipeDifficultyPersonsTime;
    private TextView textViewRecipeCreationDate;
    private TextView textViewRecipeDescription;
    private ImageView imageViewRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        String recipeId = getIntent().getStringExtra("RECIPE_ID");
        String recipeTitle = getIntent().getStringExtra("RECIPE_TITLE");
        String recipeDescription = getIntent().getStringExtra("RECIPE_DESCRIPTION");
        String recipeDifficulty = getIntent().getStringExtra("RECIPE_DIFFICULTY");
        String recipePreparationSteps = getIntent().getStringExtra("RECIPE_PREPARATION_STEPS");
        String recipeNumberOfPeople = getIntent().getStringExtra("RECIPE_NUMBER_OF_PEOPLE");
        String recipeTimeOfPreparation = getIntent().getStringExtra("RECIPE_TIME_OF_PREPARATION");
        String recipeCreationDate = getIntent().getStringExtra("RECIPE_CREATION_DATE");
        String recipeImgURL = getIntent().getStringExtra("RECIPE_IMAGE_URL");

        textViewRecipePreparationSteps = findViewById(R.id.textViewRecipePreparationSteps);
        textViewRecipeTitle = findViewById(R.id.textViewRecipeTitle);
        textViewRecipeCreationDate = findViewById(R.id.textViewRecipeCreationDate);
        textViewRecipeDescription = findViewById(R.id.textViewRecipeDescription);
        textViewRecipeDifficultyPersonsTime = findViewById(R.id.textViewRecipeDifficultyPersonsTime);
        imageViewRecipe = findViewById(R.id.imageViewRecipe);

        textViewRecipeTitle.setText(recipeTitle);
        textViewRecipeCreationDate.setText(recipeCreationDate);
        textViewRecipeDescription.setText(recipeDescription);

        String difficultyPersonsTime = "Tezina: " + recipeDifficulty + ", ";
        difficultyPersonsTime += recipeNumberOfPeople + " osobe, ";
        difficultyPersonsTime += recipeTimeOfPreparation + " min";
        textViewRecipeDifficultyPersonsTime.setText(difficultyPersonsTime);

        String[] preparationSteps = recipePreparationSteps.split("\n");
        for(int i = 0; i< preparationSteps.length; i++) {
            int step = i + 1;
            textViewRecipePreparationSteps.append("Korak " + step + ":\n" + preparationSteps[i] + "\n");
        }

        //create the imageloader object
        ImageLoader imageLoader = ImageLoader.getInstance();

        int defaultImage = this.getResources().getIdentifier("@drawable/image_failed",null, this.getPackageName());

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(recipeImgURL, imageViewRecipe, options);
    }


}
