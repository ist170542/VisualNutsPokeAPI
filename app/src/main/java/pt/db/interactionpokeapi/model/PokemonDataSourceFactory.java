package pt.db.interactionpokeapi.model;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PokemonDataSourceFactory extends DataSource.Factory{

    PokemonDataSource pokemonDataSource;
    MutableLiveData<PokemonDataSource> mutableLiveData;

    public PokemonDataSourceFactory() {
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {

        pokemonDataSource = new PokemonDataSource();
        mutableLiveData.postValue(pokemonDataSource);

        return pokemonDataSource;

    }

    public MutableLiveData<PokemonDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
