package pt.db.interactionpokeapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import pt.db.interactionpokeapi.R;
import pt.db.interactionpokeapi.model.PokemonItem;

public class PokemonFavouriteAdapter extends RecyclerView.Adapter< PokemonFavouriteAdapter.PokemonViewHolder> {

    OnPokemonClickedListener onPokemonClickedListener;

    List<PokemonItem> list = new ArrayList<>();

    public void setList(List<PokemonItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public PokemonFavouriteAdapter(OnPokemonClickedListener onPokemonClickedListener) {
//        super(PokemonItem.CALLBACK);
        this.onPokemonClickedListener = onPokemonClickedListener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.pokemon_list_item_layout,viewGroup,false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder pokemonViewHolder, int i) {

        Glide.with(pokemonViewHolder.itemView.getContext()).load(list.get(i).getPhotoURL()).into(pokemonViewHolder.pokePhoto);
        pokemonViewHolder.pokeName.setText(list.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        ImageView pokePhoto;
        TextView pokeName;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokePhoto = itemView.findViewById(R.id.imageViewPokemon);
            pokeName = itemView.findViewById(R.id.textViewPokemonName);

            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v)
                {
                    onPokemonClickedListener.onPokemonClicked(list.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                public boolean onLongClick(View v)
                {
                    onPokemonClickedListener.onPokemonLongClicked(list.get(getAdapterPosition()));

                    return true;
                }
            });

        }
    }

    public interface OnPokemonClickedListener{
        void onPokemonClicked(PokemonItem pokemonItem);
        void onPokemonLongClicked(PokemonItem pokemonItem);

    }

}
