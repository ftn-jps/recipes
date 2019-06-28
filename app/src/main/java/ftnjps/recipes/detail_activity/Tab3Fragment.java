package ftnjps.recipes.detail_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ftnjps.recipes.R;
import ftnjps.recipes.data.Comment;
import ftnjps.recipes.data.DatabaseInstance;
import ftnjps.recipes.data.Recipe;

public class Tab3Fragment extends Fragment {

    private Recipe mRecipe;
    private ListView mCommentListView;
    private ArrayList<Comment> mComments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_three, container, false);

        Button commentSend = view.findViewById(R.id.commentSend);
        commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView commentEdit = view.findViewById(R.id.commentEdit);
                String commentContent = commentEdit.getText().toString();
                commentEdit.setText("");
                if(commentContent.isEmpty())
                    return;
                Comment comment = new Comment(mRecipe.getId(), commentContent);

                // Add to local database
                long id = DatabaseInstance.getInstance(
                        getActivity().getApplicationContext())
                        .commentDao()
                        .insertOne(comment);
                comment.setId(id);
                // Add to firebase
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference commentsRef = database.getReference("comments");
                commentsRef.push().setValue(comment);
                // Update view
                mComments.add(comment);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecipe = (Recipe) this.getArguments().getSerializable("recipe");
        mComments = new ArrayList(
                DatabaseInstance.getInstance(
                        getActivity().getApplicationContext())
                        .commentDao()
                        .findByRecipeId(mRecipe.getId()));

        // KREIRANJE LISTE KOMENTARA POKUPLJENIH IZ BAZE
        mCommentListView = getActivity().findViewById(R.id.commentsListView);
        final CommentListAdapter adapter = new CommentListAdapter(getActivity(), R.layout.adapter_comment_layout, mComments);
        mCommentListView.setAdapter(adapter);
    }
}
