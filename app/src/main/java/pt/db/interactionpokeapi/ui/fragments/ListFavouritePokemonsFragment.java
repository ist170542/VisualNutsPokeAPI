package pt.db.interactionpokeapi.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.db.interactionpokeapi.R;
import pt.db.interactionpokeapi.adapter.PokemonFavouriteAdapter;
import pt.db.interactionpokeapi.model.PokemonItem;
import pt.db.interactionpokeapi.viewmodel.ListFavouritePokemonViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFavouritePokemonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFavouritePokemonsFragment extends Fragment implements PokemonFavouriteAdapter.OnPokemonClickedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListFavouritePokemonViewModel listFavPokemonsViewModel;
    RecyclerView pokemonRecylerview;

    public ListFavouritePokemonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFavouritePokemons.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFavouritePokemonsFragment newInstance(String param1, String param2) {
        ListFavouritePokemonsFragment fragment = new ListFavouritePokemonsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_favourite_pokemons, container, false);

        listFavPokemonsViewModel = ViewModelProviders.of(this).get(ListFavouritePokemonViewModel.class);

        pokemonRecylerview = view.findViewById(R.id.recylerviewListFavouritePokes);

        pokemonRecylerview.setLayoutManager(new GridLayoutManager(getActivity(),2));

        PokemonFavouriteAdapter photosAdapter = new PokemonFavouriteAdapter(this);
        pokemonRecylerview.setAdapter(photosAdapter);

        listFavPokemonsViewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<PokemonItem>>() {
            @Override
            public void onChanged(@Nullable List<PokemonItem> pokemonItems) {
                Log.e("FavPoke", "size fav " + pokemonItems.size());
                photosAdapter.setList(pokemonItems);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onPokemonClicked(PokemonItem pokemonItem) {

        FragmentManager fm = getFragmentManager();
        ShowPokemonDetailsDialogFragment editNameDialogFragment = ShowPokemonDetailsDialogFragment.newInstance(pokemonItem.getName(), pokemonItem.getPhotoURL());
        editNameDialogFragment.show(fm, "fragment_show_pokemon_details");

    }

    @Override
    public void onPokemonLongClicked(PokemonItem pokemonItem) {

        listFavPokemonsViewModel.delete(pokemonItem);

    }
}