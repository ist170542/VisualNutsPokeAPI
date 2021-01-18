package pt.db.interactionpokeapi.ui.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import pt.db.interactionpokeapi.R;
import pt.db.interactionpokeapi.adapter.PokemonAdapter;
import pt.db.interactionpokeapi.model.PokemonItem;
import pt.db.interactionpokeapi.viewmodel.ListFavouritePokemonViewModel;
import pt.db.interactionpokeapi.viewmodel.ListPokemonViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListPokemonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListPokemonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPokemonsFragment extends Fragment implements PokemonAdapter.OnPokemonClickedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListPokemonViewModel mainActivityViewModel;
    RecyclerView pokemonRecylerview;

    ListFavouritePokemonViewModel listFavouritePokemonViewModel;

    public ListPokemonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListPokemonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListPokemonsFragment newInstance(String param1, String param2) {
        ListPokemonsFragment fragment = new ListPokemonsFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_list_pokemons, container, false);

        mainActivityViewModel = ViewModelProviders.of(this).get(ListPokemonViewModel.class);

        pokemonRecylerview = view.findViewById(R.id.recylerview);

        pokemonRecylerview.setLayoutManager(new GridLayoutManager(getActivity(),2));

        PokemonAdapter photosAdapter = new PokemonAdapter(this);

        listFavouritePokemonViewModel = ViewModelProviders.of(this).get(ListFavouritePokemonViewModel.class);

        mainActivityViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), new Observer<PagedList<PokemonItem>>() {
            @Override
            public void onChanged(@Nullable PagedList<PokemonItem> pokemons) {
                photosAdapter.submitList(pokemons);
                pokemonRecylerview.setAdapter(photosAdapter);
            }
        });

//        mainActivityViewModel.getPagedListLiveData().removeObserver();

        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onPokemonClicked(PokemonItem pokemonItem) {

        Log.e("Poke", "poke url "  + pokemonItem.getName());

        FragmentManager fm = getFragmentManager();
        ShowPokemonDetailsDialogFragment editNameDialogFragment = ShowPokemonDetailsDialogFragment.newInstance(pokemonItem.getName(), pokemonItem.getPhotoURL());
        editNameDialogFragment.show(fm, "fragment_show_pokemon_details");

    }

    @Override
    public void onPokemonLongClicked(PokemonItem pokemonItem) {
        listFavouritePokemonViewModel.insert(pokemonItem);
    }
}
