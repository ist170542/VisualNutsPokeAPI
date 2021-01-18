package pt.db.interactionpokeapi.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {PokemonItem.class}, version = 1)
public abstract class FavouritePokemonDB extends RoomDatabase {

    private static FavouritePokemonDB instance;

    public abstract FavouritePokemonItemDao favouritePokemonDao();

    public static synchronized FavouritePokemonDB getInstance(Context context){

        if (instance == null){

            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    FavouritePokemonDB.class,
                    "fav_poke_database").fallbackToDestructiveMigration().build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
