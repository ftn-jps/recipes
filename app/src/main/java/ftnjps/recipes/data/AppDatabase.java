package ftnjps.recipes.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ftnjps.recipes.data.typeconverters.MapTypeConverter;

@Database(entities = {Recipe.class, Comment.class}, version = 3)
@TypeConverters({Converters.class, MapTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    public abstract CommentDao commentDao();
}
