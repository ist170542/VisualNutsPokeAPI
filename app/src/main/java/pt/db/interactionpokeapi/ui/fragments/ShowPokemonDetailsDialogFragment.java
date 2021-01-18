package pt.db.interactionpokeapi.ui.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import pt.db.interactionpokeapi.R;
import pt.db.interactionpokeapi.model.GetPokemonDetailedResponse;
import pt.db.interactionpokeapi.model.PokemonItem;
import pt.db.interactionpokeapi.viewmodel.ListFavouritePokemonViewModel;
import pt.db.interactionpokeapi.viewmodel.PokemonDetailsViewModel;

public class ShowPokemonDetailsDialogFragment extends DialogFragment {

    public ShowPokemonDetailsDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ShowPokemonDetailsDialogFragment newInstance(String name, String photoUrl) {
        ShowPokemonDetailsDialogFragment frag = new ShowPokemonDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("photoUrl", photoUrl);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_pokeman_details_dialog_fragment, container);
    }

    TextView nameTextView;
    TextView idTextView;
    TextView heightTextView;
    TextView baseExperienceTextView;
    TextView weigthTextView;
    TextView orderTextView;

    ImageView logoPokeImageView;

    PokemonDetailsViewModel pokemonDetailsViewModel;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        idTextView = (TextView) view.findViewById(R.id.id_textview);
        heightTextView = (TextView) view.findViewById(R.id.heigth_textview);
        baseExperienceTextView = (TextView) view.findViewById(R.id.base_experience_textview);
        weigthTextView = (TextView) view.findViewById(R.id.weight_textview);
        orderTextView = (TextView) view.findViewById(R.id.order_textview);

        logoPokeImageView = (ImageView) view.findViewById(R.id.logoPokeImageView);

        //round the edges
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        String name = getArguments().getString("name", "bulbasaur");
        String photoUrl = getArguments().getString("photoUrl", "https://www.tm-town.com/assets/default_male600x600-79218392a28f78af249216e097aaf683.png");

        Glide.with(getContext()).load(photoUrl).into(logoPokeImageView);

        pokemonDetailsViewModel = ViewModelProviders.of(this).get(PokemonDetailsViewModel.class);

        pokemonDetailsViewModel.getPokemonDetailed(name).observe(getViewLifecycleOwner(), new Observer<GetPokemonDetailedResponse>() {
            @Override
            public void onChanged(@Nullable GetPokemonDetailedResponse pokemons) {

                nameTextView.setText(getString(R.string.name_field,name));
                idTextView.setText(getString(R.string.id_field,pokemons.getId()));
                heightTextView.setText(getString(R.string.height_field,pokemons.getId()));
                baseExperienceTextView.setText(getString(R.string.base_experience_field, pokemons.getBase_experience()));
                weigthTextView.setText(getString(R.string.weight_field, pokemons.getWeight()));
                orderTextView.setText(getString(R.string.order_field, pokemons.getOrder()));

            }
        });

    }
}
