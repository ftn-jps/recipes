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

    @Query("SELECT * FROM recipe WHERE title LIKE :search")
    List<Recipe> getWithNameFilter(final String search);

    @Query("SELECT * FROM recipe WHERE difficulty LIKE :search")
    List<Recipe> getWithDifficultyFilter(final String search);

    @Query("SELECT * FROM recipe WHERE timeOfPreparation <= :search")
    List<Recipe> getWithTimeFilter(final int search);

    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    List<Recipe> findFavorites();

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
