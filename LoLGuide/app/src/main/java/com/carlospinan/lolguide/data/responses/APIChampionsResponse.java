package com.carlospinan.lolguide.data.responses;

import com.carlospinan.lolguide.data.models.api.APIChampion;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class APIChampionsResponse {

    private List<APIChampion> champions;

    public List<APIChampion> getChampions() {
        return champions;
    }

    public void setChampions(List<APIChampion> champions) {
        this.champions = champions;
    }
}
