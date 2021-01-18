package pt.db.interactionpokeapi.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pt.db.interactionpokeapi.model.FavouritePokemonDB;
import pt.db.interactionpokeapi.model.FavouritePokemonItemDao;
import pt.db.interactionpokeapi.model.GetPokemonDetailedResponse;
import pt.db.interactionpokeapi.model.GetPokemonResponse;
import pt.db.interactionpokeapi.model.PokemonItem;
import pt.db.interactionpokeapi.network.GetPokemonsService;
import pt.db.interactionpokeapi.network.RetrofitInstanceCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonRepository {

    private static PokemonRepository pokemonRepository;
    private FavouritePokemonItemDao pokeDao;

    private LiveData<List<PokemonItem>> favPokemons;

    GetPokemonsService dataService;

    public static PokemonRepository getInstance(Context context){
        if (pokemonRepository == null){
            pokemonRepository = new PokemonRepository(context);
        }
        return pokemonRepository;
    }

    public PokemonRepository(Context context){

        FavouritePokemonDB favouritePokemonDB = FavouritePokemonDB.getInstance(context.getApplicationContext());
        pokeDao = favouritePokemonDB.favouritePokemonDao();
        favPokemons = pokeDao.getAllFavouritePokemons();

        dataService = RetrofitInstanceCreator.getRetrofitInstance().create(GetPokemonsService .class);
    }

    public void insert(PokemonItem pokemonItem){
        new InsertFavPokemonAsyncTask(pokeDao).execute(pokemonItem);
    }

    public void delete(PokemonItem pokemonItem){
        new DeleteFavPokemonAsyncTask(pokeDao).execute(pokemonItem);
    }

    public void deleteAll(){
        new DeleteAllFavPokemonAsyncTask(pokeDao).execute();
    }

    public LiveData<List<PokemonItem>> getAllFavouritePokemons(){
        return favPokemons;
    }

    private static class InsertFavPokemonAsyncTask extends AsyncTask<PokemonItem, Void, Void>{
        private FavouritePokemonItemDao pokemonItemDao;

        private InsertFavPokemonAsyncTask(FavouritePokemonItemDao pokemonItemDao){
            this.pokemonItemDao = pokemonItemDao;
        }

        @Override
        protected Void doInBackground(PokemonItem... pokemonItems) {
            pokemonItemDao.insert(pokemonItems[0]);
            return null;
        }
    }

    private static class DeleteFavPokemonAsyncTask extends AsyncTask<PokemonItem, Void, Void>{
        private FavouritePokemonItemDao pokemonItemDao;

        private DeleteFavPokemonAsyncTask(FavouritePokemonItemDao pokemonItemDao){
            this.pokemonItemDao = pokemonItemDao;
        }

        @Override
        protected Void doInBackground(PokemonItem... pokemonItems) {
            pokemonItemDao.delete(pokemonItems[0]);
            return null;
        }
    }

    private static class DeleteAllFavPokemonAsyncTask extends AsyncTask<Void, Void, Void>{
        private FavouritePokemonItemDao pokemonItemDao;

        private DeleteAllFavPokemonAsyncTask(FavouritePokemonItemDao pokemonItemDao){
            this.pokemonItemDao = pokemonItemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pokemonItemDao.deleteAllFavouritePokemonsItems();
            return null;
        }
    }

    public LiveData<GetPokemonDetailedResponse> getPokemonDetails(String name){

        final MutableLiveData<GetPokemonDetailedResponse> pokemonDetails = new MutableLiveData<>();

        Call<GetPokemonDetailedResponse> data = dataService.getPokemonDetails(name);
        data.enqueue(new Callback<GetPokemonDetailedResponse>() {
            @Override
            public void onResponse(Call<GetPokemonDetailedResponse> call, Response<GetPokemonDetailedResponse> response) {

                if (response.body() != null){
                    pokemonDetails.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetPokemonDetailedResponse> call, Throwable t) {
                pokemonDetails.setValue(null);
            }
        });
        return pokemonDetails;

    }

}
