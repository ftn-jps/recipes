package ftnjps.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import androidx.fragment.app.Fragment;

public class Tab1Fragment extends Fragment {

    private TextView textViewRecipeTitle;
    private TextView textViewRecipePreparationSteps;
    private TextView textViewRecipeDifficultyPersonsTime;
    private TextView textViewRecipeCreationDate;
    private TextView textViewRecipeDescription;
    private ImageView imageViewRecipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_one, container, false);
        Recipe r = (Recipe) this.getArguments().getSerializable("recipe");
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
        textViewRecipeDescription.setText(r.getRecipeDescription());

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


        return v;
    }
}
