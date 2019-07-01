package ftnjps.recipes.detail_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ftnjps.recipes.R;
import ftnjps.recipes.data.Recipe;

public class IngredientListAdapter extends ArrayAdapter<Map.Entry<String,String>> {
    private static final String TAG = "IngredientListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView ingredientName;
        TextView ingredientAmount;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public IngredientListAdapter(Context context, int resource, List<Map.Entry<String,String>> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String ingredientName = getItem(position).getKey();
        String ingredientAmount = getItem(position).getValue();

        //create the view result for showing the animation
        final View result;

        //need final position for the inner class
        final int finalPosition = position;

        //ViewHolder object
        ViewHolder holder= new ViewHolder();


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder.ingredientName =  convertView.findViewById(R.id.ingredientName);
            holder.ingredientAmount =  convertView.findViewById(R.id.ingredientAmount);
            holder.ingredientName.setText(ingredientName);
            holder.ingredientAmount.setText(ingredientAmount);
            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;
        return convertView;
    }
}
