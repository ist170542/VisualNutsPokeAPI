package pt.db.interactionpokeapi.model;

import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

import pt.db.interactionpokeapi.misc.ParameterResolver;
import pt.db.interactionpokeapi.network.GetPokemonsService;
import pt.db.interactionpokeapi.network.RetrofitInstanceCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonDataSource extends PageKeyedDataSource<Long, PokemonItem>{

    GetPokemonsService dataService;

    static int LIMIT_PER_PAGE = 10;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, PokemonItem> callback) {

        dataService = RetrofitInstanceCreator.getRetrofitInstance().create(GetPokemonsService.class);
        Call<GetPokemonResponse> data = dataService.getPokemonsPaged(LIMIT_PER_PAGE, 0);
        data.enqueue(new Callback<GetPokemonResponse>() {
            @Override
            public void onResponse(Call<GetPokemonResponse> call, Response<GetPokemonResponse> response) {
                List<PokemonItem> pokemonList = response.body().getResults();

                int inc = 1;

                for (PokemonItem pokemonItem : pokemonList){
                    pokemonItem.setPhotoURL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+inc+".png");
                    inc++;
                }

                callback.onResult(pokemonList,null,(long)2);

            }

            @Override
            public void onFailure(Call<GetPokemonResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, PokemonItem> callback) {

    }



    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, PokemonItem> callback) {

        ParameterResolver parameterResolver = new ParameterResolver("https://pokeapi.co/api/v2/pokemon/{pokemonID}/");

        dataService = RetrofitInstanceCreator.getRetrofitInstance().create(GetPokemonsService.class);
        Call<GetPokemonResponse> data = dataService.getPokemonsPaged(LIMIT_PER_PAGE, (int)((params.key-1)*10));
        data.enqueue(new Callback<GetPokemonResponse>() {
            @Override
            public void onResponse(Call<GetPokemonResponse> call, Response<GetPokemonResponse> response) {
                List<PokemonItem> pokemonList = response.body().getResults();


                for (PokemonItem pokemonItem : pokemonList){

                    Map paramUrl = parameterResolver.parametersByName(pokemonItem.getUrl());

                    if (paramUrl.containsKey("pokemonID")){
                        int id = Integer.valueOf((String)paramUrl.get("pokemonID"));
                        pokemonItem.setPhotoURL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+id+".png");
                    }

                }

                callback.onResult(pokemonList, params.key+1);
            }

            @Override
            public void onFailure(Call<GetPokemonResponse> call, Throwable t) {

            }
        });


    }

}
