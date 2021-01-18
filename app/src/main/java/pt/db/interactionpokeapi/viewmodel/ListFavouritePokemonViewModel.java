package pt.db.interactionpokeapi.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pt.db.interactionpokeapi.model.PokemonItem;
import pt.db.interactionpokeapi.repository.PokemonRepository;

public class ListFavouritePokemonViewModel extends AndroidViewModel {

    private PokemonRepository repository;
    private LiveData<List<PokemonItem>> favouritePokemons;

    public ListFavouritePokemonViewModel(@NonNull Application application) {
        super(application);
        repository = new PokemonRepository(application);
        favouritePokemons = repository.getAllFavouritePokemons();
    }

    public LiveData<List<PokemonItem>> getAll(){
        return favouritePokemons;
    }

    public void insert(PokemonItem pokemonItem){
        repository.insert(pokemonItem);
    }

    public void delete(PokemonItem pokemonItem){
        repository.delete(pokemonItem);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
