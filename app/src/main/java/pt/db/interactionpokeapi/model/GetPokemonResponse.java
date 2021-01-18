package pt.db.interactionpokeapi.model;

import java.util.ArrayList;

public class GetPokemonResponse {

    private Integer count;
    private String previous;
    private String next;
    private ArrayList<PokemonItem> results;

    public GetPokemonResponse() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public ArrayList<PokemonItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<PokemonItem> results) {
        this.results = results;
    }
}
