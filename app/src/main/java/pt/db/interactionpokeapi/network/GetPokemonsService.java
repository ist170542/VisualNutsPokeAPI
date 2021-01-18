package pt.db.interactionpokeapi.network;

import pt.db.interactionpokeapi.model.GetPokemonDetailedResponse;
import pt.db.interactionpokeapi.model.GetPokemonResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetPokemonsService {

    @GET("pokemon")
    Call<GetPokemonResponse> getPokemons();

    @GET("pokemon")
    Call<GetPokemonResponse> getPokemonsPaged(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{pokemonName}")
    Call<GetPokemonDetailedResponse> getPokemonDetails(@Path("pokemonName") String name);

}
