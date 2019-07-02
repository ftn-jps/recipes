package ftnjps.recipes.detail_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

import ftnjps.recipes.R;

public class PreparationListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "IngredientListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView stepNumber;
        TextView stepDescription;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PreparationListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int stepNum = position + 1;
        String stepNumber = "Korak " + stepNum + ".";
        String stepDescription = getItem(position);

        //create the view result for showing the animation
        final View result;

        //need final position for the inner class
        final int finalPosition = position;

        //ViewHolder object
        ViewHolder holder= new ViewHolder();


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder.stepNumber =  convertView.findViewById(R.id.stepNumber);
            holder.stepDescription =  convertView.findViewById(R.id.stepDescription);
            holder.stepNumber.setText(stepNumber);
            holder.stepDescription.setText(stepDescription);
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
