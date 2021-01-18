package pt.db.interactionpokeapi.model;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavouritePokemons")
public class PokemonItem {

    @PrimaryKey
    private @NotNull String name;
    private String url;
    private String photoURL;

    public PokemonItem() {
    }

    public static final DiffUtil.ItemCallback<PokemonItem> CALLBACK = new DiffUtil.ItemCallback<PokemonItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull PokemonItem pokemon, @NonNull PokemonItem t1) {
            return pokemon.url.equals(t1.url);
        }

        @Override
        public boolean areContentsTheSame(@NonNull PokemonItem pokemon, @NonNull PokemonItem t1) {

            if (pokemon == t1){
                return true;
            }

            return false;
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
