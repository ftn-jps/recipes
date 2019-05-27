package ftnjps.recipes.data;

import android.content.Context;

import androidx.room.Room;
import ftnjps.recipes.data.AppDatabase;

// SINGLETON JER JE KREIRANJE BAZE SKUPO PA DA NE KREIRAMO STALNO NOVU
public class DatabaseInstance {

    private static final String DB_NAME = "recipes-database";
    private static volatile AppDatabase instance;

    public DatabaseInstance() {}

    //
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

}
