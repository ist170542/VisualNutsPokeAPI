package pt.db.interactionpokeapi.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FavouritePokemonItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PokemonItem pokemonItem);

    @Delete
    void delete(PokemonItem pokemonItem);

    @Query("DELETE FROM FavouritePokemons")
    void deleteAllFavouritePokemonsItems();

    @Query("SELECT * FROM favouritepokemons")
    LiveData<List<PokemonItem>> getAllFavouritePokemons();

}
