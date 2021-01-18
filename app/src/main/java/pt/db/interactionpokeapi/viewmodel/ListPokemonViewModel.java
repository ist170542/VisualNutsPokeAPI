package pt.db.interactionpokeapi.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pt.db.interactionpokeapi.model.PokemonDataSource;
import pt.db.interactionpokeapi.model.PokemonDataSourceFactory;
import pt.db.interactionpokeapi.model.PokemonItem;

public class ListPokemonViewModel extends AndroidViewModel {

//        PhotoRepository photoRepository;
        PokemonDataSourceFactory pokemonDataSourceFactory;
        MutableLiveData<PokemonDataSource> dataSourceMutableLiveData;
        Executor executor;
        LiveData<PagedList<PokemonItem>> pagedListLiveData;

        public ListPokemonViewModel(@NonNull Application application) {
            super(application);

            pokemonDataSourceFactory = new PokemonDataSourceFactory();
            dataSourceMutableLiveData = pokemonDataSourceFactory.getMutableLiveData();

            PagedList.Config config = (new PagedList.Config.Builder())
                    .setEnablePlaceholders(true)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .setPrefetchDistance(10)
                    .build();
            executor = Executors.newFixedThreadPool(5);
            pagedListLiveData = (new LivePagedListBuilder<Long,PokemonItem>(pokemonDataSourceFactory,config))
                    .setFetchExecutor(executor)
                    .build();


        }

        public LiveData<PagedList<PokemonItem>> getPagedListLiveData() {
            return pagedListLiveData;
        }
    }

