package ftnjps.recipes.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CommentDao {

    @Query("SELECT * FROM comment")
    List<Comment> getAll();

    @Query("SELECT * FROM comment WHERE id = :comment_id")
    Comment findById(Long comment_id);

    @Query("SELECT * FROM comment WHERE recipeId = :recipe_id")
    List<Comment> findByRecipeId(Long recipe_id);

    @Insert
    void insertAll(Comment... comments);

    @Insert
    long insertOne(Comment comment);

    @Delete
    void delete(Comment comment);

    @Query("DELETE FROM comment")
    void deleteAll();

}
