package pt.db.interactionpokeapi.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import pt.db.interactionpokeapi.model.GetPokemonDetailedResponse;
import pt.db.interactionpokeapi.model.PokemonItem;
import pt.db.interactionpokeapi.repository.PokemonRepository;

public class PokemonDetailsViewModel extends AndroidViewModel {

    private PokemonRepository repository;
    private GetPokemonDetailedResponse requestedPokemonDetail;

    public PokemonDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new PokemonRepository(application);
    }

    public LiveData<GetPokemonDetailedResponse> getPokemonDetailed(String name){
        return repository.getPokemonDetails(name);
    }

}
