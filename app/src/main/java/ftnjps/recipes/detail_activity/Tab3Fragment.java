package ftnjps.recipes.detail_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ftnjps.recipes.R;
import ftnjps.recipes.data.Comment;
import ftnjps.recipes.data.DatabaseInstance;
import ftnjps.recipes.data.Recipe;

public class Tab3Fragment extends Fragment {

    private ListView mListView;
    private ArrayList<Comment> mComments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Recipe r = (Recipe) this.getArguments().getSerializable("recipe");
        mComments = new ArrayList(
                DatabaseInstance.getInstance(
                        getActivity().getApplicationContext())
                        .commentDao()
                        .findByRecipeId(r.getId()));

        // KREIRANJE LISTE KOMENTARA POKUPLJENIH IZ BAZE
        mListView = getActivity().findViewById(R.id.commentsListView);
        final CommentListAdapter adapter = new CommentListAdapter(getActivity(), R.layout.adapter_comment_layout, mComments);
        mListView.setAdapter(adapter);
    }
}
