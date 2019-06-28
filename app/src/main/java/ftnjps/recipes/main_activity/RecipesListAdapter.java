package ftnjps.recipes.main_activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import androidx.annotation.NonNull;
import ftnjps.recipes.R;
import ftnjps.recipes.data.Recipe;
import ftnjps.recipes.detail_activity.RecipeActivity;

public class RecipesListAdapter extends ArrayAdapter<Recipe> {

    private static final String TAG = "RecipeListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView recipeDescription;
        ImageView recipeImage;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public RecipesListAdapter(Context context, int resource, ArrayList<Recipe> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the recipe information
        String recipeDescription = getItem(position).getRecipeDescription();
        String imgUrl = getItem(position).getImgURL();

        //create the view result for showing the animation
        final View result;

        //need final position for the inner class
        final int finalPosition = position;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.recipeDescription = (TextView) convertView.findViewById(R.id.textView1);
            holder.recipeImage = (ImageView) convertView.findViewById(R.id.image);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        //create the imageloader object
        ImageLoader imageLoader = ImageLoader.getInstance();

        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(imgUrl, holder.recipeImage, options);

        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");

        holder.recipeDescription.setText(recipeDescription);
        holder.recipeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra("RECIPE_ID", getItem(finalPosition).getId().toString());
                intent.putExtra("RECIPE_TITLE", getItem(finalPosition).getTitle());
                intent.putExtra("RECIPE_DESCRIPTION", getItem(finalPosition).getDescription());
                intent.putExtra("RECIPE_YOUTUBEURL", getItem(finalPosition).getYoutubeURL());
                intent.putExtra("RECIPE_DIFFICULTY", getItem(finalPosition).getDifficulty());
                intent.putExtra("RECIPE_PREPARATION_STEPS", getItem(finalPosition).getPreparationSteps());
                intent.putExtra("RECIPE_NUMBER_OF_PEOPLE", Integer.toString(getItem(finalPosition).getNumberOfPeople()));
                intent.putExtra("RECIPE_TIME_OF_PREPARATION", Integer.toString(getItem(finalPosition).getTimeOfPreparation()));
                intent.putExtra("RECIPE_LONGITUDE", getItem(finalPosition).getLongitude().toString());
                intent.putExtra("RECIPE_LATITUDE", getItem(finalPosition).getLatitude().toString());
                try {
                    intent.putExtra("RECIPE_CREATION_DATE", format.format(getItem(finalPosition).getCreationDate()));
                    System.out.println( format.parse(format.format(getItem(finalPosition).getCreationDate())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                intent.putExtra("RECIPE_IMAGE_URL", getItem(finalPosition).getImgURL());

                mContext.startActivity(intent);
                Toast.makeText(mContext, "Opening recipe...", Toast.LENGTH_SHORT).show();
            }
        });

        holder.recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra("RECIPE_ID", getItem(finalPosition).getId().toString());
                intent.putExtra("RECIPE_TITLE", getItem(finalPosition).getTitle());
                intent.putExtra("RECIPE_DESCRIPTION", getItem(finalPosition).getDescription());
                intent.putExtra("RECIPE_YOUTUBEURL", getItem(finalPosition).getYoutubeURL());
                intent.putExtra("RECIPE_DIFFICULTY", getItem(finalPosition).getDifficulty());
                intent.putExtra("RECIPE_PREPARATION_STEPS", getItem(finalPosition).getPreparationSteps());
                intent.putExtra("RECIPE_NUMBER_OF_PEOPLE", Integer.toString(getItem(finalPosition).getNumberOfPeople()));
                intent.putExtra("RECIPE_TIME_OF_PREPARATION", Integer.toString(getItem(finalPosition).getTimeOfPreparation()));
                intent.putExtra("RECIPE_LONGITUDE", getItem(finalPosition).getLongitude().toString());
                intent.putExtra("RECIPE_LATITUDE", getItem(finalPosition).getLatitude().toString());
                try {
                    intent.putExtra("RECIPE_CREATION_DATE", format.format(getItem(finalPosition).getCreationDate()));
                    System.out.println( format.parse(format.format(getItem(finalPosition).getCreationDate())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                intent.putExtra("RECIPE_IMAGE_URL", getItem(finalPosition).getImgURL());

                mContext.startActivity(intent);
                Toast.makeText(mContext, "Opening recipe...", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    /**
     * Required for setting up the Universal Image loader Library
     */
    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}
