package ftnjps.recipes.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import ftnjps.recipes.data.Recipe;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipe WHERE id = :recipe_id")
    Recipe findById(Long recipe_id);

    @Insert
    void insertAll(Recipe... recipes);

    @Insert
    void insertOne(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("DELETE FROM recipe")
    void deleteAll();

    @Update
    void update(Recipe recipe);
}
